<template>
  <div>
    <h1 class="mt-5">{{ name }}의 롤링페이퍼 💌</h1>

    <div class="container-md mt-5">
      <form class="w-100" @submit.prevent="register" id="formregister">

        <h4>배경색을 선택해 주세요</h4>
        <input type="color" class="form-control mt-3" >

      </form>
    </div>

  </div>
</template>

<script>
export default {

  data() {
    return {
      name: 'create',

    }
  },
  created() {
    this.name = sessionStorage.getItem('name');
  }
}
</script>

<style>

</style>