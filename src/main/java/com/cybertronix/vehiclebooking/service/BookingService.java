package com.cybertronix.vehiclebooking.service;

import java.util.List;

import com.cybertronix.vehiclebooking.dto.request.BookingRequestDto;
import com.cybertronix.vehiclebooking.dto.request.BookingUpdateRequestDto;
import com.cybertronix.vehiclebooking.dto.request.CompleteBookingRequestDto;
import com.cybertronix.vehiclebooking.dto.response.BookingReceiptResponseDto;
import com.cybertronix.vehiclebooking.dto.response.BookingResponseDto;

public interface BookingService {

    BookingResponseDto addBooking(BookingRequestDto request, Long userId);

    BookingResponseDto updateBooking(Long id,BookingUpdateRequestDto request, Long userId);

    BookingResponseDto getBookingById(Long id);

    String deleteBooking(Long bookingId, Long userId);

    String startTrip(Long bookingId, Long userId);

    BookingResponseDto endTrip(CompleteBookingRequestDto request, Long userId);

    List<BookingResponseDto> getAllBookings(Long userId);

    BookingReceiptResponseDto getReceipt(Long bookingId);
}
