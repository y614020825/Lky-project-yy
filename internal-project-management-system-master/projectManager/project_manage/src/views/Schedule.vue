<template>
  <!-- 技术排期 -->
  <div class="schedule">
    <div class="container_title">
      <!-- 面包屑 -->
      <Breadcrumb>
        <BreadcrumbItem>技术排期 —— <span style="font-size:16px;">项目名称：{{$route.query.name}}</span>
          <div style="font-size:16px;margin-left:118px" v-if="$route.query.demandName">需求名称：{{$route.query.demandName}}</div>
        </BreadcrumbItem>
      </Breadcrumb>
      <!-- 操作按钮 -->
      <div class="ops_btn">
        <Button type="primary" ghost size='small' @click="$router.go(-1)" style="margin-right:10px">返回</Button>
        <Button type="success" ghost size='small' @click="addModal">新增</Button>
      </div>
    </div>
    <div class="container_info">
      <div class="container_box">
        <!-- 查询条件 -->
        <div class="query_option">
          <Select style="width: 190px;margin-right:10px;" v-model="search.teamName" placeholder="请选择团队">
            <Option :value='1'>Andriod</Option>
            <Option :value='2'>Java</Option>
            <Option :value='3'>Web</Option>
            <Option :value='4'>UI</Option>
            <Option :value='5'>IOS</Option>
            <Option :value='6'>硬件</Option>
            <Option :value='7'>测试</Option>
          </Select>
          <Select style="width: 190px;margin-right:10px;" v-model="search.taskState" placeholder="请选择任务状态">
            <Option :value='1'>待启动</Option>
            <Option :value='2'>进行中</Option>
            <Option :value='3'>暂停中</Option>
            <Option :value='4'>测试中</Option>
            <Option :value='5'>已完成</Option>
            <Option :value='6'>已逾期</Option>
          </Select>
          <Input style="width: 190px;margin-right:10px" v-model="search.taskName" placeholder="请输入任务名称"></Input>
          <Input style="width: 190px;" v-model="search.developer" placeholder="请输入开发人员"></Input>
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
        <p><img src="~/assets/images/warn_tip.png" alt="">您确定要删除该排期吗?</p>
      </div>
      <div slot="footer">
        <Button type="primary" class="confirm_btn" ghost @click="deleteMethod(scheduleId)">确定</Button>
        <Button type="default" class="clear_btn" @click="cancel()">取消</Button>
      </div>
    </Modal>
    <Modal class="sys_modal" v-model="modal_add" width="450" title="新增排期">
      <div class="modal_body">
        <Form ref="addInfo" label-position="left" :model="addInfo" :label-width="100">
          <FormItem label="团队名称">
            <Select v-model="addInfo.teamName">
              <Option :value='1'>Andriod</Option>
              <Option :value='2'>Java</Option>
              <Option :value='3'>Web</Option>
              <Option :value='4'>UI</Option>
              <Option :value='5'>IOS</Option>
              <Option :value='6'>硬件</Option>
              <Option :value='7'>测试</Option>
            </Select>
          </FormItem>
          <FormItem label="任务名称">
            <Input v-model="addInfo.taskName"></Input>
          </FormItem>
          <FormItem label="任务状态">
            <Select v-model="addInfo.taskState">
              <Option :value='1'>待启动</Option>
              <Option :value='2'>进行中</Option>
              <Option :value='3'>暂停中</Option>
              <Option :value='4'>测试中</Option>
              <Option :value='5'>已完成</Option>
              <Option :value='6'>已逾期</Option>
            </Select>
          </FormItem>
          <FormItem label="开发人员">
            <Input v-model="addInfo.developer"></Input>
          </FormItem>
          <FormItem label="计划人时">
            <Input v-model="addInfo.planHours" type="number"></Input>
          </FormItem>
          <FormItem label="计划人天">
            <Input v-model="addInfo.planDays" type="number"></Input>
          </FormItem>
          <FormItem label="计划开始">
            <DatePicker type="date" format="yyyy-MM-dd" v-model="addInfo.planStartDate" @on-change="addInfo.planStartDate=$event"></DatePicker>
          </FormItem>
          <FormItem label="计划结束">
            <DatePicker type="date" format="yyyy-MM-dd" v-model="addInfo.planEndDate" @on-change="addInfo.planEndDate=$event"></DatePicker>
          </FormItem>
          <FormItem label="实际开始">
            <DatePicker type="date" format="yyyy-MM-dd" v-model="addInfo.actualStartDate" @on-change="addInfo.actualStartDate=$event"></DatePicker>
          </FormItem>
          <FormItem label="实际结束">
            <DatePicker type="date" format="yyyy-MM-dd" v-model="addInfo.actualEndDate" @on-change="addInfo.actualEndDate=$event"></DatePicker>
          </FormItem>
          <FormItem label="实际人时">
            <Input v-model="addInfo.actualHours" type="number"></Input>
          </FormItem>
          <FormItem label="实际人天">
            <Input v-model="addInfo.actualDays" type="number"></Input>
          </FormItem>
          <FormItem label="开始偏差">
            <Input v-model="addInfo.deviationStartDate"></Input>
          </FormItem>
          <FormItem label="结束偏差">
            <Input v-model="addInfo.deviationEndDate"></Input>
          </FormItem>
          <FormItem label="人时偏差">
            <Input v-model="addInfo.deviationHours"></Input>
          </FormItem>
          <FormItem label="人天偏差">
            <Input v-model="addInfo.deviationDays"></Input>
          </FormItem>
          <FormItem label="备注">
            <Input v-model="addInfo.remarks" type="textarea" :rows="4"></Input>
          </FormItem>
        </Form>
      </div>
      <div slot="footer">
        <Button type="primary" class="confirm_btn" ghost @click="addInfoMethod('addInfo')">确定</Button>
        <Button type="default" class="clear_btn" @click="cancel('addInfo')">取消</Button>
      </div>
    </Modal>
    <Modal class="sys_modal" v-model="modal_modify" width="450" title="修改排期">
      <div class="modal_body">
        <Form ref="modifyInfo" label-position="left" :model="modifyInfo" :label-width="100">
          <FormItem label="团队名称">
            <Select v-model="modifyInfo.teamName">
              <Option :value='1'>Andriod</Option>
              <Option :value='2'>Java</Option>
              <Option :value='3'>Web</Option>
              <Option :value='4'>UI</Option>
              <Option :value='5'>IOS</Option>
              <Option :value='6'>硬件</Option>
              <Option :value='7'>测试</Option>
            </Select>
          </FormItem>
          <FormItem label="任务名称">
            <Input v-model="modifyInfo.taskName"></Input>
          </FormItem>
          <FormItem label="任务状态">
            <Select v-model="modifyInfo.taskState">
              <Option :value='1'>待启动</Option>
              <Option :value='2'>进行中</Option>
              <Option :value='3'>暂停中</Option>
              <Option :value='4'>测试中</Option>
              <Option :value='5'>已完成</Option>
              <Option :value='6'>已逾期</Option>
            </Select>
          </FormItem>
          <FormItem label="开发人员">
            <Input v-model="modifyInfo.developer"></Input>
          </FormItem>
          <FormItem label="计划人时">
            <Input v-model="modifyInfo.planHours" type="number"></Input>
          </FormItem>
          <FormItem label="计划人天">
            <Input v-model="modifyInfo.planDays" type="number"></Input>
          </FormItem>
          <FormItem label="计划开始">
            <DatePicker type="date" format="yyyy-MM-dd" :value="modifyInfo.planStartDate" @on-change="modifyInfo.planStartDate=$event"></DatePicker>
          </FormItem>
          <FormItem label="计划结束">
            <DatePicker type="date" format="yyyy-MM-dd" :value="modifyInfo.planEndDate" @on-change="modifyInfo.planEndDate=$event"></DatePicker>
          </FormItem>
          <FormItem label="实际开始">
            <DatePicker type="date" format="yyyy-MM-dd" :value="modifyInfo.actualStartDate" @on-change="modifyInfo.actualStartDate=$event"></DatePicker>
          </FormItem>
          <FormItem label="实际结束">
            <DatePicker type="date" format="yyyy-MM-dd" :value="modifyInfo.actualEndDate" @on-change="modifyInfo.actualEndDate=$event"></DatePicker>
          </FormItem>
          <FormItem label="实际人时">
            <Input v-model="modifyInfo.actualHours" type="number"></Input>
          </FormItem>
          <FormItem label="实际人天">
            <Input v-model="modifyInfo.actualDays" type="number"></Input>
          </FormItem>
          <FormItem label="开始偏差">
            <Input v-model="modifyInfo.deviationStartDate"></Input>
          </FormItem>
          <FormItem label="结束偏差">
            <Input v-model="modifyInfo.deviationEndDate"></Input>
          </FormItem>
          <FormItem label="人时偏差">
            <Input v-model="modifyInfo.deviationHours"></Input>
          </FormItem>
          <FormItem label="人天偏差">
            <Input v-model="modifyInfo.deviationDays"></Input>
          </FormItem>
          <FormItem label="备注">
            <Input v-model="modifyInfo.remarks" type="textarea" :rows="4"></Input>
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
  name: "Schedule",
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
      // defaultRoleId: '',//默认角色id
      scheduleId: '',// 删除的时候传值
      modal_delete: false,
      modal_add: false,
      modal_modify: false,
      addInfo: {},
      roleList: [],
      modifyInfo: {},
      columns: [
        {
          "title": "团队名称",
          "align": "center",
          "key": "teamName",
          "minWidth": "60px",
          render: (h, params) => {
            var name = params.row.teamName
            return h('span',
              name == 1 ? 'Andriod' : name == 2 ? 'Java' : name == 3 ? 'Web' : name == 4 ? 'UI' : name == 5 ? 'IOS' : name == 6 ? '硬件' : name == 7 ? '测试' : ''
            )
          }
        }, {
          "title": "任务名称",
          "align": "center",
          "key": "taskName",
          "minWidth": "170px"
        }, {
          "title": "任务状态",
          "align": "center",
          "key": "taskState",
          "minWidth": "60px",
          render: (h, params) => {
            var state = params.row.taskState
            return h('span',{
                style: {
                  color: state == 1 ? '#ff9900' : state == 2 ? '#19be6b' : state == 3 ? '#ff449f' : state == 4 ? '#2d8cf0' :  state == 5 ? '#c5c8ce' : state == 6 ? '#ed4014' : ''
                }
              },
              state == 1 ? '待启动' : state == 2 ? '进行中' : state == 3 ? '暂停中' : state == 4 ? '测试中' : state == 5 ? '已完成' : state == 6 ? '已逾期' : ''
            )
          }
        }, {
          "title": "开发人员",
          "align": "center",
          "key": "developer",
          'minWidth': '60px'
        }, {
          "title": "计划人时",
          "align": "center",
          "key": "planHours",
          'minWidth': '60px'
        }, {
          "title": "计划人天",
          "align": "center",
          "key": "planDays",
          'minWidth': '60px'
        }, {
          "title": "计划开始",
          "align": "center",
          "key": "planStartDate",
          'minWidth': '82px',
          render: (h, params) => {
            return h(
              "span",
              formatDate(new Date(params.row.planStartDate), "yyyy-MM-dd")
            );
          }
        }, {
          "title": "计划结束",
          "align": "center",
          "key": "planEndDate",
          'minWidth': '82px',
          render: (h, params) => {
            return h(
              "span",
              formatDate(new Date(params.row.planEndDate), "yyyy-MM-dd")
            );
          }
        }, {
          "title": "实际开始",
          "align": "center",
          "key": "actualStartDate",
          'minWidth': '82px',
          render: (h, params) => {
            return h(
              "span",
              formatDate(new Date(params.row.actualStartDate), "yyyy-MM-dd")
            );
          }
        }, {
          "title": "实际结束",
          "align": "center",
          "key": "actualEndDate",
          'minWidth': '82px',
          render: (h, params) => {
            return h(
              "span",
              formatDate(new Date(params.row.actualEndDate), "yyyy-MM-dd")
            );
          }
        }, {
          "title": "实际人时",
          "align": "center",
          "key": "actualHours",
          'minWidth': '60px'
        }, {
          "title": "实际人天",
          "align": "center",
          "key": "actualDays",
          'minWidth': '60px'
        }, {
          "title": "开始偏差",
          "align": "center",
          "key": "deviationStartDate",
          'minWidth': '60px'
        }, {
          "title": "结束偏差",
          "align": "center",
          "key": "deviationEndDate",
          'minWidth': '60px'
        }, {
          "title": "人时偏差",
          "align": "center",
          "key": "deviationHours",
          'minWidth': '60px'
        }, {
          "title": "人天偏差",
          "align": "center",
          "key": "deviationDays",
          'minWidth': '60px'
        }, {
          "title": "修改日期",
          "align": "center",
          "key": "modifyDate",
          'minWidth': '82px',
          render: (h, params) => {
            return h(
              "span",
              // formatDate(new Date(params.row.modifyDate), "yyyy-MM-dd hh:mm:ss")
              formatDate(new Date(params.row.modifyDate), "yyyy-MM-dd")
            );
          }
        }, {
          'title': '操作',
          'align': 'center',
          'key': 'action',
          'width': '80px',
          'fixed': 'right',
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
                    this.modifyModal(params.row.scheduleId)
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
                    this.deleteModal(params.row.scheduleId)
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
    this.projectId = this.$route.query.projectId
    this.demandId = this.$route.query.demandId
    this.type = this.demandId ? 2 : 1 // 1：项目排期，2：新增需求排期
    this.queryPageInfo();
  },
  methods: {
    // 获取分页信息
    queryPageInfo () {
      let params = {
        pagenum: this.pageNow,
        pagesize: this.pagesize,
        type: this.type,
        projectId: this.type == 1 ? this.projectId : '',
        demandId: this.type == 2 ? this.demandId : '',
        ...this.filterObj(this.search)
      }
      this.$Spin.show()
      this.$post('/projectSchedule/queryPageList', params).then(data => {
        this.$Spin.hide()
        if(data.rs === 1) {
          this.pageInfo = data.pageInfo;
          // this.pageInfo = {}
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
    filterObj (obj) {
      for(var key in obj) {
        var val = obj[key]
        obj[key] = (val != undefined && val != null) ? val : ''
      }
      return obj
    },
    //分页
    handleChange (page) {
      this.pageNow = page;//赋值当前页
      this.queryPageInfo();
    },
    // 删除弹框
    deleteModal (scheduleId) {
      this.scheduleId = scheduleId
      this.modal_delete = true
    },
    // 确认删除
    deleteMethod (scheduleId) {
      this.$post('/projectSchedule/delete', {
        scheduleId: scheduleId
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
      // this.$refs[name].validate((valid) => {
      //   if(valid) {
      this.$Spin.show()
      this.$post('/projectSchedule/add', {
        type: this.type,
        projectId: this.projectId,
        demandId: this.demandId || '',
        ...this.filterObj(this.addInfo)
      }).then(data => {
        this.$Spin.hide()
        if(data.rs === 1) {
          this.modal_add = false
          this.$Message.success('新增成功!');
          this.$refs[name].resetFields();// 清空
          this.addInfo = {}
          this.queryPageInfo();
        } else {
          if(data.data && data.data.errorMsg) {
            this.$Message.error(data.data.errorMsg);
          } else {
            this.$Message.error(data.errorMsg);
          }
        }
      })
      //   } else {
      //     this.$Message.error('新增失败!');
      //   }
      // })
    },
    handleResDate (obj, keys) {
      keys.forEach(key => {
        if(obj[key]) {
          obj[key] = formatDate(new Date(obj[key]), 'yyyy-MM-dd')
        }
      })
    },
    // 编辑弹框
    modifyModal (scheduleId) {
      this.modal_modify = true
      this.$post('/projectSchedule/queryDetail', {
        scheduleId: scheduleId
      }).then(data => {
        if(data.rs === 1) {
          this.modifyInfo = data.data
          delete this.modifyInfo.modifyDate
          delete this.modifyInfo.createDate
          this.handleResDate(this.modifyInfo, ['planStartDate', 'planEndDate', 'actualStartDate', 'actualEndDate'])
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
      // this.$refs[name].validate((valid) => {
      //   if(valid) {
      this.$Spin.show()
      this.$post('/projectSchedule/update', this.filterObj(this.modifyInfo)).then(data => {
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
      //   } else {
      //     this.$Message.error('修改失败!');
      //   }
      // })
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
<style lang="less" scoped>
.schedule /deep/ .ivu-table-cell {
  padding: 0 4px;
}
.schedule /deep/ .container_table .ivu-table-header thead tr th,
.schedule /deep/ .container_table .ivu-table-fixed-header thead tr th {
  font-size: 13px;
}
.schedule /deep/ .ivu-table {
  overflow-x: auto;
}
.schedule /deep/ .ivu-table-header,
.schedule /deep/ .ivu-table-tip {
  overflow: initial;
}
.schedule /deep/ .ivu-table table {
  width: 100% !important;
}
</style>