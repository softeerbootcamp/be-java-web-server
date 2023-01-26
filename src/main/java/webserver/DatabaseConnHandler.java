package webserver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnHandler {
    public static Connection dbConnection(){
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/was",
                    "root","19450000");
            System.out.println("success");
            return conn;

        }catch (SQLException ex){
            System.out.println("sqlExceoption occured : "+ ex);
            return null;
        }

    }
}
