package com.runstory.domain.user.dto;

import com.runstory.domain.user.RegType;
import com.runstory.domain.user.RoleType;
import com.runstory.domain.user.entity.User;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class UserDto {
    private Long userSeq;
    private String userId;
    private String userPwd;
    private String userName;
    private String userNickname;
    private boolean emailAuth;
    private String phoneNum;
    private int gender;
    private String address;
    private int age;
    private String token;
    private RoleType roleType;
    private int level;
    private int experience;
    private String profileImgFilePath;
    private String profileImgFileName;
    private RegType regType;
    private LocalDateTime regdate;
    private LocalDateTime updatedate;

    public UserDto(User user){
        this.userSeq = user.getUserSeq();
        this.userId = user.getUserId();
        this.userPwd = user.getUserPwd();
        this.userName = user.getUserName();
        this.userNickname = user.getUserNickname();
        this.emailAuth = user.getEmailAuth();
        this.phoneNum = user.getPhoneNum();
        this.gender = user.getGender();
        this.address = user.getAddress();
        this.age = user.getAge();
        this.token = user.getToken();
        this.roleType = user.getRoleType();
        this.level = user.getLevel();
        this.experience = user.getExperience();
        this.profileImgFilePath = user.getProfileImgFilePath();
        this.profileImgFileName = user.getProfileImgFileName();
        this.regType = user.getRegType();
        this.regdate = user.getRegdate();
        this.updatedate = user.getUpdatedate();
    }
}
