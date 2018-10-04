import {
  FETCH_USER,
  DESTROY_USER
} from './mutation-types'

import * as actions from './actions'
import * as getters from './getters'

const initialState = {
  user: null
}

const mutations = {
  [FETCH_USER] (state, user) {
    state.user = user
  },
  [DESTROY_USER] (state) {
    state.user = null
  }
}

export default {
  state: {...initialState},
  mutations,
  getters,
  actions
}
