# sist_prj3_eLearnWeb
# 🎓 IntLearn - 권한 분리형 이러닝 플랫폼

- 2025-12~ 2026-02에 쌍용교육센터에서 진행한 Spring Boot 프로젝트.
- 학생, 강사, 관리자 3개 권한으로 개발됨 <br />

<img width="1055" height="1059" alt="image" src="https://github.com/user-attachments/assets/ae2c1273-9f8e-4072-a547-1e8b7091acaa" />


> Spring 기반 B2C 교육 플랫폼 웹 애플리케이션 <br/>
> 학생, 강사, 관리자 3계층 권한 분리 및 정밀한 동영상 학습 트래킹, 비동기(Ajax) 기반의 동적 사용자 경험(UX)을 지원하는 서비스입니다.

<br/>

## 🗓 프로젝트 개요
* 진행 기간: 2026.01.05 ~ 2026.02.23 (6주)
* 참여 인원: 6명 (백엔드 및 프론트엔드 풀스택)
* 기획 의도: 상용화 가능한 수준의 비즈니스 로직 설계를 목표로, 역할 세분화 및 데이터 무결성을 보장하는 교육 웹 플랫폼 구축

<br/>

## 🛠 Tech Stack
Backend
* Java (JDK 17)
* Spring Boot
* MyBatis
* Oracle DB

Frontend
* HTML5 / CSS3 / JavaScript
* Thymeleaf (Server-Side Rendering)
* Ajax / jQuery
* Bootstrap 5 / YouTube Player API

DevOps & Tools
* Linux (Ubuntu), NGINX, Docker
* Git / GitHub, VS Code, Eclipse (STS)
* Notion (지식 자산화 및 협업)

<br/>

## 📊 System ERD

<img width="2604" height="2127" alt="image" src="https://github.com/user-attachments/assets/2f1a8689-be7c-4e78-ad2f-e8eb0126bc06" />

<br/>

## ✨ 핵심 기능 (Key Features)

### 1. 👥 권한 분리 및 동적 뷰 렌더링
* 학생 / 강사 / 관리자 3계층으로 역할을 세분화하여 접근 권한 제어
* Thymeleaf를 활용하여 로그인된 사용자 권한에 따른 맞춤형 대시보드 및 서버 사이드 동적 뷰(View) 렌더링 제공

### 2. 📺 진도율 트래킹 및 평가 시스템
* YouTube Player API와 Ajax를 연동하여 사용자의 영상 시청 이벤트를 감지 및 비동기 기록
* 모든 챕터의 진도율 100% 달성 시에만 '시험 응시 권한'을 동적으로 부여하는 엄격한 학습 플로우 구현

### 3. 💬 SPA(Single Page Application)급 비동기 수강평 시스템
* 강의 상세 페이지 이탈 없이 수강평 조회, 작성, 수정, 삭제가 가능한 비동기(Ajax) CRUD 및 페이징 구현
* 페이지 새로고침(F5) 없이 모달창과 동적 DOM 제어를 통해 별점 부여 및 리뷰 상태를 즉시 렌더링하여 사용자 경험(UX) 극대화

### 4. 🛒 데이터 무결성 기반 장바구니 및 안전한 파일 다운로드
* 기 수강 중인 강의와 장바구니에 담으려는 강의의 중복 여부를 교차 검증 (MyBatis 다중 조건 쿼리)
* 강의 자료 다운로드 시, 무조건 링크를 호출하지 않고 서버 내 실제 파일 존재 여부를 선행 검증(`checkFile`)하는 방어적 백엔드 로직 구축

<br/>

<img width="1007" height="872" alt="image" src="https://github.com/user-attachments/assets/b2960833-1828-4c9b-9115-82e729ba19cc" />
<img width="1912" height="906" alt="image" src="https://github.com/user-attachments/assets/e39c9a08-7e0d-432c-8ae0-dc406e693146" />

<br/>

## 🚀 트러블 슈팅 (Troubleshooting)

### 🚩 Issue 1. 비동기 렌더링 시 DOM 이벤트 유실 및 파싱 에러 해결
* 문제 상황: 수강평 탭을 Ajax `.load()`로 비동기 호출 시, 새롭게 렌더링된 버튼(작성, 수정, 삭제)에 기존 JavaScript 이벤트가 바인딩되지 않거나, Thymeleaf 템플릿 파싱 에러(500 Internal Server Error) 발생.
* 해결 방법: 
    * HTML 렌더링 시점과 JS 실행 시점을 분리 분석하여, 정적 바인딩(`click`) 대신 이벤트 위임(Event Delegation, `$(document).on()`) 방식으로 아키텍처 전면 수정.
    * Thymeleaf의 불필요한 레이아웃 중복을 제거하고, 순수 데이터 영역(`#review-content-only`)만 추출하여 매핑함으로써 500 에러 해결 및 랜더링 속도 향상.


### 🚩 Issue 2. 진도율 어뷰징 방지 및 UX 개선
* 문제 상황: 초기 설계 시 단순 누적 시간 기록 방식을 사용하여, 유료 강의의 탐색(건너뛰기 등) 시 데이터 정합성이 어긋나는 문제 발생.
* 해결 방법: JavaScript의 `Math.floor` 등을 활용해 로직을 정제하고, '최대 시청 위치 갱신' 방식으로 진도율 산정 알고리즘을 전면 개편하여 어뷰징 방지.

### 🚩 Issue 3. 로컬 파일 경로 충돌 및 배포 의존성 문제
* 문제 상황: 팀원 간 개발 환경(OS, 디렉터리) 차이로 인해 파일 업로드/다운로드 로컬 경로 충돌 발생.
* 해결 방법: 
    * `WebMvcConfigurer`를 이용해 파일 업로드 경로를 물리적 로컬이 아닌 외부 경로로 매핑.
    * 물리 경로를 `application.properties`로 외표화하고 의존성 주입(DI)으로 참조하도록 구조 개선하여 운영 서버 배포 효율성 극대화.

### 🚩 Issue 4. 대규모 소스 병합 충돌 방지 및 생산성 향상
* 해결 방법: 6인 팀 프로젝트의 원활한 통합을 위해 명확한 GitHub 브랜치 전략(Branching Strategy) 수립. 엄격한 디렉터리 구조 및 명명 규칙을 사전에 정의하고, Notion을 통해 자체 제작한 '외부 경로 매핑', 'DB 외래키 에러 대응 가이드'를 배포하여 팀 전체의 생산성 상향 평준화에 기여함.

<br/>

## ⚙️ 시작하기 (Getting Started)

### Prerequisites
* Java 17 (or 1.8+)
* Oracle DB 11g 이상
* Maven

### Installation
```bash
# 1. Repository 클론
$ git clone [https://github.com/](https://github.com/)[사용자계정]/IntLearn.git

# 2. application.properties 설정 (DB 계정 및 외부 파일 업로드 경로 수정)
spring.datasource.url=jdbc:oracle:thin:@localhost:1521:xe
spring.datasource.username=YOUR_USERNAME
spring.datasource.password=YOUR_PASSWORD
file.upload.dir=/your/custom/path/

# 3. 프로젝트 빌드 및 실행
$ ./mvnw spring-boot:run
