package com.cybertronix.vehiclebooking.service.impl;

import com.cybertronix.vehiclebooking.common.enums.WellKnownUserRole;
import com.cybertronix.vehiclebooking.common.enums.WellKnownUserStatus;
import com.cybertronix.vehiclebooking.exception.BadRequestException;
import com.cybertronix.vehiclebooking.repository.UserRepository;

import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.cybertronix.vehiclebooking.common.enums.WellKnownStatus;
import com.cybertronix.vehiclebooking.dto.request.UserSaveRequestDto;
import com.cybertronix.vehiclebooking.dto.request.UserUpdateRequestDto;
import com.cybertronix.vehiclebooking.dto.response.UserResponseDto;
import com.cybertronix.vehiclebooking.model.User;
import com.cybertronix.vehiclebooking.model.Vehicle;
import com.cybertronix.vehiclebooking.repository.VehicleRepository;
import com.cybertronix.vehiclebooking.service.UserService;

import jakarta.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
	private final UserRepository userRepository;

	private final ModelMapper modelMapper;

	private final PasswordEncoder passwordEncoder;

	private final VehicleRepository vehicleRepository;

	@Override
	@Transactional
	public UserResponseDto register(UserSaveRequestDto userSaveRequestDto) {

		// Check user name all ready exist
		Boolean userNameExist = userRepository.existsByUserName(userSaveRequestDto.getUserName());

		if (userNameExist)
			throw new BadRequestException("Username Already Exist!");

		User user = modelMapper.map(userSaveRequestDto, User.class);
		String passwordHash = passwordEncoder.encode(userSaveRequestDto.getPassword());

        user.setPassword(passwordHash);

		if(WellKnownUserRole.USER.getValue()== userSaveRequestDto.getRole()){
			user.setStatus(WellKnownUserStatus.ACTIVE.getValue());

			userRepository.save(user);
		}else if(WellKnownUserRole.DRIVER.getValue()== userSaveRequestDto.getRole()){
			user.setStatus(WellKnownUserStatus.PENDING.getValue());
			
			userRepository.save(user);

			if(userSaveRequestDto.getVehicleRequest() == null){
				throw new BadRequestException("Vehicle is required when signing up as driver!");
			}

			// save vehicle
			Vehicle vehicle = modelMapper.map(userSaveRequestDto.getVehicleRequest(), Vehicle.class);

			vehicle.setStatus(WellKnownStatus.ACTIVE.getValue());

			vehicle.setUser(user);

			vehicleRepository.save(vehicle);
		}else if(WellKnownUserRole.ADMIN.getValue()== userSaveRequestDto.getRole()){
			user.setStatus(WellKnownUserStatus.ACTIVE.getValue());

			userRepository.save(user);
		}

		UserResponseDto userResponseDto = modelMapper.map(user, UserResponseDto.class);

		return userResponseDto;
	}

	public UserResponseDto getUserProfile(Long userId){
		UserResponseDto userResponseDto = null;

		User user = userRepository.findByUserIdAndStatusIn(userId, List.of(WellKnownUserStatus.ACTIVE.getValue()));

		if(user != null){
			userResponseDto = modelMapper.map(user, UserResponseDto.class);
		}

		return userResponseDto;
	}

	public UserResponseDto updateUserProfile(UserUpdateRequestDto request, Long userId){
		UserResponseDto userResponseDto = null;

		User user = userRepository.findByUserIdAndStatusIn(userId, List.of(WellKnownUserStatus.ACTIVE.getValue()));

		if(user != null){
			user.setFirstName(request.getFirstName());
			user.setLastName(request.getLastName());
			user.setPhoneNumber(request.getPhoneNumber());
			user.setNicNumber(request.getNicNumber());
			user.setDateOfBirth(request.getDateOfBirth());
			user.setAddress(request.getAddress());
			user.setNicImage(request.getNicImage());
			user.setProfileImage(request.getProfileImage());

			userRepository.save(user);

			userResponseDto = modelMapper.map(user, UserResponseDto.class);
		}
		
		return userResponseDto;
	}

	@Transactional
	public UserResponseDto deleteUserProfile(Long userId){
		UserResponseDto userResponseDto = null;

		User user = userRepository.findByUserIdAndStatusIn(userId, List.of(WellKnownUserStatus.ACTIVE.getValue()));

		if(user != null){
			user.setStatus(WellKnownUserStatus.DELETED.getValue());

			userRepository.save(user);

			if(user.getRole() == WellKnownUserRole.DRIVER.getValue()){
				Vehicle vehicle = vehicleRepository.findByUserUserIdAndStatusIn(userId, List.of(WellKnownStatus.ACTIVE.getValue()));

				if(vehicle != null){
					vehicle.setStatus(WellKnownStatus.DELETED.getValue());

					vehicleRepository.save(vehicle);
				}
			}

			userResponseDto = modelMapper.map(user, UserResponseDto.class);
		} else{
			throw new BadRequestException("User profile not found or already deleted!");
		}

		return userResponseDto;
	}

	public List<UserResponseDto> getAllUsers(String roles, Boolean isWithInactive){

		List<UserResponseDto> users = new ArrayList<UserResponseDto>();

		List<Integer> roleList = Arrays.stream(roles.split(",")).map(Integer::parseInt).collect(Collectors.toList());

		List<Integer> status = new ArrayList<Integer>();


		if(isWithInactive){
			status.add(WellKnownUserStatus.ACTIVE.getValue());
			status.add(WellKnownUserStatus.PENDING.getValue());
		}else{
			status.add(WellKnownUserStatus.ACTIVE.getValue());
		}

		List<User> userList = userRepository.findByRoleInAndStatusIn(roleList, status);

		for(User user : userList){
			UserResponseDto userResponseDto = modelMapper.map(user, UserResponseDto.class);
			users.add(userResponseDto);
		}

		return users;

	}

	public UserResponseDto getUserById(Long userId) {

		UserResponseDto userResponseDto = null;

		User user = userRepository.findByUserIdAndStatusIn(userId, List.of(WellKnownUserStatus.ACTIVE.getValue(), WellKnownUserStatus.PENDING.getValue()));

		if(user != null){
			userResponseDto = modelMapper.map(user, UserResponseDto.class);
		}

		return userResponseDto;
	}

	public void approveDriver(Long userId, Long approvedBy){

		User user = userRepository.findByUserIdAndStatusIn(userId, List.of(WellKnownUserStatus.PENDING.getValue(), WellKnownUserStatus.ACTIVE.getValue()));

		if(user != null){

			if(user.getRole() == WellKnownUserRole.DRIVER.getValue()){

				if(user.getStatus() == WellKnownUserStatus.ACTIVE.getValue()){
					throw new BadRequestException("This driver is already approved!");
				}

				user.setStatus(WellKnownUserStatus.ACTIVE.getValue());
				user.setApprovedBy(approvedBy);

				userRepository.save(user);
			}else{
				throw new BadRequestException("This user is not a driver, cannot be approved!");
			}
		}
	}

}
