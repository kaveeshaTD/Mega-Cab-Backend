package com.cybertronix.vehiclebooking.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AuthenticationTicketDto {
    private Long userId;
    private String username;
    private Integer role;
    // private User user;
}

