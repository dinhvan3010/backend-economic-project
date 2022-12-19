package net.codejava.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.*;
import net.codejava.enums.Gender;

@Entity
@Data
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@Builder
@Table(name = "profiles")
public class Profile {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private int id;
	@Column
	private String firstName;
	@Column
	private String lastName;
	@Column
	private String image;
	@Column
	private Gender gender;
	@Column
	private String photoId;
	@Column
	private Date birthday;
	@OneToOne(mappedBy = "profile")
	private User user;

}
