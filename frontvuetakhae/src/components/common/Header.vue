<template>
  <div id="header" v-if="isHeader">
    <div class="upperHead d-flex justify-content-between">
      <router-link
        class="ml-4"
        v-bind:to="{ name: constants.URL_TYPE.POST.MAIN }"
      >
        <img class="logoImage" src="../../assets/img/logo.png" />
      </router-link>
      <div class="right" v-if="this.$cookies.get('token') == null">
        <a @click="modalShow = !modalShow" class="a_tag_modal">로그인</a>
        <a
          style="margin:0px 20px"
          @click="signUpShow = !signUpShow"
          class="a_tag_modal"
          >회원가입</a
        >
        <b-modal v-model="modalShow" hide-footer hide-header>
          <h2 class="text-center">로그인</h2>

          <div>
            <div class="div_item">
              <span class="item_100px">아이디</span>
              <input
                class="item_200px pl-2"
                type="text"
                id="loginEmail"
                ref="loginEmail"
                v-model="loginEmail"
                placeholder="이메일을 입력해주세요"
              />
            </div>

            <div class="div_item">
              <span class="item_100px">비밀번호</span>
              <input
                class="item_200px pl-2"
                type="password"
                ref="loginPassword"
                id="passwloginPasswordord"
                v-model="loginPassword"
                placeholder="영문, 숫자 혼용 8자 이상"
                v-on:keyup.enter="login()"
              />
            </div>
          </div>
          <div>
            <b-button
              class="mt-3"
              block
              variant="danger"
              size="lg"
              @click="login"
              >로그인 하기</b-button
            >

            <p class="go_signup" style="color:gray">
              아직 회원이 아니신가요?
              <b-button
                class="ml-5"
                size="sm"
                variant="outline-danger"
                @click="switchModal"
                >회원가입 하러 가기</b-button
              >
            </p>
          </div>
        </b-modal>

        <b-modal v-model="signUpShow" hide-footer hide-header>
          <h1 class="text-center">회원가입</h1>
          <div class>
            <div class="div_item row">
              <span class="item_100px col-3 p-0">아이디</span>
              <div class="col-8 p-0">
                <input
                  class="item_200px pl-2"
                  type="text"
                  id="email"
                  ref="email"
                  name="email"
                  v-validate="'required|email'"
                  v-model="email"
                  placeholder="이메일을 입력해주세요"
                />
                <div
                  class="signupCheck alert alert-danger"
                  v-if="errors.has('email')"
                >{{ errors.first('email') }}</div>
              </div>
            </div>

            <div class="div_item row">
              <span class="item_100px col-3 p-0">비밀번호</span>
              <div class="col-8 p-0">
                <input
                  class="item_200px pl-2"
                  name="password"
                  type="password"
                  ref="password"
                  v-model="password"
                  v-validate="'required|min:8'"
                  placeholder="영문, 숫자 혼용 8자 이상 사용해주세요."
                />

                <div
                  class="passwordCheck alert alert-danger"
                  v-if="errors.has('password')"
                >{{ errors.first('password') }}</div>
              </div>
            </div>

            <div class="div_item row">
              <span class="item_100px col-3 p-0">비밀번호 확인</span>
              <div class="col-8 p-0">
                <input
                  class="item_200px pl-2"
                  type="password"
                  ref="passwordConfirm"
                  id="passwordConfirm"
                  v-model="passwordConfirm"
                  name="password_confirmation"
                  v-validate="'required|confirmed:password'"
                  placeholder="비밀번호를 한번 더 입력해주세요."
                />
                <div
                  class="passwordConfirmCheck alert alert-danger"
                  v-if="errors.has('password_confirmation')"
                >{{ errors.first('password_confirmation') }}</div>
              </div>
            </div>
            <div class="div_item row">
              <span class="item_100px col-3 p-0">닉네임</span>
              <div class="col-8 p-0">
                <input
                  class="item_200px pl-2"
                  type="text"
                  name="nickname"
                  ref="nickname"
                  id="nickname"
                  v-model="nickname"
                  v-validate="'required|min:3|max:20'"
                  placeholder="10자 이내로 입력해주세요."
                />
                <div
                  class="nicknameCheck alert alert-danger"
                  v-if="errors.has('nickname')"
                >{{ errors.first('nickname') }}</div>
              </div>
            </div>
          </div>

          <b-button class="mt-3" block variant="danger" @click="signUp"
            >회원가입 하기</b-button
          >

          <p class="go_signup ml-4" @click="switchModal">
            로그인으로 돌아가기
            <b-button variant="light">
              <b-icon-arrow-return-left></b-icon-arrow-return-left>
            </b-button>
          </p>
        </b-modal>
      </div>
      <div class="right" v-if="this.$cookies.get('token') != null">
        <!-- <small>{{}}님 환영합니다. </small> -->
        <a @click="logout" class="a_tag_modal">로그아웃</a>
        <a style="margin:0px 20px" @click="goMyPage" class="a_tag_modal"
          >마이페이지</a
        >
      </div>
    </div>

    <div class="lowerHeader d-flex justify-content-around align-items-center">
      <!-- 기능 nav -->
      <div>
        <router-link class="text-white" to="/">홈</router-link>
      </div>
      <div>
        <router-link class="text-white" to="/search"
          >냉장고를 Vue탁해</router-link
        >
      </div>
      <div>
        <router-link
          v-bind:to="{ name: constants.URL_TYPE.POST.CREATEPOST }"
          class="login-btn text-white"
          v-if="this.$cookies.get('token') != null"
          >Recipe 작성하기</router-link
        >
      </div>
      <div>
        <router-link
          class="text-white"
          v-if="this.$cookies.get('token') != null"
          to="/notice"
          >공지사항</router-link
        >
      </div>
    </div>
  </div>
