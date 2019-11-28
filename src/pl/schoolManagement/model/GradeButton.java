package pl.schoolManagement.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Button;

public class GradeButton extends Button {

    private IntegerProperty grade_id;
    private IntegerProperty value;
    private IntegerProperty multiple;
    private IntegerProperty student_id;
    private StringProperty teacher_name;
    private IntegerProperty subject_id;
    private StringProperty date;
    private StringProperty description;

    public GradeButton() {
        this.grade_id = new SimpleIntegerProperty();
        this.value = new SimpleIntegerProperty();
        this.multiple = new SimpleIntegerProperty();
        this.student_id = new SimpleIntegerProperty();
        this.teacher_name = new SimpleStringProperty();
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
        super.setText(Integer.toString(value));
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

    public String getTeacher_name() {
        return teacher_name.get();
    }

    public StringProperty teacher_nameProperty() {
        return teacher_name;
    }

    public void setTeacher_name(String teacher_name) {
        this.teacher_name.set(teacher_name);
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