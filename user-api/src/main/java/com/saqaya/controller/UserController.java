package com.saqaya.controller;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.saqaya.dto.UserDto;
import com.saqaya.service.UserService;
import com.saqaya.util.SecurityUtil;

/**
 * 
 * <h1>UserController Class</h1> 
 * <p>This controller contains user operations.</p>
 *
 * @author Heba Abd El-Halim
 *
 */
@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService service;
	
	@PostMapping
	public UserDto create(@Valid @RequestBody UserDto dto, HttpServletResponse response) {
		UserDto created = service.create(dto);
		
		SecurityUtil.addJwtToResponse(SecurityUtil.generateJWT(dto.getEmail()), response);
		
		return created;
	}
	
	@GetMapping
    public UserDto find(@RequestParam String id) {
		return service.find(id);
    }
			
}
