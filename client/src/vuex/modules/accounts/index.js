import {
  ADD_BILL,
  FETCH_BILLS,
  FETCH_BILL,
  INIT_CATEGORIES,
  INIT_REGULARITIES
} from './mutation-types'

import * as actions from './actions'
import * as getters from './getters'

const initialState = {
  iae: [],
  currentBill: null,
  initializedIae: false,
  categories: null,
  repeats: null
}

const mutations = {
  [ADD_BILL] (state, bill) {
    state.iae.push(bill)
  },
  [FETCH_BILLS] (state, bills) {
    state.iae = bills
    state.initializedIae = true
  },
  [FETCH_BILL] (state, bill) {
    state.currentBill = bill
  },
  [INIT_CATEGORIES] (state, categories) {
    state.categories = categories
  },
  [INIT_REGULARITIES] (state, repeats) {
    state.repeats = repeats
  }
}

export default {
  state: {...initialState},
  mutations,
  getters,
  actions
}
