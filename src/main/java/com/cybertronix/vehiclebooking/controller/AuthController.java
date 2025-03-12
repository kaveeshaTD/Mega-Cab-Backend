package com.cybertronix.vehiclebooking.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cybertronix.vehiclebooking.common.ApplicationRoute;
import com.cybertronix.vehiclebooking.common.CommonResponse;
import com.cybertronix.vehiclebooking.dto.request.LoginRequestDto;
import com.cybertronix.vehiclebooking.dto.response.LoginResponseDto;
import com.cybertronix.vehiclebooking.service.AuthenticationService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(ApplicationRoute.Authentication.Root)
public class AuthController {
    
    private final  AuthenticationService authenticationService;

     @PostMapping(ApplicationRoute.Authentication.Login)
    public ResponseEntity<CommonResponse> UserLogin(@RequestBody @Valid LoginRequestDto request){
        ResponseEntity<CommonResponse> response = null;

        LoginResponseDto userLoginResponseDto = authenticationService.UserLogin(request);

        response = new ResponseEntity<CommonResponse>(
                new CommonResponse(true, "Login Successful!", userLoginResponseDto),
                HttpStatus.OK
        );

        return  response;
    }
}
