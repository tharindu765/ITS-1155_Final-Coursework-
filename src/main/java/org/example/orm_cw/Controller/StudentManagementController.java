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
import org.example.orm_cw.bo.Custom.StudentBo;
import org.example.orm_cw.dto.CourseDto;
import org.example.orm_cw.dto.StudentDto;
import org.example.orm_cw.tdm.StudentTm;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class StudentManagementController {

    private StudentBo studentBO = (StudentBo) BoFactory.getBoFactory().getBo(BoTypes.StudentBo);
    private CourseBo courseBO = (CourseBo) BoFactory.getBoFactory().getBo(BoTypes.CourseBo);

    @FXML
    private TextField nameField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField phoneField;
    @FXML
    private Label totalPaymentLabel;
    @FXML
    private ListView<String> availableCoursesListView; // For available courses
    @FXML
    private ListView<String> selectedCoursesListView; // For selected courses

    @FXML
    private TableView<StudentTm> studentTable;
    @FXML
    private TableColumn<StudentTm, Integer> studentIdColumn;
    @FXML
    private TableColumn<StudentTm, String> nameColumn;
    @FXML
    private TableColumn<StudentTm, String> emailColumn;
    @FXML
    private TableColumn<StudentTm, String> coursesColumn;
    @FXML
    private TableColumn<StudentTm, String> phoneColumn;

    private ObservableList<StudentTm> studentList;

    public void initialize() {
        // Initialize Table Columns
        studentIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        coursesColumn.setCellValueFactory(new PropertyValueFactory<>("course"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));

        // Load students from the database
        loadAllStudents();

        // Populate available courses
        loadAvailableCourses();

        // Populate form on table row selection
        studentTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                populateFields(newSelection);
            }
        });
    }

    private void loadAvailableCourses() {
        try {
            List<CourseDto> courses = courseBO.loadAll();
            for (CourseDto course : courses) {
                availableCoursesListView.getItems().add(course.getCourseName());
            }
        } catch (SQLException | ClassNotFoundException e) {
            showAlert("Error", "Failed to load courses.");
        }
    }

    private void loadAllStudents() {
        studentList = FXCollections.observableArrayList();
        try {
            List<StudentDto> students = studentBO.loadAll();
            for (StudentDto student : students) {
                studentList.add(new StudentTm(student.getId(), student.getName(), student.getEmail(), student.getCourse(), student.getPhone()));
            }
            studentTable.setItems(studentList);
        } catch (SQLException | ClassNotFoundException e) {
            showAlert("Error", "Failed to load students.");
        }
    }

    private void populateFields(StudentTm student) {
        nameField.setText(student.getName());
        emailField.setText(student.getEmail());
        phoneField.setText(student.getPhone());
        selectedCoursesListView.getItems().clear();
        // Add selected courses to the list
        String[] courses = student.getCourse().split(", ");
        for (String course : courses) {
            selectedCoursesListView.getItems().add(course);
        }

        // Update total payment when a student is selected
        updateTotalPayment();
    }

    @FXML
    private void addCourseToStudent(ActionEvent event) {
        String selectedCourse = availableCoursesListView.getSelectionModel().getSelectedItem();
        if (selectedCourse != null && !selectedCoursesListView.getItems().contains(selectedCourse)) {
            selectedCoursesListView.getItems().add(selectedCourse); // Add to selected list
            updateTotalPayment();  // Update total payment after adding a course
        }
    }

    @FXML
    private void removeCourseFromStudent(ActionEvent event) {
        String selectedCourse = selectedCoursesListView.getSelectionModel().getSelectedItem();
        if (selectedCourse != null) {
            selectedCoursesListView.getItems().remove(selectedCourse); // Remove from selected list
            updateTotalPayment();  // Update total payment after removing a course
        }
    }

    @FXML
    private void saveStudent(ActionEvent event) {
        if (isValidInput()) {
            try {
                // Create the student DTO and set courses
                StudentDto newStudent = new StudentDto(
                        generateStudentId(),
                        nameField.getText(),
                        emailField.getText(),
                        String.join(", ", selectedCoursesListView.getItems()), // Join selected courses
                        phoneField.getText()
                );
                studentBO.add(newStudent);
                studentList.add(new StudentTm(newStudent.getId(), newStudent.getName(), newStudent.getEmail(), newStudent.getCourse(), newStudent.getPhone()));
                clearFields(event);
                showAlert("Confirmation", "Student saved successfully.");
            } catch (SQLException | ClassNotFoundException e) {
                showAlert("Error", "Failed to save student.");
            }
        }
    }

    @FXML
    private void updateStudent(ActionEvent event) {
        StudentTm selectedStudent = studentTable.getSelectionModel().getSelectedItem();
        if (selectedStudent != null && isValidInput()) {
            try {
                selectedStudent.setName(nameField.getText());
                selectedStudent.setEmail(emailField.getText());
                selectedStudent.setPhone(phoneField.getText());
                selectedStudent.setCourse(String.join(", ", selectedCoursesListView.getItems())); // Join selected courses

                studentBO.update(new StudentDto(selectedStudent.getId(), selectedStudent.getName(), selectedStudent.getEmail(), selectedStudent.getCourse(), selectedStudent.getPhone()));
                studentTable.refresh();
                clearFields(event);
                showAlert("Confirmation", "Student updated successfully.");
            } catch (SQLException | ClassNotFoundException e) {
                showAlert("Error", "Failed to update student.");
            }
        }
    }

    @FXML
    private void deleteStudent(ActionEvent event) {
        StudentTm selectedStudent = studentTable.getSelectionModel().getSelectedItem();
        if (selectedStudent != null) {
            try {
                studentBO.delete(String.valueOf(selectedStudent.getId()));
                studentList.remove(selectedStudent);
                clearFields(event);
                showAlert("Confirmation", "Student deleted successfully.");
            } catch (SQLException | ClassNotFoundException e) {
                showAlert("Error", "Failed to delete student.");
            }
        }
    }

    @FXML
    private void clearFields(ActionEvent event) {
        nameField.clear();
        emailField.clear();
        phoneField.clear();
        selectedCoursesListView.getItems().clear();
    }

    private boolean isValidInput() {
        // Validate Name (Only letters and spaces, minimum 2 characters)
        if (!nameField.getText().matches("^[A-Za-z ]{2,}$")) {
            showAlert("Input Error", "Name must contain only letters and spaces, and be at least 2 characters long.");
            return false;
        }

        // Validate Email (Basic email regex for format validation)
        if (!emailField.getText().matches("^[\\w._%+-]+@[\\w.-]+\\.[A-Za-z]{2,6}$")) {
            showAlert("Input Error", "Please enter a valid email address.");
            return false;
        }

        // Validate Phone Number (10 digits, can include dashes or spaces for formatting)
        if (!phoneField.getText().matches("^(\\d{10}|\\d{3}[-\\s]\\d{3}[-\\s]\\d{4})$")) {
            showAlert("Input Error", "Please enter a valid 10-digit phone number. You may use dashes or spaces for formatting.");
            return false;
        }

        return true;
    }

    private int generateStudentId() {
        return studentList.size() + 1; // Temporary logic for generating student ID
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void updateTotalPayment() {
        double totalPayment = 0;

        for (String course : selectedCoursesListView.getItems()) {
            totalPayment += getCourseFee(course);
        }

        totalPaymentLabel.setText(String.format("Total Payment: %.2f", totalPayment));
    }

    private double getCourseFee(String courseName) {
        try {
            CourseDto course = courseBO.getCourseByName(courseName);
            if (course != null) {
                return course.getFee();
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            showAlert("Error", "Failed to fetch course fee.");
        }

        return 0;
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
