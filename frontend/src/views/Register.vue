<template>
    <section class="container-md d-flex justify-content-center p-5 mt-5">
        
        <div class="col-sm-12 col-md-12 col-lg-5 p-0 bg-white d-flex align-items-center rounded-end pt-4 pb-4 me-5">

            <div class="container-md d-flex flex-column align-items-center">               
                
                <h1 class="h2 text-center fw-bold title">개인 회원가입</h1>
                <p class="fs-6 text-center mb-4">회원가입 후 다양한 서비스를 이용하세요.</p>
                
                <form class="w-75" @submit="register" id="formregister">               
                    <div class="mb-1">아이디</div>
                    <div class="form-floating mb-3">
                        <input type="text" class="form-control" id="userid" placeholder="name@example.com" name="m_id" v-model="id"> 
                        <label for="floatingInput">6~12자 이내로 입력해 주세요</label>
                    </div>
                        
                    <div class="mb-1">비밀번호</div>
                        <div class="form-floating mb-3">
                        <input type="password" class="form-control" id="password" placeholder="Password" name="m_pw" required v-model="pw"> 
                        <label for="floatingPassword">8~12자 이내로 입력해 주세요 (알파벳, 숫자 필수)</label>
                    </div>

                    <div class="mb-1">이름</div>
                        <div class="form-floating mb-3">
                        <input type="text" class="form-control" id="name" placeholder="name" name="m_name" required v-model="name"> 
                        <label for="floatingPassword" >이름을 입력해 주세요</label>
                    </div>
                    
                    <div class="mb-1">연락처</div>
                        <div class="form-floating">
                        <input type="text" class="form-control" id="tel" placeholder="tel" name="m_tel" required v-model="tel"> 
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
            console.log(this.userinfo);
        },
    }
}
</script>

<style>
section {
    background-color: #FFF9F3;
}
</style>