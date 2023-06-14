import {
  request
} from './request'
// 相当于这里写各组件页方法，直接then调用
// 登录接口
export function loginMethod(name, pwd) {
  return request({
    method: 'post',
    url: '/manage/goLogin',
    data: {
      loginAccount: name,
      pwd: pwd
    }
  });
}
// 退出接口
export function quitMethod(name, token) {
  return request({
    method: 'post',
    url: '/manage/loginOut',
    data: {
      loginAccount: name,
      _token_iben: token
    }
  });
}
// 当前用户信息（菜单、菜单权限，用户信息）
export function currentUserInfoMethod(token) {
  return request({
    method: 'post',
    url: '/sys/getCurrentUserInfo',
    data: {
      _token_iben: token
    }
  });
}