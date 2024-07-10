package com.example.hotelreversation;

import java.sql.*;

public class CheckOuts {
    private final String url = "jdbc:mysql://localhost:3306/hotelinfo";
    private final String user = "Admin";
    private final String password = "pass123";

    public void checkOuts(int roomno) {
        String query = "SELECT * FROM HOTELINFO WHERE room_no = ?";
        String insertQuery = "INSERT INTO guests(id, Guest_fname, Guest_lname, Contact, Check_in,room_no) " +
                "VALUES (?, ?, ?, ?,?,?)";

        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = connection.prepareStatement(query);         // TRy with resources introduced in java 7
             PreparedStatement stmt2 = connection.prepareStatement(insertQuery)) {

            stmt.setInt(1, roomno);
            ResultSet resultSet = stmt.executeQuery();

            if (resultSet.next()) {
                int regId = resultSet.getInt("registration_id");
                String guestFirstName = resultSet.getString("guest_name");
                String guestLastName = resultSet.getString("guest_lastname");
                String mobileNo = resultSet.getString("guest_mobile_no");
                Timestamp timestamp = resultSet.getTimestamp("reservation_date");

                stmt2.setInt(1, regId);
                stmt2.setString(2, guestFirstName);
                stmt2.setString(3, guestLastName);
                stmt2.setString(4, mobileNo);
                stmt2.setTimestamp(5, timestamp);
                stmt2.setInt(6,roomno);


                stmt2.executeUpdate();
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        finally {
        }
    }

    public void closeResources(Connection connection, Statement stmt,Statement stmt2){
      try{
          connection.close();
          stmt.close();
          stmt2.close();
    }catch(Exception e){
          System.out.println( "Error While closing"+e.getMessage());
      }
    }
}
