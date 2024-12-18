package vn.hoidanit.jobhunter.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import vn.hoidanit.jobhunter.domain.User;
import vn.hoidanit.jobhunter.dto.LoginDTO;
import vn.hoidanit.jobhunter.dto.ResLoginDTO;
import vn.hoidanit.jobhunter.service.UserService;
import vn.hoidanit.jobhunter.util.SecurityUtil;
import vn.hoidanit.jobhunter.util.annotation.ApiMessage;

@RestController
@RequestMapping("/api/v1")
public class AuthController {
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final SecurityUtil securityUtil;
    private final UserService userService;
    @Value("${hoidanit.jwt.refresh-token-validity-in-seconds}")
    private long refreshTokenExpiration;
    public AuthController(AuthenticationManagerBuilder authenticationManagerBuilder,SecurityUtil securityUtil,UserService userService) {
        this.authenticationManagerBuilder = authenticationManagerBuilder;
        this.securityUtil = securityUtil;
        this.userService = userService;
    }

    @PostMapping("/auth/login")
    public ResponseEntity<ResLoginDTO>login(@Valid @RequestBody LoginDTO loginDTO){
        //Nạp input gồm username/password vào Security
        UsernamePasswordAuthenticationToken authenticationToken
                = new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword());
        //xác thực người dùng => cần viết hàm loadUserByUsername
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        //set thong tin dang nhap vao context (can will use in future)
        SecurityContextHolder.getContext().setAuthentication(authentication);

        ResLoginDTO res=new ResLoginDTO();
        User currentUserDB=this.userService.handleGetUserByUsername(loginDTO.getUsername());
        if(currentUserDB!=null){
            ResLoginDTO.UserLogin userLogin= new ResLoginDTO.UserLogin(
                    currentUserDB.getId()
                    ,currentUserDB.getEmail()
                    ,currentUserDB.getName());
            res.setUser(userLogin);
        }
        // create token
        String access_token= this.securityUtil.createAccessToken(authentication,res.getUser());




        res.setAccessToken(access_token);
        //create refresh toekn
        String refresh_token=this.securityUtil.createRefreshToken(loginDTO.getUsername(),res);
        //update user
        this.userService.updateUserToken(refresh_token,loginDTO.getUsername());
        //set cookies
        ResponseCookie responseCookie=ResponseCookie.from("refresh_token1",refresh_token)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(refreshTokenExpiration)
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, responseCookie.toString())
                .body(res);
    }

    @GetMapping("/auth/account")
    @ApiMessage("fetch account")
    public ResponseEntity<ResLoginDTO.UserLogin> getAccount(){
        String email=SecurityUtil.getCurrentUserLogin().isPresent()?
                SecurityUtil.getCurrentUserLogin().get():"";

        User currentUserDB=this.userService.handleGetUserByUsername(email);
        ResLoginDTO.UserLogin userLogin= new ResLoginDTO.UserLogin();
        if(currentUserDB!=null){
            userLogin.setId(currentUserDB.getId());
            userLogin.setMail(currentUserDB.getEmail());
            userLogin.setName(currentUserDB.getName());
        }
        return ResponseEntity.ok(userLogin);
    }
}
