package menus;

import model.Sale;
import model.SaleItem;
import util.SalesManager;

import java.util.List;
import java.util.Scanner;

public class ShowSalesMenu {

    public static void show() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\n--- Show Sales Menu ---");
            System.out.println("1. Show All Sales");
            System.out.println("2. View Sale Items by Sale ID");
            System.out.println("3. Search Sale by ID");
            System.out.println("0. Return to Main Menu");
            System.out.print("Choose an option: ");

            String input = scanner.nextLine();

            switch (input) {
                case "1":
                    List<Sale> allSales = SalesManager.getSalesHistory();
                    if (allSales.isEmpty()) {
                        System.out.println("No sales found.");
                    } else {
                        for (Sale sale : allSales) {
                            System.out.println(sale);
                        }
                    }
                    break;

                case "2":
                    System.out.print("Enter Sale ID: ");
                    int id = Integer.parseInt(scanner.nextLine());
                    List<SaleItem> items = SalesManager.getSaleItemsBySaleID(id);
                    if (items.isEmpty()) {
                        System.out.println("No sale items found for Sale ID: " + id);
                    } else {
                        for (SaleItem item : items) {
                            System.out.println(item);
                        }
                    }
                    break;

                case "3":
                    System.out.print("Enter Sale ID to search: ");
                    int searchId = Integer.parseInt(scanner.nextLine());
                    Sale sale = SalesManager.getSalesHistory().stream()
                            .filter(s -> s.getSaleID() == searchId)
                            .findFirst()
                            .orElse(null);
                    if (sale == null) {
                        System.out.println("Sale not found.");
                    } else {
                        System.out.println(sale);
                    }
                    break;

                case "0":
                    return;

                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }
}
