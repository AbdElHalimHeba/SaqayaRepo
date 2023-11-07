package com.saqaya.service;

import com.saqaya.dto.UserDto;

public interface UserService {

	UserDto create(UserDto dto);
	UserDto find(String id);
}
