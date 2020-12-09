<template>
  <!-- <div> -->
  <div class="container">
    <div class="writerButton" v-if="userData.nickname == detailData.nickname">
      <v-btn @click="goUpdate" small class="mr-2">수정</v-btn>
      <v-btn small color="error" @click="deletePost">삭제</v-btn>
      <hr />
    </div>
    <div class="detailHeader">
      <div class="mt-5 mb-2">
        <h1>
          {{ detailData.title }}
          <b-badge class="mr-2" variant="success">난이도 {{ detailData.grade }}</b-badge>
          <b-badge variant="secondary">소요시간 {{ detailData.cookingTime }}시간</b-badge>
        </h1>
        <span class="d-flex justify-content-end">조회 수 {{ detailData.views }}</span>
      </div>

      <b-row>
        <b-col col="1">
          <img class="profile" :src="detailData.writerImage" />
        </b-col>
        <b-col cols="9" class="writerArea">
          <p @click="goOtherpage(detailData.nickname)">{{ detailData.nickname }}</p>
          <p>{{ detailData.createAt }}</p>
        </b-col>
        <b-col cols="2">
          <b-progress
            :value="detailData.easyCount"
            :max="detailData.easyCount + detailData.difficultyCount"
            variant="warning"
            show-progress
            animated
          ></b-progress>
          <v-row>
            <div class="easyhardCss" @click="plusEasy">
              <b-icon icon="emoji-smile" scale="2" variant="warning"></b-icon>
              <p class="caption mb-0 mt-1">
                {{ detailData.easyCount }}명
                <br />쉬워요
              </p>
            </div>

            <v-spacer></v-spacer>
            <div class="easyhardCss" @click="plusHard">
              <b-icon icon="emoji-frown" scale="2" variant="secondary"></b-icon>
              <p class="caption mb-0 mt-1">
                {{ detailData.difficultyCount }}명
                <br />어려워요
              </p>
            </div>
          </v-row>
        </b-col>
      </b-row>
    </div>
    <hr />
    <div class="detailContent">
      <h4 class="detailContentItem">필요한 재료</h4>
      <div class="font">
        <p>{{ detailData.ingredient }}</p>
      </div>

      <h4 class="detailContentItem">요리 과정</h4>
      <div class="font">
        <ol v-for="(step, index) in detailData.steps" :key="step" type="1">
          <p>
            {{ index + 1 }}.
            <span v-if="step.image != 'no image'">
              <v-img :src="step.image" height="100px" width="100px"></v-img>
            </span>
            {{ step.description }}
            <br />관련 태그 :
            <b>{{ detailData.tags[index] }}</b>
          </p>
        </ol>
      </div>
      <hr />

      <h4 class="detailContentItem">후기</h4>
      {{ detailData.content }}
      <hr />

      <div class="comments">
        <h4>댓글</h4>
        <p class="d-flex justify-content-end">댓글 수 {{ detailData.comments.length }}</p>
        <b-row v-for="comment in detailData.comments" :key="comment.commentId">
          <b-col>
            <img class="profile" :src="comment.userImage" />

            <p style="text-align:center;">{{ comment.nickname }}</p>
          </b-col>
          <b-col cols="10">
            <div v-if="userData.nickname == comment.nickname">
              <input
                :value="comment.commentContent"
                @input="comment.commentContent = $event.target.value"
                class="inputLength"
              />
              <p>{{ comment.createAt }}</p>
            </div>
            <div v-else>
              <p>{{ comment.commentContent }}</p>
              <p>{{ comment.createAt }}</p>
            </div>
          </b-col>
          <b-col v-if="userData.nickname == comment.nickname">
            <v-btn @click="updateComment(comment.commentId, comment.commentContent)">수정</v-btn>
            <v-btn color="secondary" class="mt-2" @click="deleteComment(comment.commentId)">삭제</v-btn>
          </b-col>
          <b-col v-else></b-col>
        </b-row>
        <Comment @create-comment="createComment" v-if="userData.nickname != ''" />
        <p v-if="userData.nickname == ''">댓글 작성은 로그인 후 이용해주세요!!</p>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import Comment from "@/page/postItem/Comment.vue";
const BACK_URL = "http://i3a305.p.ssafy.io:8399/api";

