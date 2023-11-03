<template>
  <nav class="p-3 navbar navbar-expand-lg bg-light font-bold container-fluid">
    <a class="navbar-brand ms-3" href="/">롤링페이퍼 🧾</a>

    <button aria-controls="mainNavigation" aria-expanded="false" aria-label="Toggle navigation" class="navbar-toggler"
            data-bs-target="#mainNavigation" data-bs-toggle="collapse" type="button">
      <span class="navbar-toggler-icon"></span>
    </button>
    <div id="mainNavigation" class="collapse navbar-collapse">
      <ul class="navbar-nav">
        <li class="nav-item">
          <router-link active-class="active" class="nav-link me-3" to="/create">도화지 만들기</router-link>
        </li>
        <li class="nav-item">
          <router-link active-class="active" class="nav-link " to="/postcard">마음 전하기</router-link>
        </li>
        <li class="nav-item">
          <router-link active-class="active" class="nav-link ms-4" to="/treasure">보물상자</router-link>
        </li>
      </ul>
    </div>

    <button aria-controls="userNavigation" aria-expanded="false" aria-label="Toggle navigation" class="navbar-toggler"
            data-bs-target="#userNavigation" data-bs-toggle="collapse" type="button">
      <span class="navbar-toggler-icon"></span>
    </button>
    <div id="userNavigation" class="collapse navbar-collapse">
      <ul class="navbar-nav">
        <li v-if="isMember" class="nav-item me-2" @click="logout">
          <a class="nav-link">로그아웃</a>
        </li>
        <li v-if="isMember" class="nav-item me-2">
          <a class="nav-link" href="/myprofile">회원정보</a>
        </li>
        <li v-if="!isMember" class="nav-item me-2">
          <a class="nav-link" href="/login">로그인</a>
        </li>
        <li v-if="!isMember" class="nav-item me-2">
          <a class="nav-link" href="/register">회원가입</a>
        </li>
      </ul>
    </div>
  </nav>
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
    if (sessionStorage.getItem('id') != null) {
      this.isMember = true;
    }
    if (sessionStorage.getItem('social') != null) {
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
        if (this.isSocial) {
          this.socialLogout();
          sessionStorage.removeItem('social');
          sessionStorage.removeItem('access_token');
          sessionStorage.removeItem('socialType');
        }
        location.href = '/';
      });
    },
    socialLogout() {

      const accessToken = JSON.parse(atob(sessionStorage.getItem('access_token')));
      const btnType = sessionStorage.getItem('socialType');

      if (btnType === 'kakao') {
        const url = 'https://kauth.kakao.com/oauth/logout';
        location.href = `${url}?client_id=${this.kakaoLogoutParams.client_id}&logout_redirect_uri=${this.kakaoLogoutParams.logout_redirect_uri}`;
      } else if (btnType === 'naver') {
        this.naverLogoutParams.access_token = accessToken;
        axios.post(this.$store.state.url + 'snslogout', new URLSearchParams(this.naverLogoutParams).toString())
      }

    }
  }
}
</script>

<style>
a:hover {
  cursor: pointer;
}
</style>