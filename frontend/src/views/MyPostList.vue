<template>
  <div v-if="isMember" class="container-fluid row d-lg-flex align-items-sm-start p-5 pt-4">

    <my-page-navbar/>
    <my-page-sidebar/>

    <main class="col-9 container-lg">
      <header class="mb-4 ps-1 text-start text-gray border-bottom border-2">
        <h1 class="h3 pb-2 m-0 fw-bold">게시글 목록</h1>
      </header>

      <p class="mt-3 text-end">총 {{ postCnt }}건</p>

      <table class="container-lg table table-hover">
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
        <tr v-for="post in postlist" class="text-center align-middle">
          <td class="col-1 text-wrap">{{ post.b_seq }}</td>
          <td class="col-6 text-wrap text-dark text-decoration-none text-hover" @click="myPost(post)">{{
              post.title
            }}
          </td>
          <td class="col-2 text-wrap">{{ post.regdate }}</td>
          <td class="col-1 text-wrap">{{ post.commentCnt }}</td>
          <td class="col-1 text-wrap">{{ post.hit }}</td>
          <td class="col-1 text-wrap">
            <button class="btn btn-danger btn-sm" type="button" @click="deletePost(post)">삭제</button>
          </td>
        </tr>
        </tbody>
      </table>

      <nav aria-label="Page Navigation" class="mt-4">
        <ul class="pagination justify-content-center">
          <li :class="{ 'disabled': pageNo <= 1 }" class="page-item">
            <a aria-label="Previous" class="page-link" href="#" @click.prevent="prevPage">
              <span aria-hidden="true">&laquo;</span>
            </a>
          </li>
          <li v-for="page in totalPage" :key="page" :class="{ 'active': pageNo === page }" class="page-item">
            <a class="page-link" href="#" @click.prevent="goToPage(page)">{{ page }}</a>
          </li>
          <li :class="{ 'disabled': pageNo >= totalPage }" class="page-item">
            <a aria-label="Next" class="page-link" href="#" @click.prevent="nextPage">
              <span aria-hidden="true">&raquo;</span>
            </a>
          </li>
        </ul>
      </nav>

    </main>
  </div>

</template>

<script>

import MyPageSidebar from "@/components/MypageSidebar.vue";
import MyPageNavbar from "@/components/MyPageNavbar.vue";
import Swal from "sweetalert2";
import axios from "axios";

export default {
  name: 'MyPostList',
  data() {
    return {
      postlist: [],       // 게시글 목록
      isMember: false,    // 로그인 여부
      pageNo: 1,          // 현재 페이지 번호
      totalPage: 0,       // 전체 페이지 수
      pageSize: 10,       // 페이지당 게시물 수
      postCnt: 0,         // 작성한 총 게시물 수
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
      const config = {
        pageNo: this.pageNo,
        size: this.pageSize,
        id: sessionStorage.getItem('id')
      }
      axios.get(this.$store.state.url + 'mypostlist', {params: config})
          .then(response => {
            this.postlist = response.data.result;
            this.postCnt = this.postlist.length;
            this.totalPage = Math.ceil(this.postCnt / this.pageSize);
          })
          .catch(error => {
            console.log(error);
          })
    },
    myPost(post) {
      this.$router.push({name: 'treasure-detail', query: {seq: post.b_seq}})
    },
    deletePost(post) {
      Swal.fire({
        title: '삭제하시겠습니까?',
        text: "삭제한 게시글은 다시 되돌릴 수 없습니다",
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
        confirmButtonText: 'Delete',
        preConfirm: () => {
          return axios.delete(this.$store.state.url + 'post', {params: {seq: post.b_seq}})
              .then(response => {
                this.getPosts();
              })
              .catch(error => {
                console.log(error);
                Swal.showValidationMessage(
                    '게시글을 삭제하지 못했습니다'
                )
              })
        }
      }).then((result) => {
        if (result.isConfirmed) {
          Swal.fire(
              '삭제 완료',
              '게시글을 삭제했습니다',
              'success'
          )
        }
      })
    },
    prevPage() {
      if (this.pageNo > 1) {
        this.pageNo--;
        this.getPosts();
      }
    },
    nextPage() {
      if (this.pageNo < this.totalPage) {
        this.pageNo++;
        this.getPosts();
      }
    },
    goToPage(pageNo) {
      this.pageNo = pageNo;
      this.getPosts();
    }
  }
}

</script>

<style>
td {
  cursor: pointer;
}
</style>