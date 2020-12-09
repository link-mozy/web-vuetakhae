<template>
  <div class="post">
    <SearchHomePost @search-input="searchInput" />
    <div v-if="searchDatas" class="container">
      <h2 class="m-5 0 4">검색한 레시피</h2>

      <div class="row row-cols-3">
        <ul v-for="searchData in searchDatas" :key="searchData.title">
          <v-hover v-slot:default="{ hover }" open-delay="200">
            <v-card max-width="344" class="mx-auto" :elevation="hover ? 16 : 2">
              <v-list-item>
                <v-list-item-content class="row">
                  <v-list-item-title
                    class="headline text-center col-9"
                    @click="goDetail(searchData.boardId)"
                  >
                    {{
                    searchData.title
                    }}
                  </v-list-item-title>
                  <div class="col-3" @click="heartRecipe(searchData.boardId)">
                    <span v-if="searchData.favorite">
                      <v-bottom-navigation
                        class="elevation-0"
                        :value="searchData.favorite"
                        style="width: 60px"
                        color="deep-purple"
                      >
                        <v-btn>
                          <span>즐겨찾기</span>
                          <v-icon>mdi-heart</v-icon>
                        </v-btn>
                      </v-bottom-navigation>
                    </span>
                    <span v-else>
                      <v-bottom-navigation
                        class="elevation-0"
                        :value="searchData.favorite"
                        style="width: 60px"
                        color="secondary lighten-2"
                      >
                        <v-btn>
                          <span>즐겨찾기</span>
                          <v-icon>mdi-heart</v-icon>
                        </v-btn>
                      </v-bottom-navigation>
                    </span>
                  </div>
                </v-list-item-content>
              </v-list-item>

              <v-img
                :src="searchData.thumbnailImage"
                height="194"
                @click="goDetail(searchData.boardId)"
              ></v-img>

              <v-card-text @click="goDetail(searchData.boardId)" style="text-align: left;">
                <v-list-item-subtitle class="mb-2" @click="goOtherpage(searchData.nickname)">
                  작성자 : {{ searchData.nickname }}
                  <small
                    style="float:right"
                  >{{ searchData.createAt }}</small>
                </v-list-item-subtitle>

                <p class="m-0">소요시간 {{ searchData.cookingTime }} 시간</p>난이도
                <v-rating
                  class="d-inline-flex pa-2"
                  small
                  v-model="searchData.grade"
                  background-color="orange lighten-3"
                  color="orange"
                ></v-rating>
                <v-btn icon style="float:right">
                  <img
                    @click="kakaoShare(apiData.title, apiData.boardId, apiData.thumbnailImage, apiData.nickname)"
                    src="//developers.kakao.com/assets/img/about/logos/kakaolink/kakaolink_btn_small.png"
                    width="40"
                  />
                </v-btn>
              </v-card-text>
            </v-card>
          </v-hover>
        </ul>
        <ul v-for="searchApiData in searchApiDatas" :key="searchApiData.title">
          <v-hover v-slot:default="{ hover }" open-delay="200">
            <v-card max-width="344" class="mx-auto" :elevation="hover ? 16 : 2">
              <v-list-item>
                <v-list-item-content class="row">
                  <v-list-item-title
                    class="headline text-center col-9"
                    @click="goApiDetail(searchApiData.boardId)"
                  >
                    {{
                    searchApiData.title
                    }}
                  </v-list-item-title>
                  <div class="col-3" @click="heartRecipe(searchApiData.boardId)">
                    <span v-if="searchApiData.favorite">
                      <v-bottom-navigation
                        class="elevation-0"
                        :value="searchApiData.favorite"
                        style="width: 60px"
                        color="deep-purple"
                      >
                        <v-btn>
                          <span>즐겨찾기</span>
                          <v-icon>mdi-heart</v-icon>
                        </v-btn>
                      </v-bottom-navigation>
                    </span>
                    <span v-else>
                      <v-bottom-navigation
                        class="elevation-0"
                        :value="searchApiData.favorite"
                        style="width: 60px"
                        color="secondary lighten-2"
                      >
                        <v-btn>
                          <span>즐겨찾기</span>
                          <v-icon>mdi-heart</v-icon>
                        </v-btn>
                      </v-bottom-navigation>
                    </span>
                  </div>
                </v-list-item-content>
              </v-list-item>

              <v-img
                :src="searchApiData.thumbnailImage"
                height="194"
                @click="goApiDetail(searchApiData.boardId)"
              ></v-img>

              <v-card-text @click="goApiDetail(searchApiData.boardId)" style="text-align: left;">
                <v-list-item-subtitle class="mb-2" @click="goOtherpage(searchApiData.nickname)">
                  작성자 : {{ searchApiData.nickname }}
                  <small
                    style="float:right"
                  >{{ searchApiData.createAt }}</small>
                </v-list-item-subtitle>

                <p class="m-0">소요시간 {{ searchApiData.cookingTime }} 시간</p>난이도
                <v-rating
                  class="d-inline-flex pa-2"
                  small
                  v-model="searchApiData.grade"
                  background-color="orange lighten-3"
                  color="orange"
                ></v-rating>
                <v-btn icon style="float:right">
                  <img
                    @click="kakaoShare(apiData.title, apiData.boardId, apiData.thumbnailImage, apiData.nickname)"
                    src="//developers.kakao.com/assets/img/about/logos/kakaolink/kakaolink_btn_small.png"
                    width="40"
                  />
                </v-btn>
              </v-card-text>
            </v-card>
          </v-hover>
        </ul>
      </div>
    </div>
    <img
      @click="goUp"
      class="upper"
      src="https://user-images.githubusercontent.com/60081201/90482034-a1c5c880-e16d-11ea-986a-0469e187f948.png"
      alt
    />
    <br />
    <div class="container">
      <h2 class="m-5 0 4">{{ searchType }} 레시피</h2>
      <v-col cols="12" sm="3">
        <select
          v-model="searchType"
          class="form-control search-slt"
          label="인기순"
          @change="watchBoard"
        >
          <option selected value="인기순">인기순</option>
          <option value="최신순">최신순</option>
        </select>
      </v-col>
      <div class="row row-cols-3">
        <ul v-for="backData in backDatas" :key="backData.title">
          <v-hover v-slot:default="{ hover }" open-delay="200">
            <v-card max-width="344" class="mx-auto" :elevation="hover ? 16 : 2">
              <v-list-item>
                <v-list-item-content class="row">
                  <v-list-item-title
                    class="headline text-center col-9"
                    @click="goDetail(backData.boardId)"
                  >{{ backData.title }}</v-list-item-title>
                  <div class="col-3" @click="heartRecipe(backData.boardId)">
                    <span v-if="backData.favorite">
                      <v-bottom-navigation
                        class="elevation-0"
                        :value="backData.favorite"
                        style="width: 60px"
                        color="deep-purple"
                      >
                        <v-btn>
                          <span>즐겨찾기</span>
                          <v-icon>mdi-heart</v-icon>
                        </v-btn>
                      </v-bottom-navigation>
                    </span>
                    <span v-else>
                      <v-bottom-navigation
                        class="elevation-0"
                        :value="backData.favorite"
                        style="width: 60px"
                        color="secondary lighten-2"
                      >
                        <v-btn>
                          <span>즐겨찾기</span>
                          <v-icon>mdi-heart</v-icon>
                        </v-btn>
                      </v-bottom-navigation>
                    </span>
                  </div>
                </v-list-item-content>
              </v-list-item>

              <v-img
                :src="backData.thumbnailImage"
                height="194"
                @click="goDetail(backData.boardId)"
              ></v-img>

              <v-card-text @click="goDetail(backData.boardId)" style="text-align: left;">
                <v-list-item-subtitle class="mb-2" @click="goOtherpage(backData.nickname)">
                  작성자 : {{ backData.nickname }}
                  <small style="float:right">{{ backData.createAt }}</small>
                </v-list-item-subtitle>

                <p class="m-0">소요시간 {{ backData.cookingTime }} 시간</p>난이도
                <v-rating
                  class="d-inline-flex pa-2"
                  small
                  v-model="backData.grade"
                  background-color="orange lighten-3"
                  color="orange"
                ></v-rating>
                <v-btn icon style="float:right">
                  <img
                    @click="kakaoShare(backData.title, backData.boardId, backData.thumbnailImage, backData.nickname)"
                    src="//developers.kakao.com/assets/img/about/logos/kakaolink/kakaolink_btn_small.png"
                    width="40"
                  />
                </v-btn>
              </v-card-text>
            </v-card>
          </v-hover>
        </ul>
      </div>
      <div class="m-5">
        <h2 class="mt-5">식품안전나라 레시피</h2>
        <small>식품의약품안전처에서 제공하는 레시피입니다.</small>
      </div>
      <div class="row row-cols-3">
        <ul v-for="apiData in apiDatas" :key="apiData.rcpSeq">
          <v-hover v-slot:default="{ hover }" open-delay="200">
            <v-card max-width="344" class="mx-auto" :elevation="hover ? 16 : 2">
              <v-list-item>
                <v-list-item-content class="row">
                  <v-list-item-title
                    class="headline text-center col-9"
                    @click="goApiDetail(apiData.boardId)"
                  >{{ apiData.title }}</v-list-item-title>
                  <div class="col-3" @click="heartApiRecipe(apiData.boardId)">
                    <span v-if="apiData.favorite">
                      <v-bottom-navigation
                        class="elevation-0"
                        :value="apiData.favorite"
                        style="width: 60px"
                        color="deep-purple"
                      >
                        <v-btn>
                          <span>즐겨찾기</span>
                          <v-icon>mdi-heart</v-icon>
                        </v-btn>
                      </v-bottom-navigation>
                    </span>
                    <span v-else>
                      <v-bottom-navigation
                        class="elevation-0"
                        :value="apiData.favorite"
                        style="width: 60px"
                        color="secondary lighten-2"
                      >
                        <v-btn>
                          <span>즐겨찾기</span>
                          <v-icon>mdi-heart</v-icon>
                        </v-btn>
                      </v-bottom-navigation>
                    </span>
                  </div>
                </v-list-item-content>
              </v-list-item>

              <v-img
                :src="apiData.thumbnailImage"
                height="194"
                @click="goApiDetail(apiData.boardId)"
              ></v-img>

              <v-card-text @click="goApiDetail(apiData.boardId)" style="text-align: left;">
                <v-list-item-subtitle class="mb-2" @click="goOtherpage(apiData.nickname)">
                  작성자 : {{ apiData.nickname }}
                  <small style="float:right">{{ apiData.createAt }}</small>
                </v-list-item-subtitle>

                <p class="m-0">소요시간 1.5시간</p>난이도
                <v-rating
                  class="d-inline-flex pa-2"
                  small
                  v-model="numList[1]"
                  background-color="orange lighten-3"
                  color="orange"
                ></v-rating>
                <v-btn icon style="float:right">
                  <img
                    @click="kakaoShare(apiData.title, apiData.boardId, apiData.thumbnailImage, apiData.nickname)"
                    src="//developers.kakao.com/assets/img/about/logos/kakaolink/kakaolink_btn_small.png"
                    width="40"
                  />
                </v-btn>
              </v-card-text>
            </v-card>
          </v-hover>
        </ul>
      </div>

      <infinite-loading @infinite="infiniteHandler" spinner="waveDots">
        <div
          slot="no-more"
          style="color: rgb(102, 102, 102); font-size: 14px; padding: 10px 0px;"
        >목록의 끝입니다 :)</div>
      </infinite-loading>
    </div>
  </div>
