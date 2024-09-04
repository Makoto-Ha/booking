package com.booking.mapper;

import java.sql.ResultSet;

public interface RowMapper<T> {
	public T getRow(ResultSet resultSet);
}
