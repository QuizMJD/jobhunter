package vn.hoidanit.jobhunter.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import vn.hoidanit.jobhunter.domain.User;
import vn.hoidanit.jobhunter.service.UserService;
import vn.hoidanit.jobhunter.service.error.IdInvalidException;

import java.util.List;

@RestController
public class UserController {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public UserController(UserService userService ,PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/users")
    public ResponseEntity<User> createNewUser(@RequestBody User postManUser) {
        String hashPassword=passwordEncoder.encode(postManUser.getPassword());
        postManUser.setPassword(hashPassword);
       User ericUser= this.userService.handleCreateUser(postManUser);
       return ResponseEntity.status(HttpStatus.CREATED).body(ericUser);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable("id") long id) throws IdInvalidException {
        if(id>=1500){
            throw new IdInvalidException("id lớn hơn 1501");
        }
    this.userService.handledeleteUser(id);
//        return ResponseEntity.status(HttpStatus.CREATED).body("ericUser");
        return ResponseEntity.ok("ericUser");
    }
    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUserByID(@PathVariable("id") long id) {
       User userID= this.userService.handleGetUserByID(id);
        return ResponseEntity.ok(userID);
    }
//
    @GetMapping("/users")
    public ResponseEntity<List<User> >getUserAll() {
        return ResponseEntity.ok(this.userService.getAllUsers());
    }

    @PutMapping("/users")
    public ResponseEntity<User> updateUser(@RequestBody User user) {
            return ResponseEntity.ok(this.userService.handleUpdate(user));
    }

    }
