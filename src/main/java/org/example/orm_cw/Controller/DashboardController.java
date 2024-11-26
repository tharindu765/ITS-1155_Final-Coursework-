package org.example.orm_cw.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.example.orm_cw.HelloApplication;
import org.example.orm_cw.bo.BoFactory;
import org.example.orm_cw.bo.BoTypes;
import org.example.orm_cw.bo.Custom.CourseBo;
import org.example.orm_cw.bo.Custom.StudentBo;

import java.io.IOException;
import java.sql.SQLException;

public class DashboardController {

    @FXML
    private Label studentCountLabel;
    @FXML
    private Label courseCountLabel;

    // BO instances for Student and Course
    private StudentBo studentBO = (StudentBo) BoFactory.getBoFactory().getBo(BoTypes.StudentBo);
    private CourseBo courseBO = (CourseBo) BoFactory.getBoFactory().getBo(BoTypes.CourseBo);

    @FXML
    public void initialize() {
        loadCounts();
    }

    private void loadCounts() {
        try {
            // Retrieve student and course counts
            int studentCount = studentBO.getCount();
            int courseCount = courseBO.getCount();

            // Display counts on the dashboard
            studentCountLabel.setText(String.valueOf(studentCount));
            courseCountLabel.setText(String.valueOf(courseCount));
        } catch (SQLException | ClassNotFoundException e) {
            // Handle exceptions and show an error message
            e.printStackTrace();
            studentCountLabel.setText("Error");
            courseCountLabel.setText("Error");
        }
    }

    @FXML
    private void goToAddStudentForm() {
        navigateTo("StudentManagement.fxml", "Add Student");
    }

    @FXML
    private void goToAddCourseForm() {
        navigateTo("CourseManagement.fxml", "Add Course");
    }

    @FXML
    private void logout() {
        navigateTo("Login.fxml", "Login");
    }

    @FXML
    private void showStudentList() {
        navigateTo("StudentManagement.fxml", "Add Student");
    }

    @FXML
    private void showCourseList() {
        navigateTo("CourseManagement.fxml", "Add Course");
    }

    @FXML
    private void userManage(ActionEvent actionEvent) {
        if ("Admin".equals(LoginFromController.currentUserRole)) {
            navigateTo("UserManagement.fxml", "User Management");
        } else {
            showAlert("Access Denied", "You do not have permission to access User Management.");
        }
    }
    private void navigateTo(String fxmlFile, String title) {
        try {
            System.out.println("Navigating to: " + fxmlFile);
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource(fxmlFile));
            Scene scene = new Scene(fxmlLoader.load());
            Stage currentStage = (Stage) studentCountLabel.getScene().getWindow();
            currentStage.setScene(scene);
            currentStage.setTitle(title);
            currentStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Failed to navigate to " + fxmlFile + ".");
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
