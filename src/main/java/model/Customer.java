package model;

import util.JsonFileHandler;
import java.util.ArrayList;
import java.util.List;

public class Customer {
    private int id;
    private String name;

    private Customer(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() { return id; }
    public String getName() { return name; }

    public String toJson() {
        return String.format("{\"id\":%d,\"name\":\"%s\"}", id, escape(name));
    }

    @Override
    public String toString() {
        return String.format("ID: %d | Name: %s", id, name);
    }

    public static Customer fromJson(String json) {
        json = json.trim();
        if (json.startsWith("{")) json = json.substring(1);
        if (json.endsWith("}")) json = json.substring(0, json.length() - 1);

        String[] parts = json.split(",");
        int id = Integer.parseInt(parts[0].split(":")[1].trim());
        String name = parts[1].split(":")[1].trim().replaceAll("^\"|\"$", "");
        return new Customer(id, name);
    }

    public static Customer create(String name, String filePath) {
        List<String> existing = JsonFileHandler.load(filePath);

        for (String json : existing) {
            Customer c = fromJson(json);
            if (c.getName().equalsIgnoreCase(name)) {
                System.out.println("Duplicate customer name: " + name);
                return null;
            }
        }

        int maxId = 0;
        for (String json : existing) {
            Customer c = fromJson(json);
            if (c.getId() > maxId) {
                maxId = c.getId();
            }
        }

        int nextId = maxId + 1;
        Customer newCustomer = new Customer(nextId, name);
        JsonFileHandler.append(filePath, newCustomer.toJson());
        return newCustomer;
    }

    public static boolean update(int id, String newName, String filePath) {
        List<String> all = JsonFileHandler.load(filePath);
        List<String> updatedList = new ArrayList<>();
        boolean updated = false;

        for (String json : all) {
            Customer c = fromJson(json);
            if (c.getId() == id) {
                c.name = newName;
                updated = true;
            }
            updatedList.add(c.toJson());
        }

        if (updated) JsonFileHandler.save(filePath, updatedList);
        return updated;
    }

    public static Customer getById(int id, String filePath) {
        List<String> list = JsonFileHandler.load(filePath);
        for (String json : list) {
            Customer c = fromJson(json);
            if (c.getId() == id) return c;
        }
        return null;
    }

    public static List<Customer> getAll(String filePath) {
        List<String> jsonList = JsonFileHandler.load(filePath);
        List<Customer> result = new ArrayList<>();
        for (String json : jsonList) {
            result.add(fromJson(json));
        }
        return result;
    }

    public static boolean delete(int id, String filePath) {
        List<String> jsonList = JsonFileHandler.load(filePath);
        List<String> updatedList = new ArrayList<>();
        boolean deleted = false;

        for (String json : jsonList) {
            Customer c = fromJson(json);
            if (c.getId() == id) {
                deleted = true;
                continue;
            }
            updatedList.add(c.toJson());
        }

        if (deleted) JsonFileHandler.save(filePath, updatedList);
        return deleted;
    }

    private static String escape(String text) {
        return text.replace("\"", "\\\"");
    }
}
