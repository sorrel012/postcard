<template>
  <div v-if="isMember" class="container-fluid row d-lg-flex align-items-sm-start p-5 pt-4">

    <my-page-navbar/>
    <my-page-sidebar/>

    <main class="col-9 container-lg">
      <header class="mb-4 ps-1 text-start text-gray border-bottom border-2">
        <h1 class="h3 pb-2 m-0 fw-bold">댓글 목록</h1>
      </header>

      <p class="mt-3 text-end">총 {{ commentCnt }}건</p>

      <table class="container-lg table table-hover">
        <thead class="table-light">
        <tr class="text-center">
          <th class="col-2 text-wrap">게시글 번호</th>
          <th class="col-7 text-wrap">내용</th>
          <th class="col-2 text-wrap">작성일</th>
          <th class="col-1 text-wrap">삭제</th>
        </tr>
        </thead>
        <tbody>
        <tr v-for="comment in commentlist" class="text-center align-middle">
          <td class="col-2 text-wrap">{{ comment.b_seq }}</td>
          <td class="col-7 text-wrap text-dark text-decoration-none text-hover" @click="myComment(comment)">
            {{ comment.content }}
          </td>
          <td class="col-2 text-wrap">{{ comment.regdate }}</td>
          <td class="col-1 text-wrap">
            <button class="btn btn-danger btn-sm" type="button" @click="deleteComment(comment)">삭제</button>
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
  name: 'MyPost',
  data() {
    return {
      commentlist: [],     // 댓글 목록
      isMember: false,    // 로그인 여부
      pageNo: 1,          // 현재 페이지 번호
      totalPage: 0,       // 전체 페이지 수
      pageSize: 10,       // 페이지당 댓글 수
      commentCnt: 0,      // 작성한 총 댓글 수
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
    this.getComments();
  },
  mounted() {
    document.body.style.backgroundColor = '#fffff';
  },
  methods: {
    getComments() {
      const config = {
        pageNo: this.pageNo,
        size: this.pageSize,
        id: sessionStorage.getItem('id')
      }
      axios.get(this.$store.state.url + 'mycommentlist', {params: config})
          .then(response => {
            this.commentlist = response.data.result;
            this.commentCnt = this.commentlist.length;
            this.totalPage = Math.ceil(this.commentCnt / this.pageSize);
          })
          .catch(error => {
            console.log(error);
          })
    },
    myComment(comment) {
      this.$router.push({name: 'treasure-detail', query: {commentId: comment.c_seq, seq: comment.b_seq}});
    },
    deleteComment(comment) {
      Swal.fire({
        title: '삭제하시겠습니까?',
        text: "삭제한 댓글은 다시 되돌릴 수 없습니다",
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
        confirmButtonText: 'Delete',
        preConfirm: () => {
          return axios.delete(this.$store.state.url + 'comment', {params: {seq: post.c_seq}})
              .then(response => {
                this.getComments();
              })
              .catch(error => {
                console.log(error);
                Swal.showValidationMessage(
                    '댓글을 삭제하지 못했습니다'
                )
              })
        }
      }).then((result) => {
        if (result.isConfirmed) {
          Swal.fire(
              '삭제 완료',
              '댓글을 삭제했습니다',
              'success'
          )
        }
      })
    },
    prevPage() {
      if (this.pageNo > 1) {
        this.pageNo--;
        this.getComments();
      }
    },
    nextPage() {
      if (this.pageNo < this.totalPage) {
        this.pageNo++;
        this.getComments();
      }
    },
    goToPage(pageNo) {
      this.pageNo = pageNo;
      this.getComments();
    }
  }
}

</script>

<style>
td {
  cursor: pointer;
}
</style>