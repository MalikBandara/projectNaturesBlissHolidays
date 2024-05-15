package lk.ijse.controller.databse;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static DBConnection dbConnection ;

    private static Connection connection;

    private DBConnection() throws SQLException {
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/NatureBlissHolidays",
                "root",
                "QWer12@*");
    }

    public static DBConnection getInstance() throws SQLException {
        if(dbConnection == null){
            dbConnection = new DBConnection();
        }
        return dbConnection;
    }
    public static Connection getConnection(){
        return connection;
    }


}
