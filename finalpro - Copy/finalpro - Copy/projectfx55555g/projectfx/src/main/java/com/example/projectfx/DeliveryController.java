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

public class DeliveryController {
    @FXML
    private Button Homebtn;

    @FXML
    private Button Ordersbtn;

    @FXML
    private Button Customerbtn;

    @FXML
    private Button productbtn;

    @FXML
    private Button Employeebtn;

    @FXML
    private Button deliverybtn;

    @FXML
    private Button signOutbtn;

    @FXML
    private TableView<Delivery> DeliveryTable;

    @FXML
    private TableColumn<Delivery, String> Addresscol;

    @FXML
    private TableColumn<Delivery, Integer> DeliveryIDcol;

    @FXML
    private TableColumn<Delivery, String> DeliveryNamecol;

    @FXML
    private TableColumn<Delivery, String> Emailcol;

    @FXML
    private TableColumn<Delivery, String> PhoneNumbercol;

    @FXML
    private Label welcomeLabel;

    @FXML
    private String jobPosition;


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
        DeliveryIDcol.setCellValueFactory(cellData -> cellData.getValue().deliveryIdProperty().asObject());
        DeliveryNamecol.setCellValueFactory(cellData -> cellData.getValue().deliveryNameProperty());
        Addresscol.setCellValueFactory(cellData -> cellData.getValue().addressProperty());
        Emailcol.setCellValueFactory(cellData -> cellData.getValue().emailProperty());
        PhoneNumbercol.setCellValueFactory(cellData -> cellData.getValue().phoneNumberProperty());

