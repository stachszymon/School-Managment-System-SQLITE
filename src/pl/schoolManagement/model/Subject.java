package pl.schoolManagement.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Subject {

    private IntegerProperty subject_id;
    private StringProperty name;
    private StringProperty description;

    public Subject() {
        this.subject_id = new SimpleIntegerProperty();
        this.name = new SimpleStringProperty();
        this.description = new SimpleStringProperty();
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

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
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

    @Override
    public String toString() {
        return this.name.get();
    }
}
