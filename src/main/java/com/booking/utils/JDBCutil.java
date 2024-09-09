package com.booking.utils;

import java.beans.PropertyVetoException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class JDBCutil {
	private static ComboPooledDataSource dataSource;

	static {
		try {
			// (InputStream is =
			// JDBCutil.class.getClassLoader().getResourceAsStream("jdbc.properties"))
//			Properties props = new Properties();
//			props.load(is);
			dataSource = new ComboPooledDataSource();
//			String url = props.getProperty("url");
//			String user = props.getProperty("user");
//			String password = props.getProperty("password");

			String url = "jdbc:sqlserver://localhost:1433;DatabaseName=bookingDB;encrypt=false";
			String user = "Cheng";
			String password = "1234";

			dataSource.setDriverClass("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			dataSource.setJdbcUrl(url);
			dataSource.setUser(user);
			dataSource.setPassword(password);

			dataSource.setInitialPoolSize(5);
			dataSource.setMinPoolSize(5);
			dataSource.setMaxPoolSize(10);
			dataSource.setMaxIdleTime(300);
		} catch (PropertyVetoException e) {
			e.printStackTrace();
		}

	}

	public static Connection getConnection() throws SQLException {
		try {
			return dataSource.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("連接失敗");
			return null;
		}
	}

	public static void closeResource(Connection connection, Statement statement, ResultSet resultSet) {
		try {

			if (resultSet != null) {
				resultSet.close();
			}

			if (statement != null) {
				statement.close();
			}

			if (connection != null) {
				connection.close();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void closeResource(Connection connection, Statement statement) {
		try {

			if (statement != null) {
				statement.close();
			}

			if (connection != null) {
				connection.close();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void closeResource(Connection connection) {
		try {
			if (connection != null) {
				connection.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void closeResource(PreparedStatement preparedStatement) {
		try {
			if (preparedStatement != null) {
				preparedStatement.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void closeResource(Connection connection, ResultSet resultSet) {
		try {
			if (resultSet != null) {
				resultSet.close();
			}

			if (connection != null) {
				connection.close();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void closeResource(Connection connection, CallableStatement callableStatement, ResultSet resultSet) {
		try {
			if (resultSet != null) {
				resultSet.close();
			}

			if (callableStatement != null) {
				callableStatement.close();
			}

			if (connection != null) {
				connection.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void closeResource(Connection connection, PreparedStatement preparedStatement, FileInputStream fis) {
		try {
			if (fis != null) {
				fis.close();
			}

			if (preparedStatement != null) {
				preparedStatement.close();
			}

			if (connection != null) {
				connection.close();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void closeResource(Connection connection, PreparedStatement preparedStatement, FileOutputStream fos) {
		try {
			if (preparedStatement != null) {
				preparedStatement.close();
			}

			if (fos != null) {
				fos.close();
			}

			if (connection != null) {
				connection.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void closeResource(ResultSet resultSet) {
		try {
			if (resultSet != null) {
				resultSet.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
