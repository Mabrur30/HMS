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
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class doctorPageController implements Initializable {
    @FXML
    private TextField Login_doctorID;

    @FXML
    private AnchorPane Login_form;

    @FXML
    private PasswordField Login_password;

    @FXML
    private TextField Login_showPassword;

    @FXML
    private CheckBox Register_cheackBox;

    @FXML
    private TextField Register_doctorEmail;

    @FXML
    private TextField Register_doctorID;

    @FXML
    private PasswordField Register_doctorPassword;

    @FXML
    private TextField Register_doctorShowPassword;

    @FXML
    private Button Register_doctorSignUp;

    @FXML
    private AnchorPane Register_form;

    @FXML
    private Hyperlink Register_login_here;

    @FXML
    private TextField fullName;

    @FXML
    private Button login_btn;

    @FXML
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
    private AlertMessage alert;

    public void switchForm(ActionEvent event) {
        if (event.getSource() == login_register_now) {
            Login_form.setVisible(false);
            Register_form.setVisible(true);
        } else if (event.getSource() == Register_login_here) {
            Login_form.setVisible(true);
            Register_form.setVisible(false);
        }
    }

    public void switchPage() {
        if (login_user.getSelectionModel().getSelectedItem() == "Admin Portal") {
            try {
                Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
                Stage stage = new Stage();
                stage.setTitle("Hospital Management System");
                stage.setMinWidth(368);
                stage.setMinHeight(531);
                stage.setScene(new Scene(root));
                stage.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (login_user.getSelectionModel().getSelectedItem() == "Doctor Portal") {

            try {
                Parent root = FXMLLoader.load(getClass().getResource("doctorPage.fxml"));
                Stage stage = new Stage();
                stage.setTitle("Hospital Management System");
                stage.setMinWidth(368);
                stage.setMinHeight(531);
                stage.setScene(new Scene(root));
                stage.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (login_user.getSelectionModel().getSelectedItem() == "Patient Portal") {
            try {
                Parent root = FXMLLoader.load(getClass().getResource("patientPage.fxml"));
                Stage stage = new Stage();
                stage.setTitle("Hospital Management System");
                stage.setMinWidth(368);
                stage.setMinHeight(531);
                stage.setScene(new Scene(root));
                stage.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        login_user.getScene().getWindow().hide();
    }

    public void userList() {
        List<String> listU = new ArrayList<>();
        for (String data : Users.user) {
            listU.add(data);
        }
        ObservableList listData = FXCollections.observableList(listU);
        login_user.setItems(listData);
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
    public void registerShowPassword() {
        if (Register_cheackBox.isSelected()) {
            Register_doctorShowPassword.setText(Register_doctorPassword.getText());
            Register_doctorShowPassword.setVisible(true);
            Register_doctorPassword.setVisible(false);
        } else {
            Register_doctorPassword.setText(Register_doctorShowPassword.getText());
            Register_doctorShowPassword.setVisible(false);
            Register_doctorPassword.setVisible(true);
        }
    }

    public void registerDoctorID() throws SQLException, ClassNotFoundException {
        String doctorID = "DID-";
        int tempID = 0;
        String sql = "SELECT MAX(id) FROM doctor";
        connect = Database.connectDB();
        prepare = connect.prepareStatement(sql);
        result = prepare.executeQuery();
        if (result.next()) {
            tempID = result.getInt("MAX(id)");
        }
        if (tempID == 0) {
            tempID += 1;
            doctorID += tempID;
        } else {
            doctorID += (tempID + 1);
        }
        Register_doctorID.setText(doctorID);
        Register_doctorID.setDisable(true);


    }

    public void loginAccount() throws SQLException, ClassNotFoundException {
        if (Login_doctorID.getText().isEmpty() || Login_password.getText().isEmpty()) {
            alert.errorMessage("Incorrect username or password");
        } else {
            String sql = "SELECT * FROM admin WHERE username =? AND password=? AND date_delete IS NULL";
            connect = Database.connectDB();
            try {
                String checkStatus = "SELECT status FROM doctor WHERE doctor_id = '"
                        +Login_doctorID.getText()+"' AND password= '"
                        +Login_password.getText()+"' AND status ='Confirm'";

                prepare = connect.prepareStatement(checkStatus);
                result = prepare.executeQuery();
                if (result.next()) {
                    prepare = connect.prepareStatement(sql);
                    prepare.setString(1, Login_doctorID.getText());
                    prepare.setString(2, Login_password.getText());
                    result = prepare.executeQuery();
                    if (result.next()) {
                        alert.successMessage("Login Successfully");
                    }

                } else {
                    alert.errorMessage("Need the confirmation of Admin");
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    public void registerAccount() throws SQLException, ClassNotFoundException {
        if (fullName.getText().isEmpty() ||
                Register_doctorEmail.getText().isEmpty() ||
                Register_doctorID.getText().isEmpty() ||
                Register_doctorPassword.getText().isEmpty()) {
            alert.errorMessage("Please fill all blank fields");
        } else {
            String cheackDoctorID = "SELECT * FROM doctor WHERE doctor_id= ' " + Register_doctorID.getText() + "'";
            connect = Database.connectDB();
            if (!Register_doctorShowPassword.isVisible()) {
                if (!Register_doctorShowPassword.getText().equals(Register_doctorPassword.getText())) {
                    Register_doctorShowPassword.setText(Register_doctorPassword.getText());
                } else {
                    if (!Register_doctorShowPassword.getText().equals(Register_doctorPassword.getText())) {
                        Register_doctorPassword.setText(Register_doctorShowPassword.getText());
                    }
                }

                prepare = connect.prepareStatement(cheackDoctorID);
                result = prepare.executeQuery();
                if (result.next()) {
                    alert.errorMessage(Register_doctorID.getText() + " is already taken");
                } else if (Register_doctorPassword.getText().length() < 8) {
                    alert.errorMessage("Invalid passwoed,At least 8 character needed");
                } else {
                    String insertData = "INSERT INTO doctor(full_name,email,doctor_id,password,date,status)" + "VALUES (?,?,?,?,?,?)";
                    prepare = connect.prepareStatement(insertData);
                    Date date = new Date();
                    java.sql.Date sqlDate = new java.sql.Date(date.getTime());
                    prepare.setString(1, fullName.getText());
                    prepare.setString(2, Register_doctorEmail.getText());
                    prepare.setString(3, Register_doctorID.getText());
                    prepare.setString(4, Register_doctorPassword.getText());
                    prepare.setString(5, String.valueOf(sqlDate));
                    prepare.setString(6, "Confirm");
                    prepare.executeQuery();
                    alert.successMessage("Registered Successfully");
                }
            }
        }
    }
        @Override
        public void initialize (URL url, ResourceBundle resourceBundle){
            try {
                registerDoctorID();
                userList();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }
