package pl.schoolManagement.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class User {

    private IntegerProperty user_id;
    private StringProperty login;
    private StringProperty pass;
    private IntegerProperty rank;
    private StringProperty name;
    private StringProperty surname;
    private StringProperty email;
    private IntegerProperty phone;

    public User(){
        this.user_id = new SimpleIntegerProperty();
        this.login = new SimpleStringProperty();
        this.pass = new SimpleStringProperty();
        this.rank = new SimpleIntegerProperty();
        this.name = new SimpleStringProperty();
        this.surname = new SimpleStringProperty();
        this.email = new SimpleStringProperty();
        this.phone = new SimpleIntegerProperty();
    }

    public int getUser_id() {
        return user_id.get();
    }

    public IntegerProperty user_idProperty() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id.set(user_id);
    }

    public String getLogin() {
        return login.get();
    }

    public StringProperty loginProperty() {
        return login;
    }

    public void setLogin(String login) {
        this.login.set(login);
    }

    public String getPass() {
        return pass.get();
    }

    public StringProperty passProperty() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass.set(pass);
    }

    public int getRank() {
        return rank.get();
    }

    public IntegerProperty rankProperty() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank.set(rank);
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getSurname() {
        return surname.get();
    }

    public StringProperty surnameProperty() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname.set(surname);
    }

    public String getEmail() {
        return email.get();
    }

    public StringProperty emailProperty() {
        return email;
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    public int getPhone() {
        return phone.get();
    }

    public IntegerProperty phoneProperty() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone.set(phone);
    }

    @Override
    public String toString() {
        return this.name.get() + " " + this.surname.get();
    }
}
