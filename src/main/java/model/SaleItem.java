package model;

import util.JsonFileHandler;
import java.util.ArrayList;
import java.util.List;

public class SaleItem {
    private int saleID;  // Change saleID to int
    private int productId;
    private int quantity;

    public SaleItem(int saleID, int productId, int quantity) {  // Update constructor
        this.saleID = saleID;
        this.productId = productId;
        this.quantity = quantity;
    }

    public int getSaleID() { return saleID; }  // Change return type to int
    public int getProductId() { return productId; }
    public int getQuantity() { return quantity; }

    public String toJson() {
        return String.format("{\"saleID\":%d,\"productId\":%d,\"quantity\":%d}", saleID, productId, quantity);
    }

    public static SaleItem fromJson(String json) {
        json = json.trim();
        if (json.startsWith("{")) json = json.substring(1);
        if (json.endsWith("}")) json = json.substring(0, json.length() - 1);

        String[] parts = json.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)"); // safe split
        int saleID = 0;
        int productId = 0;
        int quantity = 0;

        for (String part : parts) {
            String[] kv = part.split(":", 2);
            String key = kv[0].replaceAll("\"", "").trim();
            String value = kv[1].replaceAll("\"", "").trim();

            switch (key) {
                case "saleID":
                    saleID = Integer.parseInt(value.replaceAll("\"", ""));
                    break;
                case "productId":
                    productId = Integer.parseInt(value.replaceAll("\"", ""));
                    break;
                case "quantity":
                    quantity = Integer.parseInt(value.replaceAll("\"", ""));
                    break;
            }
        }

        return new SaleItem(saleID, productId, quantity);
    }

    public static SaleItem create(int saleID, int productId, int quantity, String filePath) {  // Change saleID to int
        SaleItem item = new SaleItem(saleID, productId, quantity);
        JsonFileHandler.append(filePath, item.toJson());
        return item;
    }

    public static List<SaleItem> getAll(String filePath) {
        List<String> jsonList = JsonFileHandler.load(filePath);
        List<SaleItem> items = new ArrayList<>();
        for (String json : jsonList) {
            items.add(fromJson(json));
        }
        return items;
    }

    public static List<SaleItem> getBySaleId(int saleID, String filePath) {  // Change saleID to int
        List<SaleItem> all = getAll(filePath);
        List<SaleItem> result = new ArrayList<>();
        for (SaleItem item : all) {
            if (item.getSaleID() == saleID) {  // Compare saleID as int
                result.add(item);
            }
        }
        return result;
    }

    @Override
    public String toString() {
        return String.format("SaleID: %d | ProductID: %d | Quantity: %d", saleID, productId, quantity);
    }
}
