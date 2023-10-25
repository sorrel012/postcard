<template>
  <div class="p-5 h-100 w-100">

    <div class="mt-4" v-if="!isSuccess">
      <img src="@/assets/postcard_banner.png" class="img-fluid" alt="main-banner">
    </div>

    <div :class="{'mt-4':true, 'fs-1':true, 'f-bold':true, 'd-none': !isLoading}" @click="inputCode">
      <button class="btn btn-danger w-50">전하기 💌</button>
    </div>

    <h1 class="mt-4 mb-5">{{ paper.title }}</h1>

    <div class="row w-100 m-0 align-items-baseline">
      <!-- 기존의 쪽지들 -->
      <div class="d-flex flex-wrap justify-content-start">
        <div class="p-3 fs-4 mt-4 mb-4 col-12 col-sm-4 col-xl-2" v-for="postcard in postcards">
          <div class="pt-3 pb-3 ps-2 pe-2" :style="{backgroundColor: paper.pcColor, outline: `${paper.pcBorderPx} solid ${paper.pcbColor}`,
        borderRadius: paper.pcbRadiusPx, boxShadow: `4px 4px 1px 3px ${paper.pcbColor}`, color: postcard.textColor}">
            {{ postcard.content }}
          </div>
        </div>
      </div>
      <!-- 새로운 쪽지-->
      <div class="p-3 fs-4 m-2 ms-0 me-0">
        <div class="d-flex align-items-start">
            <textarea :class="{'p-3':true, 'fs-4':true, 'col-12':true, 'col-sm':true, 'w-75':true, 'd-none': !isNew}"
                      :style="{backgroundColor: paper.pcColor, outline: `${paper.pcBorderPx} solid ${paper.pcbColor}`,borderRadius: paper.pcbRadiusPx, boxShadow: `4px 4px 1px 3px ${paper.pcbColor}`, color:postcard.textColor, maxWidth: '210px'}"
                      v-model="postcard.content">
            </textarea>
          <div class="ms-3 d-flex flex-column align-items-center">
            <input type="color"
                   :class="{'form-control':true, 'form-control-color':true, 'mb-2':true, 'd-none':!isNew}"
                   style="height:50px"
                   v-model="postcard.textColor">
            <button :class="{'btn':true, 'btn-success':true, 'd-none':!isNew}"
                    @click="regPostcard">✔</button>
          </div>
        </div>
      </div>
    </div>

    <div :class="{'mt-3':true, 'd-none':isLoading}" @click="newPostcard">
      <button class="btn btn-danger">+</button>
    </div>

  </div>
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
      isSuccess: false,
    }
  },
  created() {
    document.body.style.backgroundColor = '#FFFFFF';
    this.$store.commit('setLoadingStatus', true);
  },
  computed: {
    isLoading() {
      return this.$store.state.isLoading;
    }
  },
  methods: {
    inputCode() {
      Swal.fire({
        title: '도화지 번호를 입력해 주세요.',
        input: 'text',
        inputAttributes: {
          autocapitalize: 'off'
        },
        showCancelButton: true,
        confirmButtonText: 'Submit',
        showLoaderOnConfirm: true,
        preConfirm: (code) => {
          return axios.get(this.$store.state.url + 'paper', {params: {code: code}})
              .then(response => {

                this.paper = response.data.result;

                document.body.style.backgroundColor = this.paper.bgColor;
                this.postcard.pcc_seq = this.paper.pcc_seq;

                this.getPostcards();
                console.log(this.isLoading);

                return response.data;
              })
              .catch(error => {
                console.log(error);
                Swal.showValidationMessage(
                    '도화지가 존재하지 않습니다'
                )
              })
        },
        allowOutsideClick: () => !Swal.isLoading()
      }).then((result) => {
        if (result.isConfirmed) {
          this.isSuccess = true;
          Swal.fire({
            title: '도화지를 불러왔습니다',
            text: '친구에게 마음을 전해주세요!'
          });
        }
      })
    },
    newPostcard() {
      if(this.isNew) {
        Swal.fire({
          icon: 'error',
          title: '변경사항을 저장해 주세요',
        });
      }
      this.isNew = true;
    },
    async regPostcard() {
      await axios.post(this.$store.state.url + 'write', this.postcard)
          .then(result => {
            if(result.data.state) {
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
            console.log(response);
            this.postcards = response.data.result;
            this.$store.commit('setLoadingStatus', false);
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