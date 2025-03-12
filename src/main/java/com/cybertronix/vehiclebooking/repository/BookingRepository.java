package com.cybertronix.vehiclebooking.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cybertronix.vehiclebooking.model.Booking;

@Repository
@EnableJpaRepositories
public interface BookingRepository extends JpaRepository<Booking, Long>{

    @Query("SELECT b FROM Booking b " +
       "WHERE b.vehicle.vehicleId = :vehicleId " +
       "AND ((b.startDate <= :endDate AND b.endDate >= :startDate) OR " +
       "(b.startDate >= :startDate AND b.endDate <= :endDate) OR " +
       "(b.startDate <= :startDate AND b.endDate >= :endDate)) " +
       "AND b.status IN :statuses")
    List<Booking> findConflictingBookings(@Param("vehicleId") Long vehicleId,
                                        @Param("startDate") Date startDate,
                                        @Param("endDate") Date endDate,
                                        @Param("statuses") List<Integer> statuses);

   Booking findByBookingIdAndStatusIn(Long bookingId, List<Integer> statuses);

   @Query("SELECT b FROM Booking b " +
       "WHERE b.user.userId = :userId " +
       "AND b.status IN :statusList")
   List<Booking> findBookingsByUserAndStatus(@Param("userId") Long userId, 
                                             @Param("statusList") List<Integer> statusList);

   @Query("SELECT b FROM Booking b " + "WHERE b.vehicle.user.userId = :driverId " + "AND b.status IN :statusList")
    List<Booking> findBookingsByDriverAndStatus(@Param("driverId") Long driverId, @Param("statusList") List<Integer> statusList);

   @Query("SELECT b FROM Booking b " + "WHERE b.status IN :statusList")
   List<Booking> findBookingsByStatus(@Param("statusList") List<Integer> statusList);

                                      

}
