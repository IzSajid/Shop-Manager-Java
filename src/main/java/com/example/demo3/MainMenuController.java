package com.example.demo3;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import model.Employee;

public class MainMenuController {

    private Employee loggedInEmployee;

    @FXML
    private Label welcomeLabel;

    public void setLoggedInEmployee(Employee employee) {
        this.loggedInEmployee = employee;
        welcomeLabel.setText("Logged in as: " + employee.getName());
    }

    public void onManageProducts(ActionEvent event) {
        System.out.println("TODO: Load product management scene.");
    }

    public void onManageCustomers(ActionEvent event) {
        System.out.println("TODO: Load customer management scene.");
    }

    public void onMakeSale(ActionEvent event) {
        System.out.println("TODO: Load sale making scene.");
    }

    public void onViewSalesHistory(ActionEvent event) {
        System.out.println("TODO: Load sales history scene.");
    }

    public void onLogout(ActionEvent event) {
        System.out.println("TODO: Return to login screen.");
    }
}
