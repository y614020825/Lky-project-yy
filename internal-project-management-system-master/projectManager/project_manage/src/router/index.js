import Vue from 'vue'
import VueRouter from 'vue-router'
import {
  Message
} from 'view-design'

Vue.use(VueRouter)

const routes = [{
    path: '',
    redirect: '/login'
  },
  {
    path: '/login',
    // 指定的组件
    component: () => import('views/login/Login')
  },
  {
    path: '/main',
    name: 'Main',
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () => import('@/components/common/Main.vue'),
    children: [{
      path: '/projectManage',
      component: () => import('@/views/ProjectManage.vue')
    }, {
      path: '/roleManage',
      component: () => import('@/views/RoleManage.vue')
    }, {
      path: '/memberManage',
      component: () => import('@/views/MemberManage.vue')
    }, {
      path: '/schedule',
      component: () => import('@/views/Schedule.vue')
    }, {
      path: '/increaseDemand',
      component: () => import('@/views/IncreaseDemand.vue')
    }, {
      path: '/viewDetail',
      component: () => import('@/views/ViewDetail.vue')
    }]
  }
]

const router = new VueRouter({
  routes
})
// 导航守卫
// 使用 router.beforeEach 注册一个全局前置守卫，判断用户是否登陆
const defaultTitle = '项目管理平台'; //宣传页的默认title
router.beforeEach((to, from, next) => {
  document.title = to.meta.title ? to.meta.title : defaultTitle; //更改宣传页的网页title

  if (to.path === '/login' || to.path === '/sendMsg' || to.path === '/leaveMessage' || to.path === '/advertisement') {
    next();
  } else {
    let token = localStorage.getItem('token');
    if (token === null || token === '') {
      // token不存在
      Message.error('登录超时，请重新登录!');
      next('/login');
    } else {
      next();
    }
  }
});

router.afterEach(route => {
  $('.content-box').scrollTop(0)
})

export default router