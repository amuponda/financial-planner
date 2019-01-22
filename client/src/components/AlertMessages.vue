<template>
  <div>
    <div v-for="message in messages" :class="['alert', statusClasses[message.type]]" :key="message.text" role="alert">
      <p class="text-center">{{ message.text }}</p>
    </div>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'

export default {
  name: 'AlertMessages',
  data () {
    return {
      statusClasses: {
        success: 'alert-success',
        danger: 'alert-danger',
        info: 'alert-info'
      }
    }
  },
  computed: mapGetters({
    messages: 'getAlerts'
  })

}
</script>

<style scoped>

</style>
