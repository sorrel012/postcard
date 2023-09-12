<template>
  <section class="container-md d-flex justify-content-center p-5 mt-4 mb-4 w-75">

    <div class="col-sm-12 col-md-12 col-lg-5 d-flex align-items-center rounded-end me-5 w-75 h-100 p-3">

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
        <div class=" mt-4 mb-2">
          <img src="@/assets/kakao_login.png" alt="kakao" @click="kakaoLogin" class="me-2">
          <img src="@/assets/naver_login.png" alt="naver" @click="naverLogin">
        </div>


      </div>

    </div>
  </section>
</template>

<script type="text/javascript" src="https://static.nid.naver.com/js/naverLogin_implicit-1.0.3.js" charset="utf-8"></script>
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
      },
      kakaoLoginParams: {
        client_id: process.env.VUE_APP_KAKAO_REST_KEY,
        redirect_uri: process.env.VUE_APP_KAKAO_REDIRECT_URI,
        response_type: 'code',
        scope: 'account_email',
      },
      naverLoginParams: {
        client_id: process.env.VUE_APP_NAVER_CLIENT_ID,
        redirect_uri: process.env.VUE_APP_NAVER_REDIRECT_URI,
        response_type: 'code',
        state: '1234',
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
              sessionStorage.setItem('name', response.data.result.name);
              sessionStorage.setItem('no', response.data.result.m_seq);
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
    },
    kakaoLogin() {
      const url = 'https://kauth.kakao.com/oauth/authorize';
      location.href = `${url}?client_id=${this.kakaoLoginParams.client_id}&redirect_uri=${this.kakaoLoginParams.redirect_uri}&response_type=${this.kakaoLoginParams.response_type}`;
    },
    naverLogin() {
      const url = 'https://nid.naver.com/oauth2.0/authorize';
      location.href = `${url}?client_id=${this.naverLoginParams.client_id}&redirect_uri=${this.naverLoginParams.redirect_uri}&response_type=${this.naverLoginParams.response_type}&state=${this.naverLoginParams.state}`;
    },
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
img:hover {
  cursor: pointer;
}
</style>