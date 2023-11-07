package com.saqaya.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.saqaya.dto.UserDto;
import com.saqaya.entity.User;
import com.saqaya.exception.AlreadyExistsException;
import com.saqaya.exception.EmptyContentException;
import com.saqaya.mapper.UserMapper;
import com.saqaya.repository.UserRepository;
import com.saqaya.service.UserService;

/**
 * 
 * <h1>UserServiceImpl Class</h1> 
 * <p>This service contains services for User.</p>
 *
 * @author Heba Abd El-Halim
 *
 */
@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepository repository;
	
	@Autowired
	private UserMapper mapper;
	
	@Override
	public UserDto create(UserDto dto) {
		
		User user = mapper.mapUserDtoToUser(dto);
		
		if(repository.findById(user.getId()).isPresent())
			throw new AlreadyExistsException();
		
		return mapper.mapUserToUserDto(repository.save(user));
		
	}

	@Override
	public UserDto find(String id) {
		User user = repository.findById(id)
				.orElseThrow(EmptyContentException::new);
		
		return mapper.mapUserToUserDto(user);
	}
	
}
