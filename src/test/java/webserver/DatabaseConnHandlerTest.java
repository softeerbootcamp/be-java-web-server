package webserver;

import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.*;

class DatabaseConnHandlerTest {

    @Test
    void dbConnection() {
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/was",
                    "root","19450000");
            System.out.println("success");
            Statement stmt = conn.createStatement();

        }catch (SQLException ex){
            System.out.println("sqlExceoption occured : "+ ex);
        }
    }
}