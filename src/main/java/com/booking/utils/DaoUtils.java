package com.booking.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.booking.mapper.RowMapper;

// Dao增刪改查工具類
public class DaoUtils {
	// 增刪改
	public static DaoResult<Integer> commonsUpdate(String sql, Object... args) {
		DaoResult<Integer> daoResult = DaoResult.create();
		ResultSet resultSet = null;
		try(Connection connection = JDBCutil.getConnection();
			PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {
	
			if(args != null) {
				for(int i=1; i<=args.length; i++) {
					statement.setObject(i, args[i-1]);
				}
			}
			
			int affectedRows = statement.executeUpdate();
			daoResult.setAffectedRows(affectedRows);
			resultSet = statement.getGeneratedKeys();
			if(resultSet.next()) {
				daoResult.setGeneratedId(resultSet.getInt(1));;
			}
			
			return daoResult;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCutil.closeResource(resultSet);
		}
		return daoResult;
	}
	
	// 查詢
	public static <T> DaoResult<T> commonsQuery(String sql, RowMapper<T> rowMapper, Object... args) {
		DaoResult<T> daoResult = new DaoResult<>();
		ResultSet resultSet = null;
		try (Connection connection = JDBCutil.getConnection();
				PreparedStatement statement = connection.prepareStatement(sql);) {
				if(args != null) {
					for(int i=1; i<=args.length; i++) {
						statement.setObject(i, args[i-1]);
					}
				}
				resultSet = statement.executeQuery();
				List<T> list = new ArrayList<>();
				
				while(resultSet.next()) {
					T entity = rowMapper.getRow(resultSet);
					list.add(entity);
				}
				daoResult.setData(list);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCutil.closeResource(resultSet);
		}
		return daoResult;
	}
}