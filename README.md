# java-was-2022
Java Web Application Server 2022


## 프로젝트 정보 

이 프로젝트는 우아한 테크코스 박재성님의 허가를 받아 https://github.com/woowacourse/jwp-was 
를 참고하여 작성되었습니다.
프로젝트 설명 외의 개념적 설명은 github wiki에서 확인 가능합니다.

### model
데이터를 관리하는 model 패키지. domain, repository, service 로 구성.

#### domain
웹서비스와 관련된 객체들이 있는 패키지. 현재는 User의 회원가입만을 처리하기 때문에 User 클래스만 존재.
+ User: 유저 정보 도메인 (아이디, 비밀번호, 이름, 이메일)

#### repository
도메인 객체들을 저장 및 관리하는 패키지. 현재 도메인인 User를 관리하는 UserRepository가 있음.  
현재는 memory에 데이터를 저장하지만, 추후에는 DB 연동 등으로 구현 방법이 바뀔 수 있기 때문에 UserRepository를 인터페이스로 하고 
이를 구현하는 MemoryUserRepository 클래스를 통해 구현.
+ UserRepository: 유저 리포지토리의 기능을 정의한 인터페이스. 유저 생성, 조회의 기능을 가짐.
+ MemoryUserRepository: 메모리에서의 유저 리포지토리를 구현한 클래스. 


#### service
도메인 별 비즈니스 로직을 처리하는 패키지. 레포지토리를 활용하여 도메인 객체의 저장, 조회 등의 기능.
+ UserService: 유저에 관련된 비즈니스로직을 처리하는 클래스. 유저 리포지토리를 활용하여 유저의 생성, 조회가 가능하며 중복 유저에 대한 validation 기능을 가짐. 


### view
http 메시지를 관리하는 패키지. Http Request Message를 파싱하여 각 정보를 저장하는 RequestHeaderMessage와 request에 대한 response를
 담당하는 Response 클래스로 구성.
+ RequestHeaderMessage: HTTP 메시지의 헤더부분 정보를 관리하는 클래스. 추후 Post 방식등을 고려해 메시지의 헤더와 바디 모두를 관리하는
 RequestMessage로 개편할 계획
+ Response: Response 메시지를 작성하고 클라이언트로 전송하는 클래스.

### webserver
프로그램의 전체 흐름을 관리하는 일종의 controller 역할을 하는 패키지. 소켓을 통해 http request 메시지를 전달받으며 view를 활용하여 메시지를 필요한 
데이터로 가공. 이후 해당 데이터를 model을 통해 적절한 조작을 하고, 가공된 결과 데이터를 Response view를 활용하여 클라이언트로 전달.

### util
메시지 파싱, 리다이렉션 링크 등 필요한 정보를 가공하는데 도움이 되는 클래스 및 메소드를 모아놓은 패키지.
+ HttpRequestUtil: HTTP 메시지를 파싱하는데 도움이 되는 메서드를 모아놓은 클래스
+ HttpStatus: HTTP Status Code를 관리하는 Enum
+ Redirect: Redirection 주소를 관리하는 클래스