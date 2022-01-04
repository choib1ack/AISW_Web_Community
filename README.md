## ![AISW](https://github.com/JunHeon-Ch/AISW_Web_Community/blob/main/readme/logo.PNG) 커뮤니티 플랫폼
http://ec2-54-180-144-125.ap-northeast-2.compute.amazonaws.com/
</br>

> ### Description

![image](https://user-images.githubusercontent.com/37904738/133571234-f5c7ed9a-9364-468d-9295-fac4a66529ab.png)

- 이 프로젝트는 대학 생활 중 필요한 정보를 찾기 위해 다양한 사이트를 확인해야 하는 불편함을 해소하고자 만들어졌습니다.
- 가천대학교 AISW학부 학생들이 학교, 학과, 학생회 공지, 대외 활동, 취업 등 대학 생활 중 필요한 정보를 이 플랫폼 내에서 모두 확인할 수 있으며, 학생들 간에 **정보 공유 및 소통을 위한 웹 커뮤니티 플랫폼**입니다.

---

</br>

> ### Framework/Library (수정 必)

- Backend
    - Spring Boot 2.4.1, gradle 6.7.1, Spring Data JPA, Spring Security
    - OAuth2.0, JWT, Querydsl
    - MySQL 8.0.22, Redis 6.2.1
    - Docker 20.10.7

- Frontend
    - React 17.0.1
    - Redux 4.0.5
    - Bootstrap 4.5.3

---

</br>

> ### Project structure
├─java
│  │
│  └─com
│      │
│      └─aisw
│          │
│          └─community
│              │  CommunityApplication.java
│              │
│              ├─component
│              │  │  LoginUserAuditorAware.java
│              │  │
│              │  ├─advice
│              │  │  │  ApiErrorResponse.java
│              │  │  │
│              │  │  ├─exception
│              │  │  │      AdminNotFoundException.java
│              │  │  │      AlertNotFoundException.java
│              │  │  │      BannerNotFoundException.java
│              │  │  │      CanNotDetermineFileTypeException.java
│              │  │  │      CommentNotFoundException.java
│              │  │  │      ContentLikeAlreadyExistException.java
│              │  │  │      ContentLikeNotFoundException.java
│              │  │  │      FaqNotFoundException.java
│              │  │  │      FileNotFoundException.java
│              │  │  │      FileStorageException.java
│              │  │  │      MyFileNotFoundException.java
│              │  │  │      NotEqualUserException.java
│              │  │  │      PhoneNumberNotSuitableException.java
│              │  │  │      PostNotFoundException.java
│              │  │  │      PostStatusNotSuitableException.java
│              │  │  │      SignUpNotSuitableException.java
│              │  │  │      SiteCategoryNameNotFoundException.java
│              │  │  │      SiteCategoryNotFoundException.java
│              │  │  │      SiteInformationNotFoundException.java
│              │  │  │      TokenException.java
│              │  │  │      UserNotFoundException.java
│              │  │  │      WrongRequestException.java
│              │  │  │
│              │  │  └─handler
│              │  │          ApiExceptionHandler.java
│              │  │          ExceptionHandlerFilter.java
│              │  │
│              │  ├─provider
│              │  │      JwtTokenProvider.java
│              │  │      RedisProvider.java
│              │  │
│              │  └─util
│              │          KeyCreatorBean.java
│              │
│              ├─config
│              │  │  CorsConfig.java
│              │  │  JpaConfig.java
│              │  │  QuearydslConfig.java
│              │  │  WebSecurityConfig.java
│              │  │
│              │  ├─auth
│              │  │      PrincipalDetails.java
│              │  │      PrincipalDetailsService.java
│              │  │
│              │  ├─cache
│              │  │      CacheConfig.java
│              │  │      RedisConfig.java
│              │  │
│              │  ├─document
│              │  │      SwaggerConfig.java
│              │  │
│              │  ├─jwt
│              │  │      JwtAuthenticationFilter.java
│              │  │      JwtAuthorizationFilter.java
│              │  │      JwtProperties.java
│              │  │
│              │  └─storage
│              │          FileStorageProperties.java
│              │
│              ├─controller
│              │  │  HomeController.java
│              │  │
│              │  ├─admin
│              │  │      BannerController.java
│              │  │      FaqController.java
│              │  │      SiteCategoryController.java
│              │  │      SiteInformationController.java
│              │  │      UserManagementController.java
│              │  │
│              │  ├─post
│              │  │  │  AbsBulletinController.java
│              │  │  │  BulletinController.java
│              │  │  │  PostController.java
│              │  │  │
│              │  │  ├─board
│              │  │  │      BoardController.java
│              │  │  │      BoardPostController.java
│              │  │  │      FreeController.java
│              │  │  │      JobController.java
│              │  │  │      QnaController.java
│              │  │  │
│              │  │  ├─comment
│              │  │  │      CommentController.java
│              │  │  │
│              │  │  ├─file
│              │  │  │      FileController.java
│              │  │  │
│              │  │  ├─like
│              │  │  │      ContentLikeController.java
│              │  │  │
│              │  │  └─notice
│              │  │          CouncilController.java
│              │  │          DepartmentController.java
│              │  │          NoticeController.java
│              │  │          NoticePostController.java
│              │  │          UniversityController.java
│              │  │
│              │  └─user
│              │          UserController.java
│              │
│              ├─model
│              │  ├─entity
│              │  │  ├─admin
│              │  │  │      Banner.java
│              │  │  │      Faq.java
│              │  │  │      SiteCategory.java
│              │  │  │      SiteInformation.java
│              │  │  │
│              │  │  ├─post
│              │  │  │  │  Bulletin.java
│              │  │  │  │
│              │  │  │  ├─board
│              │  │  │  │      Board.java
│              │  │  │  │      Free.java
│              │  │  │  │      Job.java
│              │  │  │  │      Qna.java
│              │  │  │  │
│              │  │  │  ├─comment
│              │  │  │  │      Comment.java
│              │  │  │  │
│              │  │  │  ├─file
│              │  │  │  │      File.java
│              │  │  │  │
│              │  │  │  ├─like
│              │  │  │  │      ContentLike.java
│              │  │  │  │
│              │  │  │  └─notice
│              │  │  │          Council.java
│              │  │  │          Department.java
│              │  │  │          Notice.java
│              │  │  │          University.java
│              │  │  │
│              │  │  └─user
│              │  │          Alert.java
│              │  │          User.java
│              │  │
│              │  ├─enumclass
│              │  │      AlertCategory.java
│              │  │      BulletinStatus.java
│              │  │      Campus.java
│              │  │      FirstCategory.java
│              │  │      Gender.java
│              │  │      Grade.java
│              │  │      SecondCategory.java
│              │  │      UploadCategory.java
│              │  │
│              │  └─network
│              │      │  Header.java
│              │      │  Pagination.java
│              │      │
│              │      ├─request
│              │      │  ├─admin
│              │      │  │      BannerApiRequest.java
│              │      │  │      FaqApiRequest.java
│              │      │  │      FileUploadToBannerRequest.java
│              │      │  │      FileUploadToSiteRequest.java
│              │      │  │      SiteInformationApiRequest.java
│              │      │  │      UserManagementApiRequest.java
│              │      │  │
│              │      │  ├─post
│              │      │  │  ├─board
│              │      │  │  │      FileUploadToFreeRequest.java
│              │      │  │  │      FileUploadToJobRequest.java
│              │      │  │  │      FileUploadToQnaRequest.java
│              │      │  │  │      FreeApiRequest.java
│              │      │  │  │      JobApiRequest.java
│              │      │  │  │      QnaApiRequest.java
│              │      │  │  │
│              │      │  │  ├─comment
│              │      │  │  │      CommentApiRequest.java
│              │      │  │  │
│              │      │  │  ├─like
│              │      │  │  │      ContentLikeApiRequest.java
│              │      │  │  │
│              │      │  │  └─notice
│              │      │  │          CouncilApiRequest.java
│              │      │  │          DepartmentApiRequest.java
│              │      │  │          FileUploadToCouncilRequest.java
│              │      │  │          FileUploadToDepartmentRequest.java
│              │      │  │          FileUploadToUniversityRequest.java
│              │      │  │          UniversityApiRequest.java
│              │      │  │
│              │      │  └─user
│              │      │          AlertApiRequest.java
│              │      │          LoginRequest.java
│              │      │          UserApiRequest.java
│              │      │          VerificationRequest.java
│              │      │
│              │      └─response
│              │          │  HomeApiResponse.java
│              │          │
│              │          ├─admin
│              │          │      BannerApiResponse.java
│              │          │      FaqApiResponse.java
│              │          │      HomeBannerAndSiteResponse.java
│              │          │      SiteCategoryApiResponse.java
│              │          │      SiteInformationApiResponse.java
│              │          │      SiteInformationByCategoryResponse.java
│              │          │      SiteInformationWithFileApiResponse.java
│              │          │      UserManagementApiResponse.java
│              │          │
│              │          ├─post
│              │          │  │  BulletinApiResponse.java
│              │          │  │  BulletinResponseDTO.java
│              │          │  │
│              │          │  ├─board
│              │          │  │      BoardApiResponse.java
│              │          │  │      BoardResponseDTO.java
│              │          │  │      FreeApiResponse.java
│              │          │  │      FreeDetailApiResponse.java
│              │          │  │      JobApiResponse.java
│              │          │  │      JobDetailApiResponse.java
│              │          │  │      JobResponseDTO.java
│              │          │  │      QnaApiResponse.java
│              │          │  │      QnaDetailApiResponse.java
│              │          │  │
│              │          │  ├─comment
│              │          │  │      CommentApiResponse.java
│              │          │  │
│              │          │  ├─file
│              │          │  │      FileApiResponse.java
│              │          │  │
│              │          │  ├─like
│              │          │  │      ContentLikeApiResponse.java
│              │          │  │
│              │          │  └─notice
│              │          │          CouncilApiResponse.java
│              │          │          DepartmentApiResponse.java
│              │          │          NoticeApiResponse.java
│              │          │          NoticeResponseDTO.java
│              │          │          UniversityApiResponse.java
│              │          │
│              │          └─user
│              │                  AlertApiResponse.java
│              │                  UserApiResponse.java
│              │                  VerificationApiResponse.java
│              │
│              ├─repository
│              │  ├─admin
│              │  │      BannerRepository.java
│              │  │      CustomSiteInformationRepository.java
│              │  │      CustomSiteInformationRepositoryImpl.java
│              │  │      FaqRepository.java
│              │  │      SiteCategoryRepository.java
│              │  │      SiteInformationRepository.java
│              │  │
│              │  ├─post
│              │  │  │  BulletinRepository.java
│              │  │  │
│              │  │  ├─board
│              │  │  │      BoardRepository.java
│              │  │  │      FreeRepository.java
│              │  │  │      JobRepository.java
│              │  │  │      QnaRepository.java
│              │  │  │
│              │  │  ├─comment
│              │  │  │      CommentRepository.java
│              │  │  │      CustomCommentRepository.java
│              │  │  │      CustomCommentRepositoryImpl.java
│              │  │  │
│              │  │  ├─file
│              │  │  │      FileRepository.java
│              │  │  │
│              │  │  ├─like
│              │  │  │      ContentLikeRepository.java
│              │  │  │
│              │  │  └─notice
│              │  │          CouncilRepository.java
│              │  │          DepartmentRepository.java
│              │  │          NoticeRepository.java
│              │  │          UniversityRepository.java
│              │  │
│              │  └─user
│              │          AlertRepository.java
│              │          UserRepository.java
│              │
│              └─service
│                  │  HomeService.java
│                  │
│                  ├─admin
│                  │      BannerService.java
│                  │      FaqService.java
│                  │      SiteCategoryService.java
│                  │      SiteInformationService.java
│                  │      UserManagementService.java
│                  │
│                  ├─post
│                  │  │  AbsBulletinService.java
│                  │  │  BulletinService.java
│                  │  │  PostService.java
│                  │  │
│                  │  ├─board
│                  │  │      BoardPostService.java
│                  │  │      BoardService.java
│                  │  │      FreeService.java
│                  │  │      JobService.java
│                  │  │      QnaService.java
│                  │  │
│                  │  ├─comment
│                  │  │      CommentService.java
│                  │  │
│                  │  ├─file
│                  │  │      FileService.java
│                  │  │      FileStorageService.java
│                  │  │
│                  │  ├─like
│                  │  │      ContentLikeService.java
│                  │  │
│                  │  └─notice
│                  │          CouncilService.java
│                  │          DepartmentService.java
│                  │          NoticePostService.java
│                  │          NoticeService.java
│                  │          UniversityService.java
│                  │
│                  └─user
│                          AlertService.java
│                          UserService.java
│
└─resources
    │  application-jwt.properties
    │  application.properties
    │
    ├─static
    │  ├─css
    │  │      base.css
    │  │      bootstrap-responsive.min.css
    │  │      bootstrap-table.css
    │  │      bootstrap.min.css
    │  │      style.css
    │  │
    │  ├─images
    │  │      glyphicons-halflings-white.png
    │  │      glyphicons-halflings.png
    │  │      spring_boot.png
    │  │      spring_boot_gray.png
    │  │      spring_boot_green.png
    │  │
    │  └─js
    │          bootstrap-table.js
    │          bootstrap.min.js
    │          jquery-2.1.3.min.js
    │
    └─templates
        │  home.html
        │  login.html
        │
        ├─admin
        │      config.html
        │
        ├─layout
        │      footer.html
        │      header.html
        │      left.html
        │      top.html
        │
        └─user
            │  messages.html
            │  mypage.html
            │
            └─login
                    register.html


> ### Function (수정 必)
<details>
  <summary><b>사용자 페이지</b></summary>
  
- 메인페이지: 배너, 사이트, 공지사항, 게시판 목록을 확인할 수 있습니다.
   
- 사용자 관리
    
   - 회원가입: 구글, 가천대학교 계정으로 회원가입 가능합니다.

   - 로그인: 구글 계정으로 로그인할 시 접근 제한되는 서비스가 있습니다.

   - 회원 정보 수정

   - 회원 탈퇴
    
   - 댓글/좋아요 알림
      - 댓글과 대댓글, 게시물과 댓글에 대한 좋아요 알림을 확인할 수 있습니다.
      - 읽지 않은 알림을 확인할 수 있습니다.

- 공지사항(학교 / 학과 / 학생회)
    
   - 공지사항 목록 조회
    
   - 게시물 키워드 검색(제목, 작성자, 작성자+내용)

   - 게시물 작성
    
   - 게시물 조회
    
   - 게시물 수정
    
   - 게시물 삭제
    
- 게시판(자유 / 질문 / 취업)
    
   - 게시판 목록 조회

   - 게시물 키워드 검색(제목, 작성자, 작성자+내용)
    
   - 게시물 작성
    
   - 게시물 조회
    
   - 게시물 수정
    
   - 게시물 삭제
    
   - 댓글 작성
   
   - 게시물/댓글 좋아요
    
- FAQ
    
   - FAQ 목록 조회 
    
</details>
<details>
  <summary><b>관리자 페이지</b></summary>
    
- FAQ 관리
    
   - FAQ 등록
    
   - FAQ 수정
   
   - FAQ 삭제
    
- 배너 관리
    
   - 배너 등록
   
   - 배너 목록 조회
    
   - 배너 수정
   
   - 배너 삭제
   
- 유용한 사이트 관리
    
   - 카테고리 등록
    
   - 카테고리 수정
    
   - 카테고리 삭제
    
   - 사이트 등록
    
   - 사이트 목록 조회
    
   - 사이트 수정
    
   - 사이트 삭제

- 사용자 권한 관리
    
   - 사용자 목록 조회
    
   - 사용자 권한 수정
    
</details>

---

</br>

> ### API

<details>
  <summary><b>사용자 페이지</b></summary>
  
- HomeController (메인)
   - 로그인한 사용자 홈: GET /auth/home
   - 로그인하지 않은 사용자 홈: GET /home

- UserController (사용자)
   - 회원 가입 API: POST /user/signup
   - 로그인 API: POST /login
   - 회원 탈퇴: DELETE /auth/user
   - 정보 수정: PUT /auth/user
   - Refresh token 재발급: GET /auth/refresh
   - 알림
      - 전체 알림 조회: GET /auth/alert
      - 알림 조회: GET /auth/alert/{id}

- BulletinController (통합)
   - 통합 검색(제목): GET /bulletin/search/title
   - 통합 검색(작성자): GET /bulletin/search/writer
   - 통합 검색(제목+내용): GET /bulletin/search/title&content
   
   - FileController (첨부파일)
      - 파일 다운로드: GET /file/download/{fileName}
  
   - NoticeController (공지사항)
       - 공지사항 목록 조회: GET /notice/main
       - 공지사항 검색(제목): GET /notice/search/title
       - 공지사항 검색(작성자): GET /notice/search/writer
       - 공지사항 검색(제목+내용): GET /notice/search/title&content

       - UniversityController (학교 공지사항)
          - 학교 공지사항 작성: POST /auth-admin/notice/university
          - 학교 공지사항 작성(첨부파일 포함): POST /auth-admin/notice/university/upload
          - 학교 공지사항 수정: PUT /auth-admin/notice/university
          - 학교 공지사항 수정(첨부파일 포함): PUT /auth-admin/notice/university/upload
          - 학교 공지사항 삭제: DELETE /auth-admin/notice/university/{id}
          - 학교 공지사항 목록 조회: GET /notice/university
          - 학교 공지사항 조회: GET /auth/notice/university/{id}
          - 학교 공지사항 검색(제목): GET /notice/university/search/title
          - 학교 공지사항 검색(작성자): GET /notice/university/search/writer
          - 학교 공지사항 검색(제목+내용): GET /notice/university/search/title&content
       
       - DepartmentController (학과 공지사항)
          - 학과 공지사항 작성: POST /auth-admin/notice/department
          - 학과 공지사항 작성(첨부파일 포함): POST /auth-admin/notice/department/upload
          - 학과 공지사항 수정: PUT /auth-admin/notice/department
          - 학과 공지사항 수정(첨부파일 포함): PUT /auth-admin/notice/department/upload
          - 학과 공지사항 삭제: DELETE /auth-admin/notice/department/{id}
          - 학과 공지사항 목록 조회: GET /notice/department
          - 학과 공지사항 조회: GET /auth-student/notice/department/{id}
          - 학과 공지사항 검색(제목): GET /notice/department/search/title
          - 학과 공지사항 검색(작성자): GET /notice/department/search/writer
          - 학과 공지사항 검색(제목+내용): GET /notice/department/search/title&content

       - CouncilController (학생회 공지사항)
          - 학생회 공지사항 작성: POST /auth-council/notice/council
          - 학생회 공지사항 작성(첨부파일 포함): POST /auth-council/notice/council/upload
          - 학생회 공지사항 수정: PUT /auth-council/notice/council
          - 학생회 공지사항 수정(첨부파일 포함): PUT /auth-council/notice/council/upload
          - 학생회 공지사항 삭제: DELETE /auth-council/notice/council/{id}
          - 학생회 공지사항 목록 조회: GET /notice/council
          - 학생회 공지사항 조회: GET /auth-student/notice/council/{id}
          - 학생회 공지사항 검색(제목): GET /notice/council/search/title
          - 학생회 공지사항 검색(작성자): GET /notice/council/search/writer
          - 학생회 공지사항 검색(제목+내용): GET /notice/council/search/title&content

     - BoardController (게시판)
       - 게시판 목록 조회: GET /board/main
       - 게시판 검색(제목): GET /board/search/title
       - 게시판 검색(작성자): GET /board/search/writer
       - 게시판 검색(제목+내용): GET /board/search/title&content

       - FreeController (자유게시판)
          - 자유게시판 작성: POST /auth/board/free
          - 자유게시판 작성(첨부파일 포함): POST /auth/board/free/upload
          - 자유게시판 수정: PUT /auth/board/free
          - 자유게시판 수정(첨부파일 포함): PUT /auth/board/free/upload
          - 자유게시판 삭제: DELETE /auth/board/free/{id}
          - 자유게시판 목록 조회: GET /board/free
          - 자유게시판 조회(비로그인 사용자): GET /board/free/comment/{id}
          - 자유게시판 조회(로그인 사용자): GET /auth/board/free/comment&like/{id}
          - 자유게시판 검색(제목): GET /board/free/search/title
          - 자유게시판 검색(작성자): GET /board/free/search/writer
          - 자유게시판 검색(제목+내용): GET /board/free/search/title&content
       
       - JobController (취업게시판)
          - 취업게시판 작성: POST /auth/board/job
          - 취업게시판 작성(첨부파일 포함): POST /auth/board/job/upload
          - 취업게시판 수정: PUT /auth/board/job
          - 취업게시판 수정(첨부파일 포함): PUT /auth/board/job/upload
          - 취업게시판 삭제: DELETE /auth/board/job/{id}
          - 취업게시판 목록 조회: GET /board/job
          - 취업게시판 조회(비로그인 사용자): GET /board/job/comment/{id}
          - 취업게시판 조회(로그인 사용자): GET /auth/board/job/comment&like/{id}
          - 취업게시판 검색(제목): GET /board/job/search/title
          - 취업게시판 검색(작성자): GET /board/job/search/writer
          - 취업게시판 검색(제목+내용): GET /board/job/search/title&content

       - QnaControoler (질문게시판)
          - 질문게시판 작성: POST /auth-student/board/qna
          - 질문게시판 작성(첨부파일 포함): POST /auth-student/board/qna/upload
          - 질문게시판 수정: PUT /auth-student/board/qna
          - 질문게시판 수정(첨부파일 포함): PUT /auth-student/board/qna/upload
          - 질문게시판 삭제: DELETE /auth-student/board/qna/{id}
          - 질문게시판 목록 조회: GET /board/qna
          - 질문게시판 조회(로그인 사용자): GET /auth-student/board/qna/comment&like/{id}
          - 질문게시판 검색(제목): GET /board/qna/search/title
          - 질문게시판 검색(작성자): GET /board/qna/search/writer
          - 질문게시판 검색(제목+내용): GET /board/qna/search/title&content
          - 질문게시판 검색(과목명): GET /board/qna/search/subject

        - CommentController (댓글)
          - 자유게시판 댓글 작성: POST /auth/free/{boardId}/comment
          - 자유게시판 댓글 삭제: DELETE /auth/free/{boardId}/comment/{commentId}
          - 취업게시판 댓글 작성: POST /auth/job/{boardId}/comment
          - 취업게시판 댓글 삭제: DELETE /auth/job/{boardId}/comment/{commentId}
          - 질문게시판 댓글 작성: POST /auth-student/qna/{boardId}/comment
          - 질문게시판 댓글 삭제: DELETE /auth-student/qna/{boardId}/comment/{commentId}

       - ContentLikeController (게시물/댓글 좋아요)
          - 좋아요 클릭: POST /like/press
          - 좋아요 취소: DELETE /like/remove/{id}
  
  - FaqController (FAQ)
     - FAQ 목록 조회: GET /faq
  
</details>

<details>
  <summary><b>관리자 페이지</b></summary>

  - UserManagementController (사용자 관리)
     - 사용자 권한 수정 : PUT /auth-admin/user
     - 사용자 권한 조회: GET /auth-admin/users

  - FaqController (FAQ)
     - FAQ 작성: POST /auth-admin/faq
     - FAQ 목록 조회: GET /faq
     - FAQ 수정: PUT /auth-admin/faq
     - FAQ 삭제: DELETE /auth-admin/faq/{id}
    
  - BannerController (배너)
     - 배너 등록: POST /auth-admin/banner
     - 배너 조회: GET /auth-admin/banner
     - 배너 수정: PUT /auth-admin/banner
     - 배너 삭제: DELETE /auth-admin/banner/{id}

  - SiteCategoryController (사이트 카테고리)
     - 사이트 카테고리 추가: POST /auth-admin/site/category
     - 사이트 카테고리 수정: PUT /auth-admin/site/category
     - 사이트 카테고리 삭제: DELETE /auth-admin/site/category/{id}
   
  - SiteInformationController (참고 사이트)
     - 참고 사이트 등록: POST /auth-admin/site
     - 참고 사이트 조회: GET /auth-admin/site
     - 참고 사이트 수정: PUT /auth-admin/site
     - 참고 사이트 삭제: DELETE /auth-admin/site/{id}
  
</details>

---

</br>

> ### Usage (수정 必)


---

</br>

> ### Test (수정 必)

</br>
