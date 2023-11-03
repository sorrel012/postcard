<template>
  <nav class="d-flex flex-column align-items-start mt-4 col-3 container">
    <h2 id="userinfo-heading" class="mb-1 f-bold">회원정보</h2>
    <ul aria-labelledby="userinfo-heading" class="list-unstyled mb-2">
      <li class="nav-item pb-1">
        <router-link active-class="active" class="text-decoration-none text-secondary" to="/myprofile">프로필
        </router-link>
      </li>
    </ul>
    <h2 id="myposts-heading" class="mt-3 mb-1 f-bold">내가 쓴 글</h2>
    <ul aria-labelledby="myposts-heading" class="list-unstyled mb-2 text-start">
      <li class="nav-item pb-1">
        <router-link active-class="active" class="text-decoration-none text-secondary" to="/mypaperlist">도화지
        </router-link>
      </li>
      <li class="nav-item pb-1">
        <router-link active-class="active" class="text-decoration-none text-secondary" to="/mypostlist">보물상자
        </router-link>
      </li>
    </ul>
    <h2 id="mycomments-heading" aria-labelledby="mycomments-heading" class="mt-3 mb-1 f-bold">내가 쓴 댓글</h2>
    <ul class="list-unstyled mb-2">
      <li class="nav-item pb-1">
        <router-link active-class="active" class="text-decoration-none text-secondary" to="/mycommentlist">보물상자
        </router-link>
      </li>
    </ul>
  </nav>
</template>

<script>
export default {
  name: 'MyPageSidebar'
}
</script>

<style>
h2 {
  font-size: large;
}
</style>