package pl.schoolManagement.controllers;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import pl.schoolManagement.model.SubjectDAO;
import pl.schoolManagement.model.User;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

public class EditGradeController {

    @FXML
    Button okButton, cancelButton;
    @FXML
    ChoiceBox<Integer> valueBox, multipleBox;
    @FXML
    ChoiceBox<User> studentBox;
    @FXML
    TextField teacherField, subjectField;
    @FXML
    DatePicker datePicker;
    @FXML
    TextArea descField;

    private boolean isEdit = false;
    private int subject_id;

    @FXML
    public void initialize(){
        valueBox.setItems(FXCollections.observableArrayList(
                1,2,3,4,5
        ));
        valueBox.getSelectionModel().selectFirst();
        multipleBox.setItems(FXCollections.observableArrayList(
                1,2,3
        ));
        multipleBox.getSelectionModel().selectFirst();



    }

    public static final LocalDate LOCAL_DATE (String dateString){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate localDate = LocalDate.parse(dateString, formatter);
        return localDate;
    }

    @FXML
    public void setValues(int id, int value, int multiple, int student_id, String teacher_name, String subject_name, String date, String desc){

        this.isEdit = true;
    }
    @FXML
    public void setValues(int subject_id, String teacher_name, String subject_name){
        this.subject_id = subject_id;
        teacherField.setText(teacher_name);
        subjectField.setText(subject_name);
        this.isEdit = false;

        Calendar cal = Calendar.getInstance();
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        datePicker.setValue(LOCAL_DATE(dateFormat.format(cal.getTime())));

        try {
            studentBox.setItems(SubjectDAO.searchUserSubject(subject_id, 0));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    public void handleButton(ActionEvent event){
        if(event.getTarget().equals(okButton)){

        }else if(event.getTarget().equals(cancelButton)){
            Stage stage = (Stage) cancelButton.getScene().getWindow();
            stage.close();
        }
    }
}
