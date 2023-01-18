# java-was-2022
Java Web Application Server 2022


## 프로젝트 정보 

이 프로젝트는 우아한 테크코스 박재성님의 허가를 받아 https://github.com/woowacourse/jwp-was 
를 참고하여 작성되었습니다.

## 프로젝트 구조


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
      
--------
  + Day 9
    + MIME Type
      + 인코딩(Encoding): text 파일로 변환
      + 디코딩(Decoding): text 파일을 바이너리 파일로 변환
      + Type : text, image, audio, video, application
      + subType : .avi, .bin, .css, .csv, .ico 등등..
      + Type/subType -> text/html, text/css ..
      
    + HTTP 302 FOUND response
      + 회원 가입 성공 시 
      기존의 성공 메세지와 회원 가입 정보를 byte화해서 body로 넘겨주던 로직을
      302 FOUND로 넘겨주었다
      + 302 Header
        + HTTP/1.1 302 FOUND
        + location: /index.html
        + location은 응답코드 301, 302 리다이렉션 상태에서 위치를 지정해준다
    
    + Java 객체 비교
      + ==, != 연산자
        + Primitive type를 비교할 때는 value값을 비교
        + Object를 비교할 때는 메모리 주소 비교 
      + equals() 사용
      + Objects.equals(a, b) 도 가능 (null check가 되어 있음)

    + Http Response 의 Status Line
      + HTTP version + Status code + Status text 로 구성
      ```
      HTTP/1.1 404 NOT FOUND
      ```
      
      + Java Unit Test 작성
        + given/when/then 패턴으로 깔끔하게
          + 어떤 데이터가 준비되었을 때 어떤 함수를 실행하면 어떤 결과가 나와야 한다.
        ```java
        @Test
        @DisplayName("테스트 이름")
        void test() {
            // given
            final String str = "test string"

            // when
            final String result = 테스트할클래스.method(str);

            // then
            assertThat(result).isEqualTo("예상되는 결과");
        }  
        ```
-------------
  + Day 11
    + HTTP POST 방식
      + POST 방식은 데이터 전송을 기반으로 한 요청 메서드
      + GET 방식은 URL에 데이터를 붙여서 보내지만 POST 방식은 BODY에 데이터를 넣어 보냄
      + 그래서 POST 방식에는 Content-Type 헤더 필드가 있음
        + ex) application/x-www-form-urlencoded, text/plain, multipart/form-data
      + POST Request 예시)
      ```
      POST /user/create HTTP/1.1
      Host: localhost:8080
      Connection: keep-alive
      Content-Length: 59
      Content-Type: application/x-www-form-urlencoded
      Accept: */*
      
      userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net
      ```
    + JAVA 생성 패턴 [Builder 패턴]
      + 많은 Optional한 멤버 변수(파라미터)나 지속성 없는 상태 값들 처리 문제 해결
      + 구현 방법
        + 빌더 클래스를 Static Nested Class로 생성 - 관례적으로 클래스 이름 + Builder로 명명
        + 생성자는 public, 파라미터는 필수 값들
        + Optional한 값들은 속성마다 메소드로 제공, 리턴 값이 빌더 객체 자신이어야 함
        + 마지막으로 빌더 클래스 내에 build()메소드를 정의하여 최종 생성된 결과물 리턴, 
        builder를 통해서만 객체 생성을 하므로 생성 대상이 되는 클래스의 생성자는 private
-----------
  + Day 12
    + 쿠키와 세션
      + 서버는 Set-Cookie header 값으로 상태를 유지하고 싶은 값을 클라이언트에 전송
      + 클라이언트는 받은 쿠키 값을 모든 요청의 쿠키 헤더로 서버에 전송
      + 서버는 쿠키 헤더에 따라 이전 접속자인지 확인!