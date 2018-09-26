import axios from 'axios'
import router from '@/router'

const http = axios.create({
  baseURL: process.env.SERVER_URL,
  headers: {
    'Content-Type': 'application/json'
  }
})

http.interceptors.request.use(config => {
  let token = sessionStorage.getItem('fp_token')
  if (token) config.headers.Authorization = 'Bearer ' + token
  return config
}, error => {
  return Promise.reject(error)
})

http.interceptors.response.use(response => {
  return response
}, error => {
  if (error.response.status === 401) {
    sessionStorage.removeItem('fp_token')
    router.push({ name: 'login', params: { originalUrl: router.currentRoute.fullPath } })
  }
  return Promise.reject(error)
})

export default http
