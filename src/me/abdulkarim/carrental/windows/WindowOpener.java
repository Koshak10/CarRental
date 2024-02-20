package me.abdulkarim.carrental.windows;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;

public class WindowOpener {

    public static void showMainWindow() {
        try {
            URL url = WindowOpener.class.getResource("../CarRental.fxml");
            Parent parent = FXMLLoader.load(Objects.requireNonNull(url));

            Scene scene = new Scene(parent);

            Stage stage = new Stage();
            stage.setTitle("Car Rental Management System");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    public static void showClientWindow() {
        try {
            URL url = WindowOpener.class.getResource("client/ClientWindow.fxml");
            Parent parent = FXMLLoader.load(Objects.requireNonNull(url));

            Scene scene = new Scene(parent);

            Stage stage = new Stage();
            stage.setTitle("Car Rental Management System");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    public static void showVehicleWindow() {
        try {
            URL url = WindowOpener.class.getResource("vehicle/VehicleWindow.fxml");
            Parent parent = FXMLLoader.load(Objects.requireNonNull(url));

            Scene scene = new Scene(parent);

            Stage stage = new Stage();
            stage.setTitle("Car Rental Management System");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    public static void showSelectionWindow() {
        try {
            URL url = WindowOpener.class.getResource("selection/SelectionWindow.fxml");
            Parent parent = FXMLLoader.load(Objects.requireNonNull(url));

            Scene scene = new Scene(parent);

            Stage stage = new Stage();
            stage.setTitle("Car Rental Management System");
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

}