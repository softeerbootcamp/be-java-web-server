package db;

import util.FileIoUtil;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Objects;

public class Database {
    private static final Database database = new Database();

    private Database() {
    }

    public static Database getInstance() {
        return database;
    }

    public Connection getConnection() throws Exception {
        String url = "jdbc:mysql://localhost:3306/jdbc";
        String name = null;
        String password = null;
        File file = new File(Objects.requireNonNull(FileIoUtil.class.getClassLoader().getResource("SQL_INFO.txt")).toURI());
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line;
        while ((line = br.readLine()) != null) {
            if(line.contains("name")) {
                name = line.split(":")[1];
            }
            if(line.contains("password")) {
                password = line.split(":")[1];
            }
        }
        return DriverManager.getConnection(url, name, password);
    }
}
