package vn.hoidanit.jobhunter.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import vn.hoidanit.jobhunter.domain.User;
import vn.hoidanit.jobhunter.dto.Meta;
import vn.hoidanit.jobhunter.dto.ResponseCreateDTO;
import vn.hoidanit.jobhunter.dto.ResponseUpdateDTO;
import vn.hoidanit.jobhunter.dto.ResultPaginationDTO;
import vn.hoidanit.jobhunter.repository.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;


    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;

    }

    public ResponseCreateDTO handleCreateUser(User user) {
    User handleUser = userRepository.save(user);
        ResponseCreateDTO rDTO = new ResponseCreateDTO();
        rDTO.setId(handleUser.getId());
        rDTO.setName(handleUser.getName());
        rDTO.setEmail(handleUser.getEmail());
        rDTO.setGender(handleUser.getGender());
        rDTO.setAddress(handleUser.getAddress());
        rDTO.setAge(handleUser.getAge());
        rDTO.setCreatedAt(handleUser.getCreatedAt());
        return rDTO;

    }

    public void handleDeleteUser(long id) {
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

    public ResponseUpdateDTO handleUpdate(User ReqUser) {
        User currentUser = this.handleGetUserByID(ReqUser.getId());
        if (currentUser != null) {
            currentUser.setName(ReqUser.getName());
            currentUser.setEmail(ReqUser.getEmail());
            currentUser.setPassword(ReqUser.getPassword());
            currentUser.setGender(ReqUser.getGender());
            currentUser.setAddress(ReqUser.getAddress());
            currentUser.setAge(ReqUser.getAge());
            currentUser = this.userRepository.save(currentUser);

        }
        ResponseUpdateDTO rDTO = new ResponseUpdateDTO();
        rDTO.setId(currentUser.getId());
        rDTO.setName(currentUser.getName());
        rDTO.setEmail(currentUser.getEmail());
        rDTO.setGender(currentUser.getGender());
        rDTO.setAddress(currentUser.getAddress());
        rDTO.setAge(currentUser.getAge());
        rDTO.setUpdatedAt(currentUser.getUpdatedAt());
        return rDTO;
    }

    public User handleGetUserByUsername(String username) {
        return this.userRepository.findByEmail(username);


    }
}
