package com.cybertronix.vehiclebooking.service.impl;

import java.util.List;
import java.util.ArrayList;
import java.util.Date;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.cybertronix.vehiclebooking.common.enums.WellKnownBookingStatus;
import com.cybertronix.vehiclebooking.common.enums.WellKnownStatus;
import com.cybertronix.vehiclebooking.common.enums.WellKnownUserRole;
import com.cybertronix.vehiclebooking.common.enums.WellKnownUserStatus;
import com.cybertronix.vehiclebooking.dto.request.BookingRequestDto;
import com.cybertronix.vehiclebooking.dto.request.BookingUpdateRequestDto;
import com.cybertronix.vehiclebooking.dto.request.CompleteBookingRequestDto;
import com.cybertronix.vehiclebooking.dto.response.BookingReceiptResponseDto;
import com.cybertronix.vehiclebooking.dto.response.BookingResponseDto;
import com.cybertronix.vehiclebooking.exception.BadRequestException;
import com.cybertronix.vehiclebooking.model.Booking;
import com.cybertronix.vehiclebooking.model.User;
import com.cybertronix.vehiclebooking.model.Vehicle;
import com.cybertronix.vehiclebooking.repository.BookingRepository;
import com.cybertronix.vehiclebooking.repository.UserRepository;
import com.cybertronix.vehiclebooking.repository.VehicleRepository;
import com.cybertronix.vehiclebooking.service.BookingService;

import lombok.AllArgsConstructor;
import lombok.Data;

