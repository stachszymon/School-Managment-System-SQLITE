package pl.schoolManagement.util;

import com.sun.rowset.CachedRowSetImpl;

import javax.sql.rowset.CachedRowSet;
import java.sql.*;

public class DBUtil {

    private static final String DATABASENAME = "jdbc:sqlite:schoolbase.db";
    private static final String DATABASEDRIVER = "org.sqlite.JDBC";
    //Connection
    private static Connection conn = null;

    public static void dbConnect() throws SQLException, ClassNotFoundException {
        //Sprawdzanie czy jest sterownik i czy działa
        try{
            Class.forName(DATABASEDRIVER);
        }catch (ClassNotFoundException e){
            System.out.println("Where is your Oracle JDBC Driver?");
            e.printStackTrace();
            throw e;
        }

        //Właściwe łącznie z bazą danych
        try{
            conn = DriverManager.getConnection(DATABASENAME);
        }catch (SQLException e){
            System.out.println("Connection Failed! Check output console" + e);

            e.printStackTrace();
            throw e;
        }
    }

    public static void dbDisconnect() throws SQLException {
        try {
            if (conn != null && !conn.isClosed()){
                conn.close();
            }
        } catch (SQLException e){
            throw e;
        }
    }

    public static boolean tableExists(String tableName){
        try{
            dbConnect();
            DatabaseMetaData md = conn.getMetaData();
            ResultSet rs = md.getTables(null, null, tableName, null);
            rs.next();
            dbDisconnect();
            return rs.getRow() > 0;
        }catch(ClassNotFoundException | SQLException ex){
            ex.printStackTrace();
        }
        return false;
    }

    //metoda do obsługi zapytan do bazy danych
    public static ResultSet dbExecuteQuery(String queryStmt) throws SQLException, ClassNotFoundException{
        Statement stmt = null;
        ResultSet resultSet = null;
        CachedRowSetImpl cachedRowSet = null;

        try{
            dbConnect();
            System.out.println("Select statement: "+queryStmt+"\n");

            //stworzenie zapytania
            stmt = conn.createStatement();

            //zapisanie wykonania zapytania do zmiennej
            resultSet = stmt.executeQuery(queryStmt);

            //CachedRowSet Implementation
            //In order to prevent "java.sql.SQLRecoverableException: Closed Connection: next" error
            //We are using CachedRowSet
            cachedRowSet = new CachedRowSetImpl();
            cachedRowSet.populate(resultSet);

        }catch (SQLException e){
            System.out.println("Problem occurred at executeQuery operation: " +e);
            throw e;
        }finally {
            if (resultSet != null){
                resultSet.close();
            }
            if (stmt != null){
                stmt.close();
            }
            dbDisconnect();
        }
        return cachedRowSet;
    }

    //metoda do wpiswania danych do bazy danych
    public static void dbExecuteUpdate(String sqlStmt) throws SQLException, ClassNotFoundException{
        Statement stmt = null;
        try {
            dbConnect();
            stmt = conn.createStatement();
            stmt.executeUpdate(sqlStmt);
        }catch (SQLException e){
            System.out.println("Problem occurred at executeUpdate operation : " + e);
            throw e;
        }finally {
            if (stmt != null){
                stmt.close();
            }
            dbDisconnect();
        }
    }
}
