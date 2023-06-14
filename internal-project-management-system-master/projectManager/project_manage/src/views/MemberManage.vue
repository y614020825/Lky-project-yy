<template>
  <!-- 人员管理 -->
  <div>
    <div class="container_title">
      <!-- 面包屑 -->
      <Breadcrumb>
        <BreadcrumbItem>人员管理</BreadcrumbItem>
      </Breadcrumb>
      <!-- 操作按钮 -->
      <div class="ops_btn">
        <Button type="primary" ghost size='small' @click="addModal">新增</Button>
      </div>
    </div>
    <div class="container_info">
      <div class="container_box">
        <!-- 查询条件 -->
        <div class="query_option">
          <Input style="width: 200px;margin-right:10px;" v-model="search.loginAccount" placeholder="请输入登录账号"></Input>
          <Input style="width: 200px" v-model="search.fullName" placeholder="请输入人员姓名"></Input>
          <span class="query_btn">
            <Button type="info" @click="queryPageInfo">查询</Button>
            <Button @click="clearSearch">重置</Button>
          </span>
        </div>
        <!-- 表格列表 -->
        <div class="container_table">
          <Table :columns="columns" :data="pageInfo.data"></Table>
        </div>
        <div class="pageInfo">
          <Page size="small" :total="total" :page-size='pagesize' @on-change="handleChange" show-total show-elevator></Page>
        </div>
      </div>
    </div>
    <!-- 模态框 -->
    <Modal class="sys_modal" class-name="vertical_modal" v-model="modal_delete" width="316">
      <div class="modal_body modal_body modal_body_delete">
        <p><img src="~/assets/images/warn_tip.png" alt="">您确定要删除该人员吗?</p>
      </div>
      <div slot="footer">
        <Button type="primary" class="confirm_btn" ghost @click="deleteMethod(userId)">确定</Button>
        <Button type="default" class="clear_btn" @click="cancel()">取消</Button>
      </div>
    </Modal>
    <Modal class="sys_modal" v-model="modal_add" width="450" title="新增人员">
      <div class="modal_body">
        <Form ref="addInfo" label-position="left" :model="addInfo" :rules="ruleValidate" :label-width="100">
          <FormItem label="登录账号" prop="loginAccount">
            <Input v-model="addInfo.loginAccount"></Input>
          </FormItem>
          <FormItem label="密码" prop="realPasswd">
            <Input v-model="addInfo.realPasswd" type="password" autocomplete="new-password"></Input>
          </FormItem>
          <FormItem label="真实姓名" prop="fullName">
            <Input v-model="addInfo.fullName"></Input>
          </FormItem>
          <FormItem label="角色" prop="roleId">
            <Select v-model="addInfo.roleId">
              <Option :value="info.roleId" v-for="info in roleList" :key="info.roleId">{{info.roleName}}</Option>
            </Select>
          </FormItem>
        </Form>
      </div>
      <div slot="footer">
        <Button type="primary" class="confirm_btn" ghost @click="addInfoMethod('addInfo')">确定</Button>
        <Button type="default" class="clear_btn" @click="cancel('addInfo')">取消</Button>
      </div>
    </Modal>
    <Modal class="sys_modal" v-model="modal_modify" width="450" title="修改人员">
      <div class="modal_body">
        <Form ref="modifyInfo" label-position="left" :model="modifyInfo" :rules="ruleValidate" :label-width="100">
          <FormItem label="登录账号" prop="loginAccount">
            <Input v-model="modifyInfo.loginAccount"></Input>
          </FormItem>
          <FormItem label="密码" autocomplete="new-password">
            <Input v-model="modifyInfo.realPasswd" type="password"></Input>
          </FormItem>
          <FormItem label="真实姓名" prop="fullName">
            <Input v-model="modifyInfo.fullName"></Input>
          </FormItem>
          <FormItem label="角色">
            <Select v-model="modifyInfo.roleId" disabled>
              <Option :value="info.roleId" v-for="info in roleList" :key="info.roleId">{{info.roleName}}</Option>
            </Select>
          </FormItem>
        </Form>
      </div>
      <div slot="footer">
        <Button type="primary" class="confirm_btn" ghost @click="modifyInfoMethod('modifyInfo')">确定</Button>
        <Button type="default" class="clear_btn" @click="cancel('modifyInfo')">取消</Button>
      </div>
    </Modal>
  </div>
