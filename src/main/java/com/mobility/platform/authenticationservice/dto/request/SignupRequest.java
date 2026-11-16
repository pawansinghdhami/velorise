package com.mobility.platform.authenticationservice.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SignupRequest {

	@NotBlank
    private String firstName;
	@NotBlank
	private String lastName;
	@Email
	@NotBlank
	private String email;
	@Size(min = 8, max = 20)
	private String password;
	@Size(min = 10, max = 15)
	@NotBlank
	@Pattern(regexp = "^[6-9]\\d{9}$")
	private String phone;
}
