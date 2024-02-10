import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;

public class Game implements Initializable {
    public static int currentGuesses;
    public static ArrayList<String> gameMessages;

    //For changing scenes
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private Label heading;
    @FXML
    private Label guessesText;
    @FXML
    private Label wordText;
    //@FXML
    //private TextField input;
    @FXML
    private Label warningText;

    private String letter;
    private ArrayList<Button> pressedButtons;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Set heading guesses Text and wordText
        heading.setText("Your theme is " + PreGame.currentTheme);
        guessesText.setText("Guesses left: " + Game.currentGuesses);
        StringBuilder word = new StringBuilder();
        for (int i = 0; i < PreGame.wordLength; i++) {
            if (i == 0) {
                word.append("_");
            } else {
                word.append(" _");
            }
        }
        wordText.setText(word.toString());
        if (gameMessages == null) {
            gameMessages = new ArrayList<>();
        }
        pressedButtons = new ArrayList<>();
    }

    public void switchScenes(ActionEvent event, String filePath) {
        try {
            root = FXMLLoader.load(Objects.requireNonNull(LoadScreen.class.getResource(filePath)));
            stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root, 700, 500);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Issues switching to " + filePath.substring(0, filePath.length()-5));
        }
    }

    public void getClientResponse(ActionEvent event) {

        //Reads message from server
        while (!gameMessages.isEmpty()) {
            String message = gameMessages.get(0);
            if (message.equals("already made")) {
                warningText.setText("You already made that guess");
                warningText.setVisible(true);
            } else if (message.contains("found at index ")) {
                System.out.println("Game got this message: " + message);
                String currentText = wordText.getText();
                int indexToModify = Integer.parseInt(message.substring("found at index ".length())) * 2;
                System.out.println(indexToModify / 2);
                //String newText = currentText.substring(0, indexToModify) + input.getText() + currentText.substring(indexToModify + 1);
                String newText = currentText.substring(0, indexToModify) + letter + currentText.substring(indexToModify + 1);
                wordText.setText(newText);
            } else if (message.equals("not in word")) {
                warningText.setText("That was not in the word");
                warningText.setVisible(true);
                currentGuesses--;
                guessesText.setText("Guesses left: " + Game.currentGuesses);
            } else if (message.equals("successful in him/her/them losing the attempt") || message.equals("successful in him/her/them winning! :)")) {
                try {
                    synchronized (GuiServer.clientConnection) {
                        //Where servers request name for themes
                        GuiServer.clientConnection.send("I need themes");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("Error sending message about themes in Game");
                }
                gameMessages.clear();
                for (Button button : pressedButtons)
                {
                    button.setDisable(false);
                }
                switchScenes(event, "themes.fxml");
            } else if (message.equals("successful in him/her/them losing the game")) {
                gameMessages.clear();
                for (Button button : pressedButtons)
                {
                    button.setDisable(false);
                }
                switchScenes(event, "LoseGame.fxml");
            } else if (message.equals("successful in him/her/them winning the game! :)")) {
                gameMessages.clear();
                for (Button button : pressedButtons)
                {
                    button.setDisable(false);
                }
                switchScenes(event, "WinGame.fxml");
            } else {
                System.out.println("Weird message in Game: " + message);
            }
            gameMessages.remove(0);
        }
    }

    public void ButtonPressed(ActionEvent event) {
        letter = (String)((Node) event.getSource()).getUserData();
        Button curButton = (Button)event.getSource();
        curButton.setDisable(true);
        pressedButtons.add(curButton);
        
        warningText.setVisible(false);
        synchronized (GuiServer.clientConnection) {
            GuiServer.clientConnection.send("Guess: " + letter);
        }

        try {
            Thread.sleep(10);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error with thread sleep in Game Scene");
        }
        getClientResponse(event);
        /*if (input.getText().length() > 1) {
            warningText.setVisible(true);
            warningText.setText("You need to put only 1 letter");
        } else if (input.getText().isEmpty()) {
            warningText.setVisible(true);
            warningText.setText("You need to put 1 letter");
        } else {
            if (Character.isLetter(input.getText().charAt(0)) || input.getText().equals("-")) {
                synchronized (GuiServer.clientConnection) {
                    GuiServer.clientConnection.send("Guess: " + input.getText().toLowerCase());
                }

                try {
                    Thread.sleep(10);
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("Error with thread sleep in Game Scene");
                }
                getClientResponse(event);
            } else {
                warningText.setVisible(true);
                warningText.setText("You need to type either\na letter or a dash");
            }
        }*/
    }
}
