import axios from 'axios'
import { Message } from 'view-design'
import { baseUrl,baseUrl2 } from '@/util/urlUtil'
import router from '@/router/index'


export function request(config) {
  const instance = new axios.create({
    baseURL:baseUrl,
    timeout:60*1000*10,
    headers: {
      'Content-Type': 'application/x-www-form-urlencoded;charset=utf-8'
    },
    //该选项只适用于以下请求方式：`put/post/patch`--formDate格式
    transformRequest: [
      function (data) {
         let ret = ''
         for (let it in data) {
            ret += encodeURIComponent(it) + '=' + encodeURIComponent(data[it]) + '&'
         }
         ret = ret.substring(0, ret.lastIndexOf('&'));
         return ret
      }
    ]
  });

  // 请求拦截器
  instance.interceptors.request.use(config => {
    //拦截后需要将拦截下来的请求数据返回发送
    let token = localStorage.getItem('token');
      if (token) { // 每次发送请求之前判断是否存在token，如果存在，则统一在http请求的header都加上token，不用每次请求都手动添加了
       //  config.headers['token'] = token;
       // 每个请求接口都需要往formData上，加_token_iben
       config.data._token_iben = token
      }
    return config;
  }, err => {
    console.log(err)
  })

  // 响应拦截器
  instance.interceptors.response.use(res => {
    // 拦截后需要将拦截下来处理成的结果返回
    // console.log(res)
    //登录超时
    if(res.data.data && res.data.data.errorCode==='901'){
      Message.error('登录超时，请重新登录!');
      router.push('/login')
    }
    if(res.data && res.data.errorCode === "901"){
      // localStorage.remove("_ocs_login_flag");
      Message.error('登录超时，请重新登录!');
      router.push('/login')
    }
    if(res.data && res.data.errorCode === "902"){
      Message.error('暂无权限，请重新登录!');
    }
    return res.data
  }, err => {
    console.log(err)
    Message.error('响应超时！');
  })
  return instance(config)
}

// 如果有另外的---请求另外一个baseURL的接口
export function request2(config) {
  const instance = new axios.create({
    baseURL:baseUrl2,
    timeout:5000
  });

  // 请求拦截器
  instance.interceptors.request.use(config => {
    //拦截后需要将拦截下来的请求数据返回发送
    return config;
  }, err => {
    console.log(err)
  })

  // 相应拦截器
  instance.interceptors.response.use(res => {
    // 拦截后需要将拦截下来处理成的结果返回
    return res.data
  }, err => {
    console.log(err)
  })

  return instance(config)
}


