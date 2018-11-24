import http from '@/http'

import {
  FETCH_USER,
  DESTROY_USER,
  ADD_ACCOUNT,
  FETCH_ACCOUNTS
} from './mutation-types'

const GET_USER_URL = '/api/user'
const GET_ACCOUNTS_URL = '/api/accounts'
const CREATE_ACCOUNT_URL = '/api/account/create'

export function fetchUser ({ commit }) {
  http.get(GET_USER_URL)
    .then(response => {
      commit(FETCH_USER, response.data)
    })
}

export function obliterateUser ({ commit }) {
  commit(DESTROY_USER)
}

export function fetchAccounts ({ commit }) {
  http.get(GET_ACCOUNTS_URL)
    .then(response => {
      commit(FETCH_ACCOUNTS, response.data)
    })
}

export function addAccount ({ commit, dispatch }, name) {
  http.get(CREATE_ACCOUNT_URL, {
    params: { name: name }
  })
    .then(response => {
      commit(ADD_ACCOUNT, response.data)
      dispatch('addAlert', { text: 'account added', type: 'success' })
    })
    .catch(reason => {
      dispatch('addAlert', { text: reason.response.data.message, type: 'danger' })
    })
}
