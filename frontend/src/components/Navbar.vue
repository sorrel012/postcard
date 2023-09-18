<template>
  <div>
    <nav class="pt-3 pb-3 navbar navbar-expand-lg bg-light">
      <div class="font-bold container-fluid">
        <a class="navbar-brand ms-3" href="/">롤링페이퍼 🧾</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav"
                aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
          <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNav">
          <ul class="navbar-nav">
            <li class="nav-item">
              <router-link class="nav-link me-3" to="/create" active-class="active">도화지 만들기</router-link>
            </li>
            <li class="nav-item">
              <router-link class="nav-link " to="/write" active-class="active">마음 전하기</router-link>
            </li>

            <li class="nav-item">
              <router-link class="nav-link ms-4" to="/board" active-class="active">게시판</router-link>
            </li>
          </ul>
        </div>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav2"
                aria-controls="navbarNav2" aria-expanded="false" aria-label="Toggle navigation">
          <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNav2">
          <ul class="navbar-nav">
            <li class="nav-item me-2" v-if="isMember" @click="logout">
              <a class="nav-link">로그아웃</a>
            </li>
            <li class="nav-item me-2" v-if="isMember">
              <a class="nav-link" href="/myprofile">회원정보</a>
            </li>
            <li class="nav-item me-2" v-if="!isMember">
              <a class="nav-link" href="/login">로그인</a>
            </li>
            <li class="nav-item me-2" v-if="!isMember">
              <a class="nav-link" href="/register">회원가입</a>
            </li>
          </ul>
        </div>
      </div>
    </nav>
  </div>
</template>

<script>
import Swal from 'sweetalert2';
import axios from "axios";

export default {
  name: 'Navbar',
  data() {
    return {
      isMember: false,
      isSocial: false,
      kakaoLogoutParams: {
        client_id: process.env.VUE_APP_KAKAO_REST_KEY,
        logout_redirect_uri: process.env.VUE_APP_SNS_LOGOUT_REDIRECT_URI,
      },
      naverLogoutParams: {
        client_id: process.env.VUE_APP_NAVER_CLIENT_ID,
        client_secret: process.env.VUE_APP_NAVER_CLIENT_SECRET_ID,
        grant_type: 'delete',
        access_token: '',
        service_provider: 'NAVER',
      },
    }
  },
  created() {
    if(sessionStorage.getItem('id') != null) {
      this.isMember = true;
    }
    if(sessionStorage.getItem('social') != null) {
      this.isSocial = true;
    }
  },
  methods: {
    logout() {
      Swal.fire({
        icon: 'success',
        title: sessionStorage.getItem('name') + '님 안녕히 가세요',
        timer: 2000,
      }).then(async () => {
        sessionStorage.removeItem('id');
        sessionStorage.removeItem('name');
        sessionStorage.removeItem('no');
        if(this.isSocial) {
          await this.socialLogout();
          sessionStorage.removeItem('social');
          sessionStorage.removeItem('access_token');
          sessionStorage.removeItem('socialType');
        }
        //location.href = '/';
      });
    },
    async socialLogout() {

      const accessToken = JSON.parse(atob(sessionStorage.getItem('access_token')));
      const btnType = sessionStorage.getItem('socialType');

      if(btnType === 'kakao') {
        const url = 'https://kauth.kakao.com/oauth/logout';
        location.href = `${url}?client_id=${this.kakaoLogoutParams.client_id}&logout_redirect_uri=${this.kakaoLogoutParams.logout_redirect_uri}`;
      } else {
        // const headers = {
        //     'Authorization': `Bearer ${accessToken}`,
        //     'Content-Type': 'application/x-www-form-urlencoded',
        //     'btnType': btnType,
        // }

        this.naverLogoutParams.access_token = accessToken;

        await axios.post(this.$store.state.url+'snslogout', new URLSearchParams(this.naverLogoutParams).toString())
            .then(response => {
              console.log(response);
            })
        // await axios.post(this.$store.state.url+'snslogout', {}, userConfig)
      }



    }
  }
}
</script>

<style >
a:hover {
  cursor: pointer;
}
</style>