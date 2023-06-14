<template>
  <!-- 运营角色 -->
  <div>
    <div class="container_title">
      <!-- 面包屑 -->
      <Breadcrumb>
        <BreadcrumbItem>角色管理</BreadcrumbItem>
      </Breadcrumb>
    </div>
    <div class="container_info">
      <div class="role_container">
        <div class="role_left">
          <!-- <div class="ops_btn">
            <Button type="primary" ghost @click="addModal">新增</Button>
          </div> -->
          <div class="roleList">
            <div v-for="info in pageInfo" :key="info.roleId">
              <p @click="changeMenuById(info.roleId)" :class="{active:curRoleId===info.roleId}" style="cursor:pointer">
                <span class="text_ellipsis">{{info.roleName}}</span>
                <span class="role_btn">
                  <!-- <i class="iconfont icon-bianji" v-if="info.roleId !=1" @click.stop="modifyModal(info.roleId)"></i>
                  <Icon type="md-trash" size="20" v-if="info.roleId !=1" @click.stop="deleteModal(info.roleId)" /> -->
                </span>
              </p>
            </div>
          </div>
        </div>
        <div class="role_right">
          <Tabs value="name1">
            <TabPane label="权限控制" name="name1">
              <!-- <div class="menuItem">
                <Checkbox :value="checkAllfunctionIds" @click.prevent.native="handleCheckAll">
                  <em>机器人功能</em>
                </Checkbox>
                <div class="menu_list">
                  <p class="menu_second">
                    <CheckboxGroup v-model="functionIds" class="functionIds_box" @on-change="checkAllGroupChange">
                      <Checkbox class="functionIds_item" :label="info.fId" v-for="info in functionManageList" :key="info.fId">{{info.fName}}</Checkbox>
                    </CheckboxGroup>
                  </p>
                </div>
              </div> -->
              <div id="role_content">
                <div v-for="m1 in menuList" :key="m1.menuId" class="menuItem menuItem2">
                  <label @click="checkMe($event)" :class="'ivu-checkbox-wrapper ivu-checkbox-default' + (m1.roleMenuId!=null?' ivu-checkbox-wrapper-checked':'')" :checkAll="m1.menuId" :value="m1.menuId">
                    <span :class="'ivu-checkbox' + (m1.roleMenuId!=null?' ivu-checkbox-checked':'')">
                      <span class="ivu-checkbox-inner"></span>
                      <input type="checkbox" class="ivu-checkbox-input"></span>
                    {{m1.menuName}}
                  </label>
                  <div class="menu_list" v-if="m1.children.length>0">
                    <div v-for="m2 in m1.children" :key="m2.menuId">
                      <div class="menu_first">
                        <label @click="checkMe($event)" :class="'ivu-checkbox-wrapper ivu-checkbox-default' + (m2.roleMenuId!=null?' ivu-checkbox-wrapper-checked':'')" :group="m1.menuId" :checkAll="m2.menuId" :value="m2.menuId">
                          <span :class="'ivu-checkbox' + (m2.roleMenuId!=null?' ivu-checkbox-checked':'')">
                            <span class="ivu-checkbox-inner"></span>
                            <input type="checkbox" class="ivu-checkbox-input"></span>
                          {{m2.menuName}}
                        </label>
                      </div>
                      <div v-if="m2.children.length>0" class="menu_child">
                        <div class="menu_first" v-for="m3 in m2.children" :key="m3.menuId">
                          <label @click="checkMe($event)" :class="'ivu-checkbox-wrapper ivu-checkbox-default' + (m3.roleMenuId!=null?' ivu-checkbox-wrapper-checked':'')" :group="m2.menuId" :checkAll="m3.menuId" :value="m3.menuId">
                            <span :class="'ivu-checkbox' + (m3.roleMenuId!=null?' ivu-checkbox-checked':'')">
                              <span class="ivu-checkbox-inner"></span>
                              <input type="checkbox" class="ivu-checkbox-input"></span>
                            {{m3.menuName}}
                          </label>
                          <div v-if="m3.children.length>0" class="menu_second">
                            <span v-for="m4 in m3.children" :key="m4.menuId">
                              <label @click="checkMe($event)" :class="'ivu-checkbox-wrapper ivu-checkbox-default' + (m4.roleMenuId!=null?' ivu-checkbox-wrapper-checked':'')" :group="m3.menuId" :checkAll="m4.menuId" :value="m4.menuId">
                                <span :class="'ivu-checkbox' + (m4.roleMenuId!=null?' ivu-checkbox-checked':'')">
                                  <span class="ivu-checkbox-inner"></span>
                                  <input type="checkbox" class="ivu-checkbox-input"></span>
                                {{m4.menuName}}
                              </label>
                            </span>
                          </div>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
                <div class="save_btn">
                  <Button type="primary" size='small' @click="saveRoleMenu">保存</Button>
                </div>
              </div>
            </TabPane>
          </Tabs>
        </div>
      </div>
    </div>
    <!-- 模态框 -->
    <!-- <Modal class="sys_modal" class-name="vertical_modal" v-model="modal_delete" width="330">
      <div class="modal_body modal_body_delete">
        <p><img src="~/assets/images/warn_tip.png" alt="">您确定要删除该角色吗?</p>
      </div>
      <div slot="footer">
        <Button type="primary" class="confirm_btn" ghost @click="deleteMethod(curRoleId)">确定</Button>
        <Button type="default" class="clear_btn" @click="closeDialog()">取消</Button>
      </div>
    </Modal>
    <Modal class="sys_modal" v-model="modal_add" width="450" title="新增角色">
      <div class="modal_body">
        <Form ref="addInfo" label-position="left" :model="addInfo" :rules="ruleValidate" :label-width="80">
          <FormItem label="角色名称" prop="roleName">
            <Input v-model="addInfo.roleName"></Input>
          </FormItem>
          <FormItem label="角色描述" prop="descriptions">
            <Input v-model="addInfo.descriptions" type="textarea"></Input>
          </FormItem>
        </Form>
      </div>
      <div slot="footer">
        <Button type="primary" class="confirm_btn" ghost @click="addInfoMethod('addInfo')">确定</Button>
        <Button type="default" class="clear_btn" @click="cancel('addInfo')">取消</Button>
      </div>
    </Modal>
    <Modal class="sys_modal" v-model="modal_modify" width="450" title="修改角色">
      <div class="modal_body">
        <Form ref="modifyInfo" label-position="left" :model="modifyInfo" :rules="ruleValidate" :label-width="80">
          <FormItem label="角色名称" prop="roleName">
            <Input v-model="modifyInfo.roleName"></Input>
          </FormItem>
          <FormItem label="角色描述" prop="descriptions">
            <Input v-model="modifyInfo.descriptions" type="textarea"></Input>
          </FormItem>
        </Form>
      </div>
      <div slot="footer">
        <Button type="primary" class="confirm_btn" ghost @click="modifyInfoMethod('modifyInfo')">确定</Button>
        <Button type="default" class="clear_btn" @click="cancel('modifyInfo')">取消</Button>
      </div>
    </Modal> -->
  </div>
