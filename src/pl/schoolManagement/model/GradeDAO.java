package pl.schoolManagement.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import pl.schoolManagement.util.DBUtil;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class GradeDAO {


    public static ObservableList<Grade> searchTeacherGrades(int teacher_id, int subject_id){
        String stmt = "SELECT DISTINCT grades.grade_id, grades.value, grades.multiple, grades.student_id, users.name, users.surname, grades.subject_id, grades.date, grades.description " +
                "FROM grades INNER JOIN users WHERE users.user_id=grades.student_id AND teacher_id="+teacher_id+" AND subject_id="+subject_id;
        try{
            ResultSet resultSet = DBUtil.dbExecuteQuery(stmt);
            ObservableList<Grade> gradeButtons = getTeacherGrade(resultSet);
            return gradeButtons;
        }catch (SQLException | ClassNotFoundException e){
            System.out.println("SQL select operation has been failed: "+e);
        }
        return null;
    }
    private static ObservableList<Grade> getTeacherGrade(ResultSet resultSet) throws SQLException {
        ObservableList<Grade> grades = FXCollections.observableArrayList();
        while (resultSet.next()){
            Grade grade = new Grade();
            grade.setGrade_id(resultSet.getInt("grade_id"));
            grade.setValue(resultSet.getInt("value"));
            grade.setMultiple(resultSet.getInt("multiple"));
            grade.setStudent_id(resultSet.getInt("student_id"));
            grade.setStudent_name(resultSet.getString("name"));
            grade.setStudent_surname(resultSet.getString("surname"));
            grade.setSubject_id(resultSet.getInt("subject_id"));
            grade.setDate(resultSet.getString("date"));
            grade.setDescription(resultSet.getString("description"));
            grades.add(grade);
        }
        return grades;
    }

    public static ArrayList<GradeButton> searchStudentGrades(int user_id, int subject_id) throws SQLException, ClassNotFoundException{
        String stmt = "SELECT grades.grade_id, grades.value, grades.multiple, grades.student_id, users.name, users.surname, grades.subject_id, grades.date, grades.description FROM grades INNER JOIN users WHERE users.user_id=grades.teacher_id AND student_id="+user_id+" AND subject_id="+subject_id;
        try{
            ResultSet resultSet = DBUtil.dbExecuteQuery(stmt);
            ArrayList<GradeButton> gradeButtons = getStudentGradeButtons(resultSet);
            return gradeButtons;
        }catch (SQLException e){
            System.out.println("SQL select operation has been failed: "+e);
            throw e;
        }
    }

    private static ArrayList<GradeButton> getStudentGradeButtons(ResultSet resultSet) throws SQLException {
        ArrayList<GradeButton> gradeButtons = new ArrayList<GradeButton>();
        while (resultSet.next()){
            GradeButton gradeButton = new GradeButton();
            gradeButton.setGrade_id(resultSet.getInt("grade_id"));
            gradeButton.setValue(resultSet.getInt("value"));
            gradeButton.setMultiple(resultSet.getInt("multiple"));
            gradeButton.setStudent_id(resultSet.getInt("student_id"));
            String name = resultSet.getString("name") + " " + resultSet.getString("surname");
            gradeButton.setTeacher_name(name);
            gradeButton.setSubject_id(resultSet.getInt("subject_id"));
            gradeButton.setDate(resultSet.getString("date"));
            gradeButton.setDescription(resultSet.getString("description"));
            gradeButtons.add(gradeButton);
        }
        return gradeButtons;
    }

}
