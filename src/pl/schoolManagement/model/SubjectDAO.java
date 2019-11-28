package pl.schoolManagement.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.sqlite.core.DB;
import pl.schoolManagement.util.DBUtil;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SubjectDAO {

    //||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||
    //pobieranie jednego przedmiotu
    //||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||
    public static Subject searchSubject(String subject_id) throws SQLException, ClassNotFoundException{
        String stmt = "SELECT * FROM subjects WHERE subject_id="+subject_id;
        try{
            ResultSet resultSet = DBUtil.dbExecuteQuery(stmt);
            Subject subject = getSubject(resultSet);
            return subject;
        }catch (SQLException e){
            e.printStackTrace();
            throw e;
        }
    }
    //formatowanie jednego przedmiotu
    private static Subject getSubject(ResultSet resultSet) throws SQLException{
        Subject subject = null;
        if(resultSet.next()){
            subject = new Subject();
            subject.setSubject_id(resultSet.getInt("subject_id"));
            subject.setName(resultSet.getString("name"));
            subject.setDescription(resultSet.getString("description"));
        }
        return subject;
    }
    //||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||
    //pobierz przedmiotów
    //||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||
    public static ObservableList<Subject> searchSubjects() throws SQLException, ClassNotFoundException {
        String stmt = "SELECT * FROM subjects";
        try{
            ResultSet resultSet = DBUtil.dbExecuteQuery(stmt);
            ObservableList<Subject> subjectsList = getSubjectsList(resultSet);
            return subjectsList;
        }catch (SQLException e){
            System.out.println("SQL select operation has been failed: " + e);
            throw e;
        }
    }
    private static ObservableList<Subject> getSubjectsList(ResultSet resultSet) throws SQLException, ClassNotFoundException{

        ObservableList<Subject> subjectsList = FXCollections.observableArrayList();
        while (resultSet.next()){
            Subject subject = new Subject();
            subject.setSubject_id(resultSet.getInt("subject_id"));
            subject.setName(resultSet.getString("name"));
            subject.setDescription(resultSet.getString("description"));
            subjectsList.add(subject);
        }
        return subjectsList;
    }
    //------------------------------------------------------------------------------------------------------------------

    //------------------------------------------------------------------------------------------------------------------
    public static ObservableList<Subject> searchUserSubjects(int user_id) throws SQLException, ClassNotFoundException{
        String stmt = "SELECT DISTINCT subjects.subject_id, subjects.name FROM subjects INNER JOIN whoseSubject ON subjects.subject_id=whoseSubject.subject_id where whoseSubject.user_id="+user_id;
        try {
            ResultSet resultSet = DBUtil.dbExecuteQuery(stmt);
            ObservableList<Subject> subjects = getSimpleSubjects(resultSet);//
            return subjects;//
        }catch (SQLException e){
            System.out.println("SQL select operation has been failed: " + e);
            throw e;
        }
    }
    private static ObservableList<Subject> getSimpleSubjects(ResultSet resultSet) throws SQLException, ClassNotFoundException{
        ObservableList<Subject> subjects = FXCollections.observableArrayList();
        while (resultSet.next()){
            Subject subject = new Subject();
            subject.setSubject_id(resultSet.getInt("subject_id"));
            subject.setName(resultSet.getString("name"));
            subjects.add(subject);
        }
        return subjects;
    }

    public static ObservableList<User> searchUserSubject(int subject_id, int rank) throws SQLException, ClassNotFoundException{
        String stmt = "SELECT whoseSubject.user_id, users.name, users.surname FROM whoseSubject INNER JOIN  users ON whoseSubject.user_id = users.user_id WHERE users.rank="+rank+" AND subject_id="+Integer.toString(subject_id);
        try {
            ResultSet resultSet = DBUtil.dbExecuteQuery(stmt);
            ObservableList<User> users = getUserSubject(resultSet);
            return users;
        }catch (SQLException e){
            System.out.println("SQL select operation has been failed: " + e);
            throw e;
        }
    }

    private static ObservableList<User> getUserSubject(ResultSet resultSet) throws SQLException, ClassNotFoundException {
        ObservableList<User> users = FXCollections.observableArrayList();
        while (resultSet.next()){
            User user = new User();
            user.setUser_id(resultSet.getInt("user_id"));
            user.setName(resultSet.getString("name"));
            user.setSurname(resultSet.getString("surname"));
            users.add(user);
        }
        return users;
    }
    //------------------------------------------------------------------------------------------------------------------
    //
    //------------------------------------------------------------------------------------------------------------------
    public static ObservableList<User> searchUsersNotInSubject(int subject_id, int rank) throws SQLException, ClassNotFoundException{
        String stmt = "SELECT DISTINCT users.user_id, users.name, users.surname FROM users \n" +
                "INNER JOIN whoseSubject\n" +
                "WHERE NOT whoseSubject.subject_id="+subject_id+" AND users.rank="+rank+";";
        try{
            ResultSet resultSet = DBUtil.dbExecuteQuery(stmt);
            ObservableList<User> userList = getUserSubject(resultSet);
            return userList;
        }catch (SQLException e) {
            System.out.println("SQL select operation has been failed: " + e);
            throw e;
        }
    }
    //------------------------------------------------------------------------------------------------------------------
        //INSERT SUBJECT
    //------------------------------------------------------------------------------------------------------------------
    public static void insertSubject(String name, String desc){
        String stmt = "INSERT INTO subjects (name, description) VALUES " +
                "('"+name+"', "+
                "'"+desc+"') ";
        try {
            DBUtil.dbExecuteUpdate(stmt);
        } catch (SQLException | ClassNotFoundException e) {
            System.out.print("Error occurred while INSERT Operation: " + e);
        }
    }
    //------------------------------------------------------------------------------------------------------------------
        //UPDATE SUBJECT
    //------------------------------------------------------------------------------------------------------------------
    public static void updateSubject(int id, String name, String desc){
        String stmt = "UPDATE subjects SET " +
                "name='"+name+"', "+
                "description='"+desc+"' "+
                "WHERE subject_id ="+id+";";
        try {
            DBUtil.dbExecuteUpdate(stmt);
        } catch (SQLException | ClassNotFoundException e) {
            System.out.print("Error occurred while UPDATE Operation: " + e);
        }
    }
    //------------------------------------------------------------------------------------------------------------------
        //DELETE SUBJAECT
    //------------------------------------------------------------------------------------------------------------------
    public static void deleteSubject(int id){
        String stmt = "DELETE FROM subjects WHERE subject_id ="+Integer.toString(id)+";";
        try{
            DBUtil.dbExecuteUpdate(stmt);
        }catch (SQLException | ClassNotFoundException e){
            System.out.println("Error occurred while DELETE Operatorion: " +e);
        }
    }

    //------------------------------------------------------------------------------------------------------------------
        //add user to subject
    //------------------------------------------------------------------------------------------------------------------
    public static void insertUserSubject(int user_id, int subject_id){
        String stmt = "INSERT INTO whoseSubject (user_id, subject_id) VALUES " +
                "('"+user_id+"', "+
                "'"+subject_id+"') ";
        try {
            DBUtil.dbExecuteUpdate(stmt);
        } catch (SQLException | ClassNotFoundException e) {
            System.out.print("Error occurred while INSERT Operation: " + e);
        }
    }
    //------------------------------------------------------------------------------------------------------------------
        //delete user from subject
    //------------------------------------------------------------------------------------------------------------------
    public static void deleteUserSubject(int user_id, int subject_id){
        String stmt = "DELETE FROM whoseSubject WHERE subject_id ="+subject_id+" AND user_id="+user_id+";";
        try{
            DBUtil.dbExecuteUpdate(stmt);
        }catch (SQLException | ClassNotFoundException e){
            System.out.println("Error occurred while DELETE Operatorion: " +e);
        }
    }

    public static boolean isNameFree(String name){
        String stmt = "SELECT * FROM subjects WHERE name='"+name+"';";
        try{
            ResultSet resultSet = DBUtil.dbExecuteQuery(stmt);
            if(resultSet.next()) {
                return false;
            }else{
                return true;
            }
        }catch (SQLException | ClassNotFoundException e){
            System.out.println("SQL select operation has been failed: " + e);
        }
        return true;
    }
}
