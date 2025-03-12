package com.cybertronix.vehiclebooking.dto.response;

import java.util.Date;

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
public class UserResponseDto {
		private Long userId;
		
		private String firstName;
	 	
	 	private String lastName;
	    
	 	private String username;

	 	private String phoneNumber;
	 	
	 	private String nicNumber;
		
	 	private String dateOfBirth;
		
	 	private String address;
		
	 	private String nicImage;
		
	 	private String profileImage;
	 	
	 	private Integer status;
	   	
	    private Integer role;
	    
	    private Date createdAt;
	    
	    private Date updatedAt;
}
