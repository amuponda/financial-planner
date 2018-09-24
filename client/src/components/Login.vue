<template>
  <b-container>
    <b-row>
      <b-col sm="4" offset-sm="4">
        <b-card border-variant="primary">

          <h4 slot="header" class="text-center">Log In</h4>

          <span v-show="errorMessage" class="text-danger">{{errorMessage}}</span>

          <b-form @submit.prevent="submit" novalidate>
            <b-form-group id="emailGroup" label="Email:" label-for="email">
              <b-form-input id="email" type="email" v-model="form.username" name="username" v-validate="'required|email'"></b-form-input>
              <p v-show="errors.has('username')" class="text-danger">{{ errors.first('username') }}</p>
            </b-form-group>

            <b-form-group id="passwordGroup" label="Password:" label-for="password">
              <b-form-input id="password" type="password" v-model="form.password" name="password" v-validate="'required'"></b-form-input>
              <p v-show="errors.has('password')" class="text-danger">{{ errors.first('password') }}</p>
            </b-form-group>

            <b-button type="submit" variant="primary">Sign in</b-button>
          </b-form>
        </b-card>
      </b-col>
    </b-row>
  </b-container>
</template>

<script>
import http from '@/http'
const loginUrl = '/api/login'

export default {
  name: 'login',
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
              this.$router.push({ name: 'dashboard' })
            })
            .catch(error => {
              if (error.response.status === 403) {
                this.errorMessage = error.response.data.message
              } else {
                this.errorMessage = 'Authentication Failed'
              }
            })
        }
      })
    }
  }
}
</script>

<style scoped>

</style>
