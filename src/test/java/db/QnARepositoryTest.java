package db;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

class QnARepositoryTest {

    QnARepository repository;

    @BeforeEach
    void setUp() {
        repository= QnARepository.getInstance();
    }

    @Test
    @DisplayName("DB 데이터 전체 조회")
    void selectAll() {
        //given
        //when
        List<Map<String,String>> res = repository.selectAll();
        //then
        Map<String,String> map = res.get(0);
        //containsOnly 는 원소의 순서, 중복 여부 관계 없이 값만 일치하면 됩니다.
        //contains 와 다른 점은 원소의 갯수까지 정확히 일치해야 한다는 점입니다.
        Assertions.assertThat(map.keySet()).containsOnly("row_id","writer","title","contents","time");
    }

    @Test
    @DisplayName("특정 데이터 조회")
    void selectOne() {
        //given,when
        SoftAssertions softAssertions = new SoftAssertions();
        Map<String,String> elem = repository.selectOne("7");
        //then
        softAssertions.assertThat(elem.get("writer")).isEqualTo("성은 글이고 이름은 쓴이야");
        softAssertions.assertThat(elem.get("title")).isEqualTo("제목쓰");
        softAssertions.assertThat(elem.get("contents")).isEqualTo("내용쓰");
        softAssertions.assertAll();
    }

    @Test
    @DisplayName("db 데이터 추가")
    void storeInfo() {
        //given,when
        SoftAssertions softAssertions = new SoftAssertions();
        repository.storeInfo("writer","title","contents");
        //then
        Map<String,String> elem = repository.selectOne("10");
        //then
        softAssertions.assertThat(elem.get("writer")).isEqualTo("writer");
        softAssertions.assertThat(elem.get("title")).isEqualTo("title");
        softAssertions.assertThat(elem.get("contents")).isEqualTo("contents");
        softAssertions.assertAll();

    }
}
