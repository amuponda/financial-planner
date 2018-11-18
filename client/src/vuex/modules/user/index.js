import {
  FETCH_USER,
  DESTROY_USER,
  ADD_ACCOUNT, FETCH_ACCOUNTS
} from './mutation-types'

import * as actions from './actions'
import * as getters from './getters'

const initialState = {
  user: null,
  accounts: []
}

const mutations = {
  [FETCH_USER] (state, user) {
    state.user = user
  },
  [DESTROY_USER] (state) {
    state.user = null
  },
  [FETCH_ACCOUNTS] (state, accounts) {
    state.accounts = accounts
  },
  [ADD_ACCOUNT] (state, account) {
    state.accounts.push(account)
  }
}

export default {
  state: {...initialState},
  mutations,
  getters,
  actions
}
