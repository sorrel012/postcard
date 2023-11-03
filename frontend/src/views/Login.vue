<template>
  <section class="container-md d-flex justify-content-center p-5 mt-4 mb-4 w-75">
    <div class="col-sm-12 col-md-12 col-lg-5 d-flex align-items-center rounded-end me-5 w-75 h-100 p-3">
      <div class="container-md d-flex flex-column align-items-center">
        <header>
          <h1 class="h1 text-center fw-bold title mt-3">로그인</h1>
          <p class="fs-6 text-center mb-5">로그인 후 다양한 서비스를 이용하세요.</p>
        </header>
        <form class="w-50" @submit.prevent="login">
          <div class="form-floating mb-3 ">
            <input id="id" v-model="account.id" class="form-control" placeholder="name@example.com"
                   type="text">
            <label for="id">아이디</label>
          </div>
          <div class="form-floating">
            <input id="pw" v-model="account.pw" class="form-control" placeholder="Password"
                   type="password">
            <label for="pw">패스워드</label>
          </div>

          <div class="container-fluid p-0 mt-2">
            <button id="btnlogin" class="btn btn-primary mt-4 w-100" type="submit">로그인</button>
          </div>
        </form>
        <div class="mt-4">
          <p class="fs-6 text-center">
            회원이 아니신가요?
            <router-link to="/register">회원가입</router-link>
          </p>
        </div>

        <sns-login></sns-login>
      </div>
    </div>
  </section>
</template>

<script charset="utf-8" src="https://static.nid.naver.com/js/naverLogin_implicit-1.0.3.js" type="text/javascript"/>
<script>

import axios from 'axios';
import Swal from 'sweetalert2';
import SnsLogin from "@/components/SnsLogin.vue";

export default {
  name: 'Login',
  data() {
    return {
      account: {
        id: '',
        pw: ''
      },
    }
  },
  components: {
    SnsLogin
  },
  methods: {
    login() {
      axios.post(this.$store.state.url + 'login', this.account)
          .then(response => {
            if (response.data.state) {
              Swal.fire({
                icon: 'success',
                title: response.data.message,
                timer: 2000,
              }).then(() => {
                sessionStorage.setItem('id', this.account.id);
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
              title: '아이디 비밀번호가 일치하지 않습니다',
            });
          })
    },
  }
}
</script>

<style scoped>
section {
  border: 1px solid #e3e3e3;
  border-radius: 10px;
}
</style>