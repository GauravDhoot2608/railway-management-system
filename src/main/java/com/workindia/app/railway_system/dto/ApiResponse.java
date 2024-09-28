package com.workindia.app.railway_system.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ApiResponse {

	private ApiResponseStatus status = ApiResponseStatus.SUCCESS;
	private Object data;
}
