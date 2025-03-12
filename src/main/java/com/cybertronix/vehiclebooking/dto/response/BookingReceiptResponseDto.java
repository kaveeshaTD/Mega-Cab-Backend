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
public class BookingReceiptResponseDto {
    private String receiptNo;

    private Date startDate;

    private Date endDate;

    private String description;

    private Double distance;

    private Double chargePerKm;

    private String vehicleType;

    private String registrationNo;

    private String driverName;

    private String bookingUserName;

    private Date bookingDate;

    private Double totalAmount;

    private Double taxAmount;

    private Double vatAmount;

    private Double serviceChargeAmount;

    private Double grossAmount;

    private Double discountAmount;

    private Double discountRate;

    private Integer status;

    private String statusName;
}