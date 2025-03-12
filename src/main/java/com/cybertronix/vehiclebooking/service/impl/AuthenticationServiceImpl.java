package com.cybertronix.vehiclebooking.service.impl;

import com.cybertronix.vehiclebooking.exception.*;
import com.cybertronix.vehiclebooking.model.User;

import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.cybertronix.vehiclebooking.common.enums.WellKnownUserStatus;
import com.cybertronix.vehiclebooking.dto.AuthenticationTicketDto;
import com.cybertronix.vehiclebooking.dto.request.LoginRequestDto;
import com.cybertronix.vehiclebooking.dto.response.LoginResponseDto;
import com.cybertronix.vehiclebooking.dto.response.UserResponseDto;
import com.cybertronix.vehiclebooking.repository.UserRepository;
import com.cybertronix.vehiclebooking.service.AuthenticationService;
import com.cybertronix.vehiclebooking.service.JwtService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl  implements AuthenticationService {
    
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final ModelMapper modelMapper;


    public LoginResponseDto UserLogin(LoginRequestDto request){
           try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUserName(), request.getPassword())
            );
        } catch (Exception e) {
            throw new BadRequestException("Invalid Email or Password!");
        }

        User user = userRepository.findByUserName(request.getUserName()).orElseThrow(() -> new BadRequestException("Invalid Email or Password!"));
        
        if(user.getStatus() == WellKnownUserStatus.PENDING.getValue()){
            throw new BadRequestException("Your account is not approved yet, please contact admin for more details!");
        }
        
        String token = jwtService.generateToken(user);

        UserResponseDto userResponseDto = modelMapper.map(user, UserResponseDto.class);

        LoginResponseDto response = new LoginResponseDto(
                token, userResponseDto
        );
        return response;
    }

     @Override
    public AuthenticationTicketDto AuthenticationTicket() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            UserDetails userDetails = (UserDetails) authentication.getPrincipal();

            // User user = userRepository.findByUserName(userDetails.getUsername()).get();

            AuthenticationTicketDto authTicket = modelMapper.map(userDetails, AuthenticationTicketDto.class);

            // authTicket.setUser(user);

            return authTicket;

        }catch (Exception e){
            throw new BadRequestException(e.getMessage());
        }
    }
}
