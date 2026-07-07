package com.mobility.platform.authenticationservice.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LoginRequest {

	@NotBlank
	@Email
	private String email;
	@Size(min = 8, max = 20)
	private String password;
}
