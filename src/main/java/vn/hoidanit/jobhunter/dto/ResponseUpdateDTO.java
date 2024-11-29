package vn.hoidanit.jobhunter.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;
import vn.hoidanit.jobhunter.util.constant.GenderEnum;

import java.time.Instant;
@Getter
@Setter
public class ResponseUpdateDTO {
    private long id;
    private String name;
    private String email;
    @Enumerated(EnumType.STRING)
    private GenderEnum gender;
    private String address;
    private int age;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss a",timezone = "GMT+7")
    private Instant updatedAt;


}
