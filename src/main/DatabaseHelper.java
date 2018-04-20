package main;

import java.sql.*;

public class DatabaseHelper {
	
	Connection connection;
	Statement statement;
	
	public DatabaseHelper() throws Exception {
		
		// Edit this to match your database 
		String driverName = "com.mysql.jdbc.Driver";
		String serverName = "";
		String myDatabase  = "";
		String url = "jdbc:mysql://" + serverName + "/" + myDatabase + "?autoReconnect=true&useSSL=false";
		String username = "";
		String password = "";
		
		Class.forName(driverName);
		connection = DriverManager.getConnection(url, username, password);
	}

	public String[] getRowWithId(String idNum) {
		String query = "SELECT * FROM table1 WHERE id = " + idNum + ";";
		String[] resultList = new String[4];
		try {
			statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(query);
			resultSet.next();
			resultList[0] = resultSet.getString("id");
			resultList[1] = resultSet.getString("name");
			resultList[2] = resultSet.getString("price");
			resultList[3] = resultSet.getString("description");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return resultList;
	}
	
	public String[][] getAllRows() {
		String query = "SELECT * FROM table1;";
		String[][] resultList = new String[30][4];
		try {
			statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(query);
			int counter = 0;
			
			while (resultSet.next()) {
				resultList[counter][0] = resultSet.getString("id");
				resultList[counter][1] = resultSet.getString("name");
				resultList[counter][2] = resultSet.getString("price");
				resultList[counter][3] = resultSet.getString("description");
				counter++;
			}
			return resultList;
		} catch (SQLException e) {
			e.printStackTrace();
			return resultList;
		}
	}
	
	public Boolean addRow(String[] values) {
		boolean completed = false;
		String query = "INSERT INTO table1(id, name, price, description) VALUES('" + values[0] + "', '" + values[1] +
				"', '" + values[2] + "', '" + values[3] + "');";

		try {
			statement = connection.createStatement();
			statement.executeUpdate(query);
			completed = true;
		} catch (SQLException e) {
			e.printStackTrace();
			completed = false;
		}
		return completed;
		
	}
	
	public Boolean updateRow(String[] values) {
		Boolean completed = false;
		String query = "UPDATE table1 SET name = '" + values[1] +
				"', price = '" + values[2] + "', description = '" + values[3] +
				"' WHERE id = " + values[0] + ";";
		
		try {
			statement = connection.createStatement();
			statement.executeUpdate(query);
			completed = true;
		} catch (SQLException e) {
			e.printStackTrace();
			completed = false;
		}
		return completed;
	}
	
	public Boolean deleteRowWithId(String idNum) {
		Boolean completed = false;
		String query = "DELETE FROM table1 WHERE id = " + idNum + ";";
		
		try {
			statement = connection.createStatement();
			statement.executeUpdate(query);
			completed = true;
		} catch (SQLException e) {
			e.printStackTrace();
			completed = false;
		}
		return completed;
	}
	
	public boolean close() {
		try {
			statement.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
}
