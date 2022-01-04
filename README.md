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
├─java</br>
│  │</br>
│  └─com</br>
│      │</br>
│      └─aisw</br>
│          │</br>
│          └─community</br>
│              │  CommunityApplication.java</br>
│              │</br>
│              ├─component</br>
│              │  │  LoginUserAuditorAware.java</br>
│              │  │</br>
│              │  ├─advice</br>
│              │  │  │  ApiErrorResponse.java</br>
│              │  │  │</br>
│              │  │  ├─exception</br>
│              │  │  │      AdminNotFoundException.java</br>
│              │  │  │      AlertNotFoundException.java</br>
│              │  │  │      BannerNotFoundException.java</br>
│              │  │  │      CanNotDetermineFileTypeException.java</br>
│              │  │  │      CommentNotFoundException.java</br>
│              │  │  │      ContentLikeAlreadyExistException.java</br>
│              │  │  │      ContentLikeNotFoundException.java</br>
│              │  │  │      FaqNotFoundException.java</br>
│              │  │  │      FileNotFoundException.java</br>
│              │  │  │      FileStorageException.java</br>
│              │  │  │      MyFileNotFoundException.java</br>
│              │  │  │      NotEqualUserException.java</br>
│              │  │  │      PhoneNumberNotSuitableException.java</br>
│              │  │  │      PostNotFoundException.java</br>
│              │  │  │      PostStatusNotSuitableException.java</br>
│              │  │  │      SignUpNotSuitableException.java</br>
│              │  │  │      SiteCategoryNameNotFoundException.java</br>
│              │  │  │      SiteCategoryNotFoundException.java</br>
│              │  │  │      SiteInformationNotFoundException.java</br>
│              │  │  │      TokenException.java</br>
│              │  │  │      UserNotFoundException.java</br>
│              │  │  │      WrongRequestException.java</br>
│              │  │  │</br>
│              │  │  └─handler</br>
│              │  │          ApiExceptionHandler.java</br>
│              │  │          ExceptionHandlerFilter.java</br>
│              │  │</br>
│              │  ├─provider</br>
│              │  │      JwtTokenProvider.java</br>
│              │  │      RedisProvider.java</br>
│              │  │</br>
│              │  └─util</br>
│              │          KeyCreatorBean.java</br>
│              │</br>
│              ├─config</br>
│              │  │  CorsConfig.java</br>
│              │  │  JpaConfig.java</br>
│              │  │  QuearydslConfig.java</br>
│              │  │  WebSecurityConfig.java</br>
│              │  │</br>
│              │  ├─auth</br>
│              │  │      PrincipalDetails.java</br>
│              │  │      PrincipalDetailsService.java</br>
│              │  │</br>
│              │  ├─cache</br>
│              │  │      CacheConfig.java</br>
│              │  │      RedisConfig.java</br>
│              │  │</br>
│              │  ├─document</br>
│              │  │      SwaggerConfig.java</br>
│              │  │</br>
│              │  ├─jwt</br>
│              │  │      JwtAuthenticationFilter.java</br>
│              │  │      JwtAuthorizationFilter.java</br>
│              │  │      JwtProperties.java</br>
│              │  │</br>
│              │  └─storage</br>
│              │          FileStorageProperties.java</br>
│              │</br>
│              ├─controller</br>
│              │  │  HomeController.java</br>
│              │  │</br>
│              │  ├─admin</br>
│              │  │      BannerController.java</br>
│              │  │      FaqController.java</br>
│              │  │      SiteCategoryController.java</br>
│              │  │      SiteInformationController.java</br>
│              │  │      UserManagementController.java</br>
│              │  │</br>
│              │  ├─post</br>
│              │  │  │  AbsBulletinController.java</br>
│              │  │  │  BulletinController.java</br>
│              │  │  │  PostController.java</br>
│              │  │  │</br>
│              │  │  ├─board</br>
│              │  │  │      BoardController.java</br>
│              │  │  │      BoardPostController.java</br>
│              │  │  │      FreeController.java</br>
│              │  │  │      JobController.java</br>
│              │  │  │      QnaController.java</br>
│              │  │  │</br>
│              │  │  ├─comment</br>
│              │  │  │      CommentController.java</br>
│              │  │  │</br>
│              │  │  ├─file</br>
│              │  │  │      FileController.java</br>
│              │  │  │</br>
│              │  │  ├─like</br>
│              │  │  │      ContentLikeController.java</br>
│              │  │  │</br>
│              │  │  └─notice</br>
│              │  │          CouncilController.java</br>
│              │  │          DepartmentController.java</br>
│              │  │          NoticeController.java</br>
│              │  │          NoticePostController.java</br>
│              │  │          UniversityController.java</br>
│              │  │</br>
│              │  └─user</br>
│              │          UserController.java</br>
│              │</br>
│              ├─model</br>
│              │  ├─entity</br>
│              │  │  ├─admin</br>
│              │  │  │      Banner.java</br>
│              │  │  │      Faq.java</br>
│              │  │  │      SiteCategory.java</br>
│              │  │  │      SiteInformation.java</br>
│              │  │  │</br>
│              │  │  ├─post</br>
│              │  │  │  │  Bulletin.java</br>
│              │  │  │  │</br>
│              │  │  │  ├─board</br>
│              │  │  │  │      Board.java</br>
│              │  │  │  │      Free.java</br>
│              │  │  │  │      Job.java</br>
│              │  │  │  │      Qna.java</br>
│              │  │  │  │</br>
│              │  │  │  ├─comment</br>
│              │  │  │  │      Comment.java</br>
│              │  │  │  │</br>
│              │  │  │  ├─file</br>
│              │  │  │  │      File.java</br>
│              │  │  │  │</br>
│              │  │  │  ├─like</br>
│              │  │  │  │      ContentLike.java</br>
│              │  │  │  │</br>
│              │  │  │  └─notice</br>
│              │  │  │          Council.java</br>
│              │  │  │          Department.java</br>
│              │  │  │          Notice.java</br>
│              │  │  │          University.java</br>
│              │  │  │</br>
│              │  │  └─user</br>
│              │  │          Alert.java</br>
│              │  │          User.java</br>
│              │  │</br>
│              │  ├─enumclass</br>
│              │  │      AlertCategory.java</br>
│              │  │      BulletinStatus.java</br>
│              │  │      Campus.java</br>
│              │  │      FirstCategory.java</br>
│              │  │      Gender.java</br>
│              │  │      Grade.java</br>
│              │  │      SecondCategory.java</br>
│              │  │      UploadCategory.java</br>
│              │  │</br>
│              │  └─network</br>
│              │      │  Header.java</br>
│              │      │  Pagination.java</br>
│              │      │</br>
│              │      ├─request</br>
│              │      │  ├─admin</br>
│              │      │  │      BannerApiRequest.java</br>
│              │      │  │      FaqApiRequest.java</br>
│              │      │  │      FileUploadToBannerRequest.java</br>
│              │      │  │      FileUploadToSiteRequest.java</br>
│              │      │  │      SiteInformationApiRequest.java</br>
│              │      │  │      UserManagementApiRequest.java</br>
│              │      │  │</br>
│              │      │  ├─post</br>
│              │      │  │  ├─board</br>
│              │      │  │  │      FileUploadToFreeRequest.java</br>
│              │      │  │  │      FileUploadToJobRequest.java</br>
│              │      │  │  │      FileUploadToQnaRequest.java</br>
│              │      │  │  │      FreeApiRequest.java</br>
│              │      │  │  │      JobApiRequest.java</br>
│              │      │  │  │      QnaApiRequest.java</br>
│              │      │  │  │</br>
│              │      │  │  ├─comment</br>
│              │      │  │  │      CommentApiRequest.java</br>
│              │      │  │  │</br>
│              │      │  │  ├─like</br>
│              │      │  │  │      ContentLikeApiRequest.java</br>
│              │      │  │  │</br>
│              │      │  │  └─notice</br>
│              │      │  │          CouncilApiRequest.java</br>
│              │      │  │          DepartmentApiRequest.java</br>
│              │      │  │          FileUploadToCouncilRequest.java</br>
│              │      │  │          FileUploadToDepartmentRequest.java</br>
│              │      │  │          FileUploadToUniversityRequest.java</br>
│              │      │  │          UniversityApiRequest.java</br>
│              │      │  │</br>
│              │      │  └─user</br>
│              │      │          AlertApiRequest.java</br>
│              │      │          LoginRequest.java</br>
│              │      │          UserApiRequest.java</br>
│              │      │          VerificationRequest.java</br>
│              │      │</br>
│              │      └─response</br>
│              │          │  HomeApiResponse.java</br>
│              │          │</br>
│              │          ├─admin</br>
│              │          │      BannerApiResponse.java</br>
│              │          │      FaqApiResponse.java</br>
│              │          │      HomeBannerAndSiteResponse.java</br>
│              │          │      SiteCategoryApiResponse.java</br>
│              │          │      SiteInformationApiResponse.java</br>
│              │          │      SiteInformationByCategoryResponse.java</br>
│              │          │      SiteInformationWithFileApiResponse.java</br>
│              │          │      UserManagementApiResponse.java</br>
│              │          │</br>
│              │          ├─post</br>
│              │          │  │  BulletinApiResponse.java</br>
│              │          │  │  BulletinResponseDTO.java</br>
│              │          │  │</br>
│              │          │  ├─board</br>
│              │          │  │      BoardApiResponse.java</br>
│              │          │  │      BoardResponseDTO.java</br>
│              │          │  │      FreeApiResponse.java</br>
│              │          │  │      FreeDetailApiResponse.java</br>
│              │          │  │      JobApiResponse.java</br>
│              │          │  │      JobDetailApiResponse.java</br>
│              │          │  │      JobResponseDTO.java</br>
│              │          │  │      QnaApiResponse.java</br>
│              │          │  │      QnaDetailApiResponse.java</br>
│              │          │  │</br>
│              │          │  ├─comment</br>
│              │          │  │      CommentApiResponse.java</br>
│              │          │  │</br>
│              │          │  ├─file</br>
│              │          │  │      FileApiResponse.java</br>
│              │          │  │</br>
│              │          │  ├─like</br>
│              │          │  │      ContentLikeApiResponse.java</br>
│              │          │  │</br>
│              │          │  └─notice</br>
│              │          │          CouncilApiResponse.java</br>
│              │          │          DepartmentApiResponse.java</br>
│              │          │          NoticeApiResponse.java</br>
│              │          │          NoticeResponseDTO.java</br>
│              │          │          UniversityApiResponse.java</br>
│              │          │</br>
│              │          └─user</br>
│              │                  AlertApiResponse.java</br>
│              │                  UserApiResponse.java</br>
│              │                  VerificationApiResponse.java</br>
│              │</br>
│              ├─repository</br>
│              │  ├─admin</br>
│              │  │      BannerRepository.java</br>
│              │  │      CustomSiteInformationRepository.java</br>
│              │  │      CustomSiteInformationRepositoryImpl.java</br>
│              │  │      FaqRepository.java</br>
│              │  │      SiteCategoryRepository.java</br>
│              │  │      SiteInformationRepository.java</br>
│              │  │</br>
│              │  ├─post</br>
│              │  │  │  BulletinRepository.java</br>
│              │  │  │</br>
│              │  │  ├─board</br>
│              │  │  │      BoardRepository.java</br>
│              │  │  │      FreeRepository.java</br>
│              │  │  │      JobRepository.java</br>
│              │  │  │      QnaRepository.java</br>
│              │  │  │</br>
│              │  │  ├─comment</br>
│              │  │  │      CommentRepository.java</br>
│              │  │  │      CustomCommentRepository.java</br>
│              │  │  │      CustomCommentRepositoryImpl.java</br>
│              │  │  │</br>
│              │  │  ├─file</br>
│              │  │  │      FileRepository.java</br>
│              │  │  │</br>
│              │  │  ├─like</br>
│              │  │  │      ContentLikeRepository.java</br>
│              │  │  │</br>
│              │  │  └─notice</br>
│              │  │          CouncilRepository.java</br>
│              │  │          DepartmentRepository.java</br>
│              │  │          NoticeRepository.java</br>
│              │  │          UniversityRepository.java</br>
│              │  │</br>
│              │  └─user</br>
│              │          AlertRepository.java</br>
│              │          UserRepository.java</br>
│              │</br>
│              └─service</br>
│                  │  HomeService.java</br>
│                  │</br>
│                  ├─admin</br>
│                  │      BannerService.java</br>
│                  │      FaqService.java</br>
│                  │      SiteCategoryService.java</br>
│                  │      SiteInformationService.java</br>
│                  │      UserManagementService.java</br>
│                  │</br>
│                  ├─post</br>
│                  │  │  AbsBulletinService.java</br>
│                  │  │  BulletinService.java</br>
│                  │  │  PostService.java</br>
│                  │  │</br>
│                  │  ├─board</br>
│                  │  │      BoardPostService.java</br>
│                  │  │      BoardService.java</br>
│                  │  │      FreeService.java</br>
│                  │  │      JobService.java</br>
│                  │  │      QnaService.java</br>
│                  │  │</br>
│                  │  ├─comment</br>
│                  │  │      CommentService.java</br>
│                  │  │</br>
│                  │  ├─file</br>
│                  │  │      FileService.java</br>
│                  │  │      FileStorageService.java</br>
│                  │  │</br>
│                  │  ├─like</br>
│                  │  │      ContentLikeService.java</br>
│                  │  │</br>
│                  │  └─notice</br>
│                  │          CouncilService.java</br>
│                  │          DepartmentService.java</br>
│                  │          NoticePostService.java</br>
│                  │          NoticeService.java</br>
│                  │          UniversityService.java</br>
│                  │</br>
│                  └─user</br>
│                          AlertService.java</br>
│                          UserService.java</br>
│</br>
└─resources</br>
    │  application-jwt.properties</br>
    │  application.properties</br>
    │</br>
    ├─static</br>
    │  ├─css</br>
    │  │      base.css</br>
    │  │      bootstrap-responsive.min.css</br>
    │  │      bootstrap-table.css</br>
    │  │      bootstrap.min.css</br>
    │  │      style.css</br>
    │  │</br>
    │  ├─images</br>
    │  │      glyphicons-halflings-white.png</br>
    │  │      glyphicons-halflings.png</br>
    │  │      spring_boot.png</br>
    │  │      spring_boot_gray.png</br>
    │  │      spring_boot_green.png</br>
    │  │</br>
    │  └─js</br>
    │          bootstrap-table.js</br>
    │          bootstrap.min.js</br>
    │          jquery-2.1.3.min.js</br>
    │</br>
    └─templates</br>
        │  home.html</br>
        │  login.html</br>
        │</br>
        ├─admin</br>
        │      config.html</br>
        │</br>
        ├─layout</br>
        │      footer.html</br>
        │      header.html</br>
        │      left.html</br>
        │      top.html</br>
        │</br>
        └─user</br>
            │  messages.html</br>
            │  mypage.html</br>
            │</br>
            └─login</br>
                    register.html</br>


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
