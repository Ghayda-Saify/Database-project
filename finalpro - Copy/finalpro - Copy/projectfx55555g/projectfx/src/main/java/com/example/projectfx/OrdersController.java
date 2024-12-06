package com.example.projectfx;

import com.example.DataBaseConnection;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.scene.control.cell.CheckBoxTableCell;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import javax.swing.*;
import java.io.IOException;
import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class OrdersController {
    @FXML
    private GridPane ProductContainer;
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
    private TableView<Orders> Orderstable;

    @FXML
    private TableColumn<Orders, Integer> CustomerIDcol;

    @FXML
    private TableColumn<Orders, Integer> DeliveryIDcol;

    @FXML
    private TableColumn<Orders, Integer> EmployeeIDcol;

    @FXML
    private TableColumn<Orders, LocalDate> OrderDatecol;

    @FXML
    private TableColumn<Orders, Integer> OrderIdcol;

    @FXML
    private TableColumn<Orders, LocalDate> RequiredDatecol;

    @FXML
    private TableColumn<Orders, LocalDate> ShippedDatecol;

    @FXML
    private TableColumn<Orders, Boolean> checkcol;
    @FXML
    private String jobPosition;

    @FXML
    private Label welcomeLabel;
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
    @FXML
    private Button Orderdetails;
    @FXML
    private RadioButton done;
    //for textfields
    @FXML
    private TextField oidtext;
    @FXML
    private DatePicker odtext;
    @FXML
    private DatePicker ddtext;
    @FXML
    private DatePicker rdtext;
    @FXML
    private TextField cidtext;
    @FXML
    private TextField eidtext;
    @FXML
    private ToggleButton status;
    @FXML
    private TextField didtext;
    //for search combo box
    private final String[] searchele = {"Order ID","Order Date","Customer ID","Employee ID"};
    @FXML
    private ComboBox<String> searchby;
    @FXML
    private TextField searchbox;
    @FXML
    private ComboBox<String> custid;
    @FXML
    private ComboBox<String> empid;
    @FXML
    private ComboBox<String> delid;
    @FXML
    public void initialize() {

        OrderIdcol.setCellValueFactory(cellData -> cellData.getValue().orderIDProperty().asObject());
        CustomerIDcol.setCellValueFactory(cellData -> cellData.getValue().customerIDProperty().asObject());
        DeliveryIDcol.setCellValueFactory(cellData -> cellData.getValue().deliveryIDProperty().asObject());
        EmployeeIDcol.setCellValueFactory(cellData -> cellData.getValue().employeeIDProperty().asObject());
        OrderDatecol.setCellValueFactory(cellData -> ( cellData.getValue().orderDateProperty()));
        RequiredDatecol.setCellValueFactory(cellData -> (cellData.getValue().requiredDateProperty()));
        ShippedDatecol.setCellValueFactory(cellData -> (cellData.getValue().shippedDateProperty()));


        // Assuming checkcol is for checkboxes and uses a Boolean property
        checkcol.setCellValueFactory(cellData -> cellData.getValue().selectedProperty());
        checkcol.setCellFactory(CheckBoxTableCell.forTableColumn(checkcol));

        // Load order data from the database
        String sql = "SELECT \"Order_ID\", \"Order_Date\", \"FK CustomerID\", \"Status\", \"FK DeliveryID\", \"FK EmployeeID\" , \"Delivering Date\" ,\"Required_Date\"FROM \"Orders\" ";
        loadOrderData(sql);

    }

    private void loadOrderData(String sql) {
        String url = "jdbc:postgresql://localhost:5432/postgres";
        String user = "postgres";
        String password = "123456";

        ObservableList<Orders> orderList = FXCollections.observableArrayList();

        try (Connection connection = DriverManager.getConnection(url, user, password);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                int orderID = resultSet.getInt("Order_ID");
                LocalDate orderDate =resultSet.getDate("Order_Date").toLocalDate();
                int customerID = resultSet.getInt("FK CustomerID");
                boolean Status = resultSet.getBoolean("Status");
                int deliveryID = resultSet.getInt("FK DeliveryID");
                int employeeID = resultSet.getInt("FK EmployeeID");
                LocalDate shippedDate =resultSet.getDate("Delivering Date").toLocalDate();
                LocalDate requiredDate = resultSet.getDate("Required_Date").toLocalDate();


                // Create a new Orders object and add it to the list
                orderList.add(new Orders(Status,orderID,customerID , deliveryID, employeeID, requiredDate, shippedDate,orderDate ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Show an alert to the user if there's a database error
            Alert alert = new Alert(Alert.AlertType.ERROR, "Database error: " + e.getMessage());
            alert.showAndWait();
        } catch (Exception e) {
            e.printStackTrace(); // Catch any other exceptions
        }

        Orderstable.setItems(orderList);
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
    DataBaseConnection connectNow = new DataBaseConnection();
    Connection connectDB = connectNow.getConnection();


    //buttons mothods
    public void addOnAction(ActionEvent event) {
        try{
            String getMaxIdQuery = "SELECT MAX(\"Order_ID\") FROM \"Orders\" ;";
            Statement stmt = connectDB.createStatement();
            ResultSet rs = stmt.executeQuery(getMaxIdQuery);

            int newId = 1;
            if (rs.next()) {
                newId = rs.getInt(1) + 1; // increment max ID by 1
            }
            String sql = "insert into \"Orders\" values(?,?,?,?,?,?,?,?) ";

            PreparedStatement prepare = connectDB.prepareStatement(sql);
            if(!oidtext.getText().isEmpty()){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("ERROR");
                alert.setHeaderText(null);
                alert.setContentText("ID is a default value, You can't enter random values");
                alert.showAndWait();
            }
            else if(odtext.getValue()==null ||rdtext.getValue()==null ||ddtext.getValue()==null
                    || custid.getValue() == null||empid.getValue() == null ||delid.getValue() == null
                   ||status.getText().isEmpty()
            ) {

                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("ERROR");
                alert.setHeaderText(null);
                alert.setContentText("Please enter all the fields");
                alert.showAndWait();
            }else {
                prepare.setInt(1, newId);
                prepare.setDate(2,  Date.valueOf(odtext.getValue()));
                prepare.setDate(8,  Date.valueOf(rdtext.getValue()));
                prepare.setDate(7,  Date.valueOf(ddtext.getValue()));
                prepare.setInt(3, Integer.parseInt(custid.getSelectionModel().getSelectedItem()));
                prepare.setInt(6, Integer.parseInt(empid.getSelectionModel().getSelectedItem()));
                prepare.setInt(5, Integer.parseInt(delid.getSelectionModel().getSelectedItem()));
                prepare.setBoolean(4, status.isSelected());

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

    public void clearOnAction(){
        oidtext.clear();
        custid.setValue("Choose a number");
        empid.setValue("Choose a number");
        delid.setValue("Choose a number");
        odtext.setValue(null);
        rdtext.setValue(null);
        ddtext.setValue(null);
        status.setSelected(false);
        customerid();
        employeeid();
        deliveryid();
    }


    //      there is an error here with database -- I solved it haha
    public void search() {
        String search_by = searchby.getSelectionModel().getSelectedItem(); //to know the search will be by which attribute

        if (search_by.equals("Order ID")) {
            if(searchbox.getText().isEmpty()){
                // Load order data from the database
                String sql = "SELECT \"Order_ID\", \"Order_Date\", \"FK CustomerID\", \"Status\", \"FK DeliveryID\", \"FK EmployeeID\" , \"Delivering Date\" ,\"Required_Date\"FROM \"Orders\" ";
                loadOrderData(sql);
                done();
            }
            else {
                try {

                    String sql = "SELECT \"Order_ID\", \"Order_Date\", \"FK CustomerID\", \"Status\", \"FK DeliveryID\", \"FK EmployeeID\" , \"Delivering Date\" ,\"Required_Date\"FROM \"Orders\" WHERE \"Order_ID\" =" + Integer.parseInt(searchbox.getText()) + ";";
                    loadOrderData(sql);

                } catch (NumberFormatException e) { //if the user enters a non valid value
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("ERROR");
                    alert.setHeaderText(null);
                    alert.setContentText("Not Found");
                    alert.showAndWait();

                }
            }

        }
        if (search_by.equals("Order Date")) {
            if(searchbox.getText().isEmpty()){
                // Load order data from the database
                String sql = "SELECT \"Order_ID\", \"Order_Date\", \"FK CustomerID\", \"Status\", \"FK DeliveryID\", \"FK EmployeeID\" , \"Delivering Date\" ,\"Required_Date\"FROM \"Orders\" ";
                loadOrderData(sql);
                done();
            }else {
                try {

                    String sql = "SELECT \"Order_ID\", \"Order_Date\", \"FK CustomerID\", \"Status\", \"FK DeliveryID\", \"FK EmployeeID\" , \"Delivering Date\" ,\"Required_Date\"FROM \"Orders\" WHERE \"Order_Date\" ='" + Date.valueOf( searchbox.getText()) + "';";
                    loadOrderData(sql);

                }
                catch (IllegalArgumentException e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("ERROR");
                    alert.setHeaderText(null);
                    alert.setContentText("Invalid date format. Please use yyyy-[m]m-[d]d format.");
                    alert.showAndWait();

                }
            }

        }
        if (search_by.equals("Customer ID")) {
            if(searchbox.getText().isEmpty()){
                // Load order data from the database
                String sql = "SELECT \"Order_ID\", \"Order_Date\", \"FK CustomerID\", \"Status\", \"FK DeliveryID\", \"FK EmployeeID\" , \"Delivering Date\" ,\"Required_Date\"FROM \"Orders\" ";
                loadOrderData(sql);
                done();
            }else {
                try {

                    String sql = "SELECT \"Order_ID\", \"Order_Date\", \"FK CustomerID\", \"Status\", \"FK DeliveryID\", \"FK EmployeeID\" , \"Delivering Date\" ,\"Required_Date\"FROM \"Orders\" WHERE \"FK CustomerID\" =" + Integer.parseInt(searchbox.getText()) + ";";
                    loadOrderData(sql);

                } catch (NumberFormatException e) { //if the user enters a non valid value
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("ERROR");
                    alert.setHeaderText(null);
                    alert.setContentText("Not Found");
                    alert.showAndWait();
                }
            }
        }
        if (search_by.equals("Employee ID")) {
            if(searchbox.getText().isEmpty()){
                // Load order data from the database
                String sql = "SELECT \"Order_ID\", \"Order_Date\", \"FK CustomerID\", \"Status\", \"FK DeliveryID\", \"FK EmployeeID\" , \"Delivering Date\" ,\"Required_Date\"FROM \"Orders\" ";
                loadOrderData(sql);
                done();
            }else {

                try {

                    String sql = "SELECT \"Order_ID\", \"Order_Date\", \"FK CustomerID\", \"Status\", \"FK DeliveryID\", \"FK EmployeeID\" , \"Delivering Date\" ,\"Required_Date\"FROM \"Orders\" WHERE \"FK EmployeeID\" =" + Integer.parseInt(searchbox.getText()) + ";";
                    loadOrderData(sql);

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
    public void done() {
        if (done.isSelected()) {


                try {
                    String sql = "SELECT \"Order_ID\", \"Order_Date\", \"FK CustomerID\", \"Status\", \"FK DeliveryID\", \"FK EmployeeID\" , \"Delivering Date\" ,\"Required_Date\"FROM \"Orders\" WHERE \"Status\" =  false ;";
                    loadOrderData(sql);

                } catch (NumberFormatException e) { //if the user enters a non valid value
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("ERROR");
                    alert.setHeaderText(null);
                    alert.setContentText("Not Found");
                    alert.showAndWait();
                }


        }else if(searchbox.getText().isEmpty()){
            // Load order data from the database
            String sql = "SELECT \"Order_ID\", \"Order_Date\", \"FK CustomerID\", \"Status\", \"FK DeliveryID\", \"FK EmployeeID\" , \"Delivering Date\" ,\"Required_Date\"FROM \"Orders\" ";
            loadOrderData(sql);
        }
    }

    public void select (){
        Orders emp = Orderstable.getSelectionModel().getSelectedItem();
        int num = Orderstable.getSelectionModel().getSelectedIndex();
//        if(num < 0)
//            return;

        oidtext.setText(String.valueOf(emp.orderIDProperty().get()));
        custid.setValue(String.valueOf(emp.customerIDProperty().getValue()));
        empid.setValue(String.valueOf(emp.employeeIDProperty().getValue()));
        delid.setValue(String.valueOf(emp.deliveryIDProperty().getValue()));

        odtext.setValue(emp.orderDateProperty().get());
        rdtext.setValue(emp.requiredDateProperty().get());
        ddtext.setValue(emp.shippedDateProperty().get());
        if(emp.getselected()){
            status.setText("Done");
            status.setSelected(false);

        }
        else {
            status.setText("Not Done");
            status.setSelected(false);
        }
        customerid();
        employeeid();
        deliveryid();
    }

    public void statusOnAction(){
        if (status.isSelected()) {
            status.setText("Done");
            status.setSelected(true);

        } else {
            status.setText("Not Done");
            status.setSelected(false);

        }

    }
    public void deleteOnAction(){


        String sql = "DELETE FROM \"Orders\" WHERE \"Order_ID\" = " +oidtext.getText()+";";
        try{
            Statement statment = connectDB.createStatement();
            statment.executeUpdate(sql);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText(null);
            alert.setContentText("Successfully deleted");
            alert.showAndWait();

             sql = "SELECT \"Order_ID\", \"Order_Date\", \"FK CustomerID\", \"Status\", \"FK DeliveryID\", \"FK EmployeeID\" , \"Delivering Date\" ,\"Required_Date\"FROM \"Orders\" ;";
            loadOrderData(sql);



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
            int enteredId = Integer.parseInt(oidtext.getText());

            // Get the selected employee from the table

            Orders selectedEmployee = Orderstable.getSelectionModel().getSelectedItem();

            if (selectedEmployee == null || selectedEmployee.orderIDProperty().get() != enteredId) {
                // Display an alert if the IDs don't match
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Invalid ID");
                alert.setHeaderText(null);
                alert.setContentText("ID is invalid, please enter the correct ID.");
                alert.showAndWait();
            }
            else {
                try{
                    String sql = "update \"Orders\" set \"Order_Date\" = '"+odtext.getValue()
                            +"', \"FK CustomerID\" = "+Integer.parseInt(custid.getValue())
                            +", \"Status\" = '"+status.isSelected()
                            +"' , \"FK DeliveryID\" = "+Integer.parseInt(delid.getValue())
                            +", \"FK EmployeeID\" ="+Integer.parseInt(empid.getValue())
                            +", \"Delivering Date\" ='"+ddtext.getValue()
                            +"' ,\"Required_Date\"  ='"+rdtext.getValue()+"' WHERE \"Order_ID\" ="+enteredId;
                    Statement statment = connectDB.createStatement();
                    statment.executeUpdate(sql);

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Success");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully updated");
                    alert.showAndWait();

                    loadOrderData("SELECT \"Order_ID\", \"Order_Date\", \"FK CustomerID\", \"Status\", \"FK DeliveryID\", \"FK EmployeeID\" , \"Delivering Date\" ,\"Required_Date\" FROM \"Orders\" ;");


                }catch(Exception e){

                    e.printStackTrace();
                    e.getCause();
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Failure");
                    alert.setHeaderText(null);
                    alert.setContentText("Enter Valid information");
                    alert.showAndWait();
                    loadOrderData("SELECT \"Order_ID\", \"Order_Date\", \"FK CustomerID\", \"Status\", \"FK DeliveryID\", \"FK EmployeeID\" , \"Delivering Date\" ,\"Required_Date\" FROM \"Orders\" ;");

                }

            }
        } catch (NumberFormatException e) {
            // Show an alert if the entered text is not a valid integer
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Input Error");
            alert.setHeaderText(null);
            alert.setContentText("Please enter a valid numeric ID.");
            alert.showAndWait();
            String sql = "SELECT \"Order_ID\", \"Order_Date\", \"FK CustomerID\", \"Status\", \"FK DeliveryID\", \"FK EmployeeID\" , \"Delivering Date\" ,\"Required_Date\" FROM \"Orders\" ;";
            loadOrderData(sql);

        }




    }

    public void OrderDetails(){
        try {
            JasperDesign load = JRXmlLoader.load("C:\\Users\\ASUS\\JaspersoftWorkspace\\rep1\\Simple_Blue_1.jrxml");
            JasperReport jasperreport = JasperCompileManager.compileReport(load);
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("ParameterName", oidtext.getText());
            JasperPrint jasperprint =JasperFillManager.fillReport(jasperreport,null,connectDB);
            JasperViewer.viewReport(jasperprint,false);
        } catch (Exception e) {
            e.printStackTrace(); // Print any errors that occur
        }
    }

    public void customerid(){
        String query = "SELECT \"Customer_ID\" FROM \"Customer\" order by \"Customer_ID\""; // Replace `id` and `your_table` with your table/column names

        try (PreparedStatement statement = connectDB.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            List<String> idList = new ArrayList<>();
            while (resultSet.next()) {
                idList.add(String.valueOf(resultSet.getInt("Customer_ID"))); // Replace "id" with the actual column name
            }

            // Populate the ComboBox
            custid.getItems().addAll(idList);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void deliveryid(){
        String query = "SELECT \"Delivery ID\" FROM \"Delivery\" order by \"Delivery ID\""; // Replace `id` and `your_table` with your table/column names

        try (PreparedStatement statement = connectDB.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            List<String> idList = new ArrayList<>();
            while (resultSet.next()) {
                idList.add(String.valueOf(resultSet.getInt("Delivery ID"))); // Replace "id" with the actual column name
            }

            // Populate the ComboBox
            delid.getItems().addAll(idList);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void employeeid(){
        String query = "SELECT \"Employee ID\" FROM \"Employee\" order by  \"Employee ID\""; // Replace `id` and `your_table` with your table/column names

        try (PreparedStatement statement = connectDB.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            List<String> idList = new ArrayList<>();
            while (resultSet.next()) {
                idList.add(String.valueOf(resultSet.getInt("Employee ID"))); // Replace "id" with the actual column name
            }

            // Populate the ComboBox
            empid.getItems().addAll(idList);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
