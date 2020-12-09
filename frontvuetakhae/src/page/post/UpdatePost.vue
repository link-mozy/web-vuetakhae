<template>
  <div class="container" style="margin-top: 30px;">
    <div class="writerButton">
      <v-btn small color="warning">임시 저장하기</v-btn>
      <hr />
    </div>
    <b-form-group
      label-cols="4"
      label-cols-lg="2"
      label-size="lg"
      label="제목"
      label-for="input-lg"
      autofocus
    >
      <b-form-input v-model="postData.title" id="input-lg" size="lg"></b-form-input>
    </b-form-group>

    <v-stepper v-model="e6" vertical>
      <!-- 1. 재료 입력 단계 -->
      <v-stepper-step color="red" :complete="e6 > 1" step="1">
        재료
        <small class="mt-2">드래그앤드롭으로 쉽게 요리에 필요한 재료를 입력하세요.</small>
      </v-stepper-step>
      <v-stepper-content step="1">
        <v-card class="mb-12">
          <b-container class="bv-example-row">
            <b-row>
              <b-col class="bg-my-gredient">
                <h5>요리에 필요한 재료</h5>
                <div>
                  <draggable
                    tag="span"
                    v-model="postData.content.ingredients"
                    v-bind="dragOptions"
                    @start="drag = true"
                    @end="drag = false"
                  >
                    <v-chip
                      class="mr-2 mb-2"
                      v-for="tag in postData.content.ingredients"
                      :key="tag"
                      close
                      @click:close="closeChip(tag)"
                    >{{ tag }}</v-chip>
                  </draggable>
                </div>
              </b-col>
              <b-col>
                <v-row class="m-2">
                  <v-text-field
                    label="직접 추가하기"
                    v-model="addText"
                    hide-details="auto"
                    v-on:keyup.enter="plusFood"
                  ></v-text-field>
                  <v-icon large @click="plusFood">mdi-plus</v-icon>
                </v-row>
                <div class="bg-my-box">
                  나의 냉장고
                  <div>
                    <draggable
                      tag="ul"
                      v-model="list"
                      v-bind="dragOptions"
                      @start="drag = true"
                      @end="drag = false"
                    >
                      <v-chip class="m-1" v-for="tag in list" :key="tag">{{ tag }}</v-chip>
                    </draggable>
                  </div>
                </div>
              </b-col>
            </b-row>
          </b-container>
        </v-card>
        <v-btn color="error" @click="e6 = 2">완료</v-btn>
      </v-stepper-content>
      <!-- 2. 요리 과정 단계 -->
      <v-stepper-step color="red" :complete="e6 > 2" step="2">요리 과정</v-stepper-step>
      <v-stepper-content step="2">
        <v-card class="mb-12">
          <div class="bg-my-step">
            <h5>과정 순서</h5>
            <draggable
              class="list-group"
              tag="ul"
              v-model="postData.content.steps"
              v-bind="dragOptions"
              @start="drag = true"
              @end="drag = false"
            >
              <transition-group type="transition" :name="'flip-list'">
                <li
                  id="itemSteps"
                  v-for="(tag, index) in postData.content.steps"
                  :key="tag.description"
                >
                  <v-row>
                    <v-col>
                      <v-overflow-btn
                        class="type-button mt-0"
                        :items="typeList"
                        v-model="tp[index]"
                        label="타입 선택"
                        segmented
                      ></v-overflow-btn>
                    </v-col>
                    <v-col>
                      <!-- color="rgba(191, 32, 59, 1.0)" -->
                      <v-chip
                        class="mr-2 mb-2"
                        v-for="hash in tag.hashtag"
                        :key="hash"
                        close
                        @click:close="closeHashtag(tag.hashtag, hash, index)"
                      >#{{ hash }}</v-chip>

                      <div class="input-tag">
                        <v-text-field
                          v-model="tempHashtag[index]"
                          v-on:keyup.enter="plusTag(tag.hashtag, index, tag.hashTagString)"
                          placeholder="해시태그 입력"
                        ></v-text-field>
                      </div>
                    </v-col>
                    <v-col>
                      {{ tag.description }}
                      <div class="d-flex flex-column">
                        <i aria-hidden="true"></i>
                        <div class>
                          <v-btn small @click="deleleStep(tag.description)">삭제</v-btn>
                          <v-btn small color="primary" class="ml-1">내 저장소</v-btn>
                        </div>
                      </div>
                    </v-col>
                    <v-col>
                      <input type="file" @change="previewImage(tag)" accept="image/*" />
                      <v-img :src="tag.image" height="100px" width="100px"></v-img>

                      <!-- <p>
                        업로드 준비 중 : {{ uploadValue.toFixed() + "%" }}
                        <progress
                          id="progress"
                          :value="uploadValue"
                          max="100"
                        ></progress>
                      </p>-->
                    </v-col>
                  </v-row>
                </li>
              </transition-group>
            </draggable>
          </div>
          <div class="bg-plus-step">
            <h5 class="mb-3">과정 입력</h5>
            <v-row no-gutters justify="center">
              <v-col md="5">
                <v-card>
                  <b-form-input
                    type="text"
                    placeholder="요리 과정을 입력해주세요."
                    v-model="postData.content.process"
                    v-on:keyup.enter="plusStep"
                  />
                </v-card>
              </v-col>
              <v-btn @click="plusStep" class="ml-2">과정추가</v-btn>
            </v-row>
          </div>
        </v-card>
        <v-btn color="error" class="mr-2" @click="e6 = 3">완료</v-btn>
        <v-btn color="secondary" @click="e6 = 1">뒤로 가기</v-btn>
      </v-stepper-content>
      <!-- 3. 난이도 & 소요시간 단계 -->
      <v-stepper-step color="red" :complete="e6 > 3" step="3">난이도 & 소요시간</v-stepper-step>
      <v-stepper-content step="3">
        <v-card class="mb-12" height="200px">
          난이도
          <div>
            <small>별이 많을수록 어렵습니다.</small>
            <v-rating
              v-model="postData.difficulty"
              background-color="orange lighten-3"
              color="orange"
              medium
            ></v-rating>
          </div>
          <hr />소요 시간
          <v-row class="container">
            <!-- <div class="timeinput"> -->
            <b-form-input
              type="text"
              v-model="postData.time"
              style="width:100px;height:40px;font-size:12px"
            />
            <span>시간</span>
            <!-- </div> -->
          </v-row>
        </v-card>
        <v-btn color="error" class="mr-2" @click="e6 = 4">완료</v-btn>
        <v-btn color="secondary" @click="e6 = 2">뒤로 가기</v-btn>
      </v-stepper-content>
      <!-- 4. 후기 작성 단계 -->
      <v-stepper-step color="red" step="4">후기 작성</v-stepper-step>
      <v-stepper-content step="4">
        <v-card class="mb-12 p-2">
          <v-text-field
            label="요리하면서 꿀팁이나 소감을 작성해주세요."
            v-model="postData.review"
            :rules="rules"
            hide-details="auto"
          ></v-text-field>
          <br />
          <h5 style="text-align: left;">썸네일 사진 넣기</h5>
          <v-col>
            <div>
              <input type="file" @change="onFileSelected()" accept="image/*" style="float:left" />
            </div>
            <v-img :src="postData.thumbnailImage" height="100px" width="100px" class="mb-5"></v-img>
          </v-col>
        </v-card>
        <v-btn color="error" class="mr-2" @click="updatePost">수정 완료</v-btn>
        <v-btn color="secondary" @click="e6 = 3">뒤로 가기</v-btn>
      </v-stepper-content>
    </v-stepper>
  </div>
