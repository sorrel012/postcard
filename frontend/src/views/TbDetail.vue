<template>
  <main class="container mt-5 p-3">
    <div class="p-5 pb-2 p-3 mb-4 border rounded-end">
      <!-- 게시글 -->
      <article>
        <section class="d-flex justify-content-between text-secondary">
          <div>
            <span class="me-3">{{ writer }}</span>
            <span>{{ postDetail.regdate }}</span>
          </div>
          <div>조회 {{ postDetail.hit }}</div>
        </section>

        <header class="border-bottom pb-3 mt-3 text-start fs-4 fw-bold">{{ postDetail.title }}</header>

        <section class="mt-5 text-start fs-5" v-html="postDetail.content"></section>

        <div v-if="postDetail.m_id===loginUser" class="text-end mt-5 mb-4">
          <button class="btn btn-lg btn-success me-2" type="button" @click="editPost">수정</button>
          <button class="btn btn-lg btn-danger" type="button" @click="deletePost">삭제</button>
        </div>
      </article>

      <!-- 댓글 -->
      <article>
        <div class="text-end border-bottom pb-1 text-secondary mb-4">
          <font-awesome-icon :icon="['fa', 'message']"/>
          {{ commentCnt }}
        </div>

        <section v-for="comment in commentList" :id="`comment-${comment.c_seq}`"
                 :key="comment.c_seq" class="mt-3 p-3 ps-4 pe-4 text-start text-bg-light rounded">
          <div class="text-secondary">
            <span class="me-3">{{ comment.writer }}</span>
            <span>{{ comment.regdate }}</span>
          </div>

          <div v-if="!comment.editing" class="text-start fs-5 mt-2">{{ comment.content }}</div>
          <div v-else class="mt-2">
            <textarea v-model="comment.editedContent" class="form-control resize-none"></textarea>
          </div>

          <div v-if="comment.m_id===loginUser" class="text-end mt-2">
            <button v-if="!comment.editing" class="btn btn-sm btn-border me-2" type="button"
                    @click="editComment(comment)">수정
            </button>
            <button v-if="!comment.editing" class="btn btn-sm btn-border" type="button"
                    @click="deleteComment(comment.c_seq)">삭제
            </button>

            <button v-if="comment.editing" class="btn btn-sm btn-border me-2" type="button"
                    @click="updateComment(comment)">확인
            </button>
            <button v-if="comment.editing" class="btn btn-sm btn-border me-2" type="button"
                    @click="comment.editing=false">취소
            </button>
          </div>
        </section>

        <div class="btn-group mt-4 mb-4 w-100 overflow-auto">
          <textarea v-model="newComment" class="form-control resize-none"></textarea>
          <input class="btn btn-primary btn-lg" type="button" value="등록" @click="writeComment">
        </div>
      </article>

    </div>

    <div class="text-end mb-2">
      <button class="btn btn-lg btn-border" type="button" @click="backToList">
        <font-awesome-icon :icon="['fas', 'bars']" style="color: black;"/>
        목록
      </button>
    </div>

  </main>
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
  async created() {
    this.loginUser = sessionStorage.getItem('id');

    //게시글 정보 받기
    const b_seq = this.$route.query.seq;
    await axios.get(this.$store.state.url + 'post', {params: {seq: b_seq}})
        .then(response => {
          this.postDetail = response.data.result;
        })
        .catch(error => {
          console.log(error);
        })

    //조회수 올리기
    axios.put(this.$store.state.url + 'hit', this.postDetail.b_seq, {
      headers: {
        'Content-Type': 'application/json'
      }
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
  watch: {
    commentList() { //해당 댓글 위치로 스크롤
      this.$nextTick(() => {
        if (this.$route.query.commentId) {
          const commentElement = document.getElementById(`comment-${this.$route.query.commentId}`);
          if (commentElement) {
            commentElement.scrollIntoView({behavior: 'smooth'});
          }

        }
      });
    }
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
            this.commentList = response.data.result.map(comment => {
              comment.editing = false;
              comment.editedContent = '';
              return comment;
            });
            this.commentCnt = this.commentList.length;
          })
          .catch(error => {
            console.log(error);
          })
    },
    editPost() {
      router.push({name: 'treasure-write', query: {seq: this.postDetail.b_seq}})
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
          return axios.delete(this.$store.state.url + 'post', {params: {seq: this.postDetail.b_seq}})
              .then(response => {
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
    editComment(comment) {
      comment.editing = true; // 수정 모드로 전환
      comment.editedContent = comment.content; // 원래 댓글 내용을 editedContent에 저장
    },
    updateComment(comment) {
      axios.put(this.$store.state.url + 'comment', {content: comment.editedContent, c_seq: comment.c_seq})
          .then(response => {
            comment.editing = false; // 수정 모드 종료
            this.getCommentList();
          })
          .catch(error => {
            console.log(error);
          });
    },
    deleteComment(seq) {
      Swal.fire({
        title: '삭제하시겠습니까?',
        text: "삭제한 댓글은 다시 되돌릴 수 없습니다",
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
        confirmButtonText: 'Delete',
        preConfirm: () => {
          return axios.delete(this.$store.state.url + 'comment', {params: {seq: seq}})
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
        this.getCommentList();
      })
    }
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