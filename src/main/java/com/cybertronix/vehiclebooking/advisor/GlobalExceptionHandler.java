// package com.cybertronix.vehiclebooking.advisor;


// import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
// import org.springframework.http.HttpStatus;
// import org.springframework.http.ResponseEntity;
// import org.springframework.security.core.userdetails.UsernameNotFoundException;
// import org.springframework.web.bind.MethodArgumentNotValidException;
// import org.springframework.web.bind.annotation.ControllerAdvice;
// import org.springframework.web.bind.annotation.ExceptionHandler;
// import org.springframework.security.access.AccessDeniedException;
// import org.springframework.web.bind.annotation.ResponseStatus;

// import com.cybertronix.vehiclebooking.common.CommonResponse;
// import com.cybertronix.vehiclebooking.exception.BadRequestException;
// import com.cybertronix.vehiclebooking.exception.ForbiddenException;
// import com.cybertronix.vehiclebooking.exception.InternalServerErrorException;
// import io.jsonwebtoken.ExpiredJwtException;
// import io.jsonwebtoken.JwtException;

// import io.jsonwebtoken.ExpiredJwtException;

// import java.security.SignatureException;


// @ControllerAdvice
// public class GlobalExceptionHandler {
// 	@ExceptionHandler(BadRequestException.class)
//     public ResponseEntity<CommonResponse> handleBadeRequestException(Exception e) {
//         return new ResponseEntity<CommonResponse>(
//                 new CommonResponse(false,e.getMessage(),null),
//                 HttpStatus.BAD_REQUEST
//         );
//     }

//     @ExceptionHandler(NotFoundException.class)
//     public ResponseEntity<CommonResponse> handleNotFoundException(Exception e) {
//         return new ResponseEntity<CommonResponse>(
//                 new CommonResponse(false,e.getMessage(), null),
//                 HttpStatus.NOT_FOUND
//         );
//     }


//     @ExceptionHandler(ForbiddenException.class)
//     public ResponseEntity<CommonResponse> handleForbiddenException(Exception e) {
//         return new ResponseEntity<CommonResponse>(
//                 new CommonResponse(false, e.getMessage(), null),
//                 HttpStatus.FORBIDDEN
//         );
//     }

//     @ExceptionHandler(InternalServerErrorException.class)
//     public ResponseEntity<CommonResponse> handleInternalServerException(Exception e){
//         return new ResponseEntity<CommonResponse>(
//                 new CommonResponse(false, e.getMessage(), null),
//                 HttpStatus.INTERNAL_SERVER_ERROR
//         );
//     }

//    @ExceptionHandler(UsernameNotFoundException.class)
//    public ResponseEntity<CommonResponse> handleUsernameNotFoundException(Exception e){
//        return new ResponseEntity<CommonResponse>(
//                new CommonResponse(false, "Invalid token!", null),
//                HttpStatus.UNAUTHORIZED
//        );
//    }

//    @ExceptionHandler(SignatureException.class)
//    public ResponseEntity<CommonResponse> handleSignatureException(Exception e){
//        return new ResponseEntity<CommonResponse>(
//                new CommonResponse(false, "Invalid token signature!", null),
//                HttpStatus.UNAUTHORIZED
//        );
//    }

//    @ExceptionHandler(JwtException.class)
//    public ResponseEntity<CommonResponse> handleJwtException(Exception e){
//        return new ResponseEntity<CommonResponse>(
//                new CommonResponse(false, "Invalid token!", null),
//                HttpStatus.UNAUTHORIZED
//        );
//    }

//    @ExceptionHandler(ExpiredJwtException.class)
//    public ResponseEntity<CommonResponse> handleExpiredJwtException(Exception e){
//        return new ResponseEntity<CommonResponse>(
//                new CommonResponse(false, "Your token expired!", null),
//                HttpStatus.UNAUTHORIZED
//        );
//    }

//    @ExceptionHandler(AccessDeniedException.class)
//    public ResponseEntity<CommonResponse> handleAccessDeniedException(Exception e){
//        return new ResponseEntity<CommonResponse>(
//                new CommonResponse(false, "Your not authorize to access this resource!", null),
//                HttpStatus.FORBIDDEN
//        );
//    }
    
//     @ExceptionHandler(MethodArgumentNotValidException.class)
//     @ResponseStatus(HttpStatus.BAD_REQUEST)
//     public ResponseEntity<CommonResponse> handleMethodArgumentException(MethodArgumentNotValidException exception){
//     	// Extract the first validation error message
//         String errorMessage = exception.getBindingResult().getFieldErrors().stream()
//                 .findFirst() // Get the first validation error
//                 .map(fieldError -> fieldError.getDefaultMessage()) // Extract the default message
//                 .orElse("Validation failed"); // Fallback message if no errors are found

  
//         // Return the response with the specific error message
//         return new ResponseEntity<CommonResponse>(
//                 new CommonResponse(false, errorMessage, null), // Use the specific error message
//                 HttpStatus.BAD_REQUEST
//         );
//     }

//     @ExceptionHandler(Exception.class)
//     public ResponseEntity<CommonResponse> handleException(Exception e) {
//         return new ResponseEntity<>(
//                 new CommonResponse(false, e.getMessage(), null),
//                 HttpStatus.INTERNAL_SERVER_ERROR
//         );
//     }

// }