</template>
<script>
import { formatDate } from '@/util/date.js'
export default {
  name: "OperationAccount",
  data () {
    const validatePhone = (rule, value, callback) => {
      if(!value) {
        return callback(new Error('请输入手机号'));
      } else if(!/^1[34578]\d{9}$/.test(value)) {
        callback('手机号格式不正确');
      } else {
        callback();
      }
    };
    return {
      search: {},
      total: 0,// 初始化信息总条数
      pageNow: 1,
      pagesize: 20,// 每页显示多少条
      userId: '',// 删除的时候传值
      modal_delete: false,
      modal_add: false,
      modal_modify: false,
      addInfo: {},
      roleList: [],
      ruleValidate: {
        loginAccount: [
          { required: true, message: '请输入登录账号', trigger: 'blur' },
        ],
        fullName: [
          { required: true, message: '请输入真实姓名', trigger: 'blur' }
        ],
        realPasswd: [
          { required: true, message: '请输入密码', trigger: 'blur' }
        ],
        roleId: [
          { required: true, type: 'number', message: '请选择角色', trigger: 'change' }
        ]
      },
      modifyInfo: {},
      columns: [
        {
          "title": "登录账号",
          "align": "center",
          "key": "loginAccount"
        }, {
          "title": "真实姓名",
          "align": "center",
          "key": "fullName"
        }, {
          "title": "角色",
          "align": "center",
          "key": "roleName"
        }, {
          "title": "修改时间",
          "align": "center",
          "key": "modifyDate",
          render: (h, params) => {
            return h(
              "span",
              formatDate(new Date(params.row.modifyDate), "yyyy-MM-dd hh:mm:ss")
            );
          }
        }, {
          'title': '操作',
          'align': 'center',
          'key': 'action',
          render: (h, params) => {
            return h('div', [
              h('span', {
                class: 'iconfont icon-bianji',
                style: {
                  fontSize: '18px', // 改变icon的样式
                  cursor: 'pointer',
                },
                on: {
                  click: () => {
                    // 点击操作事件
                    this.modifyModal(params.row.userId)
                  }
                }
              }),
              h('Icon', {
                props: {
                  type: 'md-trash' // iview自带的删除icon
                },
                style: {
                  fontSize: '20px', // 改变icon的样式
                  cursor: 'pointer',
                  marginLeft: '4px',
                },
                on: {
                  click: () => {
                    this.deleteModal(params.row.userId)
                  }
                }
              })
            ])
          }
        }],
      pageInfo: {}

    }
  },
  created () {
    this.queryRoleListInfo();
    this.queryPageInfo();
  },
  methods: {
    // 角色列表
    queryRoleListInfo () {
      this.$Spin.show()
      this.$post('/role/queryList', {}).then(data => {
        this.$Spin.hide()
        if(data.rs === 1) {
          this.roleList = data.data;
        } else {
          if(data.data && data.data.errorMsg) {
            this.$Message.error(data.data.errorMsg);
          } else {
            this.$Message.error(data.errorMsg);
          }
        }
      })
    },
    // 获取分页信息
    queryPageInfo () {
      let params = {
        pagenum: this.pageNow,
        pagesize: this.pagesize,
        loginAccount: this.search.loginAccount || '',
        fullName: this.search.fullName || ''
      }
      this.$Spin.show()
      this.$post('/user/queryPageList', params).then(data => {
        this.$Spin.hide()
        if(data.rs === 1) {
          this.pageInfo = data.pageInfo;
          this.total = data.pageInfo.totalRows;//总数
          this.chosePage = data.pageInfo.page;//选择页
          this.pageNow = data.pageInfo.page;//当前页
        } else {
          if(data.data && data.data.errorMsg) {
            this.$Message.error(data.data.errorMsg);
          } else {
            this.$Message.error(data.errorMsg);
          }
        }
      })
    },
    //分页
    handleChange (page) {
      this.pageNow = page;//赋值当前页
      this.queryPageInfo();
    },
    // 删除弹框
    deleteModal (userId) {
      this.userId = userId
      this.modal_delete = true
    },
    // 确认删除
    deleteMethod (userId) {
      this.$post('/user/delete', {
        userId: userId
      }).then(data => {
        if(data.rs === 1) {
          this.modal_delete = false
          this.$Message.success('删除成功!');
          this.queryPageInfo();
        } else {
          if(data.data && data.data.errorMsg) {
            this.$Message.error(data.data.errorMsg);
          } else {
            this.$Message.error(data.errorMsg);
          }
        }
      })
    },
    // 新增弹框
    addModal () {
      this.modal_add = true
    },
    // 确认新增
    addInfoMethod (name) {
      this.$refs[name].validate((valid) => {
        if(valid) {
          this.$Spin.show()
          this.$post('/user/add', {
            loginAccount: this.addInfo.loginAccount,
            realPasswd: this.addInfo.realPasswd,
            roleId: this.addInfo.roleId,
            fullName: this.addInfo.fullName,
          }).then(data => {
            this.$Spin.hide()
            if(data.rs === 1) {
              this.modal_add = false
              this.$Message.success('新增成功!');
              this.$refs[name].resetFields();// 清空
              this.queryPageInfo();
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
    // 编辑弹框
    modifyModal (userId) {
      this.modal_modify = true
      this.$post('/user/queryDetail', {
        userId: userId
      }).then(data => {
        if(data.rs === 1) {
          this.modifyInfo = data.data
        } else {
          if(data.data && data.data.errorMsg) {
            this.$Message.error(data.data.errorMsg);
          } else {
            this.$Message.error(data.errorMsg);
          }
        }
      })
    },
    // 确认编辑
    modifyInfoMethod (name) {
      this.$refs[name].validate((valid) => {
        if(valid) {
          this.$Spin.show()
          this.$post('/user/update', {
            userId: this.modifyInfo.userId,
            loginAccount: this.modifyInfo.loginAccount,
            realPasswd: this.modifyInfo.realPasswd || '',
            fullName: this.modifyInfo.fullName,
          }).then(data => {
            this.$Spin.hide()
            if(data.rs === 1) {
              this.modal_modify = false
              this.$Message.success('修改成功!');
              this.queryPageInfo();
            } else {
              if(data.data && data.data.errorMsg) {
                this.$Message.error(data.data.errorMsg);
              } else {
                this.$Message.error(data.errorMsg);
              }
            }
          })
        } else {
          this.$Message.error('修改失败!');
        }
      })
    },
    cancel (name) {
      this.modal_add = false
      this.modal_modify = false
      this.modal_delete = false
      if(name) {
        this.$refs[name].resetFields();
      }
      // 清空
      this.addInfo = {}
    },
    // 重置
    clearSearch () {
      this.search = {}
    }
  }
}
</script>
<style scoped>
</style>