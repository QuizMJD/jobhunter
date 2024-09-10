package vn.hoidanit.jobhunter.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import vn.hoidanit.jobhunter.domain.User;
import vn.hoidanit.jobhunter.service.UserService;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

//    @GetMapping("/user/create")
    @PostMapping("/user/create")
    public User createNewUser(@RequestBody User postManUser) {

       User ericUser= this.userService.handleCreateUser(postManUser);

        return ericUser;
    }

}
