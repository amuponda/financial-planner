import Vue from 'vue'
import Router from 'vue-router'
import Root from '@/components/Root'
import Signup from '@/components/Signup'
import Login from '@/components/Login'
import Dashboard from '@/components/Dashboard'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/signup',
      name: 'signup',
      component: Signup
    },
    {
      path: '/',
      name: 'Root',
      component: Root,
      children: [
        {
          path: '/login',
          name: 'login',
          component: Login
        },
        {
          path: '/dashboard',
          name: 'dashboard',
          component: Dashboard
        }
      ]
    }
  ]
})
