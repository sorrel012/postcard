import Vue from 'vue'
import VueRouter from 'vue-router'

Vue.use(VueRouter)

const routes = [
    {
        path: '/',
        name: 'main',
        component: () => import('@/views/Main.vue')
    },
    {
        path: '/register',
        name: 'register',
        component: () => import('@/views/Register.vue')
    },
    {
        path: '/login',
        name: 'login',
        component: () => import('@/views/Login.vue')
    },
    {
        path: '/myprofile',
        name: 'myprofile',
        component: () => import('@/views/MyProfile.vue')
    },
    {
        path: '/create',
        name: 'create',
        component: () => import('@/views/Create.vue')
    },
    {
        path: '/mypaperlist',
        name: 'mypaperlist',
        component: () => import('@/views/MyPaperList.vue')
    },
    {
        path: '/mypaper',
        name: 'mypaper',
        component: () => import('@/views/MyPaper.vue')
    },
    {
        path: '/postcard',
        name: 'postcard',
        component: () => import('@/views/Postcard.vue')
    }, ,
    {
        path: '/postcard-write',
        name: 'postcard-write',
        component: () => import('@/views/PostcardWrite.vue')
    },
    {
        path: '/sns-login',
        name: 'sns-login',
        component: () => import('@/components/SnsLogin.vue')
    },
    {
        path: '/sns-register',
        name: 'sns-register',
        component: () => import('@/views/SnsRegister.vue')
    },
    {
        path: '/treasure',
        name: 'treasure-box',
        component: () => import('@/views/TreasureBox.vue')
    },
    {
        path: '/treasure-write',
        name: 'treasure-write',
        component: () => import('@/views/TreasureBoxWrite.vue')
    },
    {
        path: '/treasure-detail',
        name: 'treasure-detail',
        component: () => import('@/views/TbDetail.vue')
    },
    {
        path: '/mypostlist',
        name: 'mypostlist',
        component: () => import('@/views/MyPostList.vue')
    },
    {
        path: '/mycommentlist',
        name: 'mycommentlist',
        component: () => import('@/views/MyCommentList.vue')
    },
]

const router = new VueRouter({
    mode: 'history',
    base: process.env.BASE_URL,
    routes
})

export default router
