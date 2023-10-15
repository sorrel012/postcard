<template>
  <div class="p-5 h-100" :class="{'d-none':!isMember}" :style="{backgroundColor: paper.bgColor }" >
    <h1 class="mt-4">{{ name }}의 롤링페이퍼 💌</h1>

    <div class="container-md mt-5 h-100 mh-100 d-inline-block">
      <form class="w-100 h-100 mh-100" @submit.prevent="create">


        <h4 class="m-4 f-bold">도화지 이름</h4>
          <input type="text" placeholder="친구들에게 보여질 이름을 적어주세요" class="form-control mt-4 mb-5" style="height: 50px;" v-model="paper.title" required>

        <h4 class="m-4 mt-5 mb-3 f-bold">도화지 배경 색상</h4>
        <h6>선택하지 않을 시 배경 색상은 하얀색으로 지정됩니다.</h6>
        <input type="color" class="form-control mt-4 mb-2" style="height: 50px;" v-model="fbgColor" >

        <h4 class="m-4 mt-5 mb-3 f-bold">쪽지 꾸미기</h4>
        <h6>선택하지 않을 시 쪽지는 기본으로 지정됩니다</h6>
        <div class="d-flex mt-4">
          <div class="w-50 me-5 p-4 fs-4" :style="{backgroundColor: paper.pcColor,
                                                  outline: `${paper.pcBorderPx} solid ${paper.pcbColor}`,
                                                  borderRadius: paper.pcbRadiusPx,
                                                  boxShadow: `4px 4px 1px 3px ${paper.pcbColor}`,
                                                  }">
            <p>쪽지입니다.</p>
            <p>친구에게 마음을 전해주세요!</p>
          </div>
          <div class="w-50">
            <label class="form-label">배경 색상</label>
            <input type="color" class="form-control mb-3 w-100" style="height: 50px;" v-model="paper.pcColor" >

            <label class="mt-2 mb-0 form-label">테두리 두께</label>
            <input type="range" class="form-range mb-1 w-100" min="0" max="5" style="height: 50px;" v-model="pcBorder" value="postcard.pcBorderPx">

            <label class="mt-1 form-label">테두리 색상</label>
            <input type="color" class="form-control mb-2 w-100" style="height: 50px;" v-model="paper.pcbColor">

            <label class="mt-3 mb-0 form-label">테두리 둥글기</label>
            <input type="range" class="form-range w-100" min="0" max="50" style="height: 50px;" v-model="pcbRadius" value="postcard.pcbRadiusPx">

          </div>
        </div>

        <div class="container-fluid p-0 mt-5">
          <button type="submit" class="btn btn-primary mt-5 w-100 mb-4" id="btnCreate">만들기</button>
        </div>

      </form>
    </div>

  </div>
</template>

<script>
import axios from "axios";
import Swal from "sweetalert2";
import router from "@/router";

export default {
  name: 'create',
  data() {
    return {
      paper: {
        title: '',
        bgColor: '#FFFFFF',
        pcColor: '#FFFFFF',
        pcbColor: '#000000',
        pcBorderPx: '',
        pcbRadiusPx: '',
        id: ''
      },
      name: '',
      pcBorder: 2,
      pcbRadius: 5,
      fbgColor: '#FFFFFF',
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

    this.name = sessionStorage.getItem('name');
    this.paper.pcBorderPx = this.pcBorder + 'px';
    this.paper.pcbRadiusPx = this.pcbRadius + '%';
    this.paper.bgColor = this.fbgColor;
    document.body.style.backgroundColor = this.fbgColor;

  },
  watch: {
    pcBorder() {
      this.paper.pcBorderPx = this.pcBorder + 'px';
    },
    pcbRadius() {
      this.paper.pcbRadiusPx = this.pcbRadius + '%';
    },
    fbgColor () {
      this.paper.bgColor = this.fbgColor;
      document.body.style.backgroundColor = this.fbgColor;
    }
  },
  methods: {
    create() {
      this.paper.id = sessionStorage.getItem('id');
      axios.post(this.$store.state.url + 'create', this.postcard)
          .then(response => {
            console.log(response);
            if (response.data.state) {
              Swal.fire({
                icon: 'success',
                title: response.data.result,
                text: response.data.message,
                timer: 10000,
              }).then(() => {
                location.href = '/mypaper';
              });
            } else {
              Swal.fire({
                icon: 'error',
                title: response.data.message,
              });
            }
          })
          .catch(error => {
            console.log(error);
            Swal.fire({
              icon: 'error',
              title: '도화지를 만들지 못했습니다',
            });
          });
    }
  }
}
</script>

<style>
.mh-100 {
  max-height: 100vh !important;
}
</style>