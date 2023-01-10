# java-was-2022

Java Web Application Server 2022

## 프로젝트 정보

이 프로젝트는 우아한 테크코스 박재성님의 허가를 받아 https://github.com/woowacourse/jwp-was 
를 참고하여 작성되었습니다.

# 학습한 내용

## HTTP Message 구조

First Line + Header + Message Body로 구성

### First Line

Request인 경우
- HTTP Method + URI + HTTP Version으로 구성
    - HTTP Method
      - GET: 지정된 resource(URI)를 요청
      - POST: 자원의 생성을 요청
      - PUT: 자원의 수정을 요청
      - DELETE: 클라이언트가 지정한 URI를 서버에서 삭제

Response인 경우
- HTTP Version + 상태 코드 + 상태 메시지로 구성

### Header

공통 Header(요청, 응답에 모두 사용되는 header)
- Date: HTTP 메시지가 만들어진 시각
- Connection: 기본적으로 keep-alive
- Content-Length: 본문 크기를 바이트 단위로 표시
- Cache-Control
- Content-Type: 컨텐츠 타입과 문자열 인코딩 명시
- Content-Language: 사용자의 언어
- Content-Encoding: 컨텐츠가 압축된 방식, 압축 후 전송하면 브라우저가 알아서 해제해서 사용

요청 Header
- Host: 서버의 도메인 네임
- User-Agent: 현재 사용자가 어떤 클라이언트(운영체제, 앱, 브라우저 등)를 통해 요청을 보냈는지에 대한 정보
- Accept: 클라이언트가 혀용할 수 있는 파일 형식
- Cookie: 웹서버가 클라이언트에 쿠키를 저장해 놓았다면 해당 쿠키의 정보를 이름-값 쌍으로 웹서버에 전송
- Origin: POST같은 요청을 보낼 때 요청이 어느 주소에서 시작되었는지를 나타내며, 이 때 보낸 주소와 받는 주소가 다르면 CORS 문제가 발생하기도 한다.
- If-Modified-Since: 304 상태 코드와 관련되어 있는 header, 요청한 파일이 이 필드에 지정된 시간 이후로 변경되지 않았다면 서버로부터 데이터를 전송받지 않는다.
- Authorization: 인증 토큰을 서버로 보낼 때 사용

응답 Header
- Server: 웹서버의 정보
- Access-Control-Allow-Origin
- Allow
- Content-Disposition: 응답 본문을 브라우저가 어떻게 표시해야 할지 알려주는 header
- Location: 300번대 응답이나 201 Created 응답일 때 어느 페이지로 이동할지 알려주는 header
- Content-Security-Policy: 다른 외부 파일들을 불러오는 경우, 차단할 소스와 불러올 소스를 여기에 명시할 수 있다.

### Message Body
HTTP Message의 본문