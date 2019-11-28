package pl.schoolManagement.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import pl.schoolManagement.MainLogin;
import pl.schoolManagement.model.User;
import pl.schoolManagement.singleton.LoginSingleton;
import pl.schoolManagement.util.DBDAO;
import pl.schoolManagement.util.DBUtil;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;


public class MainLoginController {

    @FXML
    TextField loginField;
    @FXML
    PasswordField passField;
    @FXML
    Button loginButton;
    @FXML
    Label errorLabel;
    @FXML
    Label dbStatusText;

    private BorderPane rootLayout;

    public void initialize(){
        System.out.println("initialize MainLoginController.java");

        loginButton.setDisable(true);
        errorLabel.setVisible(false);

        connectDB();
    }

    public void connectDB(){
        try {

            /* Wywołujemy metodę dbConnect z obiektu DBUtil, którą będziemy mogli się połączyć z bazą */

            DBUtil.dbConnect();

            /* Jeśli połączenie zostanie nawiązane to ma się wyświetlić zielony napis connected */

            dbStatusText.setText("connected");
            dbStatusText.setTextFill(Color.GREEN);

            // Ustaw przycisk logowania na "wyłączony - NIE", gdyż nie ma czegoś takiego jak .setEnable

            loginButton.setDisable(false);

            //Rozłączamy bazę, przechodzimy na Offline

            DBUtil.dbDisconnect();

            //Tworzymy sobie zmienną i, która będzie wskazywać na ilość tabel

            short i = DBDAO.createTables();

            /* Tworzymy pętle if, która spróbuje utworzyć nam bazę danych,
            założyć konto admina a jak jej się nie uda to na czerwono wyświetli nam błąd połączenia z bazą */

            if(i>0){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Pierwsze uruchomienie");
                alert.setHeaderText(null);
                alert.setContentText("Pomyślnie utworzono baze danych oraz "+ i + " tabele\nUtworzono domyślne konto administratora\nlogin: admin\nhasło: admin");

                alert.showAndWait();

                alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Przykładowe dane");
                alert.setHeaderText("Przykładowe dane");
                alert.setContentText("Zdecycuj czy chcesz uzupełnić tabele przykładowymi danymi?\n");

                ButtonType okButton = new ButtonType("Tak", ButtonBar.ButtonData.YES);
                ButtonType noButton = new ButtonType("Nie", ButtonBar.ButtonData.NO);
                alert.getButtonTypes().setAll(okButton, noButton);

                /* Jeśli wybierzemy przycisk ok to wypełnij proponowanymi danymi*/

                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == okButton){
                    DBDAO.populateTables();
                } else {
                    //TODO ... user chose CANCEL or closed the dialog
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            errorLabel.setText("Error occured while connecting to database!");
            errorLabel.setVisible(true);
            dbStatusText.setText("not connected");
            dbStatusText.setTextFill(Color.RED);
        }
    }

    public void loginEvent(){

        /* Gdy mamy ! to odwracamy warunek, więc poniższe parametry w pętli będą oznaczać, że jeżeli pole loginField NIE jest puste
        oraz pole passField NIE jest puste to ... */

        if(!loginField.getText().isEmpty() && !passField.getText().isEmpty()) {
            try {
                String statement = "SELECT * FROM users WHERE login='" + loginField.getText() + "' AND pass='" + passField.getText() + "'";
                ResultSet resultSet = DBUtil.dbExecuteQuery(statement);

                /* Poniższa pętla, jeżeli sprawdzi, że może przejść do kolejnej wartości w resultSet, czyli dostanie informacje,
                    że tabele są wypełnione (Czyli użytkownik istnieje i coś tam wypełnione już ma ) to zaczyta nam dane i uzupełni nimi nowy objekt User,
                    w przeciwnym wypadku najprawdopodobniej nie mamy takiego loginu i hasła w bazie */

                    if (resultSet.next()) {
                        User user = new User();
                        user.setUser_id(resultSet.getInt("user_id"));
                        user.setLogin(resultSet.getString("login"));
                        user.setPass(resultSet.getString("pass"));
                        int rank = resultSet.getInt("rank");
                        user.setRank(rank);
                        user.setName(resultSet.getString("name"));
                        user.setSurname(resultSet.getString("surname"));
                        user.setEmail(resultSet.getString("email"));
                        user.setPhone(resultSet.getInt("phone"));
                        LoginSingleton.getInstance().setUser(user); // uzupełnienie singletonu aktualnym uzytkownikiem

                        loadRoot(rank);
                } else {
                    //TODO error while login w label
                }

                // DOCZYTAC SOBIE PÓŹNIEJ !!!

            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }else{
            System.out.println("puste pola");
            //TODO wypisanie w label
        }
    }

    private void loadRoot(int rank){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainLogin.class.getResource("view/rootPanel.fxml"));
            rootLayout = (BorderPane) loader.load();
            loadPanel(rank);

            Stage stage = (Stage) loginButton.getScene().getWindow();
            stage.close();

            Scene scene = new Scene(rootLayout, 700, 400);
            stage = new Stage();
            stage.setTitle("School Management System");
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadPanel(int panelVersion){
        if(panelVersion==0) {
            try {
                FXMLLoader loader = new FXMLLoader(); //tworzymy nowa klase ladujcaa pliki fxml i ja inicjujemy
                loader.setLocation(MainLogin.class.getResource("view/studentPanel.fxml")); // podajemy w.w. klasie lokalizacje naszego pliku fxml
                AnchorPane studentPanel = (AnchorPane) loader.load(); // wlasciwa fukcja pobierajaca widok z pliku i ladujaca go do zmiennej studentPanel
                rootLayout.setCenter(studentPanel); //doklejanie widoku (kartki) do poprzedniej kartki
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else if(panelVersion==1) {
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(MainLogin.class.getResource("view/teacherPanel.fxml"));
                AnchorPane teacherPanel = (AnchorPane) loader.load();
                rootLayout.setCenter(teacherPanel);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else if(panelVersion>=10) {
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(MainLogin.class.getResource("view/adminPanel.fxml"));
                AnchorPane adminPanel = (AnchorPane) loader.load();
                rootLayout.setCenter(adminPanel);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void closeProgram(){
        System.exit(0);
    }
}
