package pl.schoolManagement.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import pl.schoolManagement.model.UserDAO;

public class EditUserController {
    @FXML
    Button okButton;
    @FXML
    Button cancelButton;
    @FXML
    Button changePassButton;

    @FXML
    Label idField;
    @FXML
    TextField nameField;
    @FXML
    TextField surnameField;
    @FXML
    TextField loginField;
    @FXML
    PasswordField passwordField;
    @FXML
    TextField emailField;
    @FXML
    TextField phoneField;
    @FXML
    TextField rankField;

    private boolean isEdit = false;

    @FXML
    public void handleButton(ActionEvent event){
        if(event.getTarget().equals(okButton)){
            if(notNull() && validate()){
                if(isEdit){
                    int id = Integer.parseInt(this.idField.getText());
                    String login = this.loginField.getText();
                    String pass = this.passwordField.getText();
                    int rank = Integer.valueOf(this.rankField.getText());
                    String name = this.nameField.getText();
                    String surname = this.surnameField.getText();
                    String email = this.emailField.getText();
                    int phone = Integer.valueOf(this.phoneField.getText());
                    UserDAO.updateUser(id, login, pass, rank, name, surname, email, phone);
                    //UserDAO.updateUser(LoginSingleton.getInstance().getUser().getUser_id(), this.loginField.getText(), this.passwordField.getText(), 100, this.nameField.getText(), this.surnameField.getText(), this.emailField.getText(), Integer.getInteger(this.phoneField.getText()));
                    Stage stage = (Stage) okButton.getScene().getWindow();
                    stage.fireEvent(
                            new WindowEvent(
                                    stage,
                                    WindowEvent.WINDOW_CLOSE_REQUEST
                            )
                    );
                }else{
                    if (UserDAO.isLoginFree(this.loginField.getText())){
                        UserDAO.insertUser(this.loginField.getText(), this.passwordField.getText(), Integer.parseInt(this.rankField.getText()), this.nameField.getText(), this.surnameField.getText(), this.emailField.getText(), Integer.parseInt(this.phoneField.getText()));

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
        }else if(event.getTarget().equals(changePassButton)){
            passwordField.setDisable(false);
        }
    }

    private boolean validate(){
        try {
            int number = Integer.parseInt(this.rankField.getText());
            number = Integer.parseInt(this.phoneField.getText());
            if(!this.idField.getText().equals("nowy")) number = Integer.parseInt(this.idField.getText());
            return true;
        }
        catch(NumberFormatException e) {
            System.out.println("Validation error");
        }
        return false;
    }

    private boolean notNull(){
        return (this.nameField.getText().isEmpty() || this.surnameField.getText().isEmpty() || this.loginField.getText().isEmpty() || this.passwordField.getText().isEmpty() || this.emailField.getText().isEmpty() || this.phoneField.getText().isEmpty()) ? false : true;
    }

    @FXML
    public void setValues(int id, String name, String surname, String login, String pass, String email, int phone, int rank){
        this.idField.setText(Integer.toString(id));
        this.nameField.setText(name);
        this.surnameField.setText(surname);
        this.loginField.setText(login);
        this.passwordField.setText(pass);
        this.emailField.setText(email);
        this.phoneField.setText(Integer.toString(phone));
        this.rankField.setText(Integer.toString(rank));
        this.loginField.setDisable(true);
        this.isEdit = true;
    }
    @FXML
    public void setValues(int rank){
        this.rankField.setText(Integer.toString(rank));
        this.isEdit = false;
    }
}
