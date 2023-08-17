<template>
  <div id="app">
    <div>
      <nav class="pt-3 pb-3 navbar navbar-expand-lg bg-light">
        <div class="font-bold container-fluid">
          <a class="navbar-brand ms-3" href="/">📧 쪽지 보내기 📧</a>
          <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
          </button>
          <div class="collapse navbar-collapse" id="navbarNav">
            <ul class="navbar-nav">
              <li class="nav-item">
                <a class="nav-link ms-3 me-3" href="/create">도화지 만들기</a>
              </li>
              <li class="nav-item">
                <a class="nav-link ms-4" href="/write">쪽지 쓰기</a>
              </li>
            </ul>
          </div>
          <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav2" aria-controls="navbarNav2" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
          </button>
          <div class="collapse navbar-collapse" id="navbarNav2">
            <ul class="navbar-nav">
              <li class="nav-item me-2">
                <a class="nav-link" href="/login">로그인</a>
              </li>
              <li class="nav-item me-2">
                <a class="nav-link" href="/register">회원가입</a>
              </li>
            </ul>
          </div>
        </div>
      </nav>
    </div>
    <router-view/>
  </div>
</template>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}
</style>
