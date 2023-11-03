<template>
  <main class="p-5 h-100 w-100">
    <header>
      <h1 class="mt-4 mb-5">{{ paper.title }}</h1>
    </header>

    <section class="row w-100 m-0 align-items-baseline">
      <!-- 기존의 쪽지들 -->
      <section class="d-flex flex-wrap justify-content-start">
        <div v-for="postcard in postcards" class="p-3 fs-4 mt-4 mb-4 col-12 col-sm-4 col-xl-2">
          <article :style="{backgroundColor: paper.pcColor, outline: `${paper.pcBorderPx} solid ${paper.pcbColor}`,
        borderRadius: paper.pcbRadiusPx, boxShadow: `4px 4px 1px 3px ${paper.pcbColor}`, color: postcard.textColor}"
                   class="pt-3 pb-3 ps-2 pe-2">
            {{ postcard.content }}
          </article>
        </div>
      </section>
      <!-- 새로운 쪽지-->
      <section class="p-3 fs-4 m-2 ms-0 me-0">
        <form class="d-flex align-items-start" @submit.prevent="regPostcard">
          <textarea v-model="postcard.content" :class="{'d-none': !isNew}"
                    :style="{backgroundColor: paper.pcColor, outline: `${paper.pcBorderPx} solid ${paper.pcbColor}`,borderRadius: paper.pcbRadiusPx, boxShadow: `4px 4px 1px 3px ${paper.pcbColor}`, color:postcard.textColor, maxWidth: '210px'}"
                    class="p-3 fs-4 col-12 col-sm w-75">
          </textarea>
          <div class="ms-3 d-flex flex-column align-items-center">
            <input v-model="postcard.textColor" :class="{'d-none':!isNew}"
                   class="form-control form-control-color mb-2"
                   style="height:50px"
                   type="color">
            <button v-if="isNew" class="btn btn-success">✔
            </button>
          </div>
        </form>
      </section>
    </section>

    <div class="mt-3">
      <button class="btn btn-danger" @click="newPostcard">+</button>
    </div>
  </main>
</template>

<script>
import axios from 'axios';
import Swal from 'sweetalert2';

export default {
  name: 'Postcard',
  data() {
    return {
      paper: {},
      postcards: [],
      isNew: false,
      postcard: {
        content: '',
        textColor: '#000000',
        pcc_seq: ''
      },
    }
  },
  created() {
    const storedPaper = sessionStorage.getItem('paper');
    if (storedPaper) {
      this.paper = JSON.parse(storedPaper);
    }
    document.body.style.backgroundColor = this.paper.bgColor;
    this.postcard.pcc_seq = this.paper.pcc_seq;
    this.getPostcards();
  },
  methods: {
    newPostcard() {
      if (this.isNew) {
        Swal.fire({
          icon: 'error',
          title: '변경사항을 저장해 주세요',
        });
      }
      this.isNew = true;
    },
    async regPostcard() {
      //공백 제거
      this.postcard.content = this.postcard.content.trim();

      //값이 있는지 검사
      if (this.postcard.content == null || this.postcard.content === '') {
        Swal.fire({
          icon: 'error',
          title: '내용을 입력해 주세요',
        });
        return;
      }

      await axios.post(this.$store.state.url + 'write', this.postcard)
          .then(result => {
            if (result.data.state) {
              Swal.fire({
                icon: 'success',
                title: result.data.message,
              });

              this.postcard.content = '';
              this.postcard.textColor = '#000000';

            } else {
              Swal.fire({
                icon: 'error',
                title: result.data.message,
                text: '다시 시도해 주세요.'
              });
            }

          })
          .catch(error => {
            console.log(error);
          })

      this.isNew = false;
      this.getPostcards();
    },
    getPostcards() {
      axios.get(this.$store.state.url + 'postcardlist', {params: {pcc_seq: this.paper.pcc_seq}})
          .then(response => {
            this.postcards = response.data.result;
          })
          .catch(error => {
            console.log(error);
          })
    }
  },
}
</script>

<style scoped>
textarea {
  border: none;
  resize: none;
}
</style>