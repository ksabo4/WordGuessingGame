import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class GuiServer extends Application {
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		
		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent t) {
                Platform.exit();
                System.exit(0);
            }
        });

		//Loads scene for server
		FXMLLoader fxmlLoader = new FXMLLoader(GuiServer.class.getResource("LoadScreen.fxml"));
		Scene scene1 = new Scene(fxmlLoader.load(), 700, 500);
		primaryStage.setTitle("Server Program");
		primaryStage.setScene(scene1);
		primaryStage.show();

		GuessWord guessWord = new GuessWord("hello");
		System.out.println(guessWord.updateGuesses('h'));
		System.out.println(guessWord.updateGuesses('e'));
		System.out.println(guessWord.updateGuesses('l'));
		System.out.println(guessWord.updateGuesses('a'));
		System.out.println(guessWord.updateGuesses('d'));
		System.out.println(guessWord.updateGuesses('o'));
	}
}
