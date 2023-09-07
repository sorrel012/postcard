<template>
    <div class="col-3 container">
      <nav class="nav d-flex flex-column align-items-start mt-4">
        <div class="mb-1 f-bold">회원정보</div>
        <ul class="list-unstyled mb-2">
          <li class="nav-item pb-1">
            <router-link class="text-decoration-none text-secondary" to="/myprofile" active-class="active">프로필</router-link>
          </li>
        </ul>
        <div class="mt-3 mb-1 f-bold">내가 쓴 글</div>
        <ul class="list-unstyled mb-2">
          <li class="nav-item pb-1">
            <router-link class="text-decoration-none text-secondary" to="/mypr" active-class="active">도화지</router-link>
          </li>
          <li class="nav-item pb-1">
            <router-link class="text-decoration-none text-secondary" to="/myp" active-class="active">게시판</router-link>
          </li>
        </ul>
        <div class="mt-3 mb-1 f-bold">내가 쓴 댓글</div>
        <ul class="list-unstyled mb-2">
          <li class="nav-item pb-1">
            <router-link class="text-decoration-none text-secondary" to="/mypre" active-class="active">게시판</router-link>
          </li>
        </ul>
      </nav>
    </div>
</template>

<script>
export default {
  name: 'MyPageSidebar'
}
</script>

<style>

</style>