<template>

</template>

<script>
import Swal from "sweetalert2";

export default {
  name:'TreasureBoxWrite',
  data() {
    return {
      isMember: false,
    }
  },
  async created() {
    const id = sessionStorage.getItem('id');
    if(id == null || id == '') {
      await Swal.fire({
        icon: 'error',
        title: '로그인 후 이용하실 수 있습니다',
      })
      location.href='/login';
      return
    } else {
      this.isMember = true;
    }
  }
}
</script>

<style>

</style>