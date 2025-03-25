package com.cybertronix.vehiclebooking.common;

public class ApplicationRoute {
	 public static final String Root = "/api/v1";

	    public class User {
	        public static final String Root = ApplicationRoute.Root + "/user";
	        public static final String Save = "/register";
	        public static final String GetProfile = "/profile";
	        public static final String UpdateProfile = "/profile";
	        public static final String DeleteProfile = "/profile";
	        public static final String GetAll = "/get-all";
			public static final String GetById = "/{id}";
			public static final String ApproveDriver = "/admin/approve-driver/{id}";
			public static final String UpdateUserByAdmin = "/admin/update-user/{id}";
			public static final String DeleteUserByAdmin = "/admin/delete-user/{id}";
	    }

	    public class Authentication {
	        public static final String Root = ApplicationRoute.Root + "/auth";
	        public static final String Login = "/login";
	    }
	    
	    public class Vehicle {
	        public static final String Root = ApplicationRoute.Root + "/vehicle";
			public static final String GetAllVehicles = "/get-all";
			public static final String GetVehicleById = "/{id}";
			public static final String UpdateVehicle = "/{id}";
			public static final String DeleteVehicle = "/{id}";
			public static final String GetVehiclesByDriver = "/driver/{id}";
	    }
	    
	    public class Booking {
	        public static final String Root = ApplicationRoute.Root + "/booking";
			public static final String AddBooking = "/add-booking";
			public static final String UpdateBooking = "update-booking/{id}";
			public static final String GetAllBookings = "/get-all";
			public static final String GetBookingById = "/{id}";
			public static final String DeleteBooking = "/{id}";
			public static final String StartTrip = "/start-trip/{id}";
			public static final String EndTrip = "/end-trip";
			public static final String GetReceipt = "/receipt/{id}";
	    }
}
