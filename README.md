# java-was-2022
Java Web Application Server 2022


## 프로젝트 정보 

이 프로젝트는 우아한 테크코스 박재성님의 허가를 받아 https://github.com/woowacourse/jwp-was 
를 참고하여 작성되었습니다.



### 1일차 학습내용
#### 정적 리소스에 관한 요청 처리
- WAS와 WS의 개념과 차이점
- Multi-Threading을 이용한 자바 웹소켓의 소켓 통신
- Http Status 코드의 종류와 각 상태가 뜻하는 바
- Http 요청의 흐름 - HTML 요청 -> 안에 들어있는 CSS / JS 요청 
-----

### 2일차 학습내용
#### 회원가입 요청 처리
- 클래스의 구조화 (HTTP 요청, 응답 객체)
- 서블릿과 핸들러 매핑을 통한 HTTP Request 라우팅
- MVC 패턴에 의한 비즈니스 로직 처리 방안
- Exception Handling을 Customizing 하면서 얻게 되는 기대 효과

-----
### 3일차 학습 내용
## pull request 피드백 반영
- [x] RequestBody, RequestHeader 객체에 대해서 Response와 통합적으로 관리하도록 반영
- [x] 반복적인 작업을 메소드화
- [x] ReponseWrite를 위한 클래스의 접근 제어자 변경
- [x] 입력값 검증 로직 구현

## StyleSheet 요청 처리
- [x] enum 클래스로 컨텐트 타입 구현
- [x] Response 타입을 캡슐화 하여 여러 상황에 대한 응답 객체 생성 방법 다원화
- [ ] 단위 테스트 작성

## 추가로 한 일
- [x] 쿼리 스트링을 파싱하여 유효성을 검사하는 로직을 메소드로써 추상화
- [x] 응답 객체에 대한 책임 분리
- [x] 에러 처리 문장의 가독성 향상

## 공부한 내용
- git stash / cherrypick
- 리다이렉트의 종류
- 값을 담기 위한 클래스와 연산을 위한 클래스 간 역할, 책임 구분
- 단위 테스트 시 최대한 세부적으로! 최대한 많은 coverage!