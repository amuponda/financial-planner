import { ADD_ALERT, DELETE_ALERT } from './mutation-types'

export function addAlert ({ commit, state }, alert) {
  commit(ADD_ALERT, alert)
  setTimeout(() => {
    commit(DELETE_ALERT, alert)
  }, 5000)
}
