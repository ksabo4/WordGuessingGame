import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class PreGame implements Initializable {
    public static String currentTheme;
    public static int wordLength;

    @FXML
    private Label heading;
    @FXML
    private Label text;
    private Stage stage;
    private Scene scene;
    private Parent root;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Set Theme text and word length text
        heading.setText("Theme: " + currentTheme);
        text.setText("Your word has " + wordLength + " letters");
    }

    public void onPlayButtonPressed(ActionEvent event) {
        //Call server again? Nope
        
        //Change scenes again
        try {
            root = FXMLLoader.load(Objects.requireNonNull(LoadScreen.class.getResource("Game.fxml")));
            stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root, 700, 500);
            scene.getStylesheets().add("GameStyle.css");
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            System.out.println("Couldn't go to pre game screen");
        }

    }
}
