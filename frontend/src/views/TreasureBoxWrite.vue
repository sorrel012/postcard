<template>
  <main class="container-md mt-5 pt-5">
    <header class="d-flex justify-content-between mb-5 text-start border-bottom border-black w-100">
      <h1 class="fw-bold">글쓰기</h1>
    </header>

    <form class="w-100 h-100 mh-100" @submit.prevent="registPost">
      <input type="text" placeholder="제목을 입력해 주세요" class="form-control mb-4" style="height: 40px" required v-model="title">

      <article>
        <CkEditor :initial-data="content" @write="content=$event" @images="images=$event"/>
      </article>

      <div class="text-end mt-4 mb-4">
        <button type="submit" class="btn btn-primary me-2" v-if="editSeq==undefined"><font-awesome-icon :icon="['fas', 'pen-to-square']" style="color: #ffffff;" /> 등록</button>
        <button type="submit" class="btn btn-primary me-2" v-if="editSeq!=undefined"><font-awesome-icon :icon="['fas', 'pen-to-square']" style="color: #ffffff;" /> 수정</button>
        <button type="button" class="btn btn-border" @click="backToList"><font-awesome-icon :icon="['fas', 'bars']" style="color: black;" /> 목록</button>
      </div>
    </form>
  </main>
</template>

<script>
import Swal from "sweetalert2";
import CkEditor from "@/components/CKEditor.vue";
import axios from "axios";

export default {
  name:'TreasureBoxWrite',
  data() {
    return {
      isMember: false,
      content: '',
      title: '',
      images: [],
      editSeq: 0,
      b_seq: 0,
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

    //수정인지 추가인지 확인하여 수정일 경우 본문 내용 보여주기
    this.editSeq = this.$route.query.seq;
    if(this.editSeq != undefined) {
      axios.get(this.$store.state.url + 'post', {params: {seq: this.editSeq}})
          .then(response => {
            this.title = response.data.result.title;
            this.content = response.data.result.content;
            this.b_seq =this.editSeq;
          })
          .catch(error => {
            console.log(error);
          })
    }

  },
  methods: {
    registPost() {

      //공백 제거
      this.content = this.content.trim();

      if(this.content !== null && this.content !== '') {
        const formData = new FormData();
        formData.append('content', this.content);
        formData.append('title', this.title);
        formData.append('id', sessionStorage.getItem('id'));
        formData.append('images', JSON.stringify(this.images));

        // 새 글 작성
        if(this.postType === 'write') {
          axios.post(this.$store.state.url + 'post', formData, {
            headers: {
              'Content-Type': 'multipart/form-data',
            },
          })
              .then(response => {
                if (response.data.state) {
                  Swal.fire({
                    icon: 'success',
                    title: response.data.message,
                  });
                  this.$router.push({name: 'treasure-box'});
                } else {
                  Swal.fire({
                    icon: 'error',
                    title: response.data.message,
                  });
                }
              })
              .catch(error => {
                console.log(error);
              });
        }
        //기존 글 수정
        else if(this.postType === 'edit') {
          formData.append('b_seq', this.b_seq);

          axios.put(this.$store.state.url + 'post', formData, {
            headers: {
              'Content-Type': 'multipart/form-data',
            },
          })
              .then(response => {
                if (response.data.state) {
                  Swal.fire({
                    icon: 'success',
                    title: response.data.message,
                  });
                  this.$router.push({name: 'treasure-box'});
                } else {
                  Swal.fire({
                    icon: 'error',
                    title: response.data.message,
                  });
                }
              })
              .catch(error => {
                console.log(error);
              });
        }

      } else {
        Swal.fire({
          icon: 'error',
          title: '내용을 입력해 주세요',
        });
      }
    },
    backToList() {
      location.href = '/treasure';
    },
  },
}
</script>

<style>
.btn-border {
  background-color: white;
  border-color: #797979;
}
h1 {
  font-size: xx-large;
}
</style>