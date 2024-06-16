# CareerTime

## 프로젝트 개요
취준생과 현직자가 자유롭게 글을 쓰고 서로 1:1 매칭 및 채팅으로 소통할 수 있는 플랫폼   
기획에 대한 자세한 내용과 발표자료는 **[presentation](./presentation)** 디렉토리 확인

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
![image](https://github.com/parkm2ngyu00/GPS_webservice/assets/88785472/1f5496e1-6d7e-4a63-bb5b-a3dcbbff13d0)

## 주요 UI
### 메인페이지
![mainpage1](https://github.com/parkm2ngyu00/GPS_webservice/assets/88785472/6addc34b-06c4-486b-917f-6bb8050300d8)
![mainpage2](https://github.com/parkm2ngyu00/GPS_webservice/assets/88785472/2206b507-ced8-4817-94e9-6baeec4a4cb4)

### 게시글
![postdetail](https://github.com/parkm2ngyu00/GPS_webservice/assets/88785472/c691fc2c-48a3-40c3-a648-70665130b3c6)
![postcomment](https://github.com/parkm2ngyu00/GPS_webservice/assets/88785472/b9bfb594-aa01-4a58-b0bf-a77a63563457)

### 마이페이지
![image](https://github.com/parkm2ngyu00/GPS_webservice/assets/88785472/9a11bbb9-3b9b-4ab1-853e-fcf1ddc5a286)
- 프로필사진, 본인 관심사 해시태그, 학교 및 회사 수정 가능
- 자기소개란 마크다운 형식으로 사용자가 자유롭게 작성 가능
![mypagechat](https://github.com/parkm2ngyu00/GPS_webservice/assets/88785472/74dffea4-250b-4c99-b18a-76206f184427)
- 내 채팅 목록 확인 가능

### 채팅페이지
![chatting](https://github.com/parkm2ngyu00/GPS_webservice/assets/88785472/4f0b432a-c23f-4b71-87de-71bb6e7ba89a)
