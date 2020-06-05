import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import {initMenu} from './utls/menu'
import 'font-awesome/css/font-awesome.min.css'

import {postKeyValueRequest} from './utls/api.js'
import {postRequest} from './utls/api.js'
import {putRequest} from './utls/api.js'
import {deleteRequest} from './utls/api.js'
import {getRequest} from './utls/api.js'

Vue.prototype.postKeyValueRequest=postKeyValueRequest
Vue.prototype.postRequest=postRequest
Vue.prototype.putRequest=putRequest
Vue.prototype.deleteRequest=deleteRequest
Vue.prototype.getRequest=getRequest


Vue.config.productionTip = false
Vue.use(ElementUI)

//定制全局路由守卫
router.beforeEach((to, from, next) => {
   // console.log(to);
    //console.log(from);
    //next();
  //如果跳转至登录界面，直接放行
   if(to.path=='/'){
     next();
   }else {
       if(window.sessionStorage.getItem('user')){
           //如果登入了
           //加载菜单栏
           initMenu(router,store);
           next();
       }else {
           //如果没登入,返回登入界面
           next('/?redirect='+to.path);
       }

   }

})


new Vue({
  store,
  router,
  render: h => h(App)
}).$mount('#app')
