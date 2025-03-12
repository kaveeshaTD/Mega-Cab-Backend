package com.cybertronix.vehiclebooking.controller;
import com.cybertronix.vehiclebooking.common.ApplicationRoute;
import com.cybertronix.vehiclebooking.common.CommonResponse;
import com.cybertronix.vehiclebooking.dto.AuthenticationTicketDto;
import com.cybertronix.vehiclebooking.dto.request.UserSaveRequestDto;
import com.cybertronix.vehiclebooking.dto.request.UserUpdateRequestDto;
import com.cybertronix.vehiclebooking.dto.response.UserResponseDto;
import com.cybertronix.vehiclebooking.service.AuthenticationService;
import com.cybertronix.vehiclebooking.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(ApplicationRoute.User.Root)
public class UserController {
	
	private final UserService userService;

    private final AuthenticationService authenticationService;
	
    @PostMapping(ApplicationRoute.User.Save)
    public ResponseEntity<CommonResponse> Register(@RequestBody @Valid UserSaveRequestDto userSaveRequestDto) {
        ResponseEntity<CommonResponse> response = null;

        UserResponseDto user = userService.register(userSaveRequestDto);

        response = new ResponseEntity<CommonResponse>(
                new CommonResponse(true, "User registered successfully!", user),
                HttpStatus.CREATED
        );

        return response;
    }

    @GetMapping(ApplicationRoute.User.GetProfile)
    ResponseEntity<CommonResponse> GetUserProfile (){
        ResponseEntity<CommonResponse> response = null;

        AuthenticationTicketDto authTicket = authenticationService.AuthenticationTicket();

        UserResponseDto user = userService.getUserProfile(authTicket.getUserId());

        response = new ResponseEntity<CommonResponse>(
                new CommonResponse(true, "", user),
                HttpStatus.OK
        );
        return response;
    }

    @PatchMapping(ApplicationRoute.User.UpdateProfile)
    ResponseEntity<CommonResponse> UpdateUserProfile (@RequestBody @Valid UserUpdateRequestDto request){
        ResponseEntity<CommonResponse> response = null;

        AuthenticationTicketDto authTicket = authenticationService.AuthenticationTicket();

        UserResponseDto user = userService.updateUserProfile(request,authTicket.getUserId());

        response = new ResponseEntity<CommonResponse>(
                new CommonResponse(true, "Profile Updated Successfully!", user),
                HttpStatus.OK
        );
        return response;
    }

    @DeleteMapping(ApplicationRoute.User.DeleteProfile)
    ResponseEntity<CommonResponse> DeleteUserProfile (){
        ResponseEntity<CommonResponse> response = null;

        AuthenticationTicketDto authTicket = authenticationService.AuthenticationTicket();

        UserResponseDto user = userService.deleteUserProfile(authTicket.getUserId());

        response = new ResponseEntity<CommonResponse>(
                new CommonResponse(true, "Profile Deleted Successfully!", user),
                HttpStatus.OK
        );
        return response;
    }

    @GetMapping(ApplicationRoute.User.GetAll)
    ResponseEntity<CommonResponse> GetAllUsers(@RequestParam(defaultValue = "1") String roles, @RequestParam(defaultValue = "false") Boolean isWithInactive){
        ResponseEntity<CommonResponse> response = null;

        List<UserResponseDto> users = userService.getAllUsers(
                roles,
                isWithInactive
        );

        response = new ResponseEntity<CommonResponse>(
                new CommonResponse(true, "", users),
                HttpStatus.OK
        );
        return response;
    }

    @GetMapping(ApplicationRoute.User.GetById)
    ResponseEntity<CommonResponse> GetUserById(@PathVariable Long id){
        ResponseEntity<CommonResponse> response = null;

        UserResponseDto user = userService.getUserById(id);

        response = new ResponseEntity<CommonResponse>(
                new CommonResponse(true, "", user),
                HttpStatus.OK
        );
        return response;
    }

    @PutMapping(ApplicationRoute.User.ApproveDriver)
    ResponseEntity<CommonResponse> ApproveDriver(@PathVariable Long id){
        ResponseEntity<CommonResponse> response = null;

        AuthenticationTicketDto authTicket = authenticationService.AuthenticationTicket();
        userService.approveDriver(id , authTicket.getUserId());

        response = new ResponseEntity<CommonResponse>(
                new CommonResponse(true, "Driver Approved Successfully!", null),
                HttpStatus.OK
        );
        return response;
    }

    @PatchMapping(ApplicationRoute.User.UpdateUserByAdmin)
    ResponseEntity<CommonResponse> UpdateUserByAdmin(@RequestBody @Valid UserUpdateRequestDto request, @PathVariable Long id){
        ResponseEntity<CommonResponse> response = null;

        UserResponseDto user = userService.updateUserProfile(request, id);

        response = new ResponseEntity<CommonResponse>(
                new CommonResponse(true, "User Updated Successfully!", user),
                HttpStatus.OK
        );
        return response;
    }

    @DeleteMapping(ApplicationRoute.User.DeleteUserByAdmin)
    ResponseEntity<CommonResponse> DeleteUserByAdmin(@PathVariable Long id){
        ResponseEntity<CommonResponse> response = null;

        UserResponseDto user = userService.deleteUserProfile(id);

        response = new ResponseEntity<CommonResponse>(
                new CommonResponse(true, "User Deleted Successfully!", user),
                HttpStatus.OK
        );
        return response;
    }
}
