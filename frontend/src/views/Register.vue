<template>
    <section class="container-md d-flex justify-content-center p-5 mt-5 h-75">
        
        <div class="col-sm-12 col-md-12 col-lg-5 p-0 d-flex align-items-center rounded-end me-5 w-75 h-100 p-5">

            <div class="container-md d-flex flex-column align-items-center">               
                
                <h1 class="h2 text-center fw-bold title">개인 회원가입</h1>
                <p class="fs-6 text-center mb-4">회원가입 후 다양한 서비스를 이용하세요.</p>
                
                <form class="w-100 mt-3" @submit.prevent="register" id="formregister">
                    <div class="mb-1 f-bold">아이디</div>
                    <div class="form-floating mb-3">
                        <input type="text" class="form-control" id="userid" placeholder="name@example.com" name="m_id" v-model="userinfo.id"> 
                        <label for="floatingInput">6~12자 이내로 입력해 주세요</label>
                    </div>
                        
                    <div class="mb-1 f-bold">비밀번호</div>
                        <div class="form-floating mb-3">
                        <input type="password" class="form-control" id="password" placeholder="Password" name="m_pw" required v-model="userinfo.pw"> 
                        <label for="floatingPassword">8~12자 이내로 입력해 주세요 (알파벳, 숫자 필수)</label>
                    </div>

                    <div class="mb-1 f-bold">이름</div>
                        <div class="form-floating mb-3">
                        <input type="text" class="form-control" id="name" placeholder="name" name="m_name" required v-model="userinfo.name"> 
                        <label for="floatingPassword" >이름을 입력해 주세요</label>
                    </div>
                    
                    <div class="mb-1 f-bold">연락처</div>
                        <div class="form-floating">
                        <input type="text" class="form-control" id="tel" placeholder="tel" name="m_tel" required v-model="userinfo.tel"> 
                        <label for="floatingPassword">"-" 없이 입력해 주세요</label>
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
                tel: ''
            }
        }
    },
    methods: {
        register() {
            axios.post(this.$store.state.url + 'register', this.userinfo)
                .then(response => {
                    console.log(response);
                    if(response.data.state) {
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
.mt-5 {
  margin-top: 7rem !important;
}
.b-color {
  border-color: #7A6B6B;
}
</style>