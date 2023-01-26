package com.example.demo.repository;

import org.junit.jupiter.api.Test;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class boardRepositoryTest {

    private static final String url = "jdbc:mysql://localhost:3306/board";
    private static final String id = "root";
    private static final String password = "1234";

    Connection con = null;

    @Test
    void insert() {

        try{
            PreparedStatement preparedStatement = null;

            con = DriverManager.getConnection(url, id, password);
        }
        catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
        }

    }


    public List<Object> selectSample(String sql) {

        Statement st = null;

        Map<String, Object> tempMap = new HashMap<String, Object>();
        List<Object> resultList = new ArrayList<Object>();

        try
        {
            st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            //System.out.println("Query : " + sql);

            while (rs.next())
            {
                for (int i = 0; i < rs.getMetaData().getColumnCount(); i++)
                {
                    tempMap.put(rs.getMetaData().getColumnName(i+1), rs.getString(rs.getMetaData().getColumnName(i+1)));
                }

                resultList.add(tempMap);
                tempMap = new HashMap<>();    // tempMap reset
            }

            rs.close(); st.close();
        }
        catch (SQLException se1)
        {
            se1.printStackTrace();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        finally
        {
            try
            {
                if (st != null)
                {
                    st.close();
                }
            }
            catch (SQLException se2)
            {
                se2.printStackTrace();
            }
        }

        return resultList;
    }

}
