package com.cybertronix.vehiclebooking.dto.response;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class VehicleResponseDto {
	private Long vehicleId;
	
 	private String vehicleType;

 	private String registrationNo;

 	private Integer passengerCount;
 	
 	private Double chargePerKm;
    
   	private String vehicleImage1;
    
    private String vehicleImage2;
    
    private String vehiceldescription;
    
    private Boolean isTaxIncluded = false;
    
    private Boolean isVatIncluded = false;
    
    private Boolean isServiceChargeIncluded = false;
    
    private Double tax = 0.00;
    
    private Double vat = 0.00;
    
    private Double serviceCharge = 0.00;
    
    private Integer status;
    
    private Date createdAt;
    
    private Date updatedAt;

    private UserResponseDto driver;
}
