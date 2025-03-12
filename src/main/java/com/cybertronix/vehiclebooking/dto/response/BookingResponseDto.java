package com.cybertronix.vehiclebooking.dto.response;

import java.util.Date;

import com.cybertronix.vehiclebooking.model.User;
import com.cybertronix.vehiclebooking.model.Vehicle;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BookingResponseDto {

 	private Long bookingId;
	
    private Date startDate;
    
    private Date endDate;
    
    private String description;
    
    private Double distance = 0.00;
    
    private Double taxAmount = 0.00;
    
    private Double vatAmount = 0.00;
    
    private Double serviceChargeAmount = 0.00;
    
    private Double totalAmount = 0.00;

    private Double chargePerKm;

    private Boolean isTaxIncluded = false;
    
    private Boolean isVatIncluded = false;
    
    private Boolean isServiceChargeIncluded = false;

    private Double serviceCharge = 0.00;

    private Double tax = 0.00;
    
    private Double vat = 0.00;

    private Double grossAmount = 0.00;

    private Double discountRate = 0.00;

    private Double discountAmount = 0.00;
    
    private Integer status;

    private Date createdAt;

    private Date updatedAt;

    private UserResponseDto user;

    private VehicleResponseDto vehicle;
}
