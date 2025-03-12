package com.cybertronix.vehiclebooking.model;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "Users")
public class User implements UserDetails {
	 	@Id
	 	@GeneratedValue(strategy = GenerationType.IDENTITY)
	 	private Long userId;
	 
	 	@Column(name = "first_name", nullable = false, length = 100)
	 	private String firstName;

	 	@Column(name = "last_name", nullable = false, length = 100)
	 	private String lastName;
	 	
	 	@Column(name = "user_name", nullable = false, length = 100)
	 	private String userName;

	 	@Column(name = "phone_number", nullable = true, length = 20)
	 	private String phoneNumber;
	 	
		@Column(name = "nic_number", nullable = true, length = 20)
	 	private String nicNumber;
		
		@Column(name = "date_of_birth", nullable = true, length = 20)
	 	private String dateOfBirth;
		
		@Column(name = "address", nullable = true, length = 200)
	 	private String address;
		
		@Column(name = "nic_image", nullable = true)
	 	private String nicImage;
		
		@Column(name = "profile_image", nullable = true)
	 	private String profileImage;
		
	 	@Column(name = "status", nullable = false)
	    private Integer status;

	    @Column(name = "role", nullable = false)
	    private Integer role;

		@Column(name = "password", nullable = false)
		private String password;

		@Column(name = "approvedBy", nullable = true)
		private Long approvedBy;

	    @CreationTimestamp
	    @Column(name = "created_at")
	    private Date createdAt;

	    @UpdateTimestamp
	    @Column(name = "updated_at")
	    private Date updatedAt;
	    
	    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	    private List<Booking> bookings;

		@Override
		public Collection<? extends GrantedAuthority> getAuthorities() {
			return List.of(new SimpleGrantedAuthority("ROLE_" + role.toString()));
		}

		@Override
		public String getUsername() {
			return this.userName;
		}

		@Override
		public boolean isAccountNonExpired() {
			return true;
		}

		@Override
		public boolean isAccountNonLocked() {
			return true;
		}

		@Override
		public boolean isCredentialsNonExpired() {
			return true;
		}

		@Override
		public boolean isEnabled() {
			return true;
		}

		@Override
		public String getPassword() {
			return this.password;
		}
}
