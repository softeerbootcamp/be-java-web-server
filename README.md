# java-was-2022
Java Web Application Server 2022


## 프로젝트 정보 

이 프로젝트는 우아한 테크코스 박재성님의 허가를 받아 https://github.com/woowacourse/jwp-was 
를 참고하여 작성되었습니다.

## Step 1 - index.html 응답
최초의 상태는 localhost:8080으로 접속 시에 "Hello World"를 출력해준다. 이는 구현되어 있는 responseBody 메서드에 해당 문구를 byte[]로 변환하여 응답해주기 때문이다.

응답으로 index.html을 넘겨주는 방식으로 개선하기 위해서는 
1. 서버로 들어오는 inputLine에서 해당 url을 parsing
2. parsing한 파일을 resources에서 찾아 byte[]로 변환
3. 이를 response body에 write

의 과정을 거쳐야 한다.
index.html에 대한 HTTP Request 메시지의 첫 라인은 **GET /index.html HTTP/1.1**이다. 이것을
1. split메서드를 통해 "/index.html"만을 parsing
2. "index.html"이 있는 resource 디렉토리를 붙인 절대 경로로 Files.readAllBytes() 메서드의 인자로 넘겨 불러와 byte[]로 변환
3. 이를 response body에 write

하여 해결 가능하다.

## Step 2 - GET으로 회원가입
현재 회원가입은 http://localhost:8080/user/create?userId=aa&password=bb&name=cc&email=dd@dd 의 예처럼 쿼리스트링으로 전송된다. (작성 예정..)

## Step 3 - stylesheet 지원

전체적인 과정은 추후 작성 예정이며, 우선 stylesheet가 항상 정상적으로 동작하기 위해 만든 ViewResolver 제작 과정을 기술하였다.

### ViewResolver 제작기
결국 모든 경우에 적절한 리소스를 내려줄 수 있는 역할을 수행할 수 있어야 한다는 생각에 ViewResolver를 따로 만들었다. 부족한 Spring MVC 지식으로 spring 뷰 리졸버와 역할이 비슷하다고 생각해서 이렇게 지었는데 네이밍이 이상하다는 피드백이 있다면 고칠 예정.. 

목표는 요청 url이 내려오면 static, templates 디렉토리에 상관없이 일관되게 리소스를 내려주는 것이었다. **단, url의 특정 정보, 예를 들어 확장자나 디렉토리 등에 종속되어 조건이 분기되어서는 안된다.** 예를 들어, 현재 css나 js는 static 디렉토리에 존재하는 반면, html은 templates에 존재한다. 그렇다고 해서 css 확장자 -> static에서 찾기, 이런 식이나 css면 css/style.css 이렇게 저장하기 이런 종속적인 규칙이 발생해서는 안된다. 

여기까지 하고 보니까 url을 일일이 파싱하고 붙이는 작업으로는 이 로직을 수행할 수 없을 것 같다는 생각이 들었다. 그래서 파일 검색의 로직 자체에 대한 접근 방식을 바꾸었다. 아이디어는 구현 과정에서 리소스를 못내려주는 경우에 대한 에러 로그로 url을 찍어주는 방식에서 찾았다. 로직은 아래와 같다.

[ static 디렉토리에서 뒤져보기 ] -> [찾으면 반환, 없으면 exception] -> [ 바로 catch하여 templates 디렉토리 뒤져보기] -> [찾으면 반환, 없으면 exception ] -> [ 에러 로그 출력 ]

이렇게 url에 대한 처리를 확장자나 디렉토리 위치에 종속되지 않고 시행할 수 있었는데, 다른 문제가 있었다. 해당 메서드는 url을 byte[]로 리턴해주는데, 응답 메시지가 정상적으로 동작하려면 content type도 같이 넣어주어야 한다. content type은 팀원 분께서 `Files.probeContentType(path)` 라는 좋은 정적 메서드를 알려주셔서 file path만 넘겨주면 파일에 해당하는 content-type을 받을 수 있었지만, file path가 필요했다. 따라서 기존 메서드를 파일 경로를 찾아주는 `findFilePath(String url)` 와 실제 파일을 찾아주는 `findFileBytes(Path path)`로 분리하였다.

여기서 findFilePath 메서드를 만드는 과정에서 문제가 있었다.

1. 최초 시도
```java
public Path findFilePath(String url) {  
    try {  
        return new File(ViewResolver.STATIC_PATH + url).toPath();  
    } catch (InvalidPathException e) {  
        return new File(ViewResolver.TEMPLATES_PATH + url).toPath();  
    }  
}
```
이렇게 짜면 당연히 될줄 알았으나 exception이 터지질 않았다. 찾아보니 File 객체 생성자에 url을 넘겨주는 방식은 예외가 터지지 않았다. 결국 지금 필요한 것은 파일 path를 리턴하는 로직이되 해당 파일이 존재하지 않을 시 exception이 발생하는 메서드를 찾는 것이었다.

2. 두 번째 시도 (임시방편)
```java
public Path findFilePath(String url) {
  try {

    Files.readAllBytes(new File(ViewResolver.STATIC_PATH + url).toPath());

    return new File(ViewResolver.STATIC_PATH + url).toPath();

  } catch (IOException e) {

    return new File(ViewResolver.TEMPLATES_PATH + url).toPath();
  }
}
```
임시 방변으로 파일 자체를 찾는 readAllBytes() 메서드를 오로지 exception 용도로 추가하였다. 그러나 이는 실제 파일을 불러오는 메서드와 중복되는 기능이었고 수정이 필요하였다. 

3. 세 번째 시도 (현재)
```java
public Path findFilePath(String url) throws IOException {  
    try {  
        Path path = Paths.get(STATIC_PATH + url);  
        return path.toRealPath(LinkOption.NOFOLLOW_LINKS);  
    } catch (IOException e) {  
        Path path = Paths.get(TEMPLATES_PATH + url);  
        return path.toRealPath(LinkOption.NOFOLLOW_LINKS);  
    }  
}
```
이를 위해 Path interface와 구현체인 Paths 클래스에 대한 javadoc을 찾아보았다. 파일 경로를 리턴하되 찾아보고 없으면 exception이 터질 수 있는 메서드를 찾아보았고 `toRealPath()`를 이용하기로 하였다. 

> toRealPath() 메서드는 실재 파일(existing file)의 실제 경로를 반환한다. 여기서 실제 파일이라는 의미는 파일이 진짜로 있어야 한다는 뜻이다. toAbsolutePath() 메서드를 사용할 때는 실재 파일일 필요가 없다. 변환 과정에서 심볼 링크는 제외하고 싶다면 LinkOption.NOFOLLOW enum값을 전달해야 한다. 상대 경로를 전달하면 절대 경로를 반환하며 파일이 없거나 접근할 수 없다면 IOException 예외를 던진다.   -javadoc 번역

수정된 방식에서는 Path 인터페이스에 구현된 toRealPath() 메서드를 통해 절대경로를 반환하는데 없을 시에는 IOException이 동일하게 발생한다. 심볼릭 링크는 제외하는게 나을 것 같아 제외하였다.

추가적으로 ViewResolver가 정상적으로 작동하는지를 확인하기 위한 테스트 코드를 우선 작성하였고 이를 통해 findFilePath 메서드를 각각 static 디렉토리와 templates 디렉토리로 테스트하였고 통과하였다.


