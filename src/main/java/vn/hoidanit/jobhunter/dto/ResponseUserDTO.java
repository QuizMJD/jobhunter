package vn.hoidanit.jobhunter.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.hoidanit.jobhunter.util.constant.GenderEnum;

import java.time.Instant;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResponseUserDTO {
    private long id;
    private String name;
    private String email;
    private int age;
    @Enumerated(EnumType.STRING)
    private GenderEnum gender;
    private String address;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss a",timezone = "GMT+7")
    private Instant createdAt;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss a",timezone = "GMT+7")
    private Instant updatedAt;


}
