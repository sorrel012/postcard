import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
  state() {
    return {
      url: 'http://localhost:8082/',
      paper: {},
      isLoading: true,
      snsUser: {
        id: '',
        email: '',
      },
      postDetail: {
        b_seq: 0,
        m_id: '',
        title: '',
        content: '',
        regdate: '',
        hit: 0,
        comment: 0
      }
    }
  },
  mutations: {
    setPaper(state, value) {
      state.paper = value;
    },
    setLoadingStatus(state, value) {
      state.isLoading = value;
    },
    setSnsUserId(state, value) {
      state.snsUser.id = value;
    },
    setSnsUserEmail(state, value) {
      state.snsUser.email = value;
    },
    setPostDetail(state, value) {
      state.postDetail = value;
    }
  },
})
