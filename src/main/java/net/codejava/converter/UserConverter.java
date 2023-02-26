package net.codejava.converter;

import net.codejava.model.Profile;
import net.codejava.model.User;
import net.codejava.dto.UserRespDTO;

public class UserConverter {
	public static UserRespDTO toRespDTO(User entity) {

		Profile profile = entity.getProfile();

		UserRespDTO userRespDTO = UserRespDTO.builder().id(entity.getId()).email(entity.getEmail())
				.createdDate(entity.getCreatedDate().toString()).isEnable(entity.isEnabled()).build();

		if (profile != null) {
			userRespDTO.setFirstName(profile.getFirstName());
			userRespDTO.setLastName(profile.getLastName());
			userRespDTO.setImage(profile.getImage());
			userRespDTO.setGender(profile.getGender());
			userRespDTO.setBirthday(profile.getBirthday().toString());
		}

		return userRespDTO;
	}
}
