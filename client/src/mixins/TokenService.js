export default {
  getToken () {
    return localStorage.getItem('fp_token')
  },
  deleteToken () {
    localStorage.removeItem('fp_token')
  },
  isLoggedIn () {
    return this.getToken()
  }
}