</template>
<script type="text/JavaScript" src="https://developers.kakao.com/sdk/js/kakao.min.js"></script>
<script>
import InfiniteLoading from "vue-infinite-loading";
import axios from "axios";
import SearchHomePost from "@/page/postItem/SearchHomePost.vue";

const BACK_URL = "http://i3a305.p.ssafy.io:8399/api";

export default {
  name: "Post",

  data: () => {
    return {
      searchType: "인기순",
      limit: 0,
      numList: [1, 2, 3, 4, 5],
      timeList: [1.5, 1, 0.5, 2],
      backDatas: [],
      limit: 1, // 무한스크롤 되면서 갱신될 페이지 또는 글 번호를 저장하는 변수
      apiDatas: [],
      userData: {
        nickname: "",
      },
      searchDatas: null,
      searchApiDatas: null,
    };
  },
  components: {
    SearchHomePost,
    InfiniteLoading,
  },
  created() {
    axios
      .get(`${BACK_URL}/boards`, {
        headers: { "jwt-auth-token": this.$cookies.get("token") },
      })
      .then((response) => {
        // console.log(response.data);
        this.backDatas = response.data.boards;
      });
    Kakao.init("bed1ac3b578a5c6daea9bcc807fdc6d8");
    axios
      .get(`${BACK_URL}/boards/foodsafe/recipes/pages/0`)
      .then((response) => {
        // console.log(response);
        this.apiDatas = response.data.recipes;
      });
  },
  methods: {
    watchBoard() {
      if (this.searchType === "인기순") {
        axios
          .get(`${BACK_URL}/boards`, {
            headers: { "jwt-auth-token": this.$cookies.get("token") },
          })
          .then((response) => {
            this.backDatas = response.data.boards;
          });
      } else {
        axios
          .get(`${BACK_URL}/boards/create`, {
            headers: { "jwt-auth-token": this.$cookies.get("token") },
          })
          .then((response) => {
            this.backDatas = response.data.boards;
          });
      }
    },
    heartApiRecipe(apiboardId) {
      // console.log("누를꺼야");
      axios
        .post(
          `${BACK_URL}/boards/foodsafe/recipes/interest`,
          { boardId: apiboardId },
          {
            headers: {
              "jwt-auth-token": this.$cookies.get("token"),
            },
          }
        )
        .then((response) => {
          // console.log(response);
        });
    },
    goUp() {
      window.scrollTo(0, 0);
    },
    infiniteHandler($state) {
      axios
        .get(`${BACK_URL}/boards/foodsafe/recipes/pages/` + this.limit)
        .then((response) => {
          // console.log(response.data.recipes);
          setTimeout(() => {
            if (response.data.recipes.length) {
              this.apiDatas = this.apiDatas.concat(response.data.recipes);
              $state.loaded();
              this.limit += 1;
              // console.log("after", this.apiDatas.length, this.limit);
              const EACH_LEN = 12;
              if (resp.length / EACH_LEN < 1) {
                $state.complete();
              }
            } else {
              // 끝 지정(No more data)
              $state.complete();
            }
          }, 1000);
        })
        .catch((err) => {
          console.error(err);
        });
    },
    searchInput(input, typeNum) {
      axios
        .get(`${BACK_URL}/boards/foodsafe/recipes/title/${input}`, {
          headers: { "jwt-auth-token": this.$cookies.get("token") },
        })
        .then((response) => {
          // console.log(response);
          if (response.status === 200) {
            this.searchApiDatas = response.data.recipes;
          }
        })
        .catch((error) => {
          alert(error);
        });
      axios
        .post(
          `${BACK_URL}/boards/search`,
          {
            keyword: input,
            type: typeNum,
          },
          {
            headers: { "jwt-auth-token": this.$cookies.get("token") },
          }
        )
        .then((response) => {
          // console.log(response);
          if (response.status === 200) {
            this.searchDatas = response.data.boards;
          }
        })
        .catch((error) => {
          alert(error);
        });
    },
    heartRecipe(boardId) {
      // console.log(boardId);
      // 즐겨찾기 눌렀을 경우 사용자 데이터에 추가하기
      axios
        .post(
          `${BACK_URL}/boards/favorite`,
          {
            boardId: boardId,
          },
          {
            headers: { "jwt-auth-token": this.$cookies.get("token") },
          }
        )
        .then((response) => {
          // console.log(response);
          if (response.status === 200) {
            // this.$router.go();
            axios
              .get(`${BACK_URL}/boards`, {
                headers: { "jwt-auth-token": this.$cookies.get("token") },
              })
              .then((response) => {
                // console.log(response.data);
                this.backDatas = response.data.boards;
              });
          }
        })
        .catch((error) => {
          alert("로그인 후 이용해주세요!");
        });
    },
    kakaoShare(title, boardId, imgUrl, nickName) {
      Kakao.Link.sendDefault({
        objectType: "feed",
        content: {
          title: title, // 콘텐츠의 타이틀
          description: "작성자 : " + nickName, // 콘텐츠 상세설명
          imageUrl: imgUrl, // 썸네일 이미지
          link: {
            mobileWebUrl: "http://i3a305.p.ssafy.io/#/detail/" + boardId, // 모바일 카카오톡에서 사용하는 웹 링크 URL
            webUrl: "http://i3a305.p.ssafy.io/#/detail/" + boardId, // PC버전 카카오톡에서 사용하는 웹 링크 URL
          },
        },
        social: {
          likeCount: 0, // LIKE 개수
          commentCount: 0, // 댓글 개수
          sharedCount: 0, // 공유 회수
        },
        buttons: [
          {
            title: "게시글 확인", // 버튼 제목
            link: {
              mobileWebUrl: "http://i3a305.p.ssafy.io/#/detail/" + boardId, // 모바일 카카오톡에서 사용하는 웹 링크 URL
              webUrl: "http://i3a305.p.ssafy.io/#/detail/" + boardId, // PC버전 카카오톡에서 사용하는 웹 링크 URL
            },
          },
        ],
      });
    },
    goDetail(boardId) {
      this.$router.push("/detail/" + boardId);
    },
    goApiDetail(boardId) {
      this.$router.push("/foodsafe/detail/" + boardId);
    },
    goOtherpage(nickname) {
      axios
        .get(`${BACK_URL}/users/mypage`, {
          headers: { "jwt-auth-token": this.$cookies.get("token") },
        })
        .then((response) => {
          this.userData.nickname = response.data.mypage.nickname;
          if (this.userData.nickname === nickname) {
            this.$router.push("/user/mypage");
          } else {
            this.$router.push("/users/otherpage/" + nickname);
          }
        })
        .catch(() => {
          alert("로그인 후 이용 가능합니다.");
        });
    },
  },
};
</script>

