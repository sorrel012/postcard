<template>
  <div class=" mt-4 mb-2" :class="{'d-none':isRedirect}">
    <div>
      <img src="@/assets/kakao_login.png" alt="kakao" @click="socialLogin($event)" class="w-25 mb-2" id="kakao">
    </div>
    <div>
      <img src="@/assets/naver_login.png" alt="naver" @click="socialLogin($event)" class="w-25 mb-2" id="naver">
    </div>
    <div>
      <img src="@/assets/google_login.png" alt="naver" @click="socialLogin($event)" class="w-25" id="google">
    </div>

    <div class="mt-5" :class="{'d-none':!isRedirect}">
      <img src="@/assets/banner.png" class="img-fluid" alt="main-banner">
    </div>
  </div>
</template>

<script>
import axios from 'axios';
import Swal from "sweetalert2";

export default {
  name: 'SnsLogin',
  data() {
    return {
      kakaoLoginParams: {
        client_id: process.env.VUE_APP_KAKAO_REST_KEY,
        redirect_uri: process.env.VUE_APP_SNS_REDIRECT_URI,
        response_type: 'code',
        scope: 'account_email',
        state: '',
      },
      naverLoginParams: {
        client_id: process.env.VUE_APP_NAVER_CLIENT_ID,
        redirect_uri: process.env.VUE_APP_SNS_REDIRECT_URI,
        response_type: 'code',
        state: '',
      },
      googleLoginParams: {
        client_id: process.env.VUE_APP_GOOGLE_CLIENT_ID,
        redirect_uri: process.env.VUE_APP_SNS_REDIRECT_URI,
        response_type: 'code',
        scope: 'email',
        state: '',
      },
      kakaoTokenParams: {
        client_id: process.env.VUE_APP_KAKAO_REST_KEY,
        code: this.$route.query.code,
        grant_type : 'authorization_code',
      },
      naverTokenParams: {
        client_id: process.env.VUE_APP_NAVER_CLIENT_ID,
        client_secret: process.env.VUE_APP_NAVER_CLIENT_SECRET_ID,
        code: this.$route.query.code,
        grant_type : 'authorization_code',
        state: this.generateEncodedState(),
      },
      googleTokenParams: {
        client_id: process.env.VUE_APP_GOOGLE_CLIENT_ID,
        client_secret: process.env.VUE_APP_GOOGLE_CLIENT_SECRET_PW,
        code: this.$route.query.code,
        grant_type : 'authorization_code',
        redirect_uri: process.env.VUE_APP_SNS_REDIRECT_URI
      },
      token: {
        access_token: '',
        refresh_token: '',
      },
      userinfo: {
        id: '',
      },
      isRedirect: false,
      btnId: '',
    }
  },
  async created() {

    if(this.$route.query.state != undefined) {
      this.isRedirect = true;

      //btn Id값 디코딩
      const decodedState = atob(this.$route.query.state);
      const stateObj = JSON.parse(decodedState);
      const btnId = stateObj.btnId;
      this.btnId = btnId;

      // 토큰 값 얻어오기
      const tokenConfig = {
        headers: {
          'btnType' : btnId
        }
      }

      switch (btnId) {
        case 'kakao':
          await this.getToken(tokenConfig, this.kakaoTokenParams);
          break;
        case 'naver':
          await this.getToken(tokenConfig, this.naverTokenParams);
          break;
        case 'google':
          await this.getToken(tokenConfig, this.googleTokenParams);
          break;
        default:
          console.error('로그인 토큰을 얻어오지 못했습니다.');
      }


      //유저 정보 가져오기
      const userConfig = {
        headers: {
          'Authorization' : `Bearer ${this.token.access_token}`,
          'Content-type' : 'application/x-www-form-urlencoded;charset=utf-8',
          'btnType' : btnId
        }
      }

      switch (btnId) {
        case 'kakao':
          await this.getSnsInfo(btnId, userConfig);
          break;
        case 'naver':
          await this.getSnsInfo(btnId, userConfig);
          break;
        case 'google':
          await this.getSnsInfo(btnId, userConfig);
          break;
        default:
          console.error('회원정보를 가져오지 못했습니다.');
      }

    }
  },
  methods: {
    socialLogin(ev) {
      const btnId = ev.target.id;

      //btn id값을 redirect uri로 이동했을 때 알 수 있게 인코딩해서 보내기
      const stateObj = {
        originalState: this.generateEncodedState(),
        btnId: btnId
      };
      const encodedState = btoa(JSON.stringify(stateObj));

      switch (btnId) {
        case 'kakao':
          this.kakaoLoginParams.state = encodedState;
          this.kakaoLogin();
          break;
        case 'naver':
          this.naverLoginParams.state = encodedState;
          this.naverLogin();
          break;
        case 'google':
          this.googleLoginParams.state = encodedState;
          this.googleLogin();
          break;
        default:
          console.error('정의되지 않은 사이트 로그인 시도입니다.');
      }
    },
    kakaoLogin() {
      const url = 'https://kauth.kakao.com/oauth/authorize';
      location.href = `${url}?client_id=${this.kakaoLoginParams.client_id}&redirect_uri=${this.kakaoLoginParams.redirect_uri}&response_type=${this.kakaoLoginParams.response_type}&state=${this.kakaoLoginParams.state}`;
    },
    naverLogin() {
      const url = 'https://nid.naver.com/oauth2.0/authorize';
      location.href = `${url}?client_id=${this.naverLoginParams.client_id}&redirect_uri=${this.naverLoginParams.redirect_uri}&response_type=${this.naverLoginParams.response_type}&state=${this.naverLoginParams.state}`;
    },
    googleLogin() {
      const url = 'https://accounts.google.com/o/oauth2/v2/auth';
      location.href = `${url}?client_id=${this.googleLoginParams.client_id}&redirect_uri=${this.googleLoginParams.redirect_uri}&response_type=${this.googleLoginParams.response_type}&scope=${this.googleLoginParams.scope}&state=${this.googleLoginParams.state}`;
    },
    generateRandomState() {
      return Math.random().toString(36).substring(2, 15) + Math.random().toString(36).substring(2, 15);
    },
    generateEncodedState() {
      return encodeURIComponent(this.generateRandomState());
    },
    async getToken(header, tokenParams) {
      await axios.post(this.$store.state.url+'snslogin', new URLSearchParams(tokenParams).toString(), header)
          .then(response => {
            this.token.access_token = response.data.result.access_token;
            this.token.refresh_token = response.data.result.refresh_token;
          })
          .catch(error => {
            console.log(error);
          })
    },
    async getSnsInfo(btnId, header) {
      await axios.get(this.$store.state.url+'snslogin', header)
          .then(response => {
            if(btnId === 'naver') {
              this.$store.commit('setSnsUserId', response.data.result.response.id);
              this.$store.commit('setSnsUserEmail', response.data.result.response.email);
              this.userinfo.id = response.data.result.response.id;
            } else if(btnId === 'kakao') {
              this.$store.commit('setSnsUserId', response.data.result.id);
              this.$store.commit('setSnsUserEmail', response.data.result.kakao_account.email);
              this.userinfo.id = response.data.result.id;
            } else if(btnId === 'google') {
              this.$store.commit('setSnsUserId', response.data.result.id);
              this.$store.commit('setSnsUserEmail', response.data.result.email);
              this.userinfo.id = response.data.result.id;
            }

            axios.post(this.$store.state.url + 'dup', this.userinfo)
                .then(response => {
                  if(response.data.result.length == 0) {
                    this.$router.push({name: 'sns-register'})
                  } else {
                    sessionStorage.setItem('id', this.userinfo.id);
                    this.getUserinfo();
                  }
                })
                .catch(error => {
                  console.log(error);
                })

          })
          .catch(error => {
            console.log(error);
            Swal.fire({
              title: '로그인에 실패했습니다.',
              icon: 'error'
            });
          })
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
                sessionStorage.setItem('no', response.data.result.m_seq);

                //소셜 로그인의 경우 소셜 로그인이라는 상태와 종류 저장
                sessionStorage.setItem('social', true);
                sessionStorage.setItem('socialType', this.btnId);

                //액세스 토큰 인코딩하여 저장
                const encodedAccessToken = btoa(JSON.stringify(this.token.access_token));
                sessionStorage.setItem('access_token', encodedAccessToken);

                location.href = '/';
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
              title: '로그인에 실패했습니다.',
            });
          })
    },
  }
}
</script>

<style>
.mt-5 {
  margin-top: 7rem !important;
}
img:hover {
  cursor: pointer;
}
</style>
