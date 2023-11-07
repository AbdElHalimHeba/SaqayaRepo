package com.saqaya.dto;

import javax.validation.constraints.NotBlank;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Setter
@Getter
@ToString
@EqualsAndHashCode
@Validated
public class UserDto {
	
	private String id;
	
	@NotBlank
	private String firstName;
	
	@NotBlank
	private String lastName;
	
	@JsonInclude(Include.NON_NULL)
	@NotBlank
	private String email;
	
	private boolean marketingConsent;
}