</template>

<script>
import draggable from "vuedraggable";
import axios from "axios";
import firebase from "firebase";

const BACK_URL = "http://i3a305.p.ssafy.io:8399/api";

export default {
  name: "UpdatePost",
  components: {
    draggable,
  },
  created() {
    axios
      .get(`${BACK_URL}/users/mypage/box`, {
        headers: { "jwt-auth-token": this.$cookies.get("token") },
      })
      .then((response) => {
        console.log(response.data);
        this.list = response.data.box;
      });
    let boardurlId = this.$route.params.no;
    axios.get(`${BACK_URL}/boards/${boardurlId}`).then((response) => {
      console.log(response.data);
      this.postData.title = response.data.board.title;
      this.postData.content.ingredients = response.data.board.ingredient.split(
        " "
      );
      this.postData.difficulty = response.data.board.grade;
      this.postData.time = response.data.board.cookingTime;
      this.postData.review = response.data.board.content;
      this.postData.thumbnailImage = response.data.board.thumbnailImage;
      for (var i = 0; i < response.data.board.steps.length; i++) {
        let hasTagStr = response.data.board.tags[i] + ",";
        let hasTagArr = response.data.board.tags[i].split(",");
        if (hasTagArr[0] === "") {
          hasTagArr = [];
        }
        this.tempHashtag.push("");
        this.tp.push(0);
        this.tp[i] = response.data.board.steps[i].type;

        this.postData.content.steps.push({
          stepId: response.data.board.steps[i].stepId,
          description: response.data.board.steps[i].description,
          image: response.data.board.steps[i].image,
          type: response.data.board.steps[i].type,
          hashTagString: hasTagStr,
          hashtag: hasTagArr,
        });
      }
    });
    for (var i = 0; i < this.tp.length; i++) {
      var x = document.querySelectorAll("#itemSteps")[i];
      if (this.tp[i] === 1) {
        x.style.backgroundColor = "lightgray";
      } else if (this.tp[i] === 2) {
        x.style.backgroundColor = "lightblue";
      } else {
        x.style.backgroundColor = "khaki";
      }
    }
  },
  data() {
    return {
      imageData: null,
      picture: null,
      tp: [],
      tempHashtag: [],
      chips: [],
      items: [],

      typeList: [
        // 요리 과정 단계에서 타입을 선택할 리스트
        {
          text: "재료 손질",
          value: 1,
          callback: () => {
            console.log("재료 손질");
          },
        },
        {
          text: "요리 준비",
          value: 2,
          callback: () => console.log("요리 준비"),
        },
        { text: "플레이팅", value: 3, callback: () => console.log("플레이팅") },
      ],
      e6: 1, // 페이지 변수 (처음 시작은 1부터)
      rules: [(value) => !!value || "Required."],
      postData: {
        // post 보내야할 변수들 모음
        title: null,
        content: {
          ingredients: [],
          steps: [],
          process: null,
        },
        difficulty: null,
        time: null,
        review: null,
        thumbnailImage: null,
      },
      // list : 나의 냉장고의 데이터를 저장할 변수
      list: ["소금", "밥", "간장", "돼지고기", "오이", "김치", "감자", "설탕"],
      isDragging: false,
      delayedDragging: false,
      addText: "", // 재료 단계에서 직접 추가할 때 필요한 변수
    };
  },
  methods: {
    previewImage(tag) {
      // this.uploadValue = 0;
      this.tagImage = null;
      this.imageData = event.target.files[0];
      this.onUpload(tag);
    },
    onUpload(tag) {
      this.picture = null;
      const storageRef = firebase
        .storage()
        .ref(`${this.imageData.name}`)
        .put(this.imageData);
      storageRef.on(
        `state_changed`,
        (snapshot) => {
          // this.uploadValue =
          //   (snapshot.bytesTransferred / snapshot.totalBytes) * 100;
        },
        (error) => {
          // console.log(error.message);
        },
        () => {
          // this.uploadValue = 100;
          storageRef.snapshot.ref.getDownloadURL().then((url) => {
            tag["image"] = url;
          });
        }
      );
    },
    onFileSelected() {
      this.postData.thumbnailImage = null;
      this.selectedFile = event.target.files[0];
      const storageRef = firebase
        .storage()
        .ref(`${this.selectedFile.name}`)
        .put(this.selectedFile);
      storageRef.on(`state_changed`, () => {
        storageRef.snapshot.ref.getDownloadURL().then((url) => {
          this.postData.thumbnailImage = url;
        });
      });
    },
    deleleStep(title) {
      // 요리 과정 단계에서 순서 지울 때 필요한 메서드
      const idx = this.postData.content.steps.findIndex(function (item) {
        return item.description === title;
      });
      this.postData.content.steps.splice(idx, 1);
      this.tp.splice(idx, 1);
      this.se.splice(idx, 1);
    },
    plusFood() {
      // 재료 단계에서 재료를 추가할 때 필요한 메서드
      // 빈값일 경우 추가 안되도록 한다.
      if (this.addText === "") {
        return;
      }
      // 중복되는 데이터일 경우 추가 안되도록 한다.
      for (var i = 0; i < this.postData.content.ingredients.length; i++) {
        if (this.addText === this.postData.content.ingredients[i]) {
          this.addText = "";
          return;
        }
      }
      // 위의 경우가 아니라면 추가한다.
      this.postData.content.ingredients.push(this.addText);
      this.addText = "";
    },
    plusTag(tagHashtag, index, hashTagString) {
      if (this.tempHashtag[index] === "") {
        return;
      }
      // 중복되는 데이터일 경우 추가 안되도록 한다.
      for (var i = 0; i < tagHashtag.length; i++) {
        if (this.tempHashtag[index] === tagHashtag[i]) {
          this.tempHashtag[index] = "";
          alert("중복된 태그입니다!");
          return;
        }
      }
      this.postData.content.steps[index].hashTagString =
        this.postData.content.steps[index].hashTagString +
        this.tempHashtag[index] +
        ",";

      tagHashtag.push(this.tempHashtag[index]);
      this.tempHashtag[index] = "";
      // console.log("QQQQ" + this.postData.content.steps[index].hashTagString);
    },
    closeChip(tag) {
      // 재료 단계에서 재료를 삭제할 때 필요한 메서드
      this.postData.content.ingredients.splice(
        this.postData.content.ingredients.indexOf(tag),
        1
      );
    },
    closeHashtag(tagHashtag, hashtag, index) {
      // console.log("TTTT" + tagHashtag);
      // console.log("FFF" + hashtag);
      tagHashtag.splice(tagHashtag.indexOf(hashtag), 1);
      this.postData.content.steps[index].hashTagString = tagHashtag.join(",");
    },
    plusStep() {
      // 요리 과정 단계에서 과정을 추가할 때 작동하는 메서드
      if (this.postData.content.process === "") {
        return;
      }
      this.tp.push(0);
      this.tempHashtag.push("");
      this.postData.content.steps.push({
        description: this.postData.content.process,
        image: "no image",
        type: "",
        hashTagString: "",
        hashtag: [],
      });
      this.postData.content.process = "";
    },
    updatePost() {
      let tempSteps = [];
      // 작성이 완료되어 최종적으로 post 요청을 보내는 메서드
      let tags = [];
      for (let i = 0; i < this.tp.length; i++) {
        this.postData.content.steps[i].type = this.tp[i];
      }
      for (let i = 0; i < this.postData.content.steps.length; i++) {
        let temptags = this.postData.content.steps[i].hashTagString;
        tempSteps.push({
          description: this.postData.content.steps[i].description,
          image: this.postData.content.steps[i].image,
          type: this.postData.content.steps[i].type,
        });
        // for (let j = 0; j < this.postData.content.steps[i].hashTagString.length; j++) {
        // console.log(temptags);
        //   if (j == this.postData.content.steps[i].hashTagString.length - 1) {
        //     temptags = temptags.concat(this.postData.content.steps[i].hashtag[j]);
        //   } else {
        //     temptags = temptags.concat(this.postData.content.steps[i].hashtag[j], ",");
        //   }
        // }
        tags.push(temptags);
        // delete this.postData.content.steps[i].hashtag;
      }
      // console.log(JSON.stringify(this.postData.content.steps));
      const requestHeader = {
        headers: {
          "jwt-auth-token": this.$cookies.get("token"),
        },
      };
      let ingreString = this.postData.content.ingredients.join(" ");
      // console.log(ingreString);
      let boardurlId = this.$route.params.no;
      axios
        .put(
          `http://i3a305.p.ssafy.io:8399/api/boards/${boardurlId}`,
          {
            title: this.postData.title,
            content: this.postData.review,
            grade: this.postData.difficulty,
            cookingTime: this.postData.time,
            thumbnailImage: this.postData.thumbnailImage,
            ingredient: ingreString,
            steps: tempSteps,
            tags: tags,
          },
          requestHeader
        )
        .then((response) => {
          // console.log(response);
          alert("게시글이 성공적으로 수정됐습니다!");
          this.$router.push("/");
        })
        .catch((error) => {
          // console.log(error);
        });
    },
  },
  computed: {
    dragOptions() {
      return {
        animation: 0,
        group: "description",
      };
    },
  },
  watch: {
    isDragging(newValue) {
      if (newValue) {
        this.delayedDragging = true;
        return;
      }
      this.$nextTick(() => {
        this.delayedDragging = false;
      });
    },
    tp: function (newValue) {
      for (var i = 0; i < newValue.length; i++) {
        var x = document.querySelectorAll("#itemSteps")[i];
        if (newValue[i] === 1) {
          x.style.backgroundColor = "lightgray";
        } else if (newValue[i] === 2) {
          x.style.backgroundColor = "lightblue";
        } else {
          x.style.backgroundColor = "khaki";
        }
      }
    },
  },
};
</script>

<style>
.titles {
  float: left;
}
.flip-list-move {
  transition: transform 0.5s;
}
.bg-my-gredient {
  background-image: url("https://user-images.githubusercontent.com/60081201/89654292-60c6ec00-d903-11ea-9b36-2d6f0e1ef386.jpg");
  background-repeat: no-repeat;
  background-size: cover;
  background-position: center center;
  padding: 20px;
  border-radius: 5%;
}
.bg-my-box {
  background-color: rgb(158, 215, 238);
  padding: 20px;
  border-radius: 5%;
  min-height: 120px;
}
.bg-my-step {
  background-color: khaki;
  padding: 20px;
  border-radius: 2%;
}
.bg-plus-step {
  background-color: lightsteelblue;
  padding: 20px;
  border-radius: 2%;
}
.list-group {
  list-style: none;
}
.input-tag {
  width: 110px;
  margin-left: 80px;
}
.type-button {
  width: 150px;
  height: 25px;
}
/* .timeinput {
  margin-left: 10%;
} */
</style>
