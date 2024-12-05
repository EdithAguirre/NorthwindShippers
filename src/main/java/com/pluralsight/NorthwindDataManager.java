package com.pluralsight;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NorthwindDataManager {
    private DataSource dataSource;

    public NorthwindDataManager(DataSource dataSource){
        this.dataSource = dataSource;
    }


    public void insertShipper(String companyName, String phone) {
        String query = "INSERT INTO shippers(CompanyName, Phone) VALUES (?,?);";
        // Create connection and prepared statement
        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query,
                        Statement.RETURN_GENERATED_KEYS)
        ) {
            // Set the parameter
            preparedStatement.setString(1, companyName);
            preparedStatement.setString(2, phone);

            // Execute the query
            int rows = preparedStatement.executeUpdate();

            // Confirm updated rows
            System.out.printf("Rows updated %d\n", rows);

            // Get the result containing primary key(s)
            try (
                    ResultSet keys = preparedStatement.getGeneratedKeys()
            ) {
                // Iterate through generated primary keys
                while (keys.next()) {
                    System.out.printf("%d key was added\n", keys.getLong(1));
                }

            } catch (SQLException e) {
                System.out.println("Failed to execute query to get generated keys.");
            }
        } catch (SQLException e) {
            System.out.println("Failed to create connection or prepared statement.");
        }
    }

    public List<Shipper> getAllShippers(){
        List<Shipper> shippers = new ArrayList<>();

        String query = "SELECT * FROM shippers;";
        // Create connection and prepared statement
        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                ResultSet resultSet = preparedStatement.executeQuery()
        ) {
            // loop through result set and add all shippers to list
            while(resultSet.next()){
                shippers.add(new Shipper(resultSet.getInt(1), resultSet.getString(2),
                        resultSet.getString(3)));
            }
        }catch (SQLException e){
            System.out.println("Failed to connect sql or to create prepared statement.");
        }

        return shippers;
    }


    public void updateShipper(int id, String phoneNumber){
        String query = "UPDATE shippers SET Phone = ? WHERE ShipperID = ?;";
        // Create connection and prepared statement
        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)
        ) {
            // Set the parameter
            preparedStatement.setString(1, phoneNumber);
            preparedStatement.setInt(2, id);

            // Execute the query
            int rows = preparedStatement.executeUpdate();

            // Confirm updated rows
            System.out.printf("Rows updated %d\n", rows);

        } catch (SQLException e) {
            System.out.println("Failed to create connection or prepared statement.");
        }

    }


    public void deleteShipper(int shipperId){
        // Create connection and prepared statement
        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM shippers WHERE ShipperID = ?;");
        ) {
            // Set parameters
            preparedStatement.setInt(1,shipperId);

            // Execute prepared statement
            int rows = preparedStatement.executeUpdate();

            // Display rows updated
            System.out.printf("Rows deleted: %d\n", rows);

        }catch (SQLException e){
            System.out.println("Failed to connect sql or to create prepared statement.");
        }

    }

}
