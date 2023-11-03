<template>
  <section class="container-md d-flex justify-content-center p-5 mt-4 mb-4">

    <div class="col-sm-12 col-md-12 col-lg-5 d-flex align-items-center rounded-end me-5 w-75 h-100 p-3">

      <div class="container-md d-flex flex-column align-items-center">

        <h1 class="h2 text-center fw-bold title">소셜 로그인 회원가입</h1>
        <p class="fs-6 text-center mb-4">회원가입 후 다양한 서비스를 이용하세요.</p>

        <form id="formregister" class="w-100 mt-3" @submit.prevent="register">

          <div class="row mb-4">
            <div class="col-2 f-bold text-start d-flex align-items-center ps-4">아이디</div>
            <div class="col-10 d-flex flex-column">
              <div class="btn-group w-100">
                <input ref="idRef" :value="userinfo.id" class="form-control" disabled required type="text">
              </div>
            </div>
          </div>

          <div class="row mb-4 mt-4">
            <div class="col-2 f-bold text-start d-flex align-items-center ps-4" style="height: 50px;">이름(닉네임)</div>
            <div class="col-10">
              <div class="d-flex flex-column">
                <div class="form-floating btn-group w-100 mb-2">
                  <input ref="nameRef" v-model="userinfo.name" :readonly="!isDupName" class="form-control" placeholder=""
                         required type="text">
                  <label for="floatingInput">이름은 10자까지 입력할 수 있습니다</label>
                  <input :value="nameCheckBtnMsg" class="btn btn-primary" type="button" @click="dupOrMod">
                </div>
                <div v-if="isCheckNameDup" :class="{'c-red':isDupName, 'c-blue':!isDupName}"
                     class="form-floating btn-group w-50 flex-start ps-2">
                  {{ nameDupMsg }}
                </div>
              </div>
            </div>
          </div>

          <div class="row mb-4">
            <div class="col-2 f-bold text-start d-flex align-items-center ps-4">이메일</div>
            <div class="col-10 d-flex flex-column">
              <div class="btn-group w-100">
                <input ref="eRef" :disabled="hasEmail" :value="userinfo.email" class="form-control" placeholder=""
                       type="text">
              </div>
            </div>
          </div>

          <div class="row mb-4">
            <div class="col-2 f-bold text-start d-flex align-items-center ps-4">연락처</div>
            <div class="col-10">
              <div class="d-flex flex-column">
                <div class="form-floating btn-group w-100 mb-2">
                  <input ref="telRef" v-model="userinfo.tel" class="form-control" placeholder="" required type="text">
                  <label for="floatingInput">-빼고 입력해 주세요.</label>
                </div>
              </div>
            </div>
          </div>

          <div class="row mb-4">
            <div class="col-2 f-bold text-start d-flex align-items-center ps-4">생년월일</div>
            <div class="col-10">
              <div class="form-floating">
                <input ref="bdRef" v-model="userinfo.birth" class="form-control" placeholder="" required type="date">
                <label for="floatingInput">날짜를 골라주세요</label>
              </div>
            </div>
          </div>

          <div class="row mb-4">
            <div class="col-2 f-bold text-start d-flex align-items-center ps-4" style="height: 48px;">주소</div>
            <div class="col-10">
              <div class="form-floating btn-group mb-2 w-100">
                <input ref="addRef" v-model="userinfo.address" class="form-control" placeholder="" readonly required
                       type="text">
                <label for="floatingInput">주소를 검색해 주세요</label>
                <input class="btn btn-primary" type="button" value="주소 검색" @click="daumPostcode">
              </div>
              <div class="form-floating">
                <input ref="addDetailRef" v-model="userinfo.addressDetail" class="form-control" placeholder="" required
                       type="text">
                <label for="floatingInput">상세 주소를 입력해 주세요</label>
              </div>
            </div>
          </div>

          <div class="container-fluid p-0 mt-3">
            <button class="btn btn-primary mt-4 w-100" type="submit">회원가입</button>
            <div class="mt-2">* 이름(닉네임)은 가입 후 변경할 수 없습니다.</div>
          </div>
        </form>

      </div>
    </div>
  </section>
</template>

<script>

import Swal from "sweetalert2";
import axios from "axios";

