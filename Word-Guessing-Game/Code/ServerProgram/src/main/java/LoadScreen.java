import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.Objects;

public class LoadScreen {
    @FXML
    private TextField userInput;
    @FXML
    private Label warningText;
    public void onButtonClick(ActionEvent event) {
        if (userInput.getText().isEmpty()) {
            warningText.setText("You need to type something");
            warningText.setVisible(true);
            return;
        }
        try {
            Server.portNumber = Integer.parseInt(userInput.getText());
            Parent root = FXMLLoader.load(Objects.requireNonNull(LoadScreen.class.getResource("ServerMenu.fxml")));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 500, 500);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            warningText.setText("You need to type a number");
            warningText.setVisible(true);
        }
    }
}
