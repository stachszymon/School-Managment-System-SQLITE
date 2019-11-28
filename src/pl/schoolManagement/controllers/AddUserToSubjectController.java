package pl.schoolManagement.controllers;

import com.sun.javafx.scene.control.TableColumnComparatorBase;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import pl.schoolManagement.model.Subject;
import pl.schoolManagement.model.SubjectDAO;
import pl.schoolManagement.model.User;
import pl.schoolManagement.model.UserDAO;

import java.sql.SQLException;

public class AddUserToSubjectController {

    @FXML
    Button okButton, cancelButton;
    @FXML
    TableView<User> usersTable;
    @FXML
    TableColumn<User, Integer> idCol;
    @FXML
    TableColumn<User, String> nameCol, surnameCol;

    private int subject_id;
    private int rank;

    @FXML
    private void initialize(){
        idCol.setCellValueFactory(cellData -> cellData.getValue().user_idProperty().asObject());
        nameCol.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        surnameCol.setCellValueFactory(cellData -> cellData.getValue().surnameProperty());
        usersTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }

    private void searchUsers(){
        try{
            ObservableList<User> users = null;
            //ObservableList<User> users = SubjectDAO.searchUsersNotInSubject(subject_id, rank);
            if(this.rank==0){
                users = UserDAO.searchStudents();
            }else if(this.rank==1) {
                users = UserDAO.searchTeachers();
            }
            usersTable.setItems(users);
        }catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    public void setValues(int subject_id, int rank){
        this.subject_id = subject_id;
        this.rank = rank;
        searchUsers();
    }

    @FXML
    public void handleButton(ActionEvent event){
        if(event.getTarget().equals(okButton)) {
            if(usersTable.getSelectionModel().getSelectedItem()!=null){
                SubjectDAO.insertUserSubject(usersTable.getSelectionModel().getSelectedItem().getUser_id(), subject_id);
                Stage stage = (Stage) okButton.getScene().getWindow();
                stage.fireEvent(
                        new WindowEvent(
                                stage,
                                WindowEvent.WINDOW_CLOSE_REQUEST
                        )
                );
            }
        }else if(event.getTarget().equals(cancelButton)){
            Stage stage = (Stage) cancelButton.getScene().getWindow();
            stage.close();
        }
    }
}
