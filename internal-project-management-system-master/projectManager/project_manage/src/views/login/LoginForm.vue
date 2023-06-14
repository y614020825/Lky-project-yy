<template>
  <div class="form">
    <div class="logo_big">
      <!-- <img src="~/assets/images/logo_big.png" alt=""> -->
      <h1>项目管理平台</h1>
      <h3>Project Management Platform</h3>
    </div>
    <div class="formBox">
      <Form class="formItem ivu-form-label-left" ref="formInline" :model="formInline" :rules="ruleInline">
        <FormItem prop="user">
          <Input type="text" v-model="formInline.user" placeholder="用户名">
          </Input>
        </FormItem>
        <!-- <div class="ivu-form-item">
                <label class="ivu-form-item-label" style="padding: 10px 12px 10px 0;">
                    用户名
                </label>
                <Input v-model="formInline.user"  ></Input>
            </div> -->
        <!-- <div class="ivu-form-item">
                <label class="ivu-form-item-label" style="padding: 10px 12px 10px 0;">
                    密&emsp;&emsp;码
                </label>
                <Input v-model="formInline.password" ></Input>
            </div> -->
        <FormItem prop="password" style="margin-bottom:10px;">
          <Input type="password" v-model="formInline.password" placeholder="密码">
          </Input>
        </FormItem>
        <div class="ivu-form-item">
          <label class="ivu-form-item-label">
            <Checkbox v-model="checked">记住密码</Checkbox>
          </label>
        </div>
        <FormItem>
          <Button style="height:52px;" long type="primary" @click="handleSubmit('formInline')">登 录</Button>
        </FormItem>
      </Form>
    </div>
    <div class="bottom">
      © 2016-2026 All Rights Reserved
    </div>
  </div>
</template>
<script>
// 引进方法
import { loginMethod } from 'network/login'
let Base64 = require('js-base64').Base64

export default {
  data () {
    return {
      checked: true,
      formInline: {
        user: '',
        password: ''
      },
      ruleInline: {
        user: [
          { required: true, message: '请输入用户名', trigger: 'blur' }
        ],
        password: [
          { required: true, message: '请输入密码', trigger: 'blur' }
        ]
      },
      menuList: []
    }
  },
  mounted () {
    this.getCookie();
  },
  methods: {
    //登录方法
    handleSubmit (name) {
      this.$refs[name].validate((valid) => {
        if(valid) {
          // 引用login.js中的方法，.then回调
          this.$Spin.show()
          loginMethod(this.formInline.user, this.$md5(this.formInline.password)).then(data => {
            this.$Spin.hide()
            if(data.rs === 1) {

              if(!data.menuList.length) {
                this.$Message.error('该用户尚未开通任何权限')
                return false;
              }

              const self = this;
              if(self.checked == true) {
                // console.log("checked == true");
                //传入账号名，密码，和保存天数3个参数
                self.setCookie(self.formInline.user, Base64.encode(this.formInline.password), 7);
              } else {
                // console.log("清空Cookie");
                //清空Cookie
                self.clearCookie();
              }

              this.$Message.success('登录成功!');
              // 将用户token,menuList,userName保存到vuex中
              this.$store.commit('refreshCurrentUserInfo',
                {
                  token: data._token_iben,
                  menuList: data.menuList,
                  requestPaths: data.requestPaths,
                  currentUserInfo: data.currentUserInfo
                })
              // if(data.userInfo.headImage) {
              //   this.$store.commit('refreshHeadImg', data.userInfo.headImage)
              // } else {
              //   this.$store.commit('refreshHeadImg', '')
              // }
              this.menuList = data.menuList
              this.$router.push('/' + this.menuList[0].url)
              //type用户类型：1-运营；2-渠道；3-企业
              // if(data.userInfo.type !== 3){
              //   this.menuList.some(item =>{
              //       if(item.children&& item.children.length>0){
              //           this.$router.push('/'+item.children[0].url)
              //           return true
              //       }
              //   })
              // }else{
              //     this.$router.push('/home')
              // }
            } else {
              if(data.data && data.data.errorMsg) {
                this.$Message.error(data.data.errorMsg);
              } else {
                this.$Message.error(data.errorMsg);
              }
            }
          })
        } else {
          this.$Message.error('登录失败!');
        }
      })
    },
    //设置cookie
    setCookie (c_name, c_pwd, exdays) {
      var exdate = new Date(); //获取时间
      exdate.setTime(exdate.getTime() + 24 * 60 * 60 * 1000 * exdays); //保存的天数
      //字符串拼接cookie
      window.document.cookie =
        "userName" + "=" + c_name + ";path=/;expires=" + exdate.toGMTString();
      window.document.cookie =
        "userPwd" + "=" + c_pwd + ";path=/;expires=" + exdate.toGMTString();
    },
    //获取cookie
    getCookie () {
      if(document.cookie.length > 0) {
        var arr = document.cookie.split("; "); //这里显示的格式需要切割一下自己可输出看下
        for(var i = 0; i < arr.length; i++) {
          var arr2 = arr[i].split("="); //再次切割
          //判断查找相对应的值
          if(arr2[0] == "userName") {
            this.formInline.user = arr2[1]; //保存到保存数据的地方
          } else if(arr2[0] == "userPwd") {
            this.formInline.password = Base64.decode(arr2[1]);
          }
        }
      }
    },
    //清除cookie
    clearCookie: function () {
      this.setCookie("", "", -1); //修改2值都为空，天数为负1天就好了
    }
  }
}
</script>
<style scoped>
.form .logo_big {
  position: absolute;
  top: 7%;
  left: 50%;
  width: 336px;
  margin-left: -168px;
  color: #fff;
  text-align: center;
}
.form .logo_big h1 {
  font-size: 44px;
  letter-spacing: 10px;
  margin-bottom: 0px;
  margin-top: 0;
}
.form .logo_big h3 {
  font-size: 16px;
  font-weight: normal;
  letter-spacing: 3px;
}
.form .formBox {
  width: 420px;
  height: 346px;
  position: absolute;
  top: 50%;
  left: 55%;
  /* margin-left: -180px; */
  margin-top: -160px;
  background-color: #ffffff;
  border-radius: 5px;
}
.form .logo_big img {
  width: 100%;
}
.form .formItem {
  width: 338px;
  margin-left: 41px;
  margin-top: 48px;
}
.bottom {
  width: 100%;
  text-align: center;
  font-family: STHeitiSC-Medium;
  font-size: 12px;
  font-weight: normal;
  font-stretch: normal;
  letter-spacing: 1px;
  color: #ffffff;
  position: absolute;
  bottom: 6%;
}
</style>

