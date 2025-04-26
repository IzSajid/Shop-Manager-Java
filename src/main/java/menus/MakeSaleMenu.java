package menus;

import model.Employee;
import model.Product;
import model.SaleItem;
import util.SalesManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MakeSaleMenu {

    public static void make(Employee employee) {
        Scanner scanner = new Scanner(System.in);
        List<SaleItem> saleItems = new ArrayList<>();

        System.out.println("\n--- Make a Sale ---");

        System.out.print("Is the customer registered? (yes/no): ");
        String response = scanner.nextLine().trim().toLowerCase();
        Integer customerId = null;
        if (response.equals("yes")) {
            System.out.print("Enter Customer ID: ");
            customerId = Integer.parseInt(scanner.nextLine());
        }

        while (true) {
            System.out.print("Enter Product ID to add (or 0 to finish): ");
            int productId = Integer.parseInt(scanner.nextLine());
            if (productId == 0) break;

            Product p = Product.getById(productId, "Data/products.json");
            if (p == null) {
                System.out.println("Product not found.");
                continue;
            }

            System.out.println("Product: " + p.getName() + " | Price: " + p.getPrice() + " | Available: " + p.getQuantity());
            System.out.print("Enter Quantity: ");
            int quantity = Integer.parseInt(scanner.nextLine());

            if (quantity <= 0) {
                System.out.println("Quantity must be greater than 0.");
                continue;
            }

            if (quantity > p.getQuantity()) {
                System.out.println("Not enough stock. Available: " + p.getQuantity());
                continue;
            }

            saleItems.add(new SaleItem(0, productId, quantity)); // temp saleID, actual ID will be injected during processing
            System.out.println("Item added to sale.");
        }

        if (saleItems.isEmpty()) {
            System.out.println("No items added. Sale cancelled.");
            return;
        }

        System.out.println("\nConfirm Sale? (yes/no): ");
        if (scanner.nextLine().trim().equalsIgnoreCase("yes")) {
            var sale = SalesManager.processSale(employee.getId(), customerId, saleItems);
            if (sale != null) {
                System.out.println("Sale completed successfully. Sale ID: " + sale.getSaleID());
            } else {
                System.out.println("Sale failed.");
            }
        } else {
            System.out.println("Sale cancelled.");
        }
    }
}
