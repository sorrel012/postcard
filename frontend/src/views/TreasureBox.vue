<template>

  <section class="container-md mt-5 mb-5 pt-5">
    <div class="d-flex justify-content-between">
      <div class="headline text-start">
        <h2 class="fw-bold">보물상자🎁</h2>
      </div>
      <div class="btn-group mb-3">
        <select class="form-select w-35 me-1" v-model="searchOption">
          <option value="1">제목</option>
          <option value="2">내용</option>
          <option value="3">제목+내용</option>
          <option value="4">작성자</option>
        </select>
        <input type="text" class="form-control" placeholder="검색어를 입력해주세요." v-model="searchKeyword">
        <button type="button" class="btn btn-primary text-nowrap" @click="getPosts()"><font-awesome-icon :icon="['fas', 'magnifying-glass']" beat style="color: #ffffff;" /></button>
      </div>
    </div>

    <div class="side-headline mt-4 mb-3">
      <div class="d-flex justify-content-between align-items-end">
        <p class="mb-0">총 <span class="fw-bold">{{ postCnt }}</span>건</p>
        <div>
          <select class="form-select w-100" v-model="selectedOption" @change="getPosts">
            <option value="1">최신순</option>
            <option value="2">오래된순</option>
          </select>
        </div>
      </div>
    </div>

    <div>
      <table class="container-lg table table-hover mt-1 truncLayout">
        <thead class="table-light">
        <tr class="text-center">
          <th class="col-1 text-wrap">번호</th>
          <th class="col-6 text-wrap">제목</th>
          <th class="col-2 text-wrap">작성자</th>
          <th class="col-2 text-wrap">작성일</th>
          <th class="col-1 text-wrap">조회수</th>
        </tr>
        </thead>
        <tbody>
          <tr class="text-center align-middle hover" v-for="post in postList" :key="post.b_seq" @click="postDetail(post)">
            <td class="col-1 text-truncate">{{ post.b_seq }}</td>
            <td class="col-5 text-truncate">{{ post.title }}</td>
            <td class="col-2 text-truncate">{{ post.name }}</td>
            <td class="col-2 text-truncate">{{ post.regdate }}</td>
            <td class="col-1 text-truncate">{{ post.hit }}</td>
          </tr>
        </tbody>
      </table>

      <div class="text-center align-middle border-bottom" v-if="postCnt===0 && isSearch">
        <div class="col-12 mt-5 mb-5">검색 결과가 없습니다.</div>
      </div>
    </div>
    <div class="text-end mt-4">
      <button type="button" class="btn btn-primary" @click="write"><font-awesome-icon :icon="['fas', 'pen-to-square']" style="color: #ffffff;" /> 글쓰기</button>
    </div>

    <nav aria-label="Page navigation" class="mt-4">
      <ul class="pagination justify-content-center">
        <li class="page-item" :class="{ 'disabled': pageNo <= 1 }">
          <a class="page-link" href="#" @click.prevent="prevPage" aria-label="Previous">
            <span aria-hidden="true">&laquo;</span>
          </a>
        </li>
        <li v-for="page in totalPage" :key="page" class="page-item" :class="{ 'active': pageNo === page }">
          <a class="page-link" href="#" @click.prevent="goToPage(page)">{{ page }}</a>
        </li>
        <li class="page-item" :class="{ 'disabled': pageNo >= totalPage }">
          <a class="page-link" href="#" @click.prevent="nextPage" aria-label="Next">
            <span aria-hidden="true">&raquo;</span>
          </a>
        </li>
      </ul>
    </nav>

  </section>

</template>

<script>
import Swal from "sweetalert2";
import axios from "axios";
export default {
  name:'TreasureBox',
  data() {
    return {
      isMember: false,    // 회원 여부
      selectedOption: 1,  // 정렬 기준
      postList: [],       // 게시글 목록
      postCnt: 0,         // 게시글 수
      searchOption: 1,    // 검색 기준
      searchKeyword: '',  // 검색어
      isSearch: false,    // 검색여부
      pageNo: 1,          // 현재 페이지 번호
      totalPage: 0,       // 전체 페이지 수
      pageSize: 10,       // 페이지당 게시물 수
    }
  },
  async created() {
    document.body.style.backgroundColor = '#FFFFFF';

    const id = sessionStorage.getItem('id');
    if(id == null || id === '') {
      await Swal.fire({
        icon: 'error',
        title: '로그인 후 이용하실 수 있습니다',
      })
      location.href='/login';
      return
    } else {
      this.isMember = true;
    }

    //게시글 목록 받아오기
    this.getPosts();
  },
  methods: {
    write() {
      location.href='/treasure-write';
      sessionStorage.setItem('postType', 'write');
    },
    getPosts() {  //게시글 목록 받아오기

      if(this.searchKeyword != null || this.searchKeyword !== '') {
        this.isSearch = true;
      }

      const config = {
        searchOption: this.searchOption,
        searchKeyword: this.searchKeyword,
        selectedOption: this.selectedOption,
        pageNo: this.pageNo,
        size: this.pageSize
      }

      axios.get(this.$store.state.url + 'postlist', {params: config})
          .then(response => {
            this.postList = response.data.result;
            this.postCnt = response.data.totalRow;
            this.totalPage = Math.ceil(this.postCnt / this.pageSize);
          })
          .catch(error => {
            console.log(error);
          })
    },
    postDetail(post) {  //상세페이지로 이동
      this.$store.commit('setPostDetail', post)
      this.$router.push({ name: 'treasure-detail' })
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