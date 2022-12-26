package net.codejava.request;

import lombok.Data;

import java.util.Date;

import javax.validation.constraints.NotNull;

@Data
public class UpdateUserRequest {
 
    @NotNull
    private String firstName;
    private String lastName;
    private String image;
    @NotNull
    private String gender;
    private Date birthday;



}
