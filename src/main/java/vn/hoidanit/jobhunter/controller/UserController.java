package vn.hoidanit.jobhunter.controller;

import com.turkraft.springfilter.boot.Filter;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.hoidanit.jobhunter.domain.User;
import vn.hoidanit.jobhunter.dto.ResponseCreateDTO;
import vn.hoidanit.jobhunter.dto.ResponseUpdateDTO;
import vn.hoidanit.jobhunter.dto.ResponseUserDTO;
import vn.hoidanit.jobhunter.dto.ResultPaginationDTO;
import vn.hoidanit.jobhunter.service.UserService;
import vn.hoidanit.jobhunter.service.error.IdInvalidException;
import vn.hoidanit.jobhunter.util.annotation.ApiMessage;

@RestController
@RequestMapping("/api/v1")
public class UserController {
    private final UserService userService;
    public UserController(UserService userService ) {
        this.userService = userService;
    }

    @PostMapping("/users")
    @ApiMessage("tao thanh cong")
    public ResponseEntity<ResponseCreateDTO> createNewUser(@Valid @RequestBody User postManUser) throws IdInvalidException {
        User User= this.userService.handleCreateUser(postManUser);
       return ResponseEntity.status(HttpStatus.CREATED).body(userService.ConvertUserCreateToDTO(User));
    }

    @DeleteMapping("/users/{id}")
    @ApiMessage("Xoa thanh cong")
    public ResponseEntity<User> deleteUser(@PathVariable("id") long id) throws IdInvalidException {
        this.userService.handleDeleteUser(id);
        return ResponseEntity.ok(null);
    }
    ////
    @GetMapping("/users/{id}")
    public ResponseEntity<ResponseUserDTO> getUserByID(@PathVariable("id") long id) throws IdInvalidException {
//       User userID= this.userService.handleGetUserByID(id);
        return ResponseEntity.ok(this.userService.ConvertDetailUserDTO(this.userService.handleGetUserByID(id)));
    }

    @GetMapping("/users")
    @ApiMessage("fetch all user")
    public ResponseEntity<ResultPaginationDTO>getUserAll(
            @Filter Specification<User> spec,Pageable pageable
    ) {
        return ResponseEntity.ok(this.userService.getAllUsers(spec,pageable));
    }

    @PutMapping("/users")
    public ResponseEntity<ResponseUpdateDTO> updateUser(@Valid @RequestBody User userClient) throws IdInvalidException{
//        User user=this.userService.handleUpdate(userClient);
        return ResponseEntity.ok(this.userService.ConvertUserUpdateToDTO(this.userService.handleUpdate(userClient)));
    }



    }
