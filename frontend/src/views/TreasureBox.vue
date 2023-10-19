<template>

  <section class="container-md mt-5 mb-5 pt-5">
    <div class="d-flex justify-content-between">
      <div class="headline text-start">
        <h2 class="fw-bold">보물상자🎁</h2>
      </div>
      <div class="btn-group mb-3">
        <input type="text" class="form-control" placeholder="검색어를 입력해주세요.">
        <button type="button" class="btn btn-primary text-nowrap" @click="search()"><font-awesome-icon :icon="['fas', 'magnifying-glass']" beat style="color: #ffffff;" /></button>
      </div>
    </div>

    <div class="side-headline mt-4 mb-3">
      <div class="d-flex justify-content-between align-items-end">
        <p class="mb-0">총 <span class="fw-bold">3</span>건</p>
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
    </div>
    <div class="text-end mt-4">
      <button type="button" class="btn btn-primary" @click="write"><font-awesome-icon :icon="['fas', 'pen-to-square']" style="color: #ffffff;" /> 글쓰기</button>
    </div>

<!--    페이징-->
<!--    <div class="d-flex justify-content-center mt-4">-->
<!--      <div id="pagination-buttons">-->
<!--        <button class="btn w-10" id="previous-button">&lt;&lt;</button>-->
<!--      </div>-->
<!--    </div>-->

<!--    검색 -->
<!--    <c:if test="${count == 0}">-->

<!--      <hr>-->
<!--      <div>-->
<!--        <div class="text-center my-5 fs-2 fw-bold">검색 결과가 존재하지 않습니다.</div>-->
<!--        <div class="w-100 d-flex justify-content-center" >-->
<!--          <button type="button" class="btn btn-light btn-lg mb-5 text-dark" style="background-color : #ededed;" onclick="location.href='/listenlist.do'">전체 리스트 보기</button>-->
<!--        </div>-->


<!--      </div>-->
<!--    </c:if>-->

  </section>

</template>

<script>
import Swal from "sweetalert2";
import axios from "axios";
export default {
  name:'TreasureBox',
  data() {
    return {
      isMember: false,
      selectedOption: 1,
      postList: []
    }
  },
  async created() {
    document.body.style.backgroundColor = '#FFFFFF';

    const id = sessionStorage.getItem('id');
    if(id == null || id == '') {
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
    this.getPosts(this.selectedOption);
  },
  methods: {
    write() {
      location.href='/treasure-write'
    },
    //게시글 목록 받아오기
    getPosts() {
      axios.get(this.$store.state.url + 'postlist', {params: {selectedOption: this.selectedOption}})
          .then(response => {
            this.postList = response.data.result
          })
          .catch(error => {
            console.log(error);
          })
    },
    //상세페이지로 이동
    postDetail(post) {
      this.$store.commit('setPostDetail', post)
      this.$router.push({ name: 'treasure-detail' })
    }
  }
}
</script>
<style>
.truncLayout {
  table-layout: fixed;
}
.hover {
  cursor: pointer;
}
</style>