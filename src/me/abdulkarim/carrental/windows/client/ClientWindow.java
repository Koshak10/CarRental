package me.abdulkarim.carrental.windows.client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import me.abdulkarim.carrental.client.Client;
import me.abdulkarim.carrental.client.ClientManager;
import me.abdulkarim.carrental.windows.WindowOpener;

import java.util.ArrayList;

public class ClientWindow {

    @FXML
    private Button backButton;

    @FXML
    private Label clientIdLabel;

    @FXML
    private ListView<Client> clientList;

    @FXML
    private Label clientNameLabel;

    @FXML
    private Label clientPhoneNoLabel;

    @FXML
    private Button deleteButton;

    @FXML
    private TextField idTextField;

    @FXML
    private TextField nameTextField;

    @FXML
    private Label outputLabel;

    @FXML
    private TextField phoneNoTextField;

    @FXML
    private Button registerButton;

    @FXML
    private Label registeredClientsLabel;

    @FXML
    private Label titleLabel;

    public void initialize() {
        ClientManager.getInstance().setup();

        ArrayList<Client> clients = ClientManager.getInstance().getClients();
        for (Client client : clients) {
            this.clientList.getItems().add(client);
        }

        registeredClientsLabel.setText("Registered Clients: (" + clients.size() + ")");
    }

    @FXML
    private void actionPerformed(ActionEvent event) {
        if (event.getSource() == registerButton) {
            if (idTextField.getText().isEmpty()
                    || nameTextField.getText().isEmpty()
                    || phoneNoTextField.getText().isEmpty()) {
                outputLabel.setText("ERROR - Missing information!");
                outputLabel.setTextFill(Color.RED);
                return;
            }

            int id = Integer.parseInt(idTextField.getText());
            String name = nameTextField.getText();
            String phoneNumber = phoneNoTextField.getText();

            Client client = new Client(id, name, phoneNumber);
            if (ClientManager.getInstance().newRecord(client, outputLabel)) {
                clientList.getItems().add(client);
                registeredClientsLabel.setText("Registered Clients: (" + clientList.getItems().size() + ")");
                clearTextFields();
            }
        } else if (event.getSource() == deleteButton) {
            if (ClientManager.getInstance().getClients().isEmpty()) {
                outputLabel.setText("ERROR - There are no clients in the database!");
                outputLabel.setTextFill(Color.RED);
                return;
            }

            boolean isNullSelection = clientList.getSelectionModel().isEmpty();
            if (isNullSelection) {
                outputLabel.setText("ERROR - You must select a client from the list!");
                outputLabel.setTextFill(Color.RED);
                return;
            }

            int id = Integer.parseInt(idTextField.getText());
            String name = nameTextField.getText();
            String phoneNumber = phoneNoTextField.getText();

            Client client = new Client(id, name, phoneNumber);
            if (ClientManager.getInstance().deleteRecord(client, outputLabel)) {
                clientList.getItems().remove(client);
                registeredClientsLabel.setText("Registered Clients: (" + clientList.getItems().size() + ")");
                clearTextFields();
            }
        } else if (event.getSource() == backButton) {
            Stage stage = (Stage) backButton.getScene().getWindow();
            stage.close();

            WindowOpener.showMainWindow();
        }
    }

    @FXML
    private void valueChanged() {
        Client client = clientList.getSelectionModel().getSelectedItem();

        idTextField.setText("" + client.getId());
        nameTextField.setText(client.getName());
        phoneNoTextField.setText(client.getPhoneNumber());
    }

    private void clearTextFields() {
        idTextField.setText("");
        nameTextField.setText("");
        phoneNoTextField.setText("");
    }

}
