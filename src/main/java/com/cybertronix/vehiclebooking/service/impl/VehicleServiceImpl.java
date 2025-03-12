package com.cybertronix.vehiclebooking.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.cybertronix.vehiclebooking.common.enums.WellKnownStatus;
import com.cybertronix.vehiclebooking.common.enums.WellKnownUserStatus;
import com.cybertronix.vehiclebooking.dto.request.VehicleUpdateRequestDto;
import com.cybertronix.vehiclebooking.dto.response.UserResponseDto;
import com.cybertronix.vehiclebooking.dto.response.VehicleResponseDto;
import com.cybertronix.vehiclebooking.exception.BadRequestException;
import com.cybertronix.vehiclebooking.model.User;
import com.cybertronix.vehiclebooking.model.Vehicle;
import com.cybertronix.vehiclebooking.repository.UserRepository;
import com.cybertronix.vehiclebooking.repository.VehicleRepository;
import com.cybertronix.vehiclebooking.service.VehicleService;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class VehicleServiceImpl implements VehicleService{

    private final VehicleRepository vehicleRepository;

    private final ModelMapper modelMapper;

    private final UserRepository userRepository;

    @Override
    public VehicleResponseDto updateVehicle(Long id, VehicleUpdateRequestDto request){
        VehicleResponseDto vehicleResponseDto = null;

        // Get Active vehicle
        Vehicle vehicle = vehicleRepository.findByVehicleIdAndStatusIn(id, List.of(WellKnownStatus.ACTIVE.getValue()));

        if(vehicle == null){
           throw new BadRequestException("Vehicle not found or vehicle already deleted!");
        }

        vehicle.setVehicleType(request.getVehicleType());
        vehicle.setRegistrationNo(request.getRegistrationNo());
        vehicle.setPassengerCount(request.getPassengerCount());
        vehicle.setChargePerKm(request.getChargePerKm());
        vehicle.setVehicleImage1(request.getVehicleImage1());
        vehicle.setVehicleImage2(request.getVehicleImage2());
        vehicle.setVehiceldescription(request.getVehiceldescription());
        vehicle.setIsTaxIncluded(request.getIsTaxIncluded());
        vehicle.setIsVatIncluded(request.getIsVatIncluded());
        vehicle.setIsServiceChargeIncluded(request.getIsServiceChargeIncluded());
        vehicle.setTax(request.getTax());
        vehicle.setVat(request.getVat());
        vehicle.setServiceCharge(request.getServiceCharge());

        vehicleRepository.save(vehicle);
        
        vehicleResponseDto = modelMapper.map(vehicle, VehicleResponseDto.class);

        return vehicleResponseDto;
    }

    @Override
    public VehicleResponseDto getVehicleById(Long id){
        VehicleResponseDto vehicleResponseDto = null;

        // Get Active vehicle
        Vehicle vehicle = vehicleRepository.findByVehicleIdAndStatusIn(id, List.of(WellKnownStatus.ACTIVE.getValue()));

        if(vehicle != null){
            vehicleResponseDto = modelMapper.map(vehicle, VehicleResponseDto.class);

            UserResponseDto userResponseDto = modelMapper.map(vehicle.getUser(), UserResponseDto.class);

            vehicleResponseDto.setDriver(userResponseDto);
        }

        return vehicleResponseDto;
    }

    @Override
    @Transactional
    public String deleteVehicle(Long id){
        String message = "";
        Vehicle vehicle = vehicleRepository.findByVehicleIdAndStatusIn(id, List.of(WellKnownStatus.ACTIVE.getValue()));

        if(vehicle != null){
            vehicle.setStatus(WellKnownStatus.DELETED.getValue());
            
            vehicleRepository.save(vehicle);

            User user = vehicle.getUser();

            if(user.getStatus() == WellKnownUserStatus.ACTIVE.getValue() || user.getStatus() == WellKnownUserStatus.PENDING.getValue()){
                user.setStatus(WellKnownUserStatus.DELETED.getValue());
                
                userRepository.save(user);
            }
        
        }else{
            throw new BadRequestException("Vehicle not found or vehicle already deleted!");
        }

        message = "Vehicle deleted successfully!";

        return message;
    }

    @Override
    public List<VehicleResponseDto> getAllVehicles(Boolean isWithInactive){
        List<VehicleResponseDto> vehicleResponseDtos = new ArrayList<>();

        List<Vehicle> vehicles = new ArrayList<>();
        if(isWithInactive){
            vehicles = vehicleRepository.findByStatusIn(List.of(WellKnownStatus.ACTIVE.getValue(), WellKnownStatus.INACTIVE.getValue()));
        }else{
            vehicles = vehicleRepository.findByStatusIn(List.of(WellKnownStatus.ACTIVE.getValue()));
        }

        for(Vehicle vehicle : vehicles){
            VehicleResponseDto vehicleResponseDto = modelMapper.map(vehicle, VehicleResponseDto.class);

            UserResponseDto userResponseDto = modelMapper.map(vehicle.getUser(), UserResponseDto.class);

            vehicleResponseDto.setDriver(userResponseDto);

            vehicleResponseDtos.add(vehicleResponseDto);
        }

        return vehicleResponseDtos;
    }

}
