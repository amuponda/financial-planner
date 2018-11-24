<template>
  <div class="container mt-5">
    <div class="row justify-content-md-center">
      <div class="col-sm-4">
        <div class="card text-center border-0">
          <div class="card-body">
            <h5 class="card-title text-center">Sign In</h5>
            <span v-show="errorMessage" class="text-danger">{{errorMessage}}</span>

            <form @submit.prevent="submit" novalidate>
              <div class="form-group">
                <input id="email" type="email" v-model="form.username" name="username" v-validate="'required|email'" class="form-control" placeholder="Email"/>
                <p v-show="errors.has('username')" class="text-danger">{{ errors.first('username') }}</p>
              </div>

              <div class="form-group">
                <input id="password" type="password" v-model="form.password" name="password" v-validate="'required'" class="form-control" placeholder="Password"/>
                <p v-show="errors.has('password')" class="text-danger">{{ errors.first('password') }}</p>
              </div>

              <button type="submit" class="btn btn-primary btn-sm px-5">Sign In</button>
            </form>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import http from '@/http'
const loginUrl = '/api/login'

export default {
  name: 'Login',
  data () {
    return {
      form: {
        username: '',
        password: ''
      },
      errorMessage: ''
    }
  },
  methods: {
    submit () {
      this.$validator.validateAll().then(result => {
        if (result) {
          http.post(loginUrl, this.form)
            .then(response => {
              sessionStorage.setItem('fp_token', response.data.access_token)
              let originalUrl = this.$router.currentRoute.params.originalUrl
              this.$router.push(originalUrl ? { path: originalUrl } : { name: 'dashboard' })
            })
            .catch(error => {
              let isForbidden = error.response.status === 403
              this.errorMessage = isForbidden ? error.response.data.message : 'Authentication Failed'
            })
        }
      })
    }
  }
}
</script>

<style scoped>
  .form-control {
    height: calc(1.25rem + 14px);;
  }
</style>
