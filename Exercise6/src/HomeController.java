import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.*;

public class HomeController {

    @FXML
    private TableView<User> userTable;
    @FXML
    private TableColumn<User, String> colUsername;
    @FXML
    private TableColumn<User, String> colPassword;
    @FXML
    private TextField usernameField;
    @FXML
    private TextField passwordField;
    @FXML
    private TextField changepasstextfield;
    @FXML
    private Button changepassbutton;
    @FXML
    private Label homelabel2;

    private ObservableList<User> userList = FXCollections.observableArrayList();

    public void initialize() {
        colUsername.setCellValueFactory(new PropertyValueFactory<>("username"));
        colPassword.setCellValueFactory(new PropertyValueFactory<>("password"));

        loadUsers();
    }

    private void loadUsers() {
        userList.clear();
        String query = "SELECT * FROM users";

        try (Connection conn = DataBaseHandler.getDBConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                userList.add(new User(rs.getString("UserName"), rs.getString("Password")));
            }
            userTable.setItems(userList);

        } catch (SQLException e) {
            showAlert("Database Error", "Failed to load users: " + e.getMessage());
        }
    }

    @FXML
    private void createUser() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (username.isEmpty() || password.isEmpty()) {
            showAlert("Error", "Fields cannot be empty!");
            return;
        }

        String query = "INSERT INTO users (UserName, Password) VALUES (?, ?)";
        try (Connection conn = DataBaseHandler.getDBConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, username);
            pstmt.setString(2, password); // Consider hashing this
            pstmt.executeUpdate();
            loadUsers();
            clearFields();

        } catch (SQLException e) {
            showAlert("Database Error", "Failed to create user: " + e.getMessage());
        }
    }

    @FXML
    private void updateUser() {
        User selectedUser = userTable.getSelectionModel().getSelectedItem();
        if (selectedUser == null) {
            showAlert("Error", "No user selected!");
            return;
        }

        String query = "UPDATE users SET UserName=?, Password=? WHERE UserName=?";
        try (Connection conn = DataBaseHandler.getDBConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, usernameField.getText());
            pstmt.setString(2, passwordField.getText()); // Consider hashing this
            pstmt.setString(3, selectedUser.getUsername());
            pstmt.executeUpdate();
            loadUsers();
            clearFields();

        } catch (SQLException e) {
            showAlert("Database Error", "Failed to update user: " + e.getMessage());
        }
    }

    @FXML
    private void deleteUser() {
        User selectedUser = userTable.getSelectionModel().getSelectedItem();
        if (selectedUser == null) {
            showAlert("Error", "No user selected!");
            return;
        }

        String query = "DELETE FROM users WHERE UserName=?";
        try (Connection conn = DataBaseHandler.getDBConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, selectedUser.getUsername());
            pstmt.executeUpdate();
            loadUsers();
            clearFields();

        } catch (SQLException e) {
            showAlert("Database Error", "Failed to delete user: " + e.getMessage());
        }
    }

    @FXML
    private void changepasshandler(ActionEvent event) {
        User selectedUser = userTable.getSelectionModel().getSelectedItem();
        String newPassword = changepasstextfield.getText();

        if (selectedUser == null || newPassword.isEmpty()) {
            showAlert("Error", "Select a user and enter a new password!");
            return;
        }

        String query = "UPDATE users SET Password=? WHERE UserName=?";
        try (Connection conn = DataBaseHandler.getDBConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, newPassword);
            pstmt.setString(2, selectedUser.getUsername());
            pstmt.executeUpdate();
            loadUsers();
            changepasstextfield.clear();

        } catch (SQLException e) {
            showAlert("Database Error", "Failed to change password: " + e.getMessage());
        }
    }

    private void clearFields() {
        usernameField.clear();
        passwordField.clear();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
