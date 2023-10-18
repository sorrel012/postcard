<template>
  <div class="p-5 h-100" :class="{'d-none':!isMember}">
    <h1 class="mt-4 mb-5">{{ paper.title }}</h1>

    <div class="d-flex flex-wrap justify-content-start">
      <div class="p-3 fs-4 mt-4 mb-4 col-12 col-sm-4 col-xl-2 hover" v-for="postcard in postcards">
        <div class="pt-3 pb-3 ps-2 pe-2" :style="{backgroundColor: paper.pcColor, outline: `${paper.pcBorderPx} solid ${paper.pcbColor}`,
        borderRadius: paper.pcbRadiusPx, boxShadow: `4px 4px 1px 3px ${paper.pcbColor}`, color: postcard.textColor}" @click="deletePostcard(postcard)">
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
        title: '로그인 후 이용하실 수 있습니다',
      })
      location.href='/login';
      return
    } else {
      this.isMember = true;
    }

    this.paper = this.$store.state.paper;
    document.body.style.backgroundColor = this.paper.bgColor;

    this.getPostcards(this.paper);
    this.$store.commit('setPaper', {});
  },
  methods: {
    getPostcards(paper) {
      console.log(paper.pcc_seq);
      axios.get(this.$store.state.url + 'postcardlist', {params: {pcc_seq: paper.pcc_seq}})
          .then(response => {
            this.postcards = response.data.result;
          })
          .catch(error => {
            console.log(error);
          })
    },
    deletePostcard(postcard) {
      Swal.fire({
        title: '삭제하시겠습니까?',
        text: "삭제한 쪽지는 다시 되돌릴 수 없습니다",
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
        confirmButtonText: 'Yes, delete it!',
        preConfirm: () => {
          return axios.delete(this.$store.state.url + 'postcard', {params: {pc_seq : postcard.pc_seq}})
              .then(response => {
                this.getPostcards(this.paper);
              })
              .catch(error => {
                console.log(error);
                Swal.showValidationMessage(
                    '쪽지를 삭제하지 못했습니다'
                )
              })
        }
      }).then((result) => {
        if (result.isConfirmed) {
          Swal.fire(
              '삭제 완료',
              '쪽지를 삭제했습니다',
              'success'
          )
        }
      })
    }
  }
}
</script>

<style scoped>
.hover {
  cursor: pointer;
}
</style>