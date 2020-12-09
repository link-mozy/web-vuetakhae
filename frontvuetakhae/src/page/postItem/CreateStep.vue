<template>
  <div>
    <h1>유빈 수정중</h1>
    <!-- <v-toolbar dense>
      <v-overflow-btn :items="dropdown_font" label="Select font" hide-details class="pa-0"></v-overflow-btn>

      <template v-if="$vuetify.breakpoint.mdAndUp">
        <v-divider vertical></v-divider>

        <v-overflow-btn
          :items="dropdown_edit"
          editable
          label="Select size"
          hide-details
          class="pa-0"
          overflow
        ></v-overflow-btn>

        <v-divider vertical></v-divider>

        <v-spacer></v-spacer>

        <v-btn-toggle v-model="toggle_multiple" color="primary" dense group multiple>
          <v-btn :value="1" text>
            <v-icon>mdi-format-bold</v-icon>
          </v-btn>

          <v-btn :value="2" text>
            <v-icon>mdi-format-italic</v-icon>
          </v-btn>

          <v-btn :value="3" text>
            <v-icon>mdi-format-underline</v-icon>
          </v-btn>

          <v-btn :value="4" text>
            <v-icon>mdi-format-color-fill</v-icon>
          </v-btn>
        </v-btn-toggle>

        <div class="mx-4"></div>

        <v-btn-toggle v-model="toggle_exclusive" color="primary" dense group>
          <v-btn :value="1" text>
            <v-icon>mdi-format-align-left</v-icon>
          </v-btn>

          <v-btn :value="2" text>
            <v-icon>mdi-format-align-center</v-icon>
          </v-btn>

          <v-btn :value="3" text>
            <v-icon>mdi-format-align-right</v-icon>
          </v-btn>

          <v-btn :value="4" text>
            <v-icon>mdi-format-align-justify</v-icon>
          </v-btn>
        </v-btn-toggle>
      </template>
    </v-toolbar>-->
    <v-text-field
      label="요리하면서 꿀팁이나 소감을 작성해주세요."
      v-model="review"
      :rules="rules"
      hide-details="auto"
    ></v-text-field>
    <!-- <div id="editor">
      <textarea :value="input" @input="update"></textarea>
      <div v-html="compiledMarkdown"></div>
    </div>-->
  </div>
</template>

<!-- 에디터 -->
<script src="https://unpkg.com/marked@0.3.6"></script>
<script src="https://unpkg.com/lodash@4.16.0"></script>

<script>
export default {
  name: "CreateStep",
  data() {
    return {
      review: "",
      input: "# hello",
      dropdown_font: [
        { text: "Arial" },
        { text: "Calibri" },
        { text: "Courier" },
        { text: "Verdana" },
      ],
      dropdown_edit: [
        { text: "100%" },
        { text: "75%" },
        { text: "50%" },
        { text: "25%" },
        { text: "0%" },
      ],
      toggle_exclusive: 2,
      toggle_multiple: [1, 2, 3],
    };
  },
  computed: {
    compiledMarkdown: function() {
      return marked(this.input, { sanitize: true });
    },
  },
  methods: {
    update: _.debounce(function(e) {
      this.input = e.target.value;
    }, 300),
  },
};
</script>

<style scoped>
html,
body,
#editor {
  margin: 0;
  height: 100%;
  font-family: "Helvetica Neue", Arial, sans-serif;
  color: #333;
}

textarea,
#editor div {
  display: inline-block;
  width: 49%;
  height: 100%;
  vertical-align: top;
  box-sizing: border-box;
  padding: 0 20px;
}

textarea {
  border: none;
  border-right: 1px solid #ccc;
  resize: none;
  outline: none;
  background-color: #f6f6f6;
  font-size: 14px;
  font-family: "Monaco", courier, monospace;
  padding: 20px;
}

code {
  color: #f66;
}
</style>
