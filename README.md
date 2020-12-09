# Sub-PJT3 `냉장고를 Vue 탁해`

## :smiley: 프로젝트 소개 

### 프로젝트 이름

<img src="https://user-images.githubusercontent.com/60081201/88865312-83417100-d242-11ea-9cc2-c83c84303b21.png" alt="logo png" style="zoom:50%;" />

### 프로젝트 소개

`냉장고를 Vue탁해 프로젝트`는 **사용자가 등록한 재료를 활용하여 맞춤 레시피를 제공하는 웹 블로그**입니다.

핵심 기능은 다음과 같습니다.

- 사용자가 자신의 냉장고 재료들을 등록하면 재료 데이터를 기반으로 맞춤 레시피를 제공합니다.
- 자신만의 요리 레시피 글을 작성할 때 `냉장고를 vue탁해`에서 제공하는 에디터를 통해 쉽고 즐겁게 등록할 수 있습니다.



### 주요 기술 스택

<img src="https://img.shields.io/badge/platform-Web-yellow" alt="기술스택" style="zoom:120%;" /><img src="https://img.shields.io/badge/Frontend-Vue.js, css/html/JavaScript, vuetify, BoostrapVue-green" alt="기술스택" style="zoom:120%;" /><img src="https://img.shields.io/badge/database-MySQL, mariaDB-yellowgreen" alt="기술스택" style="zoom:120%;" /><img src="https://img.shields.io/badge/backend-Springboot, swagger-ff69b4" alt="기술스택" style="zoom:120%;" /><img src="https://img.shields.io/badge/server-AWS-9cf" alt="기술스택" style="zoom:120%;" /><img src="https://img.shields.io/badge/language-Java, JavaScript, Python-important" alt="기술스택" style="zoom:120%;" /><img src="https://img.shields.io/badge/OS-Ubuntu 18.04-blueviolet" alt="기술스택" style="zoom:120%;" /> <img src="https://img.shields.io/badge/WAS-Tomcat-lightgray" alt="기술스택" style="zoom:120%;" />



#### 기술 특이점

- JWT를 이용한 로그인 인증
- Firebase Storage 이미지 업로드 
- Kakao 공유 기능 
- 식품의약품안전처에서 제공하는 "조리식품의 레시피 DB" 데이터 활용
- 드래그앤드롭을 활용한 글 작성 기능



## :key: 프로젝트 사용법

### 로컬 웹 서버 실행 방법

#### Frontend

```bash
cd frontvuetakhae
yarn install
yarn serve
```

#### Backend

```bash
cd ~/app/backend/
./run.sh start
```



#### Brower Support

호환 브라우저는 다음과 같습니다.

| chrome<br /><img src="https://user-images.githubusercontent.com/60081201/88929957-cab51500-d2b5-11ea-98aa-013452b4eec4.PNG" alt="chrome" style="zoom: 33%;" /> | firefox<br /><img src="https://user-images.githubusercontent.com/60081201/88929960-cbe64200-d2b5-11ea-8399-c54eac3ea214.PNG" alt="firefox" style="zoom:33%;" /> |
| :----------------------------------------------------------: | :----------------------------------------------------------: |
|                            Latest                            |                            Latest                            |



### 프로젝트 아키텍처 

- MVC 패턴을 기반으로 한 **spring** 백엔드 서버 구축

- MVVM패턴을 기반으로 한 **vue.js** 프론트 엔드 구현



## :clipboard: 서비스 설명

### 인프라 구조

<img width="1473" alt="인프라" src="https://user-images.githubusercontent.com/60081201/90840952-580bf680-e396-11ea-8e9a-c2f3fbc92ad4.png">

### 파일 목록

#### FrontEnd	

