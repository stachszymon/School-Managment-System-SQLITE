package pl.schoolManagement.controllers;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import pl.schoolManagement.model.Subject;
import pl.schoolManagement.model.SubjectDAO;
import pl.schoolManagement.model.User;
import pl.schoolManagement.model.UserDAO;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

public class AdminPanelController {
    @FXML
    private TableView<User> studentTable;
    @FXML
    private TableColumn<User, Integer> studentIdColumn;
    @FXML
    private TableColumn<User, String> studentNameColumn;
    @FXML
    private TableColumn<User, String> studentSurnameColumn;
    @FXML
    private TableColumn<User, String> studentEmailColumn;
    @FXML
    private TableColumn<User, Integer> studentPhoneColumn;
    @FXML
    private Button addStudentButton;
    @FXML
    private Button delStudentButton;
    @FXML
    private Button editStudentButton;

    @FXML
    private TableView<User> teacherTable;
    @FXML
    private TableColumn<User, Integer> teacherIdColumn;
    @FXML
    private TableColumn<User, String> teacherNameColumn;
    @FXML
    private TableColumn<User, String> teacherSurnameColumn;
    @FXML
    private TableColumn<User, String> teacherEmailColumn;
    @FXML
    private TableColumn<User, Integer> teacherPhoneColumn;
    @FXML
    private Button addTeacherButton;
    @FXML
    private Button delTeacherButton;
    @FXML
    private Button editTeacherButton;

    @FXML
    private TableView<Subject> subjectTable;
    @FXML
    private TableColumn<Subject, Integer> subjectIdColumn;
    @FXML
    private TableColumn<Subject, String> subjectNameColumn;
    @FXML
    private TableColumn<Subject, String> subjectDescriptionColumn;
    @FXML
    private Button addSubjectButton;
    @FXML
    private Button delSubjectButton;
    @FXML
    private Button editSubjectButton;

    private static final int STUDENT = 0;
    private static final int TEACHER = 1;
    private static final int SUBJECT = 2;

    @FXML
    private void initialize(){

        studentIdColumn.setCellValueFactory(cellData -> cellData.getValue().user_idProperty().asObject());
        studentNameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        studentSurnameColumn.setCellValueFactory(cellData -> cellData.getValue().surnameProperty());
        studentEmailColumn.setCellValueFactory(cellData -> cellData.getValue().emailProperty());
        studentPhoneColumn.setCellValueFactory(cellData -> cellData.getValue().phoneProperty().asObject());

        teacherIdColumn.setCellValueFactory(cellData -> cellData.getValue().user_idProperty().asObject());
        teacherNameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        teacherSurnameColumn.setCellValueFactory(cellData -> cellData.getValue().surnameProperty());
        teacherEmailColumn.setCellValueFactory(cellData -> cellData.getValue().emailProperty());
        teacherPhoneColumn.setCellValueFactory(cellData -> cellData.getValue().phoneProperty().asObject());

        subjectIdColumn.setCellValueFactory(cellData -> cellData.getValue().subject_idProperty().asObject());
        subjectNameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        subjectDescriptionColumn.setCellValueFactory(cellData -> cellData.getValue().descriptionProperty());

        studentTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        teacherTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        subjectTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        ScrollPane scrollpane1 = new ScrollPane();
        scrollpane1.setContent(studentTable);

        searchStudents();
        searchTeachers();
        searchSubjects();
    }

