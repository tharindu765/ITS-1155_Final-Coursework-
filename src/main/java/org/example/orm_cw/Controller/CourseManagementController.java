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
import org.example.orm_cw.bo.Custom.CourseBo;
import org.example.orm_cw.dto.CourseDto;
import org.example.orm_cw.tdm.CourseTm;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class CourseManagementController {

    private CourseBo courseBO = (CourseBo) BoFactory.getBoFactory().getBo(BoTypes.CourseBo);

    @FXML
    private TextField courseIdField;

    @FXML
    private TextField courseNameField;

    @FXML
    private TextField durationField;

    @FXML
    private TextField feeField;

    @FXML
    private TableView<CourseTm> courseTable;

    @FXML
    private TableColumn<CourseTm, Integer> courseIdColumn;

    @FXML
    private TableColumn<CourseTm, String> courseNameColumn;

    @FXML
    private TableColumn<CourseTm, String> durationColumn;

    @FXML
    private TableColumn<CourseTm, Double> feeColumn;

    private ObservableList<CourseTm> courseList;

    public void initialize() {
        // Initialize Table Columns
        courseIdColumn.setCellValueFactory(new PropertyValueFactory<>("courseId"));
        courseNameColumn.setCellValueFactory(new PropertyValueFactory<>("courseName"));
        durationColumn.setCellValueFactory(new PropertyValueFactory<>("duration"));
        feeColumn.setCellValueFactory(new PropertyValueFactory<>("fee"));

        // Load courses from the database
        loadAllCourses();

        // Populate form on table row selection
        courseTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                populateFields(newSelection);
            }
        });
    }

    private void loadAllCourses() {
        courseList = FXCollections.observableArrayList();
        try {
            List<CourseDto> courses = courseBO.loadAll();
            for (CourseDto course : courses) {
                courseList.add(new CourseTm(course.getCourseId(), course.getCourseName(), course.getDuration(), course.getFee()));
            }
            courseTable.setItems(courseList);
        } catch (SQLException | ClassNotFoundException e) {
            showAlert("Error", "Failed to load courses.");
        }
    }

    private void populateFields(CourseTm course) {
        courseIdField.setText(String.valueOf(course.getCourseId()));
        courseNameField.setText(course.getCourseName());
        durationField.setText(course.getDuration());
        feeField.setText(String.valueOf(course.getFee()));
    }

    @FXML
    private void saveCourse(ActionEvent event) {
        if (isValidCourseInput()) {  // Validate input fields before saving
            try {
                // Parse the fee from the input field and get other values
                double fee = Double.parseDouble(feeField.getText());
                String courseName = courseNameField.getText();
                String duration = durationField.getText();

                // Create a new CourseDto object with the entered data
                CourseDto newCourse = new CourseDto(
                        0,  // ID will be set to 0 to indicate a new course
                        courseName,
                        duration,
                        fee
                );

                // Add the new course using the courseBO
                courseBO.add(newCourse);

                // Reload the courses list to show the new course in the table
                loadAllCourses();
                clearFields();  // Clear input fields after saving
                showAlert("Confirmation", "Course saved successfully.");
            } catch (NumberFormatException e) {
                showAlert("Invalid Input", "Please enter valid numeric values for fee.");
            } catch (SQLException | ClassNotFoundException e) {
                showAlert("Error", "Failed to save course.");
            }
        }
    }

    @FXML
    private void updateCourse() {
        CourseTm selectedCourse = courseTable.getSelectionModel().getSelectedItem();
        if (selectedCourse != null) {
            try {
                selectedCourse.setCourseName(courseNameField.getText());
                selectedCourse.setDuration(durationField.getText());
                selectedCourse.setFee(Double.parseDouble(feeField.getText()));

                // Update in the database
                courseBO.update(new CourseDto(selectedCourse.getCourseId(), selectedCourse.getCourseName(), selectedCourse.getDuration(), selectedCourse.getFee()));
                courseTable.refresh(); // Refresh table to show changes
                clearFields();
                showAlert("Confirmation", "Course updated successfully.");
            } catch (NumberFormatException e) {
                showAlert("Invalid Input", "Please enter a valid numeric value for the fee.");
            } catch (SQLException | ClassNotFoundException e) {
                showAlert("Error", "Failed to update course.");
            }
        }
    }

    @FXML
    private void deleteCourse() {
        CourseTm selectedCourse = courseTable.getSelectionModel().getSelectedItem();
        if (selectedCourse != null) {
            try {
                courseBO.delete(String.valueOf(selectedCourse.getCourseId())); // Remove from the database
                courseList.remove(selectedCourse); // Remove from observable list
                clearFields();
                showAlert("Confirmation", "Course deleted successfully.");
            } catch (SQLException | ClassNotFoundException e) {
                showAlert("Error", "Failed to delete course.");
            }
        }
    }

    @FXML
    private void clearFields() {
        courseIdField.clear();
        courseNameField.clear();
        durationField.clear();
        feeField.clear();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
    private boolean isValidCourseInput() {
        if (courseNameField.getText().isEmpty() || durationField.getText().isEmpty() || feeField.getText().isEmpty()) {
            showAlert("Invalid Input", "All fields must be filled.");
            return false;
        }
        try {
            Double.parseDouble(feeField.getText());  // Validate if fee is a valid number
        } catch (NumberFormatException e) {
            showAlert("Invalid Input", "Please enter a valid number for the fee.");
            return false;
        }
        return true;
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
