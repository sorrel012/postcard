<template>
  <div class="p-5 h-100">
    <h1 class="mt-4 mb-5">{{ paper.title }}</h1>

    <div class="container d-flex">
      <div class="p-3 fs-3 m-5 w-25" v-for="postcard in postcards" :key="postcard.pc_seq"
          :style="{backgroundColor: paper.pcColor, outline: `${paper.pcBorderPx} solid ${paper.pcbColor}`,
                    borderRadius: paper.pcbRadiusPx, boxShadow: `4px 4px 1px 3px ${paper.pcbColor}`, color: postcard.textColor}">
          {{ postcard.content }}
      </div>
    </div>

  </div>
</template>

<script>
import axios from 'axios';

export default {
  name: 'MyPaper',
  data() {
    return {
      paper: {},
      postcards: [],
    }
  },
  created() {
    this.paper = this.$store.state.paper;
    console.log(this.paper);
    document.body.style.backgroundColor = this.paper.bgColor;

    axios.get(this.$store.state.url + 'postcardlist', {params: {pcc_seq: this.paper.pcc_seq}})
          .then(response => {
            console.log(response);
            this.postcards = response.data.result;
          })
          .catch(error => {
            console.log(error);
          })
  },
}
</script>

<style scoped>
</style>