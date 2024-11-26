package org.example.orm_cw.Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.orm_cw.HelloApplication;
import org.example.orm_cw.bo.BoFactory;
import org.example.orm_cw.bo.BoTypes;
import org.example.orm_cw.bo.Custom.UserBo;
import org.example.orm_cw.dto.UserDto;
import org.example.orm_cw.utill.PasswordUtil;

import java.io.IOException;
import java.sql.SQLException;

public class LoginFromController {

    private UserBo userBO = (UserBo) BoFactory.getBoFactory().getBo(BoTypes.UserBo); // Initialize UserBo

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    public static String currentUserRole; // Store logged-in user's role globally

    @FXML
    private void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (authenticate(username, password)) {
            try {
                UserDto user = userBO.getUserByUsername(username);
                currentUserRole = user.getRole();

                FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Dashboard.fxml"));
                Scene scene = new Scene(fxmlLoader.load());
                Stage currentStage = (Stage) usernameField.getScene().getWindow();
                currentStage.setScene(scene);
                currentStage.setTitle("Dashboard");
                currentStage.show();
            } catch (IOException | SQLException | ClassNotFoundException e) {
                showAlert("Error", "Failed to load the dashboard.");
            }
        } else {
            showAlert("Login Failed", "Invalid username or password.");
        }
    }

    @FXML
    private void handleForgotPassword() {
        // Handle forgot password action
        showAlert("Forgot Password", "Please contact admin to reset your password.");
    }

    private boolean authenticate(String username, String password) {
        try {
            UserDto user = userBO.getUserByUsername(username);
            if (user != null && PasswordUtil.checkPassword(password, user.getPassword())) {
                return true; // Authentication successful
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            showAlert("Error", "An error occurred during authentication.");
        }
        return false; // Authentication failed
    }


    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
