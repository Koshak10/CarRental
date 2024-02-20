package me.abdulkarim.carrental;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import me.abdulkarim.carrental.windows.WindowOpener;

public class CarRentalController {

    @FXML
    private Button clientButton;

    @FXML
    private ImageView clientImageView;

    @FXML
    private Label clientLabel;

    @FXML
    private Label greetingLabel;

    @FXML
    private Button vehicleButton;

    @FXML
    private ImageView vehicleImageView;

    @FXML
    private Label vehicleLabel;

    @FXML
    private void handleButtonEvent(ActionEvent event) {
        Stage stage = (Stage) clientButton.getScene().getWindow();
        stage.close();

        if (event.getSource() == clientButton) {
            WindowOpener.showClientWindow();
        } else if (event.getSource() == vehicleButton) {
            WindowOpener.showVehicleWindow();
        }
    }

}
