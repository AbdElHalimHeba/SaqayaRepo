package com.saqaya.mapper;

import com.saqaya.dto.UserDto;
import com.saqaya.entity.User;

public interface UserMapper {

	User mapUserDtoToUser(UserDto dto);
	UserDto mapUserToUserDto(User user);

}
