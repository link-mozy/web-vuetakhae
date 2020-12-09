<template>
  <div class="container">
    <div class="detailHeader">
      <h1 class="mt-5 mb-2">
        {{ detailApiData.title }}
        <b-badge class="mr-2" variant="success">난이도 {{ detailApiData.grade }}</b-badge>
        <b-badge variant="secondary">소요시간 {{ detailApiData.cookingTime }}시간</b-badge>
      </h1>

      <b-row>
        <b-col col="1">
          <b-avatar class="mt-2 pl-0"></b-avatar>
        </b-col>
        <b-col cols="11" class="writerArea">
          <p>{{ detailApiData.nickname }}</p>
          <p>{{ detailApiData.createAt }}</p>
        </b-col>
      </b-row>
    </div>
    <hr />
    <div class="detailContent">
      <h4 class="detailContentItem">필요한 재료</h4>
      <p>{{ detailApiData.ingredient }}</p>

      <h4 class="detailContentItem">과정</h4>

      <ul v-for="step in detailApiData.steps" :key="step.manualText">
        <p>{{ step.manualText }}</p>
        <div v-if="step.mnualImg">
          <img :src="step.mnualImg" alt="요리과정 사진" />
        </div>
      </ul>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import _ from "lodash";
const BACK_URL = "http://i3a305.p.ssafy.io:8399/api";

export default {
  name: "ApiDetailPost",
  created() {
    let boardurlId = this.$route.params.no;
    axios
      .get(`${BACK_URL}/boards/foodsafe/recipes/${boardurlId}`)
      .then((response) => {
        // console.log(response);
        this.detailApiData.boardId = response.data.recipes[0].rcpSeq;
        this.detailApiData.title = response.data.recipes[0].rcpNm;
        this.detailApiData.nickname = "식품안전나라";
        this.detailApiData.ingredient = response.data.recipes[0].rcpPartsDtls;
        this.detailApiData.difficultyCount =
          response.data.recipes[0].difficultyCount;
        this.detailApiData.easyCount = response.data.recipes[0].easyCount;
        this.detailApiData.grade = _.sample(this.numList);
        this.detailApiData.cookingTime = _.sample(this.timeList);
        this.detailApiData.thumbailImage =
          response.data.recipes[0].attFileNoMain;
        this.detailApiData.steps = response.data.recipes[0].manuals;
        this.detailApiData.tags = response.data.recipes[0].tags;
        this.detailApiData.comments = response.data.recipes[0].comments;
        // console.log(this.detailApiData);
      });
  },
  data() {
    return {
      easy: true,
      numList: [1, 2, 3, 4, 5],
      timeList: [1.5, 1, 0.5, 2],
      detailApiData: {
        boardId: "",
        title: "",
        nickname: "",
        ingredients: "",
        content: "",
        createAt: "2020-08-17",
        grade: "",
        cookingTime: "",
        thumbailImage: null,
        steps: [],
        tags: [],
        comments: [],
        difficultyCount: 0,
        easyCount: 0,
      },
    };
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
</style>