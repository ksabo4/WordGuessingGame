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

public class Themes implements Initializable {

    public static String[] themes = new String[3];
    public static int[] attempts = new int[3];
    public static boolean[] won = new boolean[3];


    @FXML
    private Label heading1, heading2, heading3, attempts1, attempts2, attempts3;
    @FXML
    private Button play1, play2, play3;
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("Themes");
        heading1.setText(themes[0]);
        heading2.setText(themes[1]);
        heading3.setText(themes[2]);
        attempts1.setText("Guesses left: " + attempts[0]);
        attempts2.setText("Guesses left: " + attempts[1]);
        attempts3.setText("Guesses left: " + attempts[2]);
        play1.setDisable(won[0]);
        play2.setDisable(won[1]);
        play3.setDisable(won[2]);
    }

    @FXML
    private void onPlay1ButtonPressed(ActionEvent event) {
        try {
            synchronized (GuiServer.clientConnection) {
                //Where servers request word for theme1
                GuiServer.clientConnection.send("I need word for theme 1");
            }
            PreGame.currentTheme = themes[0];
        } catch (Exception e) {
            System.out.println("Couldn't get word from Client");
        }

        try {
            root = FXMLLoader.load(Objects.requireNonNull(LoadScreen.class.getResource("PreGame.fxml")));
            stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root, 700, 500);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            System.out.println("Couldn't go to pre game screen");
        }
    }

    @FXML
    private void onPlay2ButtonPressed(ActionEvent event) {
        try {
            synchronized (GuiServer.clientConnection) {
                //Where servers request word for theme2
                GuiServer.clientConnection.send("I need word for theme 2");
            }
            PreGame.currentTheme = themes[1];
        } catch (Exception e) {
            System.out.println("Couldn't get word from Client");
        }

        try {
            root = FXMLLoader.load(Objects.requireNonNull(LoadScreen.class.getResource("PreGame.fxml")));
            stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root, 700, 500);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            System.out.println("Couldn't go to pre game screen");
        }
    }
    @FXML
    private void onPlay3ButtonPressed(ActionEvent event) {
        try {
            synchronized (GuiServer.clientConnection) {
                //Where servers request word for theme3
                GuiServer.clientConnection.send("I need word for theme 3");
            }
            PreGame.currentTheme = themes[2];
        } catch (Exception e) {
            System.out.println("Couldn't get word from Client");
        }

        try {
            root = FXMLLoader.load(Objects.requireNonNull(LoadScreen.class.getResource("PreGame.fxml")));
            stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root, 700, 500);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            System.out.println("Couldn't go to pre game screen");
        }
    }

    @FXML
    private void onBackButtonPressed(ActionEvent event) {
        try {
            root = FXMLLoader.load(Objects.requireNonNull(LoadScreen.class.getResource("MainMenu.fxml")));
            stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root, 700, 500);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            System.out.println("Couldn't go back to main Menu");
        }

    }
}
