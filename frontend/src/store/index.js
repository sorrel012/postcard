import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
  state() {
    return {
      url: 'http://localhost:8082/',
      paper: {},
      isLoading: true,
    }
  },
  mutations: {
    setPaper(state, value) {
      state.paper = value;
    },
    setLoadingStatus(state, value) {
      state.isLoading = value;
    }
  },
})
