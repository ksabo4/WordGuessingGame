import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class GuiServer extends Application{

	public static Client clientConnection = null;
	public static Stage curStage;

	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}


	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent t) {
                Platform.exit();
                System.exit(0);
            }
        });


		FXMLLoader fxmlLoader = new FXMLLoader(GuiServer.class.getResource("LoadScreen.fxml"));
		Scene scene1 = new Scene(fxmlLoader.load(), 700, 500);
		primaryStage.setTitle("Word Guessing Game");
		primaryStage.setScene(scene1);
		primaryStage.show();
		curStage = primaryStage;
	}
}
