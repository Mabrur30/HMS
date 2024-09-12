package com.example.hospital_management_system;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    @FXML
    private AnchorPane Login_form;

    @FXML
    private PasswordField Login_password;

    @FXML
    private TextField Login_showPassword;

    @FXML
    private TextField Login_username;

    @FXML
    private CheckBox Register_cheakBox;

    @FXML
    private TextField Register_email;

    @FXML
    private AnchorPane Register_form;

    @FXML
    private Hyperlink Register_login_here;

    @FXML
    private PasswordField Register_password;

    @FXML
    private TextField Register_showPassword;

    @FXML
    private Button Register_signUp;

    @FXML
    private TextField Register_username;

    @FXML
    private Button login_btn;

    private CheckBox login_checkBox;

    @FXML
    private Hyperlink login_register_now;

    @FXML
    private ComboBox<?> login_user;

    @FXML
    private StackPane stackpane;

    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;
    private AlertMessage alert = new AlertMessage();

    public void loginAccount() throws SQLException, ClassNotFoundException {
        if(Login_username.getText().isEmpty()||Login_password.getText().isEmpty()){
            alert.errorMessage("Incorrect username or password");
        }else{
            String sql ="SELECT * FROM admin WHERE username =? AND password=? ";
            connect=Database.connectDB();
            try{
                if(!Login_showPassword.isVisible()){
                    if(!Login_showPassword.getText().equals(Login_password.getText())){
                        Login_showPassword.setText(Login_password.getText());
                    }
                }else{
                    if(!Login_showPassword.getText().equals(Login_password.getText())){
                        Login_password.setText(Login_showPassword.getText());
                    }
                }

                prepare=connect.prepareStatement(sql);
                prepare.setString(1,Login_username.getText());
                prepare.setString(2,Login_password.getText());
                result=prepare.executeQuery();
                if(result.next()){
                    Data.admin_username=Login_username.getText();

                    alert.successMessage("Login Successfully");
                    Parent root=FXMLLoader.load(getClass().getResource("AdminMainForm.fxml"));
                    Stage stage = new Stage();
                    stage.setTitle("Hospital Management System | Admin Portal");
                    stage.setScene(new Scene(root));
                    stage.show();
                    login_btn.getScene().getWindow().hide();
                }else{
                    alert.errorMessage("Incorrect username or password");
                }
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                if (result != null) {
                    result.close();
                }
                if (prepare != null) {
                    prepare.close();
                }
                if (connect != null) {
                    connect.close();
                }
            }
        }
    }

    public void loginShowPassword(){
        if(login_checkBox.isSelected()){
            Login_showPassword.setText(Login_password.getText());
            Login_showPassword.setVisible(true);
            Login_password.setVisible(false);
        }else{
            Login_password.setText( Login_showPassword.getText());
            Login_showPassword.setVisible(false);
            Login_password.setVisible(true);
        }
    }
    public void registerShowPassword(){
        if(Register_cheakBox.isSelected()){
            Register_showPassword.setText(Register_password.getText());
            Register_showPassword.setVisible(true);
            Register_password.setVisible(false);
        }else{
            Register_password.setText(Register_showPassword.getText());
            Register_showPassword.setVisible(false);
            Register_password.setVisible(true);
        }
    }
    public void registerAccount() throws SQLException, ClassNotFoundException {
        if (Register_email.getText().isEmpty() || Register_username.getText().isEmpty() || Register_password.getText().isEmpty()) {
            alert.errorMessage("Please fill all the blank fields");
        } else {
            String checkUsername = "SELECT * FROM admin WHERE username = ?";
            connect = Database.connectDB();

            try {

                if(!Register_showPassword.isVisible()){
                    if(!Register_showPassword.getText().equals(Register_password.getText())){
                        Register_showPassword.setText(Register_password.getText());
                    }
                }else{
                    if(!Register_showPassword.getText().equals(Register_password.getText())){
                        Register_password.setText(Register_showPassword.getText());
                    }
                }


                prepare = connect.prepareStatement(checkUsername);
                prepare.setString(1, Register_username.getText());
                result = prepare.executeQuery();

                if (result.next()) {
                    alert.errorMessage(Register_username.getText() + " already exists!");
                } else if (Register_password.getText().length()<8) {
                    alert.errorMessage("Password is less than 8 charecters");
                } else {
                    String insertData = "INSERT INTO admin(email, username, password, date) VALUES (?, ?, ?, ?)";
                    java.util.Date date = new java.util.Date();
                    java.sql.Date sqlDate = new java.sql.Date(date.getTime());
                    prepare = connect.prepareStatement(insertData);
                    prepare.setString(1, Register_email.getText());
                    prepare.setString(2, Register_username.getText());
                    prepare.setString(3, Register_password.getText());
                    prepare.setDate(4, sqlDate);
                    prepare.executeUpdate();
                    alert.successMessage("Registered Successfully!");
                    registerClear();
                    Login_form.setVisible(true);
                    Register_form.setVisible(false);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (result != null) {
                    result.close();
                }
                if (prepare != null) {
                    prepare.close();
                }
                if (connect != null) {
                    connect.close();
                }
            }
        }
    }
    public void registerClear(){
        Register_email.clear();
        Register_username.clear();
        Register_password.clear();
        Register_showPassword.clear();
    }



    public void switchPage(){
        if(login_user.getSelectionModel().getSelectedItem().equals("Admin Portal")){
            try{
                Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
                Stage stage = new Stage();
                stage.setTitle("Hospital Management System");
                stage.setMinWidth(368);
                stage.setMinHeight(531);
                stage.setScene(new Scene(root));
                stage.show();
            }catch (Exception e){
                e.printStackTrace();
            }
        } else if (login_user.getSelectionModel().getSelectedItem().equals("Doctor Portal")) {

            try{
                Parent root = FXMLLoader.load(getClass().getResource("doctorPage.fxml"));
                Stage stage = new Stage();
                stage.setTitle("Hospital Management System");
                stage.setMinWidth(368);
                stage.setMinHeight(531);
                stage.setScene(new Scene(root));
                stage.show();
            }catch (Exception e){
                e.printStackTrace();
            }
        } else if (login_user.getSelectionModel().getSelectedItem()=="Patient Portal") {
            try{
                Parent root = FXMLLoader.load(getClass().getResource("patientPage.fxml"));
                Stage stage = new Stage();
                stage.setTitle("Hospital Management System");
                stage.setMinWidth(368);
                stage.setMinHeight(531);
                stage.setScene(new Scene(root));
                stage.show();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        login_user.getScene().getWindow().hide();
    }

    public void userList(){
        List<String> listU =new ArrayList<>();
        for(String data: Users.user){
            listU.add(data);
        }
        ObservableList listData = FXCollections.observableList(listU);
        login_user.setItems(listData);
    }

    public void switchForm(ActionEvent event) {
        if (event.getSource() == login_register_now) {
            Login_form.setVisible(false);
            Register_form.setVisible(true);
        } else if (event.getSource() == Register_login_here) {
            Login_form.setVisible(true);
            Register_form.setVisible(false);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        userList();
    }
}

