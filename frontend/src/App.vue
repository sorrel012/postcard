<template>
  <div id="app">
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
                <router-link class="nav-link" to="/check" active-class="active">도화지 보기</router-link>
              </li>
              <li class="nav-item">
                <router-link class="nav-link ms-4" to="/write" active-class="active">마음 전하기</router-link>
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
    <router-view />
  </div>
</template>

<script>
import Swal from 'sweetalert2';

export default {
  name: 'App',
  data() {
    return {
      isMember: false,
    }
  },
  created() {
    if(sessionStorage.getItem('id') != null) {
      this.isMember = true;
    };
  },
  methods: {
    logout() {
      Swal.fire({
        icon: 'success',
        title: sessionStorage.getItem('name') + '님 안녕히 가세요',
        timer: 2000,
      }).then(() => {
        sessionStorage.removeItem('id');
        sessionStorage.removeItem('pw');
        sessionStorage.removeItem('name');
        location.href = '/';
      });
    }
  }
}

</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}
a:hover {
  cursor: pointer;
}
</style>
