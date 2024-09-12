package com.example.hospital_management_system;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.AreaChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;

import java.net.URL;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

public class adminFormController implements Initializable {
    @FXML
    private TableColumn<?, ?> appointment_col_action;

    @FXML
    private TableColumn<?, ?> appointment_col_appointmentID;

    @FXML
    private TableColumn<?, ?> appointment_col_contact;

    @FXML
    private TableColumn<?, ?> appointment_col_date;

    @FXML
    private TableColumn<?, ?> appointment_col_date_delete;

    @FXML
    private TableColumn<?, ?> appointment_col_date_modify;

    @FXML
    private TableColumn<?, ?> appointment_col_description;

    @FXML
    private TableColumn<?, ?> appointment_col_gender;

    @FXML
    private TableColumn<?, ?> appointment_col_name;

    @FXML
    private TableColumn<?, ?> appointment_col_status;

    @FXML
    private AnchorPane appointment_form;

    @FXML
    private TableView<?> appointment_tableview;

    @FXML
    private Button appointments_btn;

    @FXML
    private Label current_form;

    @FXML
    private Label dashboard_AD;

    @FXML
    private Label dashboard_AP;

    @FXML
    private Label dashboard_TA;

    @FXML
    private Label dashboard_TP;

    @FXML
    private Button dashboard_btn;

    @FXML
    private AreaChart<?, ?> dashboard_chart_DD;

    @FXML
    private AreaChart<?, ?> dashboard_chart_PD;

    @FXML
    private TableColumn<?, ?> dashboard_col_doctorID;

    @FXML
    private TableColumn<?, ?> dashboard_col_name;

    @FXML
    private TableColumn<?, ?> dashboard_col_specialised;

    @FXML
    private TableColumn<?, ?> dashboard_col_status;

    @FXML
    private AnchorPane dashboard_form;

    @FXML
    private TableView<?> dashboard_tableview;

    @FXML
    private Label date_time;

    @FXML
    private Button doctors_btn;

    @FXML
    private TableColumn<?, ?> doctors_col_action;

    @FXML
    private TableColumn<?, ?> doctors_col_address;

    @FXML
    private TableColumn<?, ?> doctors_col_contact;

    @FXML
    private TableColumn<?, ?> doctors_col_doctorID;

    @FXML
    private TableColumn<?, ?> doctors_col_email;

    @FXML
    private TableColumn<?, ?> doctors_col_gender;

    @FXML
    private TableColumn<?, ?> doctors_col_name;

    @FXML
    private TableColumn<?, ?> doctors_col_specialization;

    @FXML
    private TableColumn<?, ?> doctors_col_status;

    @FXML
    private AnchorPane doctors_form;

    @FXML
    private TableView<?> doctors_tableview;

    @FXML
    private AnchorPane main_form;

    @FXML
    private Label nav_adminID;

    @FXML
    private Label nav_username;

    @FXML
    private Button patients_btn;

    @FXML
    private TableColumn<?, ?> patients_col_action;

    @FXML
    private TableColumn<?, ?> patients_col_contact;

    @FXML
    private TableColumn<?, ?> patients_col_date;

    @FXML
    private TableColumn<?, ?> patients_col_date_delete;

    @FXML
    private TableColumn<?, ?> patients_col_date_modify;

    @FXML
    private TableColumn<?, ?> patients_col_description;

    @FXML
    private TableColumn<?, ?> patients_col_gender;

    @FXML
    private TableColumn<?, ?> patients_col_name;

    @FXML
    private TableColumn<?, ?> patients_col_patientID;

    @FXML
    private TableColumn<?, ?> patients_col_status;

    @FXML
    private AnchorPane patients_form;

    @FXML
    private TableView<?> patients_tableview;

    @FXML
    private Button payment_btn;

    @FXML
    private Button profile_btn;

    @FXML
    private Circle top_profile;

    @FXML
    private Label top_username;
    public Connection connect;
    private PreparedStatement prepare;
    private Statement statement;
    private ResultSet result;

    public void switchform(ActionEvent event){
        if(event.getSource()==dashboard_btn){
            dashboard_form.setVisible(true);
            doctors_form.setVisible(false);
            patients_form.setVisible(false);
            appointment_form.setVisible(false);
        }else if(event.getSource()==doctors_btn){
            dashboard_form.setVisible(false);
            doctors_form.setVisible(true);
            patients_form.setVisible(false);
            appointment_form.setVisible(false);
        }else if(event.getSource()==patients_btn) {
            dashboard_form.setVisible(false);
            doctors_form.setVisible(false);
            patients_form.setVisible(true);
            appointment_form.setVisible(false);
        } else if (event.getSource()==appointments_btn) {
            dashboard_form.setVisible(false);
            doctors_form.setVisible(false);
            patients_form.setVisible(false);
            appointment_form.setVisible(true);
        }
    }

    public void displayAdminIDUsername() throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM admin WHERE username = '"+Data.admin_username+ "'";
        connect=Database.connectDB();
        prepare=connect.prepareStatement(sql);
        result= prepare.executeQuery();
        if(result.next()){
            nav_adminID.setText(result.getString("admin_id"));
            String tempUsername = result.getString("username");
            tempUsername =tempUsername.substring(0,1).toUpperCase()+tempUsername.substring(1);
            nav_username.setText(tempUsername);
        }

    }


    public void runTime(){
        new Thread(){
            public void run() {
                SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss s");
                while (true) {
                    try {
                        Thread.sleep(1000);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    Platform.runLater(() -> {
                        date_time.setText(format.format(new Date()));
                    });
                }
            }
        }.start();
    }
@Override
    public void initialize(URL location, ResourceBundle resources){
    runTime();
    try {
        displayAdminIDUsername();
    } catch (SQLException e) {
        throw new RuntimeException(e);
    } catch (ClassNotFoundException e) {
        throw new RuntimeException(e);
    }
}
}
