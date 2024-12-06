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

public class CustomerController {
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
    private Button add;

    @FXML
    private Button clear;

    @FXML
    private Button delete;

    @FXML
    private Button update;

    @FXML
    private TableView<Customer> CustomerTable;

    @FXML
    private TableColumn<Customer, String> Addresscol;

    @FXML
    private TableColumn<Customer, Integer> CustomerIDcol;

    @FXML
    private TableColumn<Customer, String> CustomerNamecol;

    @FXML
    private TableColumn<Customer, String> Emailcol;

    @FXML
    private TableColumn<Customer, String> PhoneNumbercol;

    @FXML
    private Label welcomeLabel;

    @FXML
    private String jobPosition;

    @FXML
    private TextField idtext;
    @FXML
    private TextField nametext;
    @FXML
    private TextField phonetext;
    @FXML
    private TextField emailtext;
    @FXML
    private TextField addresstext;

    //for search combo box
    private final String[] searchele = {"ID","Name","Address"};
    @FXML
    private ComboBox<String> searchby;
    @FXML
    private TextField searchbox;




    DataBaseConnection connectNow = new DataBaseConnection();
    Connection connectDB = connectNow.getConnection();

    @FXML
    public void initialize() {
        // Initialize table columns
        CustomerIDcol.setCellValueFactory(cellData -> cellData.getValue().customerIdProperty().asObject());
        CustomerNamecol.setCellValueFactory(cellData -> cellData.getValue().customerNameProperty());
        Addresscol.setCellValueFactory(cellData -> cellData.getValue().addressProperty());
        Emailcol.setCellValueFactory(cellData -> cellData.getValue().emailProperty());
        PhoneNumbercol.setCellValueFactory(cellData -> cellData.getValue().phoneProperty());
        String sql = "SELECT \"Customer_ID\", \"Customer_Name\", \"Address\", \"Email\", \"Phone_Number\" FROM \"Customer\"";
        // Load customer data from the database
        loadCustomerData(sql);
    }
    private void loadCustomerData(String sql) {
        String url = "jdbc:postgresql://localhost:5432/postgres";
        String user = "postgres";
        String password = "123456";

        ObservableList<Customer> customerList = FXCollections.observableArrayList();

        try (Connection connection = DriverManager.getConnection(url, user, password);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                int customerId = resultSet.getInt("Customer_ID");
                String customerName = resultSet.getString("Customer_Name");
                String address = resultSet.getString("Address");
                String email = resultSet.getString("Email");
                String phoneNumber = resultSet.getString("Phone_Number");

                // Create a new Customer object and add it to the list
                customerList.add(new Customer(customerId, customerName, address, email, phoneNumber));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR, "Database error: " + e.getMessage());
            alert.showAndWait();
        }

        CustomerTable.setItems(customerList);
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
    //    public void setJobPosition(String jobPosition) {
//        this.jobPosition=jobPosition;
//    }
//    public void initializeWithUserData(String username,String jobPosition) {
//
//        setJobPosition(jobPosition);
//        welcomeLabel.setText( username);
//
//    }
    @FXML
    public void EmployeebtnOnAction(ActionEvent event) {
        jobPosition = UserSession.getInstance().getJobPosition();
        String fxmlFile;

        // Choose the FXML file based on the job position
        switch (jobPosition.toLowerCase()) {
            case "manager":
                fxmlFile = "Manager.fxml";
                break;
            default:
                fxmlFile = "Employee.fxml"; // A generic or default dashboard for other positions
                break;
        }

        try {
            // Load the selected FXML file
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlFile));
            Parent root = fxmlLoader.load();

            // Set up the new scene
            Stage stage = (Stage) Employeebtn.getScene().getWindow();
            stage.setResizable(false);
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace(); // Handle any file loading errors
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



    public void addOnAction(ActionEvent event) {

        try{
            String getMaxIdQuery = "SELECT MAX(\"Customer_ID\") FROM \"Customer\" ;";
            Statement stmt = connectDB.createStatement();
            ResultSet rs = stmt.executeQuery(getMaxIdQuery);

            int newId = 1;
            if (rs.next()) {
                newId = rs.getInt(1) + 1; // increment max ID by 1
            }



            String sql = "insert into \"Customer\" values(?,?,?,?,?) ";
            PreparedStatement prepare = connectDB.prepareStatement(sql);

            if(!idtext.getText().isEmpty()){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("ERROR");
                alert.setHeaderText(null);
                alert.setContentText("ID is a default value, You can't enter random values");
                alert.showAndWait();
            }
            else if(nametext.getText().isEmpty() || emailtext.getText().isEmpty() ||addresstext.getText().isEmpty()
                    || phonetext.getText().isEmpty())
            {

                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("ERROR");
                alert.setHeaderText(null);
                alert.setContentText("Please enter all the fields");
                alert.showAndWait();
            }else {
                prepare.setInt(1, newId);
                prepare.setString(2, nametext.getText());
                prepare.setString(4, emailtext.getText());
                prepare.setString(3, addresstext.getText());
                prepare.setString(5, phonetext.getText());

                prepare.executeUpdate();
                initialize();

            }
        }catch(Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText(null);
            alert.setContentText("You entered invalid value, Please try again");
            alert.showAndWait();
            e.printStackTrace();
            e.getCause();
        }

    }//end of add button

    public void clearOnAciot(){
        idtext.clear();
        nametext.clear();
        emailtext.clear();
        addresstext.clear();
        phonetext.clear();
    }

    //for search by box
    public void searchbyOnAction(){
        List<String> list = new ArrayList<String>(Arrays.asList(searchele));
        ObservableList<String> datalist = FXCollections.observableArrayList(list);
        searchby.setItems(datalist);
    }



    //      there is an error here with database -- I solved it haha
    public void search() {
        String search_by = searchby.getSelectionModel().getSelectedItem(); //to know the search will be by which attribute

        if (search_by.equals("ID")) {
            if(searchbox.getText().isEmpty()){
                String sql = "SELECT \"Customer_ID\", \"Customer_Name\", \"Address\", \"Email\", \"Phone_Number\" FROM \"Customer\"";
                // Load customer data from the database
                loadCustomerData(sql);
            }else{
            try {

                String sql = "SELECT \"Customer_ID\", \"Customer_Name\", \"Address\", \"Email\", \"Phone_Number\" FROM \"Customer\"  WHERE \"Customer_ID\" ="+Integer.parseInt(searchbox.getText())+";";
                loadCustomerData(sql);

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
                String sql = "SELECT \"Customer_ID\", \"Customer_Name\", \"Address\", \"Email\", \"Phone_Number\" FROM \"Customer\"";
                // Load customer data from the database
                loadCustomerData(sql);
            }else {
                try {

                    String sql = "SELECT \"Customer_ID\", \"Customer_Name\", \"Address\", \"Email\", \"Phone_Number\" FROM \"Customer\"  WHERE \"Customer_Name\" ='" + searchbox.getText() + "';";
                    loadCustomerData(sql);

                } catch (NullPointerException e) { //if the user enters a non valid value
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("ERROR");
                    alert.setHeaderText(null);
                    alert.setContentText("Not Found");
                    alert.showAndWait();
                }
            }

        }
        if (search_by.equals("Address")) {
            if(searchbox.getText().isEmpty()){
                String sql = "SELECT \"Customer_ID\", \"Customer_Name\", \"Address\", \"Email\", \"Phone_Number\" FROM \"Customer\"";
                // Load customer data from the database
                loadCustomerData(sql);
            }else {
                try {

                    String sql = "SELECT \"Customer_ID\", \"Customer_Name\", \"Address\", \"Email\", \"Phone_Number\" FROM \"Customer\" WHERE \"Address\" ='" + searchbox.getText() + "' ;";
                    loadCustomerData(sql);

                } catch (NullPointerException e) { //if the user enters a non valid value
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("ERROR");
                    alert.setHeaderText(null);
                    alert.setContentText("Not Found");
                    alert.showAndWait();
                }
            }
        }
    }//end of search method


    private int num;
    public void select (){
        Customer cust = CustomerTable.getSelectionModel().getSelectedItem();
        num = CustomerTable.getSelectionModel().getSelectedIndex();
        if(num < 0)
            return;

        idtext.setText(String.valueOf(cust.customerIdProperty().get()));
        nametext.setText(String.valueOf(cust.customerNameProperty().get()));
        emailtext.setText(String.valueOf(cust.emailProperty().get()));
        addresstext.setText(String.valueOf(cust.addressProperty().get()));
        phonetext.setText(String.valueOf(cust.phoneProperty().get()));
    }
    public void deleteOnAciot(){


        try{
            String sql = "DELETE FROM \"Customer\" WHERE \"Customer_ID\" = " +idtext.getText()+";";
            Statement statment = connectDB.createStatement();
            statment.executeUpdate(sql);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText(null);
            alert.setContentText("Successfully deleted");
            alert.showAndWait();
            sql = "SELECT \"Customer_ID\", \"Customer_Name\", \"Address\", \"Email\", \"Phone_Number\" FROM \"Customer\" " ;

            loadCustomerData(sql);



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
            Customer selectedEmployee = CustomerTable.getSelectionModel().getSelectedItem();

            if (selectedEmployee == null || selectedEmployee.customerIdProperty().get() != enteredId) {
                // Display an alert if the IDs don't match
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Invalid ID");
                alert.setHeaderText(null);
                alert.setContentText("ID is invalid, please enter the correct ID.");
                alert.showAndWait();
            }
            else {
                try{
                    String sql = "update \"Customer\" set \"Customer_Name\" = '" +nametext.getText()
                            +"' , \"Email\" = '" +emailtext.getText()
                            +"' , \"Address\" = '" +addresstext.getText()
                            +"' , \"Phone_Number\" = " +Integer.parseInt(phonetext.getText())
                            +" where \"Customer_ID\"  = " +Integer.parseInt(idtext.getText()) +" ;";
                    Statement statment = connectDB.createStatement();
                    statment.executeUpdate(sql);

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Success");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully updated");
                    alert.showAndWait();

                    sql = "SELECT \"Customer_ID\", \"Customer_Name\", \"Address\", \"Email\", \"Phone_Number\" FROM \"Customer\" " ;

                    loadCustomerData(sql);



                }catch(Exception e){

                    e.printStackTrace();
                    e.getCause();
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Failure");
                    alert.setHeaderText(null);
                    alert.setContentText("Enter Valid information");
                    alert.showAndWait();
                    String sql = "SELECT \"Customer_ID\", \"Customer_Name\", \"Address\", \"Email\", \"Phone_Number\" FROM \"Customer\" " ;

                    loadCustomerData(sql);
                }

            }
        } catch (NumberFormatException e) {
            // Show an alert if the entered text is not a valid integer
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Input Error");
            alert.setHeaderText(null);
            alert.setContentText("Please enter a valid numeric ID.");
            alert.showAndWait();
            String sql = "SELECT \"Customer_ID\", \"Customer_Name\", \"Address\", \"Email\", \"Phone_Number\" FROM \"Customer\" " ;

            loadCustomerData(sql);
        }




    }


}//end of customer class
