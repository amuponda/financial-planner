import axios from '@/http'
import {
  ADD_BILL,
  FETCH_BILLS,
  FETCH_BILL,
  INIT_CATEGORIES,
  INIT_REGULARITIES
} from './mutation-types'

export function addBill ({ commit }, bill) {
  return new Promise((resolve, reject) => {
    axios.post('/api/iae/create', {}, {
      params: bill
    })
      .then(response => {
        commit(ADD_BILL, response.data)
        resolve()
      })
      .catch(reason => {
        reject(reason)
      })
  })
}

export function getBill ({ commit }, billId) {
  return new Promise((resolve, reject) => {
    axios.get(`/api/iae/${billId}`).then(response => {
      let bill = response.data.bill; bill.transactions = response.data.transactions
      commit(FETCH_BILL, bill)
      resolve()
    })
      .catch(reason => {
        reject(reason)
      })
  })
}

export function fetchIncomeAndExpenditure ({ commit, dispatch }) {
  axios.get('/api/iae').then(response => {
    commit(FETCH_BILLS, response.data)
    if (response.data.length === 0) {
      let info = 'Add some bills to your account(s) to track your spending.'
      dispatch('addAlert', { text: info, type: 'info' })
    }
  })
    .catch(reason => {
      console.error(reason.response.data.message)
      let error = 'Failed to load your current list of bills'
      dispatch('addAlert', { text: error, type: 'danger' })
    })
}

export function fetchCategories ({ commit, dispatch }) {
  axios.get('/api/iae/categories').then(response => {
    commit(INIT_CATEGORIES, response.data)
  })
    .catch(reason => {
      console.error(reason.response.data.message)
      let error = 'Failed to load expense category types'
      dispatch('addAlert', { text: error, type: 'danger' })
    })
}

export function fetchRegularityOptions ({ commit, dispatch }) {
  axios.get('/api/iae/regularity').then(response => {
    commit(INIT_REGULARITIES, response.data)
  })
    .catch(reason => {
      console.error(reason.response.data.message)
      let error = 'Failed to initialize application data'
      dispatch('addAlert', { text: error, type: 'danger' })
    })
}
