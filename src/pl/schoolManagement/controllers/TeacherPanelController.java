package pl.schoolManagement.controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import pl.schoolManagement.model.*;
import pl.schoolManagement.singleton.LoginSingleton;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class TeacherPanelController {
    @FXML
    TableColumn<Grade, Integer> idCol, valueCol, multipleCol;
    @FXML
    TableColumn<Grade, String> nameCol, surnameCol, descCol, dataCol;
    @FXML
    ListView<Subject> teacherListView;
    @FXML
    TableView<Grade> teacherTable;
    @FXML
    Button addGradeButton, editGradeButton, delGradeButton;

    public void initialize(){
        idCol.setCellValueFactory(param -> param.getValue().grade_idProperty().asObject());
        valueCol.setCellValueFactory(param -> param.getValue().valueProperty().asObject());
        multipleCol.setCellValueFactory(param -> param.getValue().multipleProperty().asObject());
        nameCol.setCellValueFactory(param -> param.getValue().student_nameProperty());
        surnameCol.setCellValueFactory(param -> param.getValue().student_surnameProperty());
        dataCol.setCellValueFactory(param -> param.getValue().dateProperty());
        descCol.setCellValueFactory(param -> param.getValue().descriptionProperty());

        teacherListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Subject>() {
            @Override
            public void changed(ObservableValue<? extends Subject> observable, Subject oldValue, Subject newValue) {
                Subject subject = (Subject) teacherListView.getSelectionModel().getSelectedItem();
                fillTableView(subject.getSubject_id());
            }
        });

        fillListView();

        teacherTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }

    private void fillTableView(int subject_id){
        ObservableList<Grade> gradeList = GradeDAO.searchTeacherGrades(LoginSingleton.getInstance().getUser().getUser_id(), subject_id);
        teacherTable.setItems(gradeList);
    }

    private void fillListView() {
        try {
            ObservableList<Subject> subjects = SubjectDAO.searchUserSubjects(LoginSingleton.getInstance().getUser().getUser_id());
            teacherListView.setItems(subjects);
            teacherListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
            teacherListView.getSelectionModel().selectFirst();
        }catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    @FXML
    public void handleButton(ActionEvent event){
        if(event.getTarget().equals(addGradeButton)){
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(AdminPanelController.class.getResource("../view/editGrade.fxml"));
            try {
                loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            EditGradeController editGradeController = loader.getController();
            String teacher_name = LoginSingleton.getInstance().getUser().getName() + " " + LoginSingleton.getInstance().getUser().getSurname();
            editGradeController.setValues(teacherListView.getSelectionModel().getSelectedItem().getSubject_id(), teacher_name, teacherListView.getSelectionModel().getSelectedItem().toString());
            Parent parent = loader.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene(parent));
            stage.showAndWait();

        }else if(event.getTarget().equals(editGradeButton)) {

        }else if(event.getTarget().equals(delGradeButton)){

        }
    }
}
