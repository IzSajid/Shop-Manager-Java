package util;

import model.*;
import util.JsonFileHandler;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class SalesManager {

    private static final String SALE_FILE = "Data/sales.json";
    private static final String SALE_ITEM_FILE = "Data/sale_items.json";
    private static final String PRODUCT_FILE = "Data/products.json";

    // Generate a new SaleID by looking at existing Sale IDs and finding the next in sequence
    public static int generateNextSaleID() {
        List<Sale> sales = Sale.getAll(SALE_FILE);
        int max = 0;
        for (Sale s : sales) {
            int num = s.getSaleID();  // SaleID is now just an integer
            if (num > max) max = num;
        }
        return max + 1;  // Return the next SaleID (incremented by 1)
    }

    // Process the sale
    public static Sale processSale(int employeeId, Integer customerId, List<SaleItem> saleItems) {
        if (saleItems == null || saleItems.isEmpty()) return null;

        // Generate a new saleID for the transaction (based on available SaleIDs)
        int saleID = generateNextSaleID();
        double total = 0;

        // Step 1: Check stock and calculate total for the sale
        for (SaleItem item : saleItems) {
            Product p = Product.getById(item.getProductId(), PRODUCT_FILE);
            if (p == null || item.getQuantity() > p.getQuantity()) {
                System.out.println("Error: Not enough stock for product ID " + item.getProductId());
                return null;  // Exit if stock is insufficient
            }
            total += p.getPrice() * item.getQuantity();  // Calculate total cost
        }

        // Step 2: Apply discount for registered customers
        if (customerId != null) {
            total *= 0.95;  // Apply 5% discount for registered customers
        }

        LocalDateTime now = LocalDateTime.now();  // Get current timestamp
        Sale sale = Sale.create(saleID, employeeId, customerId, total, now, SALE_FILE);  // Create sale

        // Step 3: Process each SaleItem
        for (SaleItem item : saleItems) {
            // Step 3a: Reduce product quantity based on sale
            Product p = Product.getById(item.getProductId(), PRODUCT_FILE);
            p.setQuantity(p.getQuantity() - item.getQuantity());  // Decrease quantity of the product
            Product.update(p.getId(), p.getName(), p.getPrice(), p.getQuantity(), PRODUCT_FILE);  // Update product

            // Step 3b: Create SaleItem entry for the sale
            SaleItem.create(saleID, item.getProductId(), item.getQuantity(), SALE_ITEM_FILE);
        }

        return sale;  // Return created sale object
    }

    // Retrieve all sales history
    public static List<Sale> getSalesHistory() {
        return Sale.getAll(SALE_FILE);  // Get all sales records
    }

    // Retrieve SaleItems associated with a specific saleID
    public static List<SaleItem> getSaleItemsBySaleID(int saleID) {  // Change saleID to int
        return SaleItem.getBySaleId(saleID, SALE_ITEM_FILE);  // Get SaleItems based on saleID
    }
}
