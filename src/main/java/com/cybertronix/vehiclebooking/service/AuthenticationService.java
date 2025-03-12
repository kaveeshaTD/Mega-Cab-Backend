package com.cybertronix.vehiclebooking.service;

import com.cybertronix.vehiclebooking.dto.AuthenticationTicketDto;
import com.cybertronix.vehiclebooking.dto.request.LoginRequestDto;
import com.cybertronix.vehiclebooking.dto.response.LoginResponseDto;

public interface AuthenticationService {
    LoginResponseDto UserLogin(LoginRequestDto userLoginRequestDto);

    AuthenticationTicketDto AuthenticationTicket();
}
