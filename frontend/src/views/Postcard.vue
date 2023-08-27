<template>
  <div class="p-5 h-100">
    <h1 class="mt-4 mb-5">{{ paper.title }}</h1>

    <div class="container d-flex">
      <div class="p-3 fs-4 m-4 w-25" v-for="postcard in postcards" :key="postcard.pc_seq"
           :style="{backgroundColor: paper.pcColor, outline: `${paper.pcBorderPx} solid ${paper.pcbColor}`,
                    borderRadius: paper.pcbRadiusPx, boxShadow: `4px 4px 1px 3px ${paper.pcbColor}`, color: postcard.textColor}">
        {{ postcard.content }}
      </div>
      <textarea :class="{'p-3':true, 'fs-4':true, 'm-4':true, 'w-25':true, 'd-none': isNew}" :style="{backgroundColor: paper.pcColor, outline: `${paper.pcBorderPx} solid ${paper.pcbColor}`, borderRadius: paper.pcbRadiusPx, boxShadow: `4px 4px 1px 3px ${paper.pcbColor}`, color:postcard.textColor}">
      </textarea>
      <div :class="{'mt-4':true, 'd-none':isLoading}" @click="regPostcard">
        <input type="color" :class="{'form-control':true, 'form-control-color':true, 'mb-2':true, 'w-100':true, 'd-none':isNew}" style= height:50px v-model="postcard.textColor">
        <button :class="{'btn':true, 'btn-success':true, 'd-none':isNew}" v-model="postcard.content">✔</button>
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
      isLoading: true,
      isNew: true,
      postcard: {
        content: '',
        textColor: '#000000',
      },
    }
  },
  created() {
    document.body.style.backgroundColor = '#FFFFFF';

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

              this.paper = response.data.result[0];

              document.body.style.backgroundColor = this.paper.bgColor;

              axios.get(this.$store.state.url + 'postcardlist', {params: {pcc_seq: this.paper.pcc_seq}})
                  .then(response => {
                    console.log(response);
                    this.postcards = response.data.result;
                  })
                  .catch(error => {
                    console.log(error);
                  })

              return response.data;
            })
            .catch(error => {
              Swal.showValidationMessage(
                  '도화지가 존재하지 않습니다.'
              )
            })
      },
      allowOutsideClick: () => !Swal.isLoading()
    }).then((result) => {
      if (result.isConfirmed) {
        Swal.fire({
          title: '도화지를 불러왔습니다.',
          text: '친구에게 마음을 전해주세요!'
        });
      }
      this.isLoading = false;
    })
  },
  methods: {
    newPostcard() {
      this.isNew = false;
    },
    regPostcard() {

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