import http from '@/http'

import {FETCH_USER, DESTROY_USER} from './mutation-types'

const USER_URL = '/api/user'

export function fetchUser ({ commit }) {
  http.get(USER_URL)
    .then(response => {
      commit(FETCH_USER, response.data)
    })
}

export function obliterateUser ({ commit }) {
  commit(DESTROY_USER)
}
