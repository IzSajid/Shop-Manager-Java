package model;

import util.JsonFileHandler;
import java.util.ArrayList;
import java.util.List;

public class Product {
    private int id;
    private String name;
    private double price;
    private int quantity;

    private Product(int id, String name, double price, int quantity) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public double getPrice() { return price; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public String toJson() {
        return String.format("{\"id\":%d,\"name\":\"%s\",\"price\":%.2f,\"quantity\":%d}", id, escape(name), price, quantity);
    }

    @Override
    public String toString() {
        return String.format("ID: %d | Name: %s | Price: %.2f | Quantity: %d", id, name, price, quantity);
    }

    public static Product fromJson(String json) {
        json = json.trim();
        if (json.startsWith("{")) json = json.substring(1);
        if (json.endsWith("}")) json = json.substring(0, json.length() - 1);

        String[] parts = json.split(",");
        int id = Integer.parseInt(parts[0].split(":")[1].trim());
        String name = parts[1].split(":")[1].trim().replaceAll("^\"|\"$", "");
        double price = Double.parseDouble(parts[2].split(":")[1].trim());
        int quantity = Integer.parseInt(parts[3].split(":")[1].trim());
        return new Product(id, name, price, quantity);
    }

    public static Product create(String name, double price, int quantity, String filePath) {
        List<String> existingJsonList = JsonFileHandler.load(filePath);

        // Check for duplicates by name (case-insensitive)
        for (String json : existingJsonList) {
            Product p = fromJson(json);
            if (p.getName().equalsIgnoreCase(name)) {
                System.out.println("Duplicate product name: " + name);
                return null;
            }
        }

        int maxId = 0;
        for (String json : existingJsonList) {
            Product p = fromJson(json);
            if (p.getId() > maxId) {
                maxId = p.getId();
            }
        }

        int nextId = maxId + 1;
        Product newProduct = new Product(nextId, name, price, quantity);
        JsonFileHandler.append(filePath, newProduct.toJson());
        return newProduct;
    }

    public static Product getById(int id, String filePath) {
        List<String> existingJsonList = JsonFileHandler.load(filePath);
        for (String json : existingJsonList) {
            Product p = fromJson(json);
            if (p.getId() == id) return p;
        }
        return null;
    }

    public static boolean update(int id, String newName, double newPrice, int newQuantity, String filePath) {
        List<String> existingJsonList = JsonFileHandler.load(filePath);
        List<String> updatedList = new ArrayList<>();
        boolean updated = false;

        for (String json : existingJsonList) {
            Product p = fromJson(json);
            if (p.getId() == id) {
                p.name = newName;
                p.price = newPrice;
                p.quantity = newQuantity;
                updated = true;
            }
            updatedList.add(p.toJson());
        }

        if (updated) JsonFileHandler.save(filePath, updatedList);
        return updated;
    }

    public static boolean delete(int id, String filePath) {
        List<String> existingJsonList = JsonFileHandler.load(filePath);
        List<String> updatedList = new ArrayList<>();
        boolean deleted = false;

        for (String json : existingJsonList) {
            Product p = fromJson(json);
            if (p.getId() == id) {
                deleted = true;
                continue;
            }
            updatedList.add(p.toJson());
        }

        if (deleted) JsonFileHandler.save(filePath, updatedList);
        return deleted;
    }

    private static String escape(String text) {
        return text.replace("\"", "\\\"");
    }

    public static List<Product> getAll(String filePath) {
        List<String> jsonList = JsonFileHandler.load(filePath);
        List<Product> products = new ArrayList<>();
        for (String json : jsonList) {
            products.add(fromJson(json));
        }
        return products;
    }

    public static List<Product> searchByName(String keyword, String filePath) {
        List<Product> results = new ArrayList<>();
        List<Product> all = getAll(filePath);
        for (Product p : all) {
            if (p.getName().toLowerCase().contains(keyword.toLowerCase())) {
                results.add(p);
            }
        }
        return results;
    }
}