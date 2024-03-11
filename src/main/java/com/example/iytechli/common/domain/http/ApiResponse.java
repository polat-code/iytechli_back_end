package com.example.iytechli.common.domain.http;

import java.util.Date;

public record ApiResponse<T>(T data, String message,int statusCode, boolean success, Date timestamp) {
}
