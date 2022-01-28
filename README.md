# ![AISW](https://github.com/JunHeon-Ch/AISW_Web_Community/blob/main/readme/logo.PNG) 커뮤니티 플랫폼
</br>

># Description

![image](https://user-images.githubusercontent.com/37904738/133571234-f5c7ed9a-9364-468d-9295-fac4a66529ab.png)

- 이 프로젝트는 대학 생활 중 필요한 정보를 찾기 위해 다양한 사이트를 확인해야 하는 불편함을 해소하고자 만들어졌습니다.
- 가천대학교 AISW학부 학생들이 학교, 학과, 학생회 공지, 대외 활동, 취업 등 대학 생활 중 필요한 정보를 이 플랫폼 내에서 모두 확인할 수 있으며, 학생들 간에 **정보 공유 및 소통을 위한 웹 커뮤니티 플랫폼**입니다.
- home page URL: http://ec2-54-180-144-125.ap-northeast-2.compute.amazonaws.com/


</br>

># Framework & Library

## Backend
- Spring Boot 2.4.1, gradle 6.7.1, Spring Data JPA, Spring Security
- OAuth2.0, JWT, Querydsl
- MySQL 8.0.22, Redis 6.2.1
- Docker 20.10.7

## Frontend
- React 17.0.1
- Redux 4.0.5
- Bootstrap 4.5.3

</br>

># Function

## 사용자 페이지
  
<details>
  <summary><b>로그인 API</b></summary>

  </br>

  - **`login`**(POST): 로그인
    - `SpringSecurityFilterChain`에 커스텀 필터를 추가함
      - `JwtAuthenticationFilter`: 사용자 인증 필터
      - `JwtAuthorizationFilter`: 사용자 인증 및 권한 처리 필터
  - **`JwtAuthenticationFilter`**: `UsernamePasswordAuthenticationFilter`를 상속받음
    - /login 요청시 실행
    - 로직</br>
      1. 클라이언트로부터 `username`, `password`를 받음
      2. `username`과 `password`로부터 `UsernamePasswordAuthenticationToken` 생성
      3. `AuthenticationManager`의 `authenticate`함수를 호출해 `AuthenticationProvider`에게 처리 위임
         - `AuthenticationManager`는 List 형태로 `AuthenticationProvider`들을 갖고 있음
         - `ProviderManager`가 갖고 있는 `Provider`들을 차례로 탐색하면서 각 `Provider`들의 `supports` 함수로 확인
      4. `AuthenticaionProvider`는 `UserDetailsService`를 구현한 `PrincipalDetailsService`의 `loadUserByUsername` 함수 실행
      5. DB에서 사용자 데이터를 꺼내 `UserDetails`를 구현한 `PrincipalDetails` 형태로 반환
      6. 인증 완료 시 `Authentication` 객체를 `SecurityContextHolder` 안에 저장
      7. 인증이 정상적이면 `successfulAuthentication` 함수 실행
      8. `access token`과 `refresh token`를 생성 후 response header에 추가
      9. `refresh token`은 `access token`이 만료됐을 때 검증하기 위해 서버 인메모리 DB(Redis)에 저장
  - **`JwtAuthorizationFilter`**: `BasicAuthenticationFilter`를 상속받음
    - 토큰 유효성 확인 후, `SpringSecurityFilterChain`이 사용자 권한을 처리할 수 있게 인증된 `Authentication` 객체를 `SecurityContextHolder`에 저장
    - 로직
      - request header에 포함된 token 추출
        1. request header에 **access token**만 포함된 경우
           - access token 유효성 검증
             1. **유효성 검증 실패**: `TokenException("invalid token", accessToken)` 던짐
             </br>→ 클라이언트에게 해당 access token은 사용할 수 없음을 알리고 다시 로그인하게 함
             2. **access token 만료**: `TokenException("access token", accessToken)` 던짐
             </br>→ 클라이언트에게 해당 access token의 유효 기간이 만료됨을 알리고 refresh token과 함께 request하게 함
             3. **유효성 검증 성공**: JWT 페이로드에 포함된 사용자 정보로부터 인증된 `Authentication` 객체를 만들어 `SecurityContextHolder`에 저장
             </br>→ `SpringSecurityFilterChain`이 사용자 권한을 처리하기 위함
        2. request header에 **access token**과 **refresh token** 모두 포함된 경우 
        </br>→ **access token이 만료된 경우**
           - refresh token 유효성 검증
           1. **유효성 검증 실패 및 refresh token 만료**: `TokenException` 던짐
              - 클라이언트에게 해당 refresh token은 사용할 수 없음을 알리고 다시 로그인하게 함
           2. **유효성 검증 성공**: JWT 페이로드에 포함된 사용자 정보로부터 인증된 `Authentication` 객체를 만들어 `SecurityContextHolder`에 저장
              - `SpringSecurityFilterChain`이 사용자 권한을 처리하기 위함
              - access token을 재발급해 response header에 추가
  - **개선해야 될 부분**
    - access token이 탈취되었을 때 문제점이 발생할 수 있기 때문에 access token의 expired time을 짧게 가져감
    - access token이 만료되었을 때 클라이언트는 클라이언트에 저장된 refresh token을 포함해 request를 함
    - **이 부분에서 refresh token 또한 탈취될 수 있음!!
    </br>→ JWT는 서버에서 저장하고 있지 않기 때문에 무력화할 수 있는 방법이 없다.**
    - refresh token은 클라이언트에 저장하는 것이 아니라 서버에 저장해서 access token이 만료되었을 때 서버에 저장된 refresh token을 확인해 새로운 access token을 생성해 클라이언트로 보내주는 것이 보안상 유리함

</br>

</details>

<details>
  <summary><b>회원 정보 API</b></summary>
    
  </br>**CRUD API**
    
  - **`signup`**(POST): 회원가입
    - `provider` 와 `providerId` 를 사용하여 `username` 생성
    - `password` 는 `BCryptPasswordEncoder` 를 사용하여 인코더

  - **`verification`**(POST): 사용자의 정보가 이미 가입된 정보인지 확인
    - `username` 을 통해 사용자의 가입 여부를 확인함
      - 가입되어 있을 경우
        - `validation` **→** `true`
      - 가입되어 있지 않은 경우
        - `validation` **→** `false`
        - `email` 을 확인하여 가천대학교 학생과 일반 학생을 구분함
        </br>→ 사용자의 `role` 을 구분하기 위함
          - `gachon.ac.kr` 이메일을 사용한 경우 → `student`
          - 다른 이메일을 사용한 경우 → `general`

  - **`update`**(PUT): 사용자 정보 수정

  - **`delete`**(DELETE): 사용자 탈퇴
</br>

</details>

<details>
  <summary><b>게시판 API (익명 작성, 조회수, 키워드 검색)</b></summary>
    
  </br>**CRUD API**
    
  - **`create`**(POST): 게시판 정보, 첨부할 파일을 받아 저장
    - **익명 작성**
      - request에 익명을 선택하였는지 확인하는 필드(`isAnonymous`)를 두어 익명 여부 판단
      - 익명 선택 → writer 필드를 익명으로 저장
      - 실명 선택 → writer 필드를 user의 name으로 저장
    - **`@Transactional`**
      - 함수 내부에서 게시물을 저장하는 `save` 함수, 파일을 저장하는 `fileService.uploadFiles` 함수를 호출하기 때문에 함수 전체를 하나의 transaction으로 만들어 처리함

  - **`read`**(GET): 게시물 조회
    - **비로그인** → 파일, 댓글 포함 Response
      - 비로그인일 경우 댓글과 게시물의 좋아요 여부를 알 수 없음
    - **로그인** → 파일, 댓글, 좋아요 포함 Response
    - **N+1 문제**: 게시물 조회 시 **fetch join** 사용으로 파일 리스트 한 번에 조회 → 1회 쿼리로 N+1 문제 해결
      - `findById` → `"select post from Post post left join fetch post.fileList where post.id = :id"`
    - **조회수**
      - 게시물을 조회할 때마다 `views` 필드 +1
      - **개선해야 할 부분**
        - 사용자가 몰리게 될 경우 조회 수 작업을 계속 데이터베이스에 업데이트하게 된다면 서버 성능이 저하됨
        - **해결**: 인메모리 DB를 사용해서 일정한 텀을 두어 데이터베이스에 업데이트하는 식의 방법이 필요함
        - **추가 문제**: 인메모리 DB와 데이터베이스와의 데이터 불일치가 생길 수 있음
        - **해결**: AOF 파일과 같이 인메모리 데이터가 휘발되더라도 데이터를 백업할 수 있는 방법 사용

  - **`update`**(PUT): 게시물 수정
    - 첨부 파일 수정
      - 기존의 첨부 파일 중 삭제할 파일 → 삭제
      - 추가할 파일 → 생성
      - 기존에 있던 파일 → 유지
    - **`@Transactional`**
      - 함수 내부에서 게시물을 저장하는 `save` 함수, 파일을 저장하는 `fileService.uploadFiles` 함수를 호출하기 때문에 함수 전체를 하나의 transaction으로 만들어 처리함
        
  - **`delete`**(DELETE): 게시물 삭제
    - 게시물 삭제 시 매핑된 파일, 댓글도 같이 삭제 됨
      - **cascade = CascadeType.REMOVE**
    - 서버에 저장된 파일 삭제

  - **`readAll`**(GET): 게시판 목록 조회
    - **페이지 처리**: 게시물 생성 일자 기준 내림차순 10개
    - 게시물 상태가 긴급(`URGENT`), 공지(`NOTICE`)인 상위 10개 게시물 조회
      </br>→ `findTop10ByStatusIn`
    - **N+1 문제**: 게시물 목록 조회 시 게시물별 N개의 추가 파일 조회 쿼리가 생성됨
        </br>→ 게시물 목록에 대해 페이지 처리를 해야하기 때문에 **batch size**를 적용해 N+1 문제 해결 (쿼리 최소 2회)
        </br>→ fetch join을 사용해 매핑된 컬렉션을 한 번에 조회할 경우 full scan한 데이터를 모두 메모리에 올려 페이지 처리를 해야 하기 때문에 성능 이슈가 생김

    
  **검색 API**
    
  - **`searchByWriter`**(GET): 작성자 명으로 게시물 검색
    - `findAllByWriterContaining`: `like`절을 사용하여 `%Writer%`인 모든 작성자 검색
    - 페이지 처리: 게시물 생성 일자 기준 내림차순 10개
  - **`searchByTitle`**(GET): 제목 명으로 게시물 검색
    - `findAllByTitleContaining`: `like`절을 사용하여 `%Title%`인 모든 제목 검색
    - 페이지 처리: 게시물 생성 일자 기준 내림차순 10개
  - **`searchByTitleOrContent`**(GET): 제목 명과 내용으로 게시물 검색
    - `findAllByTitleContainingOrContentContaining`: `like`절을 사용하여 `%Title%` or `%Content%`인 모든 제목 검색
    - 페이지 처리: 게시물 생성 일자 기준 내림차순 10개
  - **`searchBySubject`**(GET): 과목명으로 게시물 검색
    - `findAllBySubjectIn`: `in`절을 사용하여 선택된 과목의 게시물 검색
    - 페이지 처리: 게시물 생성 일자 기준 내림차순 10개

</br>

</details>

<details>
  <summary><b>댓글 API</b></summary>
    
  </br>**CRUD API**
    
  - **`create`**(POST): 댓글 작성
    - 댓글을 작성할 게시물을 먼저 조회하고 댓글인지 대댓글인지 구분하여 댓글 데이터를 저장함
    - **N+1 문제**: 댓글을 작성할 게시물 조회 시 **fetch join**을 사용하여 댓글 리스트를 한 번에 조회 → 1회 쿼리로 N+1 문제 해결
      - `findByIdWithComment` → `"select board from Board board left join fetch board.commentList where board.id = :id"`
    - **대댓글**
      - `superComment` 필드를 넣어 대댓글과 댓글 구분
      - `superComment` 와 `subComment` 는 일대다 매핑 관계를 가짐
      - `getRootComment` 함수를 통해 최상위 댓글을 찾음 (재귀 구현)
    - **익명 작성**
      - request에 익명을 선택하였는지 확인하는 필드(`isAnonymous`)를 두어 판단
        - **실명 선택** → writer 필드를 user의 name으로 저장
        - **익명 선택**
          - 게시물 작성자 → “글쓴이”
          - 게시물 작성자 X → 고유 번호 부여 “익명XX”
    - **`@Transactional`**
      - 함수 내부에서 댓글을 저장하는 `save` 함수, 알림을 생성하는 `alertService.create` 함수를 호출하기 때문에 함수 전체를 하나의 transaction으로 만들어 처리함
      
  - **`delete`**(DELETE): 댓글 삭제
      - `findCommentByIdWithSuperComment` → `"select c from Comment c left join fetch c.superComment where c.id = :id"`
    - 대댓글을 가지고 있을 경우
      - `isDeleted` 필드를 `true` 로 두어 댓글 조회시 “삭제된 댓글입니다.”라고 표시
    - 대댓글을 가지고 있지 않은 경우
      - `getDeletableAncestorComment` 함수를 통해 상위 댓글 중 삭제할 수 있는 댓글이 있는지 확인하여 모두 삭제
    - 댓글 삭제 시 매핑된 좋아요, 알림도 같이 삭제됨
      - **cascade = CascadeType.REMOVE**
    - **N+1 문제**: 삭제할 댓글 조회시 **fetch join**을 사용하여 상위 댓글을 한 번에 조회 → 1회 쿼리로 N+1 문제 해결

</br>

</details>

<details>
  <summary><b>좋아요 API</b></summary>
    
  </br>**CRUD API**
    
   - **`pressLike`** (POST): 좋아요 클릭
     - 게시물 좋아요
       - 사용자가 이전에 해당 게시물에 좋아요를 눌렀을 경우
       </br>→ `ContentLikeAlreadyExistException`
       - 사용자가 이전에 해당 게시물에 좋아요를 누르지 않은 경우
       </br>→ 좋아요 데이터 생성 후 게시물의 `likes` 필드 + 1
     - 댓글 좋아요
       - 사용자가 이전에 해당 댓글에 좋아요를 눌렀을 경우
       </br>→ `ContentLikeAlreadyExistException`
       - 사용자가 이전에 해당 댓글에 좋아요를 누르지 않은 경우
       </br>→ 좋아요 데이터 생성 후 댓글의 `likes` 필드 + 1
     - **N+1 문제**: 좋아요 누른 댓글 조회시 **fetch join**을 사용하여 게시물도 한 번에 조회 → 1회 쿼리로 N+1 문제 해결
     - **`@Transactional`**
       - 함수 내부에서 좋아요를 저장하는 `save` 함수, 게시물/댓글의 `likes` 필드를 수정하는 `setLikes` 함수, 알림을 생성하는 `alertService.create` 함수를 호출하기 때문에 함수 전체를 하나의 transaction으로 만들어 처리함
        
  - **`removeLike`** (DELETE): 좋아요 해제
    - 게시물 좋아요
      - 사용자가 이전에 해당 게시물에 좋아요를 누르지 않은 경우
      </br>→ `ContentLikeNotFoundException`
       - 사용자가 이전에 해당 게시물에 좋아요를 눌렀을 경우
       </br>→ 좋아요 데이터 삭제 후 게시물의 `likes` 필드 - 1
    - 댓글 좋아요
      - 사용자가 이전에 해당 댓글에 좋아요를 누르지 않은 경우
      </br>→ `ContentLikeNotFoundException`
      - 사용자가 이전에 해당 댓글에 좋아요를 눌렀을 경우
      </br>→ 좋아요 데이터 삭제 후 댓글의 `likes` 필드 - 1
    - **`@Transactional`**
      - 함수 내부에서 좋아요를 삭제하는 `delete` 함수, 게시물/댓글의 `likes` 필드를 수정하는 `setLikes` 함수를 호출하기 때문에 함수 전체를 하나의 transaction으로 만들어 처리함
    - 좋아요 삭제 시 매핑된 알림도 같이 삭제됨
      - **cascade = CascadeType.REMOVE**

</br>

</details>

<details>
  <summary><b>파일 첨부 API</b></summary>

  </br>**CRUD API**
    
  - **`upload`**: 게시물에 첨부한 파일 업로드
    - **개선해야 할 부분**
      - 저장해야 할 파일이 여러 개일 경우 엔터티를 개별 저장함
      </br>→ `saveAll` 함수를 사용해 bulk insert 시간을 최소화해야 함
  - **`download`** (GET): 첨부 파일 다운로드
 
</br>

</details>

<details>
  <summary><b>홈 API</b></summary>

</br>

  - **`main`** (GET): 메인 홈에 필요한 모든 데이터 조회
    - 공지사항 리스트: 생성 일자 기준 최신 10개의 데이터
      - `findTop10ByOrderByCreatedAtDesc`
    - 게시판 리스트: 생성 일자 기준 최신 10개의 데이터
      - `findTop10ByOrderByCreatedAtDesc`
    - 배너 리스트: 게시 상태가 true인 모든 데이터
      - `findAllByPublishStatus(true)`
    - 유용한 사이트 리스트: 모든 데이터
      - `findAllFetchJoinWithFile`
      - N+1 문제가 발생할 수 있기 때문에 fetch join을 사용하여 한 번의 쿼리로 매핑된 파일을 조회함
    - 로그인한 사용자일 경우 읽지 않은 알림 개수 조회
       - `countAlertByUserIdAndChecked`

</br>

</details>

<details>
  <summary><b>알림 API</b></summary>

  </br>**CRUD API**
    
  - **`create`**: 댓글/대댓글, 게시물/댓글 좋아요 구분해서 알림 생성
    - `enum class`를 사용하여  `AlertCategory.COMMENT`, `AlertCategory.NESTED_COMMENT`, `AlertCategory.LIKE_POST`, `AlertCategory.LIKE_COMMENT` 구분

  - **`readAllAlert`**(GET) : 모든 알림 조회
    - **페이지 처리**: 알림 생성 일자 기준 내림차순 10개
       
  - **`checkAlert`**(PUT): 알림 확인
    - `checked` 필드를 두어 해당 알림을 확인했는지 확인함

  - **개선해야 할 부분**
    - pub-sub 패턴으로 구조 변경
  
</details>

</br>

## 관리자 페이지

<details>
  <summary><b>배너 API</b></summary>
    
  </br>**CRUD API**
    
  - **`create`**(POST): 배너 생성
    - **`@Transactional`**
      - 함수 내부에서 배너를 저장하는 `save` 함수, 파일(배너 이미지)을 저장하는 `fileService.uploadFiles` 함수를 호출하기 때문에 함수 전체를 하나의 transaction으로 만들어 처리함
        
 - **`readAll`**(GET): 배너 목록 조회
   - **페이지 처리**: 배너 생성 일자 기준 내림차순 10개
   - **N+1 문제**: 배너 목록 조회 시 배너별 N개의 추가 파일 조회 쿼리가 생성됨
   </br>→ 게시물 목록에 대해 페이지 처리를 해야하기 때문에 **batch size**를 적용해 N+1 문제 해결 (쿼리 최소 2회)
   </br>→ fetch join을 사용해 매핑된 컬렉션을 한 번에 조회할 경우 full scan한 데이터를 모두 메모리에 올려 페이지 처리를 해야 하기 때문에 페이지 처리가 불가능함
        
  - **`update`**(PUT): 배너 수정
    - 파일(배너 이미지) 수정
      - 기존의 이미지 중 삭제할 이미지 → 삭제
      - 추가할 이미지 → 생성
      - 기존에 있던 이미지 → 유지
    - **`@Transactional`**
      - 함수 내부에서 배너를 저장하는 `save` 함수, 파일(배너 이미지)을 저장하는 `fileService.uploadFiles` 함수를 호출하기 때문에 함수 전체를 하나의 transaction으로 만들어 처리함
        
  - **`delete`**(DELETE): 배너 삭제
    - 배너 삭제 시 매핑된 파일(배너 이미지)도 같이 삭제 됨
      - **cascade = CascadeType.REMOVE**
    - 서버에 저장된 파일 삭제
    
  - **`checkPublish`**: 시작, 종료 일자를 확인하여 게시 여부 결정
    - **`@PreUpdate`** 를 사용하여 배너 게시 여부를 결정함
      - 현재 일자가 `startDate`, `endDate` 사이에 있을 경우
        - `publishStatus` → `true`
      - 현재 일자가 `startDate`, `endDate` 사이에 없을 경우
        - `publishStatus` → `false`
   
  - **`checkEndDate`**: 시작, 종료 일자를 확인하여 게시 상태 수정
    - **`@Scheduled`** 를 사용하여 배너 게시 여부를 지속적으로 확인함
      - cron 표현식 사용: `cron = "0 0 4 * * *"` → 매일 4시에 함수 실행
        - 현재 일자가 `startDate`, `endDate` 사이에 있을 경우
          - `publishStatus` → `true`
        - 현재 일자가 `startDate`, `endDate` 사이에 없을 경우
          - `publishStatus` → `false`

</br>

</details>

<details>
  <summary><b>유용한 사이트 API</b></summary>
    
  </br>**CRUD API**
    
  - **`create`** : 사이트 생성
    - **`@Transactional`**
      - 함수 내부에서 사이트를 저장하는 `save` 함수, 파일(사이트 이미지)을 저장하는 `fileService.uploadFiles` 함수를 호출하기 때문에 함수 전체를 하나의 transaction으로 만들어 처리함
        
  - **`readAll`** : 사이트 목록 조회
    - 카테고리별로 그룹을 만들어 리스폰스함
    - **N+1 문제**: 사이트 목록 조회 시 **fetch join** 사용으로 파일을 한 번에 조회 → 1회 쿼리로 N+1 문제 해결
       
  - **`update`** : 사이트 수정
    - 파일(사이트 이미지) 수정
       - 기존의 이미지 중 삭제할 이미지 → 삭제
       - 추가할 이미지 → 생성
       - 기존에 있던 이미지 → 유지
    - **`@Transactional`**
       - 함수 내부에서 사이트를 저장하는 `save` 함수, 파일(사이트 이미지)을 저장하는 `fileService.uploadFiles` 함수를 호출하기 때문에 함수 전체를 하나의 transaction으로 만들어 처리함
        
  - **`delete`** : 사이트 삭제
    - 사이트 삭제 시 매핑된 파일(사이트 이미지)도 같이 삭제 됨
      - **cascade = CascadeType.REMOVE**
    - 서버에 저장된 파일 삭제

</br>

</details>

<details>
  <summary><b>FAQ API</b></summary>
    
  </br>**CRUD API**
    
  - **`create`**(POST): FAQ 생성

  - **`readAll`** (GET): FAQ 목록 조회
    - 생성 일자 기준 내림차순 리스트 조회
        
  - **`update`** (PUT): FAQ 수정

  - **`delete`**(DELETE): FAQ 삭제

</br>

</details>

<details>
  <summary><b>사용자 관리 API</b></summary>

  </br>**CRUD API**
    
  - **`readAll`**(GET): 사용자 정보 목록 조회

  - **`changeRole`**(PUT): 사용자 권한 수정

</br>
</details>

</br>

># API

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
