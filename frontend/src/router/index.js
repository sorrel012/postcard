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
    path: '/write',
    name: 'write',
    component: () => import('@/views/Postcard.vue')
  },
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
