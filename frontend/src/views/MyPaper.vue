<template>
  <div class="p-5 h-100" :class="{'d-none':!isMember}">
    <h1 class="mt-4 mb-5">{{ paper.title }}</h1>

    <div class="row w-100 m-0 align-items-baseline">
      <!-- 기존의 쪽지들 -->
      <div class="p-3 fs-4 ms-auto me-auto mt-4 mb-4 col-12 col-sm-4 col-xl-2" v-for="postcard in postcards" >
        <div class="pt-3 pb-3 ps-2 pe-2" :style="{backgroundColor: paper.pcColor, outline: `${paper.pcBorderPx} solid ${paper.pcbColor}`,
        borderRadius: paper.pcbRadiusPx, boxShadow: `4px 4px 1px 3px ${paper.pcbColor}`, color: postcard.textColor}">
          {{ postcard.content }}
        </div>
      </div>
    </div>

  </div>
</template>

<script>
import axios from 'axios';
import Swal from "sweetalert2";

export default {
  name: 'MyPaper',
  data() {
    return {
      paper: {},
      postcards: [],
      isMember: false,
    }
  },
  async created() {

    const id = sessionStorage.getItem('id');
    if(id == null || id == '') {
      await Swal.fire({
        icon: 'error',
        title: '로그인 후 이용하실 수 있습니다.',
      })
      location.href='/login';
      return
    } else {
      this.isMember = true;
    }

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