<template>
  <div>
    <nav class="navbar navbar-expand-md navbar-light bg-light">
      <a class="navbar-brand" href="#">cashFlow</a>
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
            <a class="nav-link" href="#">Bills</a>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="#">Sign out</a>
          </li>
        </ul>
      </div>
    </nav>
    <router-view></router-view>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'

export default {
  name: 'root',
  created () {
    this.$store.dispatch('fetchUser')
  },
  methods: {
    signOut () {
      this.$store.dispatch('obliterateUser')
      sessionStorage.removeItem('fp_token')
      this.$router.push({ name: 'login' })
    }
  },
  computed: mapGetters({
    user: 'getUser'
  })
}
</script>

<style scoped>

</style>
