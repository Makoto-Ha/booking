package com.booking.utils;

import java.util.HashMap;
import java.util.Map;

// Controller用於接收Service結果的類別
public class Result<T> {
    private boolean success;  
    private T data;           
    private Map<String, Object> extraData = new HashMap<>(); 
    private String message;   

    public static <T> Result<T> success(T data) {
        return new Result<>(data);
    }
    
    public static <T> Result<T> success(String message) {
    	Result<T> result = new Result<>(message);
    	result.setSuccess(true);
        return result;
    }

    public static <T> Result<T> failure(String message) {
        return new Result<>(message);
    }
    
    private Result(T data) {
        this.success = true;
        this.data = data;
    }

    private Result(String message) {
        this.success = false;
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }
    
    public boolean isFailure() {
    	return !success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
    
    public Object getExtraData(String extraDataName) {
		return this.extraData.get(extraDataName);
	}

	public Result<T> setExtraData(String extraDataName, Object extraDataValue) {
		this.extraData.put(extraDataName, extraDataValue);
		return this;
	}

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "Result{" +
               "success=" + success +
               ", data=" + data +
               ", message='" + message + '\'' +
               '}';
    }
}