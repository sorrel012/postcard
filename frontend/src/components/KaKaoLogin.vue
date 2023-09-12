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
        client_id: process.env.VUE_APP_KAKAO_REST_KEY,
        redirect_uri: process.env.VUE_APP_KAKAO_REDIRECT_URI,
        code: this.$route.query.code,
        grant_type : "authorization_code",
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

    const tokenUrl = 'https://kauth.kakao.com/oauth/token';
    const tokenConfig = {
      headers: {
        'Content-type' : 'application/x-www-form-urlencoded;charset=utf-8'
      }
    }

    await axios.post(tokenUrl, new URLSearchParams(this.tokenParams).toString(), tokenConfig)
        .then(response => {
          this.token.access_token = response.data.access_token;
          this.token.refresh_token = response.data.refresh_token;
        })
        .catch(error => {
          console.log(error);
        })

    const userUrl = 'https://kapi.kakao.com/v2/user/me';
    const userConfig = {
      headers: {
        'Authorization' : `Bearer ${this.token.access_token}`,
        'Content-type' : 'application/x-www-form-urlencoded;charset=utf-8'
      }
    }

    await axios.post(userUrl, {}, userConfig)
        .then(response => {
          this.$store.commit('setSnsUserId', response.data.id)
          this.$store.commit('setSnsUserEmail', response.data.kakao_account.email)

          this.userinfo.id = response.data.id;

          axios.post(this.$store.state.url + 'dup', this.userinfo)
              .then(response => {
                console.log(response.data.result.length);
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
    }
  }
}
</script>

<style>

</style>