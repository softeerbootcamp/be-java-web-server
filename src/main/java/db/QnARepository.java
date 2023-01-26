package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QnARepository {

    private final DBManager dbManager;
    private static QnARepository instance;

    public static QnARepository getInstance() {
        if (instance == null) {
            synchronized (QnARepository.class) {
                instance = new QnARepository();
            }
        }
        return instance;
    }

    public QnARepository(){
        this.dbManager = DBManager.getInstance();
    }

    public List<Map<String,String>> selectAll() {
        List<Map<String,String>> result = new ArrayList<>();

        try(Connection conn = dbManager.getConnection()){
            // SQL 실행
            // - SQL 작성
            // writer=1234&title=1234&contents=1234
            String sql = "SELECT _id, writer, title, contents, time FROM qna_table ORDER BY time DESC";
            // - PreparedStatement: SQL을 DBMS에서 실행할 준비
            try(PreparedStatement ps = conn.prepareStatement(sql)){
                ResultSet rs = ps.executeQuery();
                // executeQuery(): 준비된 SQL 실행. 실행된 결과는 DBMS에 있다.
                // ResultSet: DBMS 안에 있는 실행결과를 참조

                while(rs.next()){   // rs.next(): 레코드 한 줄의 실행결과를 가져옴(boolean)
                    // rs가 읽어들인 레코드에 속한 컬럼 쪼개기
                    //Integer id = rs.getRow(); // row number
                    Integer id = rs.getInt("_id");
                    String writer = rs.getString("writer");
                    String title = rs.getString("title");
                    String contents = rs.getString("contents");
                    String time = rs.getString("time");

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

    public Map<String,String> selectOne(String id) {
        Map<String,String> elem = new HashMap<>();
        // 트랜잭션 시작
        // try( connection 얻어오기 ) -> 이렇게 하면 connection이 완료된 뒤에 자동으로 close된다.
        try(Connection conn = dbManager.getConnection()){
            // 데이터베이스명: qna_db
            // DB 연결 ID: root
            // DB Password: 1234
            //System.out.println(conn);   // null 또는 exception이 발생하면 연결 실패한 것
            System.out.println(conn.getClass().getName());  // Connection 인터페이스의 구현부를 알 수 있다.

            // SQL 실행
            // - SQL 작성
            // writer=1234&title=1234&contents=1234
            String sql = "SELECT _id, writer, title, contents, time FROM qna_table WHERE _id = ? ORDER BY time DESC";
            System.out.println(sql);

            // - PreparedStatement: SQL을 DBMS에서 실행할 준비
            try(PreparedStatement ps = conn.prepareStatement(sql)){
                ps.setString(1,id);
                ResultSet rs = ps.executeQuery();
                // executeQuery(): 준비된 SQL 실행. 실행된 결과는 DBMS에 있다.
                // ResultSet: DBMS 안에 있는 실행결과를 참조

                while(rs.next()){   // rs.next(): 레코드 한 줄의 실행결과를 가져옴(boolean)
                    // rs가 읽어들인 레코드에 속한 컬럼 쪼개기
                    //Integer id = rs.getRow(); // row number
                    Integer rowId = rs.getInt("_id");
                    String writer = rs.getString("writer");
                    String title = rs.getString("title");
                    String contents = rs.getString("contents");
                    String time = rs.getString("time");

                    elem.put("row_id",rowId.toString());
                    elem.put("writer",writer);
                    elem.put("title",title);
                    elem.put("contents",contents);
                    elem.put("time",time);
                }
                rs.close();
            }catch (Exception ex){
                System.out.println("SQL 실행 오류: " + ex);
            }

        } catch(Exception ex){
            ex.printStackTrace();
        }

        return elem;
    }

    public void storeInfo(String writer, String title, String contents) {
        // 트랜잭션 시작
        // try( connection 얻어오기 ) -> 이렇게 하면 connection이 완료된 뒤에 자동으로 close된다.
        try(Connection conn = dbManager.getConnection()){
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
