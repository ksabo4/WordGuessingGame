import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class MainMenu implements Initializable {

    @FXML
    private Label portLabel;

    @FXML
    private Button playButton;
    //For changing scenes
    private Stage stage;
    private Scene scene;
    private Parent root;

    public static boolean validPort = true;

    //This method is called upon fxml load
    public void initialize(URL location, ResourceBundle resources) {
        if (GuiServer.clientConnection.isConnected()) {
            portLabel.setText("You are on\n port " + Client.portNumber);
        } else {
            portLabel.setText("You are not\nconnected\nto the server");
            playButton.setText("Change\nPort");
        }

    }

    @FXML
    protected void onPlayButtonPressed(ActionEvent event) {
        if (GuiServer.clientConnection.isConnected()) {
            try {
                synchronized (GuiServer.clientConnection) {
                    //Where servers request name for themes
                    GuiServer.clientConnection.send("I need themes");
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Issues requesting themes to Server");
            }

            try {
                root = FXMLLoader.load(Objects.requireNonNull(LoadScreen.class.getResource("Themes.fxml")));
                stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
                scene = new Scene(root, 700, 500);
                stage.setScene(scene);
                stage.show();
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Issues switching to Themes scene");
            }
        }
        else
        {
            try {
                root = FXMLLoader.load(Objects.requireNonNull(LoadScreen.class.getResource("LoadScreen.fxml")));
                stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
                scene = new Scene(root, 700, 500);
                stage.setScene(scene);
                stage.show();
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Issues switching to LoadScreen scene");
            }
        }
    }

    @FXML
    protected void onQuitButtonPressed() {
        Platform.exit();
        System.exit(0);
    }
}
