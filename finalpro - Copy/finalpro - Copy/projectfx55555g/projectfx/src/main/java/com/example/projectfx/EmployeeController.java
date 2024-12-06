package com.example.projectfx;

import com.example.DataBaseConnection;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;

import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;

public class EmployeeController {

    @FXML
    private Button Customerbtn;

    @FXML
    private Button Homebtn;

    @FXML
    private Button Ordersbtn;

    @FXML
    private Button productbtn;

    @FXML
    private Button Employeebtn;

    @FXML
    private Button deliverybtn;

    @FXML
    private Button signOutbtn;

    @FXML
    private Label helloLabel;

    @FXML
    private TextField BirthDatetextField;

    @FXML
    private TextField Emailtxt;

    @FXML
    private TextField IDtextField;

    @FXML
    private TextField NametextField;

    @FXML
    private TextField SalarytextField;

    @FXML
    private TextField addresstxt;

    @FXML
    private TextField hiredatetxtfield;

    @FXML
    private TextField passtxt;

    @FXML
    private TextField phonetxt;

    @FXML
    private TextField positiontxt;

    @FXML
    private TextField usernametxt;

    @FXML
    private TextField workinghourstxtf;

    private Employee employee;


    @FXML
    public void initialize() {
        helloLabel.setText(UserSession.getInstance().getUsername());
        loadEmployeeData(UserSession.getInstance().getUsername());
        bindTextFieldsToEmployee();
    }
    // This method loads employee data from the database
    private void loadEmployeeData(String username) {
        DataBaseConnection connectNow = new DataBaseConnection();
        Connection connectDB = connectNow.getConnection();

        try {
            String query = "SELECT * FROM \"Employee\" WHERE \"User Name\" = ?";
            PreparedStatement preparedStatement = connectDB.prepareStatement(query);
            preparedStatement.setString(1, username);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                // Create a new Employee object with data from the database
                employee = new Employee(
                        resultSet.getInt("Employee ID"),
                        resultSet.getString("Employee Name"),
                        resultSet.getDate("Birth Date").toLocalDate(),
                        resultSet.getInt("Employee ID"),
                        resultSet.getString("Address"),
                        resultSet.getString("Email"),
                        resultSet.getString("Phone Number"),
                        resultSet.getString("User Name"),
                        resultSet.getString("Password"),
                        resultSet.getString("Job Position"),
                        resultSet.getDouble("Salary"),
                        resultSet.getInt("Working Hours"),
                        resultSet.getDate("Hire Date").toLocalDate()
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR, "Failed to load employee data: " + e.getMessage());
            alert.showAndWait();
        }
    }

    private void bindTextFieldsToEmployee() {
        if (employee != null) {
            IDtextField.textProperty().bind(employee.idProperty().asString());
            NametextField.textProperty().bind(employee.nameProperty());
            SalarytextField.textProperty().bind(employee.salaryProperty().asString());
            addresstxt.textProperty().bind(employee.addressProperty());
            passtxt.textProperty().bind(employee.passwordProperty());
            usernametxt.textProperty().bind(employee.usernameProperty());
            positiontxt.textProperty().bind(employee.jobPositionProperty());
            phonetxt.textProperty().bind(employee.phoneProperty());
            BirthDatetextField.textProperty().bind(employee.birthdateDateProperty().asString()); // assuming age in years
            hiredatetxtfield.textProperty().bind(employee.hireDateProperty().asString());
            workinghourstxtf.textProperty().bind(employee.workingHoursProperty().asString());
            Emailtxt.textProperty().bind(employee.emailProperty());
        }
    }



