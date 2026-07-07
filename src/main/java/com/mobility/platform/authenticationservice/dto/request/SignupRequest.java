package com.mobility.platform.authenticationservice.dto.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SignupRequest {

    private String firstName;
	private String lastName;
	private String email;
	private String password;
	private String role;
	private String phone;
	private String createdBy;
}
