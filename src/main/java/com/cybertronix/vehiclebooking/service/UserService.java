package com.cybertronix.vehiclebooking.service;

import java.util.List;

import com.cybertronix.vehiclebooking.dto.request.UserSaveRequestDto;
import com.cybertronix.vehiclebooking.dto.request.UserUpdateRequestDto;
import com.cybertronix.vehiclebooking.dto.response.UserResponseDto;

public interface UserService {
	
	UserResponseDto register(UserSaveRequestDto userSaveRequestDto);

	UserResponseDto getUserProfile(Long userId);

	UserResponseDto updateUserProfile(UserUpdateRequestDto userUpdateRequestDto, Long userId);

	UserResponseDto deleteUserProfile(Long userId);

	List<UserResponseDto> getAllUsers(String roles, Boolean isWithInactive);

	UserResponseDto getUserById(Long userId);

	void approveDriver(Long userId, Long approvedBy);
}
