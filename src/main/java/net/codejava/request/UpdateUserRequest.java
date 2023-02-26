package net.codejava.request;

import lombok.Data;

import java.util.Date;

import javax.validation.constraints.NotNull;

@Data
public class UpdateUserRequest {

    private String firstName;
    private String lastName;
    private String image;
    private String gender;
    private Date birthday;

}
