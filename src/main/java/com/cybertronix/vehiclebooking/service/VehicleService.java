package com.cybertronix.vehiclebooking.service;

import java.util.List;

import com.cybertronix.vehiclebooking.dto.request.VehicleUpdateRequestDto;
import com.cybertronix.vehiclebooking.dto.response.VehicleResponseDto;

public interface VehicleService {
    VehicleResponseDto updateVehicle(Long id, VehicleUpdateRequestDto request);

    VehicleResponseDto getVehicleById(Long id);

    String deleteVehicle(Long id);

    List<VehicleResponseDto> getAllVehicles(Boolean isWithInactive);
}
