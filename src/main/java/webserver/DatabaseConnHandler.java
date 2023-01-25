package webserver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnHandler {
    public static void dbRun(){
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/was?serverTimezone=UTC",
                    "root","19450000");
            System.out.println("success");
            Statement stmt = conn.createStatement();

        }catch (SQLException ex){
            System.out.println("sqlExceoption occured : "+ ex);
        }
    }

}
