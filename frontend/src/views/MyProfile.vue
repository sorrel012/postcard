<template>
  <div class="container-fluid row d-lg-flex align-items-sm-start p-5 pt-4 pb-3">

    <my-page-navbar/>
    <my-page-sidebar/>

    <div class="col-9 container-lg">
      <div class="container-lg mb-4 p-0">
        <h3 class="h3 mb-3 pb-2 fw-bold border-bottom border-2 border-dark">프로필</h3>
      </div>
      <form class="form-control p-4 pb-4 bg-secondary bg-opacity-10 p-5" @submit.prevent="updateInfo">
        <div class="row mb-4 pe-3">
          <div class="col-2 f-bold text-start d-flex align-items-center ps-4">아이디</div>
          <div class="col-10">
            <input type="text" class="form-control" disabled :value="newUserinfo.id">
          </div>
        </div>
        <div class="row mb-4 pe-3">
          <div class="col-2 f-bold text-start d-flex align-items-center ps-4">이름(닉네임)</div>
          <div class="col-10">
            <input type="text" class="form-control" disabled :value="newUserinfo.name">
          </div>
        </div>
        <div class="row mb-4 pe-3">
          <div class="col-2 f-bold text-start d-flex align-items-center ps-4">이메일</div>
          <div class="col-10">
            <input type="text" class="form-control" required v-model="newUserinfo.email" placeholder="id@email.com">
          </div>
        </div>
        <div class="row mb-4 pe-3">
          <div class="col-2 f-bold text-start d-flex align-items-center ps-4">생년월일</div>
          <div class="col-10">
            <input type="date" class="form-control" required v-model="newUserinfo.birth" placeholder="날짜를 골라주세요">
          </div>
        </div>
        <div class="row mb-4 pe-3">
          <div class="col-2 f-bold text-start d-flex align-items-center ps-4" style="height: 35px;">주소</div>
          <div class="col-10">
            <div class="btn-group mb-2 w-100" >
              <input type="text" class="form-control" placeholder="주소를 검색해 주세요" required readonly v-model="newUserinfo.address" ref="addRef">
              <input type="button" class="btn btn-primary" value="주소 검색" @click="daumPostcode">
            </div>
            <div class="">
              <input type="text" class="form-control" placeholder="상세 주소를 입력해 주세요" required v-model="newUserinfo.addressDetail" ref="addDetailRef">
            </div>
          </div>
        </div>
        <div class="row mb-4">
          <div class="col-2 f-bold text-start d-flex align-items-center ps-4" style="height: 35px;">연락처</div>
          <div class="col-10">
            <div class="d-flex flex-column pe-3">
              <div class="btn-group w-100 mb-2">
                <input type="text" class="form-control" placeholder="" required v-model="newUserinfo.tel" ref="telRef">
                <input type="button" class="btn btn-primary" value="본인 인증" @click="updateTel" v-if="!isSocial">
              </div>
              <div class="btn-group w-50 flex-start" v-if="wantAuth">
                <input type="text" class="form-control" placeholder="인증번호를 입력해 주세요." v-model="userAuthCode" :readonly="isSuccess" ref="authRef">
                <input type="button" class="btn btn-primary" value="인증" @click="confirmAuth" :disabled="isSuccess">
              </div>
              <div class="form-floating btn-group w-50 flex-start ps-2 mt-1" v-if="wantAuth" :class="{'c-red':!isSuccess, 'c-blue':isSuccess}">
                {{confirmMsg}}
              </div>
            </div>
          </div>
        </div>
        <div class="mt-4 text-end pe-3">
          <button type="submit" class="btn btn-primary" style="width: 80px;">수정</button>
        </div>
      </form>

      <form class="form-control p-4 pb-4 bg-secondary bg-opacity-10 p-5 pt-4 mt-3" @submit.prevent="updatePw" v-if="!isSocial">
        <div class="mt-4">
          <div class="w-100 pe-2">
            <p class="m-0 mb-4 w-auto fw-bold">비밀번호 변경</p>
            <input class="form-control mb-2" type="password" placeholder="현재 비밀번호" v-model="currentPw" ref="currentPwRef" required>
            <div class="mb-2 text-start ps-2" :class="{'c-red':!isCorrect, 'c-blue':isCorrect}" v-if="currentPwMsg !== ''">{{currentPwMsg}}</div>
            <input class="form-control mb-2" type="password" placeholder="새 비밀번호" v-model="newPw" ref="pwRef" required>
            <input class="form-control" type="password" placeholder="새 비밀번호 확인" v-model="pwCheck" ref="pwCorrectRef" required>
            <div class="mt-1 mb-4 text-start ps-2" :class="{'c-red':!isSame, 'c-blue':isSame}">{{correctPwMsg}}</div>
          </div>
        </div>
        <div class="mt-4 text-end pe-2">
          <button type="submit" class="btn btn-primary" style="width: 80px;">수정</button>
        </div>
      </form>

      <div class="text-end">
        <button class="btn btn-danger mt-4 btn-md" @click="deleteAccount">회원 탈퇴</button>
      </div>

    </div>

  </div>