</template>
<script>
import { checkMe, getSelectIds } from '@/util/newCommon.js'
export default {
  name: "RoleManage",
  data () {
    return {
      modal_delete: false,
      modal_add: false,
      modal_modify: false,
      pageInfo: [],
      curRoleId: '',// 当前角色
      menuList: [],// 菜单权限列表
      functionManageList: [],//机器人功能列表
      functionIds: [],//机器人功能
      checkAllfunctionIds: false,
      addInfo: {
        roleName: '',
        descriptions: ''
      },
      modifyInfo: {
        roleName: '',
        descriptions: ''
      },
      ruleValidate: {
        roleName: [
          { required: true, message: '请输入角色名称', trigger: 'blur' },
        ],
        descriptions: [
          { required: true, message: '请输入角色描述', trigger: 'blur' }
        ]
      }
    }
  },
  created () {
    this.queryPageInfo();
    // this.queryFunctionManageList();//查询机器人功能列表
  },
  methods: {
    // handleCheckAll () {
    //   this.checkAllfunctionIds = !this.checkAllfunctionIds;
    //   if(this.checkAllfunctionIds) {
    //     this.functionManageList.forEach(item => {
    //       this.functionIds.push(item.fId)
    //     })
    //   } else {
    //     this.functionIds = [];
    //   }
    // },
    // checkAllGroupChange (data) {
    //   if(data.length === this.functionManageList.length) {
    //     this.checkAllfunctionIds = true;
    //   } else if(data.length > 0) {
    //     this.checkAllfunctionIds = true;
    //   } else {
    //     this.checkAllfunctionIds = false;
    //   }
    // },
    //check方法
    checkMe (thiss) {
      checkMe($(thiss.currentTarget));
    },
    // 列表
    queryPageInfo () {
      this.$Spin.show()
      this.$post('/role/queryList', {}).then(data => {
        this.$Spin.hide()
        if(data.rs === 1) {
          this.pageInfo = data.data;
          this.curRoleId = this.pageInfo[0].roleId
          this.queryMenuListInfo()
        } else {
          if(data.data && data.data.errorMsg) {
            this.$Message.error(data.data.errorMsg);
          } else {
            this.$Message.error(data.errorMsg);
          }
        }
      })
    },
    // 菜单权限列表
    queryMenuListInfo () {
      this.menuList = []
      this.$Spin.show()
      this.$post('/role/queryMenu', {
        roleId: this.curRoleId
      }).then(data => {
        this.$Spin.hide()
        if(data.rs === 1) {
          this.menuList = data.data;
          // if(data.robFunctionIds) {
          //   this.checkAllfunctionIds = true;//机器人功能全选选中
          //   this.functionIds = data.robFunctionIds.split(',')
          //   // 转化为数值类型，forEach竟然不行
          //   for(let i = 0; i < this.functionIds.length; i++) {
          //     this.functionIds[i] = Number(this.functionIds[i])
          //   }
          // }
          //console.log(this.menuList)
        } else {
          if(data.data && data.data.errorMsg) {
            this.$Message.error(data.data.errorMsg);
          } else {
            this.$Message.error(data.errorMsg);
          }
        }
      })
    },
    // 根据角色查看菜单权限
    changeMenuById (roleId) {
      this.curRoleId = roleId
      this.queryMenuListInfo()
    },
    // 删除弹框
    deleteModal (roleId) {
      this.curRoleId = roleId
      this.modal_delete = true
    },
    // 确认删除
    deleteMethod (roleId) {
      this.$post('/pm/delEnterpriseRole', {
        roleId: roleId
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
      this.addInfo = {}
      this.modal_add = true
    },
    // 确认新增
    addInfoMethod (name) {
      this.$refs[name].validate((valid) => {
        if(valid) {
          this.$Spin.show()
          this.$post('/pm/addEnterpriseRole', {
            roleName: this.addInfo.roleName,
            descriptions: this.addInfo.descriptions
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
    modifyModal (roleid) {
      this.modal_modify = true
      this.$post('/pm/getEnterpriseRoleInfo', {
        roleid: roleid
      }).then(data => {
        if(data.rs === 1) {
          this.modifyInfo = data.RoleInfo
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
          this.$post('/pm/editEnterpriseRole', {
            roleId: this.modifyInfo.roleId,
            roleName: this.modifyInfo.roleName,
            descriptions: this.modifyInfo.descriptions
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
    // 保存菜单权限
    saveRoleMenu () {
      this.$Spin.show()
      var menuIds = getSelectIds("role_content").join(',');
      console.log(menuIds);
      // let robFunctionIds = this.functionIds
      this.$post('/role/savePmData', {
        roleId: this.curRoleId,
        menuIds: menuIds,
        // robFunctionIds: robFunctionIds
      }).then(data => {
        this.$Spin.hide()
        if(data.rs === 1) {
          this.$Message.success('保存成功!');
        } else {
          if(data.data && data.data.errorMsg) {
            this.$Message.error(data.data.errorMsg);
          } else {
            this.$Message.error(data.errorMsg);
          }
        }
      })
    },
    cancel (name) {
      this.modal_add = false
      this.modal_modify = false
      this.$refs[name].resetFields();
    },
    closeDialog () {
      this.modal_delete = false
    }
  }
}
</script>
<style scoped>
.role_container {
  display: flex;
  justify-content: space-between;
}
.functionIds_box {
  display: flex;
}
.menuItem .menu_child .menu_first {
  padding-left: 35px;
}
.menuItem2 .menu_list .menu_first {
  line-height: 35px;
  border-bottom: 1px solid #ddd;
}
</style>