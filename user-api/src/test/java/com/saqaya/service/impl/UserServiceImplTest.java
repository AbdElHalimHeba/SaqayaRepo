package com.saqaya.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.saqaya.dto.UserDto;
import com.saqaya.entity.User;
import com.saqaya.exception.AlreadyExistsException;
import com.saqaya.exception.EmptyContentException;
import com.saqaya.repository.UserRepository;
import com.saqaya.service.UserService;
import com.saqaya.util.SecurityUtil;

/**
 * 
 * <h1>UserServiceImplTest Class</h1> 
 * <p>JUnit for User service.</p>
 *
 * @author Heba Abd El-Halim
 *
 */
@SpringBootTest
public class UserServiceImplTest {

	@Autowired
	private UserService service;
	
	@MockBean
	private UserRepository repository;
			
	@Test
	public void create_returnUserIfIdNotExists() {
		
		UserDto input = buildUserDto();
		User expectedUser = buildUser();
		
		when(repository.findById(anyString())).thenReturn(Optional.empty());
		when(repository.save(any())).thenReturn(expectedUser);
		
		UserDto actualUser = service.create(input);
		
		assertion(expectedUser, actualUser);
	}
	
	@Test
	public void create_throwExceptionIfIdExists() {
		
		UserDto input = buildUserDto();
		User expectedUser = buildUser();
		
		when(repository.findById(anyString())).thenReturn(Optional.of(expectedUser));
		
		assertThrows(AlreadyExistsException.class, 
				() -> service.create(input));
	}
	
	@Test
	public void find_returnUserIfIdExists() {
		
		User expectedUser = buildUser();
		
		when(repository.findById(anyString())).thenReturn(Optional.of(expectedUser));
		
		UserDto actualUser = service.find(anyString());
		
		assertion(expectedUser, actualUser);
	}
	
	@Test
	public void find_throwExceptionIfIdNotExists() {
		
		when(repository.findById(anyString())).thenReturn(Optional.empty());
		
		assertThrows(EmptyContentException.class, 
				() -> service.find(anyString()));
	}
	
	private void assertion(User expectedUser, UserDto actualUser) {
		
		assertEquals(expectedUser.getId(), actualUser.getId());
		
		if(actualUser.isMarketingConsent())
			assertEquals(expectedUser.getEmail(), actualUser.getEmail());
		else
			assertNull(actualUser.getEmail());
		
		assertEquals(expectedUser.getFirstName(), actualUser.getFirstName());
		assertEquals(expectedUser.getLastName(), actualUser.getLastName());
		assertEquals(expectedUser.isMarketingConsent(), actualUser.isMarketingConsent());
	}
	
	private User buildUser() {
		User user = new User();
		
		user.setEmail("info@saqaya.com");
		user.setFirstName("Michael");
		user.setLastName("Knight");
		user.setMarketingConsent(false);
		user.setId(SecurityUtil.generateSHA1Hash(user.getEmail()));
		
		return user;
	}
	
	private UserDto buildUserDto() {
		UserDto dto = new UserDto();
		
		dto.setEmail("info@saqaya.com");
		dto.setFirstName("Michael");
		dto.setLastName("Knight");
		dto.setMarketingConsent(false);
		
		return dto;
	}
	
}
