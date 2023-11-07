package com.saqaya.mapper.impl;

import org.springframework.stereotype.Service;

import com.saqaya.dto.UserDto;
import com.saqaya.entity.User;
import com.saqaya.mapper.UserMapper;
import com.saqaya.util.SecurityUtil;

/**
 * 
 * <h1>UserMapperImpl Class</h1> 
 * <p>Mapping between DTOs & Entities.</p>
 *
 * @author Heba Abd El-Halim
 *
 */
@Service
public class UserMapperImpl implements UserMapper {

	@Override
	public User mapUserDtoToUser(UserDto dto) {
		User user = new User();
		
		user.setId(SecurityUtil.generateSHA1Hash(dto.getEmail()));
		user.setFirstName(dto.getFirstName());
		user.setLastName(dto.getLastName());
		user.setEmail(dto.getEmail());
		user.setMarketingConsent(dto.isMarketingConsent());
		
		return user;
	}
	
	@Override
	public UserDto mapUserToUserDto(User user) {
		UserDto dto = new UserDto();
		
		dto.setId(user.getId());
		dto.setFirstName(user.getFirstName());
		dto.setLastName(user.getLastName());
		
		if(user.isMarketingConsent()) 
			dto.setEmail(user.getEmail());
		
		dto.setMarketingConsent(user.isMarketingConsent());
		
		return dto;
	}
}
