package pl.schoolManagement.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.sqlite.core.DB;
import pl.schoolManagement.util.DBUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.SortedMap;

public class UserDAO {

    //------------------------------------------------------------------------------------------------------------------
    //SELECT
    //------------------------------------------------------------------------------------------------------------------
    public static User searchStudent(int id) throws SQLException, ClassNotFoundException {
        String stmt = "SELECT * FROM users WHERE user_id='"+id+"';";
        try{
            ResultSet resultSet = DBUtil.dbExecuteQuery(stmt);
            User students = getUser(resultSet);
            return students;
        }catch (SQLException e){
            System.out.println("SQL select operation has been failed: " + e);
            throw e;
        }
    }
    //------------------------------------------------------------------------------------------------------------------
        //SELECT
    //------------------------------------------------------------------------------------------------------------------
    public static ObservableList<User> searchStudents() throws SQLException, ClassNotFoundException {
        String stmt = "SELECT * FROM users WHERE rank='0'";
        try{
            ResultSet resultSet = DBUtil.dbExecuteQuery(stmt);
            ObservableList<User> studentsList = getUserList(resultSet);
            return studentsList;
        }catch (SQLException e){
            System.out.println("SQL select operation has been failed: " + e);
            throw e;
        }
    }
    //pobierz nauczycielow
    public static ObservableList<User> searchTeachers() throws SQLException, ClassNotFoundException {
        String stmt = "SELECT * FROM users WHERE rank='1'";
        try{
            ResultSet resultSet = DBUtil.dbExecuteQuery(stmt);
            ObservableList<User> teachersList = getUserList(resultSet);
            return teachersList;
        }catch (SQLException e){
            System.out.println("SQL select operation has been failed: " + e);
            throw e;
        }
    }
    //formatowanie nauczycielwo
    private static ObservableList<User> getUserList(ResultSet resultSet) throws SQLException, ClassNotFoundException{
        ObservableList<User> teachersList = FXCollections.observableArrayList();
        while (resultSet.next()){
            User user = new User();
            user.setUser_id(resultSet.getInt("user_id"));
            user.setLogin(resultSet.getString("login"));
            user.setPass(resultSet.getString("pass"));
            user.setRank(resultSet.getInt("rank"));
            user.setName(resultSet.getString("name"));
            user.setSurname(resultSet.getString("surname"));
            user.setEmail(resultSet.getString("email"));
            user.setPhone(resultSet.getInt("phone"));
            teachersList.add(user);
        }
        return teachersList;
    }
    private static User getUser(ResultSet resultSet){
        User user = null;
        try {
            while (resultSet.next()) {
                user = new User();
                user.setUser_id(resultSet.getInt("user_id"));
                user.setLogin(resultSet.getString("login"));
                user.setPass(resultSet.getString("pass"));
                user.setRank(resultSet.getInt("rank"));
                user.setName(resultSet.getString("name"));
                user.setSurname(resultSet.getString("surname"));
                user.setEmail(resultSet.getString("email"));
                user.setPhone(resultSet.getInt("phone"));
                return user;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return user;
    }
    //------------------------------------------------------------------------------------------------------------------
        //UPDATE
    //------------------------------------------------------------------------------------------------------------------
    public static void updateUser(int id, String login, String pass, int rank, String name, String surname, String email, int phone) {
        String stmt = "UPDATE users SET " +
                "login='"+login+"', "+
                "pass='"+pass+"', "+
                "rank='"+rank+"', "+
                "name='"+name+"', "+
                "surname='"+surname+"', "+
                "email='"+email+"', "+
                "phone='"+phone+"' "+
                "WHERE user_id ="+id+";";
        try {
            DBUtil.dbExecuteUpdate(stmt);
        } catch (SQLException | ClassNotFoundException e) {
            System.out.print("Error occurred while UPDATE Operation: " + e);
        }
    }
    //------------------------------------------------------------------------------------------------------------------
        //INSERT
    //------------------------------------------------------------------------------------------------------------------
    public static void insertUser(String login, String pass, int rank, String name, String surname, String email, int phone) {
        String stmt = "INSERT INTO users (login, pass, rank, name, surname, email, phone) VALUES " +
                "('"+login+"', "+
                "'"+pass+"', "+
                "'"+rank+"', "+
                "'"+name+"', "+
                "'"+surname+"', "+
                "'"+email+"', "+
                "'"+phone+"') ";
        try {
            DBUtil.dbExecuteUpdate(stmt);
        } catch (SQLException | ClassNotFoundException e) {
            System.out.print("Error occurred while INSERT Operation: " + e);
        }
    }
    //------------------------------------------------------------------------------------------------------------------
        //DELETE
    //------------------------------------------------------------------------------------------------------------------
    public static void deleteUser(int id) {
        String stmt = "DELETE FROM users WHERE user_id ="+Integer.toString(id)+";";
        try{
            DBUtil.dbExecuteUpdate(stmt);
        }catch (SQLException | ClassNotFoundException e){
            System.out.println("Error occurred while DELETE Operatorion: " +e);
        }
    }

    public static boolean isLoginFree(String login){
        String stmt = "SELECT * FROM users WHERE login='"+login+"';";
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
