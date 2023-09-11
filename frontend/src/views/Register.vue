<template>
  <section class="container-md d-flex justify-content-center p-5 mt-4 mb-4">

    <div class="col-sm-12 col-md-12 col-lg-5 d-flex align-items-center rounded-end me-5 w-75 h-100 p-3">

      <div class="container-md d-flex flex-column align-items-center">

        <h1 class="h2 text-center fw-bold title">개인 회원가입</h1>
        <p class="fs-6 text-center mb-4">회원가입 후 다양한 서비스를 이용하세요.</p>

        <form class="w-100 mt-3" @submit.prevent="register" id="formregister">

          <div class="row mb-4">
            <div class="col-2 f-bold text-start d-flex align-items-center ps-4" style="height: 50px;">아이디</div>
            <div class="col-10">
              <div class="d-flex flex-column">
                <div class="form-floating btn-group w-100 mb-2">
                  <input type="text" class="form-control" :readonly="!isDup" placeholder="" v-model="userinfo.id">
                  <label for="floatingInput">6~12자로 입력해 주세요</label>
                  <input type="button" class="btn btn-primary" :value="idCheckBtnMsg" @click="dupOrMod">
                </div>
                <div class="form-floating btn-group w-50 flex-start ps-2 mt-1 c-red" :class="{'d-none':!isCheckDup}">
                  {{dupMsg}}
                </div>
              </div>
            </div>
          </div>

          <div class="row mb-4">
            <div class="col-2 f-bold text-start d-flex align-items-center ps-4">비밀번호</div>
            <div class="col-10">
              <div class="form-floating">
                <input type="password" class="form-control" placeholder="" required v-model="userinfo.pw">
                <label for="floatingInput">8~15자로 입력해 주세요 (알파벳, 숫자 필수)</label>
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
            <div class="col-2 f-bold text-start d-flex align-items-center ps-4" style="height: 48px;">연락처</div>
            <div class="col-10">
              <div class="d-flex flex-column">
                <div class="form-floating btn-group w-100 mb-2">
                  <input type="text" class="form-control" :readonly="isSuccess" placeholder="" required v-model="userinfo.tel">
                  <label for="floatingInput">-빼고 입력해 주세요.</label>
                  <input type="button" class="btn btn-primary" :disabled="isSuccess"  :value="authMsg" @click="requireAuth">
                </div>
                <div class="form-floating btn-group w-50 flex-start" :class="{'d-none':!wantAuth}">
                  <input type="text" class="form-control" placeholder="" required v-model="userinfo.userAuthCode" :readonly="isSuccess">
                  <label for="floatingInput">인증번호를 입력해 주세요.</label>
                  <input type="button" class="btn btn-primary" value="인증" @click="confirmAuth" :disabled="isSuccess">
                </div>
                <div class="form-floating btn-group w-50 flex-start ps-2 mt-1 c-red" :class="{'d-none':!isAuth}">
                  {{confirmMsg}}
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
            <div class="col-2 f-bold text-start d-flex align-items-center ps-4" style="height: 48px;">주소</div>
            <div class="col-10">
              <div class="form-floating btn-group mb-2 w-100" >
                <input type="text" class="form-control" placeholder="" readonly required>
                <label for="floatingInput">주소를 검색해 주세요</label>
                <input type="button" class="btn btn-primary" value="주소 검색" @click="sample6_execDaumPostcode();">
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
      confirmMsg: '',
      authCode: '',
      userAuthCode: '',
      wantAuth: false,
      isAuth: false,
      isSuccess: false,
      isCheckDup: false,
      isDup: true,
      idCheckBtnMsg: '중복 확인',
      dupMsg: '',
    }
  },
  methods: {
    dupOrMod() {
      if(!this.isDup) {
        this.isCheckDup = false;
        this.isDup = true;
        this.userinfo.id = '';
        this.idCheckBtnMsg = '중복 확인';

      } else {
        this.checkDup();
      }
    },
    checkDup() {
      if(this.userinfo.id.length < 6) {
        Swal.fire({
          title: '아이디를 6자 이상 입력해 주세요',
          icon: 'error'
        });
        return
      }

      if(this.userinfo.id.length > 12) {
        Swal.fire({
          title: '아이디를 12자 이하 입력해 주세요',
          icon: 'error'
        });
        return
      }

      this.isCheckDup = true;
      axios.post(this.$store.state.url + 'dup', this.userinfo)
          .then(result => {
            this.dupMsg = result.data.message;

            if(result.data.state) {
              this.isDup = false;
              this.idCheckBtnMsg = '수정하기'
            }

          })
          .catch(error => {
            console.log(error);
          })
    },
    requireAuth() {

      // if(this.userinfo.tel.length != 11) {
      //   Swal.fire({
      //     title: '연락처를 입력해 주세요',
      //     icon: 'error'
      //   });
      //   return
      // }

      this.wantAuth = true;
      this.makeAuthCode();
      const authConfig = {
        to: this.userinfo.tel,
        content: this.authCode
      }
      // axios.post(this.$store.state.url + 'auth', authConfig)
      //     .then(result => {
      //       if(result.data.status) {
      //         this.confirmMsg = '인증 성공';
      //         this.isAuth = true;
      //         this.authMsg = '인증 완료'
      //       }
      //     }).catch(error => {
      //       console.log(error);
      //     })
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
    confirmAuth() {
      this.isAuth = true;
      if(this.authCode == this.userAuthCode) {
        this.confirmMsg = '인증 성공';
        this.isSuccess = true;
        this.authMsg = '인증 완료'
      } else {
        this.confirmMsg = '인증 실패';
        this.isSuccess = false;
      }
    },
    register() {

      console.log('생일: ', this.userinfo.birth);
      
      //아이디 중복확인 완료 검사
      if(this.isDup) {
        Swal.fire({
          title: '아이디 중복 검사를 완료해 주세요',
          icon: 'error'
        });
        return
      }

      //비밀번호 유효성 검사(숫자+문자, 8-12글자)
      const pwPattern = new RegExp('^(?=.*[A-Za-z])(?=.*\\d).{8,15}$');
      let isPwValid = pwPattern.test(this.userinfo.pw);
      if(!isPwValid) {
        Swal.fire({
          title: '올바르지 않은 비밀번호 형식입니다',
          text: '숫자와 알파벳을 포함하여 8-15자로 입력해 주세요',
          icon: 'error'
        });
        return
      }

      //문자 본인 인증 완료 검사
      if(!this.isAuth) {
        Swal.fire({
          title: '본인 인증을 완료해 주세요',
          icon: 'error'
        });
        return
      }

      //생년월일 유효성 검사(YYYY-mm-DD)
      const bdPattern = new RegExp('^([0-9]{0,4})-([0-9]{0,2})-([0-9]{0,2})$');
      let isBdValid = bdPattern.test(this.userinfo.birth);
      if(!isBdValid) {
        Swal.fire({
          title: '올바르지 않은 생년월일 형식입니다',
          text: '연도(4자리)-월(2자리)-일(2자리) 형식으로 입력해 주세요',
          icon: 'error'
        });
        return
      }

      // axios.post(this.$store.state.url + 'register', this.userinfo)
      //     .then(response => {
      //       console.log(response);
      //       if (response.data.state) {
      //         Swal.fire({
      //           icon: 'success',
      //           title: response.data.message,
      //         });
      //         this.$router.push({name: 'login'});
      //       } else {
      //         Swal.fire({
      //           icon: 'error',
      //           title: response.data.message,
      //         });
      //       }
      //     })
      //     .catch(error => {
      //       console.log(error);
      //       Swal.fire({
      //         icon: 'error',
      //         title: '아이디 비밀번호가 일치하지 않습니다.',
      //       });
      //     });

    },
  }
}
</script>

<style scoped>
section {
  border: 1px solid #e3e3e3;
  border-radius: 10px;
}
.c-red {
  color: red;
}
</style>