package util;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class JsonFileHandler {

    // Save entire list (overwrite)
    public static void save(String filePath, List<String> jsonList) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write("[");
            for (int i = 0; i < jsonList.size(); i++) {
                writer.write(jsonList.get(i));
                if (i < jsonList.size() - 1) writer.write(",");
            }
            writer.write("]");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Load JSON strings from a valid array
    public static List<String> load(String filePath) {
        List<String> list = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            StringBuilder jsonArray = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                jsonArray.append(line.trim());
            }

            String raw = jsonArray.toString();
            if (raw.startsWith("[") && raw.endsWith("]")) {
                raw = raw.substring(1, raw.length() - 1).trim();
                if (!raw.isEmpty()) {
                    String[] items = raw.split("(?<=\\}),\\s*(?=\\{)");
                    for (String item : items) {
                        list.add(item.trim());
                    }
                }
            }
        } catch (IOException e) {
            // File might not exist yet
        }
        return list;
    }

    // Append a new item to the file
    public static void append(String filePath, String newItemJson) {
        List<String> currentItems = load(filePath);
        currentItems.add(newItemJson);
        save(filePath, currentItems);
    }
}