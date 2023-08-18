<template>
  <div class="p-5 h-100">
    <h1 class="mt-4 mb-5">{{ paper.title }}</h1>

    <div class="container mt-5 d-flex">
      <div class="postcard p-4 fs-4">첫 번째 쪽지~ 안녕안녕안녕</div>
      <div class="postcard p-4 fs-4">두 번째 쪽지</div>
    </div>

    <div>
      <button>추가</button>
    </div>

  </div>
</template>

<script>
export default {
  name: 'MyPaper',
  data() {
    return {
      paper: {},
    }
  },
  created() {
    this.paper = this.$store.state.paper;
    console.log(this.paper.bgColor);
    document.body.style.backgroundColor = this.paper.bgColor;
  },
  mounted() {
  },
}
</script>

<style scoped>
.postcard {
  background-color: antiquewhite;
  outline: 2px solid black;
  border-radius: postcard.pcbRadiusPx;
  boxS-shadow: 4px 4px 1px 3px black;
}
</style>