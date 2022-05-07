<template>
  <v-app>
    <translator-component></translator-component>
  </v-app>

</template>

<script>
import translatorComponent from "@/components/translator-component";

export default {
  name: 'App',

  components: {
    translatorComponent
  },

  data: () => ({
    //
  }),
};
</script>
