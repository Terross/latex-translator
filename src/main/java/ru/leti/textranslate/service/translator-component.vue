<template>
  <v-container>
    <v-col>
      <v-file-input
          v-model="files"
          label="Tex file"
      ></v-file-input>
      <v-btn
          color="red"
          @click="upload"
      >
        Отправить файл
      </v-btn>
    </v-col>
  </v-container>

</template>

<script>
export default {
  name: "translator-component",
  data: () => ({
    files: []
  }),
  methods: {
    upload() {
      const file = this.files
      let formData = new FormData()
      formData.append("tex-file", file)


      fetch("http://localhost:8080/tex-translate",
          {
            body: formData,
            method: "POST",
            headers: {
                'Accept': 'multipart/form-data',
                'Content-Type': 'multipart/form-data',
              }
          }).then( response => {
            console.log(response.data)
      }
      );
    }
  }
}
</script>

<style scoped>

</style>