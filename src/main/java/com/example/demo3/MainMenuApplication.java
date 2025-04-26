/*
package com.example.demo3;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainMenuApplication extends Application {

    private static Employee loggedInEmployee;

    public static void launchWith(Employee employee) {
        loggedInEmployee = employee;
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainMenuApplication.class.getResource("main-menu-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 400, 300);
        stage.setTitle("Main Menu");
        stage.setScene(scene);

        // Pass the logged-in employee to the main menu controller
        MainMenuController controller = fxmlLoader.getController();
        controller.initialize(loggedInEmployee);

        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
*/