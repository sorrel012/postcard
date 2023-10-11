<template>
  <section class="container-md mt-5 pt-5">
    <div class="d-flex justify-content-between mb-5">
      <div class="headline text-start border-bottom border-black w-100">
        <h2 class="fw-bold ">글쓰기</h2>
      </div>
    </div>


    <form class="w-100 h-100 mh-100" @submit.prevent="registWriting">

      <input type="text" placeholder="제목을 입력해 주세요" class="form-control mb-4" style="height: 40px" required v-model="title">

      <CkEditor @write="content=$event" @images="images=$event"/>

      <div class="text-end mt-4">
        <button type="submit" class="btn btn-primary"><font-awesome-icon :icon="['fas', 'pen-to-square']" style="color: #ffffff;" /> 등록</button>
      </div>
    </form>

  </section>
</template>

<script>
import Swal from "sweetalert2";
import CkEditor from "@/components/CkEditor.vue";
import axios from "axios";

export default {
  name:'TreasureBoxWrite',
  data() {
    return {
      isMember: false,
      content: '',
      title: '',
      images: [],
    }
  },
  components: {
    CkEditor,
  },
  async created() {
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

  },
  methods: {
    registWriting() {
      console.log(this.content);
      console.log('regist', this.images);
    //
    //   //공백 제거
    //   this.content = this.content.trim();
    //
    //   if(this.content !== null && this.content !== '') {
    //
    //     const formData = new FormData();
    //     formData.append('content', this.content);
    //     formData.append('title', this.title);
    //     formData.append('id', sessionStorage.getItem('id'));
    //
    //     axios.post(this.$store.state.url + 'writing', formData, {
    //           headers: {
    //             'Content-Type': 'multipart/form-data',
    //           },
    //         })
    //         .then(response => {
    //           console.log(response);
    //           if (response.data.state) {
    //             Swal.fire({
    //               icon: 'success',
    //               title: response.data.message,
    //             });
    //             this.$router.push({name: 'treasure'});
    //           } else {
    //             Swal.fire({
    //               icon: 'error',
    //               title: response.data.message,
    //             });
    //           }
    //         })
    //         .catch(error => {
    //           console.log(error);
    //           Swal.fire({
    //             icon: 'error',
    //             title: '게시글 작성에 실패했습니다',
    //           });
    //         });
    //   } else {
    //     Swal.fire({
    //       icon: 'error',
    //       title: '내용을 입력해 주세요',
    //     });
    //   }
    //

    },
    saveHandler() {
      // 서버 저장 요청 로직
    },
  },
}
</script>

<style>
</style>