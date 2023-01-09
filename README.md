# java-was-2022
Java Web Application Server 2022


## 프로젝트 정보 

이 프로젝트는 우아한 테크코스 박재성님의 허가를 받아 https://github.com/woowacourse/jwp-was 
를 참고하여 작성되었습니다.

## 프로젝트 학습 내용

### 2주차
  + Day 1
    + InputStream > InputStreamReader > BufferedReader로 Http Request Header 가져오기
    ``` java
      try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
        BufferedReader br = new BufferedReader(new InputStreamReader(in, "UTF-8"));
    ```
    + BufferedReader.readLine() 메소드를 활용해 모든 Http header 출력
    + Header의 첫 번째 라인에서 요청 URL을 추출
    + Path에 해당하는 파일을 응답하기
    ``` java
        DataOutputStream dos = new DataOutputStream(out);
        byte[] body = Files.readAllBytes(new File("./src/main/resources/templates/" + url).toPath());
    ```
    + 테스트 코드 추가
    ``` java
        @Test
        public void getUrl(){
        String url = HttpRequestUtils.getUrl("GET /index.html HTTP/1.1");
        assertThat(url).isEqualTo("/index.html");
        }
    ```
    