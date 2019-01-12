import {
  ADD_BILL,
  FETCH_BILLS,
  INIT_CATEGORIES,
  INIT_REGULARITIES
} from './mutation-types'

import * as actions from './actions'
import * as getters from './getters'

const initialState = {
  iae: [],
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
