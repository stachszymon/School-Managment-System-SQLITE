package pl.schoolManagement.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.css.SimpleStyleableIntegerProperty;


public class Grade {

    private IntegerProperty grade_id;
    private IntegerProperty value;
    private IntegerProperty multiple;
    private IntegerProperty student_id;
    private StringProperty student_name;
    private StringProperty student_surname;
    private IntegerProperty teacher_id;
    private IntegerProperty subject_id;
    private StringProperty date;
    private StringProperty description;

    public Grade() {
        this.grade_id = new SimpleIntegerProperty();
        this.value = new SimpleIntegerProperty();
        this.multiple = new SimpleIntegerProperty();
        this.student_id = new SimpleIntegerProperty();
        this.student_name = new SimpleStringProperty();
        this.student_surname = new SimpleStringProperty();
        this.teacher_id = new SimpleIntegerProperty();
        this.student_id = new SimpleIntegerProperty();
        this.subject_id = new SimpleIntegerProperty();
        this.date = new SimpleStringProperty();
        this.description = new SimpleStringProperty();
    }

    public int getGrade_id() {
        return grade_id.get();
    }

    public IntegerProperty grade_idProperty() {
        return grade_id;
    }

    public void setGrade_id(int grade_id) {
        this.grade_id.set(grade_id);
    }

    public int getValue() {
        return value.get();
    }

    public IntegerProperty valueProperty() {
        return value;
    }

    public void setValue(int value) {
        this.value.set(value);
    }

    public int getMultiple() {
        return multiple.get();
    }

    public IntegerProperty multipleProperty() {
        return multiple;
    }

    public void setMultiple(int multiple) {
        this.multiple.set(multiple);
    }

    public int getStudent_id() {
        return student_id.get();
    }

    public IntegerProperty student_idProperty() {
        return student_id;
    }

    public void setStudent_id(int student_id) {
        this.student_id.set(student_id);
    }

    public String getStudent_name() {
        return student_name.get();
    }

    public StringProperty student_nameProperty() {
        return student_name;
    }

    public void setStudent_name(String student_name) {
        this.student_name.set(student_name);
    }

    public String getStudent_surname() {
        return student_surname.get();
    }

    public StringProperty student_surnameProperty() {
        return student_surname;
    }

    public void setStudent_surname(String student_surname) {
        this.student_surname.set(student_surname);
    }

    public int getTeacher_id() {
        return teacher_id.get();
    }

    public IntegerProperty teacher_idProperty() {
        return teacher_id;
    }

    public void setTeacher_id(int teacher_id) {
        this.teacher_id.set(teacher_id);
    }

    public int getSubject_id() {
        return subject_id.get();
    }

    public IntegerProperty subject_idProperty() {
        return subject_id;
    }

    public void setSubject_id(int subject_id) {
        this.subject_id.set(subject_id);
    }

    public String getDate() {
        return date.get();
    }

    public StringProperty dateProperty() {
        return date;
    }

    public void setDate(String date) {
        this.date.set(date);
    }

    public String getDescription() {
        return description.get();
    }

    public StringProperty descriptionProperty() {
        return description;
    }

    public void setDescription(String description) {
        this.description.set(description);
    }
}
