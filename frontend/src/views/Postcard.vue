<template>
  <main class="p-5 h-100 w-100">
    <figure class="mt-4">
      <img alt="main-banner" class="img-fluid" src="@/assets/postcard_banner.png">
    </figure>
    <div class="mt-4 fs-1 f-bold" @click="inputCode">
      <button class="btn btn-danger w-50">전하기 💌</button>
    </div>
  </main>
</template>

<script>
import axios from 'axios';
import Swal from 'sweetalert2';

export default {
  name: 'Postcard',
  data() {
    return {}
  },
  created() {
    document.body.style.backgroundColor = '#FFFFFF';
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
                sessionStorage.setItem('paper', JSON.stringify(response.data.result));
                this.$router.push({name: 'postcard-write'});
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
  },
}
</script>