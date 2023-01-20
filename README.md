# java-was-2022

Java Web Application Server 2022

## 프로젝트 정보

이 프로젝트는 우아한 테크코스 박재성님의 허가를 받아 https://github.com/woowacourse/jwp-was
를 참고하여 작성되었습니다.

# 데모 영상
https://drive.google.com/drive/folders/1fiup4puUcKT0FKiiD4JWScaqKPhgKk-9?usp=sharing
# 디렉토리 구조


# Goals
### 의존성 주입
- 컨트롤러, 서비스, 레포지토리는 각 스레드 마다 존재할 필요가 없다고 생각함  
  -> 초기에는 싱글턴 패턴을 사용하려함  
  -> DI(의존성 주입)를 해보자고 생각함
- Context 클래스
  - 레포지토리 객체 생성 
  - 필요한 레포지토리를 주입 받은 서비스 객체 생성
  - 필요한 서비스를 주입 받은 컨트롤러 객체 생성
  - 존재하는 모든 컨트롤러를 Map 에 <path, Controller> 형태로 저장
- 해당 클래스에서 만든 Map 을 WebServer 가 작동할 때 각 스레드에 인자로 넘겨서 주입
- 결국 각 스레드는 동일한 Map 객체를 주입받아서 마치 싱글턴처럼 사용하려고 함

