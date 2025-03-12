package com.cybertronix.vehiclebooking.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class LoginRequestDto {
     @NotEmpty(message = "Username is required")
	private String userName;
 	
    @NotEmpty(message = "Password is required")
 	private String password;
}
