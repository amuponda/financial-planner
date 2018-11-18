import http from '@/http'

import {FETCH_USER, DESTROY_USER, ADD_ACCOUNT, FETCH_ACCOUNTS} from './mutation-types'

const USER_URL = '/api/user'
const ACCOUNTS_URL = '/api/accounts'
const ACCOUNT_CREATE_URL = '/api/account/create'

export function fetchUser ({ commit }) {
  http.get(USER_URL)
    .then(response => {
      commit(FETCH_USER, response.data)
    })
}

export function obliterateUser ({ commit }) {
  commit(DESTROY_USER)
}

export function fetchAccounts ({ commit }) {
  http.get(ACCOUNTS_URL)
    .then(response => {
      commit(FETCH_ACCOUNTS, response.data)
    })
}
export function addAccount ({ commit }, name) {
  http.get(ACCOUNT_CREATE_URL, {
    params: { name: name }
  })
    .then(response => {
      commit(ADD_ACCOUNT, response.data)
    })
}
