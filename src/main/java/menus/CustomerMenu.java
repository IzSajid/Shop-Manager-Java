package menus;

import model.Customer;

import java.util.List;
import java.util.Scanner;

public class CustomerMenu {

    public static void show(String customerFile) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n--- Customer Menu ---");
            System.out.println("1. Add Customer");
            System.out.println("2. View All Customers");
            System.out.println("3. Search Customer by ID");
            System.out.println("4. Delete Customer");
            System.out.println("5. Back to Main Menu");
            System.out.print("Select an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1 -> {
                    System.out.print("Enter customer name: ");
                    String name = scanner.nextLine();
                    Customer c = Customer.create(name, customerFile);
                    System.out.println("Customer added: " + c);
                }
                case 2 -> {
                    List<Customer> all = Customer.getAll(customerFile);
                    if (all.isEmpty()) {
                        System.out.println("No customers found.");
                    } else {
                        all.forEach(System.out::println);
                    }
                }
                case 3 -> {
                    System.out.print("Enter ID to search: ");
                    int id = scanner.nextInt();
                    Customer matches=null;
                    matches = Customer.getById(id, customerFile);
                    if (matches == null) {
                        System.out.println("No matches found.");
                    } else {
                        System.out.println("Customer found: " + matches);
                    }
                }
                case 4 -> {
                    System.out.print("Enter customer ID to delete: ");
                    int id = scanner.nextInt();
                    boolean deleted = Customer.delete(id, customerFile);
                    System.out.println(deleted ? "Customer deleted." : "Customer not found.");
                }
                case 5 -> {
                    System.out.println("Returning to main menu...");
                    return;
                }
                default -> System.out.println("Invalid option.");
            }
        }
    }
}
