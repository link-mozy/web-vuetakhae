import Vue from "vue";
import App from "./App.vue";
import router from "./router";
// 새로 추가한 것
// firebase
import firebase from "firebase";
// BootstrapVue + BootstrapVueIcons
import { BootstrapVue, BootstrapVueIcons } from "bootstrap-vue";
import "bootstrap/dist/css/bootstrap.min.css";
import "bootstrap-vue/dist/bootstrap-vue.css";
// fontawesome
import { library } from "@fortawesome/fontawesome-svg-core";
import { faUserSecret } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/vue-fontawesome";

// Cookies
import VueCookies from "vue-cookies";
// vuetify
import vuetify from "./plugins/vuetify";
Vue.use(VueCookies);
library.add(faUserSecret);

Vue.component("font-awesome-icon", FontAwesomeIcon);

Vue.use(BootstrapVue);
Vue.use(BootstrapVueIcons);

Vue.config.productionTip = false;

firebase.initializeApp({
  apiKey: "AIzaSyC6FZ6OLcBQrFBB-572PeWyeNLUMi0_J8U",
  authDomain: "vue-upload-1951e.firebaseapp.com",
  databaseURL: "https://vue-upload-1951e.firebaseio.com",
  projectId: "vue-upload-1951e",
  storageBucket: "vue-upload-1951e.appspot.com",
  messagingSenderId: "230308073226",
  appId: "1:230308073226:web:246ad47b46cc5d286d34f3",
  measurementId: "G-ZRNR87EZ41",
});

new Vue({
  el: "#app",
  router,
  vuetify,
  render: (h) => h(App),
}).$mount("#app");
