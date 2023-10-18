<template>
  <section class="container mt-5">

    <div class="p-3 mb-4 border rounded-end">

      <div class="p-5 pb-2">
        <div class="d-flex justify-content-between text-secondary">
          <div>
            <span class="me-3">{{ writer }}</span>
            <span>{{ writingDetail.regdate }}</span>
          </div>
          <div>{{ writingDetail.hit }}</div>
        </div>

        <div class="border-bottom pb-3 mt-3 text-start fs-4 fw-bold">{{ writingDetail.title }}</div>

        <div class="mt-5 text-start fs-5" v-html="writingDetail.content"></div>

        <div class="text-end mt-5 mb-4">
          <button type="button" class="btn btn-lg btn-success me-2">수정</button>
          <button type="button" class="btn btn-lg btn-danger">삭제</button>
        </div>

        <div class="text-end border-bottom pb-1 text-secondary">
          <div><font-awesome-icon :icon="['fa', 'message']"  /> {{ commentCnt }}</div>
        </div>

        <!--댓글-->
        <div class="mt-4">
          <div class="btn-group mb-2 w-100 overflow-auto ">
            <textarea class="form-control resize-none" v-model="comment"></textarea>
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

export default {
  name: 'TbDetail',
  components: {CkEditor},
  data() {
    return {
      writingDetail: {},
      writer: '',
      commentCnt: 0,
      loginUser: '',
      comment: '',
    }
  },
  created() {
    this.writingDetail = this.$store.state.writingDetail;
    this.$store.commit('setWritingDetail', {})

    const maskingId = this.writingDetail.m_id.substring(0, 3);
    this.writer = `${this.writingDetail.name}(${maskingId}***)`;


  },
  methods: {
    backToList() {
      location.href = '/treasure';
    },
    writeComment() {
      //댓글 저장
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