    private void searchStudents() {
        try{
            ObservableList<User> studentsList = UserDAO.searchStudents();
            studentTable.setItems(studentsList);
        }catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    private void searchTeachers() {
        try{
            ObservableList<User> teachersList = UserDAO.searchTeachers();
            teacherTable.setItems(teachersList);
        }catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    private void searchSubjects() {
        try{
            ObservableList<Subject> subjectsList = SubjectDAO.searchSubjects();
            subjectTable.setItems(subjectsList);
        }catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    @FXML
    public void handleButton(ActionEvent event){
        if(event.getTarget().equals(addStudentButton)){
            openDialogEditUser(false, STUDENT);
        }else if(event.getTarget().equals(editStudentButton)){
            openDialogEditUser(true, STUDENT);
        }else if(event.getTarget().equals(delStudentButton)) {
            if(studentTable.getSelectionModel().getSelectedItem()!=null){
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Usuwanie Ucznia?");
                alert.setHeaderText("Czy na pewno chcesz usunąć?");
                alert.setContentText("ucznia: "+ studentTable.getSelectionModel().getSelectedItem().getName() + " " + studentTable.getSelectionModel().getSelectedItem().getSurname());

                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    UserDAO.deleteUser(studentTable.getSelectionModel().getSelectedItem().getUser_id());
                    searchStudents();
                } else {
                    // ... user chose CANCEL or closed the dialog
                }
            }
        }else if(event.getTarget().equals(addTeacherButton)){
            openDialogEditUser(false, TEACHER);
        }else if(event.getTarget().equals(editTeacherButton)){
            openDialogEditUser(true, TEACHER);
        }else if(event.getTarget().equals(delTeacherButton)){
            if(teacherTable.getSelectionModel().getSelectedItem()!=null){
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Usuwanie nauczyciela");
                alert.setHeaderText("Czy na pewno chcesz usunąć?");
                alert.setContentText("nauczyciela: "+ teacherTable.getSelectionModel().getSelectedItem().getName() + " " + teacherTable.getSelectionModel().getSelectedItem().getSurname());

                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    UserDAO.deleteUser(teacherTable.getSelectionModel().getSelectedItem().getUser_id());
                    searchTeachers();
                } else {
                    // ... user chose CANCEL or closed the dialog
                }
            }
        }else if(event.getTarget().equals(addSubjectButton)){
            openDialogEditUser(false, SUBJECT);
        }else if(event.getTarget().equals(editSubjectButton)){
            openDialogEditUser(true, SUBJECT);
        }else if(event.getTarget().equals(delSubjectButton)){
            if(subjectTable.getSelectionModel().getSelectedItem()!=null){
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Usuwanie Przedmiotu");
                alert.setHeaderText("Czy na pewno chcesz usunąć?");
                alert.setContentText("przedmiot: "+ subjectTable.getSelectionModel().getSelectedItem().getName());

                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    SubjectDAO.deleteSubject(subjectTable.getSelectionModel().getSelectedItem().getSubject_id());
                    searchSubjects();
                } else {
                    // ... user chose CANCEL or closed the dialog
                }
            }
        }
    }

    private boolean checkIfCellNotNull(int editType){
        switch (editType){
            case STUDENT:
                if(!studentTable.getSelectionModel().isEmpty()) return true;
                break;
            case TEACHER:
                if(!teacherTable.getSelectionModel().isEmpty()) return true;
                break;
            case SUBJECT:
                if(!subjectTable.getSelectionModel().isEmpty()) return true;
                break;
        }
        return false;
    }

    private void openDialogEditUser(boolean isEdit, int editType){
        if(checkIfCellNotNull(editType) || !isEdit) {
            User user = null;
            Subject subject = null;
            switch (editType){
                case STUDENT:
                    user = studentTable.getSelectionModel().getSelectedItem();
                    break;
                case TEACHER:
                    user = teacherTable.getSelectionModel().getSelectedItem();
                    break;
                case SUBJECT:
                    subject = subjectTable.getSelectionModel().getSelectedItem();
                    break;
            }

            FXMLLoader loader = new FXMLLoader();
            if (editType == STUDENT || editType == TEACHER) {
                loader.setLocation(AdminPanelController.class.getResource("../view/editUser.fxml"));
                try {
                    loader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                EditUserController editUserController = loader.getController();
                if (isEdit && user!=null) {
                    editUserController.setValues(user.getUser_id(), user.getName(), user.getSurname(), user.getLogin(), user.getPass(), user.getEmail(), user.getPhone(), user.getRank());
                }else{
                    editUserController.setValues(editType);
                }
            } else if (editType == SUBJECT) {
                loader.setLocation(AdminPanelController.class.getResource("../view/editSubject.fxml"));
                try {
                    loader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (isEdit && subject!=null) {
                    //TODO
                    EditSubjectController editSubjectController = loader.getController();
                    editSubjectController.setValues(subject.getSubject_id(), subject.getName(), subject.getDescription());
                }
            }
            Parent parent = loader.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene(parent));
            stage.setOnCloseRequest(event -> {
                searchStudents();
                searchTeachers();
                searchSubjects();
            });
            //stage.setAlwaysOnTop(true);

            stage.showAndWait();
        }
    }
}
