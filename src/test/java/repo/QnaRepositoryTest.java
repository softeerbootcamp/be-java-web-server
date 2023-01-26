package repo;

import db.DBConnector;
import model.Qna;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import repository.QnaRepository;

import java.sql.Connection;
import java.time.LocalDateTime;

public class QnaRepositoryTest {

    @Test
    @DisplayName("qna 저장 잘되는지 테스트")
    public void saveTest() throws Exception {
        //given
        Qna qna  = new Qna(-1, "jieon", "spring boot", "how to learn spring boot", LocalDateTime.now().toString());
        Connection conn = DBConnector.connect();
        //when
        QnaRepository.getInstance().saveQna(qna, conn);
        Qna q = QnaRepository.getInstance().findOneByName("jieon", conn);
        //then
        Assertions.assertThat(q.getSubject()).isEqualTo("spring boot");
    }
}
