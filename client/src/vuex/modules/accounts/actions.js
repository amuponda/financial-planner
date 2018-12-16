import axios from '@/http'
import { ADD_BILL } from './mutation-types'

export function addBill ({ commit }, bill) {
  console.log('in action add bill')
  return new Promise((resolve, reject) => {
    axios.post('/api/accounts/bills/create', {}, {
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
