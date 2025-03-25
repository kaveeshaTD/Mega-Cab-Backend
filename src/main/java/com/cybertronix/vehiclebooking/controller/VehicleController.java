package com.cybertronix.vehiclebooking.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cybertronix.vehiclebooking.common.ApplicationRoute;
import com.cybertronix.vehiclebooking.common.CommonResponse;
import com.cybertronix.vehiclebooking.dto.AuthenticationTicketDto;
import com.cybertronix.vehiclebooking.dto.request.UserUpdateRequestDto;
import com.cybertronix.vehiclebooking.dto.request.VehicleUpdateRequestDto;
import com.cybertronix.vehiclebooking.dto.response.UserResponseDto;
import com.cybertronix.vehiclebooking.dto.response.VehicleResponseDto;
import com.cybertronix.vehiclebooking.service.AuthenticationService;
import com.cybertronix.vehiclebooking.service.UserService;
import com.cybertronix.vehiclebooking.service.VehicleService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(ApplicationRoute.Vehicle.Root)
public class VehicleController {

    private final AuthenticationService authenticationService;

    private final VehicleService vehicleService;

    @PatchMapping(ApplicationRoute.Vehicle.UpdateVehicle)
    ResponseEntity<CommonResponse> UpdateUserProfile (@RequestBody @Valid VehicleUpdateRequestDto request, @PathVariable Long id){
        ResponseEntity<CommonResponse> response = null;

        VehicleResponseDto vehicle = vehicleService.updateVehicle(id, request);

        response = new ResponseEntity<CommonResponse>(
                new CommonResponse(true, "Profile Updated Successfully!", vehicle),
                HttpStatus.OK
        );

        return response;
    }


    @GetMapping(ApplicationRoute.Vehicle.GetVehicleById)
    ResponseEntity<CommonResponse> GetVehicleById(@PathVariable Long id){
        ResponseEntity<CommonResponse> response = null;

        VehicleResponseDto vehicle = vehicleService.getVehicleById(id);

        response = new ResponseEntity<CommonResponse>(
                new CommonResponse(true, "", vehicle),
                HttpStatus.OK
        );
        return response;
    }

    @DeleteMapping(ApplicationRoute.Vehicle.DeleteVehicle)
    ResponseEntity<CommonResponse> DeleteVehicle(@PathVariable Long id){
        ResponseEntity<CommonResponse> response = null;

        String message = vehicleService.deleteVehicle(id);

        response = new ResponseEntity<CommonResponse>(
                new CommonResponse(true, message, null),
                HttpStatus.OK
        );
        return response;
    }

    // public static final String GetVehiclesByDriver = "/driver/{id}";
    @GetMapping(ApplicationRoute.Vehicle.GetAllVehicles)
    ResponseEntity<CommonResponse> GetAllVehicles(
        @RequestParam(defaultValue = "false") Boolean isWithInactive
    ){
        ResponseEntity<CommonResponse> response = null;

        List<VehicleResponseDto> vehicles = vehicleService.getAllVehicles(isWithInactive);

        response = new ResponseEntity<CommonResponse>(
                new CommonResponse(true, "", vehicles),
                HttpStatus.OK
        );
        return response;
    }

    

}
