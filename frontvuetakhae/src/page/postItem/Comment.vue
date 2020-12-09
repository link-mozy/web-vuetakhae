<template>
  <b-row>
    <b-col>
      <p style="text-align:center;">댓글 입력창</p>
      <!-- <img class="profile" :src="userData.image" />
      <p>{{ userData.nickname}}</p>-->
    </b-col>
    <b-col cols="10">
      <b-input-group>
        <b-form-input label="댓글을 입력해주세요." v-model="commentInput"></b-form-input>
        <v-btn @click="completeInput">등록</v-btn>
      </b-input-group>
    </b-col>
  </b-row>
</template>

<script>
export default {
  name: "Comment",
  data() {
    return {
      commentInput: "",
      userData: {
        nickname: "",
        image: "",
      },
    };
  },
  created() {
    axios
      .get(`${BACK_URL}/users/mypage`, {
        headers: { "jwt-auth-token": this.$cookies.get("token") },
      })
      .then((response) => {
        console.log(response);
        this.userData.nickname = response.data.mypage.nickname;
        this.userData.image = response.data.mypage.image;
      })
      .catch((error) => {
        this.userData.nickname = "";
      });
  },
  methods: {
    completeInput() {
      if (this.commentInput.trim()) {
        this.$emit("create-comment", this.commentInput);
        this.commentInput = "";
      }
    },
  },
};
</script>

<style></style>
