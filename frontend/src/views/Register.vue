<template>
  <section class="container-md d-flex justify-content-center p-5 mt-4 mb-4">

    <div class="col-sm-12 col-md-12 col-lg-5 d-flex align-items-center rounded-end me-5 w-75 h-100 p-3">

      <div class="container-md d-flex flex-column align-items-center">

        <h1 class="h2 text-center fw-bold title">개인 회원가입</h1>
        <p class="fs-6 text-center mb-4">회원가입 후 다양한 서비스를 이용하세요.</p>

        <form class="w-100 mt-3" @submit.prevent="register" id="formregister">

          <div class="row mb-3">
            <div class="col-2 f-bold text-start d-flex align-items-center ps-4" style="height: 50px;">아이디</div>
            <div class="col-10">
              <div class="d-flex flex-column">
                <div class="form-floating btn-group w-100 mb-2">
                  <input type="text" class="form-control" :readonly="!isDupId" placeholder="" v-model="userinfo.id" ref="idRef" required>
                  <label for="floatingInput">6~12자로 입력해 주세요</label>
                  <input type="button" class="btn btn-primary" :value="idCheckBtnMsg" @click="dupOrMod('id')">
                </div>
                <div v-if="isCheckIdDup" class="form-floating btn-group w-50 flex-start ps-2" :class="{'c-red':isDupId, 'c-blue':!isDupId}">
                  {{idDupMsg}}
                </div>
              </div>
            </div>
          </div>

          <div class="row mb-4">
            <div class="col-2 f-bold text-start d-flex align-items-center ps-4">비밀번호</div>
            <div class="col-10">
              <div class="form-floating">
                <input type="password" class="form-control" placeholder="" required v-model="userinfo.pw" ref="pwRef">
                <label for="floatingInput">8~15자로 입력해 주세요 (알파벳, 숫자 필수)</label>
              </div>
            </div>
          </div>
          <div class="row">
            <div class="col-2 f-bold text-start d-flex align-items-center ps-4">비밀번호 확인</div>
            <div class="col-10">
              <div class="form-floating">
                <input type="password" class="form-control" placeholder="" required v-model="correctPw" ref="pwCorrectRef">
                <label for="floatingInput">비밀번호를 한 번 더 입력해 주세요</label>
              </div>
            </div>
          </div>
          <div class="row">
            <div class="col-2"></div>
            <div class="col-10">
              <div v-if="correctPw.length>0" class="mt-2 text-start ps-2" :class="{'c-red':!isCorrect, 'c-blue':isCorrect}">{{matchMsg}}</div>
            </div>
          </div>

          <div class="row mb-4 mt-4">
            <div class="col-2 f-bold text-start d-flex align-items-center ps-4" style="height: 50px;">이름(닉네임)</div>
            <div class="col-10">
              <div class="d-flex flex-column">
                <div class="form-floating btn-group w-100 mb-2">
                  <input type="text" class="form-control" :readonly="!isDupName" placeholder="" v-model="userinfo.name" ref="nameRef" required>
                  <label for="floatingInput">이름은 10자까지 입력할 수 있습니다</label>
                  <input type="button" class="btn btn-primary" :value="nameCheckBtnMsg" @click="dupOrMod('name')">
                </div>
                <div v-if="isCheckNameDup" class="form-floating btn-group w-50 flex-start ps-2" :class="{'c-red':isDupName, 'c-blue':!isDupName}">
                  {{nameDupMsg}}
                </div>
              </div>
            </div>
          </div>

          <div class="row mb-4">
            <div class="col-2 f-bold text-start d-flex align-items-center ps-4">이메일</div>
            <div class="col-10">
              <div class="form-floating">
                <input type="text" class="form-control" placeholder="" required v-model="userinfo.email" ref="eRef">
                <label for="floatingInput">id@email.com</label>
              </div>
            </div>
          </div>

          <div class="row mb-4">
            <div class="col-2 f-bold text-start d-flex align-items-center ps-4" style="height: 48px;">연락처</div>
            <div class="col-10">
              <div class="d-flex flex-column">
                <div class="form-floating btn-group w-100 mb-2">
                  <input type="text" class="form-control" :readonly="isSuccess" placeholder="" required v-model="userinfo.tel" ref="telRef">
                  <label for="floatingInput">-빼고 입력해 주세요.</label>
                  <input type="button" class="btn btn-primary" :disabled="isSuccess"  :value="authMsg" @click="requireAuth">
                </div>
                <div class="form-floating btn-group w-50 flex-start" v-if="wantAuth">
                  <input type="text" class="form-control" placeholder="" v-model="userAuthCode" :readonly="isSuccess" ref="authRef">
                  <label for="floatingInput">인증번호를 입력해 주세요.</label>
                  <input type="button" class="btn btn-primary" value="인증" @click="confirmAuth" :disabled="isSuccess">
                </div>
                <div class="form-floating btn-group w-50 flex-start ps-2 mt-1" v-if="isAuth" :class="{'c-red':!isSuccess, 'c-blue':isSuccess}">
                  {{confirmMsg}}
                </div>
              </div>
            </div>
          </div>

          <div class="row mb-4">
            <div class="col-2 f-bold text-start d-flex align-items-center ps-4">생년월일</div>
            <div class="col-10">
              <div class="form-floating">
                <input type="date" class="form-control" placeholder="" required v-model="userinfo.birth" ref="bdRef">
                <label for="floatingInput">날짜를 골라주세요</label>
              </div>
            </div>
          </div>

          <div class="row mb-4">
            <div class="col-2 f-bold text-start d-flex align-items-center ps-4" style="height: 48px;">주소</div>
            <div class="col-10">
              <div class="form-floating btn-group mb-2 w-100" >
                <input type="text" class="form-control" placeholder="" required readonly v-model="userinfo.address" ref="addRef">
                <label for="floatingInput">주소를 검색해 주세요</label>
                <input type="button" class="btn btn-primary" value="주소 검색" @click="daumPostcode">
              </div>
              <div class="form-floating">
                <input type="text" class="form-control" placeholder="" required v-model="userinfo.addressDetail" ref="addDetailRef">
                <label for="floatingInput">상세 주소를 입력해 주세요</label>
              </div>
            </div>
          </div>

          <div class="container-fluid p-0 mt-3">
            <button type="submit" class="btn btn-primary mt-4 w-100">회원가입</button>
            <div class="mt-2">* 아이디와 이름(닉네임)은 가입 후 변경할 수 없습니다.</div>
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
        addressDetail: '',
        email: '',
      },
      authMsg: '본인 인증',
      confirmMsg: '',                 //본인인증 성공 여부 안내 메시지
      authCode: '',                   //발송된 인증번호
      userAuthCode: '',               //사용자가 입력한 인증번호
      wantAuth: false,                //본인인증 버튼 클릭 여부
      isAuth: false,                  //본인인증 확인 버튼 클릭 여부
      isSuccess: false,               //본인인증 성공 여부
      isCheckIdDup: false,            //아이디 중복체크 버튼 클릭 여부
      isDupId: true,                  //아이디 중복 여부
      idCheckBtnMsg: '중복 확인',      //아이디 중복확인 버튼 문구
      isCheckNameDup: false,          //이름 중복확인 버튼 클릭 여부
      isDupName: true,                //이름 중복 여부
      nameCheckBtnMsg: '중복 확인',    //이름 중복확인 버튼 문구

      idDupMsg: '',                   //아이디 중복확인 결과
      nameDupMsg: '',                 //이름 중복확인 결과
      matchMsg: '',                   //비밀번호 확인 일치 여부 안내 문구
      correctPw: '',
      isCorrect: false,
    }
  },
  watch: {
    correctPw() {
      if(this.correctPw == this.userinfo.pw) {
        this.matchMsg = '비밀번호가 일치합니다'
        this.isCorrect = true;
      } else {
        this.matchMsg = '비밀번호가 일치하지 않습니다'
        this.isCorrect = false;
      }
    }
  },
  methods: {
    //아이디 중복확인을 눌렀을 때 실행
    dupOrMod(dupType) {
      if(dupType === 'id') {
        if(!this.isDupId) {
          this.isCheckIdDup = false;
          this.isDupId = true;
          this.userinfo.id = '';
          this.idCheckBtnMsg = '중복 확인';
        } else {
          this.checkDup('id');
        }
      } else if(dupType === 'name') {
        if(!this.isDupName) {
          this.isCheckNameDup = false;
          this.isDupName = true;
          this.userinfo.name = '';
          this.nameCheckBtnMsg = '중복 확인';
        } else {
          this.checkDup('name');
        }
      }
    },
    checkDup(dupType) {
      if(dupType === 'id') {

        if(this.userinfo.id.length < 6) {
          Swal.fire({
            title: '아이디를 6자 이상으로 입력해 주세요',
            icon: 'error'
          });
          return
        }

        if(this.userinfo.id.length > 12) {
          Swal.fire({
            title: '아이디를 12자 이하로 입력해 주세요',
            icon: 'error'
          });
          return
        }

        this.isCheckIdDup = true;
        axios.post(this.$store.state.url + 'dup-id', this.userinfo)
            .then(response => {
              this.idDupMsg = response.data.message;

              if(response.data.state) {
                this.isDupId = false;
                this.idCheckBtnMsg = '수정하기'
              }

            })
            .catch(error => {
              console.log(error);
            })
      } else if(dupType === 'name') {

        if(this.userinfo.name.length < 2) {
          Swal.fire({
            title: '이름을 2자 이상으로 입력해 주세요',
            icon: 'error'
          });
          return
        }

        if(this.userinfo.name.length > 10) {
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

              if(response.data.state) {
                this.isDupName = false;
                this.nameCheckBtnMsg = '수정하기'
              }

            })
            .catch(error => {
              console.log(error);
            })
      }
    },
    requireAuth() {
      if(this.userinfo.tel.length != 11) {
        Swal.fire({
          title: '연락처를 입력해 주세요',
          icon: 'error'
        });
        return
      }

      this.wantAuth = true;
      this.makeAuthCode();
      const authConfig = {
        to: this.userinfo.tel,
        content: this.authCode
      }
      axios.post(this.$store.state.url + 'auth', authConfig)
          .then(result => {
            if(result.data.state) {
              console.log(result.data.message);
            }
          }).catch(error => {
            console.log(error);
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
    daumPostcode() {
      const _this = this;
      new window.daum.Postcode({
        oncomplete:(data) => {

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
    register() {

      //본인인증 하지 않았을 경우
      if(!this.wantAuth) {
        this.$refs.telRef.focus();
        Swal.fire({
          title: '본인 인증을 완료해 주세요',
          icon: 'error'
        })
        return
      }

      //아이디 중복확인 완료 검사
      if(this.isDupId) {
        this.$refs.idRef.focus();
        Swal.fire({
          title: '아이디 중복 검사를 완료해 주세요',
          icon: 'error'
        });
        return
      }

      //이름 중복확인 완료 검사
      if(this.isDupName) {
        this.$refs.nameRef.focus();
        Swal.fire({
          title: '이름(닉네임) 중복 검사를 완료해 주세요',
          icon: 'error'
        });
        return
      }

      //비밀번호 유효성 검사(숫자+문자, 8-12글자)
      const pwPattern = new RegExp('^(?=.*[A-Za-z])(?=.*\\d).{8,15}$');
      let isPwValid = pwPattern.test(this.userinfo.pw);

      if(!isPwValid) {
        this.$refs.pwRef.focus();
        Swal.fire({
          title: '올바르지 않은 비밀번호 형식입니다',
          text: '숫자와 알파벳을 포함하여 8-15자로 입력해 주세요',
          icon: 'error'
        });
        return
      }

      //비밀번호와 비밀번호 확인 일치 검사
      if(!this.isCorrect) {
        this.$refs.pwCorrectRef.focus();
        Swal.fire({
          title: '비밀번호 확인이 일치하지 않습니다',
          icon: 'error'
        });
        return
      }

      //이름 길이 검사
      const nameLen = this.userinfo.name.length;
      if(nameLen > 10 || nameLen < 2) {
        Swal.fire({
          title: '올바르지 않은 이름(닉네임) 형식입니다',
          text: '2-10글자로 입력해 주세요',
          icon: 'error'
        });
      }

      //이름 중복 검사


      //이메일 유효성 검사
      const emailPattern = new RegExp('^\\w+@[a-zA-Z_]+?\\.[a-zA-Z]{2,3}$');
      let isEmailValid = emailPattern.test(this.userinfo.email);

      if(!isEmailValid) {
        this.$refs.eRef.focus();
        Swal.fire({
          title: '올바르지 않은 이메일 형식입니다',
          text: 'id@email.com 형식으로 입력해 주세요',
          icon: 'error'
        });
        return
      }

      //문자 본인 인증 완료 검사
      if(!this.isAuth) {
        this.$refs.authRef.focus();
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
        this.$refs.bdRef.focus();
        Swal.fire({
          title: '올바르지 않은 생년월일 형식입니다',
          text: '연도(4자리)-월(2자리)-일(2자리) 형식으로 입력해 주세요',
          icon: 'error'
        });
        return
      }

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
              title: '회원가입에 실패했습니다',
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