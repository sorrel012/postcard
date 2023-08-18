<template>
  <section class="container-md d-flex justify-content-center p-5 mt-5 h-75">

    <div class="col-sm-12 col-md-12 col-lg-5 p-0 d-flex align-items-center rounded-end me-5 w-75 h-100 p-5">

      <div class="container-md d-flex flex-column align-items-center">

        <h1 class="h1 text-center fw-bold title mt-3">로그인</h1>
        <p class="fs-6 text-center mb-5">로그인 후 다양한 서비스를 이용하세요.</p>
        <form @submit.prevent="login" class="w-50">
          <div class="form-floating mb-3 ">
            <input type="text" class="form-control" placeholder="name@example.com" name="username" v-model="account.id">
            <label for="floatingInput">아이디</label>
          </div>
          <div class="form-floating">
            <input type="password" class="form-control" placeholder="Password" name="password" v-model="account.pw">
            <label for="floatingPassword">패스워드</label>
          </div>

          <div class="container-fluid p-0 mt-2">
            <button type="submit" class="btn btn-primary mt-4 w-100" id="btnlogin">로그인</button>
          </div>
        </form>
        <div class="mt-4">
          <p class="fs-6 text-center">
            회원이 아니신가요? <router-link to="/register">회원가입</router-link>
          </p>
        </div>

      </div>

    </div>
  </section>
</template>

<script>
import axios from 'axios';
import Swal from 'sweetalert2';

export default {
  name: 'Login',
  data() {
    return {
      account: {
        id: '',
        pw: ''
      }
    }
  },
  methods: {
    login() {
      axios.post(this.$store.state.url + 'login', this.account)
        .then(response => {
          console.log(response);
          if (response.data.state) {
            Swal.fire({
              icon: 'success',
              title: response.data.message,
              timer: 2000,
            }).then(() => {
              sessionStorage.setItem('id', this.account.id);
              sessionStorage.setItem('pw', this.account.pw);
              sessionStorage.setItem('name', response.data.result.name);              
              location.href = '/';
            });            
          } else {
            Swal.fire({
              icon: 'error',
              title: response.data.message,
            });
          }
        })
        .catch(error => {
          console.log(error);
          Swal.fire({
            icon: 'error',
            title: '아이디 비밀번호가 일치하지 않습니다.',
          });
        })
    }
  }
}
</script>

<style scoped>
section {
  border: 1px solid #e3e3e3;
  border-radius: 10px;
}
.mt-5 {
  margin-top: 7rem !important;
}
</style>