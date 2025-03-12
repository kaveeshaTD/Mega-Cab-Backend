package com.cybertronix.vehiclebooking.dto.request;

import java.util.Date;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BookingRequestDto {
    
    @NotNull(message = "Start Date is required")
    private Date startDate;
    
    @NotNull(message = "End Date is required")
    private Date endDate;

    @Size(max = 500, message = "Description should not be more than 500 characters")
    private String description;
    
    @NotNull(message = "Distance is required")
    private Double distance;
    
    @NotNull(message = "Vehicle is required")
    private Long vehicleId;
}
