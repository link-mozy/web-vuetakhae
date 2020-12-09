<template>
  <div class="searchPost">
    <div class="container">
      <v-card class="p-3">
        <h2 class="m-3 0 4">나의 냉장고</h2>
        <h3 v-if="emptyChip" class="white--text">요리할 재료를 입력해주세요. 냉뷰가 기다리고 있습니다.</h3>

        <div>
          <div class="left d-sm-inline-flex pa-2">
            <v-row class="m-2 inputBlank" variant="danger">
              <v-text-field
                label="직접 추가하기"
                v-model="addText"
                hide-details="auto"
                v-on:keyup.enter="
                  plusFood();
                  check();
                "
              ></v-text-field>
              <v-icon
                large
                @click="
                  plusFood();
                  check();
                "
              >mdi-plus</v-icon>
            </v-row>
          </div>
          <div>
            <v-chip
              class="m-1"
              v-for="tag in chips"
              close
              @click:close="closeChip(tag)"
              :key="tag"
            >{{ tag }}</v-chip>
            <div v-if="emptyChip">요리할 재료를 입력해주세요.</div>
          </div>
        </div>
      </v-card>

      <!-- API안에 있는 요리 레시피 -->

      <h2 class="m-5 white--text">지금 당장 가능한 요리 레시피</h2>

      <div class="row row-cols-3 searchPostContent">
        <ul v-for="apiboard in searchData.apiboards" :key="apiboard.title">
          <v-hover v-slot:default="{ hover }" open-delay="200">
            <v-card max-width="344" class="mx-auto" :elevation="hover ? 16 : 2">
              <v-list-item @click="goApiDetail(apiboard.boardId)">
                <v-list-item-content class="row">
                  <v-list-item-title class="headline text-center col-9">
                    {{
                    apiboard.title
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
                :src="apiboard.thumbnailImage"
                height="194"
                @click="goApiDetail(apiboard.boardId)"
              ></v-img>

              <v-card-text @click="goApiDetail(apiboard.boardId)" style="text-align: left">
                <v-list-item-subtitle class="mb-2">작성자 : {{ apiboard.nickname }}</v-list-item-subtitle>
                <small style="float:right">
                  {{
                  apiboard.createAt
                  }}
                </small>
                <p class="m-0">소요시간 : {{ apiboard.cookingTime }}시간</p>난이도
                <v-rating
                  class="d-inline-flex pa-2"
                  small
                  v-model="apiboard.grade"
                  background-color="orange lighten-3"
                  color="orange"
                ></v-rating>
                <v-btn icon style="float:right">
                  <img
                    @click="
                      kakaoShare(
                        apiData.title,
                        apiData.boardId,
                        apiData.thumbnailImage,
                        apiData.nickname
                      )
                    "
                    src="//developers.kakao.com/assets/img/about/logos/kakaolink/kakaolink_btn_small.png"
                    width="40"
                  />
                </v-btn>
              </v-card-text>
            </v-card>
          </v-hover>
        </ul>
      </div>
      <div class="row row-cols-3 searchPostContent">
        <ul v-for="board in searchData.boards" :key="board.title">
          <v-hover v-slot:default="{ hover }" open-delay="200">
            <v-card max-width="344" class="mx-auto" :elevation="hover ? 16 : 2">
              <v-list-item @click="goDetail(board.boardId)">
                <v-list-item-content class="row">
                  <v-list-item-title class="headline text-center col-9">{{ board.title }}</v-list-item-title>
                  <div class="col-3" @click="heartRecipe(board.boardId)">
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

              <v-img :src="board.thumbnailImage" height="194" @click="goDetail(board.boardId)"></v-img>
              <v-card-text @click="goDetail(board.boardId)" style="text-align: left;">
                <v-list-item-subtitle class="mb-2">
                  작성자 : {{ board.nickname }}
                  <small style="float:right">{{ board.createAt }}</small>
                </v-list-item-subtitle>
                <p class="caption">소요시간 : {{ board.cookingTime }}시간</p>난이도
                <v-rating
                  class="d-inline-flex pa-2"
                  small
                  v-model="board.grade"
                  background-color="orange lighten-3"
                  color="orange"
                ></v-rating>
                <v-btn icon style="float:right">
                  <img
                    @click="
                      kakaoShare(
                        apiData.title,
                        apiData.boardId,
                        apiData.thumbnailImage,
                        apiData.nickname
                      )
                    "
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
  </div>
</template>
<script
  type="text/JavaScript"
  src="https://developers.kakao.com/sdk/js/kakao.min.js"
></script>
<script>
import InfiniteLoading from "vue-infinite-loading";
import axios from "axios";
const BACK_URL = "http://i3a305.p.ssafy.io:8399/api";

export default {
  name: "SearchPost",
  data() {
    return {
      limit: 1,
      searchData: {
        boards: [],
        apiboards: [],
      },
      easy: true,
      addText: "",
      emptyChip: false,
      showDatas: [],
      frag: 0,
      chips: [
        // 마이페이지에 입력한 나의 냉장고 데이터를 넣기
      ],
    };
  },
  components: {
    InfiniteLoading,
  },
  created() {
    axios
      .get(`${BACK_URL}/users/mypage/box`, {
        headers: { "jwt-auth-token": this.$cookies.get("token") },
      })
      .then((response) => {
        // console.log(response.data);
        this.chips = response.data.box;
        axios
          .post(`${BACK_URL}/boards/foodList`, { foodList: this.chips })
          .then((response) => {
            this.searchData.boards = response.data.boards;
          });
        if (this.chips.length === 0) {
          this.emptyChip = true;
        }
        if (this.chips) {
          // console.log(response.data.recipes);
          axios
            .post(`${BACK_URL}/boards/foodsafe/recipes/ingredient`, {
              ingredient: this.chips,
              page: 0,
            })
            .then((response) => {
              // console.log(response);
              this.searchData.apiboards = response.data.recipes;
            });
        } else {
          axios
            .post(`${BACK_URL}/boards/foodsafe/recipes/ingredient`, {
              page: 0,
            })
            .then((response) => {
              // console.log(response);
              this.searchData.apiboards = response.data.recipes;
            });
        }
      });

    Kakao.init("bed1ac3b578a5c6daea9bcc807fdc6d8");
  },
  methods: {
    infiniteHandler($state) {
      // console.log("마지막");
      if (this.chips) {
        axios
          .post(`${BACK_URL}/boards/foodsafe/recipes/ingredient`, {
            ingredient: this.chips,
            page: this.limit,
          })
          .then((response) => {
            // console.log(this.limit);

            // console.log(response.data.recipes);

            setTimeout(() => {
              if (response.data.recipes.length) {
                this.searchData.apiboards = this.searchData.apiboards.concat(
                  response.data.recipes
                );
                $state.loaded();
                this.limit += 1;
                // console.log
                "after", this.searchData.apiboards.length, this.limit;
                const EACH_LEN = 12;
                if (response.data.recipes.length / EACH_LEN < 1) {
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
      } else {
        axios
          .post(`${BACK_URL}/boards/foodsafe/recipes/ingredient`, {
            page: this.limit,
          })
          .then((response) => {
            // console.log(response);
            this.searchData.apiboards = response.data.recipes;
          });
      }
    },
    changeEasy() {
      if (this.easy) {
        this.easy = false;
      } else {
        this.easy = true;
      }
    },
    remove(item) {
      this.chips.splice(this.chips.indexOf(item), 1);
      this.chips = [...this.chips];
    },
    goDetail(boardId) {
      this.$router.push("/detail/" + boardId);
    },
    goApiDetail(boardId) {
      this.$router.push("/foodsafe/detail/" + boardId);
    },
    plusFood() {
      // 빈값일 경우 추가 안되도록 한다.
      if (this.addText === "") {
        return;
      }
      // 중복되는 데이터일 경우 추가 안되도록 한다.
      for (var i = 0; i < this.chips.length; i++) {
        if (this.addText === this.chips[i]) {
          this.addText = "";
          return;
        }
      }
      // 위의 경우가 아니라면 추가한다.
      this.chips.push(this.addText);
      this.addText = "";
      this.emptyChip = false;
    },
    closeChip(tag) {
      // close 버튼 누를 경우 실행되는 메소드 (리스트에서 삭제)
      this.chips.splice(this.chips.indexOf(tag), 1);
      axios
        .post(`${BACK_URL}/boards/foodList`, { foodList: this.chips })
        .then((response) => {
          this.searchData.boards = response.data.boards;
        });
      if (this.chips) {
        axios
          .post(`${BACK_URL}/boards/foodsafe/recipes/ingredient`, {
            ingredient: this.chips,
            page: 0,
          })
          .then((response) => {
            // console.log(response);
            this.limit = 1;
            this.searchData.apiboards = response.data.recipes;
          });
      } else {
        axios
          .post(`${BACK_URL}/boards/foodsafe/recipes/ingredient`, {
            page: 0,
          })
          .then((response) => {
            // console.log(response);
            this.limit = 1;
            this.searchData.apiboards = response.data.recipes;
          });
      }
      if (this.chips.length === 0) {
        this.emptyChip = true;
        this.searchData.boards = [];
      }
    },
    check() {
      axios
        .post(`${BACK_URL}/boards/foodList`, { foodList: this.chips })
        .then((response) => {
          this.searchData.boards = response.data.boards;
          // console.log(this.searchData.boards);
        });
      if (this.chips) {
        // console.log(response.data.recipes);
        axios
          .post(`${BACK_URL}/boards/foodsafe/recipes/ingredient`, {
            ingredient: this.chips,
            page: 0,
          })
          .then((response) => {
            this.limit = 1;
            // console.log(response);
            this.searchData.apiboards = response.data.recipes;
          });
      } else {
        axios
          .post(`${BACK_URL}/boards/foodsafe/recipes/ingredient`, {
            page: 0,
          })
          .then((response) => {
            // console.log(response);
            this.limit = 1;
            this.searchData.apiboards = response.data.recipes;
          });
      }
      if (this.chips.length === 0) {
        this.emptyChip = true;
      }
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
            alert("관심레시피에 등록되었습니다.");
            // this.$router.go();
          }
        })
        .catch((error) => {
          alert(error);
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
  },
};
</script>

<style>
.searchPost {
  background-image: url("https://user-images.githubusercontent.com/60081201/89270362-33b1d980-d676-11ea-8b50-bee693e1ee36.jpg");
  -webkit-background-size: 100%;
  -moz-background-size: 100%;
  -o-background-size: 100%;
  background-size: 100%;
  min-height: 100%;
  background-attachment: fixed;
}
.searchPostContent {
  min-height: 400px;
}
</style>
