<template>
  <section class="container-md d-flex justify-content-center p-5 mt-5 mb-5">
    <main class="col-sm-12 col-md-12 col-lg-5 d-flex flex-column align-items-center rounded-end w-100 h-100 p-3">
      <header>
        <h1 class="h2 text-center fw-bold title">개인 회원가입</h1>
        <p class="fs-6 text-center mb-4">회원가입 후 다양한 서비스를 이용하세요.</p>
      </header>

      <form class="w-100 mt-3" @submit.prevent="register">
        <div class="row mb-3">
          <label class="col-2 f-bold text-start d-flex align-items-center ps-4" for="id"
                 style="height: 50px;">아이디</label>
          <div class="col-10">
            <div class="d-flex flex-column">
              <div class="form-floating btn-group w-100 mb-2">
                <input id="id" ref="idRef" v-model="userinfo.id" :readonly="!dupData.id.isDup" class="form-control"
                       placeholder="6~12자로 입력해 주세요"
                       required type="text">
                <label>6~12자로 입력해 주세요</label>
                <input :value="dupData.id.checkBtnMsg" class="btn btn-primary" type="button" @click="dupOrMod('id')">
              </div>
              <div v-if="dupData.id.isCheckDup" :class="{'c-red':dupData.id.isDup, 'c-blue':!dupData.id.isDup}"
                   class="form-floating btn-group w-50 flex-start ps-2">
                {{ dupData.id.dupMsg }}
              </div>
            </div>
          </div>
        </div>

        <div class="row mb-4">
          <label class="col-2 f-bold text-start d-flex align-items-center ps-4" for="pw">비밀번호</label>
          <div class="col-10">
            <div class="form-floating">
              <input id="pw" ref="pwRef" v-model="userinfo.pw" class="form-control"
                     placeholder="8~15자로 입력해 주세요 (알파벳, 숫자 필수)" required type="password">
              <label>8~15자로 입력해 주세요 (알파벳, 숫자 필수)</label>
            </div>
          </div>
        </div>
        <div class="row">
          <label class="col-2 f-bold text-start d-flex align-items-center ps-4" for="pwCheck">비밀번호 확인</label>
          <div class="col-10">
            <div class="form-floating">
              <input id="pwCheck" ref="pwCorrectRef" v-model="correctPw" class="form-control" placeholder=""
                     required type="password">
              <label for="floatingInput">비밀번호를 한 번 더 입력해 주세요</label>
            </div>
          </div>
        </div>
        <div class="row">
          <div class="col-2"></div>
          <div class="col-10">
            <div v-if="correctPw.length>0" :class="{'c-red':!isCorrect, 'c-blue':isCorrect}"
                 class="mt-2 text-start ps-2">{{ matchMsg }}
            </div>
          </div>
        </div>

        <div class="row mb-4 mt-4">
          <label class="col-2 f-bold text-start d-flex align-items-center ps-4" for="name"
                 style="height: 50px;">이름(닉네임)</label>
          <div class="col-10">
            <div class="d-flex flex-column">
              <div class="form-floating btn-group w-100 mb-2">
                <input id="name" ref="nameRef" v-model="userinfo.name" :readonly="!dupData.name.isDup"
                       class="form-control" placeholder="이름은 10자까지 입력할 수 있습니다" required type="text">
                <label>이름은 10자까지 입력할 수 있습니다</label>
                <input :value="dupData.name.checkBtnMsg" class="btn btn-primary" type="button"
                       @click="dupOrMod('name')">
              </div>
              <div v-if="dupData.name.isCheckDup" :class="{'c-red':dupData.name.isDup, 'c-blue':!dupData.name.isDup}"
                   class="form-floating btn-group w-50 flex-start ps-2">
                {{ dupData.name.dupMsg }}
              </div>
            </div>
          </div>
        </div>

        <div class="row mb-4">
          <label class="col-2 f-bold text-start d-flex align-items-center ps-4" for="email">이메일</label>
          <div class="col-10">
            <div class="form-floating">
              <input id="email" ref="eRef" v-model="userinfo.email" class="form-control" placeholder="id@email.com"
                     required type="email">
              <label>id@email.com</label>
            </div>
          </div>
        </div>

        <div class="row mb-4">
          <label class="col-2 f-bold text-start d-flex align-items-center ps-4" for="tel"
                 style="height: 48px;">연락처</label>
          <div class="col-10">
            <div class="d-flex flex-column">
              <div class="form-floating btn-group w-100 mb-2">
                <input id="tel" ref="telRef" v-model="userinfo.tel" :readonly="isSuccess" class="form-control"
                       placeholder="-빼고 입력해 주세요" required type="text">
                <label>-빼고 입력해 주세요</label>
                <input :disabled="isSuccess" :value="authMsg" class="btn btn-primary" type="button"
                       @click="requireAuth">
              </div>
              <div v-if="wantAuth" class="form-floating btn-group w-50 flex-start">
                <input ref="authRef" v-model="userAuthCode" :readonly="isSuccess" class="form-control"
                       placeholder="인증번호를 입력해 주세요"
                       type="text">
                <label>인증번호를 입력해 주세요</label>
                <input :disabled="isSuccess" class="btn btn-primary" type="button" value="인증" @click="confirmAuth">
              </div>
              <div v-if="isAuth" :class="{'c-red':!isSuccess, 'c-blue':isSuccess}"
                   class="form-floating btn-group w-50 flex-start ps-2 mt-1">
                {{ confirmMsg }}
              </div>
            </div>
          </div>
        </div>

        <div class="row mb-4">
          <label class="col-2 f-bold text-start d-flex align-items-center ps-4" for="birth">생년월일</label>
          <div class="col-10">
            <div class="form-floating">
              <input id="birth" ref="bdRef" v-model="userinfo.birth" class="form-control" placeholder="날짜를 골라주세요"
                     required type="date">
              <label>날짜를 골라주세요</label>
            </div>
          </div>
        </div>

        <div class="row mb-4">
          <label class="col-2 f-bold text-start d-flex align-items-center ps-4" for="address"
                 style="height: 48px;">주소</label>
          <div class="col-10">
            <div class="form-floating btn-group mb-2 w-100">
              <input id="address" ref="addRef" v-model="userinfo.address" class="form-control" placeholder="주소를 검색해 주세요"
                     readonly
                     required type="text">
              <label>주소를 검색해 주세요</label>
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
          <button class="btn btn-primary mt-4 w-100" type="submit">회원가입<span id="formAlert" role="alert"></span>
          </button>
          <div class="mt-2">* 아이디와 이름(닉네임)은 가입 후 변경할 수 없습니다.</div>
        </div>
      </form>
    </main>
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
      dupData: {
        id: {
          isCheckDup: false,        //중복 확인 클릭 여부
          isDup: true,              //중복 여부
          checkBtnMsg: '중복 확인',
          dupMsg: '',
          minLength: 6,
          maxLength: 12,
          apiUrl: 'dup-id',
        },
        name: {
          isCheckDup: false,
          isDup: true,
          checkBtnMsg: '중복 확인',
          dupMsg: '',
          minLength: 2,
          maxLength: 10,
          apiUrl: 'dup-name',
        }
      },
      matchMsg: '',                   //비밀번호 확인 일치 여부 안내 문구
      correctPw: '',
      isCorrect: false,
    }
  },
  watch: {
    correctPw() {
      if (this.correctPw == this.userinfo.pw) {
        this.matchMsg = '비밀번호가 일치합니다'
        this.isCorrect = true;
      } else {
        this.matchMsg = '비밀번호가 일치하지 않습니다'
        this.isCorrect = false;
      }
    }
  },
  methods: {
    dupOrMod(dupType) {
      let data = this.dupData[dupType];

      if (!data.isDup) {
        data.isCheckDup = false;
        data.isDup = true;
        this.userinfo[dupType] = '';
        data.checkBtnMsg = '중복 확인';

      } else {
        this.checkDup(dupType);
      }
    },
    checkDup(dupType) {
      let data = this.dupData[dupType];
      let typeNm = dupType === 'id' ? '아이디를' : '이름을';

      if (this.userinfo[dupType].length < data.minLength) {
        Swal.fire({
          title: `${typeNm} ${data.minLength}자 이상으로 입력해 주세요`,
          icon: 'error'
        });
        return;
      }

      if (this.userinfo[dupType].length > data.maxLength) {
        Swal.fire({
          title: `${typeNm} ${data.maxLength}자 이하로 입력해 주세요`,
          icon: 'error'
        });
        return;
      }

      data.isCheckDup = true;
      axios.post(this.$store.state.url + data.apiUrl, this.userinfo)
          .then(response => {
            data.dupMsg = response.data.message;

            if (response.data.state) {
              data.isDup = false;
              data.checkBtnMsg = '수정하기';
            }
          })
          .catch(error => {
            console.log(error);
          });
    },
    requireAuth() {
      if (this.userinfo.tel.length != 11) {
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
            console.log(result.data.message);
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
      this.authCode = authCode;
    },
    confirmAuth() {
      this.isAuth = true;
      if (this.authCode == this.userAuthCode) {
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
    register() {

      //본인인증 하지 않았을 경우
      if (!this.wantAuth) {
        this.$refs.telRef.focus();
        Swal.fire({
          title: '본인 인증을 완료해 주세요',
          icon: 'error'
        })
        return
      }

      //아이디 중복확인 완료 검사
      if (this.dupData.id.isDup) {
        this.$refs.idRef.focus();
        Swal.fire({
          title: '아이디 중복 검사를 완료해 주세요',
          icon: 'error'
        });
        return
      }

      //이름 중복확인 완료 검사
      if (this.dupData.name.isDup) {
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

      if (!isPwValid) {
        this.$refs.pwRef.focus();
        Swal.fire({
          title: '올바르지 않은 비밀번호 형식입니다',
          text: '숫자와 알파벳을 포함하여 8-15자로 입력해 주세요',
          icon: 'error'
        });
        return
      }

      //비밀번호와 비밀번호 확인 일치 검사
      if (!this.isCorrect) {
        this.$refs.pwCorrectRef.focus();
        Swal.fire({
          title: '비밀번호 확인이 일치하지 않습니다',
          icon: 'error'
        });
        return
      }

      //이름 길이 검사
      const nameLen = this.userinfo.name.length;
      if (nameLen > 10 || nameLen < 2) {
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

      if (!isEmailValid) {
        this.$refs.eRef.focus();
        Swal.fire({
          title: '올바르지 않은 이메일 형식입니다',
          text: 'id@email.com 형식으로 입력해 주세요',
          icon: 'error'
        });
        return
      }

      //문자 본인 인증 완료 검사
      if (!this.isAuth) {
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

      if (!isBdValid) {
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