<template>
  <div class="search-bar">
    <h1 class="search-bar-title">{{ introduceTitle }}</h1>
    <v-row class="search-input m-auto">
      <v-col cols="4" sm="2" style="padding: 0px;">
        <select v-model="searchSelectNum" class="form-control search-slt" label="제목">
          <option selected value="1">제목</option>
          <option value="2">작성자</option>
        </select>
      </v-col>
      <v-col cols="6" class="p-2">
        <input
          class="search-input-text"
          type="text"
          placeholder="관심있는 요리를 검색하세요."
          v-model="searchBar"
          v-on:keyup.enter="searchInput()"
        />
      </v-col>
      <v-col>
        <div class="search-img" @click="searchInput">
          <svg
            width="1em"
            height="1em"
            viewBox="0 0 16 16"
            class="bi bi-search"
            fill="currentColor"
            xmlns="http://www.w3.org/2000/svg"
          >
            <path
              fill-rule="evenodd"
              d="M10.442 10.442a1 1 0 0 1 1.415 0l3.85 3.85a1 1 0 0 1-1.414 1.415l-3.85-3.85a1 1 0 0 1 0-1.415z"
            />
            <path
              fill-rule="evenodd"
              d="M6.5 12a5.5 5.5 0 1 0 0-11 5.5 5.5 0 0 0 0 11zM13 6.5a6.5 6.5 0 1 1-13 0 6.5 6.5 0 0 1 13 0z"
            />
          </svg>
        </div>
      </v-col>
    </v-row>
  </div>
</template>

<script>
import _ from "lodash";

export default {
  name: "SearchHomePost",
  created() {
    this.introduceTitle = _.sample(this.introduceList);
  },
  data() {
    return {
      introduceTitle: "",
      introduceList: [
        "음식으로 못 고치는 병은 의사도 못 고친다.",
        "나를 살게 하는 것은 충분한 음식이지 훌륭한 말이 아니다.",
        "음식에 대한 사랑처럼 진실된 사랑은 없다.",
        "배가 비어 있으면 정신도 빌 수 밖에 없다.",
        "배고픈 자는 음식을 가리지 않는다.",
        "잘못된 음식이란 것은 없다.",
        "식욕 이상으로 진실된 집념은 없다.",
        "요리가 없다면 예술도 지성도 사라질 것이다.",
        "새로운 요리의 발견이 새로운 별의 발견보다 인간을 더욱 행복하게 만든다.",
      ],
      searchBar: "",
      searchSelectNum: 1,
    };
  },
  methods: {
    searchInput() {
      if (this.searchBar.trim()) {
        this.$emit("search-input", this.searchBar, this.searchSelectNum);
        this.searchBar = "";
      }
    },
  },
};
</script>

<style scoped>
.search-bar-title {
  font-family: "MapoGoldenPier";
}
.search-slt {
  background-color: rgb(0, 0, 0, 0);
  border-color: rgb(0, 0, 0, 0);
  height: 48px;
  border-radius: 10px 0 0 10px;
  border-inline-end-color: black;
}
</style>
