package controller;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class DomainListTest {
    @Test
    @DisplayName("존재하는 원소를 찾기")
    void findExistingElement() {
        // given
        Domain user = Domain.USER;
        // when
        Domain findDomain = Domain.find(user.getName());
        // then
        Assertions.assertThat(findDomain).isEqualTo(user);
    }

    @Test
    @DisplayName("존재하지 않으면 Main을 반환")
    void findNotExistingElement() {
        // given, when
        Domain findDomain = Domain.find("not existing domain");
        // then
        Assertions.assertThat(findDomain).isEqualTo(Domain.MAIN);
    }

}