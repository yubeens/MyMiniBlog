# API 명세서

## 1. User 관련

### 회원가입
- POST /api/users/register

### 로그인
- POST /api/users/login

---

## 2. 게시글 관련 (Post)

### 게시글 목록 조회
- GET /api/posts

### 게시글 상세보기
- GET /api/posts/{id}

### 게시글 작성
- POST /api/posts

### 게시글 수정
- PUT /api/posts/{id}

### 게시글 삭제
- DELETE /api/posts/{id}

---

## 3. 할일 관련 (Todo)

### 할일 목록 조회
- GET /api/todos

### 특정 유저의 할일 조회
- GET /api/todos/{id}

### 할일 추가
- POST /api/todos

### 할일 상태 변경 (예: 완료 체크)
- PUT /api/todos/{id}

### 할일 삭제
- DELETE /api/todos/{id}
