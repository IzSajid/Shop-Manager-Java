/*
package com.example.demo3;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.Employee;

public class LoginController {

    @FXML private TextField employeeIdField;
    @FXML private PasswordField passwordField;
    @FXML private Label errorLabel;

    @FXML
    private void handleLogin() {
        try {
            int id = Integer.parseInt(employeeIdField.getText().trim());
            String password = passwordField.getText().trim();

            Employee emp = Employee.getById(id, "Data/employees.json");
            if (emp == null) {
                errorLabel.setText("Employee not found.");
            } else if (!emp.getPassword().equals(password)) {
                errorLabel.setText("Incorrect password.");
            } else {
                // SUCCESS: Open main menu
                MainMenuApplication.launchWith(emp);
            }
        } catch (NumberFormatException e) {
            errorLabel.setText("Invalid ID format.");
        } catch (Exception e) {
            errorLabel.setText("Something went wrong.");
            e.printStackTrace();
        }
    }
}
*/