package pl.schoolManagement.controllers;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import pl.schoolManagement.model.Subject;
import pl.schoolManagement.model.SubjectDAO;
import pl.schoolManagement.model.User;
import pl.schoolManagement.model.UserDAO;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

public class EditSubjectController {

    private boolean isEdit = false;
    private int subject_id;

    @FXML
    Label idField;
    @FXML
    TextField nameField;
    @FXML
    TextArea descriptionField;
    @FXML
    Button okButton, cancelButton, addButtonStudent, delButtonStudent, addButtonTeacher, delButtonTeacher;
    @FXML
    TableView<User> studentTable, teacherTable;
    @FXML
    TableColumn<User, Integer> idStudentColumn, idTeacherColumn;
    @FXML
    TableColumn<User, String> nameStudentColumn, surnameStudentColumn, nameTeacherColumn, surnameTeacherColumn;
    @FXML
    Accordion accordion;
    @FXML
    BorderPane borderPaneSubject;


    @FXML
    private void initialize(){

        idStudentColumn.setCellValueFactory(cellData -> cellData.getValue().user_idProperty().asObject());
        nameStudentColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        surnameStudentColumn.setCellValueFactory(cellData -> cellData.getValue().surnameProperty());
        idTeacherColumn.setCellValueFactory(cellData -> cellData.getValue().user_idProperty().asObject());
        nameTeacherColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        surnameTeacherColumn.setCellValueFactory(cellData -> cellData.getValue().surnameProperty());

        accordion.setVisible(false);
        borderPaneSubject.setPrefHeight(300);
    }

    public void setValues(int subject_id, String name, String description) {
        this.isEdit = true;
        this.subject_id = subject_id;
        idField.setText(Integer.toString(subject_id));
        nameField.setText(name);
        descriptionField.setText(description);

        try {
            searchStudents();
            searchTeachers();
            accordion.setVisible(true);
            borderPaneSubject.setPrefHeight(600);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void searchStudents(){
        try{
            ObservableList<User> studentsList = SubjectDAO.searchUserSubject(subject_id, 0);
            studentTable.setItems(studentsList);
        }catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    private void searchTeachers(){
        try{
            ObservableList<User> teachersList = SubjectDAO.searchUserSubject(subject_id, 1);
            teacherTable.setItems(teachersList);
        }catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    @FXML
    public void handleButton(ActionEvent event){
        if(event.getTarget().equals(okButton)){
            if(notNull()) {
                if (isEdit) {
                    SubjectDAO.updateSubject(Integer.parseInt(this.idField.getText()), this.nameField.getText(), this.descriptionField.getText());
                    Stage stage = (Stage) okButton.getScene().getWindow();
                    stage.fireEvent(
                            new WindowEvent(
                                    stage,
                                    WindowEvent.WINDOW_CLOSE_REQUEST
                            )
                    );
                } else {
                    if(SubjectDAO.isNameFree(nameField.getText())) {
                        SubjectDAO.insertSubject(nameField.getText(), descriptionField.getText());
                        Stage stage = (Stage) okButton.getScene().getWindow();
                        stage.fireEvent(
                                new WindowEvent(
                                        stage,
                                        WindowEvent.WINDOW_CLOSE_REQUEST
                                )
                        );
                    }
                }
            }
        }else if(event.getTarget().equals(cancelButton)){
            Stage stage = (Stage) cancelButton.getScene().getWindow();
            stage.close();
        }else if(event.getTarget().equals(addButtonTeacher)){
            editUserToSubject(this.subject_id, 1);
        }else if(event.getTarget().equals(delButtonTeacher)){
            if(teacherTable.getSelectionModel().getSelectedItem()!=null){
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Usuwanie Ucznia?");
                alert.setHeaderText("Czy na pewno chcesz usunąć?");
                alert.setContentText("ucznia: "+ teacherTable.getSelectionModel().getSelectedItem().getName() + " " + teacherTable.getSelectionModel().getSelectedItem().getSurname());

                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    SubjectDAO.deleteUserSubject(teacherTable.getSelectionModel().getSelectedItem().getUser_id(), subject_id);
                    searchStudents();
                } else {
                    // ... user chose CANCEL or closed the dialog
                }
            }
        }else if(event.getTarget().equals(addButtonStudent)){
            editUserToSubject(this.subject_id, 0);
        }else if(event.getTarget().equals(delButtonStudent)){
            if(studentTable.getSelectionModel().getSelectedItem()!=null){
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Usuwanie Ucznia?");
                alert.setHeaderText("Czy na pewno chcesz usunąć?");
                alert.setContentText("ucznia: "+ studentTable.getSelectionModel().getSelectedItem().getName() + " " + studentTable.getSelectionModel().getSelectedItem().getSurname());

                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    SubjectDAO.deleteUserSubject(studentTable.getSelectionModel().getSelectedItem().getUser_id(), subject_id);
                    searchStudents();
                } else {
                    // ... user chose CANCEL or closed the dialog
                }
            }
        }
    }

    private void editUserToSubject(int subject_id, int rank){
        FXMLLoader loader = new FXMLLoader();

        loader.setLocation(EditUserController.class.getResource("../view/addUserToSubject.fxml"));
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        AddUserToSubjectController addUserToSubjectController = loader.getController();
        addUserToSubjectController.setValues(subject_id, rank);

        Parent parent = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(parent));
        stage.setOnCloseRequest(event -> {

                searchStudents();
                searchTeachers();

        });
        //stage.setAlwaysOnTop(true);

        stage.showAndWait();
    }

    private boolean notNull(){
        return (nameField.getText().isEmpty() || descriptionField.getText().isEmpty()) ? false : true;
    }

}
