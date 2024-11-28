package vn.hoidanit.jobhunter.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import vn.hoidanit.jobhunter.domain.User;
import vn.hoidanit.jobhunter.dto.Meta;
import vn.hoidanit.jobhunter.dto.ResultPaginationDTO;
import vn.hoidanit.jobhunter.repository.UserRepository;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;


    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;

    }

    public User handleCreateUser(User user) {
//        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return this.userRepository.save(user);
    }

    public void handledeleteUser(long id) {
        this.userRepository.deleteById(id);
    }

    public User handleGetUserByID(long id) {
        return this.userRepository.findById(id).orElse(null);
    }

    public ResultPaginationDTO getAllUsers(Specification<User> spec,Pageable pageableRequest) {
        Page<User> pageUser = this.userRepository.findAll(spec,pageableRequest);
        ResultPaginationDTO rs = new ResultPaginationDTO();
        Meta mt = new Meta();
        mt.setPage(pageableRequest.getPageNumber()+1);
        mt.setPageSize(pageableRequest.getPageSize());
        mt.setPages(pageUser.getTotalPages());
        mt.setTotal(pageUser.getTotalElements());
        rs.setMeta(mt);
        rs.setResult(pageUser.getContent());



        return rs;
    }

    public User handleUpdate(User ReqUser) {
        User currentUser = this.handleGetUserByID(ReqUser.getId());
        if (currentUser != null) {
            currentUser.setName(ReqUser.getName());
            currentUser.setEmail(ReqUser.getEmail());
            currentUser.setPassword(ReqUser.getPassword());
            currentUser = this.userRepository.save(currentUser);

        }
        return currentUser;
    }

    public User handleGetUserByUsername(String username) {
        return this.userRepository.findByEmail(username);


    }
}
