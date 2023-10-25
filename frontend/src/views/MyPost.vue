<template>
  <div v-if="isMember" class="container-fluid row d-lg-flex align-items-sm-start p-5 pt-4" >

    <my-page-navbar/>
    <my-page-sidebar/>

    <div class="col-9 container-lg">
      <div class="container-lg mb-4 p-0">
        <div class="container-lg text-start border-bottom border-2 border-dark d-flex">
          <div class="text-start">
            <h3 class="h3 pb-2 m-0 fw-bold">게시글 목록</h3>
          </div>
        </div>
      </div>
      <table class="container-lg table table-hover mt-5">
        <thead class="table-light">
        <tr class="text-center">
          <th class="col-1 text-wrap">번호</th>
          <th class="col-6 text-wrap">제목</th>
          <th class="col-2 text-wrap">작성일</th>
          <th class="col-1 text-wrap">댓글수</th>
          <th class="col-1 text-wrap">조회수</th>
          <th class="col-1 text-wrap">삭제</th>
        </tr>
        </thead>
        <tbody>
        <tr class="text-center align-middle" v-for="post in postlist">
          <td class="col-1 text-wrap">{{ post.b_seq }}</td>
          <td class="col-6 text-wrap">
            <div class="text-dark text-wrap text-decoration-none text-hover" @click="mypost(post)">{{ post.title }}</div>
          </td>
          <td class="col-2 text-wrap">{{ post.regdate }}</td>
          <td class="col-1 text-wrap">{{ post.commentCnt }}</td>
          <td class="col-1 text-wrap">{{ post.hit }}</td>
          <td class="col-1 text-wrap">
            <button type="button" class="btn btn-danger btn-sm" @click="deletePost(post)">삭제</button>
          </td>
        </tr>
        </tbody>
      </table>

      <!--      <div class="d-flex justify-content-center mt-4 l-matop">-->
      <!--        <div id="pagination-buttons">-->
      <!--          <button class="btn w-10" id="previous-button">&lt;&lt;</button>-->
      <!--        </div>-->
      <!--      </div>-->

    </div>

  </div>

</template>

<script>

import MyPageSidebar from "@/components/MypageSidebar.vue";
import MyPageNavbar from "@/components/MyPageNavbar.vue";
import Swal from "sweetalert2";
import axios from "axios";

export default {
  name: 'MyPost',
  data() {
    return {
      postlist: [],
      isMember: false,
    }
  },
  components: {
    MyPageNavbar,
    MyPageSidebar
  },
  async created() {
    const id = sessionStorage.getItem('id');
    if (id == null || id == '') {
      await Swal.fire({
        icon: 'error',
        title: '로그인 후 이용하실 수 있습니다',
      })
      location.href = '/login';
      return
    } else {
      this.isMember = true;
    }

    document.body.style.backgroundColor = 'rgb(255, 255, 255)';
    this.getPosts();
  },
  mounted() {
    document.body.style.backgroundColor = '#fffff';
  },
  methods: {
    getPosts() {
      axios.get(this.$store.state.url + 'mypostlist', {params: {id: sessionStorage.getItem('id')}})
          .then(response => {
            this.postlist = response.data.result;
          })
          .catch(error => {
            console.log(error);
          })
    },
  }
}

</script>

<style scoped>

</style>