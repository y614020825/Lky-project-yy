<template>
  <div class="header">
    <div class="header_right">
      <div class="avatar" @click="resetPassword">
        <!-- <img src="~/assets/images/default_headImage.png" alt=""> -->
        <span>{{$store.state.currentUserInfo.loginAccount}}</span>
      </div>
      <div class="quit" @click="quit">
        <!-- <img src="~/assets/images/out.png" alt=""> -->
        <span>退出</span>
      </div>
      <Modal class="sys_modal" class-name="vertical_modal" v-model="quitModal" width="316">
        <div class="modal_body modal_body_delete">
          <p><img src="~/assets/images/warn_tip.png" alt="">您确定要退出系统吗?</p>
        </div>
        <div slot="footer">
          <Button type="primary" class="confirm_btn" ghost @click="ok()">确定</Button>
          <Button type="default" class="clear_btn" @click="cancel()">取消</Button>
        </div>
      </Modal>
      <Modal class="sys_modal" v-model="modal_add" width="450" title="修改密码">
        <div class="modal_body">
          <Form ref="addInfo" label-position="left" :model="addInfo" :rules="ruleValidate" :label-width="100">
            <FormItem label="原密码" prop="oldPwd">
              <Input v-model="addInfo.oldPwd" type="password" autocomplete="new-password"></Input>
            </FormItem>
            <FormItem label="新密码" prop="newPwdOne">
              <Input v-model="addInfo.newPwdOne" type="password" autocomplete="new-password"></Input>
            </FormItem>
            <FormItem label="确认新密码" prop="newPwdTwo">
              <Input v-model="addInfo.newPwdTwo" type="password" autocomplete="new-password"></Input>
            </FormItem>
          </Form>
        </div>
        <div slot="footer">
          <Button type="primary" class="confirm_btn" ghost @click="addInfoMethod('addInfo')">确定</Button>
          <Button type="default" class="clear_btn" @click="cancel('addInfo')">取消</Button>
        </div>
      </Modal>
    </div>
  </div>
</template>

<script>
// @ is an alias to /src
// 引进方法
import { quitMethod } from 'network/login'
export default {
  name: 'Header',
  data () {
    return {
      quitModal: false,
      modal_add: false,
      addInfo: {},
      ruleValidate: {
        oldPwd: [
          { required: true, message: '请输入原密码', trigger: 'blur' },
        ],
        newPwdOne: [
          { required: true, message: '请输入新密码', trigger: 'blur' }
        ],
        newPwdTwo: [
          { required: true, message: '请输入确认新密码', trigger: 'blur' }
        ]
      },
    }
  },
  methods: {
    resetPassword () {
      this.addInfo = {}
      this.modal_add = true
    },
    addInfoMethod (name) {
      this.$refs[name].validate((valid) => {
        if(valid) {
          console.log(this.addInfo)
          if(this.addInfo.newPwdOne !== this.addInfo.newPwdTwo) {
            this.$Message.error('两次输入的新密码必须相同')
            return false
          }
          this.$Spin.show()
          this.$post('/user/resetPwd', this.addInfo).then(data => {
            this.$Spin.hide()
            if(data.rs === 1) {
              this.modal_add = false
              this.$Message.success('修改成功!');
              this.$refs[name].resetFields();// 清空
              this.ok()
            } else {
              if(data.data && data.data.errorMsg) {
                this.$Message.error(data.data.errorMsg);
              } else {
                this.$Message.error(data.errorMsg);
              }
            }
          })
        } else {
          this.$Message.error('新增失败!');
        }
      })
    },
    quit () {
      // 打开模态框
      this.quitModal = true
    },
    ok () {
      //退出方法
      quitMethod(this.$store.state.userName, this.$store.state.token).then(() => {
        // 清除localStorage token值
        localStorage.removeItem('token');// token值
        localStorage.removeItem('menuList');// 菜单
        localStorage.removeItem('requestPaths');// 权限
        localStorage.removeItem('userName');// userName
        localStorage.removeItem('headImg');// headImg

        this.$router.push('/login')
      })
    },
    cancel (name) {
      this.quitModal = false
      this.modal_add = false
      if(name) {
        this.$refs[name].resetFields()
      }
      // 清空
      this.addInfo = {}
    }
  }
}
</script>
<style scoped>
.header {
  width: 100%;
  height: 40px;
  position: relative;
  top: 0;
  left: 0;
  border-bottom: 1px solid #e3e3e3;
}
.header_right {
  position: absolute;
  top: 0;
  right: 15px;
  height: 40px;
  display: flex;
  /* 垂直居中，两端对齐 */
  /* align-items: center;
  justify-content: space-between; */
}
.avatar {
  margin-right: 20px;
}
.avatar,
.quit {
  display: flex;
  align-items: center;
  border-left: 1px solid #e3e3e3;
  padding-left: 10px;
  cursor: pointer;
}
.avatar span,
.quit span {
  margin-left: 10px;
}
.avatar img {
  width: 30px;
  height: 30px;
  vertical-align: middle;
  align-items: center;
}
.quit img {
  width: 13px;
  height: 13px;
  vertical-align: middle;
  align-items: center;
}
</style>
