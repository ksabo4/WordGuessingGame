import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

public class LoseGame {
    //For changing scenes
    private Stage stage;
    private Scene scene;
    private Parent root;

    public void onTryAgainPressed (ActionEvent event) {
        GuiServer.clientConnection.disconnectClient();
        try {
            root = FXMLLoader.load(Objects.requireNonNull(LoadScreen.class.getResource("LoadScreen.fxml")));
            stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root, 700, 500);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Issues switching to Main Menu");
        }
    }

    public void onQuitPressed (ActionEvent event) {
        Platform.exit();
        System.exit(0);
    }

}
