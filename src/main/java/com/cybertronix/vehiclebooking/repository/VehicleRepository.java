package com.cybertronix.vehiclebooking.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import com.cybertronix.vehiclebooking.model.Vehicle;

@Repository
@EnableJpaRepositories
public interface VehicleRepository extends JpaRepository<Vehicle, Long>{
 
    Vehicle findByUserUserIdAndStatusIn(Long userId,List<Integer> values);

    Vehicle findByVehicleIdAndStatusIn(Long vehicleId,List<Integer> values);

    List<Vehicle> findByStatusIn(List<Integer> values);

}
