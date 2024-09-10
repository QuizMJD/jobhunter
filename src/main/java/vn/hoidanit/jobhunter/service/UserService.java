package vn.hoidanit.jobhunter.service;

import org.springframework.stereotype.Service;

import vn.hoidanit.jobhunter.domain.User;
import vn.hoidanit.jobhunter.repository.UserRepository;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User handleCreateUser(User user) {
      return  this.userRepository.save(user);
    }
    public void handledeleteUser(long id){
    this.userRepository.deleteById(id);
    }
    public User handleGetUserByID(long id) {
        return  this.userRepository.findById(id).orElse(null);
    }
    public List<User> getAllUsers() {
        return this.userRepository.findAll();
    }
//    public User handleSaveUser(User user) {
//        return this.userRepository.save(user);
//
//    }
    public User handleUpdate(User ReqUser,long id) {
        User currentUser = this.handleGetUserByID(id);
        if (currentUser != null) {
            currentUser.setName(ReqUser.getName());
            currentUser.setEmail(ReqUser.getEmail());
            currentUser.setPassword(ReqUser.getPassword());
            currentUser=this.userRepository.save(currentUser);

        }


        return currentUser;
    }


}
