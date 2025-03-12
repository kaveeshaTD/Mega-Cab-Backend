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
public class UserUpdateRequestDto {
     @NotEmpty(message = "First Name is required")
	private String firstName;
 	
    @NotEmpty(message = "Last Name is required")
 	private String lastName;

    @NotEmpty(message = "Phone No is required")
 	private String phoneNumber;
 	
 	private String nicNumber;
	
 	private String dateOfBirth;
	
 	private String address;
	
 	private String nicImage;
	
 	private String profileImage;
}
