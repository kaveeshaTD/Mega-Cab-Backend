package com.cybertronix.vehiclebooking.model;

import java.util.Date;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "Vehicle")
public class Vehicle {
	@Id
 	@GeneratedValue(strategy = GenerationType.IDENTITY)
 	private Long vehicleId;
 
 	@Column(name = "vehicle_type", nullable = false, length = 100)
 	private String vehicleType;
 	
 	@Column(name = "registration_no", nullable = false, length = 100)
 	private String registrationNo;
 	
 	@Column(name = "passenger_count", nullable = false)
 	private Integer passengerCount;
 	
    @Column(name = "charge_per_km", nullable = false)
    private Double chargePerKm;
    
    @Column(name = "vehicle_image_01", nullable = true)
    private String vehicleImage1;
    
    @Column(name = "vehicle_image_02", nullable = true)
    private String vehicleImage2;
    
    @Column(name = "vehicle_description", nullable = true, length = 500)
    private String vehiceldescription;
    
    @Column(name = "is_tax_included", nullable = false)
    private Boolean isTaxIncluded = false;
    
    @Column(name = "is_vat_included", nullable = false)
    private Boolean isVatIncluded = false;
    
    @Column(name = "is_service_charge_included", nullable = false)
    private Boolean isServiceChargeIncluded = false;
    
    @Column(name = "tax", nullable = false)
    private Double tax = 0.00;
    
    @Column(name = "vat", nullable = false)
    private Double vat = 0.00;
    
    @Column(name = "service_charge", nullable = false)
    private Double serviceCharge = 0.00;
    
    @Column(name = "status", nullable = false)
    private Integer status;

    @CreationTimestamp
    @Column(name = "created_at")
    private Date createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Date updatedAt;
    
    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "userId", nullable = false, unique = true)
    private User user;
    
    @OneToMany(mappedBy = "vehicle", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Booking> bookings;
}
