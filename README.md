# 🎮💻 Keyboard-Arena
## Keyboard Arena는? <br>
<img src="https://github.com/Garodden/keyboard-arena/assets/82032418/9cc76a2e-ac2b-4f95-a77d-7bbd2c8ddcf3"><br>
Keyboard Arena는 경쟁 기반 타자 연습을 위한 회원제 커뮤니티 서비스입니다.<br>
ESTsoft 백엔드 개발자 과정 '오르미' 4기 Java/Spring 프로젝트의 일환으로 제작되었습니다.<br>
[배포 주소](http://3.36.79.208:8082/)

## 🕹 주요 기능
- `주간 랭크전 타자연습 아레나 참전 `
  - 유저는 관리자가 개설한 랭크전 타자연습에 참전해 서비스 내 랭킹을 산정받을 수 있습니다. 
- `일반 타자연습 아레나 개설과 참전`
  - 유저는 타자연습 아레나를 개장하고, 120초 내에 해당 아레나를 직접 클리어하여 서비스 내의 아레나로 정식 등록될 수 있도록 자격을 획득합니다.
  - 유저는 자격 검증을 마치고 정식으로 등록된 아레나를 자유로이 즐기고, 좋아요 및 댓글을 남길 수 있습니다. 
- `유저 랭킹 기반 권한 부여 시스템`
    - 아레나 참전 및 자유게시판 게시글 조회에 있어 권한이 차등 부여됩니다.
 
## 🤼‍♂️ Team
- **서창덕** (팀장, [Github](https://github.com/Garodden))
  - 아레나 CRUD, 참전 api 및 UI 구현
  - 아레나 검증 api 및 UI 구현
  - 유저 랭킹 산정 api 및 UI 구현
  - RDS 생성 및 발표 담당
- **송찬혁** ([Github](https://github.com/songchanyok))
  - 자유게시판 CRUD api 및 UI 구현
  - 댓글 관련 api 및 UI 구현
  - 좋아요 관련 api 및 UI 구현
- **지윤호** ([Github](https://github.com/UUUUUKnow))
  - 비밀번호 변경 api 및 UI 구현
  - 회원탈퇴 api 및 UI 구현
  - flow chart 작성
- **장한빛** ([Github](https://github.com/biiit4894))
  - 로그인/회원가입 관련 api 및 UI 구현
  - 마이페이지 관련 api 및 UI 구현
  - EC2 배포 및 발표자료 제작 담당
 
## 🛠 Tech Stack
- Language : `Java 17`
- Framework : `Spring Boot 3.2.4`
- Database : `MySQL`, `JDBC`, `Spring Data JPA`
- Frontend : `Thymeleaf`, `HTML/CSS`, `JavaScript`
- 배포 : `AWS EC2/RDS`

## ⛏ ERD
<img alt="erd 이미지" src="https://github.com/Garodden/keyboard-arena/assets/82032418/c5abc898-2dbf-4495-8292-ff1500efe5cd">

## 📈 Service Flow
<img alt="순서도 이미지" src="https://github.com/Garodden/keyboard-arena/assets/155498542/a8fcf414-5030-4a23-9bbf-70a63ce619dc">

## 📦 Project Structure
<details>
  <summary>Project Tree</summary>
  <pre>
 src
  └─ main
     ├─ java
     │  └─ com
     │     └─ example
     │        └─ KeyboardArenaProject
     │           ├─ KeyboardArenaApplication.java
     │           ├─ config
     │           │  ├─ InterceptorConfiguration.java
     │           │  ├─ LogoutListener.java
     │           │  ├─ SchedulerConfig.java
     │           │  ├─ SwaggerConfig.java
     │           │  └─ WebSecurityConfig.java
     │           ├─ controller
     │           │  ├─ AOPAspect.java
     │           │  ├─ GlobalExceptionHandler.java
     │           │  ├─ LikeCommentController.java
     │           │  ├─ arena
     │           │  │  └─ ArenaController.java
     │           │  ├─ freeBoard
     │           │  │  └─ FreeBoardController.java
     │           │  └─ user
     │           │     ├─ MyPageController.java
     │           │     ├─ UserController.java
     │           │     └─ UserViewController.java
     │           ├─ dto
     │           │  ├─ CommentResponse.java
     │           │  ├─ arena
     │           │  │  ├─ ArenaBestUserResponse.java
     │           │  │  ├─ ArenaDashBoardResponse.java
     │           │  │  ├─ ArenaReceiveForm.java
     │           │  │  ├─ ArenaResponse.java
     │           │  │  ├─ ArenaResultResponse.java
     │           │  │  ├─ ArenaStartTimeResponse.java
     │           │  │  ├─ ArenaVerifyResponse.java
     │           │  │  ├─ ArenaVerifyResultResponse.java
     │           │  │  └─ BoardDetailResponse.java
     │           │  ├─ freeBoard
     │           │  │  ├─ FreeBoardRecieveForm.java
     │           │  │  ├─ FreeBoardResponse.java
     │           │  │  └─ FreeBoardWriteRequest.java
     │           │  ├─ mypage
     │           │  │  ├─ MyArenaResponse.java
     │           │  │  ├─ MyCommentedBoardsResponse.java
     │           │  │  └─ MyLikedBoardsResponse.java
     │           │  └─ user
     │           │     ├─ AddUserRequest.java
     │           │     ├─ AnonymousUser.java
     │           │     ├─ ChangePwRequest.java
     │           │     ├─ DeleteUserRequest.java
     │           │     ├─ MyPageInformation.java
     │           │     ├─ ResetPwRequest.java
     │           │     ├─ UserResponse.java
     │           │     └─ UserTopBarInfo.java
     │           ├─ entity
     │           │  ├─ Board.java
     │           │  ├─ Cleared.java
     │           │  ├─ Comment.java
     │           │  ├─ IP.java
     │           │  ├─ Like.java
     │           │  ├─ User.java
     │           │  └─ compositeKey
     │           │     ├─ IpCompositeKey.java
     │           │     └─ UserBoardCompositeKey.java
     │           ├─ interceptor
     │           │  └─ Interceptor.java
     │           ├─ repository
     │           │  ├─ ClearedRepository.java
     │           │  ├─ CommentRepository.java
     │           │  ├─ CommonBoardRepository.java
     │           │  ├─ IpRepository.java
     │           │  ├─ LikeRepository.java
     │           │  ├─ MyPageRepository.java
     │           │  └─ UserRepository.java
     │           ├─ service
     │           │  ├─ CommentService.java
     │           │  ├─ LikeService.java
     │           │  ├─ board
     │           │  │  └─ CommonBoardService.java
     │           │  └─ user
     │           │     ├─ ClearedService.java
     │           │     ├─ MyPageService.java
     │           │     ├─ UserDetailService.java
     │           │     └─ UserService.java
     │           └─ utils
     │              └─ user
     │                 ├─ GenerateIdUtils.java
     │                 ├─ GeneratePwUtils.java
     │                 └─ UserTopBarInfoUtil.java
     └─ resources
        ├─ static
        │  ├─ css
        │  │  ├─ arena
        │  │  ├─ common
        │  │  ├─ freeboard
        │  │  ├─ mypage
        │  │  └─ user
        │  ├─ js
        │  │  ├─ arena
        │  │  ├─ common
        │  │  ├─ freeboard
        │  │  ├─ mypage
        │  │  └─ user
        │  └─ media
        └─ templates
           └─ error

  </pre>
</details>

## ⚙️ Core Features
### 유저 랭크 산정 알고리즘
랭크 아레나 기록을 기반으로 유저 랭크를 일정 시간마다 산정해주는 기능이다.
랭크 산정 알고리즘 후보군
- 랭크 아레나 4개의 등수의 평균값을 내고, 그 값을 기반으로 1/5씩 나눠 랭크 1~5까지 산정해주는 시스템
  - 문제점: 랭크 아레나 하나만 1등으로 클리어한 사람이 랭크 아레나 4개를 모두 2등으로 클리어한 사람보다 높은 랭크를 차지하게돼 불공정해진다.
  - 그럼 모든 랭크 아레나를 클리어한 사람들을 기준으로 랭크를 산정하면 안되나?
      - 랭크 아레나를 모두 플레이 하지 않았어도 랭크 산정에 포함하고 싶었음
#### 그래서 고안해낸 방법이 가중치 랭크 산정 알고리즘이다.
- 유저의 랭크는 가장 낮은 1부터 가장 높은 5까지 존재한다.
- 경쟁 아레나는 총 4개이다.
- 한명의 유저가 하나의 랭크 아레나를 클리어 해서 얻을 수 있는 가중치는 최대 1, 최소 0이다.
  - 단순하게 1등이면 가중치 1, 꼴찌면 가중치 0, 그 중간 등수 어딘가라면 경쟁 아레나에서 참여한 사람 수에 맞춰 1을 나눠 가중치를 부여한다. 이걸 공식으로 적용하면
  - (유저1이 랭크아레나 1에서 얻은 가중치) = (랭크아레나1을 클리어한 사람의 수 – 유저의 등수)/(랭크아레나1을 클리어한 사람 수-1)
  - 1+(유저 한명의 각 아레나에 대한 가중치들의 합) 값을 반올림하여 유저의 랭크를 산정한다.
 ![image](https://github.com/Garodden/keyboard-arena/assets/44630705/f5c1a1fd-f584-4130-b209-474860acc398)
- 가중치 랭크 산정 알고리즘의 장점
  - 랭크 아레나를 클리어한 사람의 수, 유저의 등수에 따라 평등하게 랭크를 산정해줄 수 있다.
  - 랭크 아레나를 모두 클리어하지 않아도 랭킹 산정 시스템에 포함이 되며, 랭크 아레나를 많이 클리어 할수록 더 높은 랭크를 가져갈 수 있다.
- 이 가중치 알고리즘은 SQL문 하나만으로 해결해 RDS와 EC2 인스턴스의 불필요한 네트워크 비용을 절감할 수 있었다.
```SQL
UPDATE user u
    JOIN (
        SELECT
            uc.id,
            SUM((uc.total_users - uc.board_rank) / (uc.total_users-1) )+1 AS rank_score
        FROM (
                 SELECT
                     id,
                     board_id,
                     RANK() OVER (PARTITION BY board_id ORDER BY clear_time Asc) AS board_rank,
                     COUNT(*) OVER (PARTITION BY board_id) AS total_users
                 FROM user_cleared_board
                 WHERE board_id IN (SELECT board_id FROM board WHERE board_type = 2)
                 
             ) AS uc
        GROUP BY uc.id

    ) AS scores
    ON u.id = scores.id

SET u.user_rank = ROUND(scores.rank_score)
where u.user_rank!=10;
```


[//]: # (- 시간 날때 기술 스택 아이콘 첨부하려고 냅둔 주석 ...https://camo.githubusercontent.com/b0648ef7a9b6980ea27c1caaeb06d5c8503dbb4f9b4d9d7ca1df60a5edc14340/68747470733a2f2f696d672e736869656c64732e696f2f62616467652f6a6176612d2532334544384230302e7376673f7374796c653d666f722d7468652d6261646765266c6f676f3d6f70656e6a646b266c6f676f436f6c6f723d7768697465)

[//]: # (  https://camo.githubusercontent.com/42dd3f9f9345fb4a3e1a24d0483c62ac853b227b6bec314dbd09aa0d9edc9671/68747470733a2f2f696d672e736869656c64732e696f2f62616467652f737072696e67626f6f742d3644423333463f7374796c653d666f722d7468652d6261646765266c6f676f3d737072696e67626f6f74266c6f676f436f6c6f723d7768697465)

[//]: # (- <img src="https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white">)

[//]: # (<img src="https://img.shields.io/badge/SpringBoot-6DB33F?style=flat-square&logo=Spring&logoColor=white">)

[//]: # (<img src="https://img.shields.io/badge/HTML-E34F26?style=for-the-badge&logo=html&logoColor=white">)

[//]: # (<img src="https://img.shields.io/badge/CSS-1572B6?style=for-the-badge&logo=css&logoColor=white">)

[//]: # (<img src="https://img.shields.io/badge/JavaScript-F7DF1E?style=for-the-badge&logo=javascript&logoColor=black">)

[//]: # (<img src="https://shields.io/badge/MySQL-lightgrey?logo=mysql&style=plastic&logoColor=white&labelColor=blue">)

[//]: # (<img src="">)

## 📑 요구사항 명세서
[명세서 링크(Notion)](https://www.notion.so/oreumi/9f73ed77821149e78ba4073f7e315cd5)

<img alt="요구사항 명세서 이미지" src="https://github.com/Garodden/keyboard-arena/assets/82032418/ee252d75-9b87-4cc1-8faa-3814f6289cc4">
