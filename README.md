# 세팅 참고
```cmd
netstat -ano | findstr 포트번호
```
### MSSQL
로컬 설치 : https://develop-writing.tistory.com/22

### Eclipse Temurin JDK
https://adoptium.net/temurin/releases/

### rabbit MQ (docker)
https://velog.io/@divan/RabbitMq-%EC%84%A4%EC%B9%98
```cmd
docker run -d --name rabbitmq -p 5672:5672 -p 15672:15672 rabbitmq:3-management
```

# 개요
#### 0. 전체 프로세스
![img.png](img_10.png)
#### 1. RabbitMQ 메시지
* https://www.rabbitmq.com/tutorials/tutorial-one-java (rabbit MQ 공식) </br>
* https://docs.spring.io/spring-amqp/reference/3.1-SNAPSHOT/amqp/abstractions.html (스프링부트 공식) </br>
* https://github.com/spring-projects/spring-amqp-samples (샘플 소스) </br>
* https://adjh54.tistory.com/292 (상세한블로그)
* https://www.javaguides.net/2022/07/spring-boot-rabbitmq-multiple-queues.html (멀티큐) </br>
#### 2. swagger (springdoc - fox 아님)
* https://springdoc.org/#getting-started (공식) </br>
* [상세한 블로그 참고](https://hogwart-scholars.tistory.com/entry/Spring-Boot-SpringDoc%EA%B3%BC-Swagger%EB%A5%BC-%EC%9D%B4%EC%9A%A9%ED%95%B4-API-%EB%AC%B8%EC%84%9C%ED%99%94-%EC%9E%90%EB%8F%99%ED%99%94%ED%95%98%EA%B8%B0#Spring%20Boot%203%EC%97%90%EC%84%9C%20Swagger%20%EC%82%AC%EC%9A%A9%ED%95%98%EA%B8%B0-1)
#### 3. CDC 개념 공부
#### 4. KCP 연동 테스트
* /업무관련파일/pay_system/분석 에 회의록, API, 기능 화면, 샘플코드, 일정표 있음
#### 5. DDD

# MSSQL 설정
![img.png](readme/img.png)
그냥 설치해서 ms sql server management studio 에서 이렇게 연결되길래 ㅇㅋ 했는데
intellij 에서 연결이 안 됨

</br>(1) 포트지정
</br>(2) user/password 인증으로 바꿔줘야 함

### (1) 포트지정하기
![img_1.png](readme/img_1.png)
sql server configuration manager (msqql 네트워크 관리를 하는 설정 툴) 를 켠다
![img_2.png](readme/img_2.png)
SQL 네트워크 구성 > [사용할db]에 대한 프로토콜 > TCP/IP > IP 주소 탭에서
</br>IP1, ...IP9 중 사용할 IP와 IP all 에다가 포트 할당
![img_3.png](readme/img_3.png)
재시작

### (2) 사용자 인증으로 변경하기
![img_4.png](readme/img_4.png)
기본값이 windows 인증이라서 SQL server 인증으로 변경해야 함
</br>localhost 는 windows 인증이 가능한데, IP 주소로 연결하기 위해선 username/password 로 하라 그럼

![img_5.png](readme/img_5.png)
대충 로그인할 어드민 계정을 만든다
</br> * 비밀번호 필수 (intellij 에서 비밀번호 없으면 뭔 클라이언트크리덴션 에러냄)

### (3) 스프링부트 yml 설정
```yml
spring:
  datasource:
    driver-class-name: com.microsoft.sqlserver.jdbc.SQLServerDriver
    url: jdbc:sqlserver://localhost:5012;databaseName=tempdb;encrypt=true;trustServerCertificate=true
    username: sa
    password: 123
```
저 url 뒤, __encrypt, certificate__ 설정을 해야 동작함

### (4) 다른 서버 테이블 갖다 붓기
1. 객체 우클릭 > 테스트 > 데이터 내보내기
2. 복사할 db (147 테스트 서버) 입력, 드라이버 선택 주의
![img_1.png](img_1.png)
3. 붙여넣기 할 db (내로컬) 입력, 이미지에 IP 잘못된 주소임, 드라이버 선택 주의
![img_2.png](img_2.png)
4. 하나 이상의 데이블/뷰, 긁을 테이블 선택 및 실행
![img_3.png](img_3.png)
![img_4.png](img_4.png)


# Rabbit MQ 메시지 형식
![img_7.png](readme/img_7.png)
```java
 public void sendMessageTestJson(MessageDto messageDto) throws JsonProcessingException {
     ObjectMapper om = new ObjectMapper();
     String toJson = om.writeValueAsString(messageDto);

     log.error("보냄-json");
     log.error(toJson);
     rabbitTemplate.convertAndSend(exchangeName, key, toJson);
 }
```
### 1. send json receive json (String to String)
```java
 @RabbitListener(queues = "test")
 public void receiveJson(String msg) {
     log.error("받음-json");
     log.info("---{}", msg);
 }
```
### 2. send json receive Message (String to Message(object))
```java
 @RabbitListener(queues = "test")
 public void receiveJson(Message dto) {
     log.error("받음-json2");
     log.info("---{}", dto.toString());
 }
```
### 3. send Object receive Object
![img_6.png](readme/img_6.png)
```java
public void sendMessageTest(MessageDto messageDto) throws JsonProcessingException {
   log.error("보냄-object");
   rabbitTemplate.convertAndSend(exchangeName, key, messageDto);
}
    
 @RabbitListener(queues = "test")
 public void receiveJson(MessageDto dto) {
     log.error("받음-object");
     log.info("---{}", dto.toString());
 }
```
### 4. 토픽 전송
1. RabbitMQConfig binding (여기키로가는지?)
2. RabbitMQService 보내는 곳 (여기키로가는지?)
3. RabbitMQConsumerService 받는 곳

내가 보낼 때, convertAndSend 에 정의된 키로 가는지, config binding 에 정의된 키로 가는지?
두개를 일치시켜야 가는 건지?

https://jonnung.dev/rabbitmq/2019/02/06/about-amqp-implementtation-of-rabbitmq/

바인딩이 큐를 결정 -> 큐는 아묻닥 보냄

# 라이브러리

### 1. restTemplate
* [restTemplate 사용 시 주의사항](https://medium.com/@ShimSeongbo/resttemplate%EC%9C%BC%EB%A1%9C-api-%ED%98%B8%EC%B6%9C-%EC%8B%9C-%EA%BC%AD-%EC%95%8C%EC%95%84%EC%95%BC-%ED%95%A0-2%EA%B0%80%EC%A7%80-best-practice-b45592ecdfbc)
* [restTemplate 사용 방법](https://juntcom.tistory.com/141)

# DDD
### 1. 디렉토리 구조
https://cheese10yun.github.io/spring-guide-directory/
### 2. DDD start!
[8, 9 장 제외 정리](https://www.notion.so/DDD-4c50439ddc094cc7a172cc9585aedf91?pvs=4)</br>
[팀장님 주신 샘플](https://github.com/Hae-Riri/ddd-start/tree/main/chap02.%20%EC%95%84%ED%82%A4%ED%85%8D%EC%B2%98%20%EA%B0%9C%EC%9A%94)</br>
[샘플소스2](https://github.com/mploed/ddd-with-spring/tree/master/credit-sales-funnel/src/main/resources)