</template>

<script>
import axios from 'axios'
import Swal from "sweetalert2";
import _ from 'lodash';

import MyPageSidebar from '@/components/MypageSidebar.vue';
import MyPageNavbar from "@/components/MyPageNavbar.vue";

export default {
  name: 'MyProfile',
  data() {
    return {
      userinfo: {
        id: '',
        name: '',
        pw: '',
        email: '',
        address: '',
        addressDetail: '',
        tel: '',
        birth: '',
      },
      newUserinfo: {},
      isSocial: true,
      userAuthCode: '',
      wantAuth: false,
      isSuccess: false,
      confirmMsg: '',
      currentPw: '',
      isCorrect: false,
      pwCheck: '',
      newPw:'',
      isSame: false,
      currentPwMsg: '',
      correctPwMsg: '',
      naverDisconnectParams: {
        client_id: process.env.VUE_APP_NAVER_CLIENT_ID,
        client_secret: process.env.VUE_APP_NAVER_CLIENT_SECRET_ID,
        grant_type: 'delete',
        access_token: '',
        service_provider: 'NAVER'
      },
    }
  },
  components: {
    MyPageSidebar,
    MyPageNavbar
  },
  created() {

    const social = sessionStorage.getItem('social');
    if(social == null || social === '') {
      this.isSocial = false;
    }

    this.userinfo.id = sessionStorage.getItem('id');
    this.userinfo.name = sessionStorage.getItem('name');

    axios.post(this.$store.state.url + 'login', this.userinfo)
        .then(response => {
          const result = response.data.result;

          this.userinfo.pw = result.pw;
          this.userinfo.email = result.email;
          this.userinfo.address = result.address;
          this.userinfo.addressDetail = result.addressDetail;
          this.userinfo.birth = result.birth;
          this.userinfo.tel = result.tel;
          this.newUserinfo = _.cloneDeep(this.userinfo);
        })
        .catch(error => {
          console.log(error);
        })
  },
  watch: {
    currentPw() {
      if(this.currentPw === '') {
        this.currentPwMsg = '';
      } else if(this.currentPw === this.userinfo.pw) {
        this.currentPwMsg = '비밀번호가 일치합니다'
        this.isCorrect = true;
      } else {
        this.currentPwMsg = '비밀번호가 일치하지 않습니다'
        this.isCorrect = false;
      }
    },
    pwCheck() {
      if(this.pwCheck === '') {
        this.correctPwMsg = '';
      } else if(this.newPw === this.pwCheck) {
        this.correctPwMsg = '비밀번호 확인이 일치합니다'
        this.isSame = true;
      } else {
        this.correctPwMsg = '비밀번호 확인이 일치하지 않습니다'
        this.isSame = false;
      }
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
          this.userinfo.addressDetail = '';
          _this.$refs.addRef.focus();
          _this.$nextTick(() => {
            _this.$refs.addDetailRef.focus();
          });
        }
      }).open();
    },
    updateTel() {
      if(this.userinfo.tel.length !== 11) {
        Swal.fire({
          title: '연락처를 입력해 주세요',
          icon: 'error'
        });
        return
      }

      //연락처가 바뀌었는지 확인
      if(this.userinfo.tel === this.newUserinfo.tel) {
        Swal.fire({
          title: '연락처가 변경되지 않았습니다',
          text: '변경 후 다시 시도해 주세요',
          icon: 'error'
        });
        return
      }

      this.wantAuth = true;
      this.makeAuthCode();
      const authConfig = {
        to: this.newUserinfo.tel,
        content: this.authCode
      }
      axios.post(this.$store.state.url + 'auth', authConfig)
          .then(result => {
            if(result.data.state) {
              console.log(result.data.message);
            }
          })
          .catch(error => {
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
    updateInfo() {

      //변경사항이 있는지 확인
      const eChng = (this.userinfo.email === this.newUserinfo.email);
      const bChng = (this.userinfo.birth === this.newUserinfo.birth);
      const adChng = (this.userinfo.address === this.newUserinfo.address);
      const addChng = (this.userinfo.addressDetail === this.newUserinfo.addressDetail);
      const tChng = (this.userinfo.tel === this.newUserinfo.tel);

      if(eChng && bChng && adChng && addChng && tChng) {
        Swal.fire({
          title: '변경사항이 없습니다',
          icon: 'error'
        });
        return
      }

      //연락처를 변경하였으나 인증하지 않은 경우 확인(일반회원)
      if(!tChng && !this.wantAuth && !this.isSocial) {
        Swal.fire({
          title: '연락처 본인 인증을 진행해 주세요',
          icon: 'error'
        });
        return
      }

      //연락처를 변경 후 인증을 요청했으나 인증확인이 되지 않은 경우 확인(일반회원)
      if(this.wantAuth && !this.isSuccess && !this.isSocial) {
        Swal.fire({
          title: '연락처 본인 인증을 완료해 주세요',
          icon: 'error'
        });
        return
      }

      //이메일 유효성 검사
      const emailPattern = new RegExp('^\\w+@[a-zA-Z_]+?\\.[a-zA-Z]{2,3}$');
      let isEmailValid = emailPattern.test(this.newUserinfo.email)

      if(!isEmailValid) {
        this.$refs.eRef.focus();
        Swal.fire({
          title: '올바르지 않은 이메일 형식입니다',
          text: 'id@email.com 형식으로 입력해 주세요',
          icon: 'error'
        });
        return
      }

      //생년월일 유효성 검사(YYYY-mm-DD)
      const bdPattern = new RegExp('^([0-9]{0,4})-([0-9]{0,2})-([0-9]{0,2})$');
      let isBdValid = bdPattern.test(this.newUserinfo.birth);

      if(!isBdValid) {
        this.$refs.bdRef.focus();
        Swal.fire({
          title: '올바르지 않은 생년월일 형식입니다',
          text: '연도(4자리)-월(2자리)-일(2자리) 형식으로 입력해 주세요',
          icon: 'error'
        });
        return
      }

      //저장
      axios.put(this.$store.state.url + 'updateprofile', this.newUserinfo)
          .then(response => {
            if(response.data.state) {
              Swal.fire({
                title: '회원정보를 수정했습니다',
                icon: 'success'
              });
            } else {
              Swal.fire({
                title: '회원정보를 수정하지 못했습니다',
                icon: 'error'
              });
            }
          })
          .catch(error => {
            console.log(error);
          })

    },
    updatePw() {

      //비밀번호 유효성 검사(숫자+문자, 8-12글자) (일반회원)
      const pwPattern = new RegExp('^(?=.*[A-Za-z])(?=.*\\d).{8,15}$');
      let isPwValid = pwPattern.test(this.newPw);

      if(!this.isSocial && !isPwValid) {
        this.$refs.pwRef.focus();
        Swal.fire({
          title: '올바르지 않은 비밀번호 형식입니다',
          text: '숫자와 알파벳을 포함하여 8-15자로 입력해 주세요',
          icon: 'error'
        });
        return
      }

      //현재 비밀번호 일치 검사 (일반회원)
      if(!this.isSocial && !this.isCorrect) {
        this.$refs.currentPwRef.focus();
        Swal.fire({
          title: '현재 비밀번호가 일치하지 않습니다',
          icon: 'error'
        });
        return
      }

      //비밀번호와 비밀번호 확인 일치 검사 (일반회원)
      if(!this.isSocial && !this.isSame) {
        this.$refs.pwCorrectRef.focus();
        Swal.fire({
          title: '비밀번호 확인이 일치하지 않습니다',
          icon: 'error'
        });
        return
      }

      //기존 비밀번호와 새로운 비밀번호가 같은지 검사 (일반회원)
      if(!this.isSocial && this.newPw === this.userinfo.pw) {
        this.$refs.currentPwRef.focus();
        Swal.fire({
          title: '비밀번호가 기존과 동일합니다',
          icon: 'error'
        });
        return
      }

      this.newUserinfo.pw = this.newPw;

      //저장
      axios.put(this.$store.state.url + 'updatepw', this.newUserinfo)
          .then(response => {
            if(response.data.state) {
              Swal.fire({
                title: '비밀번호를 수정했습니다',
                icon: 'success',
                timer: 2000,
              }).then(() => {
                location.href='/myprofile';
              });
            } else {
              Swal.fire({
                title: '비밀번호를 수정하지 못했습니다',
                icon: 'error'
              });
            }
          })
          .catch(error => {
            console.log(error);
          })
    },
    deleteAccount() {
      Swal.fire({
        title: '탈퇴하시겠습니까?',
        text: "탈퇴 후 다시 되돌릴 수 없습니다",
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
        confirmButtonText: 'Delete',
        preConfirm: () => {
          return axios.delete(this.$store.state.url + 'deleteaccount', {params: {id : this.userinfo.id}})
              .catch(error => {
                console.log(error);
                Swal.showValidationMessage(
                    '탈퇴하지 못했습니다'
                )
              })
        }
      }).then( async result => {
        if (result.isConfirmed) {
          //소셜 회원 연결 끊기
          await this.disconnect();
          Swal.fire(
              '탈퇴 완료',
              '이용해 주셔서 감사합니다',
              'success',
              2000,
          ).then(async () => {
            if (this.isSocial) {
              sessionStorage.removeItem('id');
              sessionStorage.removeItem('name');
              sessionStorage.removeItem('no');
            } else {
              sessionStorage.removeItem('social');
              await this.disconnect();
            }
            //location.href = "/";
          })
        }
      })
    },
    async disconnect() {

      const accessToken = JSON.parse(atob(sessionStorage.getItem('access_token')));
      console.log(accessToken);
      const btnType = sessionStorage.getItem('socialType');

      const kakaoHeader = {
        headers: {
          'Authorization' : `Bearer ${accessToken}`
        }
      }

      if(btnType === 'kakao') {
        await axios.post(this.$store.state.url + 'disconnectKakao', {}, kakaoHeader);
      } else if(btnType === 'naver') {
        this.naverDisconnectParams.access_token = accessToken;
        await axios.post(this.$store.state.url+'disconnectNaver', new URLSearchParams(this.naverDisconnectParams).toString());
      } else if(btnType === 'google') {
        await axios.post(this.$store.state.url+'disconnectGoogle', new URLSearchParams({token: accessToken}).toString());
      }

    }
  }
}

</script>

<style scoped>
.form-control:disabled {
  background-color: #797979;
  color: white;
}
</style>