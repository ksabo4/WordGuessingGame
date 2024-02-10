import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.ResourceBundle;

public class ServerMenu implements Initializable {
    @FXML
    private ListView<String> listItems;
    @FXML
    private Label heading;
    //This method is called as soon as scene loads
    public void initialize(URL location, ResourceBundle resources) {
        heading.setText("This is port " + Server.portNumber);
        Server serverConnection = new Server(data -> {
            Platform.runLater(() -> {
                listItems.getItems().add(data.toString());
            });
        });
    }
}
