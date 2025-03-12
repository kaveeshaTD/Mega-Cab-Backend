package com.cybertronix.vehiclebooking.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class VehicleUpdateRequestDto {
   @NotEmpty(message = "Vehicle type is required")
 	private String vehicleType;

   @NotEmpty(message = "Registration no is required")
 	private String registrationNo;

 	private Integer passengerCount;
 	
 	private Double chargePerKm;
    
   private String vehicleImage1;
    
   private String vehicleImage2;
    
   @Size(max = 500, message = "Description should not be more than 500 characters")
   private String vehiceldescription;
    
   private Boolean isTaxIncluded = false;
    
   private Boolean isVatIncluded = false;
    
   private Boolean isServiceChargeIncluded = false;
    
   private Double tax = 0.00;
    
   private Double vat = 0.00;
    
   private Double serviceCharge = 0.00;
}
