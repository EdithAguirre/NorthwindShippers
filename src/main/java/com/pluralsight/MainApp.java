package com.pluralsight;

import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class MainApp {
    public static void main(String[] args) {

        if (args.length != 2) {
            System.out.println(
                    "Application needs two arguments to run: " +
                            "java com.hca.jdbc.UsingDriverManager <username> " +
                            "<password>");
            System.exit(1);
        }

        // get the username and password from the command line args
        String url = "jdbc:mysql://localhost:3306/northwind";
        String username = args[0];
        String password = args[1];

        // Create and configure the DataSource
        try (
                BasicDataSource dataSource = new BasicDataSource()
        ) {
            // Configure the dataSource
            dataSource.setUrl(url);
            dataSource.setUsername(username);
            dataSource.setPassword(password);

            // Create the dataManager
            NorthwindDataManager dataManager = new NorthwindDataManager(dataSource);

            Scanner scanner = new Scanner(System.in);
            // 1. Prompt user for new shipper data (name and phone)
            System.out.print("Enter a new shipper. Enter the shipper's company name: ");
            String companyName = scanner.nextLine().trim();

            System.out.print("Enter the shipper's phone number: ");
            String phoneNumber = scanner.nextLine().trim();

            // Insert it into the shippers table
            // Display the new shipper id when insert is complete
            dataManager.insertShipper(companyName, phoneNumber);

            // 2. Run a query and display all the shippers
            List<Shipper> shipperList = dataManager.getAllShippers();

            displayHeader();
            shipperList.forEach(System.out::println);
            System.out.println();

            // 3. Prompt user to change the phone number of a shipper (Enter id and phone number)
            System.out.println("Change the phone number of a shipper.");
            System.out.print("Enter the shipper ID: ");
            int shipperID = scanner.nextInt();
            scanner.nextLine();
            System.out.print("Enter the shipper's updated phone number: ");
            String shipperPhoneNumber = scanner.nextLine();
            dataManager.updateShipper(shipperID, shipperPhoneNumber);

            // 4. Run query and display all the shippers
            List<Shipper> shipperList2 = dataManager.getAllShippers();
            displayHeader();
            shipperList2.forEach(System.out::println);
            System.out.println();

            // 5. Prompt user to delete a shipper (DO NOT ENTER SHIPPERS 1-3, DELETE NEW SHIPPER)
            System.out.print("Delete a shipper by entering their shipper ID: ");
            int shipID = scanner.nextInt();
            scanner.nextLine();
            dataManager.deleteShipper(shipID);

            // 6. Run query and display all the shippers
            List<Shipper> shipperList1 = dataManager.getAllShippers();
            displayHeader();
            shipperList1.forEach(System.out::println);
            System.out.println();

        } catch (SQLException e) {
            System.out.println("Fail in creating data source.");
        }
    }

    public static void displayHeader() {
        System.out.printf("\n%-10s %-20s %s\n", "ShipperID", "CompanyName", "Phone");
        System.out.printf("%-10s %-20s %s\n", "----------", "--------------------", "---------------");
    }
}
