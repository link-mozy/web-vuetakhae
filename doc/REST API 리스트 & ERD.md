# REST API 리스트

## User

| HTTP Verb | URL 패턴                                    | 설명                                                         |
| --------- | ------------------------------------------- | ------------------------------------------------------------ |
| POST      | /users/signin                               | 유저 로그인                                                  |
| POST      | /users/signup                               | 유저 회원가입                                                |
| GET       | /users/{user_id}                            | 유저 개인 정보 조회                                          |
| GET       | /users/{user_id}/mypage                     | 유저 마이페이지                                              |
| GET       | /users/info/nickname                        | 유저 닉네임 중복 검사                                        |
| PUT       | /users/info                                 | 유저 개인 정보 수정                                          |
| PUT       | /users/mypage/introduce                     | 유저 마이페이지 수정                                         |
| GET       | /users/otherpage/{닉네임}                   | 유저가 아닌 다른 사용자의 마이페이지                         |
| DELETE    | /users/{user_id}                            | 유저 정보 삭제                                               |
| GET       | /users/mypage/box                           | 유저 식자재 조회                                             |
| PUT       | /users/mypage/box                           | 마이페이지 안에 사용자 냉장고 재료 수정                      |
| DELETE    | /users                                      | 유저 정보 삭제                                               |
| GET       | /users/{user_id}/follow                     | 해당 유저의 팔로워/팔로잉 조회     <br />{<br />    follow: true      <br />    following : false     <br />} |
| GET       | /users/follow/other/{닉네임}                | 팔로우 여부 확인                                             |
| POST      | /users/follow                               | 팔로잉/팔로워 요청                                           |
| DELETE    | /users/follow/other/{닉네임}                | 팔로우 취소                                                  |
| GET       | /users/follow/list/{follower 또느 followee} | 팔로잉/팔로워 명단 조회                                      |
| POST      | /users/follow/list/{follower 또느 followee} | 다른 사용자의 팔로잉/팔로워 명단 조회                        |
| GET       | /users/steps                                | 요리 스탭(과정) 조회                                         |
| POST      | /users/steps                                | 요리 스탭(과정) 등록 +나의냉장고 버튼                        |
| DELETE    | /users/steps/{stepId}                       | 요리 스탭(과정) 삭제                                         |

## Board

| HTTP Verb | URL 패턴                                    | 설명                                                         |
| --------- | ------------------------------------------- | ------------------------------------------------------------ |
| GET       | /boards                                     | 전체 레시피 목록 조회 페이지                                 |
| POST      | /boards                                     | 신규 레시피 생성 및 form 표시                                |
| GET       | /boards/{board_pk}                          | 단일 레시피 조회와 댓글 전체 조회 및 댓글 작성 form 표시     |
| PUT       | /boards/{board_pk}                          | 기존 레시피 수정 및 form 표시                                |
| DELETE    | /boards/{board_pk}                          | 기존 레시피 삭제                                             |
| POST      | /boards/comments                            | 신규 댓글 생성                                               |
| DELETE    | /boards/comments/{comment_pk}               | 기존 댓글 삭제                                               |
| PUT       | /boards/comments/{comment_pk}               | 기존 댓글 수정                                               |
| POST      | /boards/{board_pk}                          | 레시피에 대한 쉬워요 어려워요 기능                           |
| POST      | /boards/favorite                            | 즐겨찾기 등록/취소할 때                                      |
| POST      | /boards/search                              | 키워드로 검색 기능<br />type이 1일 때 → 제목으로 검색<br /> type이 2일 때 → 글쓴이로 검색 |
| GET       | /boards/foodsafe/recipes/pages/{page}       | 무한스크롤 구현을 위한 페이징                                |
| GET       | /boards/foodsafe/recipes/{recipe number}    | 안전나라 식품 레시피 디테일 페이지                           |
| POST      | /boards/foodsafe/recipes/ingredient         | 재료로 안전나라식품 레시피 조회                              |
| GET       | /boards/foodsafe/recipes/title/{검색내용}   | 안전나라식품 레시피에서 검색 기능                            |
| GET       | /boards/foodsafe/recipes/interest/{boardId} | 관심 레시피 등록 여부                                        |
| POST      | /boards/foodsafe/recipes/interest           | 관심 레시피 등록                                             |
| DELETE    | /boards/foodsafe/recipes/interest           | 관심 레시피 취소                                             |

---

# ERD

![erd 정리](https://user-images.githubusercontent.com/60081201/90798795-5bc75b00-e34d-11ea-9266-8848413b9f77.png)

