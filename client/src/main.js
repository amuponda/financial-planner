// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
import store from './vuex/store'
import * as uiv from 'uiv'
import VeeValidate from 'vee-validate'
import Vue2Filters from 'vue2-filters'
import filters from './filters'
import './assets/css/bootstrap.css'

Vue.config.productionTip = false

Vue.use(uiv)
Vue.use(VeeValidate)
Vue.use(Vue2Filters)
Vue.use(filters)
/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  store,
  components: { App },
  template: '<App/>'
})
