package com.booking.utils;

import java.util.HashMap;
import java.util.Map;

// 用於返回Dao結果的類
public class DaoResult<T> {
	private Integer affectedRows;
	private Integer generatedId;
	private boolean success;
	private T data;
	private Map<String, Object> extraData = new HashMap<>();
	
	public static <T> DaoResult<T> create() {
		return new DaoResult<>();
	}
	
	public static <T> DaoResult<T> create(T data) {
		return new DaoResult<>(data);
	}
	
	private DaoResult() {}
	
	private DaoResult(T data) {
		this.data = data;
	}
	
	public boolean isSuccess() {
		return success;
	}
	
	public boolean isFailure() {
		return !success;
	}
	
	public DaoResult<T> setSuccess(boolean success) {
		this.success = success;
		return this;
	}

	public Integer getAffectedRows() {
		return affectedRows;
	}

	public DaoResult<T> setAffectedRows(Integer affectedRows) {
		this.affectedRows = affectedRows;
		return this;
	}

	public Integer getGeneratedId() {
		return generatedId;
	}

	public DaoResult<T> setGeneratedId(Integer generatedId) {
		this.generatedId = generatedId;
		return this;
	}
	
	public T getData() {
		return data;
	}
	
	public DaoResult<T> setData(T data) {
		this.data = data;
		return this;
	}

	public Object getExtraData(String extraKey) {
		return this.extraData.get(extraKey);
	}

	public DaoResult<T> setExtraData(String extraKey, Object extraValue) {
		this.extraData.put(extraKey, extraValue);
		return this;
	}
}