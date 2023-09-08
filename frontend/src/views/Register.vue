<template>
  <section class="container-md d-flex justify-content-center p-5 mt-4 mb-4">

    <div class="col-sm-12 col-md-12 col-lg-5 d-flex align-items-center rounded-end me-5 w-75 h-100 p-3">

      <div class="container-md d-flex flex-column align-items-center">

        <h1 class="h2 text-center fw-bold title">개인 회원가입</h1>
        <p class="fs-6 text-center mb-4">회원가입 후 다양한 서비스를 이용하세요.</p>

        <form class="w-100 mt-3" @submit.prevent="register" id="formregister">

          <div class="row mb-4">
            <div class="col-2 f-bold text-start d-flex align-items-center ps-4">아이디</div>
            <div class="col-10">
              <div class="form-floating">
                <input type="text" class="form-control" placeholder="" v-model="userinfo.id">
                <label for="floatingInput">6~12자 이내로 입력해 주세요</label>
              </div>
            </div>
          </div>

          <div class="row mb-4">
            <div class="col-2 f-bold text-start d-flex align-items-center ps-4">비밀번호</div>
            <div class="col-10">
              <div class="form-floating">
                <input type="password" class="form-control" placeholder="" required v-model="userinfo.pw">
                <label for="floatingInput">8~12자 이내로 입력해 주세요 (알파벳, 숫자 필수)</label>
              </div>
            </div>
          </div>

          <div class="row mb-4">
            <div class="col-2 f-bold text-start d-flex align-items-center ps-4">이름</div>
            <div class="col-10">
              <div class="form-floating">
                <input type="text" class="form-control" placeholder="" required v-model="userinfo.name">
                <label for="floatingInput">이름을 입력해 주세요</label>
              </div>
            </div>
          </div>

          <div class="row mb-4">
            <div class="col-2 f-bold text-start d-flex ps-4 align-items-center">연락처</div>
            <div class="col-10">
              <div class="d-flex">
                <div class="form-floating btn-group w-100">
                  <input type="text" class="form-control" placeholder="" required v-model="userinfo.tel">
                  <label for="floatingInput">-빼고 입력해 주세요.</label>
                  <input type="button" class="btn btn-primary" :value="authMsg" @click="requireAuth">
                </div>
              </div>
            </div>
          </div>

          <div class="row mb-4">
            <div class="col-2 f-bold text-start d-flex align-items-center ps-4">생년월일</div>
            <div class="col-10">
              <div class="form-floating">
                <input type="date" class="form-control" placeholder="" required v-model="userinfo.birth">
                <label for="floatingInput">날짜를 골라주세요</label>
              </div>
            </div>
          </div>

          <div class="row mb-4">
            <div class="col-2 f-bold text-start d-flex align-items-baseline ps-4">주소</div>
            <div class="col-10">
              <div class="form-floating btn-group mb-2 w-100">
                <input type="text" class="form-control" placeholder="" readonly required>
                <label for="floatingInput">주소를 검색해 주세요</label>
                <input type="button" class="btn btn-primary" value="주소 검색" onclick="sample6_execDaumPostcode();">
              </div>
              <div class="form-floating">
                <input type="text" class="form-control" placeholder="" required>
                <label for="floatingInput">상세 주소를 입력해 주세요</label>
              </div>
            </div>
          </div>

          <div class="container-fluid p-0 mt-3">
            <button type="submit" class="btn btn-primary mt-4 w-100" id="btnregister">회원가입</button>
          </div>
        </form>

      </div>
    </div>
  </section>
</template>

<script>
import axios from 'axios';
import Swal from "sweetalert2";

export default {
  name: 'Register',
  data() {
    return {
      userinfo: {
        id: '',
        pw: '',
        name: '',
        birth: '',
        tel: '',
        address: '',
        addressDetail: ''
      },
      authMsg: '본인 인증',
      authCode: '',
    }
  },
  methods: {
    requireAuth() {
      this.makeAuthCode();
      const authConfig = {
        to: this.userinfo.tel,
        content: this.authCode
      }
      axios.post(this.$store.state.url + 'auth', authConfig)
          .then(result => {
            console.log(result)
          }).catch(error => {
            console.log(error);
          })
      Swal.fire({
        title: '인증번호를 입력해 주세요.',
        text: '입력하신 번호로 인증번호가 발송되었습니다',
        input: 'text',
        inputAttributes: {
          autocapitalize: 'off'
        },
        showCancelButton: true,
        confirmButtonText: 'Submit',
        showLoaderOnConfirm: true,
        preConfirm: (code) => {
          if(code !== this.authCode) {
            throw new Error('인증번호 불일치')
          }
          return true;
        },
        allowOutsideClick: () => !Swal.isLoading()
      }).then((result) => {
        if (result.isConfirmed) {
          Swal.fire({
            title: '인증에 성공했습니다.',
            text: '회원가입을 이어서 진행해 주세요'
          });
          this.authMsg = '인증 완료';
        }
      }).catch(error => {
        console.log(error);
        Swal.showValidationMessage(
            '인증에 실패했습니다.'
        )
      })
    },
    makeAuthCode() {
      const chars = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789';
      let authCode = '';
      for (let i = 0; i < 8; i++) {
        const index = Math.floor(Math.random() * chars.length);
        authCode += chars[index];
      }
      this.authCode= authCode;
    },
    register() {
      axios.post(this.$store.state.url + 'register', this.userinfo)
          .then(response => {
            console.log(response);
            if (response.data.state) {
              Swal.fire({
                icon: 'success',
                title: response.data.message,
              });
              this.$router.push({name: 'login'});
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
              title: '아이디 비밀번호가 일치하지 않습니다.',
            });
          });

    },
  }
}
</script>

<style scoped>
section {
  border: 1px solid #e3e3e3;
  border-radius: 10px;
}

</style>