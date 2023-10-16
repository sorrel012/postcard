<template>

</template>

<script>
export default {
  name: 'TbDetail',
  data() {
    return {
      writingDetail: {},
    }
  },
  created() {
    this.writingDetail = this.$store.state.writingDetail;
    console.log('writing: ', this.writingDetail);
  }
}
</script>

<style scoped>

</style>