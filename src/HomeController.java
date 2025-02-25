

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;


public class HomeController {
    
    @FXML
    Label homelabel;

    @FXML
    TextField changepasstextfield;

    @FXML
    Button changepassbutton;

    public void displayName(String getusername){
        homelabel.setText(getusername);
    }

 


    public void changepasshandler(ActionEvent event) throws IOException{

 
        String newpass = changepasstextfield.getText();
        String getusername = homelabel.getText();

        if (DataBaseHandler.changePassword(getusername, newpass)) {

            System.out.println("Change password successful");
        }
        else{
            System.out.println("Change password unsuccessful");
        }
    }


}
