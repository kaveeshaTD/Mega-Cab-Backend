package com.cybertronix.vehiclebooking.model;

import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "Booking")
public class Booking {
	@Id
 	@GeneratedValue(strategy = GenerationType.IDENTITY)
 	private Long bookingId;
	
    @Column(name = "start_date")
    private Date startDate;
    
    @Column(name = "end_date")
    private Date endDate;
    
    @Column(name = "description", nullable = true, length = 500)
    private String description;
    
    @Column(name = "distance", nullable = false)
    private Double distance = 0.00;

    @Column(name = "charge_per_km", nullable = false)
    private Double chargePerKm;

    @Column(name = "is_tax_included", nullable = false)
    private Boolean isTaxIncluded = false;
    
    @Column(name = "is_vat_included", nullable = false)
    private Boolean isVatIncluded = false;
    
    @Column(name = "is_service_charge_included", nullable = false)
    private Boolean isServiceChargeIncluded = false;

    @Column(name = "service_charge", nullable = false)
    private Double serviceCharge = 0.00;

    @Column(name = "tax", nullable = false)
    private Double tax = 0.00;
    
    @Column(name = "vat", nullable = false)
    private Double vat = 0.00;
    
    @Column(name = "tax_amount", nullable = false)
    private Double taxAmount = 0.00;
    
    @Column(name = "vat_amount", nullable = false)
    private Double vatAmount = 0.00;
    
    @Column(name = "service_charge_amount", nullable = false)
    private Double serviceChargeAmount = 0.00;

    @Column(name = "gross_amount", nullable = false)
    private Double grossAmount = 0.00;

    @Column(name = "discount_rate", nullable = false)
    private Double discountRate = 0.00;

    @Column(name = "discount_amount", nullable = false)
    private Double discountAmount = 0.00;
    
    @Column(name = "total_amount", nullable = false)
    private Double totalAmount = 0.00;

    @Column(name = "status", nullable = false)
    private Integer status;
    
    @CreationTimestamp
    @Column(name = "created_at")
    private Date createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Date updatedAt;

    // Many-to-One Relationship with User
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "userId", nullable = false)
    private User user;

    // Many-to-One Relationship with Vehicle
    @ManyToOne
    @JoinColumn(name = "vehicle_id", referencedColumnName = "vehicleId", nullable = false)
    private Vehicle vehicle;
    
}
