# CareerTime

## 프로젝트 개요
취준생과 현직자가 자유롭게 글을 쓰고 서로 1:1 매칭 및 채팅으로 소통할 수 있는 플랫폼   
기획에 대한 자세한 내용과 발표자료는 **presentation** 디렉토리 확인

## 사용 기술
- React
- Spring Boot
- Spring Security
- MySQL
- MongoDB
- WebSocket
- Message Broker (Spring Boot 제공)

## 주요 프레임워크 및 언어 버전
```
nodejs 20.9.0
React 18.2.0
Java 17
Spring Boot 3.2.5
```

## 주요 기능
1. 로그인/회원가입
2. 게시글 CRUD
3. 댓글(후기) CRUD
4. 1:1 채팅
5. 마이페이지 관리

## ER 다이어그램
![erd](https://github.com/parkm2ngyu00/GPS_webservice/assets/88785472/9b0eaeac-6278-42b3-adcc-d9676e9c7236)
- MySQL : users, userprofiles, posts, comments, chat_rooms
- MongoDB : chat_messages
- 채팅 메시지를 담아야 하는 DB는 고속 쓰기 및 읽기 작업에 최적화 되어있는 MongoDB를 사용하고 나머지 테이블은 RDBMS를 활용함

## API 명세서