export default {
  name: "DetailPost",
  components: { Comment },
  created() {
    let boardurlId = this.$route.params.no;
    axios.get(`${BACK_URL}/boards/${boardurlId}`).then((response) => {
      // console.log(response);
      this.detailData.boardId = response.data.board.boardId;
      this.detailData.title = response.data.board.title;
      this.detailData.nickname = response.data.board.nickname;
      this.detailData.ingredient = response.data.board.ingredient;
      this.detailData.content = response.data.board.content;
      this.detailData.createAt = response.data.board.createAt;
      this.detailData.updateAt = response.data.board.updateAt;
      this.detailData.difficultyCount = response.data.board.difficultyCount;
      this.detailData.easyCount = response.data.board.easyCount;
      this.detailData.grade = response.data.board.grade;
      this.detailData.cookingTime = response.data.board.cookingTime;
      this.detailData.thumbailImage = response.data.board.crethumbailImageateAt;
      this.detailData.steps = response.data.board.steps;
      this.detailData.tags = response.data.board.tags;
      this.detailData.views = response.data.board.views;
      this.detailData.comments = response.data.board.comments;
      this.detailData.writerImage = response.data.board.writerImage;

      // console.log(this.detailData);
    });
    axios
      .get(`${BACK_URL}/users/mypage`, {
        headers: { "jwt-auth-token": this.$cookies.get("token") },
      })
      .then((response) => {
        this.userData.nickname = response.data.mypage.nickname;
      })
      .catch((error) => {
        this.userData.nickname = "";
      });
  },
  data() {
    return {
      easy: true,
      detailData: {
        views: 0,
        boardId: "",
        title: "",
        writerImage: "",
        nickname: "",
        ingredients: "",
        content: "",
        createAt: "",
        updateAt: null,
        grade: "",
        cookingTime: "",
        thumbailImage: null,
        steps: [],
        tags: [],
        comments: [],
        difficultyCount: 0,
        easyCount: 0,
      },
      userData: {
        nickname: "",
      },
    };
  },
  methods: {
    goUpdate() {
      this.$router.push(`/update/${this.detailData.boardId}`);
    },
    deletePost() {
      axios
        .delete(`${BACK_URL}/boards/${this.detailData.boardId}`, {
          headers: { "jwt-auth-token": this.$cookies.get("token") },
        })
        .then((response) => {
          if (response.status === 200) {
            alert("삭제되었습니다.");
            this.$router.push("/");
          }
        })
        .catch((error) => {
          alert(error);
        });
    },
    plusHard() {
      axios
        .post(
          `${BACK_URL}/boards/${this.detailData.boardId}`,
          {
            pressedBtn: 1,
          },
          {
            headers: { "jwt-auth-token": this.$cookies.get("token") },
          }
        )
        .then((response) => {
          if (response.status === 200) {
            alert("어려워요를 클릭하였습니다.");
            this.$router.go();
          }
        })
        .catch((error) => {
          alert(error);
        });
    },
    plusEasy() {
      axios
        .post(
          `${BACK_URL}/boards/${this.detailData.boardId}`,
          {
            pressedBtn: 2,
          },
          {
            headers: { "jwt-auth-token": this.$cookies.get("token") },
          }
        )
        .then((response) => {
          if (response.status === 200) {
            alert("쉬워요를 클릭하였습니다.");
            this.$router.go();
          }
        })
        .catch((error) => {
          alert("로그인후 이용해주세요!");
        });
    },
    goOtherpage(userNickName) {
      // console.log(this.userData.nickname);

      if (this.userData.nickname == "") {
        alert("상대방 타임라인 구경은 로그인 후 이용해주세요!!");
        return;
      } else {
        axios
          .get(`${BACK_URL}/users/mypage`, {
            headers: { "jwt-auth-token": this.$cookies.get("token") },
          })
          .then((response) => {
            this.userData.nickname = response.data.mypage.nickname;
            if (this.userData.nickname === userNickName) {
              this.$router.push("/user/mypage");
            } else {
              this.$router.push("/users/otherpage/" + userNickName);
            }
          })
          .catch((error) => {
            alert(error);
          });
      }
    },
    createComment(comment) {
      axios
        .post(
          `${BACK_URL}/boards/comments`,
          {
            boardId: this.detailData.boardId,
            commentContent: comment,
          },
          {
            headers: { "jwt-auth-token": this.$cookies.get("token") },
          }
        )
        .then((response) => {
          // console.log(response);
          if (response.status === 200) {
            alert("댓글이 작성되었습니다!");
            axios
              .get(`${BACK_URL}/boards/${this.detailData.boardId}`)
              .then((response) => {
                this.detailData.comments = response.data.board.comments;
              });
          }
        })
        .catch((error) => {
          alert(error);
        });
    },
    deleteComment(commentId) {
      axios
        .delete(`${BACK_URL}/boards/comments/${commentId}`)
        .then((response) => {
          // console.log(response);
          if (response.status === 200) {
            alert("댓글이 삭제되었습니다.");
            axios
              .get(`${BACK_URL}/boards/${this.detailData.boardId}`)
              .then((response) => {
                this.detailData.comments = response.data.board.comments;
              });
          }
        })
        .catch((error) => {
          alert(error);
        });
    },
    updateComment(commentId, commentcontent) {
      axios
        .put(
          `${BACK_URL}/boards/comments/${commentId}`,
          {
            commentContent: commentcontent,
          },
          {
            headers: { "jwt-auth-token": this.$cookies.get("token") },
          }
        )
        .then((response) => {
          // console.log(response);
          if (response.status === 200) {
            alert("댓글이 수정되었습니다.");
            this.$router.go();
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
.detailHeader {
  text-align: left;
  vertical-align: center;
}
.detailContent {
  text-align: left;
}
.detailContentItem {
  margin-bottom: 20px;
}
.icon {
  text-align: right;
  cursor: pointer;
}
.writerArea {
  cursor: pointer;
}
.inputLength {
  width: 100%;
}
.easyhardCss {
  margin-top: 10px;
  text-align: center;
  cursor: pointer;
}
.writerButton {
  text-align: end;
}
.profile {
  margin: 0px;
  width: 50px;
  height: 50px;
}

.font {
  font-family: Verdana, Geneva, Tahoma, sans-serif;
}
</style>
