import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
// 引入jquery
import $ from 'jquery'
// 引入viewUI---view-design
import ViewUI from 'view-design'
// import style
import 'view-design/dist/styles/iview.css'
import '@/assets/icon/font_icon/iconfont.css'
// md5加密
import md5 from 'js-md5'
// 全局基本路径
import {
  baseUrl
} from './util/urlUtil'
// 按钮权限
import {
  hasPermission
} from './util/permissions'

// 引入时间插件
import moment from 'moment'
//定义时间格式全局过滤器
Vue.filter("dateFormat", function (daraStr, pattern = "YYYY-MM-DD HH:mm:ss") {
  return moment(daraStr).format(pattern)
})

//封装的get、postaxios，上传文件formdata格式方法
import {
  postRequest,
  getRequest,
  postRequest2
} from './network/http'
//定义全局变量
Vue.prototype.$post = postRequest;
Vue.prototype.$post_ = postRequest2;
Vue.prototype.$get = getRequest;

Vue.prototype.$ = $
Vue.prototype.$md5 = md5
Vue.prototype.$baseUrl = baseUrl
//全局方法挂载
Vue.prototype.$_has = hasPermission
// 注册使用
Vue.use(ViewUI)

Vue.config.productionTip = false

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')