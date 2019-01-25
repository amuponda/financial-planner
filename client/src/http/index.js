import axios from 'axios'
import router from '@/router'
import tokenService from '../mixins/TokenService'

const http = axios.create({
  baseURL: process.env.SERVER_URL,
  headers: {
    'Content-Type': 'application/json'
  }
})

http.interceptors.request.use(config => {
  let token = tokenService.getToken()
  if (token) config.headers.Authorization = 'Bearer ' + token
  return config
}, error => {
  return Promise.reject(error)
})

http.interceptors.response.use(response => {
  return response
}, error => {
  if (error.response.status === 401) {
    tokenService.deleteToken()
    router.push({ name: 'login', params: { originalUrl: router.currentRoute.fullPath } })
  }
  if (error.response.status === 404) {
    router.push({ name: 'notFound' })
  }
  return Promise.reject(error)
})

export default http
