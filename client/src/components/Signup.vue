<template>
  <div class="container mt-5">
    <div class="row justify-content-md-center">
      <div class="col-sm-4">
        <div class="card text-center border-0">
          <div class="card-body">
            <h4 class="card-title text-center">Sign Up</h4>

            <div v-if="errors">
              <span v-for="error in errors" :key="error.message" class="text-danger">{{ error.message }}</span>
            </div>

            <form @submit.prevent="submit" novalidate>
              <div class="form-group">
                <input v-model="form.firstName" name="firstName" v-validate="'required'" class="form-control" placeholder="Name"/>
                <p v-show="errors.has('firstName')" class="text-danger">{{ errors.first('firstName') }}</p>
              </div>

              <div class="form-group">
                <input v-model="form.lastName" name="lastName" v-validate="'required'" class="form-control" placeholder="Surname"/>
                <p v-show="errors.has('lastName')" class="text-danger">{{ errors.first('lastName') }}</p>
              </div>

              <div class="form-group">
                <input type="email" v-model="form.username" name="username" v-validate="'required|email'" class="form-control" placeholder="Email"/>
                <p v-show="errors.has('username')" class="text-danger">{{ errors.first('username') }}</p>
              </div>

              <div class="form-group">
                <input type="password" v-model="form.password" name="password" v-validate="'required'" class="form-control" placeholder="Password"/>
                <p v-show="errors.has('password')" class="text-danger">{{ errors.first('password') }}</p>
              </div>

              <button type="submit" class="btn btn-primary btn-sm px-5">Sign Up</button>
            </form>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import http from '@/http'

const signupUrl = '/api/signup'

export default {
  name: 'Signup',
  data () {
    return {
      form: {
        firstName: '',
        lastName: '',
        username: '',
        password: '',
        errors: null
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
              this.errors = response.data.errors
            })
            .catch(error => {
              this.errors = [{ message: error.message }]
            })
        }
      })
    }
  }
}
</script>

<style scoped>
  .form-control {
    height: calc(1.25rem + 14px);
  }
</style>
