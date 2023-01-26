package db;

import model.Memo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MemoRepositoryTest {

    @DisplayName("전체 유저 탐색 테스트")
    @Test
    void MemoFindAllTest() {
        //given
        Memo memo1 = new Memo("memo1", "memo1", "memo1");
        Memo memo2 = new Memo("memo2", "memo2", "memo2");
        Memo memo3 = new Memo("memo3", "memo3", "memo3");

        //when
        MemoRepository.addMemo(memo1);
        MemoRepository.addMemo(memo2);
        MemoRepository.addMemo(memo3);

        //then
        Collection<Memo> memos = MemoRepository.findAll();
        assertEquals(memos.size(), 3);

        MemoRepository.deleteAll();
    }
}
