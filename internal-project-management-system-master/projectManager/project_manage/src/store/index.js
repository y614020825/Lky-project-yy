import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    // 企业端平台的头像
    // headImg:localStorage.getItem('headImg') ? localStorage.getItem('headImg') : require('@/assets/images/default_headImage.png'),
    // 存储token
    token: localStorage.getItem('token') ? localStorage.getItem('token') : '',
    // 存储菜单
    menuList: localStorage.getItem('menuList') ? localStorage.getItem('menuList') : [],
    userName: localStorage.getItem('userName') ? localStorage.getItem('userName') : '',
    requestPaths: localStorage.getItem('requestPaths') ? localStorage.getItem('requestPaths') : [],
    currentUserInfo: localStorage.getItem('currentUserInfo') ? localStorage.getItem('currentUserInfo') : {},
  },
  mutations: {
    // 修改token，menuList,并将token、menuList存入localStorage
    refreshCurrentUserInfo(state, user) {
      state.token = user.token
      state.menuList = user.menuList
      state.requestPaths = user.requestPaths
      state.loginAccount = user.loginAccount
      state.currentUserInfo = user.currentUserInfo
      localStorage.setItem('token', user.token)
      localStorage.setItem('menuList', JSON.stringify(user.menuList))
      localStorage.setItem('requestPaths', JSON.stringify(user.requestPaths))
      localStorage.setItem('currentUserInfo', JSON.stringify(user.currentUserInfo))
      // console.log(state.userName)
    },
    // 修改headImg
    // refreshHeadImg(state,img){
    //   state.headImg = img
    //   localStorage.setItem('headImg', img)
    // }
  },
  getters: { //获取数据的，相当于计算属性或过滤器，只读
    // 判断是否有权限
    // isBelongToRequestPaths(state){
    //   return console.log(JSON.parse(state.requestPaths))
    // }
  },
  actions: {},
  modules: {}
})