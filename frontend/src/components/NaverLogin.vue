<template>
  <div></div>
</template>

<script>
import axios from 'axios';
import Swal from "sweetalert2";

export default {
  name: 'KakaoLogin',
  data() {
    return {
      tokenParams: {
        client_id: process.env.VUE_APP_NAVER_CLIENT_ID,
        client_secret: process.env.VUE_APP_NAVER_CLIENT_SECRET_ID,
        code: this.$route.query.code,
        grant_type : 'authorization_code',
        state: this.generateEncodedState(),
      },
      token: {
        access_token: '',
        refresh_token: '',
      },
      userinfo: {
        id: '',
      }
    }
  },
  async created() {

    await axios.post(this.$store.state.url+'naverlogin', new URLSearchParams(this.tokenParams).toString())
        .then(response => {
          this.token.access_token = response.data.result.access_token;
          this.token.refresh_token = response.data.result.refresh_token;
        })
        .catch(error => {
          console.log(error);
        })

    const userConfig = {
      headers: {
        'Authorization' : `Bearer ${this.token.access_token}`,
      }
    }

    await axios.get(this.$store.state.url+'naverlogin', userConfig)
        .then(response => {
          this.$store.commit('setSnsUserId', response.data.result.response.id)
          this.$store.commit('setSnsUserEmail', response.data.result.response.email)

          this.userinfo.id = response.data.result.response.id;

          axios.post(this.$store.state.url + 'dup', this.userinfo)
              .then(response => {
                if(response.data.result.length == 0) {
                  this.$router.push({name: 'sns'})
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
  methods: {
    getUserinfo() {
      axios.post(this.$store.state.url + 'login', this.userinfo)
          .then(response => {
            console.log(response);
            if (response.data.state) {
              Swal.fire({
                icon: 'success',
                title: response.data.message,
                timer: 2000,
              }).then(() => {
                sessionStorage.setItem('name', response.data.result.name);
                sessionStorage.setItem('no', response.data.result.m_seq);
                //소셜 로그인의 경우 소셜 로그인이라는 상태 저장
                sessionStorage.setItem('social', true);
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
    generateRandomState() {
      return Math.random().toString(36).substring(2, 15) + Math.random().toString(36).substring(2, 15);
    },
    generateEncodedState() {
      return encodeURIComponent(this.generateRandomState());
    },
  }
}
</script>

<style>

</style>
