import { ADD_ALERT, DELETE_ALERT } from './mutation-types'
import * as getters from './getters'
import * as actions from './actions'

const initialState = {
  alerts: []
}

const mutations = {
  [ADD_ALERT] (state, alert) {
    state.alerts.push(alert)
  },
  [DELETE_ALERT] (state, alert) {
    let index = state.alerts.findIndex(a => a.text === alert.text)
    if (index !== -1) {
      state.alerts.splice(index, 1)
    }
  }
}

export default {
  state: {...initialState},
  getters,
  actions,
  mutations
}
