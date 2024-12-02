package vn.hoidanit.jobhunter.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import vn.hoidanit.jobhunter.domain.RestResponse;
import vn.hoidanit.jobhunter.domain.User;
import vn.hoidanit.jobhunter.dto.*;
import vn.hoidanit.jobhunter.repository.UserRepository;
import vn.hoidanit.jobhunter.service.error.IdInvalidException;
import vn.hoidanit.jobhunter.util.annotation.ApiMessage;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;

    }

    public User handleCreateUser(User user) throws IdInvalidException {
        User getCurrentEmail=handleGetUserByUsername(user.getEmail());
        if(getCurrentEmail !=null) {
            throw new IdInvalidException("email: " +user.getEmail()+"da ton tai vui long nhap mail khac");
        }
        String hashPassword=passwordEncoder.encode(user.getPassword());
        user.setPassword(hashPassword);
        return this.userRepository.save(user);
    }


    public ResponseCreateDTO ConvertUserCreateToDTO(User user) {
        ResponseCreateDTO rDTO = new ResponseCreateDTO();
           rDTO.setId(user.getId());
           rDTO.setName(user.getName());
           rDTO.setEmail(user.getEmail());
           rDTO.setGender(user.getGender());
           rDTO.setAddress(user.getAddress());
           rDTO.setAge(user.getAge());
           rDTO.setCreatedAt(user.getCreatedAt());
           return rDTO;
       }


    public User handleDeleteUser(long id) throws IdInvalidException {
         if(id>=1500){
            throw new IdInvalidException("id lớn hơn 1501");
        }
        User userToDelete = this.handleGetUserByID(id);
        if (userToDelete == null) {
            throw new IdInvalidException("Id: " + id + " Không tồn tại vui lòng nhập id khác");
        }

        this.userRepository.deleteById(id);
        return userToDelete; // Trả về User đã xóa
    }

    public User handleGetUserByID(long id) throws IdInvalidException {
        User findID = this.userRepository.findById(id).orElse(null);
        if (findID == null) {
            throw new IdInvalidException("Id: " + id + " Không tồn tại vui lòng nhập id khác");
        }
        return findID ;
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
//        rs.setResult(pageUser.getContent());

        // remove sensitive data
        List<ResponseUserDTO> listUser = pageUser.getContent()
                .stream().map(item -> new ResponseUserDTO(
                        item.getId(),
                        item.getName(),
                        item.getEmail(),
                        item.getAge(),
                        item.getGender(),
                        item.getAddress(),
                        item.getUpdatedAt(),
                        item.getCreatedAt()))
                .collect(Collectors.toList());
        rs.setResult(listUser);





        return rs;
    }

    public User handleUpdate(User ReqUser) throws IdInvalidException {
        User currentUser = this.handleGetUserByID(ReqUser.getId());
        if (currentUser != null) {
            currentUser.setName(ReqUser.getName());
            currentUser.setEmail(ReqUser.getEmail());
            currentUser.setPassword(ReqUser.getPassword());
            currentUser.setGender(ReqUser.getGender());
            currentUser.setAddress(ReqUser.getAddress());
            currentUser.setAge(ReqUser.getAge());
        }
        else {
            throw new IdInvalidException("Id: "+ReqUser.getId()+" Khong ton tai vui long nhap id khac");
        }
        return userRepository.save(currentUser);

    }
    public ResponseUpdateDTO convertUserUpdateToDTO (User user){
        ResponseUpdateDTO rDTO = new ResponseUpdateDTO();
        rDTO.setId(user.getId());
        rDTO.setName(user.getName());
        rDTO.setEmail(user.getEmail());
        rDTO.setGender(user.getGender());
        rDTO.setAddress(user.getAddress());
        rDTO.setAge(user.getAge());
        rDTO.setUpdatedAt(user.getUpdatedAt());
        return rDTO;
    }

    public User handleGetUserByUsername(String username) {

        return this.userRepository.findByEmail(username);

    }
    public ResponseUserDTO convertDetailUserDTO(User user){
        ResponseUserDTO rDTO = new ResponseUserDTO();
        rDTO.setId(user.getId());
        rDTO.setName(user.getName());
        rDTO.setEmail(user.getEmail());
        rDTO.setGender(user.getGender());
        rDTO.setAddress(user.getAddress());
        rDTO.setAge(user.getAge());
        rDTO.setCreatedAt(user.getCreatedAt());
        rDTO.setUpdatedAt(user.getUpdatedAt());
        return rDTO;
    }
    public void updateUserToken(String token,String email) {
        User currentUser = this.userRepository.findByEmail(email);
        if (currentUser != null) {
            currentUser.setRefreshToken(token);
            this.userRepository.save(currentUser);
        }
    }




}

