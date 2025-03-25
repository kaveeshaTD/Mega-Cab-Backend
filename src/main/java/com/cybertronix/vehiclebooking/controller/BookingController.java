package com.cybertronix.vehiclebooking.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cybertronix.vehiclebooking.common.ApplicationRoute;
import com.cybertronix.vehiclebooking.common.CommonResponse;
import com.cybertronix.vehiclebooking.dto.AuthenticationTicketDto;
import com.cybertronix.vehiclebooking.dto.request.BookingRequestDto;
import com.cybertronix.vehiclebooking.dto.request.BookingUpdateRequestDto;
import com.cybertronix.vehiclebooking.dto.request.CompleteBookingRequestDto;
import com.cybertronix.vehiclebooking.dto.request.UserUpdateRequestDto;
import com.cybertronix.vehiclebooking.dto.response.BookingReceiptResponseDto;
import com.cybertronix.vehiclebooking.dto.response.BookingResponseDto;
import com.cybertronix.vehiclebooking.service.AuthenticationService;
import com.cybertronix.vehiclebooking.service.BookingService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.val;

@RestController
@RequiredArgsConstructor
@RequestMapping(ApplicationRoute.Booking.Root)
public class BookingController {

    private final BookingService bookingService;

    private final AuthenticationService authenticationService;

    @PostMapping(ApplicationRoute.Booking.AddBooking)
    ResponseEntity<CommonResponse> AddBooking(@RequestBody @Valid BookingRequestDto request){
        ResponseEntity<CommonResponse> response = null;

        AuthenticationTicketDto authTicket = authenticationService.AuthenticationTicket();
        
        BookingResponseDto booking = bookingService.addBooking(request, authTicket.getUserId());

        response = new ResponseEntity<CommonResponse>(
                new CommonResponse(true, "Booking Added Successfully!", booking),
                HttpStatus.OK
        );
        return response;
    }

    @PatchMapping(ApplicationRoute.Booking.UpdateBooking)
    ResponseEntity<CommonResponse> UpdateBooking(@RequestBody @Valid BookingUpdateRequestDto request, @PathVariable Long id){
        ResponseEntity<CommonResponse> response = null;

        AuthenticationTicketDto authTicket = authenticationService.AuthenticationTicket();
        
        BookingResponseDto booking = bookingService.updateBooking(id, request, authTicket.getUserId());

        response = new ResponseEntity<CommonResponse>(
                new CommonResponse(true, "Booking Added Successfully!", booking),
                HttpStatus.OK
        );
        return response;
    }

    @GetMapping(ApplicationRoute.Booking.GetBookingById)
    ResponseEntity<CommonResponse> GetBookingById(@PathVariable Long id){
        ResponseEntity<CommonResponse> response = null;
        
        BookingResponseDto booking = bookingService.getBookingById(id);

        response = new ResponseEntity<CommonResponse>(
                new CommonResponse(true, "", booking),
                HttpStatus.OK
        );
        return response;
    }

    @DeleteMapping(ApplicationRoute.Booking.DeleteBooking)
    ResponseEntity<CommonResponse> DeleteBooking(@PathVariable Long id){
        ResponseEntity<CommonResponse> response = null;

        AuthenticationTicketDto authTicket = authenticationService.AuthenticationTicket();
        String message = bookingService.deleteBooking(id, authTicket.getUserId());

        response = new ResponseEntity<CommonResponse>(
                new CommonResponse(true, message, null),
                HttpStatus.OK
        );
        return response;
    }

    @PutMapping(ApplicationRoute.Booking.StartTrip)
    ResponseEntity<CommonResponse> StartTrip(@PathVariable Long id){
        ResponseEntity<CommonResponse> response = null;

        AuthenticationTicketDto authTicket = authenticationService.AuthenticationTicket();
        String message = bookingService.startTrip(id, authTicket.getUserId());

        response = new ResponseEntity<CommonResponse>(
                new CommonResponse(true, message, null),
                HttpStatus.OK
        );
        return response;
    }

    @PutMapping(ApplicationRoute.Booking.EndTrip)
    ResponseEntity<CommonResponse> EndTrip(@RequestBody @Valid CompleteBookingRequestDto request){
        ResponseEntity<CommonResponse> response = null;

        AuthenticationTicketDto authTicket = authenticationService.AuthenticationTicket();
        BookingResponseDto booking = bookingService.endTrip(request, authTicket.getUserId());

        response = new ResponseEntity<CommonResponse>(
                new CommonResponse(true, "Trip Completed Successfully!", booking),
                HttpStatus.OK
        );
        return response;
    }

    @GetMapping(ApplicationRoute.Booking.GetAllBookings)
    ResponseEntity<CommonResponse> GetAllBookings(){
        ResponseEntity<CommonResponse> response = null;

        AuthenticationTicketDto authTicket = authenticationService.AuthenticationTicket();
        List<BookingResponseDto> bookings = bookingService.getAllBookings(authTicket.getUserId());

        response = new ResponseEntity<CommonResponse>(
                new CommonResponse(true, "", bookings),
                HttpStatus.OK
        );
        return response;
    }
    
    @GetMapping(ApplicationRoute.Booking.GetReceipt)
    ResponseEntity<CommonResponse> GetReceipt(@PathVariable Long id){
        ResponseEntity<CommonResponse> response = null;

        BookingReceiptResponseDto receipt = bookingService.getReceipt(id);

        response = new ResponseEntity<CommonResponse>(
                new CommonResponse(true, "", receipt),
                HttpStatus.OK
        );
        return response;
    }
}
