package db;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.sql.DriverManager.getConnection;

public class DBConnection {
    public static List<Map<String,String>> selectAll() {
        List<Map<String,String>> result = new ArrayList<>();
        // 트랜잭션 시작
        // try( connection 얻어오기 ) -> 이렇게 하면 connection이 완료된 뒤에 자동으로 close된다.
        try(Connection conn = getConnection("jdbc:mysql://127.0.0.1:3306/qna_db?useSSL=false&serverTimezone=Asia/Seoul&useUnicode=true&character_set_server-utf8mb4","root","1234")){
            // 데이터베이스명: qna_db
            // DB 연결 ID: root
            // DB Password: 1234
            //System.out.println(conn);   // null 또는 exception이 발생하면 연결 실패한 것
            System.out.println(conn.getClass().getName());  // Connection 인터페이스의 구현부를 알 수 있다.

            // SQL 실행
            // - SQL 작성
            // writer=1234&title=1234&contents=1234
            String sql = "SELECT writer, title, contents, time FROM qna_table";
            System.out.println(sql);

            // - PreparedStatement: SQL을 DBMS에서 실행할 준비
            try(PreparedStatement ps = conn.prepareStatement(sql)){
                ResultSet rs = ps.executeQuery();
                // executeQuery(): 준비된 SQL 실행. 실행된 결과는 DBMS에 있다.
                // ResultSet: DBMS 안에 있는 실행결과를 참조

                while(rs.next()){   // rs.next(): 레코드 한 줄의 실행결과를 가져옴(boolean)
                    // rs가 읽어들인 레코드에 속한 컬럼 쪼개기
                    Integer id = rs.getRow(); // row number
                    String writer = rs.getString("writer");
                    String title = rs.getString("title");
                    String contents = rs.getString("contents");
                    String time = rs.getString("time");
                    System.out.println(id+","+writer + "," + title + "," + contents+","+time);
                    /*
                    3223,2523,253225
                    1234,1234,1234
                    1234,1234,1234
                    125,1235,1235
                    sgds,fyf,xf
                     */

                    Map<String,String> elem = new HashMap<>();
                    elem.put("row_id",id.toString());
                    elem.put("writer",writer);
                    elem.put("title",title);
                    elem.put("contents",contents);
                    elem.put("time",time);

                    result.add(elem);
                }
                rs.close();
            }catch (Exception ex){
                System.out.println("SQL 실행 오류: " + ex);
            }

        } catch(Exception ex){
            ex.printStackTrace();
        }

        return result;
    }

    public static void storeInfo(String writer, String title, String contents) {
        // 트랜잭션 시작
        // try( connection 얻어오기 ) -> 이렇게 하면 connection이 완료된 뒤에 자동으로 close된다.
        try(Connection conn = getConnection("jdbc:mysql://127.0.0.1:3306/qna_db?useSSL=false&serverTimezone=Asia/Seoul&useUnicode=true&character_set_server-utf8mb4","root","1234")){
            // 데이터베이스명: qna
            // DB 연결 ID: root
            // DB Password: 1234
            //System.out.println(conn);   // null 또는 exception이 발생하면 연결 실패한 것
            System.out.println(conn.getClass().getName());  // Connection 인터페이스의 구현부를 알 수 있다.

            // SQL 실행
            // - SQL 작성
            // writer=1234&title=1234&contents=1234
            String sql = "insert into qna_table(writer, title, contents) values(?, ?, ?)";

            // - PreparedStatement: SQL을 DBMS에서 실행할 준비, PreparedStatement 객체 생성, 객체 생성시 sql 문장 저장
            try(PreparedStatement ps = conn.prepareStatement(sql)){

                ps.setString(1,writer);
                ps.setString(2,title);
                ps.setString(3,contents);

                int r = ps.executeUpdate();//SQL 문장을 실행하고 결과를 리턴 - SQL 문장 실행 후, 변경된 row 수 int type 리턴
                // ps.excuteQuery() : select
                // ps.excuteUpdate() : insert, update, delete ..
                // executeQuery(): 준비된 SQL 실행. 실행된 결과는 DBMS에 있다.

                System.out.println("변경된 row : " + r);

            }catch (Exception ex){
                System.out.println("SQL 실행 오류: " + ex);
            }

        } catch(Exception ex){
            ex.printStackTrace();
        }
    }

}
