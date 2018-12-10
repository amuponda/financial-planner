<template>
  <div>
    <nav class="navbar navbar-expand-md navbar-light bg-light">
      <router-link class="navbar-brand" to="/dashboard">cashFlow</router-link>
      <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
      </button>

      <div v-if="user" class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav mr-auto">
          <li class="nav-item active">
            <a class="nav-link" href="#">{{ user.firstName }}<span class="sr-only">(current)</span></a>
          </li>
        </ul>
        <ul class="navbar-nav ml-auto">
          <li class="nav-item">
            <a class="nav-link" href="#">Analyze</a>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="#">Income</a>
          </li>
          <li class="nav-item">
            <router-link class="nav-link" to="/bills">Bills</router-link>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="#" @click.prevent="signOut">Sign out</a>
          </li>
        </ul>
      </div>
    </nav>

    <alert-messages></alert-messages>

    <router-view></router-view>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'
import AlertMessages from './AlertMessages'

export default {
  name: 'Root',
  methods: {
    signOut () {
      this.$store.dispatch('obliterateUser')
      sessionStorage.removeItem('fp_token')
      this.$router.push({ name: 'login' })
    },
    init () {
      this.$store.dispatch('fetchUser')
      this.$store.dispatch('fetchAccounts')
    }
  },
  computed: mapGetters({
    user: 'getUser'
  }),
  components: {
    AlertMessages
  },
  beforeRouteEnter (to, from, next) {
    if (sessionStorage.getItem('fp_token')) {
      next(vm => {
        vm.init()
      })
    } else if (to.name === 'login') {
      next()
    } else { // not logged in
      next({
        name: 'login',
        params: { originalUrl: to.fullPath }
      })
    }
  },
  beforeRouteUpdate (to, from, next) {
    if (to.name === 'login') {
      next()
    } else if (!this.user) {
      console.log('initing user')
      this.init()
      next()
    } else { // not logged in
      next({
        name: 'login',
        params: { originalUrl: to.fullPath }
      })
    }
  }

}
</script>

<style scoped>

</style>
