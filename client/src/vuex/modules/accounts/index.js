import { ADD_BILL } from './mutation-types'

import * as actions from './actions'

const initialState = {
  bills: []
}

const mutations = {
  [ADD_BILL] (state, bill) {
    state.bills.push(bill)
  }
}

export default {
  state: {...initialState},
  mutations,
  actions
}