export default {
  name: 'SnsRegister',
  data() {
    return {
      userinfo: {
        id: this.$store.state.snsUser.id,
        email: this.$store.state.snsUser.email,
        name: '',
        birth: '',
        tel: '',
        address: '',
        addressDetail: ''
      },
      hasEmail: true,
      isCheckNameDup: false,          //이름 중복확인 버튼 클릭 여부
      isDupName: true,                //이름 중복 여부
      nameCheckBtnMsg: '중복 확인',    //이름 중복확인 버튼 문구
      nameDupMsg: '',                 //이름 중복확인 결과
    }
  },
  created() {
    if (this.$store.state.snsUser.email == '' || this.$store.state.snsUser.email == null) {
      this.hasEmail = false;
    }
  },
  methods: {
    daumPostcode() {
      const _this = this;
      new window.daum.Postcode({
        oncomplete: (data) => {

          // 각 주소의 노출 규칙에 따라 주소를 조합한다.
          // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
          let addr = ''; // 주소 변수

          //사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
          if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
            addr = data.roadAddress;
          } else { // 사용자가 지번 주소를 선택했을 경우(J)
            addr = data.jibunAddress;
          }

          // 주소 정보를 해당 필드에 넣는다.
          _this.userinfo.address = addr;

          // 커서를 상세주소 필드로 이동한다.
          _this.$refs.addRef.focus();
          _this.$nextTick(() => {
            _this.$refs.addDetailRef.focus();
          });
        }
      }).open();
    },
    dupOrMod() {
      if (!this.isDupName) {
        this.isCheckNameDup = false;
        this.isDupName = true;
        this.userinfo.name = '';
        this.nameCheckBtnMsg = '중복 확인';
      } else {
        this.checkDup();
      }
    },
    checkDup() {
      if (this.userinfo.name.length < 2) {
        Swal.fire({
          title: '이름을 2자 이상으로 입력해 주세요',
          icon: 'error'
        });
        return
      }

      if (this.userinfo.name.length > 10) {
        Swal.fire({
          title: '이름을 10자 이하로 입력해 주세요',
          icon: 'error'
        });
        return
      }

      this.isCheckNameDup = true;
      axios.post(this.$store.state.url + 'dup-name', this.userinfo)
          .then(response => {
            this.nameDupMsg = response.data.message;

            if (response.data.state) {
              this.isDupName = false;
              this.nameCheckBtnMsg = '수정하기'
            }

          })
          .catch(error => {
            console.log(error);
          })
    },
    register() {

      //이름 중복확인 완료 검사
      if (this.isDupName) {
        this.$refs.nameRef.focus();
        Swal.fire({
          title: '이름(닉네임) 중복 검사를 완료해 주세요',
          icon: 'error'
        });
        return
      }

      //이메일 유효성 검사
      const emailPattern = new RegExp('^\\w+@[a-zA-Z_]+?\\.[a-zA-Z]{2,3}$');
      let isEmailValid = emailPattern.test(this.userinfo.email)

      if (!isEmailValid) {
        this.$refs.eRef.focus();
        Swal.fire({
          title: '올바르지 않은 이메일 형식입니다',
          text: 'id@email.com 형식으로 입력해 주세요',
          icon: 'error'
        });
        return
      }

      //연락처 유효성 검사
      const telPattern = new RegExp('^[0-1]{3}\\d{8}$');
      let isTelValid = telPattern.test(this.userinfo.tel);

      if (!isTelValid) {
        this.$refs.telRef.focus();
        Swal.fire({
          title: '올바르지 않은 연락처 형식입니다',
          text: '010-1234-5678 형식으로 입력해 주세요',
          icon: 'error'
        });
        return
      }

      //생년월일 유효성 검사(YYYY-mm-DD)
      const bdPattern = new RegExp('^([0-9]{0,4})-([0-9]{0,2})-([0-9]{0,2})$');
      let isBdValid = bdPattern.test(this.userinfo.birth);

      if (!isBdValid) {
        this.$refs.bdRef.focus();
        Swal.fire({
          title: '올바르지 않은 생년월일 형식입니다',
          text: '연도(4자리)-월(2자리)-일(2자리) 형식으로 입력해 주세요',
          icon: 'error'
        });
        return
      }

      axios.post(this.$store.state.url + 'snsregister', this.userinfo)
          .then(response => {

            if (response.data.state) {
              Swal.fire({
                icon: 'success',
                title: response.data.message,
              });

              this.getUserinfo();

              this.$router.push({name: 'main'});
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
              title: '회원가입에 실패했습니다',
            });
          });

    },
    getUserinfo() {
      axios.post(this.$store.state.url + 'login', this.userinfo)
          .then(response => {
            if (response.data.state) {
              Swal.fire({
                icon: 'success',
                title: response.data.message,
                timer: 2000,
              }).then(() => {
                sessionStorage.setItem('name', response.data.result.name);
                sessionStorage.setItem('id', this.userinfo.id);
                location.href = '/';
                //소셜 로그인의 경우 소셜 로그인이라는 상태 저장
                sessionStorage.setItem('social', true);
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
              title: '로그인에 실패했습니다',
            });
          })
    }
  }
}

</script>

<style scoped>
section {
  border: 1px solid #e3e3e3;
  border-radius: 10px;
}
</style>