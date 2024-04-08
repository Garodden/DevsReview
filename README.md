# 🎮💻 Keyboard-Arena
## Keyboard Arena는? <br>
<img src="https://github.com/Garodden/keyboard-arena/assets/82032418/9cc76a2e-ac2b-4f95-a77d-7bbd2c8ddcf3"><br>
Keyboard Arena는 경쟁 기반 타자 연습을 위한 회원제 커뮤니티 서비스입니다.<br>
ESTsoft 백엔드 개발자 과정 '오르미' 4기 Java/Spring 프로젝트의 일환으로 제작되었습니다.<br>

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
