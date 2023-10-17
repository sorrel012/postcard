<template>
  <section class="container mt-5">

    <div class="p-4 pt-5 mb-5 border rounded-end">

      <div class="p-5 border-bottom pb-2">
        <div class="mt-3 d-flex justify-content-between text-secondary">
          <div>
            <span class="me-3">스위트자몽(swt***)</span>
            <span>2023.08.21</span>
          </div>
          <div>20</div>
        </div>

        <div class="border-bottom pb-2 mt-2 text-start fs-4 fw-bold">제목</div>

        <div class="mt-4 text-start fs-5">본문</div>

        <div class="text-end mt-5 mb-4">
          <button type="button" class="btn btn-lg btn-primary me-2">수정</button>
          <button type="button" class="btn btn-lg btn-danger">삭제</button>
        </div>
      </div>

      <!--댓글-->

    </div>

    <div class="text-end mb-2">
      <button type="button" class="btn btn-lg btn-border"><font-awesome-icon :icon="['fas', 'bars']" style="color: black;" /> 목록</button>
    </div>

  </section>
</template>

<script>
import CkEditor from "@/components/CKEditor.vue";

export default {
  name: 'TbDetail',
  components: {CkEditor},
  data() {
    return {
      writingDetail: {},
    }
  },
  created() {
    this.writingDetail = this.$store.state.writingDetail;
    console.log('writing: ', this.writingDetail);
    this.$store.commit('setWritingDetail', {})
    this.writingDetail = this.$store.state.writingDetail;
    console.log('writing: ', this.writingDetail);
  }
}
</script>

<style>
.btn-border {
  background-color: white;
  border-color: #797979;
}
</style>