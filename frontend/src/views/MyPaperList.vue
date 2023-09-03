<template>
  <div :class="{'d-none':!isMember, 'd-lg-flex':isMember}" class="container-fluid row align-items-sm-start p-5 pt-4 p-height mt-5" >

    <div class="col-sm-9 col-md-9 col-lg-12 container-lg">
      <div class="container-lg mb-4 p-0">
        <div
            class="container-lg text-start border-bottom border-2 border-dark d-flex">
          <div class="text-start">
            <h3 class="h3 pb-2 m-0 fw-bold">도화지 목록</h3>
          </div>
        </div>
      </div>
      <table class="container-lg table table-hover mt-5">
        <thead class="table-light">
        <tr class="text-center">
          <th class="col-4 text-wrap">제목</th>
          <th class="col-2 text-wrap">고유번호</th>
          <th class="col-2 text-wrap">생성 날짜</th>
          <th class="col-1 text-wrap">쪽지 수</th>
        </tr>
        </thead>
        <tbody>
<!--        <c:forEach items="${list}" var="dto">-->
          <tr class="text-center" v-for="paper in paperlist" :key="paper.m_seq">
            <td class="col-4 text-wrap">
              <div class="text-dark text-wrap text-decoration-none text-hover" @click="mypaper(paper)">{{ paper.title }}</div>
            </td>
            <td class="col-2 text-wrap">{{ paper.code }}</td>
            <td class="col-2 text-wrap">{{ paper.regdate }}</td>
            <td class="col-1 text-wrap">{{ paper.pcCnt }}</td>
          </tr>
<!--        </c:forEach>-->
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
import axios from "axios";
import Swal from "sweetalert2";

export default {
  name: 'MyPaper',
  data() {
    return {
      paperlist: [],
      isMember: false,
    }
  },
  async created() {
    
    console.log(this.isMember);
    const id = sessionStorage.getItem('id');
    console.log(id);
    if(id == null || id == '') {
      await Swal.fire({
        icon: 'error',
        title: '로그인 후 이용하실 수 있습니다.',
      })
      location.href='/login';
      return
    } else {
      this.isMember = true;
    }

    document.body.style.backgroundColor = 'rgb(255, 255, 255)';
    axios.get(this.$store.state.url + 'mypaperlist', { params: {m_seq: sessionStorage.getItem('no')} })
        .then(response => {
          console.log(response);
          this.paperlist = response.data.result;
          console.log(this.paperlist);
        })
        .catch(error => {
          console.log(error);
        })
  },
  mounted() {
    document.body.style.backgroundColor = '#fffff';
  },
  methods: {
    mypaper(paper) {
      this.$store.commit('setPaper', paper);
      this.$router.push({name: 'mypaper'});
    }
  }
}

</script>

<style scoped>

</style>