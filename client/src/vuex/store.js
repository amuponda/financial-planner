import Vue from 'vue'
import Vuex from 'vuex'

import user from './modules/user'
import notifications from './modules/notifications'
import accounts from './modules/accounts'

Vue.use(Vuex)

const debug = process.env.NODE_ENV !== 'production'

export default new Vuex.Store({
  modules: {
    user,
    accounts,
    notifications
  },
  strict: debug
})
