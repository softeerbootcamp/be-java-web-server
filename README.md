# java-was-2022

Java Web Application Server 2022

## 프로젝트 정보

이 프로젝트는 우아한 테크코스 박재성님의 허가를 받아 https://github.com/woowacourse/jwp-was
를 참고하여 작성되었습니다.

## 기능 요구사항

- [x] http://localhost:8080/index.html 로 접속했을 때 src/main/resources/templates 디렉토리의 index.html 파일을 읽어 클라이언트에 응답한다.
- [ ] “회원가입” 메뉴를 클릭하면 http://localhost:8080/user/form.html 으로 이동, 회원가입 폼을 표시한다. 이 폼을 통해서 회원가입을 할 수 있다.
- [ ] Stylesheet 파일을 지원

## 추후 계획

1. InputStream을 request 객체로 추상화하기
2. OutputStream을 response 객체로 추상화하기
3. Controller로 요청 분산하기(?)
    4. Facade Controller
        5. Request, Response 객체 생성, 하위 Controller 호출
        6. response static file
    5. User Controller
        6. 회원가입 기능 수행
