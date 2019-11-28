package pl.schoolManagement.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import pl.schoolManagement.model.User;
import pl.schoolManagement.model.UserDAO;
import pl.schoolManagement.singleton.LoginSingleton;
import java.io.IOException;
import java.sql.SQLException;

public class RootPanelController {

    @FXML
    private Label bot_name, bot_surname, bot_rank;

    @FXML
    private void initialize(){
        LoginSingleton ls = LoginSingleton.getInstance();
        bot_name.setText(ls.getUser().getName());
        bot_surname.setText(ls.getUser().getSurname());
        writeRank(ls.getUser().getRank());
    }

    private void writeRank(int rank){
        if(rank==0){
            bot_rank.setText("Student");
            bot_rank.setTextFill(Paint.valueOf("#FF0000"));
        }else if(rank == 1){
            bot_rank.setText("Nauczyciel");
            bot_rank.setTextFill(Paint.valueOf("#009000"));
        }else if(rank > 1){
            bot_rank.setText("Administrator");
            bot_rank.setTextFill(Paint.valueOf("#860000"));
        }
    }

    public void handleEditUser(ActionEvent actionEvent){
        User user = LoginSingleton.getInstance().getUser();

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(AdminPanelController.class.getResource("../view/editUser.fxml"));
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        EditUserController editUserController = loader.getController();
        editUserController.setValues(user.getUser_id(), user.getName(), user.getSurname(), user.getLogin(), user.getPass(), user.getEmail(), user.getPhone(), user.getRank());

        Parent parent = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(parent));
        stage.setOnCloseRequest(event -> {
            try {
                User userr = UserDAO.searchStudent(LoginSingleton.getInstance().getUser().getUser_id());
                if(userr!=null){
                    LoginSingleton.getInstance().setUser(userr);
                    LoginSingleton ls = LoginSingleton.getInstance();
                    bot_name.setText(ls.getUser().getName());
                    bot_surname.setText(ls.getUser().getSurname());
                    writeRank(ls.getUser().getRank());
                }
            }catch (SQLException | ClassNotFoundException e){
                e.printStackTrace();
            }
        });
        stage.showAndWait();

    }

    public void handleLogout(ActionEvent actionEvent){
        Stage stage = (Stage) bot_name.getScene().getWindow();
        stage.close();

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(AdminPanelController.class.getResource("../view/mainLogin.fxml"));
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Parent parent = loader.getRoot();
        stage = new Stage();
        stage.setScene(new Scene(parent));
        stage.showAndWait();

    }

    public void handleExit(ActionEvent actionEvent){
        System.exit(0);
    }

    public void handleAbout(ActionEvent actionEvent){
        Alert alert = new Alert (Alert.AlertType.INFORMATION);
        alert.setTitle("Informacje o programie");
        alert.setHeaderText("Prosty program elektronicznego dziennika");
        alert.setContentText("Program JavaFX napisany przez Dominika Gacka \nśrodowisko: InteliJ Edu \nbaza danych: SQLite");
        alert.show();
    }
}