        // Load delivery data from the database
        String sql = "SELECT \"Delivery ID\", \"Delivery Name\", \"Address\", \"Email\", \"Phone Number\" FROM \"Delivery\"";
        loadDeliveryData(sql);
    }

    private void loadDeliveryData(String sql) {
        String url = "jdbc:postgresql://localhost:5432/postgres";
        String user = "postgres";
        String password = "123456";

        ObservableList<Delivery> deliveryList = FXCollections.observableArrayList();

        try (Connection connection = DriverManager.getConnection(url, user, password);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                int deliveryId = resultSet.getInt("Delivery ID");
                String deliveryName = resultSet.getString("Delivery Name");
                String address = resultSet.getString("Address");
                String email = resultSet.getString("Email");
                String phoneNumber = resultSet.getString("Phone Number");

                // Create a new Delivery object and add it to the list
                deliveryList.add(new Delivery(deliveryId, deliveryName, address, email, phoneNumber));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR, "Database error: " + e.getMessage());
            alert.showAndWait();
        }

        DeliveryTable.setItems(deliveryList);
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
        String fxmlFile;
        jobPosition = UserSession.getInstance().getJobPosition();

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
            stage.setScene(new Scene(root));
            stage.setResizable(false);
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

    //buttons programming




    public void addOnAction(ActionEvent event) {

        try{
            String getMaxIdQuery = "SELECT MAX(\"Delivery ID\") FROM \"Delivery\" ;";
            Statement stmt = connectDB.createStatement();
            ResultSet rs = stmt.executeQuery(getMaxIdQuery);

            int newId = 1;
            if (rs.next()) {
                newId = rs.getInt(1) + 1; // increment max ID by 1
            }



            String sql = "insert into \"Delivery\" values(?,?,?,?,?) ";


            PreparedStatement prepare = connectDB.prepareStatement(sql);
            if(!idtext.getText().isEmpty()){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("ERROR");
                alert.setHeaderText(null);
                alert.setContentText("Please enter all the fields");
                alert.showAndWait();            }
            if(nametext.getText().isEmpty()
                    || emailtext.getText().isEmpty() ||addresstext.getText().isEmpty()
                    || phonetext.getText().isEmpty()

            ) {

                JOptionPane.showMessageDialog(null,"Please enter all the fields");

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

    public void clearOnAction(){
        idtext.clear();
        nametext.clear();
        emailtext.clear();
        addresstext.clear();
        phonetext.clear();
    }


    public void search() {
        String search_by = searchby.getSelectionModel().getSelectedItem(); //to know the search will be by which attribute

        if (search_by.equals("ID")) {
            if(searchbox.getText().isEmpty()){
                String sql = "SELECT \"Delivery ID\", \"Delivery Name\", \"Address\"," +
                        " \"Email\", \"Phone Number\" FROM \"Delivery\";";
                loadDeliveryData(sql);
            }else {
                try {

                    String sql = "SELECT \"Delivery ID\", \"Delivery Name\", \"Address\"," +
                            " \"Email\", \"Phone Number\" FROM \"Delivery\"" +
                            " where \"Delivery ID\" = " + Integer.parseInt(searchbox.getText()) + ";";
                    loadDeliveryData(sql);

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
                String sql = "SELECT \"Delivery ID\", \"Delivery Name\", \"Address\"," +
                        " \"Email\", \"Phone Number\" FROM \"Delivery\"" ;
                loadDeliveryData(sql);
            }else {
                try {

                    String sql = "SELECT \"Delivery ID\", \"Delivery Name\", \"Address\"," +
                            " \"Email\", \"Phone Number\" FROM \"Delivery\"" +
                            " where \"Delivery Name\" = '" + searchbox.getText() + "';";
                    loadDeliveryData(sql);

                } catch (NumberFormatException e) { //if the user enters a non valid value
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("ERROR");
                    alert.setHeaderText(null);
                    alert.setContentText("Not Found");
                    alert.showAndWait();
                }

            }
        }
        if (search_by.equals("Address")) {
            if (searchbox.getText().isEmpty()) {
                String sql = "SELECT \"Delivery ID\", \"Delivery Name\", \"Address\"," +
                        " \"Email\", \"Phone Number\" FROM \"Delivery\"" ;
                loadDeliveryData(sql);
            } else
            {
                try {

                    String sql = "SELECT \"Delivery ID\", \"Delivery Name\", \"Address\"," +
                            " \"Email\", \"Phone Number\" FROM \"Delivery\"" +
                            " where \"Address\" = '" + searchbox.getText() + "';";
                    loadDeliveryData(sql);

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

    private int num;
    public void select (){
        Delivery emp = DeliveryTable.getSelectionModel().getSelectedItem();
        num = DeliveryTable.getSelectionModel().getSelectedIndex();
        if(num < 0)
            return;

        idtext.setText(String.valueOf(emp.deliveryIdProperty().get()));
        nametext.setText(String.valueOf(emp.deliveryNameProperty().get()));
        emailtext.setText(String.valueOf(emp.emailProperty().get()));
        addresstext.setText(String.valueOf(emp.addressProperty().get()));
        phonetext.setText(String.valueOf(emp.phoneNumberProperty().get()));
    }
    public void deleteOnAction(){


        String sql = "DELETE FROM \"Delivery\" WHERE \"Delivery ID\" = " +idtext.getText()+";";
        try{
            Statement statment = connectDB.createStatement();
            statment.executeUpdate(sql);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText(null);
            alert.setContentText("Successfully deleted");
            alert.showAndWait();

            loadDeliveryData(
                    "SELECT \"Delivery ID\", \"Delivery Name\", \"Address\", \"Email\", \"Phone Number\" FROM \"Delivery\""
            );



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
            Delivery selectedEmployee = DeliveryTable.getSelectionModel().getSelectedItem();

            if (selectedEmployee == null || selectedEmployee.deliveryIdProperty().get() != enteredId) {
                // Display an alert if the IDs don't match
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Invalid ID");
                alert.setHeaderText(null);
                alert.setContentText("ID is invalid, please enter the correct ID.");
                alert.showAndWait();
            }
            else {
                try{
                    String sql = "update \"Delivery\" set  \"Delivery Name\"  = '" + nametext.getText() + "' , \"Address\"  = '" + addresstext.getText() + "', \"Email\"  = '" + emailtext.getText() + "', \"Phone Number\"  = '" + phonetext.getText() + "' where \"Delivery ID\" = " + idtext.getText() + ";";

                    Statement statment = connectDB.createStatement();
                    statment.executeUpdate(sql);

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Success");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully updated");
                    alert.showAndWait();

                    loadDeliveryData(
                            "SELECT \"Delivery ID\", \"Delivery Name\", \"Address\", \"Email\", \"Phone Number\" FROM \"Delivery\""
                    );



                }catch(Exception e){

                    e.printStackTrace();
                    e.getCause();
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Failure");
                    alert.setHeaderText(null);
                    alert.setContentText("Enter Valid information");
                    alert.showAndWait();
                    loadDeliveryData(
                            "SELECT \"Delivery ID\", \"Delivery Name\", \"Address\", \"Email\", \"Phone Number\" FROM \"Delivery\""
                    );
                }

            }
        } catch (NumberFormatException e) {
            // Show an alert if the entered text is not a valid integer
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Input Error");
            alert.setHeaderText(null);
            alert.setContentText("Please enter a valid numeric ID.");
            alert.showAndWait();
            loadDeliveryData(
                    "SELECT \"Delivery ID\", \"Delivery Name\", \"Address\", \"Email\", \"Phone Number\" FROM \"Delivery\""
            );
        }




    }
}
