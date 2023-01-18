# java-was-2022
Java Web Application Server 2022


## 프로젝트 정보 

이 프로젝트는 우아한 테크코스 박재성님의 허가를 받아 https://github.com/woowacourse/jwp-was 
를 참고하여 작성되었습니다.


# 2주차 web server week1

## 1일차 학습내용
### 정적 리소스에 관한 요청 처리

- WAS와 WS의 개념과 차이점
- Multi-Threading을 이용한 자바 웹소켓의 소켓 통신
- Http Status 코드의 종류와 각 상태가 뜻하는 바
- Http 요청의 흐름 - HTML 요청 -> 안에 들어있는 CSS / JS 요청 
-----

## 2일차 학습내용
### 회원가입 요청 처리
- 클래스의 구조화 (HTTP 요청, 응답 객체)
- 서블릿과 핸들러 매핑을 통한 HTTP Request 라우팅
- MVC 패턴에 의한 비즈니스 로직 처리 방안
- Exception Handling을 Customizing 하면서 얻게 되는 기대 효과
-----

## 3일차 학습 내용
### pull request 피드백 반영
- [x] RequestBody, RequestHeader 객체에 대해서 Response와 통합적으로 관리하도록 반영
- [x] 반복적인 작업을 메소드화
- [x] ReponseWrite를 위한 클래스의 접근 제어자 변경
- [x] 입력값 검증 로직 구현

### StyleSheet 요청 처리
- [x] enum 클래스로 컨텐트 타입 구현
- [x] Response 타입을 캡슐화 하여 여러 상황에 대한 응답 객체 생성 방법 다원화
- [ ] 단위 테스트 작성

### 추가로 한 일
- [x] 쿼리 스트링을 파싱하여 유효성을 검사하는 로직을 메소드로써 추상화
- [x] 응답 객체에 대한 책임 분리
- [x] 에러 처리 문장의 가독성 향상

### 공부한 내용
- git stash / cherrypick
- 리다이렉트의 종류
- 값을 담기 위한 클래스와 연산을 위한 클래스 간 역할, 책임 구분
- 단위 테스트 시 최대한 세부적으로! 최대한 많은 coverage!

-----
## 4일차 학습 내용
### pull request 피드백 반영
- [x] 메소드 추상화 작업
- [x] httpResponse / httpResponseUtil 간의 의존관계 정립

### 오늘 할 일
- [x] ArgumentResolver 클래스 만들기
- [x] 핸들러 어댑터 만들기
- [x] 테스트 코드 작성

### 공부한 것
- 잘못된 인수를 전달하였을 때는 400 BAD REQUEST 에러를
- 정적 리소스의 파일은 무조건 상대경로로!
- 스프링은 Argument Resolver를 통하여 인자의 유효성을 검사한다
- Mock 객체를 이용하는 경우
  - 테스트 환경 구축이 어려운 경우
  - 테스트 시간이 오래 걸리는 경우
  - 테스트가 특정 경우나 순간에 의존적인 경우
- Mock이 달린 객체는 InjectMock이 달린 객체에 주입시킬 수 있다
- 데이터베이스와 관련된 테스트를 진행할 때에는 매 테스트가 끝난 후 롤백 시켜줘야함
- verify()를 사용하여 mock 객체에 대해 메소드가 특정 조건에 실행되었는지를 테스트해볼 수 있다


# 3주차 web server week2

-----
## 1일차 학습 내용
### pull request 피드백 반영
- [x] 하드코딩으로 처리된 ContentType Enum 클래스 수정
- [x] logger 레벨 의미에 맞게 수정
- [ ] 메소드명을 해당 메소드가 하는 일이 드러나도록 수절

### 오늘 할 일
- [x] ArgumentResolver 클래스 만들기
- [ ] RequestBody 읽고 알맞은 형태로 파싱하기
- [x] HTTP Request Method에 따른 실행 흐름 분기
- [x] Mockito, verify, when을 이용한 테스트 코드 작성

### 공부한 것
- 리팩토링의 조건
   - 테스트하기 좋은 코드
   - 변화에 강한 코드
- 메소드의 위치를 논할 때, 메소드가 해당 클래스의 데이터를 일절 사용하지 않는다면 위치 선정을 다시한번 고려해볼 것
- Rest API
- HTTP 1.1 : 파이프라인을 통한 데이터 전송
- HTTP 2.0 : 병렬 처리를 통하여 여러 데이터를 한번에 전송
- 쿠키 : "key:value" 쌍의 형태로 브라우저에 저장되는 유저 관련 파일 데이터
- 세션 : 사용자 한명한명마다 생성, 저장되는 데이터의 집합, session-id로 식별

### 더 알아볼 것
- Redis를 이용한 세션 DB 관리 방안
- CRDT
- 다양한 타입의 Request의 body를 파싱하는 시점, 방안

----
## 2일차 학습 내용
### pull request 피드백 반영
- [x] 정적 스태틱 메소드 중복 수정
- [ ] HttpRequestException의 의존 관계 수정 (RuntimeEx -> Exception)

### 오늘 할 일
- [x] HttpCookie 클래스 구조화
- [x] 세션 관련 로직 구현
   - 세션 생성, 만료 기한 정의
   - request 시 세션 핸들링
   - response에 쿠키 정보를 담아 전송
- [x] 쿠키와 세션을 이용하여 약간의 security 기능
- [x] logout 구현
- [ ] HttpCookie 기능 관련 테스트코드 작성

### 공부한 것
- 로그아웃 시 세션 핸들링
 - max-age를 0 / -1 로
- JWT와 쿠키/세션의 차이
- 세션 DB 구축 방안
- Mockito의 When() 메소드에서 발생하는 null pointer exception
- java reflection의 invocationtargetexception 원인 및 해결 방안

----
## 3일차 학습 내용
### pull request 피드백 반영
- [ ] mvc 컴포넌트를 싱글톤으로 수정
- [ ] 쿠키 관련 테스트 코드
- [ ] HttpRequestException의 생성자 수정

### 오늘 할 일
- [ ] 동적 웹 생성
- [ ] 로그인 여부를 판단하는 security 관련 로직

### 공부한 것
- Service 내 메소드의 네이밍 best practice
- 자바 싱글톤 패턴 생성 방식
   - synchronized 메소드 이용 
   - static + 생성자
- Spring Security의 동작 원리