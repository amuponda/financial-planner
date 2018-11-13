// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
import store from './vuex/store'
import * as uiv from 'uiv'
import VeeValidate from 'vee-validate'
import './assets/css/bootstrap.css'

Vue.config.productionTip = false

Vue.use(uiv)
Vue.use(VeeValidate)
/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  store,
  components: { App },
  template: '<App/>'
})