    @FXML
    public void HomebtnOnAction(ActionEvent event) {
        try {
            // Load the FXML file for the dashboard
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Dashboard.fxml"));
            Parent dashboardRoot = fxmlLoader.load();

            // Create a new stage and scene for the dashboard
            Stage dashboardStage = new Stage();
            dashboardStage.setTitle("Dashboard");
            dashboardStage.setScene(new Scene(dashboardRoot));
            dashboardStage.setResizable(false);
            // Show the new stage (dashboard window)
            dashboardStage.show();

            // Close the current window if desired
            Stage currentStage = (Stage) Homebtn.getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            e.printStackTrace(); // Print any errors that occur
        }
    }
    @FXML
    public void EmployeebtnOnAction(ActionEvent event) {
        try {
            // Load the FXML file for the dashboard
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Employee.fxml"));
            Parent dashboardRoot = fxmlLoader.load();

            // Create a new stage and scene for the dashboard
            Stage dashboardStage = new Stage();
            dashboardStage.setTitle("Employee");
            dashboardStage.setScene(new Scene(dashboardRoot));
            dashboardStage.setResizable(false);
            // Show the new stage (dashboard window)
            dashboardStage.show();

            // Close the current window if desired
            Stage currentStage = (Stage) Employeebtn.getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            e.printStackTrace(); // Print any errors that occur
        }
    }



    @FXML
    public void OrdersbtnOnAction(ActionEvent event) {
        try {
            // Load the FXML file for the dashboard
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Orders.fxml"));
            Parent dashboardRoot = fxmlLoader.load();

            // Create a new stage and scene for the dashboard
            Stage dashboardStage = new Stage();
            dashboardStage.setTitle("Orders");
            dashboardStage.setScene(new Scene(dashboardRoot));
            dashboardStage.setResizable(false);
            // Show the new stage (dashboard window)
            dashboardStage.show();

            // Close the current window if desired
            Stage currentStage = (Stage) Ordersbtn.getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            e.printStackTrace(); // Print any errors that occur
        }

    }
    @FXML
    public void productbtnOnAction(ActionEvent event) {
        try {
            // Load the FXML file for the dashboard
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("products.fxml"));
            Parent dashboardRoot = fxmlLoader.load();

            // Create a new stage and scene for the dashboard
            Stage dashboardStage = new Stage();
            dashboardStage.setTitle("Orders");
            dashboardStage.setScene(new Scene(dashboardRoot));
            dashboardStage.setResizable(false);
            // Show the new stage (dashboard window)
            dashboardStage.show();

            // Close the current window if desired
            Stage currentStage = (Stage) productbtn.getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            e.printStackTrace(); // Print any errors that occur
        }

    }
    public void CustomerbtnOnAction(ActionEvent event){

        try {
            // Load the FXML file for the dashboard
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Customer.fxml"));
            Parent dashboardRoot = fxmlLoader.load();

            // Create a new stage and scene for the dashboard
            Stage dashboardStage = new Stage();
            dashboardStage.setTitle("Customer");
            dashboardStage.setScene(new Scene(dashboardRoot));
            dashboardStage.setResizable(false);
            // Show the new stage (dashboard window)
            dashboardStage.show();

            // Close the current window if desired
            Stage currentStage = (Stage) Customerbtn.getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            e.printStackTrace(); // Print any errors that occur
        }

    }

    public void DelivrybtnOnAction(ActionEvent event){
        try {
            // Load the FXML file for the dashboard
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Delivery.fxml"));
            Parent dashboardRoot = fxmlLoader.load();

            // Create a new stage and scene for the dashboard
            Stage dashboardStage = new Stage();
            dashboardStage.setTitle("Delivery");
            dashboardStage.setScene(new Scene(dashboardRoot));
            dashboardStage.setResizable(false);
            // Show the new stage (dashboard window)
            dashboardStage.show();

            // Close the current window if desired
            Stage currentStage = (Stage) deliverybtn.getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            e.printStackTrace(); // Print any errors that occur
        }

    }
    public void signOutbtnOnAction(ActionEvent event){
        try {
            // Load the FXML file for the dashboard
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("login.fxml"));
            Parent dashboardRoot = fxmlLoader.load();

            // Create a new stage and scene for the dashboard
            Stage dashboardStage = new Stage();
            dashboardStage.setTitle("login");
            dashboardStage.setScene(new Scene(dashboardRoot));
            dashboardStage.setResizable(false);
            // Show the new stage (dashboard window)
            dashboardStage.show();

            // Close the current window if desired
            Stage currentStage = (Stage) signOutbtn.getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            e.printStackTrace(); // Print any errors that occur
        }
    }

    DataBaseConnection connectNow = new DataBaseConnection();
    Connection connectDB = connectNow.getConnection();
}