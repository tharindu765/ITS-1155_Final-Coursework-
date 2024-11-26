package org.example.orm_cw.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.example.orm_cw.HelloApplication;
import org.example.orm_cw.bo.BoFactory;
import org.example.orm_cw.bo.BoTypes;
import org.example.orm_cw.bo.Custom.UserBo;
import org.example.orm_cw.dto.UserDto;
import org.example.orm_cw.tdm.UserTm;
import org.example.orm_cw.utill.PasswordUtil;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class UserManagementController {

    private UserBo userBo = (UserBo) BoFactory.getBoFactory().getBo(BoTypes.UserBo);

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private ComboBox<String> roleComboBox;

    @FXML
    private TableView<UserTm> userTable;

    @FXML
    private TableColumn<UserTm, Integer> idColumn;

    @FXML
    private TableColumn<UserTm, String> usernameColumn;

    @FXML
    private TableColumn<UserTm, String> roleColumn;

    private ObservableList<UserTm> userList;

    @FXML
    public void initialize() {
        if (!"Admin".equals(LoginFromController.currentUserRole)) {
            showAlert("Access Denied", "You do not have permission to access this page.");
            disablePage();
        }

        // Set up table columns
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        roleColumn.setCellValueFactory(new PropertyValueFactory<>("role"));

        // Initialize role ComboBox with only two roles
        roleComboBox.setItems(FXCollections.observableArrayList("Admin", "Admissions Coordinator"));

        // Load all users
        loadAllUsers();

        // Populate form on table row selection
        userTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                populateFields(newSelection);
            }
        });
    }

    private void disablePage() {
        usernameField.setDisable(true);
        passwordField.setDisable(true);
        roleComboBox.setDisable(true);
        userTable.setDisable(true);
    }

    private void loadAllUsers() {
        userList = FXCollections.observableArrayList();
        try {
            List<UserDto> users = userBo.loadAll();
            for (UserDto user : users) {
                userList.add(new UserTm(user.getId(), user.getUsername(), user.getPassword(), user.getRole()));
            }
            userTable.setItems(userList);
        } catch (SQLException | ClassNotFoundException e) {
            showAlert("Error", "Failed to load users.");
        }
    }

    private void populateFields(UserTm user) {
        usernameField.setText(user.getUsername());
        passwordField.setText(user.getPassword()); // Optionally show hashed password or a placeholder
        roleComboBox.setValue(user.getRole());
    }

    @FXML
    private void saveUser() {
        if (isValidInput()) {
            try {
                // Hash the password before saving
                String hashedPassword = PasswordUtil.hashPassword(passwordField.getText());

                UserDto newUser = new UserDto(
                        0, // Auto-generated ID
                        usernameField.getText(),
                        hashedPassword, // Store the hashed password
                        roleComboBox.getValue()
                );
                userBo.add(newUser);
                userList.add(new UserTm(newUser.getId(), newUser.getUsername(), newUser.getPassword(), newUser.getRole()));
                clearFields();
                showAlert("Success", "User saved successfully.");
            } catch (SQLException | ClassNotFoundException e) {
                showAlert("Error", "Failed to save user.");
            }
        }
    }

    @FXML
    private void updateUser() {
        UserTm selectedUser = userTable.getSelectionModel().getSelectedItem();
        if (selectedUser != null && isValidInput()) {
            try {
                // Hash the password before updating
                String hashedPassword = PasswordUtil.hashPassword(passwordField.getText());

                selectedUser.setUsername(usernameField.getText());
                selectedUser.setRole(roleComboBox.getValue());
                selectedUser.setPassword(hashedPassword); // Set the hashed password

                userBo.update(new UserDto(selectedUser.getId(), selectedUser.getUsername(), hashedPassword, selectedUser.getRole()));
                userTable.refresh();
                clearFields();
                showAlert("Success", "User updated successfully.");
            } catch (SQLException | ClassNotFoundException e) {
                showAlert("Error", "Failed to update user.");
            }
        }
    }

    @FXML
    private void deleteUser() {
        UserTm selectedUser = userTable.getSelectionModel().getSelectedItem();
        if (selectedUser != null) {
            try {
                userBo.delete(String.valueOf(selectedUser.getId()));
                userList.remove(selectedUser);
                clearFields();
                showAlert("Success", "User deleted successfully.");
            } catch (SQLException | ClassNotFoundException e) {
                showAlert("Error", "Failed to delete user.");
            }
        }
    }

    @FXML
    private void clearFields() {
        usernameField.clear();
        passwordField.clear();
        roleComboBox.setValue(null);
    }

    private boolean isValidInput() {
        if (usernameField.getText().isEmpty() || passwordField.getText().isEmpty() || roleComboBox.getValue() == null) {
            showAlert("Input Error", "All fields must be filled.");
            return false;
        }

        String password = passwordField.getText();
        if (password.length() < 6) {
            showAlert("Password Error", "Password must be at least 6 characters long.");
            return false;
        }
        return true;
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
    @FXML
    private void goBack(ActionEvent event) {
        try {
            // Navigate back to Dashboard
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Dashboard.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage currentStage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            currentStage.setScene(scene);
            currentStage.setTitle("Dashboard");
            currentStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Failed to navigate back to the Dashboard.");
        }
    }
}
