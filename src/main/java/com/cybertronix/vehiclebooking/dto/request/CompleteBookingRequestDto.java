package com.cybertronix.vehiclebooking.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CompleteBookingRequestDto {
    @NotNull(message = "Booking is required")
    private Long bookingId;

    @NotNull(message = "Distance is required")
    private Double distance;

    private Double discountRate;
}
