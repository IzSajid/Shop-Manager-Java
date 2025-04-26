package menus;

import model.Product;

import java.util.List;
import java.util.Scanner;

public class ProductMenu {

    public static void show(String productFile) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n--- Product Menu ---");
            System.out.println("1. Add Product");
            System.out.println("2. View All Products");
            System.out.println("3. Search Product by Name");
            System.out.println("4. Update Product");
            System.out.println("5. Delete Product");
            System.out.println("6. Back to Main Menu");
            System.out.print("Select an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1 -> {
                    System.out.print("Enter product name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter price: ");
                    double price = scanner.nextDouble();
                    System.out.print("Enter quantity: ");
                    int qty = scanner.nextInt();
                    Product created = Product.create(name, price, qty, productFile);
                    if (created != null) System.out.println("Product added: " + created);
                }
                case 2 -> {
                    List<Product> products = Product.getAll(productFile);
                    if (products.isEmpty()) {
                        System.out.println("No products found.");
                    } else {
                        products.forEach(System.out::println);
                    }
                }
                case 3 -> {
                    System.out.print("Enter keyword to search: ");
                    String keyword = scanner.nextLine();
                    List<Product> results = Product.searchByName(keyword, productFile);
                    if (results.isEmpty()) {
                        System.out.println("No matches found.");
                    } else {
                        results.forEach(System.out::println);
                    }
                }
                case 4 -> {
                    System.out.print("Enter product ID to update: ");
                    int id = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("New name: ");
                    String newName = scanner.nextLine();
                    System.out.print("New price: ");
                    double newPrice = scanner.nextDouble();
                    System.out.print("New quantity: ");
                    int newQty = scanner.nextInt();
                    boolean success = Product.update(id, newName, newPrice, newQty, productFile);
                    System.out.println(success ? "Product updated." : "Product not found.");
                }
                case 5 -> {
                    System.out.print("Enter product ID to delete: ");
                    int id = scanner.nextInt();
                    boolean deleted = Product.delete(id, productFile);
                    System.out.println(deleted ? "Product deleted." : "Product not found.");
                }
                case 6 -> {
                    System.out.println("Returning to main menu...");
                    return;
                }
                default -> System.out.println("Invalid choice.");
            }
        }
    }
}
