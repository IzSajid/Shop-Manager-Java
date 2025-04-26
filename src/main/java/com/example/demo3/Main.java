package com.example.demo3;

import model.*;
import menus.*;
import java.util.Scanner;



public class Main {
    static final String EMPLOYEE_FILE = "Data/employees.json";
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        Employee loggedInEmployee = login();

        if (loggedInEmployee != null) {
            System.out.println("Login successful. Welcome, " + loggedInEmployee.getName() + "!");
            showMainMenu(loggedInEmployee);
        } else {
            System.out.println("Login failed. Exiting...");
        }
    }

    private static Employee login() {
        System.out.println("=== Employee Login ===");
        System.out.print("Enter Employee ID: ");
        int id = Integer.parseInt(scanner.nextLine());

        Employee employee = Employee.getById(id, EMPLOYEE_FILE);
        if (employee == null) {
            System.out.println("Employee not found.");
            return null;
        }

        System.out.print("Enter Password: ");
        String password = scanner.nextLine();

        if (employee.getPassword().equals(password)) {
            return employee;
        } else {
            System.out.println("Incorrect password.");
            return null;
        }
    }
    private static void showMainMenu(Employee employee) {
        Scanner scanner = new Scanner(System.in);
        final String productFile = "Data/products.json";
        final String employeeFile = "Data/employees.json";
        final String customerFile = "Data/customers.json";
        final String salesFile = "Data/sales.json";
        final String salesItemFile = "Data/sales-items.json";

        while (true) {
            System.out.println("\n--- Main Menu ---");
            System.out.println("Logged in as: " + employee.getName());
            System.out.println("1. Manage Products");
            System.out.println("2. Manage Customers");
            System.out.println("3. Make a Sale");
            System.out.println("4. View Sales History");
            System.out.println("5. Logout");
            System.out.print("Select an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1 -> ProductMenu.show(productFile);
                case 2 -> CustomerMenu.show(customerFile);
                case 3 -> MakeSaleMenu.make(employee);
                case 4 -> ShowSalesMenu.show();
                case 5 -> {
                    System.out.println("Logging out...");
                    return;
                }
                default -> System.out.println("Invalid option. Try again.");
            }
        }
    }
}