@Service
@AllArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final UserRepository userRepository;

    private final VehicleRepository vehicleRepository;

    private final BookingRepository bookingRepository;

    private final ModelMapper modelMapper;

    public BookingResponseDto addBooking(BookingRequestDto request, Long userId){
        
        // Check User exists
        User user = userRepository.findByUserIdAndStatusIn(userId, List.of(WellKnownUserStatus.ACTIVE.getValue()));

        if(user == null){
            throw new BadRequestException("User not found!");
        }else if(user.getRole() != WellKnownUserRole.USER.getValue()){
            throw new BadRequestException("Only user can book vehicle!");
        }

        // Check Vehicle exists
        Vehicle vehicle = vehicleRepository.findByVehicleIdAndStatusIn(request.getVehicleId(), List.of(WellKnownStatus.ACTIVE.getValue()));

        if(vehicle == null){
            throw new BadRequestException("Vehicle not found!");
        }

        // check vehicle is available from start date time to end date time and vehicle
        List<Booking> conflictBookings = bookingRepository.findConflictingBookings(request.getVehicleId(), request.getStartDate(), request.getEndDate(), List.of(WellKnownBookingStatus.PENDING.getValue(), WellKnownBookingStatus.STARTED.getValue()));

        if(conflictBookings.size() > 0){
            throw new BadRequestException("Vehicle is not available for given date and time!");
        }

        // map booking object
        Booking booking = new Booking();
        booking.setVehicle(vehicle);
        booking.setUser(user);
        booking.setStartDate(request.getStartDate());
        booking.setEndDate(request.getEndDate());
        booking.setDescription(request.getDescription());
        booking.setDistance(request.getDistance());
        booking.setStatus(WellKnownBookingStatus.PENDING.getValue());
        booking.setChargePerKm(vehicle.getChargePerKm());
        booking.setIsServiceChargeIncluded(vehicle.getIsServiceChargeIncluded());
        booking.setServiceCharge(vehicle.getServiceCharge());
        booking.setIsVatIncluded(vehicle.getIsVatIncluded());
        booking.setVat(vehicle.getVat());
        booking.setIsTaxIncluded(vehicle.getIsTaxIncluded());
        booking.setTax(vehicle.getTax());
        
        // calculate total amount using distance and  tax, vat, service charge
        CalculateOutputModel calculateOutputModel = calculateTotalAmount(booking);

        if(calculateOutputModel.getDiscountAmount() > calculateOutputModel.getGrossAmount()){
            throw new BadRequestException("Discount amount is greater than gross amount!");
        }

        booking.setGrossAmount(calculateOutputModel.getGrossAmount());
        booking.setTotalAmount(calculateOutputModel.getTotalAmount());
        booking.setTaxAmount(calculateOutputModel.getTaxAmount());
        booking.setVatAmount(calculateOutputModel.getVatAmount());
        booking.setServiceChargeAmount(calculateOutputModel.getServiceChargeAmount());
        booking.setDiscountAmount(calculateOutputModel.getDiscountAmount());

        // save booking
        booking = bookingRepository.save(booking);

        // map response object
        BookingResponseDto response = modelMapper.map(booking, BookingResponseDto.class);

        return response;
    }

    public BookingResponseDto updateBooking(Long id,BookingUpdateRequestDto request, Long userId){
        // Check booking exists
        Booking booking = bookingRepository.getReferenceById(id);

        if(booking == null){
            throw new BadRequestException("Booking not found!");
        }

        if(booking.getStatus() != WellKnownBookingStatus.PENDING.getValue()){
            throw new BadRequestException("Only pending booking can be updated!");
        }

        if(booking.getUser().getUserId() != userId){
            throw new BadRequestException("Only added user can update booking!");
        }

        // check vehicle is available from start date time to end date time and vehicle
        List<Booking> conflictBookings = bookingRepository.findConflictingBookings(booking.getVehicle().getVehicleId(), request.getStartDate(), request.getEndDate(), List.of(WellKnownBookingStatus.PENDING.getValue(), WellKnownBookingStatus.STARTED.getValue()));

        if(!conflictBookings.contains(booking)){
            if(conflictBookings.size() > 0){
                throw new BadRequestException("Vehicle is not available for given date and time!");
            }
        }

        // map booking object
        booking.setStartDate(request.getStartDate());
        booking.setEndDate(request.getEndDate());
        booking.setDescription(request.getDescription());
        booking.setDistance(request.getDistance());

        // calculate total amount using distance and  tax, vat, service charge
        CalculateOutputModel calculateOutputModel = calculateTotalAmount(booking);

        if(calculateOutputModel.getDiscountAmount() > calculateOutputModel.getGrossAmount()){
            throw new BadRequestException("Discount amount is greater than gross amount!");
        }

        booking.setGrossAmount(calculateOutputModel.getGrossAmount());
        booking.setTotalAmount(calculateOutputModel.getTotalAmount());
        booking.setTaxAmount(calculateOutputModel.getTaxAmount());
        booking.setVatAmount(calculateOutputModel.getVatAmount());
        booking.setServiceChargeAmount(calculateOutputModel.getServiceChargeAmount());
        booking.setDiscountAmount(calculateOutputModel.getDiscountAmount());

        // save booking
        booking = bookingRepository.save(booking);
        

        // map response object
        BookingResponseDto response = modelMapper.map(booking, BookingResponseDto.class);

        return response;
    }

    @Override
    public  BookingResponseDto getBookingById(Long id){
        BookingResponseDto response = null;

        Booking booking = bookingRepository.getReferenceById(id);

        if(booking != null){
            response = modelMapper.map(booking, BookingResponseDto.class);
        }
        
        return response;
    }

    @Override
    public String deleteBooking(Long bookingId, Long userId){
        String response = "";

        // Check booking exists
        Booking booking = bookingRepository.findByBookingIdAndStatusIn(bookingId, List.of(WellKnownBookingStatus.PENDING.getValue(), WellKnownBookingStatus.STARTED.getValue(), WellKnownBookingStatus.COMPLETED.getValue()));

        if(booking == null){
            response = "Booking not found!";
        }else if(booking.getStatus() == WellKnownBookingStatus.STARTED.getValue()){
            response = "Booking is already started, so cannot be cancelled!";
        }else if(booking.getStatus() == WellKnownBookingStatus.COMPLETED.getValue()){
            response = "Booking is already completed, so cannot be cancelled!";
        }else if(booking.getUser().getUserId() != userId || (booking.getUser().getRole() == WellKnownUserRole.DRIVER.getValue() && booking.getVehicle().getUser().getUserId() != userId)){
            response = "Only added user or vehicle driver can cancel booking!";
        }

        if(!response.isEmpty()){
            throw new BadRequestException(response);
        }

        booking.setStatus(WellKnownBookingStatus.CANCELLED.getValue());
        
        bookingRepository.save(booking);

        response = "Booking cancelled successfully!";

        return response;
    }

    @Override
    public String startTrip(Long bookingId, Long userId){
        String response = "";

        // Check booking exists
        Booking booking = bookingRepository.findByBookingIdAndStatusIn(bookingId, List.of(WellKnownBookingStatus.PENDING.getValue(), WellKnownBookingStatus.STARTED.getValue(), WellKnownBookingStatus.COMPLETED.getValue()));

        if(booking == null){
            response = "Booking not found!";
        }else if(booking.getStatus() == WellKnownBookingStatus.STARTED.getValue()){
            response = "Booking is already started!";
        }else if(booking.getStatus() == WellKnownBookingStatus.COMPLETED.getValue()){
            response = "Booking is already completed, so cannot be started again!";
        }else if(booking.getVehicle().getUser().getUserId() != userId){
            response = "Only vehicle driver can start booking!";
        }

        if(!response.isEmpty()){
            throw new BadRequestException(response);
        }

        booking.setStatus(WellKnownBookingStatus.STARTED.getValue());
        
        bookingRepository.save(booking);

        response = "Booking started successfully!";

        return response;
    }


    public BookingResponseDto endTrip(CompleteBookingRequestDto request, Long userId){
        BookingResponseDto response = null;
        // Check booking exists
        Booking booking = bookingRepository.findByBookingIdAndStatusIn(request.getBookingId(), List.of(WellKnownBookingStatus.STARTED.getValue()));

        if(booking == null){
            throw new BadRequestException("Started booking not found!");
        }else if(booking.getVehicle().getUser().getUserId() != userId){
            throw new BadRequestException("Only vehicle driver can end booking!");
        }

        booking.setStatus(WellKnownBookingStatus.COMPLETED.getValue());
        booking.setDistance(request.getDistance());
        booking.setDiscountRate(request.getDiscountRate());
        
        // calculate total amount using distance and  tax, vat, service charge
        CalculateOutputModel calculateOutputModel = calculateTotalAmount(booking);

        if(calculateOutputModel.getDiscountAmount() > calculateOutputModel.getGrossAmount()){
            throw new BadRequestException("Discount amount is greater than gross amount!");
        }

        booking.setGrossAmount(calculateOutputModel.getGrossAmount());
        booking.setTotalAmount(calculateOutputModel.getTotalAmount());
        booking.setTaxAmount(calculateOutputModel.getTaxAmount());
        booking.setVatAmount(calculateOutputModel.getVatAmount());
        booking.setServiceChargeAmount(calculateOutputModel.getServiceChargeAmount());
        booking.setDiscountAmount(calculateOutputModel.getDiscountAmount());

        // save booking
        booking = bookingRepository.save(booking);
        
        // map response object
        response = modelMapper.map(booking, BookingResponseDto.class);

        return response;
    }

    @Override
    public List<BookingResponseDto> getAllBookings(Long userId){
        List<BookingResponseDto> response = new ArrayList<>();

        User user = userRepository.findByUserIdAndStatusIn(userId, List.of(WellKnownUserStatus.ACTIVE.getValue()));

        if(user == null){
            return response;
        }

        List<Booking> bookings = new ArrayList<>();
        if(user.getRole() == WellKnownUserRole.USER.getValue()){
            bookings = bookingRepository.findBookingsByUserAndStatus(userId, List.of(WellKnownBookingStatus.PENDING.getValue(), WellKnownBookingStatus.STARTED.getValue(), WellKnownBookingStatus.COMPLETED.getValue()));
        }else if(user.getRole() == WellKnownUserRole.DRIVER.getValue())
        {
            bookings = bookingRepository.findBookingsByDriverAndStatus(userId, List.of(WellKnownBookingStatus.PENDING.getValue(), WellKnownBookingStatus.STARTED.getValue(), WellKnownBookingStatus.COMPLETED.getValue()));
            
        }else if(user.getRole() == WellKnownUserRole.ADMIN.getValue())
        {
            bookings = bookingRepository.findBookingsByStatus(List.of(WellKnownBookingStatus.PENDING.getValue(), WellKnownBookingStatus.STARTED.getValue(), WellKnownBookingStatus.COMPLETED.getValue()));
        }

        if(bookings.size() > 0){
            for (Booking booking : bookings) {
                response.add(modelMapper.map(booking, BookingResponseDto.class));
            }
        }

        return response;
    }

    public BookingReceiptResponseDto getReceipt(Long bookingId){
        BookingReceiptResponseDto response = new BookingReceiptResponseDto();

        // Check booking exists
        Booking booking = bookingRepository.findByBookingIdAndStatusIn(bookingId, List.of(WellKnownBookingStatus.COMPLETED.getValue()));

        if(booking == null){
            throw new BadRequestException("Completed booking not found!");
        }

        // map response object
        String receiptNo = String.format("REC-%04d", booking.getBookingId());
        response.setReceiptNo(receiptNo);
        response.setStartDate(booking.getStartDate());
        response.setEndDate(booking.getEndDate());
        response.setDescription(booking.getDescription());
        response.setDistance(booking.getDistance());
        response.setChargePerKm(booking.getChargePerKm());
        response.setVehicleType(booking.getVehicle().getVehicleType());
        response.setRegistrationNo(booking.getVehicle().getRegistrationNo());
        response.setDriverName(booking.getVehicle().getUser().getFirstName() + " " + booking.getVehicle().getUser().getLastName());
        response.setBookingUserName(booking.getUser().getFirstName() + " " + booking.getUser().getLastName());
        response.setBookingDate(booking.getCreatedAt());
        response.setTotalAmount(booking.getTotalAmount());
        response.setTaxAmount(booking.getTaxAmount());
        response.setVatAmount(booking.getVatAmount());
        response.setServiceChargeAmount(booking.getServiceChargeAmount());
        response.setGrossAmount(booking.getGrossAmount());
        response.setDiscountAmount(booking.getDiscountAmount());
        response.setDiscountRate(booking.getDiscountRate());
        response.setStatus(booking.getStatus());
        WellKnownBookingStatus status = WellKnownBookingStatus.values()[booking.getStatus() - 1];
        response.setStatusName(status.getStatus());

        return response;
    }

    @Data
    private class CalculateOutputModel {
        private Double grossAmount;
        private Double totalAmount;
        private Double taxAmount;
        private Double vatAmount;
        private Double serviceChargeAmount;
        private Double discountAmount;
    }

    private CalculateOutputModel calculateTotalAmount(Booking booking){
        CalculateOutputModel calculateOutputModel = new CalculateOutputModel();

        Double grossAmount = 0.00;
        Double totalAmount = 0.00;
        Double taxAmount = 0.00;
        Double vatAmount = 0.00;
        Double serviceChargeAmount = 0.00;
        Double discountAmount = 0.00;

        grossAmount = booking.getDistance() * booking.getChargePerKm();

        if(booking.getIsTaxIncluded()){
            taxAmount = grossAmount * (booking.getTax() / 100);
        }

        if(booking.getIsVatIncluded()){
            vatAmount = grossAmount * (booking.getVat() / 100);
        }

        if(booking.getIsServiceChargeIncluded()){
            serviceChargeAmount = grossAmount * (booking.getServiceCharge() / 100);
        }

        if(booking.getDiscountRate() > 0){
            discountAmount = grossAmount * (booking.getDiscountRate() / 100);
        }

        totalAmount = grossAmount + taxAmount + vatAmount + serviceChargeAmount - discountAmount;

        calculateOutputModel.setGrossAmount(grossAmount);
        calculateOutputModel.setTotalAmount(totalAmount);
        calculateOutputModel.setTaxAmount(taxAmount);
        calculateOutputModel.setVatAmount(vatAmount);
        calculateOutputModel.setServiceChargeAmount(serviceChargeAmount);
        calculateOutputModel.setDiscountAmount(discountAmount);

        
        return calculateOutputModel;
    }
}
