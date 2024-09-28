package com.workindia.app.railway_system.Exception;


import com.workindia.app.railway_system.dto.ApiResponse;
import com.workindia.app.railway_system.dto.ApiResponseStatus;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler({BadRequestException.class, ResourceNotFoundException.class})
	public ResponseEntity<ApiResponse> handleBadRequestException(RuntimeException ex) {
		ApiResponse apiResponse = new ApiResponse();
		apiResponse.setStatus(ApiResponseStatus.ERROR);
		apiResponse.setData(ex.getMessage());
		return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler({AuthenticationException.class})
	public ResponseEntity<ApiResponse> handleAuthenticationException(RuntimeException ex) {
		ApiResponse apiResponse = new ApiResponse();
		apiResponse.setStatus(ApiResponseStatus.ERROR);
		apiResponse.setData(ex.getMessage());
		return new ResponseEntity<>(apiResponse, HttpStatus.UNAUTHORIZED);
	}
}
