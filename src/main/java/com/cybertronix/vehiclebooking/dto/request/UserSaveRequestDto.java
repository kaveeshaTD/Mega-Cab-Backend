package com.cybertronix.vehiclebooking.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserSaveRequestDto {
	
    @NotEmpty(message = "First Name is required")
	private String firstName;
 	
    @NotEmpty(message = "Last Name is required")
 	private String lastName;
    
    @NotEmpty(message = "Username is required")
 	private String userName;

    @NotEmpty(message = "Phone No is required")
 	private String phoneNumber;
 	
 	private String nicNumber;
	
 	private String dateOfBirth;
	
 	private String address;
	
 	private String nicImage;
	
 	private String profileImage;
   	
 	@NotNull(message = "Role is required")
    private Integer role;

	@NotEmpty(message = "Password is required")
	private String password;
   	
   	private VehicleSaveRequestDto vehicleRequest; 
}
