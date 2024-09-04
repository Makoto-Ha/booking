package com.booking.utils;

import java.util.List;

// 用於返回Dao結果的類
public class DaoResult<T> {
	private T affectedRows;
	private T generatedId;
	private List<T> data; 
	
	protected static DaoResult<Integer> create(Integer affectedRows, Integer generatedId) {
		return new DaoResult<>(affectedRows, generatedId);
	}
	
	protected static DaoResult<Integer> create(Integer affectedRows) {
		return new DaoResult<>(affectedRows);
	}
	
	protected static <T> DaoResult<T> create() {
		return new DaoResult<>();
	}
	
	protected static <T> DaoResult<T> create(List<T> data) {
		return new DaoResult<>(data);
	}
	
	protected DaoResult() {}
	
	protected DaoResult(List<T> data) {
		this.data = data;
	}

	private DaoResult(T affectedRows, T generatedId) {
		this.affectedRows = affectedRows;
		this.generatedId = generatedId;
	}
	
	private DaoResult(T affectedRows) {
		this.affectedRows = affectedRows;
	}

	public T getAffectedRows() {
		return affectedRows;
	}

	public void setAffectedRows(T affectedRows) {
		this.affectedRows = affectedRows;
	}

	public T getGeneratedId() {
		return generatedId;
	}

	public void setGeneratedId(T generatedId) {
		this.generatedId = generatedId;
	}
	// 從Dao獲得多筆
	public List<T> getData() {
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}
	
	// 從Dao獲得單筆
	public T getEntity() {
		return data != null ? data.isEmpty() ? null : data.get(0) : null;
	}
}