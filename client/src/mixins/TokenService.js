export default {
  getToken () {
    return sessionStorage.getItem('fp_token')
  },
  deleteToken () {
    sessionStorage.removeItem('fp_token')
  },
  isLoggedIn () {
    return this.getToken()
  }
}
