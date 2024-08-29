# 🌟ReviewZip-BE🌟

> 위치기반 후기 플랫폼, Review.ZIP 

## 📖 Description
신뢰성이 약한 리뷰로 인해 어려움을 겪으신 적이 있나요?

Review.ZIP은 평점, 좋아요, 팔로잉/팔로우 등의 여러 기능을 통해 각 장소에 대한 신뢰도 높은 리뷰를 제공하는 웹 서비스입니다 😀

## :baby_chick: Demo
<p float = "left">
 

## ⭐ Main Feature
### 게시글 
- 이미지 업로드/수정/삭제 (AWS S3 사용)
- 게시글 생성, 조회, 수정, 삭제 기능
- 검색 기능 (유저 닉네임, 유저 이름)
- 게시글 해시태그 

### 회원가입 및 로그인 
- JWT & OAuth 2.0 (카카오) 이용
- 비밀번호 인증 후 비밀번호 변경 기능
- 관심장소 기능 

### 기타 기능
- 마이페이지
- 유저 팔로우, 팔로잉 / 조회 기능 
- 리뷰 스크랩 생성 / 해제 기능
- 게시글 좋아요 생성 / 해제 기능 

## 🔧 Stack
- **Language** : Java
- **Library & Framework** : SpringBoot
- **DevOps**: AWS (EC2, RDS, S3 등), Nginx 
- **Database** : AWS RDS (MySQL), Redis 
- **ORM** : JPA
- **Deploy** : Docker, GitHub Actions 


## 🔨 Server Architecture

## 📄ERD
<img src="Review.ZIP ERD.png">

## ⚒ CI/CD
- GitHub Actions를 활용한 지속적 통합 및 배포
- `feature` 브랜치에서 `dev`로 Pull Request를 보내면, CI가 동작된다.
- `dev`에서 `master`로 Pull Request를 보내면, CI가 동작되고 Merge가 되면, 운영 리소스에 배포된다.

## 👨‍💻 Role & Contribution

**Backend**
- Spring Security, JWT를 이용한 로컬 로그인 API 개발
- 프로필 이름, 이미지 수정 기능 개발
- 인증번호 인증 후 비밀번호 수정 기능 구현 (Cool SMS API 연동 및 Redis 사용)

**etc**
- 개발 일정 및 이슈 관리 

## 👨‍👩‍👧‍👦 Developer
* **이수용** ([leesuyong4029](https://github.com/leesuyong4029))
* **이혜수** ([hsuush](https://github.com/hsuush))
* **이승명** ([YPYP333YPYP](https://github.com/YPYP333YPYP))
* **정윤지** ([yoondaeng](https://github.com/yoondaeng))
  
