package exercise1;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;


public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Game.class.getResource("game-view.fxml")); //call from view
        Scene scene = new Scene(fxmlLoader.load(), 980, 600); //size of the window
        stage.setTitle("Player and Game"); //title of the window
        stage.setScene(scene);
        stage.show(); //display window
    }

    //Launch the app
    public static void main(String[] args) {
        launch();
    }

}