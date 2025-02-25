


import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;



public class LoginController {

    @FXML
    Label usernamelabel;

    @FXML
    Label passwordlabel;

    @FXML
    TextField usernametextfield;

    @FXML
    TextField passwordtextfield;

    @FXML 
    Button loginbutton;

    private Stage stage;
    private Scene scene;
    private Parent root;

    public void loginbuttonHandler(ActionEvent event) throws IOException{

        String getusername = usernametextfield.getText();
        String getpass = passwordtextfield.getText();

        DataBaseHandler.validateLogin(getusername, getpass);

        if (DataBaseHandler.validateLogin(getusername, getpass)) {

            System.out.println("Login successful");

            FXMLLoader loader = new FXMLLoader(getClass().getResource("Dashboard.fxml"));

            root = loader.load();
            
            HomeController homeController = loader.getController();

            // Pass username from textfield to displayName() method
            homeController.displayName(getusername);

            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
        else{
            System.out.println("Login unsuccessful");
        }
    }


}