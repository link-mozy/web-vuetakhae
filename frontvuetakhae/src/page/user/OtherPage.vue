<template>
  <div class="container" style="margin-top: 30px;">
    <h1>{{ mypage.nickname }}님의 마이페이지</h1>
    <div class="header">
      <div class="box" style="background: #bdbdbd;">
        <img class="profile" :src="mypage.image" />
      </div>
      <div class="introduce">
        <h3 class="mb-5" style="text-align: left;">
          <v-menu offset-y>
            <template v-slot:activator="{ on, attrs }">
              <v-btn
                class="ma-2"
                outlined
                color="indigo"
                dark
                v-bind="attrs"
                v-on="on"
                @click="checkfollowee"
              >팔로워 {{ mypage.followingCount }} 명</v-btn>
            </template>
            <v-list>
              <v-list-item v-for="followee in followeelist" :key="followee.nickname">
                <v-list-item-title>{{ followee.nickname }}</v-list-item-title>
              </v-list-item>
            </v-list>
          </v-menu>
          <v-menu offset-y>
            <template v-slot:activator="{ on, attrs }">
              <v-btn
                class="ma-2"
                outlined
                color="indigo"
                dark
                v-bind="attrs"
                v-on="on"
                @click="checkfollower"
              >팔로잉 {{ mypage.followerCount }} 명</v-btn>
            </template>
            <v-list>
              <v-list-item v-for="follower in followerlist" :key="follower.nickname">
                <v-list-item-title>{{ follower.nickname }}</v-list-item-title>
              </v-list-item>
            </v-list>
          </v-menu>
          <span v-if="isFollow">
            <v-btn class="ml-5" color="primary" @click="cancelFollow">팔로우 취소</v-btn>
          </span>
          <span v-else>
            <v-btn class="ml-5" color="primary" @click="doFollow">팔로우 하기</v-btn>
          </span>
        </h3>
        <h3 style="text-align: left;">자기소개</h3>
        <v-col cols="12">
          <div>
            <v-card class="mx-auto" max-width="100%" outlined>
              <v-list-item three-line>
                <v-list-item-content>
                  <div class="overline mb-4">{{ mypage.introduce }}</div>
                </v-list-item-content>
              </v-list-item>
            </v-card>
          </div>
        </v-col>
      </div>
    </div>
    <!-- 나의 냉장고 코드 -->
    <div class="middle">
      <h1>{{ mypage.nickname }}의 냉장고</h1>
      <div>{{ mypage.box }}</div>
    </div>
    <div class="interest">
      <h1>{{ mypage.nickname }}님이 작성한 즐겨찾기한 목록</h1>

      <li v-for="interest in mypage.interestBoards" :key="interest" class="boardList">
        <span @click="goDetail(interest.boardId)">{{ interest.title }}</span>
        <small>{{ interest.createAt }}</small>
      </li>
      <div v-if="!mypage.interestBoards.length">아직 즐겨찾기한 레시피가 없습니다.</div>
    </div>
    <hr />
    <div class="interest">
      <h1>{{ mypage.nickname }}님이 작성한 레시피 목록</h1>
      <li v-for="board in mypage.myBoards" :key="board" class="boardList">
        <span @click="goDetail(board.boardId)">{{ board.title }}</span>
        <small>{{ board.createAt }}</small>
      </li>
      <div v-if="!mypage.myBoards.length">
        작성한 레시피가 없습니다.
        <br />레시피 작성하기 버튼을 통해 나만의 레시피를 작성해보세요.
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";
const BACK_URL = "http://i3a305.p.ssafy.io:8399/api";