<style>
.postcard {
  cursor: pointer;
}
.input:focus {
  outline: none;
}
.v-card {
  cursor: pointer;
}
.element.style {
  width: 60px;
}
.search-img {
  float: right;
  margin-right: 10px;
  cursor: pointer;
}
.search-input {
  width: 600px;
  height: 50px;
  border: 1px solid black;
  border-radius: 100px;
  background-color: white;
}
.search-input-text {
  width: 300px;
  size: 30px;
  font-size: 20px;
  border: none;
  margin-right: 100px;
  letter-spacing: 2px;
  padding-left: 5px;
}
.search-bar {
  background-image: url("https://user-images.githubusercontent.com/60081201/88775069-3799b380-d1bf-11ea-8473-8e1e322c1923.jpg");
  -webkit-background-size: cover;
  -moz-background-size: cover;
  -o-background-size: cover;
  background-size: cover;
  height: 400px;
}
.search-bar-title {
  font-weight: bold;
  padding: 90px;
  /* margin-bottom: 90px; */
  /* padding-top: 90px; */
}
.search-bar-input {
  width: 100px;
}
.v-application .headline {
  font-family: "TmoneyRoundWindExtraBold" !important;
}
.upper {
  position: fixed;
  left: 90%;
  top: 90%;
  cursor: pointer;
}
</style>
