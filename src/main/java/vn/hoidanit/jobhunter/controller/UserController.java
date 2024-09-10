package vn.hoidanit.jobhunter.controller;

import org.springframework.web.bind.annotation.*;
import vn.hoidanit.jobhunter.domain.User;
import vn.hoidanit.jobhunter.service.UserService;

import java.util.List;

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
    @GetMapping("/user/{id}")
    public User getUserByID(@PathVariable("id") long id) {
       User userID= this.userService.handleGetUserByID(id);

        return userID;
    }
    @GetMapping("/user")
    public List<User> getUserAll() {
        List<User> users= this.userService.getAllUsers();
        return users;
    }
//    @PutMapping("/user/{id}")
//    public User updateUser(@RequestBody User postManUser,@PathVariable("id") long id) {
//        User currentUser = this.userService.handleGetUserByID(postManUser.getId());
//        if (currentUser != null) {
//            currentUser.setName(postManUser.getName());
//            currentUser.setEmail(postManUser.getEmail());
//            currentUser.setPassword(postManUser.getPassword());
//
//        }
//
//        User ListUserCurrent= this.userService.handleSaveUser(currentUser);
//        return ListUserCurrent;
//    }
@PutMapping("/user/{id}")
public User updateUser(@RequestBody User postManUser,@PathVariable("id") long id) {
    postManUser.setId(id);
//        User ListUserCurrent= this.userService.handleUpdate(postManUser);
        return this.userService.handleUpdate(postManUser,id);
}

}
