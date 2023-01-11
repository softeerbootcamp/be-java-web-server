# java-was-2022
Java Web Application Server 2022


## 프로젝트 정보 

이 프로젝트는 우아한 테크코스 박재성님의 허가를 받아 https://github.com/woowacourse/jwp-was 
를 참고하여 작성되었습니다.

## 프로젝트 학습 내용

### 2주차

---
 + Day 6
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
    + 유틸 클래스 (HttpRequestUtils) 생성 및 리팩토링
      + 테스트 코드 추가
    ``` java
        @Test
        public void getUrl(){
        String url = HttpRequestUtils.getUrl("GET /index.html HTTP/1.1");
        assertThat(url).isEqualTo("/index.html");
        }
    ```
    + 회원가입 요청 url 처리 - split 함수 활용(? split > & split > = split 처리)
      + 테스트 코드 추가
    
    + http request stream에 한글이 있을 경우가 있으므로 parseQueryString 메소드에서 UTF_8 형식으로 변경
    ``` java
        requestParamsMap.put(requestParam[0], URLDecoder.decode(requestParam[1], StandardCharsets.UTF_8));
    ```
  ---
  + Day 7
    + Refactoring : http 폴더 생성 후 필요한 클래스 생성
    + Http Request Header의 구조
      + GET /index.html HTTP/1.1  -  시작줄(method, uri, version)
      + Host: localhost:8080        -  헤더
      + Connection: keep-alive  -  헤더
      + ....
    + .trim() 함수: 문자열의 시작과 끝 공백 제거
  ---
  + Day 8
    + Entry를 활용한 May 순회 방법
    ```java
       Map<String, String> map = new HashMap<>();

       for (Map.Entry<String, String> entry : map.entrySet()) {
           String key = entry.getKey();
           String value = entry.getValue();
       }
    ```
    + split() 메소드에 정규 표현식의 예약어 사용
      + 이스케이프 문자 사용
      ```java
         split("\\?")
      ```
      + Pattern.quote() 메소드 사용
      ```java
         split(Pattern.quote("?"));
      ```
    
    