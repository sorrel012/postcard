<template>
  <section class="container mt-5">

    <div class="p-4 pt-5 mb-5 border rounded-end">

      <div class="p-5 border-bottom pb-2">
        <div class="mt-3 d-flex justify-content-between text-secondary">
          <div>
            <span class="me-3">{{ user }}</span>
            <span>{{ writingDetail.regdate }}</span>
          </div>
          <div>{{ writingDetail.hit }}</div>
        </div>

        <div class="border-bottom pb-3 mt-4 text-start fs-4 fw-bold">{{ writingDetail.title }}</div>

        <div class="mt-4 text-start fs-5" v-html="writingDetail.content"></div>

        <div class="text-end mt-5 mb-4">
          <button type="button" class="btn btn-lg btn-primary me-2">수정</button>
          <button type="button" class="btn btn-lg btn-danger">삭제</button>
        </div>
      </div>

      <!--댓글-->

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
      user: '',
    }
  },
  created() {
    this.writingDetail = this.$store.state.writingDetail;
    this.$store.commit('setWritingDetail', {})

    const maskingId = this.writingDetail.m_id.substring(0, 3);
    this.user = `${this.writingDetail.name}(${maskingId}***)`;
  },
  methods: {
    backToList() {
      location.href = '/treasure';
    }
  }
}
</script>

<style>
.btn-border {
  background-color: white;
  border-color: #797979;
}
</style>