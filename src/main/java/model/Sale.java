package model;

import util.JsonFileHandler;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Sale {
    private int saleID;  // Change to int
    private int employeeId;
    private Integer customerId; // null for anonymous
    private double total;
    private LocalDateTime timestamp;

    private Sale(int saleID, int employeeId, Integer customerId, double total, LocalDateTime timestamp) {
        this.saleID = saleID;
        this.employeeId = employeeId;
        this.customerId = customerId;
        this.total = total;
        this.timestamp = timestamp;
    }

    public int getSaleID() { return saleID; }  // Change to return int
    public int getEmployeeId() { return employeeId; }
    public Integer getCustomerId() { return customerId; }
    public double getTotal() { return total; }
    public LocalDateTime getTimestamp() { return timestamp; }

    public String toJson() {
        return String.format(
                "{\"saleID\":%d,\"employeeId\":%d,\"customerId\":%s,\"total\":%.2f,\"timestamp\":\"%s\"}",
                saleID, employeeId, customerId == null ? "null" : customerId, total, timestamp
        );
    }

    public static Sale fromJson(String json) {
        json = json.trim();
        if (json.startsWith("{")) json = json.substring(1);
        if (json.endsWith("}")) json = json.substring(0, json.length() - 1);

        String[] parts = json.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)"); // splits commas outside quotes

        int saleID = 0;  // Change to int
        int employeeId = 0;
        Integer customerId = null;
        double total = 0;
        LocalDateTime timestamp = null;

        for (String part : parts) {
            String[] kv = part.split(":", 2);
            String key = kv[0].replaceAll("\"", "").trim();
            String value = kv[1].trim();

            switch (key) {
                case "saleID":
                    saleID = Integer.parseInt(value.replaceAll("\"", "")); // <-- This is necessary
                    break;
                case "employeeId":
                    employeeId = Integer.parseInt(value.replaceAll("\"", ""));
                    break;
                case "customerId":
                    customerId = value.equals("null") ? null : Integer.parseInt(value.replaceAll("\"", ""));
                    break;
                case "total":
                    total = Double.parseDouble(value.replaceAll("\"", ""));
                    break;
                case "timestamp":
                    timestamp = LocalDateTime.parse(value.replaceAll("\"", ""));
                    break;
            }
        }

        return new Sale(saleID, employeeId, customerId, total, timestamp);
    }

    public static Sale create(int saleID, int employeeId, Integer customerId, double total, LocalDateTime timestamp, String filePath) {
        Sale sale = new Sale(saleID, employeeId, customerId, total, timestamp);
        JsonFileHandler.append(filePath, sale.toJson());
        return sale;
    }

    public static Sale getById(int saleID, String filePath) {  // Change to int
        List<String> jsonList = JsonFileHandler.load(filePath);
        for (String json : jsonList) {
            Sale sale = fromJson(json);
            if (sale.getSaleID() == saleID) return sale;  // Change to compare int
        }
        return null;
    }

    public static List<Sale> getAll(String filePath) {
        List<String> jsonList = JsonFileHandler.load(filePath);
        List<Sale> list = new ArrayList<>();
        for (String json : jsonList) {
            list.add(fromJson(json));
        }
        return list;
    }

    @Override
    public String toString() {
        return String.format(
                "SaleID: %d | EmployeeID: %d | CustomerID: %s | Total: %.2f | Timestamp: %s",
                saleID, employeeId, (customerId == null ? "Anonymous" : customerId), total, timestamp
        );
    }
}