```
📦src
 ┣ 📂assets
 ┃ ┣ 📂img
 ┃ ┃ ┣ 📂toolbar
 ┃ ┃ ┃ ┣ 📜bold.svg
 ┃ ┃ ┃ ┣ 📜code.svg
 ┃ ┃ ┃ ┣ 📜h1.svg
 ┃ ┃ ┃ ┣ 📜h2.svg
 ┃ ┃ ┃ ┣ 📜h3.svg
 ┃ ┃ ┃ ┣ 📜h4.svg
 ┃ ┃ ┃ ┣ 📜image.svg
 ┃ ┃ ┃ ┣ 📜italic.svg
 ┃ ┃ ┃ ┣ 📜line.svg
 ┃ ┃ ┃ ┣ 📜link.svg
 ┃ ┃ ┃ ┣ 📜p.svg
 ┃ ┃ ┃ ┣ 📜quotation1.svg
 ┃ ┃ ┃ ┣ 📜quotation2.svg
 ┃ ┃ ┃ ┣ 📜strikethrough.svg
 ┃ ┃ ┃ ┗ 📜underline.svg
 ┃ ┃ ┣ 📜BasicProfileIcon.svg
 ┃ ┃ ┣ 📜likeIcon.png
 ┃ ┃ ┣ 📜loading.gif
 ┃ ┃ ┣ 📜logo.png
 ┃ ┃ ┗ 📜shareIcon.png
 ┃ ┣ 📜logo.png
 ┃ ┗ 📜logo.svg
 ┣ 📂components
 ┃ ┗ 📂common
 ┃ ┃ ┗ 📜Header.vue
 ┣ 📂lib
 ┃ ┗ 📜constants.js
 ┣ 📂page
 ┃ ┣ 📂post
 ┃ ┃ ┣ 📜ApiDetailPost.vue
 ┃ ┃ ┣ 📜CreatePost.vue
 ┃ ┃ ┣ 📜DetailPost.vue
 ┃ ┃ ┣ 📜List.vue
 ┃ ┃ ┣ 📜NanumBarunpenB.ttf
 ┃ ┃ ┣ 📜NoticePost.vue
 ┃ ┃ ┣ 📜SearchPost.vue
 ┃ ┃ ┗ 📜UpdatePost.vue
 ┃ ┣ 📂postItem
 ┃ ┃ ┣ 📜Comment.vue
 ┃ ┃ ┣ 📜CreateStep.vue
 ┃ ┃ ┗ 📜SearchHomePost.vue
 ┃ ┗ 📂user
 ┃ ┃ ┣ 📜MyPage.vue
 ┃ ┃ ┗ 📜OtherPage.vue
 ┣ 📂plugins
 ┃ ┗ 📜vuetify.js
 ┣ 📂router
 ┃ ┗ 📜index.js
 ┣ 📜App.vue
 ┗ 📜main.js
```

#### BackEnd

```
📦src
 ┣ 📂main
 ┃ ┣ 📂java
 ┃ ┃ ┣ 📂com
 ┃ ┃ ┃ ┗ 📂web
 ┃ ┃ ┃ ┃ ┗ 📂server
 ┃ ┃ ┃ ┃ ┃ ┣ 📂config
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜StorageProperties.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜SwaggerConfig.java
 ┃ ┃ ┃ ┃ ┃ ┣ 📂controller
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜BoardRestController.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜FileRestController.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜GlobalRestExceptionHandler.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜HardEasyRestController.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜UserRestController.java
 ┃ ┃ ┃ ┃ ┃ ┣ 📂dto
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜AuthenticationRequest.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜Board.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜BoardSearchByFoodList.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜BoardSimpleDto.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜CommentDto.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜FavoriteRequestBody.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜FileResponseDto.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜FollowDto.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜FoodSafeManualDto.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜FoodSafeRecipeDto.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜HardEasy.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜ScrollDto.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜SearchByKeywordDto.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜StepOfBoardDto.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜Steps.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜StepTags.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜Tags.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜User.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜UserProfileDto.java
 ┃ ┃ ┃ ┃ ┃ ┣ 📂exception
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜FileStorageException.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜MyFileNotFoundException.java
 ┃ ┃ ┃ ┃ ┃ ┣ 📂interceptor
 ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜JwtInterceptor.java
 ┃ ┃ ┃ ┃ ┃ ┣ 📂repo
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜BoardDao.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜BoardDaoImpl.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜CommentDao.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜CommentDaoImpl.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜FollowDao.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜FollowDaoImpl.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜HardEasyDao.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜HardEasyDaoImpl.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜OpenApiDao.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜OpenApiDaoImpl.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜UserinfoDao.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜UserinfoDaoImpl.java
 ┃ ┃ ┃ ┃ ┃ ┣ 📂service
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜BoardService.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜BoardServiceImpl.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜HardEasyService.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜HardEasyServiceImpl.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜JwtService.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜StorageService.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜StorageServiceImpl.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜UserinfoService.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜UserinfoServiceImpl.java
 ┃ ┃ ┃ ┃ ┃ ┣ 📂util
 ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜FileRepository.java
 ┃ ┃ ┃ ┃ ┃ ┗ 📜ServerApplication.java
 ┃ ┃ ┗ 📂META-INF
 ┃ ┃ ┃ ┗ 📜MANIFEST.MF
 ┃ ┗ 📂resources
 ┃ ┃ ┣ 📂mybatis
 ┃ ┃ ┃ ┣ 📂mapper
 ┃ ┃ ┃ ┃ ┣ 📜boardHardEasyMapper.xml
 ┃ ┃ ┃ ┃ ┣ 📜boardMapper.xml
 ┃ ┃ ┃ ┃ ┣ 📜commentMapper.xml
 ┃ ┃ ┃ ┃ ┣ 📜openApiMapper.xml
 ┃ ┃ ┃ ┃ ┗ 📜userMapper.xml
 ┃ ┃ ┃ ┗ 📜mybatis_config.xml
 ┃ ┃ ┣ 📜application.properties
 ┃ ┃ ┗ 📜application.yml
 ┗ 📂test
 ┃ ┗ 📂java
 ┃ ┃ ┗ 📂com
 ┃ ┃ ┃ ┗ 📂web
 ┃ ┃ ┃ ┃ ┗ 📂server
 ┃ ┃ ┃ ┃ ┃ ┗ 📜ServerApplicationTests.java
```



