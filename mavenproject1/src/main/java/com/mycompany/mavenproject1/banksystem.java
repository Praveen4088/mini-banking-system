package com.mycompany.mavenproject1;
import java.sql.*;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.event.*;
import javafx.scene.control.Alert.AlertType;
/**
* @author KPK
*/
public class banksystem extends Application {

// Database credentials
static final String DB_URL = "jdbc:mysql://localhost:3306/bank";
static final String USER = "root";
static final String PASS = "praveen@2003";

// Database connection and statement
Connection conn = null;
Statement stmt = null;
// Login scene elementsn
TextField loginUsernameField;
PasswordField loginPasswordField;
Label loginErrorLabel;

// Registration scene elements
TextField regUsernameField;
PasswordField regPasswordField;
TextField regNameField;
TextField regAccountField;
TextField regEmailField;
Label regErrorLabel;

// Deposit scene elements
TextField depositAmountField;
String accountNo1;

// Withdraw scene elements
TextField withdrawAmountField;
Label withdrawErrorLabel;

// Balance check scene elements
Label balanceLabel;
public static void main(String[] args) {
launch(args);

}
@Override
public void start(Stage primaryStage) throws Exception {
primaryStage.setTitle("Banking System");

// Initialize database connection and statement
try {
Class.forName("com.mysql.cj.jdbc.Driver");
conn = DriverManager.getConnection(DB_URL, USER, PASS);
stmt = conn.createStatement();
} catch (ClassNotFoundException | SQLException e) {
Alert a1 = new Alert(AlertType.ERROR,""+e+"");
a1.show();
}

// Create login scene
loginUsernameField = new TextField();
loginUsernameField.setPromptText("Username");
loginPasswordField = new PasswordField();
loginPasswordField.setPromptText("Password");
Button loginButton = new Button("Login");
Button back4 = new Button("Go Back");
loginErrorLabel = new Label();
VBox loginLayout = new VBox(10);
loginLayout.setPadding(new Insets(20));
loginLayout.getChildren().addAll(loginUsernameField, loginPasswordField,
loginButton, back4,loginErrorLabel);
Scene loginScene = new Scene(loginLayout, 400, 300);

// Create registration scene
regUsernameField = new TextField();
regUsernameField.setPromptText("Username");
regPasswordField = new PasswordField();
regPasswordField.setPromptText("Password");
regNameField = new TextField();
regNameField.setPromptText("Full Name");

regAccountField = new TextField();
regAccountField.setPromptText("Account Number");
regEmailField = new TextField();
regEmailField.setPromptText("Email");
Button regButton = new Button("Register");
regErrorLabel = new Label();
Button loginpageButton = new Button("Login Page");
loginpageButton.setOnAction((ActionEvent ae) -> {
    primaryStage.setScene(loginScene);
});
VBox regLayout = new VBox(10);
regLayout.setPadding(new Insets(20));
regLayout.getChildren().addAll(regUsernameField, regPasswordField, regNameField,
regAccountField, regEmailField, regButton,loginpageButton,regErrorLabel);
Scene regScene = new Scene(regLayout, 400, 400);

// Create deposit scene
depositAmountField = new TextField();
depositAmountField.setPromptText("Amount");
Button depositButton = new Button("Deposit");
Button back1 = new Button("Go Back");
VBox depositLayout = new VBox(10);
depositLayout.setPadding(new Insets(20));
depositLayout.getChildren().addAll(depositAmountField,depositButton,back1);
Scene depositScene = new Scene(depositLayout, 400, 300);

// Create withdraw scene
withdrawAmountField = new TextField();
withdrawAmountField.setPromptText("Amount");
Button withdrawButton = new Button("Withdraw");
Button back2 = new Button("Go Back");
withdrawErrorLabel = new Label();

VBox withdrawLayout = new VBox(10);
withdrawLayout.setPadding(new Insets(30));
withdrawLayout.getChildren().addAll(withdrawAmountField,withdrawButton,back2,withdrawErrorLabel);
Scene withdrawScene = new Scene(withdrawLayout, 400, 300);

// Create balance check scene
balanceLabel = new Label();
Button balanceButton = new Button("Check Balance");
Button back3 = new Button("Go Back");
VBox balanceLayout = new VBox(10);
balanceLayout.setPadding(new Insets(20));
balanceLayout.getChildren().addAll(balanceLabel, balanceButton,back3);
Scene balanceScene = new Scene(balanceLayout, 400, 300);

// Deposit widthdraw and balance buttons
Button deposit = new Button("Deposit");
Button withdraw = new Button("Widthdraw");
Button balance1 = new Button("Balance");
Button back5 = new Button("Go Back");
VBox all = new VBox(10);
all.getChildren().addAll(deposit,withdraw,balance1,back5);
Scene all1 = new Scene(all,400,300);
back1.setOnAction((ActionEvent ae) -> {
    primaryStage.setScene(all1);
});
back2.setOnAction((ActionEvent ae) -> {
    primaryStage.setScene(all1);
});
back3.setOnAction((ActionEvent ae) -> {
    primaryStage.setScene(all1);
});
back4.setOnAction((ActionEvent ae) -> {
    primaryStage.setScene(regScene);
});
back5.setOnAction((ActionEvent ae) -> {
    primaryStage.setScene(loginScene);
});

deposit.setOnAction((ActionEvent ae) -> {
    primaryStage.setScene(depositScene);
});
withdraw.setOnAction((ActionEvent ae) -> {
    primaryStage.setScene(withdrawScene);
});
balance1.setOnAction((ActionEvent ae) -> {
    primaryStage.setScene(balanceScene);
});

// Set login button action
loginButton.setOnAction((ActionEvent e) -> {
    String username = loginUsernameField.getText();
    String password = loginPasswordField.getText();
    System.out.println(accountNo1);
    try {
        if(username.isEmpty()==true || password.isEmpty()==true){
            Alert al1 = new Alert(AlertType.ERROR,"Enter valid username or Password");
            al1.show();
        }
        else{
            String sql = "SELECT * FROM customers WHERE username = '" + username + "' AND password = '" + password + "'";
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
// Login successful
loginErrorLabel.setText("");
loginUsernameField.setText("");
loginPasswordField.setText("");
primaryStage.setScene(all1);
            } else {
// Login failed
loginErrorLabel.setText("Invalid username or password");
            }
        }
    } catch (SQLException ex) {
        Alert a2 = new Alert(AlertType.ERROR,""+ex+"");
        a2.show();
    }
    try {
        String sqllogin = "select accountNo from customers where username ='"+username+"'";
        ResultSet rs1 = stmt.executeQuery(sqllogin);
        while(rs1.next()){
            accountNo1 = rs1.getString("accountNo");
        }
    } catch (SQLException e1) {
        Alert a3 = new Alert(AlertType.ERROR,""+e1+"");
        a3.show();
    }
});

String acc = regAccountField.getText();
System.out.println(acc);

// Set registration button action
regButton.setOnAction(e -> {
String username = regUsernameField.getText();
String password = regPasswordField.getText();
String name = regNameField.getText();
String accountNo = regAccountField.getText();
accountNo1 = accountNo;
System.out.println(accountNo1);
String email = regEmailField.getText();
Integer amountStr1 = 0;
try {
if(username.isEmpty()==true || password.isEmpty()==true || name.isEmpty()==true
||accountNo.isEmpty()==true){
Alert al1 = new Alert(AlertType.ERROR,"Enter the valid details");
al1.show();
}
else{
String sql = "INSERT INTO customers VALUES ('" + username + "', '" + password + "','" + name + "', '" + accountNo + "', '" + email + "')";
String sql2 = "INSERT INTO MONEY VALUES ('"+accountNo+"','"+amountStr1+"')";
stmt.executeUpdate(sql);
stmt.execute(sql2);

// Registration successful
regErrorLabel.setText("");
regUsernameField.setText("");
regPasswordField.setText("");
regNameField.setText("");
regAccountField.setText("");
regEmailField.setText("");
primaryStage.setScene(loginScene);
}
} catch (SQLException ex) {

//Registration failed
System.out.println(ex);
regErrorLabel.setText("Username already taken");
}
});

// Set deposit button action
depositButton.setOnAction((var ae) -> {
    try {
        String amount = depositAmountField.getText();
        Integer damount = Integer.valueOf(amount);
        String sql3 = "UPDATE MONEY set balance = balance + "+damount+" where accountno = '"+accountNo1+"'";
        stmt.execute(sql3);
        Alert a2 = new Alert(AlertType.INFORMATION,"Money Deposited successfully");
        a2.show();
    } catch (NumberFormatException e) {
        Alert a3 = new Alert(AlertType.ERROR,"Enter amount to deposit");
        a3.show();
    } catch (SQLException e3) {
        Alert a4 = new Alert(AlertType.ERROR,""+e3+"");
        a4.show();
    }
});
withdrawButton.setOnAction((ActionEvent ae) -> {
    try{
        String amount = withdrawAmountField.getText();
        Integer wamount = Integer.valueOf(amount);
        Integer balanceInAccount = 0;
        ResultSet rs = stmt.executeQuery("Select balance from money where accountno ='"+accountNo1+"'");
        while(rs.next()){
            
            balanceInAccount = rs.getInt("balance");
        }
        if(balanceInAccount<wamount){
            Alert a1 = new Alert(AlertType.ERROR,"Invalid amount entered. Your account Balance is $"+balanceInAccount);
            a1.show();
        }
        else{
            String sql4 = "UPDATE MONEY set balance = balance - "+wamount+" where accountno = '"+accountNo1+"'";
            stmt.execute(sql4);
            Alert a1 = new Alert(AlertType.INFORMATION,"Money WithDrawn successfully. Your account balance is $"+(balanceInAccount-wamount));
            a1.show();
        }
    }catch(NumberFormatException e){
        Alert a1 = new Alert(AlertType.ERROR,"Enter amount to Withdraw");
        a1.show();
    }
    catch(SQLException e){
        Alert a1 = new Alert(AlertType.ERROR,""+e+"");
        a1.show();
    }
});
balanceButton.setOnAction((ActionEvent ae) -> {
    try{
        String sqlbalance = "Select balance from money where accountno = '"+accountNo1+"'";
        ResultSet rs = stmt.executeQuery(sqlbalance);
        Integer balanceAmount = 0;
        while(rs.next()){
            balanceAmount = rs.getInt("balance");
        }
        balanceLabel.setText("Your balance is $"+balanceAmount);
    }catch(SQLException e){
    }
});
primaryStage.setScene(regScene);
primaryStage.show();
}
}