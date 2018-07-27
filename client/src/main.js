// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
import * as uiv from 'uiv'
import VeeValidate from 'vee-validate'
import BootstrapVue from 'bootstrap-vue'
import './assets/css/bootstrap.css'
import './assets/css/grails.css'
import './assets/css/main.css'
import 'bootstrap-vue/dist/bootstrap-vue.css'

Vue.config.productionTip = false

Vue.use(uiv)
Vue.use(VeeValidate)
Vue.use(BootstrapVue)
/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  components: { App },
  template: '<App/>'
})