</template>

<script>
import constants from "../../lib/constants";
import axios from "axios";
const BACK_URL = "http://i3a305.p.ssafy.io:8399/api";

export default {
  name: "Header",
  components: {},
  props: ["isHeader"],
  computed: {},
  watch: {},
  created() {},
  methods: {
    switchModal() {
      this.modalShow = !this.modalShow;
      this.signUpShow = !this.signUpShow;
    },
    login() {
      axios
        .post("http://i3a305.p.ssafy.io:8399/api/users/signin/", {
          email: this.loginEmail,
          password: this.loginPassword,
        })
        .then((response) => {
          console.log(response);
          this.$cookies.set("token", response.data.accessToken);
          this.modalShow = !this.modalShow;
          alert("로그인이 완료됐습니다!");
          this.$router.push("/");
          axios.get(`${BACK_URL}/boards`, {
            headers: { "jwt-auth-token": this.$cookies.get("token") },
          });
          this.$router.go();
        })
        .catch((error) => {
          alert(error);
        });
    },
    signUp() {
      var exptext = /^[A-Za-z0-9+]{8,}$/;
      if (this.email.indexOf("@") === -1) {
        alert("이메일 양식을 지켜주세요!");
        this.$refs.email.focus();
        return;
      }
      if (exptext.test(this.password) === false) {
        alert("비밀번호 양식을 지켜주세요!");
        this.$refs.password.focus();
        return;
      }
      if (this.password != this.passwordConfirm) {
        alert("비밀번호를 통일시켜주세요!");
        this.$refs.passwordConfirm.focus();
        return;
      }
      if (this.nickname === "") {
        alert("닉네임을 입력해주세요!");
        this.$refs.nickname.focus();
        return;
      }
      axios
        .post("http://i3a305.p.ssafy.io:8399/api/users/signup/", {
          email: this.email,
          nickname: this.nickname,
          password: this.password,
        })
        .then((response) => {
          if (response.status === 201) {
            alert("회원가입이 완료됐습니다!");
            this.$router.push("/");
            this.signUpShow = !this.signUpShow;
          }
        })
        .catch((error) => {
          alert(error);
        });
    },
    logout() {
      this.$cookies.remove("token");
      this.$router.push("/");
      this.$router.go();
    },
    goMyPage() {
      this.$router.push("/user/mypage");
    },
  },
  data: function() {
    return {
      constants,
      keyword: "",
      isLogedin: false,
      modalShow: false,
      signUpShow: false,
      loginEmail: "",
      loginPassword: "",
      email: "",
      password: "",
      passwordConfirm: "",
      nickname: "",
      mypage: "/user/mypage",
    };
  },
};
</script>

<style scoped>
.a_tag_modal {
  margin: 5px;
  vertical-align: middle;
  color: black;
}
.logoImage {
  height: 60px;
}
.lowerHeader {
  background-color: #991a2f;
  max-width: 100%;
  height: 60px;
}
#header {
  padding: 0px;
}
.div_item {
  padding-left: 50px;
  margin-bottom: 5px;
}

.go_signup {
  text-align: center;
  margin-top: 15px;
  color: black;
}
.span_signup {
  padding-left: 35px;
  color: black;
}

.item_100px {
  display: inline-block;
  text-align: left;
  width: 100px;
}
.item_200px {
  width: 270px;
  border: 1px solid gray;
  border-radius: 5px;
}
.right {
  margin-top: 20px;
}
.text-center {
  text-align: center;
}
input[type="password"] {
  font-family: "Avenir";
}
::placeholder {
  padding: 8px;
  color: black;
  font-size: 0.7em;
}
.signupCheck {
  margin: 0px;
  height: 25px;
  font-size: 11px;
  width: 270px;
  display: flex;
  flex-direction: column;
  justify-content: center;
}
.passwordCheck {
  margin: 0px;
  height: 25px;
  font-size: 11px;
  width: 270px;
  display: flex;
  flex-direction: column;
  justify-content: center;
}
.passwordConfirmCheck {
  margin: 0px;
  height: 25px;
  font-size: 11px;
  width: 270px;
  display: flex;
  flex-direction: column;
  justify-content: center;
}
.nicknameCheck {
  margin: 0px;
  height: 25px;
  font-size: 11px;
  width: 270px;
  display: flex;
  flex-direction: column;
  justify-content: center;
}
</style>
