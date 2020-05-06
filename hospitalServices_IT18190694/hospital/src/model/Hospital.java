package model;

import java.sql.*;

public class Hospital {
	// A common method to connect to the DB
	private Connection connect() {
		Connection con = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");

			// Provide the correct details: DBServer/DBName, username, password
			con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/hospital", "root", "");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return con;
	}

	public String insertHospital(String hostID, String teleNo, String hostName, String location) {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for inserting.";
			}
			// create a prepared statement
			String query = " insert into hospital (`ID`,`hostID`,`teleNo`,`hostName`,`location`)"
					+ " values (?, ?, ?, ?, ?)";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setInt(1, 0);
			preparedStmt.setString(2, hostID);
			preparedStmt.setString(3, teleNo);
			preparedStmt.setString(4, hostName);
			preparedStmt.setString(5, location);
			// execute the statement
			preparedStmt.execute();
			con.close();
			String newHospitals = readHospitals();
			output = "{\"status\":\"success\", \"data\": \"" + newHospitals + "\"}";
		} catch (Exception e) {
			output = "{\"status\":\"error\", \"data\": \"Error while inserting the hospital.\"}";
			System.err.println(e.getMessage());
		}
		return output;
	}

	public String readHospitals() {

		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for reading.";
			}
			// Prepare the html table to be displayed
			output = "<table border='1'><tr><th>hostID</th> <th>teleNo</th><th>hostName</th>"
					+ "<th>location</th> <th>Update</th><th>Remove</th></tr>";
			String query = "select * from hospital";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			// iterate through the rows in the result set
			while (rs.next()) {
				String ID = Integer.toString(rs.getInt("ID"));
				String hostID = rs.getString("hostID");
				String teleNo = rs.getString("teleNo");
				String hostName = rs.getString("hostName");
				String location = rs.getString("location");
				// Add into the html table
				output += "<tr><td><input id='hidIDUpdate' name='hidIDUpdate' type='hidden' value='" + ID
						+ "'>" + hostID + "</td>";
				output += "<td>" + teleNo + "</td>";
				output += "<td>" + hostName + "</td>";
				output += "<td>" + location + "</td>";
				// buttons
				output += "<td><input name='btnUpdate' type='button' value='Update' class='btnUpdate btn btn-secondary'></td>"
						+ "<td><input name='btnRemove' type='button' value='Remove' class='btnRemove btn btn-danger' data-id='"
						+ ID + "'>" + "</td></tr>";
			}
			con.close();
			// Complete the html table
			output += "</table>";
		} catch (Exception e) {
			output = "Error while reading the hospitals.";
			System.err.println(e.getMessage());
		}
		return output;
	}

	public String updateHospital(String ID, String hostID, String teleNo, String hostName, String location) {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for updating.";
			}
			// create a prepared statement
			String query = "UPDATE hospital SET hostID=?,teleNo=?,hostName=?,location=? WHERE ID=?";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setString(1, hostID);
			preparedStmt.setString(2, teleNo);
			preparedStmt.setString(3, hostName);
			preparedStmt.setString(4, location);
			preparedStmt.setInt(5, Integer.parseInt(ID));
			// execute the statement
			preparedStmt.execute();
			con.close();
			String newHospitals = readHospitals();
			output = "{\"status\":\"success\", \"data\": \"" + newHospitals + "\"}";
		} catch (Exception e) {
			output = "{\"status\":\"error\", \"data\": \"Error while updating the hospital.\"}";
			System.err.println(e.getMessage());
		}
		return output;
	}

	public String deleteHospital(String ID) {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for deleting.";
			}
			// create a prepared statement
			String query = "delete from hospital where ID=?";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setInt(1, Integer.parseInt(ID));
			// execute the statement
			preparedStmt.execute();
			con.close();
			String newHospitals = readHospitals();
			output = "{\"status\":\"success\", \"data\": \"" + newHospitals + "\"}";
		} catch (Exception e) {
			output = "{\"status\":\"error\", \"data\": \"Error while deleting the hospital.\"}";
			System.err.println(e.getMessage());
		}
		return output;
	}
}