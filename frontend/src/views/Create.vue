<template>
  <div>
    <h1>{{ name }}의 롤링페이퍼 💌</h1>
    
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

<style></style>