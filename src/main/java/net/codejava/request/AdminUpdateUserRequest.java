package net.codejava.request;

import lombok.Data;

import java.util.Date;

@Data
public class AdminUpdateUserRequest {

    private int userId;
    private String password;
    private String confirmPassword;
    private String firstName;
    private String lastName;
    private String image;
    private String gender;
    private Date birthday;

}
