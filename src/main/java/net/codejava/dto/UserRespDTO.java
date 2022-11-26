package net.codejava.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRespDTO {
	private int id;
	private String email;
	private String firstName;
	private String lastName;
	private String image;
	private String gender;
	private String birthday;
	private String createdDate;

}
