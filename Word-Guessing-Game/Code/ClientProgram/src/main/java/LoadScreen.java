import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class LoadScreen implements Initializable {

    //Scene1
    @FXML
    private TextField userInput;
    @FXML
    private Label warningText;
    //For changing scenes
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    public void initialize(URL location, ResourceBundle resources)
    {

    }

    @FXML
    protected void onButtonClick(ActionEvent event) {
        if (userInput.getText().isEmpty()) {
            setWarningText("You need to type something");
            return;
        }
        try {
            Client.portNumber = Integer.parseInt(userInput.getText());
        } catch (Exception e) {
            System.out.println(e);
            setWarningText("You need to type a number");
        }

        try {
            GuiServer.clientConnection = new Client(data -> {
                Platform.runLater(() -> {
                    //System.out.println(data.toString());
                });
            });
            synchronized (GuiServer.clientConnection) {
                GuiServer.clientConnection.start();
            }
            try {
                root = FXMLLoader.load(Objects.requireNonNull(LoadScreen.class.getResource("MainMenu.fxml")));
                stage = GuiServer.curStage;
                scene = new Scene(root, 700, 500);
                stage.setScene(scene);
                stage.show();
                warningText.setVisible(false);
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Issues switching to Main Menu");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Issues connecting to server");
            setWarningText("Invalid port. Try again.");
        }

        
    }

    public void setWarningText(String text)
    {
        warningText.setText(text);
        warningText.setVisible(true);
    }
}
