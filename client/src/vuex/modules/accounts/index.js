import {
  ADD_BILL,
  INIT_CATEGORIES,
  INIT_REGULARITIES
} from './mutation-types'

import * as actions from './actions'
import * as getters from './getters'

const initialState = {
  bills: [],
  categories: null,
  repeats: null
}

const mutations = {
  [ADD_BILL] (state, bill) {
    state.bills.push(bill)
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
