package pl.schoolManagement.controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.FlowPane;
import pl.schoolManagement.model.GradeButton;
import pl.schoolManagement.model.GradeDAO;
import pl.schoolManagement.model.Subject;
import pl.schoolManagement.model.SubjectDAO;
import pl.schoolManagement.singleton.LoginSingleton;

import java.sql.SQLException;
import java.util.ArrayList;

public class StudentPanelController {

    @FXML
    ListView<Subject> studentListView;
    @FXML
    FlowPane studentFlowPane;
    @FXML
    Label gradeDescLabel, gradeMultipleLabel, gradeTeacherLabel, gradeDateLabel;


    public void initialize() {

        studentListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Subject>() {
            @Override
            public void changed(ObservableValue<? extends Subject> observable, Subject oldValue, Subject newValue) {
                Subject subject = (Subject) studentListView.getSelectionModel().getSelectedItem();
                studentFlowPane.getChildren().clear();
                clearLabels();
                try {
                    ArrayList<GradeButton> gradeButtons = GradeDAO.searchStudentGrades(LoginSingleton.getInstance().getUser().getUser_id(), subject.getSubject_id());
                    fillGradeButtons(gradeButtons);
                }catch (SQLException | ClassNotFoundException e){
                    System.out.println(e);
                }
            }
        });

        fillListView();
    }

    private void fillListView() {
        try {
            ObservableList<Subject> subjects = SubjectDAO.searchUserSubjects(LoginSingleton.getInstance().getUser().getUser_id());
            studentListView.setItems(subjects);
            studentListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
            studentListView.getSelectionModel().selectFirst();
        }catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    private void fillGradeButtons(ArrayList<GradeButton> gradeButtons){
        for(GradeButton gradeButton: gradeButtons){ //petla for each
            gradeButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    gradeDescLabel.setText(gradeButton.getDescription());
                    gradeDateLabel.setText(gradeButton.getDate());
                    gradeMultipleLabel.setText(Integer.toString(gradeButton.getMultiple()));
                    gradeTeacherLabel.setText(gradeButton.getTeacher_name());
                }
            });
            studentFlowPane.getChildren().add(gradeButton);
        }
    }

    private void clearLabels(){
        gradeDateLabel.setText("");
        gradeDescLabel.setText("");
        gradeMultipleLabel.setText("");
        gradeTeacherLabel.setText("");
    }
}
