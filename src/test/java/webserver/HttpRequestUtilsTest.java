package webserver;

import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

import request.HttpRequestUtils;

class HttpRequestUtilsTest {

	@DisplayName("쿼리 스트링 파싱")
	@Test
	public void 쿼리_스트링_파싱() {
		// given
		String queryString = "name=benny";

		// when
		Map<String, String> queryMap = HttpRequestUtils.parseQueryString(queryString);

		// then
		assertThat(queryMap.get("name")).isEqualTo("benny");
	}

	@Test
	public void 쿼리_스트링_파싱_empty() {
		// given
		String queryString = "";

		// when
		Map<String, String> queryMap = HttpRequestUtils.parseQueryString(queryString);

		// then
		assertThat(queryMap.isEmpty()).isTrue();
	}

}