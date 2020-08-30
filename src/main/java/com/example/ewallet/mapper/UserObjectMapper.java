package com.example.ewallet.mapper;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.example.ewallet.datatransferobject.UserDTO;
import com.example.ewallet.models.User;

/** The Class to map Database Objects and Data Transfer Objects */
public class UserObjectMapper {

	/** converts DTO to Database Object */
	public static User dtoToDO(UserDTO a) {
		User account = new User.UserAccountBuilder().setDateCreated(new Date()).setId(a.getId())
				.setUserName(a.getUserName()).setEmail(a.getEmail()).setBalance(a.getBalance()).build();
		return account;
	}

	/** Converts Database Object to DTO */
	public static UserDTO doToDTO(User a) {
		UserDTO dto = new UserDTO.UserAccountDTOBuilder().setId(a.getId())
				.setDateCreated(a.getDateCreated()).setUserName(a.getUserName()).setId(a.getId()).setEmail(a.getEmailId())
				.setBalance(a.getBalance()).build();
		return dto;
	}

	/** Converts List of Database Object to List of DTO */
	public static List<UserDTO> doToDTOList(List<User> account) {
		return account.stream().map(UserObjectMapper::doToDTO).collect(Collectors.toList());
	}

}
