<template>
  <section class="container mt-5">

    <div class="p-3 mb-4 border rounded-end">

      <div class="p-5 pb-2">
        <div class="d-flex justify-content-between text-secondary">
          <div>
            <span class="me-3">{{ writer }}</span>
            <span>{{ postDetail.regdate }}</span>
          </div>
          <div>조회 {{ postDetail.hit }}</div>
        </div>

        <div class="border-bottom pb-3 mt-3 text-start fs-4 fw-bold">{{ postDetail.title }}</div>

        <div class="mt-5 text-start fs-5" v-html="postDetail.content"></div>

        <div class="text-end mt-5 mb-4" v-if="postDetail.m_id===loginUser">
          <button type="button" class="btn btn-lg btn-success me-2" @click="editPost">수정</button>
          <button type="button" class="btn btn-lg btn-danger" @click="deletePost">삭제</button>
        </div>

        <div class="text-end border-bottom pb-1 text-secondary">
          <div><font-awesome-icon :icon="['fa', 'message']"  /> {{ commentCnt }}</div>
        </div>

<!--        <div v-for="comment in commentList" :key="comment.c_seq">-->
<!--          -->
<!--        </div>-->
        <div class="mt-3 p-3 text-start text-bg-light rounded" v-for="comment in commentList" :key="commentList.c_seq">
          <div class="text-secondary">
            <span class="me-3">{{ comment.writer }}</span>
            <span>{{ comment.regdate }}</span>
          </div>
          <div class="text-start fs-5 mt-2">{{ comment.content }}</div>

          <div class="text-end mt-2" v-if="comment.m_id===loginUser">
            <button type="button" class="btn btn-sm btn-border me-2">수정</button>
            <button type="button" class="btn btn-sm btn-border"">삭제</button>
          </div>
        </div>

        <div class="mt-4">
          <div class="btn-group mb-2 w-100 overflow-auto">
            <textarea class="form-control resize-none" v-model="newComment"></textarea>
            <input type="button" class="btn btn-primary btn-lg" value="등록" @click="writeComment">
          </div>
        </div>
      </div>

    </div>

    <div class="text-end mb-2">
      <button type="button" class="btn btn-lg btn-border" @click="backToList"><font-awesome-icon :icon="['fas', 'bars']" style="color: black;" /> 목록</button>
    </div>

  </section>
</template>

<script>
import CkEditor from "@/components/CKEditor.vue";
import axios from "axios";
import Swal from "sweetalert2";
import router from "@/router";

export default {
  name: 'TbDetail',
  components: {CkEditor},
  data() {
    return {
      postDetail: {},
      writer: '',
      commentCnt: 0,
      commentList: {},
      loginUser: '',
      newComment: '',
    }
  },
  created() {
    this.loginUser =sessionStorage.getItem('id');

    //게시글 정보 받기
    this.postDetail = this.$store.state.postDetail;

    //조회수 올리기
    axios.put(this.$store.state.url + 'hit', this.postDetail.b_seq , {
            headers: {
              'Content-Type': 'application/json'
            }
          })
        .then(response => {
          console.log(response);
        })
        .catch(error => {
          console.log(error);
        })

    //작성자 이름
    const maskingId = this.postDetail.m_id.substring(0, 3);
    this.writer = `${this.postDetail.name}(${maskingId}***)`;

    //댓글
    this.getCommentList();
  },
  methods: {
    backToList() {
      location.href = '/treasure';
    },
    writeComment() {
      const comment = {
        b_seq: this.postDetail.b_seq,
        m_id: this.loginUser,
        content: this.newComment
      }
      axios.post(this.$store.state.url + 'comment', comment)
          .then(response => {
            console.log(response);
            this.getCommentList();
            this.newComment = '';
          })
          .catch(error => {
            console.log(error);
          })
    },
    getCommentList() {
      axios.get(this.$store.state.url + 'commentlist', {params: {seq: this.postDetail.b_seq}})
          .then(response => {
            this.commentList = response.data.result;
            this.commentCnt = this.commentList.length;
          })
          .catch(error => {
            console.log(error);
          })
    },
    editPost() {
      sessionStorage.setItem('postType', 'edit');
      this.$store.commit('setPostDetail', this.postDetail);
      router.push({name: 'treasure-write'})
    },
    deletePost() {
      Swal.fire({
        title: '삭제하시겠습니까?',
        text: "삭제한 게시글은 다시 되돌릴 수 없습니다",
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
        confirmButtonText: 'Delete',
        preConfirm: () => {
          return axios.delete(this.$store.state.url + 'post', {params: {seq : this.postDetail.b_seq}})
              .then(response => {
                console.log(response);
                router.push({name: 'treasure-box'});
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
  }
}
</script>

<style>
.btn-border {
  background-color: white;
  border-color: #797979;
}
img {
  max-width: 100%;
}
.resize-none {
  resize: none;
}
</style>