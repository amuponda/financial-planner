<template>
  <b-container>
    <b-row>
      <b-col sm="4" offset-sm="4">
        <b-card border-variant="primary">

          <h4 slot="header" class="text-center">Sign Up</h4>

          <div v-if="errors">
            <span v-for="error in errors" class="text-danger">{{ error.message }}</span>
          </div>

          <b-form @submit.prevent="submit" novalidate>
            <b-form-group id="nameGroup" label="Name:" label-for="name">
              <b-form-input id="name" type="text" v-model="form.firstName" name="firstName" v-validate="'required'"></b-form-input>
              <p v-show="errors.has('firstName')" class="text-danger">{{ errors.first('firstName') }}</p>
            </b-form-group>

            <b-form-group id="surnameGroup" label="Surname:" label-for="surname">
              <b-form-input id="surname" type="text" v-model="form.lastName" name="lastName" v-validate="'required'"></b-form-input>
              <p v-show="errors.has('lastName')" class="text-danger">{{ errors.first('lastName') }}</p>
            </b-form-group>

            <b-form-group id="emailGroup" label="Email:" label-for="email">
              <b-form-input id="email" type="email" v-model="form.username" name="username" v-validate="'required|email'"></b-form-input>
              <p v-show="errors.has('username')" class="text-danger">{{ errors.first('username') }}</p>
            </b-form-group>

            <b-form-group id="passwordGroup" label="Password:" label-for="password">
              <b-form-input id="password" type="password" v-model="form.password" name="password" v-validate="'required'"></b-form-input>
              <p v-show="errors.has('password')" class="text-danger">{{ errors.first('password') }}</p>
            </b-form-group>

            <b-button type="submit" variant="primary">Sign Up</b-button>
          </b-form>
        </b-card>
      </b-col>
    </b-row>
  </b-container>
</template>

<script>
import http from '@/http'

const signupUrl = '/api/signup'

export default {
  name: 'signup',
  data () {
    return {
      form: {
        firstName: '',
        lastName: '',
        username: '',
        password: '',
        errors: null,
      }
    }
  },
  methods: {
    resetErrors () {
      this.errors = null
    },
    submit () {
      this.resetErrors()
      this.$validator.validateAll().then(result => {
        if (result) {
          http.post(signupUrl, this.form)
            .then(response => {
              // TODO should take them to email verification page
              if (response.status === 201) {
                this.$router.push({ name: 'login' })
                return
              }
              this.errors = response._embedded.errors
            })
            .catch(error => {
              this.errors = [{ message: error.message}]
            })
        }
      })
    }
  }
}
</script>

<style scoped>
 .card {
   margin-top: 25%;
 }
</style>
