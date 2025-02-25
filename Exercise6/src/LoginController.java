import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LoginController {

    @FXML
    private TextField usernametextfield;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button loginbutton;

    // ✅ Ensure these variables are declared **inside the class**, not inside the method
    private static final String DB_URL = "jdbc:mysql://localhost:3306/login_db?serverTimezone=UTC";
    private static final String DB_USER = "root"; // Change to your MySQL username
    private static final String DB_PASSWORD = "password"; // Change to your MySQL password

    @FXML
    private void loginbuttonHandler(ActionEvent event) {
        String username = usernametextfield.getText();
        String password = passwordField.getText();

        if (validateLogin(username, password)) {
            System.out.println("Login Successful!");
            openDashboard();
        } else {
            showAlert("Login Failed", "Invalid Username or Password");
        }
    }

    private boolean validateLogin(String username, String password) {
        String query = "SELECT * FROM users WHERE username = ? AND password = ?";
        
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();

            return rs.next(); // ✅ Returns true if user exists

        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Database Error", "Could not connect to the database.");
            return false;
        }
    }

    private void openDashboard() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Dashboard.fxml"));
            Scene scene = new Scene(fxmlLoader.load());

            Stage stage = new Stage();
            stage.setTitle("Dashboard");
            stage.setScene(scene);
            stage.show();

            // ✅ Close login window after opening dashboard
            Stage currentStage = (Stage) usernametextfield.getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Failed to open the Dashboard.");
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