export default {
  name: "OtherPage",
  data() {
    return {
      followerlist: [],
      followeelist: [],
      isFollow: false,
      modalShow: false,
      addText: "",
      emptyChip: false,
      mypage: {
        nickname: "",
        introduce: "",
        box: "",
        followingCount: "",
        followerCount: "",
        myBoards: [],
        interestBoards: [],
        image: "",
      },
      nicknameCheck: false,
    };
  },
  created() {
    let nicknameurl = this.$route.params.nickname;
    axios
      .get(`${BACK_URL}/users/otherpage/${nicknameurl}`, {
        headers: { "jwt-auth-token": this.$cookies.get("token") },
      })
      .then((response) => {
        // console.log(response);
        this.mypage.nickname = response.data.mypage.nickname;
        this.mypage.introduce = response.data.mypage.introduce;
        this.mypage.box = response.data.mypage.box;
        this.mypage.followingCount = response.data.mypage.followingCount;
        this.mypage.followerCount = response.data.mypage.followerCount;
        this.mypage.myBoards = response.data.mypage.myBoards;
        this.mypage.interestBoards = response.data.mypage.interestBoards;
        this.mypage.image = response.data.mypage.image;
      });
    axios
      .get(`${BACK_URL}/users/follow/other/${nicknameurl}`, {
        headers: { "jwt-auth-token": this.$cookies.get("token") },
      })
      .then((response) => {
        if (response.status === 200) {
          // console.log(response);
          if (response.data.follow == "yes") {
            this.isFollow = true;
          } else {
            this.isFollow = false;
          }
        }
      })
      .catch((error) => {
        alert(error);
      });
  },
  methods: {
    goDetail(boardId) {
      this.$router.push("/detail/" + boardId);
    },
    doFollow() {
      axios
        .post(
          `${BACK_URL}/users/follow/`,
          {
            follow: "follower",
            other: this.mypage.nickname,
          },
          {
            headers: { "jwt-auth-token": this.$cookies.get("token") },
          }
        )
        .then((response) => {
          if (response.status === 200) {
            this.isFollow = true;
            axios
              .get(`${BACK_URL}/users/otherpage/${this.mypage.nickname}`, {
                headers: { "jwt-auth-token": this.$cookies.get("token") },
              })
              .then((response) => {
                this.mypage.followingCount =
                  response.data.mypage.followingCount;
              });
          }
        })
        .catch((error) => {
          alert(error);
        });
    },
    cancelFollow() {
      axios
        .delete(`${BACK_URL}/users/follow/other/${this.mypage.nickname}`, {
          headers: { "jwt-auth-token": this.$cookies.get("token") },
        })
        .then((response) => {
          if (response.status === 200) {
            this.isFollow = false;
            axios
              .get(`${BACK_URL}/users/otherpage/${this.mypage.nickname}`, {
                headers: { "jwt-auth-token": this.$cookies.get("token") },
              })
              .then((response) => {
                this.mypage.followingCount =
                  response.data.mypage.followingCount;
              });
          }
        })
        .catch((error) => {
          alert(error);
        });
    },
    checkfollower() {
      axios
        .post(
          `${BACK_URL}/users/follow/list/follower/`,
          { other: this.mypage.nickname },
          {
            headers: { "jwt-auth-token": this.$cookies.get("token") },
          }
        )
        .then((response) => {
          if (response.status === 200) {
            this.followerlist = response.data.users;
          }
        })
        .catch((error) => {
          alert(error);
        });
    },
    checkfollowee() {
      axios
        .post(
          `${BACK_URL}/users/follow/list/followee/`,
          { other: this.mypage.nickname },
          {
            headers: { "jwt-auth-token": this.$cookies.get("token") },
          }
        )
        .then((response) => {
          if (response.status === 200) {
            this.followeelist = response.data.users;
          }
        })
        .catch((error) => {
          alert(error);
        });
    },
  },
};
</script>

<style>
.text-intro {
  border: 1px dotted black;
  padding: 5px;
  height: 60px;
}
.interest {
  margin-top: 30px;
  text-align: left;
  padding: 10px;
}
.middle {
  text-align: left;
  margin-top: 50px;
  background-color: skyblue;
  border-radius: 1%;
  padding: 10px;
}
.header {
  margin-top: 50px;
  width: 100%;
  overflow: auto;
}
.small {
  font-size: 0.4em;
  margin: 2px;
}
.box {
  display: inline-block;
  width: 15%;
  height: 150px;
  float: left;
  border-radius: 30%;
  overflow: hidden;
}
.introduce {
  display: inline-block;
  width: 85%;
  padding-left: 55px;
}
.profile {
  width: 100%;
  height: 100%;
  object-fit: cover;
}
.bottom-button {
  margin-bottom: 50px;
}
input[type="password"] {
  font-family: "Avenir";
}
::placeholder {
  padding: 8px;
  color: black;
  font-size: 0.7em;
}
input[value="mypage.nickname"] {
  margin-left: 2px;
}
.boardList {
  cursor: pointer;
}
</style>