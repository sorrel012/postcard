<template>
  <main :class="{'d-none':!isMember}" :style="{backgroundColor: paper.bgColor }" class="p-5 h-100">
    <header>
      <h1 class="mt-4">{{ name }}의 롤링페이퍼 💌</h1>
    </header>

    <section class="container-md mt-5 h-100 mh-100 d-inline-block">
      <form class="w-100 h-100 mh-100" @submit.prevent="create">
        <label class="mt-4 f-bold fs-3" for="title">도화지 이름</label>
        <input id="title" v-model="paper.title" class="form-control mt-4 mb-5" placeholder="친구들에게 보여질 이름을 적어주세요"
               required style="height: 50px;" type="text">

        <label class="m-4 mb-1 f-bold fs-3" for="fbgColor">도화지 배경 색상</label>
        <p class="fs-5 text-gray">선택하지 않을 시 배경 색상은 하얀색으로 지정됩니다.</p>
        <input id="fbgColor" v-model="fbgColor" class="form-control mt-4 mb-3" style="height: 50px;" type="color">

        <label class="m-4 mt-5 mb-1 f-bold fs-3">쪽지 꾸미기</label>
        <p class="fs-5 text-gray">선택하지 않을 시 쪽지는 기본으로 지정됩니다</p>
        <section class="d-flex mt-4">
          <article :style="{backgroundColor: paper.pcColor,
                                                  outline: `${paper.pcBorderPx} solid ${paper.pcbColor}`,
                                                  borderRadius: paper.pcbRadiusPx,
                                                  boxShadow: `4px 4px 1px 3px ${paper.pcbColor}`,
                                                  }" class="w-50 me-5 p-4 fs-4">
            <p>쪽지입니다.</p>
            <p>친구에게 마음을 전해주세요!</p>
          </article>
          <div class="w-50">
            <label class="form-label fs-5 f-bold text-gray" for="pcColor">배경 색상</label>
            <input id="pcColor" v-model="paper.pcColor" class="form-control mb-3 w-100" style="height: 50px;"
                   type="color">

            <label class="mt-2 mb-0 form-label l fs-5 f-bold text-gray" for="pcBorderPx">테두리 두께</label>
            <input id="pcBorderPx" v-model="pcBorder" class="form-range mb-1 w-100" max="5" min="0"
                   style="height: 50px;"
                   type="range" value="postcard.pcBorderPx">

            <label class="mt-1 form-label l fs-5 f-bold text-gray" for="pcbColor">테두리 색상</label>
            <input id="pcbColor" v-model="paper.pcbColor" class="form-control mb-2 w-100" style="height: 50px;"
                   type="color">

            <label class="mt-3 mb-0 form-label l fs-5 f-bold text-gray" for="pcbRadiusPx">테두리 둥글기</label>
            <input id="pcbRadiusPx" v-model="pcbRadius" class="form-range w-100" max="50" min="0" style="height: 50px;"
                   type="range" value="postcard.pcbRadiusPx">

          </div>
        </section>

        <div class="container-fluid p-0 mt-5">
          <button id="btnCreate" class="btn btn-primary mt-5 w-100 mb-4" type="submit">만들기</button>
        </div>
      </form>
    </section>
  </main>
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
        id: '',
        pcc_seq: 0
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
    if (id == null || id == '') {
      await Swal.fire({
        icon: 'error',
        title: '로그인 후 이용하실 수 있습니다',
      })
      location.href = '/login';
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
    fbgColor() {
      this.paper.bgColor = this.fbgColor;
      document.body.style.backgroundColor = this.fbgColor;
    }
  },
  methods: {
    create() {
      this.paper.id = sessionStorage.getItem('id');
      axios.post(this.$store.state.url + 'create', this.paper)
          .then(response => {
            if (response.data.state) {
              this.paper.pcc_seq = response.data.result.pcc_seq;
              this.$store.commit('setPaper', this.paper);
              Swal.fire({
                icon: 'success',
                title: response.data.result.code,
                text: response.data.message,
                timer: 10000,
              }).then(() => {
                router.push({name: 'mypaper'});
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