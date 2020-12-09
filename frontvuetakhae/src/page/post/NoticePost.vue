<template>
  <div class="notice-bg">
    <div class="container">
      <template>
        <h1 class="m-3 white--text">공지사항</h1>
        <v-expansion-panels focusable>
          <v-expansion-panel v-for="notice in notices" :key="notice.title">
            <v-expansion-panel-header>
              {{
              notice.title
              }}
            </v-expansion-panel-header>
            <v-expansion-panel-content>
              {{
              notice.date
              }}
            </v-expansion-panel-content>
            <v-expansion-panel-content>
              {{
              notice.content
              }}
            </v-expansion-panel-content>
          </v-expansion-panel>
        </v-expansion-panels>
      </template>
      <v-card height="150">
        <v-footer absolute class="font-weight-medium">
          <v-col class="text-center" cols="12">
            {{ new Date().getFullYear() }}
            <strong>냉장고를 vue탁해</strong>
            <v-btn
              class="a_tag_modal ml-4 white--text"
              color="rgb(153, 26, 47)"
              @click="modalShow = !modalShow"
            >만든 사람들</v-btn>
            <b-modal v-model="modalShow" hide-footer hide-header>
              <img
                class="image"
                src="https://user-images.githubusercontent.com/60081201/89375322-63211e80-d728-11ea-966f-f581c5ff13ab.PNG"
                alt
              />
            </b-modal>
            <v-icon class="ml-4">mdi-account-circle</v-icon>
          </v-col>
        </v-footer>
      </v-card>
    </div>
  </div>
</template>

<script>
export default {
  name: "NoticePost",
  methods: {
    switchModal() {
      this.modalShow = !this.modalShow;
    },
  },
  data() {
    return {
      modalShow: false,

      notices: [
        {
          title: "냉장고를 vue탁해 page 업데이트 1.0.5",
          content:
            "dummy데이터로 구성되었던 냉장고를 vue탁해 page를 DB와 연결하였습니다.",
          date: "2020-08-12",
        },
        {
          title: "마이 페이지 이슈가 수정된 1.0.4 버전이 업데이트 되었습니다.",
          content:
            "dummy데이터로 구성되었던 마이 페이지를 DB와 연결하였습니다.",
          date: "2020-08-07",
        },
        {
          title: "데이터 베이스 수정 1.0.3 업데이트 안내",
          content: "데이터 베이스가 수정되었습니다.",
          date: "2020-08-06",
        },
        {
          title: "마이페이지, 나의 냉장고 추가된 1.0.2 업데이트 안내",
          content: "마이페이지와 나의 냉장고 기능이 추가되었습니다.",
          date: "2020-08-05",
        },
        {
          title: "안녕하세요! '냉장고를 vue탁해'가 여러분께 인사드립니다.",
          content:
            "냉장고를 Vue탁해는 사용자가 등록한 재료를 활용하여 맞춤 레시피를 제공하는 웹 블로그입니다.",
          date: "2020-08-03",
        },
      ],
    };
  },
};
</script>

<style>
.notice-bg {
  background-image: url("https://user-images.githubusercontent.com/60081201/89377548-0bd17d00-d72d-11ea-95aa-b144ef9162a5.jpg");
  -webkit-background-size: cover;
  -moz-background-size: cover;
  -o-background-size: cover;
  background-size: cover;
  min-height: 100%;
}
.image {
  width: 100%;
  height: 100%;
}
</style>
