package vn.hoidanit.jobhunter.controller;

import org.springframework.web.bind.annotation.*;
import vn.hoidanit.jobhunter.domain.User;
import vn.hoidanit.jobhunter.service.UserService;

@RestController
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

//    @GetMapping("/user/create")
    @PostMapping("/user")
    public User createNewUser(@RequestBody User postManUser) {
       User ericUser= this.userService.handleCreateUser(postManUser);

       return ericUser;
    }
    @DeleteMapping("/user/{id}")
    public String deleteUser(@PathVariable("id") long id) {
    this.userService.handledeleteUser(id);
        return "ericUser";
    }

}
