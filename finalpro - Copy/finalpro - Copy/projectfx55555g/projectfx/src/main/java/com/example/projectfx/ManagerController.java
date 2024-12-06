package com.example.projectfx;

import com.example.DataBaseConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;

import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ManagerController {

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

    //table
    @FXML
    private TableView<Employee> Employeetable;

    @FXML
    private TableColumn<Employee,String > Addresscol;

    @FXML
    private TableColumn<Employee, Integer > Agecol;

    @FXML
    private TableColumn<Employee, String> Emailcol;

    @FXML
    private TableColumn<Employee,LocalDate> HireDatecol;

    @FXML
    private TableColumn<Employee, Integer> IDcol;

    @FXML
    private TableColumn<Employee, String> JobPositioncol;

    @FXML
    private TableColumn<Employee, String> Namecol;

    @FXML
    private TableColumn<Employee, String> Passcol;

    @FXML
    private TableColumn<Employee, String> Phonecol;

    @FXML
    private TableColumn<Employee, Double> Salarycol;

    @FXML
    private TableColumn<Employee, String> UserNamecol;

    @FXML
    private TableColumn<Employee, Integer> WorkingHourscol;

    //--------------------
    //buttons
    @FXML
    private Button add;

    @FXML
    private Button clear;

    @FXML
    private Button delete;

    @FXML
    private Button update;
    //for textfields
    @FXML
    private TextField idtext;
    @FXML
    private TextField nametext;
    @FXML
    private DatePicker birthdtext;
    @FXML
    private TextField salarytext;
    @FXML
    private TextField whtext;
    @FXML
    private DatePicker hiredatetext;
    @FXML
    private TextField positiontext;
    @FXML
    private TextField phonetext;
    @FXML
    private TextField emailtext;
    @FXML
    private TextField addresstext;
    @FXML
    private TextField usertext;
    @FXML
    private TextField passwordtext;

    //for search combo box
    private final String[] searchele = {"ID","Name","Salary"};
    @FXML
    private ComboBox<String> searchby;
    @FXML
    private TextField searchbox;


    DataBaseConnection connectNow = new DataBaseConnection();
    Connection connectDB = connectNow.getConnection();


    @FXML
    public void initialize() {
        searchbyOnAction(); //for searchby
        loadEmployees("SELECT \"Employee ID\", \"Employee Name\", \"Birth Date\", EXTRACT(YEAR FROM AGE(\"Birth Date\")) AS age, \"Salary\", \"Working Hours\", \"Email\", \"User Name\", \"Password\", \"Address\", \"Phone Number\", \"Job Position\", \"Hire Date\" FROM \"Employee\" ");
        // Bind each column to the corresponding property in Employee
        IDcol.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
        Namecol.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        Agecol.setCellValueFactory(cellData -> cellData.getValue().ageProperty().asObject());
        Addresscol.setCellValueFactory(cellData -> cellData.getValue().addressProperty());
        Emailcol.setCellValueFactory(cellData -> cellData.getValue().emailProperty());
        Phonecol.setCellValueFactory(cellData -> cellData.getValue().phoneProperty());
        UserNamecol.setCellValueFactory(cellData -> cellData.getValue().usernameProperty());
        Passcol.setCellValueFactory(cellData -> cellData.getValue().passwordProperty());
        JobPositioncol.setCellValueFactory(cellData -> cellData.getValue().jobPositionProperty());
        Salarycol.setCellValueFactory(cellData -> cellData.getValue().salaryProperty().asObject());
        WorkingHourscol.setCellValueFactory(cellData -> cellData.getValue().workingHoursProperty().asObject());
        HireDatecol.setCellValueFactory(cellData -> (cellData.getValue().hireDateProperty()));
    }
    private void loadEmployees(String sql) {
        String url = "jdbc:postgresql://localhost:5432/postgres";
        String user = "postgres";
        String password = "123456";

        ObservableList<Employee> employeeList = FXCollections.observableArrayList();

        try (Connection connection = DriverManager.getConnection(url, user, password);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
//            "SELECT \"Employee ID\", \"Employee Name\", \"Birth Date\", EXTRACT(YEAR FROM AGE(\"Birth Date\")) AS age, \"Salary\", \"Working Hours\", \"Email\", \"User Name\", \"Password\", \"Address\", \"Phone Number\", \"Job Position\", \"Hire Date\" FROM \"Employee\" ")) {
//
            while (resultSet.next()) {
                int id = resultSet.getInt("Employee ID");
                String name = resultSet.getString("Employee Name");
                LocalDate birthdate = resultSet.getDate("Birth Date").toLocalDate();
                int age = resultSet.getInt("age");
                double salary = resultSet.getDouble("Salary");
                int workingHours = resultSet.getInt("Working Hours");
                String email = resultSet.getString("Email");
                String username = resultSet.getString("User Name");
                String passwordDb = resultSet.getString("Password");
                String address = resultSet.getString("Address");
                String phone = resultSet.getString("Phone Number");
                String jobPosition = resultSet.getString("Job Position");
                LocalDate hireDate = resultSet.getDate("Hire Date").toLocalDate();

                employeeList.add(new Employee(id, name, birthdate,age, address, email, phone, username, passwordDb, jobPosition, salary, workingHours, hireDate));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // You could show an alert to the user here
            Alert alert = new Alert(Alert.AlertType.ERROR, "Database error: " + e.getMessage());
            alert.showAndWait();
        } catch (Exception e) {
            e.printStackTrace(); // Catch any other exceptions
        }

        Employeetable.setItems(employeeList);
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

    //buttons mothods
    public void addOnAction(ActionEvent event) {
        try{

//            LocalDate brd = birthdtext.getValue();
//            Date sqlDate= Date.valueOf(brd);
//            LocalDate hrd = hiredatetext.getValue();
//            Date sqlDate2= Date.valueOf(brd);

            String getMaxIdQuery = "SELECT MAX(\"Employee ID\") FROM \"Employee\" ;";
            Statement stmt = connectDB.createStatement();
            ResultSet rs = stmt.executeQuery(getMaxIdQuery);

            int newId = 1;
            if (rs.next()) {
                newId = rs.getInt(1) + 1; // increment max ID by 1
            }
            String sql = "insert into \"Employee\" values(?,?,?,?,?,?,?,?,?,?,?,?) ";

            PreparedStatement prepare = connectDB.prepareStatement(sql);
            if(!idtext.getText().isEmpty()){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("ERROR");
                alert.setHeaderText(null);
                alert.setContentText("ID is a default value, You can't enter random values");
                alert.showAndWait();
            }
            else if(nametext.getText().isEmpty() || birthdtext.getValue() == null ||salarytext.getText().isEmpty()
                    || emailtext.getText().isEmpty() ||usertext.getText().isEmpty() ||passwordtext.getText().isEmpty() ||addresstext.getText().isEmpty()
                    || phonetext.getText().isEmpty() ||positiontext.getText().isEmpty() ||hiredatetext.getValue()==null
                    || whtext.getText().isEmpty()) {

                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("ERROR");
                alert.setHeaderText(null);
                alert.setContentText("Please enter all the fields");
                alert.showAndWait();
            }else {
                prepare.setInt(1, newId);
                prepare.setString(2, nametext.getText());
                prepare.setDate(3, Date.valueOf(birthdtext.getValue()));
                prepare.setDouble(4, Double.parseDouble(salarytext.getText()));
                prepare.setString(5, emailtext.getText());
                prepare.setString(6, usertext.getText());
                prepare.setString(7, passwordtext.getText());
                prepare.setString(8, addresstext.getText());
                prepare.setInt(9, Integer.parseInt(phonetext.getText()));
                prepare.setString(10, positiontext.getText());
                prepare.setDate(11, Date.valueOf(hiredatetext.getValue()));
                prepare.setInt(12, Integer.parseInt(whtext.getText()));

                prepare.executeUpdate();
                initialize();

            }


        }catch(Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText(null);
            alert.setContentText("Enter Valid information");
            alert.showAndWait();
            e.printStackTrace();
            e.getCause();
        }






    }//end of add button

    //for search by box
    public void searchbyOnAction(){

        List<String> list = new ArrayList<String>(Arrays.asList(searchele));
        ObservableList<String> datalist = FXCollections.observableArrayList(list);
        searchby.setItems(datalist);


    }

    public void clearOnAciot(){
        idtext.clear();
        nametext.clear();
        salarytext.clear();
        emailtext.clear();
        usertext.clear();
        passwordtext.clear();
        addresstext.clear();
        phonetext.clear();
        positiontext.clear();
        birthdtext.setValue(null);
        hiredatetext.setValue(null);
        whtext.clear();
    }


    //      there is an error here with database -- I solved it haha
    public void search() {
        String search_by = searchby.getSelectionModel().getSelectedItem(); //to know the search will be by which attribute

        if (search_by.equals("ID")) {
            if(searchbox.getText().isEmpty()){
                loadEmployees("SELECT \"Employee ID\", \"Employee Name\", \"Birth Date\", EXTRACT(YEAR FROM AGE(\"Birth Date\")) AS age, \"Salary\", \"Working Hours\", \"Email\", \"User Name\", \"Password\", \"Address\", \"Phone Number\", \"Job Position\", \"Hire Date\" FROM \"Employee\" ");
            }else {
                try {

                    String sql = "SELECT \"Employee ID\", \"Employee Name\", \"Birth Date\", " +
                            "EXTRACT(YEAR FROM AGE(\"Birth Date\")) AS age, \"Salary\", \"Working Hours\", " +
                            "\"Email\", \"User Name\", \"Password\", \"Address\", \"Phone Number\"," +
                            " \"Job Position\", \"Hire Date\" FROM \"Employee\"  WHERE \"Employee ID\" =" + Integer.parseInt(searchbox.getText()) + ";";
                    loadEmployees(sql);

                } catch (NumberFormatException e) { //if the user enters a non valid value
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("ERROR");
                    alert.setHeaderText(null);
                    alert.setContentText("Not Found");
                    alert.showAndWait();
                }

            }
        }
        if (search_by.equals("Name")) {
            if(searchbox.getText().isEmpty()){
                loadEmployees("SELECT \"Employee ID\", \"Employee Name\", \"Birth Date\", EXTRACT(YEAR FROM AGE(\"Birth Date\")) AS age, \"Salary\", \"Working Hours\", \"Email\", \"User Name\", \"Password\", \"Address\", \"Phone Number\", \"Job Position\", \"Hire Date\" FROM \"Employee\" ");
            }else {
                try {

                    String sql = "SELECT \"Employee ID\", \"Employee Name\", \"Birth Date\", " +
                            "EXTRACT(YEAR FROM AGE(\"Birth Date\")) AS age, \"Salary\", \"Working Hours\", " +
                            "\"Email\", \"User Name\", \"Password\", \"Address\", \"Phone Number\"," +
                            " \"Job Position\", \"Hire Date\" FROM \"Employee\"  WHERE \"Employee Name\" ='" + searchbox.getText() + "' ;";
                    loadEmployees(sql);

                } catch (NumberFormatException e) { //if the user enters a non valid value
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("ERROR");
                    alert.setHeaderText(null);
                    alert.setContentText("Not Found");
                    alert.showAndWait();
                }

            }
        }
        if (search_by.equals("Salary")) {
            if(searchbox.getText().isEmpty()){
                loadEmployees("SELECT \"Employee ID\", \"Employee Name\", \"Birth Date\", EXTRACT(YEAR FROM AGE(\"Birth Date\")) AS age, \"Salary\", \"Working Hours\", \"Email\", \"User Name\", \"Password\", \"Address\", \"Phone Number\", \"Job Position\", \"Hire Date\" FROM \"Employee\" ");
            }else {
                try {

                    String sql = "SELECT \"Employee ID\", \"Employee Name\", \"Birth Date\", " +
                            "EXTRACT(YEAR FROM AGE(\"Birth Date\")) AS age, \"Salary\", \"Working Hours\", " +
                            "\"Email\", \"User Name\", \"Password\", \"Address\", \"Phone Number\"," +
                            " \"Job Position\", \"Hire Date\" FROM \"Employee\"  WHERE \"Salary\" =" + searchbox.getText() + " ;";
                    loadEmployees(sql);

                } catch (NumberFormatException e) { //if the user enters a non valid value
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("ERROR");
                    alert.setHeaderText(null);
                    alert.setContentText("Not Found");
                    alert.showAndWait();
                }

            }
        }
    }


    public void select (){
        Employee emp = Employeetable.getSelectionModel().getSelectedItem();
        int num = Employeetable.getSelectionModel().getSelectedIndex();
//        if(num < 0)
//            return;

        idtext.setText(String.valueOf(emp.idProperty().get()));
        nametext.setText(String.valueOf(emp.nameProperty().get()));
        salarytext.setText(String.valueOf(emp.salaryProperty().get()));
        emailtext.setText(String.valueOf(emp.emailProperty().get()));
        usertext.setText(String.valueOf(emp.usernameProperty().get()));
        passwordtext.setText(String.valueOf(emp.passwordProperty().get()));
        addresstext.setText(String.valueOf(emp.addressProperty().get()));
        phonetext.setText(String.valueOf(emp.phoneProperty().get()));
        positiontext.setText(String.valueOf(emp.jobPositionProperty().get()));
        birthdtext.setValue(emp.birthdateDateProperty().get());
        hiredatetext.setValue(emp.hireDateProperty().get());
        whtext.setText(String.valueOf(emp.workingHoursProperty().get()));
    }
    public void deleteOnAciot(){


        String sql = "DELETE FROM \"Employee\" WHERE \"Employee ID\" = " +idtext.getText()+";";
        try{
            Statement statment = connectDB.createStatement();
            statment.executeUpdate(sql);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText(null);
            alert.setContentText("Successfully deleted");
            alert.showAndWait();

            loadEmployees("SELECT \"Employee ID\", \"Employee Name\", \"Birth Date\", EXTRACT(YEAR FROM AGE(\"Birth Date\")) AS age, \"Salary\", \"Working Hours\", \"Email\", \"User Name\", \"Password\", \"Address\", \"Phone Number\", \"Job Position\", \"Hire Date\" FROM \"Employee\" ");



        }catch(Exception e){

            e.printStackTrace();
            e.getCause();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Failure");
            alert.setHeaderText(null);
            alert.setContentText("failed to delete");
            alert.showAndWait();
        }

    }

    public void updateOnAction(){

        try {
            int enteredId = Integer.parseInt(idtext.getText());

            // Get the selected employee from the table
            Employee selectedEmployee = Employeetable.getSelectionModel().getSelectedItem();

            if (selectedEmployee == null || selectedEmployee.idProperty().get() != enteredId) {
                // Display an alert if the IDs don't match
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Invalid ID");
                alert.setHeaderText(null);
                alert.setContentText("ID is invalid, please enter the correct ID.");
                alert.showAndWait();
            }
            else {
                try{
                    String sql = "update \"Employee\" set \"Employee Name\" = '"
                            +nametext.getText()
                            +"' , \"Salary\" = " +Double.parseDouble(salarytext.getText())
                            +" , \"Email\" = '" +emailtext.getText()
                            +"' , \"User Name\" = '" +usertext.getText()
                            +"' , \"Password\" = '" +passwordtext.getText()
                            +"' , \"Address\" = '" +addresstext.getText()
                            +"' , \"Phone Number\" = " +Integer.parseInt(phonetext.getText())
                            +" , \"Job Position\" = '"+ positiontext.getText()
                            +"' , \"Hire Date\" = '" +hiredatetext.getValue()
                            +"' , \"Birth Date\" = '" +birthdtext.getValue()
                            +"' , \"Working Hours\" = " + Integer.parseInt(whtext.getText())
                            +" where \"Employee ID\"  = " +Integer.parseInt(idtext.getText()) +" ;";
                    Statement statment = connectDB.createStatement();
                    statment.executeUpdate(sql);

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Success");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully updated");
                    alert.showAndWait();

                    loadEmployees("SELECT \"Employee ID\", \"Employee Name\", \"Birth Date\", EXTRACT(YEAR FROM AGE(\"Birth Date\")) AS age, \"Salary\", \"Working Hours\", \"Email\", \"User Name\", \"Password\", \"Address\", \"Phone Number\", \"Job Position\", \"Hire Date\" FROM \"Employee\" ");



                }catch(Exception e){

                    e.printStackTrace();
                    e.getCause();
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Failure");
                    alert.setHeaderText(null);
                    alert.setContentText("Enter Valid information");
                    alert.showAndWait();
                    loadEmployees("SELECT \"Employee ID\", \"Employee Name\", \"Birth Date\", EXTRACT(YEAR FROM AGE(\"Birth Date\")) AS age, \"Salary\", \"Working Hours\", \"Email\", \"User Name\", \"Password\", \"Address\", \"Phone Number\", \"Job Position\", \"Hire Date\" FROM \"Employee\" ");

                }

            }
        } catch (NumberFormatException e) {
            // Show an alert if the entered text is not a valid integer
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Input Error");
            alert.setHeaderText(null);
            alert.setContentText("Please enter a valid numeric ID.");
            alert.showAndWait();
            loadEmployees("SELECT \"Employee ID\", \"Employee Name\", \"Birth Date\", EXTRACT(YEAR FROM AGE(\"Birth Date\")) AS age, \"Salary\", \"Working Hours\", \"Email\", \"User Name\", \"Password\", \"Address\", \"Phone Number\", \"Job Position\", \"Hire Date\" FROM \"Employee\" ");

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






}
