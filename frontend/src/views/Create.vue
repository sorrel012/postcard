<template>
  <div class="bg-color p-5 h-100">
    <h1 class="mt-4">{{ name }}의 롤링페이퍼 💌</h1>

    <div class="container-md mt-5 h-100 d-inline-block">
      <form class="w-100 h-100" @submit.prevent="register">

        <h4 class="m-4">배경색을 선택해 주세요</h4>
        <input type="color" class="form-control mt-5 mb-2" style="height: 50px;" v-model="bgColor">

      </form>
    </div>

  </div>
</template>

<script>
export default {
  name: 'create',
  data() {
    return {
      bgColor: '',
    }
  },
  created() {
    this.name = sessionStorage.getItem('name');
  },
  watch: {
    bgColor() {
      console.log(this.bgColor);
    }
  }
}
</script>

<style>
.bg-color {
  background-color: v-bind(bgColor);
}
</style>