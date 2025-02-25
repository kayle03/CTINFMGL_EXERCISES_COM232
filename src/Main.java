import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        //as a main page
        Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));

        //title of main page
        primaryStage.setTitle("Log in");

        //window size
        primaryStage.setScene(new Scene(root, 600, 400));

        //display 
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}