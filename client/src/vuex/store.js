import Vue from 'vue'
import Vuex from 'vuex'

import user from './modules/user'
import notifications from './modules/notifications'

Vue.use(Vuex)

const debug = process.env.NODE_ENV !== 'production'

export default new Vuex.Store({
  modules: {
    user,
    notifications
  },
  strict: debug
})
