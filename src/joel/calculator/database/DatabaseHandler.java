package joel.calculator.database;

import java.sql.*;
import java.util.Date;

public class DatabaseHandler extends Config {
    private Connection dbConnection;

    public Connection getDbConnection() throws ClassNotFoundException, SQLException {
        String connectionString = "jdbc:mysql://" + dbHost + ":"
                + dbPort + "/" + dbName + dbTimezone;
        Class.forName("com.mysql.cj.jdbc.Driver");
        dbConnection = DriverManager.getConnection(connectionString, dbUser, dbPass);
        return dbConnection;
    }

    public void signUpHistory(String expression, String result, Date date) {
        String insert = "INSERT INTO " + Const.HISTORY_TABLE + " (" + Const.HISTORY_EXPRESSION + ","
                + Const.HISTORY_RESULT + "," + Const.HISTORY_DATE + ")"
                + "VALUES(?,?,?)";
        java.sql.Date nowDate = new java.sql.Date(date.getTime());
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(insert);
            prSt.setString(1, expression);
            prSt.setString(2, result);
            prSt.setDate(3, nowDate);
            prSt.executeUpdate();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
}
