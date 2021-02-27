import Vue from 'vue'
import './plugins/axios'
import App from './App.vue'
import router from './router'
import store from './store'
import './plugins/element.js'

Vue.config.productionTip = false
axios.defaults.withCredentials = true; // 解决前后端跨域


new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')