### 페이지/기능 소개

#### 홈 화면

![메인 상단](https://user-images.githubusercontent.com/60081201/90839933-ac61a700-e393-11ea-96c5-196fdfa0ea77.PNG)

![image-20200821040303915](https://user-images.githubusercontent.com/45157374/90815624-ed42c700-e365-11ea-9cf0-085d9679e41a.png)



- 제목 또는 작성자를 기준으로 검색창을 통해 게시글 검색이 가능합니다.

- 조회수가 높은 것을 기준으로 보여주는 인기순과 최근 작성된 레시피들을 보여주는 최신순을 선택하여 게시글을 볼 수 있습니다.

- 게시글에 즐겨찾기 기능을 통해 마이페이지에서 모아볼 수 있습니다.

- 1000개 이상의 레시피가 준비되어 있으며 무한 스크롤 기능을 통해 끊임없이 레시피를 제공합니다.

  - 1000개의 데이터는 식품의약품안전처에서 제공하는 조리식품의 레시피 DB입니다.

    > https://www.foodsafetykorea.go.kr/apiMain.do

- 우측 하단의 화살표 버튼을 통해 화면 위로 바로 올라갈 수 있습니다.

#### 냉장고를 Vue탁해 화면

![image-20200821040342589](https://user-images.githubusercontent.com/45157374/90815630-ee73f400-e365-11ea-8c72-29ef1f1dcb83.png)

![image-20200821040411941](https://user-images.githubusercontent.com/45157374/90815675-06e40e80-e366-11ea-8ac6-90ea6e3eae14.png)



- 사용자가 마이페이지에서 저장한 냉장고의 재료 정보를 기준으로 레시피를 제공합니다.
- 냉장고에 있는 재료 외에 추가, 삭제가 가능하며 변화에 따라 레시피를 제공합니다.

#### Recipe 작성하기 화면

재료 입력 단계, 요리 과정 입력 단계, 난이도 & 소요시간 입력 단계, 후기 작성 단계로 총 4단계로 되어있습니다.

1. **`재료` 입력 단계**

   나의 냉장고 재료를 드래그앤드롭으로 손쉽게 입력 가능하며 입력창을 통해 추가할 수 있습니다.

   ![image-20200821040502021](https://user-images.githubusercontent.com/45157374/90815677-077ca500-e366-11ea-9210-266dba7bbf0e.png)

   

2. **`요리 과정` 입력 단계**

   요리 과정 순서를 드래그앤드롭으로 손쉽게 변경 가능합니다.

   하나의 요리 단계 과정을 입력하면 타입(재료 손질, 요리 준비)과 해시태그, 사진을 추가할 수 있습니다.

   ![image-20200821040706125](https://user-images.githubusercontent.com/45157374/90815779-2f6c0880-e366-11ea-96b2-d3d2d3471c92.png)

   

3. **`난이도 & 소요시간` 입력 단계**

   난이도는 별 5개를 기준으로 별이 많을 수록 난이도가 높음을 의미합니다.

   소요시간은 시간을 기준으로 작성됩니다.

   ![image-20200821040729135](https://user-images.githubusercontent.com/45157374/90815782-309d3580-e366-11ea-9505-1eeaba8a12d4.png)

   

4. **`후기 작성` 단계**

   요리를 하면서 느꼈던 소감이나 꿀팁을 작성하는 공간입니다.

   썸네일 사진을 추가할 수 있습니다.

   ![image-20200821040806800](https://user-images.githubusercontent.com/45157374/90815784-309d3580-e366-11ea-8b53-da9a44b9a361.png)



#### 게시물 화면

![image-20200821040837561](https://user-images.githubusercontent.com/45157374/90815785-3135cc00-e366-11ea-9c5b-0af175ccf1c1.png)

![image-20200821040850611](https://user-images.githubusercontent.com/45157374/90815786-3135cc00-e366-11ea-91b3-c66caad05a75.png)

![image-20200821040911059](https://user-images.githubusercontent.com/45157374/90815787-31ce6280-e366-11ea-9bd4-551ae4ecd9b1.png)

![image-20200821040924440](https://user-images.githubusercontent.com/45157374/90815788-31ce6280-e366-11ea-9aec-1ae2b858e077.png)

- 쉬워요 어려워요를 클릭하여 작성자가 아닌 사용자들이 체감 난이도를 나타낼 수 있습니다.
- 자신이 작성한 게시물인 경우 수정과 삭제가 가능합니다.
- 작성자의 이름을 클릭하면 작성자의 마이페이지로 이동가능합니다.
- 댓글 작성 부분을 통해 소통이 가능합니다.

#### 마이페이지 화면

![image-20200821040949244](https://user-images.githubusercontent.com/45157374/90815789-3266f900-e366-11ea-9240-18b6b4aacf96.png)

![image-20200821040959260](https://user-images.githubusercontent.com/45157374/90815791-32ff8f80-e366-11ea-84dd-f74d7f8a7470.png)

- 팔로우와 팔로잉 조회가 가능하며 다른 사람의 마이페이지에서는 팔로우 등록이 가능합니다.
- 자기소개 작성이 가능하고 프로필 사진을 업로드할 수 있습니다.
- 나의 냉장고에 재료를 추가하여 저장할 수 있습니다.
- 즐겨찾기한 레시피의 목록과 내가 작성한 레시피의 목록을 볼 수 있습니다.
- 개인 정보 수정이 가능한 페이지입니다.

#### 공지사항 화면

![image-20200821041017291](https://user-images.githubusercontent.com/45157374/90815794-32ff8f80-e366-11ea-8257-961aafd9e46b.png)

- 웹 블로그 이용자들에게 사이트 이용에 있어서 개선된 점을 공유하는 공간입니다.



## 기타

### 브랜치전략

- 저희 팀의 브랜치 전략은 **기능별로** 나누는 것입니다. 브랜치 이름은 앞부분에는 front, back을 명시하고 개발할 기능을 뒤에 작성하여 만들었습니다.

  - 예시 형태
    - front/{Front 의 담당 기능}
      - ex) front/signin
    - back/{BackEnd의 담당 기능}

### 커밋전략

1. 총 70자를 넘지 않는다.
2. 마침표는 사용하지 않는다.
3. 이슈 작업 설명 - 명령문 형식으로 작성한다.

```bash
날짜 | 이슈 작업 설명 | 작업 상태 | 작성자 이름 | 이슈번호
ex)
0720 | 소셜 로그인 기능 추가 | complete | KCY | {이슈 번호}
```

- 작업 상태
  - 준비중 : preparing
  - 개발중 : developing
  - 완료 : complete

### :calendar: ​ 개발일정

![readme 일정](https://user-images.githubusercontent.com/60081201/90843053-b12a5900-e39b-11ea-8741-2b2842f059b8.PNG)



### :school: ​팀원소개 & 소감

<img src="https://user-images.githubusercontent.com/60081201/88927797-ecf96380-d2b2-11ea-8347-550fe89453cb.PNG" alt="전자파컴공레인저표지" style="zoom: 33%;" />

- 김원정

  5인 협업을 진행하면서 프로젝트 개발 과정에 대해 알 수 있었고 계획단계에서의 중요성을 많이 느꼈습니다. 개인적으로는 Spring 작업을 이번 프로젝트로 처음 시작했고 아직도 전부다 모르지만 프레임워크에 대해 어느정도 깨달았고 재미있게 다가갈수 있었습니다. 
  팀원들께 고맙다고 말씀드리고 싶고 다들 고생 많으셨습니다. 다들 "빠이티이잉" 

- 권오정

  5명이서 하나의 프로젝트를 완성하면서 깃, 지라를 통한 협업 프로젝트 개발하는 법을 배웠습니다.
  그리고 프로젝트 초반에 와어이프레임 및 프로토타입 작성의 중요도를 다시 한 번 느꼈습니다.
  기술 명세서만을 가지고 기능을 구현하면서 저의 실력을 다시 한 번 확인하며 발전시킬 수 있었습니다. 막히는 부분에서는 많은 시행착오를 겪으며 개발하면서 그동안 부족했던 기본 지식을 채워나갈 수 있었습니다.
  마지막으로 끝까지 서로 챙겨주고 응원해준 서울 3반 5조 팀원 모두에게 감사 인사드립니다. 모두 고맙고 다음 프로젝트도 화이팅입니다!

- 김찬영

  이번 프로젝트를 통해서 현업에서 사용하고 있는 깃과 지라를 사용한 애자일 방식의 협업을 배울 수 있었고, 웹 프로젝트의 처음과 끝을 제대로 느낄 수 있었습니다. 스프링과 뷰제이에스를 배워 프로젝트에 적용해보면서 효율적인 코드에 대해 고민을 많이 하게 되었습니다. 
  마지막으로 소중한 팀원들을 만나서 정말 고맙고 프로젝트를 같이 해줘서 고맙다고 말하고싶습니다.

- 나종석

  2학기의 시작을 좋은 팀원들과 함께하여 즐겁게 진행할 수 있었습니다. 프론트와 백엔드의 경계없이 이슈가 생기면 같이 공유하고 이것을 해결해나가는 과정이 인상깊었고 팀원들과 협력을 하면서 프론트에 대한 이해도를 높일 수 있었습니다. 모두 이번 프로젝트하면서 수고하셨습니다. 

- 신유빈

  2학기 첫 프로젝트인 만큼 걱정 반 설렘 반이었습니다. 지라, 깃랩, 브랜치 처음에는 낯설었던 협업을 위한 툴들이 익숙해지고 프로젝트를 단단하게 만들어가는 과정을 통해 성장할 수 있었습니다. 좋은 팀원들과 함께하며 모르는 부분을 공부하며 채워나갈 수 있었고 보다 완성도 있는 프로젝트의 결과가 나올 수 있었다고 생각합니다. 협업을 통해 프론트와 백엔드의 연결을 보다 깊이 이해할 수 있었습니다. 전자파컴공레인저 포에버 정말 모두 감사했습니다. 항상 응원합니다.

  

### :pushpin: 유용한 사이트 정리 

- 뱃지를 커스터마이징하여 기술스택 표현하는 사이트

  > https://shields.io/
  >
  > - https://img.shields.io/badge/<LABEL>-<MESSAGE>-<COLOR>
  >
  >   다음과 같이 LABEL, MESSAGE, COLOR 순서로 입력하면 된다.

- ERD 작성하는 사이트

  > https://www.erdcloud.com/
  >
  > - 팀으로 생성하면 ERD 수정, 공유 가능하다.

- 무료 이미지
  
  > https://photo-ac.com/en
- 무료 글씨체 `눈누`

  > https://noonnu.cc/