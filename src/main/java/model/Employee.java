package model;

import util.JsonFileHandler;
import java.util.ArrayList;
import java.util.List;

public class Employee {
    private int id;
    private String name;
    private String password;
    private double salary;

    private Employee(int id, String name, String password, double salary) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.salary = salary;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getPassword() { return password; }
    public double getSalary() { return salary; }

    public String toJson() {
        return String.format("{\"id\":%d,\"name\":\"%s\",\"password\":\"%s\",\"salary\":%.2f}", id, escape(name), escape(password), salary);
    }

    @Override
    public String toString() {
        return String.format("ID: %d | Name: %s | Salary: %.2f", id, name, salary);
    }

    public static Employee fromJson(String json) {
        json = json.trim();
        if (json.startsWith("{")) json = json.substring(1);
        if (json.endsWith("}")) json = json.substring(0, json.length() - 1);

        String[] parts = json.split(",");
        int id = Integer.parseInt(parts[0].split(":")[1].trim());
        String name = parts[1].split(":")[1].trim().replaceAll("^\"|\"$", "");
        String password = parts[2].split(":")[1].trim().replaceAll("^\"|\"$", "");
        double salary = Double.parseDouble(parts[3].split(":")[1].trim());

        return new Employee(id, name, password, salary);
    }

    public static Employee create(String name, String password, double salary, String filePath) {
        List<String> existing = JsonFileHandler.load(filePath);

        for (String json : existing) {
            Employee e = fromJson(json);
            if (e.getName().equalsIgnoreCase(name)) {
                System.out.println("Duplicate employee name: " + name);
                return null;
            }
        }

        int maxId = 0;
        for (String json : existing) {
            Employee e = fromJson(json);
            if (e.getId() > maxId) {
                maxId = e.getId();
            }
        }

        int nextId = maxId + 1;
        Employee newEmp = new Employee(nextId, name, password, salary);
        JsonFileHandler.append(filePath, newEmp.toJson());
        return newEmp;
    }

    public static boolean update(int id, String newName, String newPassword, double newSalary, String filePath) {
        List<String> all = JsonFileHandler.load(filePath);
        List<String> updatedList = new ArrayList<>();
        boolean updated = false;

        for (String json : all) {
            Employee e = fromJson(json);
            if (e.getId() == id) {
                e.name = newName;
                e.password = newPassword;
                e.salary = newSalary;
                updated = true;
            }
            updatedList.add(e.toJson());
        }

        if (updated) JsonFileHandler.save(filePath, updatedList);
        return updated;
    }

    public static List<Employee> getAll(String filePath) {
        List<String> jsonList = JsonFileHandler.load(filePath);
        List<Employee> result = new ArrayList<>();
        for (String json : jsonList) {
            result.add(fromJson(json));
        }
        return result;
    }

    public static Employee getById(int id, String filePath) {
        List<String> jsonList = JsonFileHandler.load(filePath);
        for (String json : jsonList) {
            Employee e = fromJson(json);
            if (e.getId() == id) {
                return e;
            }
        }
        return null; // Return null if employee is not found
    }

    public static boolean delete(int id, String filePath) {
        List<String> jsonList = JsonFileHandler.load(filePath);
        List<String> updatedList = new ArrayList<>();
        boolean deleted = false;

        for (String json : jsonList) {
            Employee e = fromJson(json);
            if (e.getId() == id) {
                deleted = true;
                continue;
            }
            updatedList.add(e.toJson());
        }

        if (deleted) JsonFileHandler.save(filePath, updatedList);
        return deleted;
    }

    private static String escape(String text) {
        return text.replace("\"", "\\\"");
    }
}
