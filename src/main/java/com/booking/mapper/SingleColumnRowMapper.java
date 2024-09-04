package com.booking.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SingleColumnRowMapper<T> implements RowMapper<T> {
    private final Class<T> requiredType;

    public SingleColumnRowMapper(Class<T> requiredType) {
        this.requiredType = requiredType;
    }

    @Override
    public T getRow(ResultSet rs) {
        try {
			return rs.getObject(1, requiredType);
		} catch (SQLException e) {
			e.printStackTrace();
		}
        return null;
    }
}

