package otpishAI.otpishAI_Backend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

//유저 정보 엔티티
@Entity
@Getter
@Setter
public class User {

    @Id
    private String email;

    private String username;
    private String name;

    private String role;

    private String addr;
    private String nickname;
    private Date birth;
    private String phone;
    private String profile_img;
    private Integer gender;
    private String genre1;
    private String genre2;
    private String genre3;
    private String is_suspended;
    private String is_secessioned;


}
