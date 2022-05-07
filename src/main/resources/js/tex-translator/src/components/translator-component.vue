<template>
  <v-container>
    <v-col>
      <v-file-input
          v-model="files"
          label="Tex file"
      ></v-file-input>
      <v-btn
          color="primary"
          @click="upload"
      >
        Отправить файл
      </v-btn>
      <v-progress-circular
          v-if="loading"
          :size="50"
          color="primary"
          indeterminate
      ></v-progress-circular>
    </v-col>
  </v-container>

</template>

<script>
import {HTTP} from "@/http-commons";
import {saveAs} from 'file-saver'

export default {
  name: "translator-component",
  data: () => ({
    files: [],
    loading: false
  }),
  methods: {
    upload() {
      this.loading = true
      const file = this.files
      let formData = new FormData()
      formData.append("tex-file", file)

      const config = { headers: {'Content-Type': 'multipart/form-data'} };

      HTTP.post( "tex-translate", formData, config )
          .then( response => {
            const blob = new Blob([response.data], {type: "text/plain;charset=utf-8"})
            saveAs(blob, "result.tex")
            this.loading = false
          })


    }
  }
}
</script>

<style scoped>

</style>