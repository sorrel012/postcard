import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
  state() {
    return {
      url: 'http://localhost:8082/',
      isLoading: true,
      snsUser: {
        id: '',
        email: '',
      },
    }
  },
  mutations: {
    setLoadingStatus(state, value) {
      state.isLoading = value;
    },
    setSnsUserId(state, value) {
      state.snsUser.id = value;
    },
    setSnsUserEmail(state, value) {
      state.snsUser.email = value;
    },
  },
})
