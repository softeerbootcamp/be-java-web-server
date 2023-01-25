package db;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("QueryBuilderFactory Test")
public class QueryBuilderFactoryTest {

    @Test
    void newQueryBuilder() {
        // given
        String configPath = "src/test/resources/db_config.xml";

        // when
        try(QueryBuilder queryBuilder = QueryBuilderFactory.newQueryBuilder(configPath)) {
            assertThat(queryBuilder).isNotNull();
        }
    }
}
