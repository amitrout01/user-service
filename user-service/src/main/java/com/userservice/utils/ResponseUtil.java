package com.userservice.utils;

import com.userservice.constants.ErrorCode;
import com.userservice.response.ApiResponse;

public class ResponseUtil {

	public static ApiResponse error(ErrorCode errorCode, String data) {
		return new ApiResponse(data, errorCode.getMessage(), null, errorCode.getCode());
	}

	public static ApiResponse error(ErrorCode errorCode) {
		return error(errorCode, null);
	}

	public static ApiResponse success(String data) {
		return new ApiResponse(data, ErrorCode.SUCCESS.getMessage(), null, ErrorCode.SUCCESS.getCode());
	}

	public static ApiResponse success(String data, String filePath) {
		return new ApiResponse(data, ErrorCode.SUCCESS.getMessage(), filePath, ErrorCode.SUCCESS.getCode());
	}
}
