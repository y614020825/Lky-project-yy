<template>
  <!-- 新增需求 -->
  <div>
    <div class="container_title">
      <!-- 面包屑 -->
      <Breadcrumb>
        <BreadcrumbItem>新增需求 —— <span style="font-size:16px">项目名称：{{$route.query.name}}</span></BreadcrumbItem>
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
          <Input style="width: 200px;margin-right:10px" v-model="search.name" placeholder="请输入需求名称"></Input>
          <Input style="width: 200px;margin-right:10px" v-model="search.provider" placeholder="请输入需求提出人姓名"></Input>
          <Select style="width: 200px;margin-right:10px;" v-model="search.demandCategory" placeholder="请选择需求类别">
            <Option :value='1'>合同项目</Option>
            <Option :value='2'>标准产品</Option>
            <Option :value='3'>内部项目</Option>
            <Option :value='4'>非合同制项目</Option>
          </Select>
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
        <p><img src="~/assets/images/warn_tip.png" alt="">您确定要删除该需求吗?</p>
      </div>
      <div slot="footer">
        <Button type="primary" class="confirm_btn" ghost @click="deleteMethod(demandId)">确定</Button>
        <Button type="default" class="clear_btn" @click="cancel()">取消</Button>
      </div>
    </Modal>
    <Modal class="sys_modal" v-model="modal_add" width="450" title="新增需求">
      <div class="modal_body">
        <Form ref="addInfo" label-position="left" :model="addInfo" :label-width="120" :rules="ruleValidate">
          <FormItem label="需求名称" prop="name">
            <Input v-model="addInfo.name"></Input>
          </FormItem>
          <FormItem label="需求类别">
            <Select v-model="addInfo.demandCategory">
              <Option :value='1'>合同项目</Option>
              <Option :value='2'>标准产品</Option>
              <Option :value='3'>内部项目</Option>
              <Option :value='4'>非合同制项目</Option>
            </Select>
          </FormItem>
          <FormItem label="新增时间">
            <DatePicker type="date" format="yyyy-MM-dd" v-model="addInfo.addDate" @on-change="addInfo.addDate=$event"></DatePicker>
          </FormItem>
          <FormItem label="需求提出人姓名">
            <Input v-model="addInfo.provider"></Input>
          </FormItem>
          <FormItem label="约定完成时间">
            <DatePicker type="date" format="yyyy-MM-dd" v-model="addInfo.agreeCompletionTime" @on-change="addInfo.agreeCompletionTime=$event"></DatePicker>
          </FormItem>
          <FormItem label="需求文件">
            <Upload :before-upload="uploadFileAdd" action="">
              <Button icon="ios-cloud-upload-outline" type="primary" style="width:150px;font-size:12px;margin-right:10px">上传文件</Button>
              <div v-if="addInfo.demandF">{{addInfo.demandF.name}}</div>
            </Upload>
            <Button v-if="addInfo.demandF" type="primary" size="small" shape="circle" ghost @click="delFile('addInfo')" style="width:90px;height:32px;">清空文件</Button>
          </FormItem>
          <FormItem label="预计工时">
            <Input v-model="addInfo.estimateHours"></Input>
          </FormItem>
          <FormItem label="备注">
            <Input v-model="addInfo.remark" type="textarea" :rows="4"></Input>
          </FormItem>
        </Form>
      </div>
      <div slot="footer">
        <Button type="primary" class="confirm_btn" ghost @click="addInfoMethod('addInfo')">确定</Button>
        <Button type="default" class="clear_btn" @click="cancel('addInfo')">取消</Button>
      </div>
    </Modal>
    <Modal class="sys_modal" v-model="modal_modify" width="450" title="修改需求">
      <div class="modal_body">
        <Form ref="modifyInfo" label-position="left" :model="modifyInfo" :label-width="120" :rules="ruleValidate">
          <FormItem label="需求名称" prop="name">
            <Input v-model="modifyInfo.name"></Input>
          </FormItem>
          <FormItem label="需求类别">
            <Select v-model="modifyInfo.demandCategory">
              <Option :value='1'>合同项目</Option>
              <Option :value='2'>标准产品</Option>
              <Option :value='3'>内部项目</Option>
              <Option :value='4'>非合同制项目</Option>
            </Select>
          </FormItem>
          <FormItem label="新增时间">
            <DatePicker type="date" format="yyyy-MM-dd" :value="modifyInfo.addDate" @on-change="modifyInfo.addDate=$event"></DatePicker>
          </FormItem>
          <FormItem label="需求提出人姓名">
            <Input v-model="modifyInfo.provider"></Input>
          </FormItem>
          <FormItem label="约定完成时间">
            <DatePicker type="date" format="yyyy-MM-dd" :value="modifyInfo.agreeCompletionTime" @on-change="modifyInfo.agreeCompletionTime=$event"></DatePicker>
          </FormItem>
          <FormItem label="需求文件">
            <Upload :before-upload="uploadFileModify" action="">
              <Button icon="ios-cloud-upload-outline" type="primary" style="width:150px;font-size:12px;margin-right:10px">上传文件</Button>
              <div v-if="modifyInfo.demandF">{{modifyInfo.demandF.name}}</div>
              <div v-if="modifyInfo.fileName && !modifyInfo.demandF">{{modifyInfo.fileName}}</div>
            </Upload>
            <Button v-if="modifyInfo.demandF || modifyInfo.file" type="primary" size="small" shape="circle" ghost @click="delFile('modifyInfo')" style="width:90px;height:32px;">清空文件</Button>
          </FormItem>
          <FormItem label="预计工时">
            <Input v-model="modifyInfo.estimateHours"></Input>
          </FormItem>
          <FormItem label="备注">
            <Input v-model="modifyInfo.remark" type="textarea" :rows="4"></Input>
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
  name: "IncreaseDemand",
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
      demandId: '',// 删除的时候传值
      modal_delete: false,
      modal_add: false,
      modal_modify: false,
      // demandF: null,
      addInfo: {},
      modifyInfo: {},
      ruleValidate: {
        name: [
          { required: true, message: '请输入需求名称', trigger: 'blur' },
        ]
      },
      columns: [
        {
          "title": "需求名称",
          "align": "center",
          "key": "name",
        }, {
          "title": "需求类别",
          "align": "center",
          "key": "demandCategory",
          render: (h, params) => {
            var type = params.row.demandCategory
            return h('span',
              type == 1 ? '合同项目' : type == 2 ? '标准产品' : type == 3 ? '内部项目' : type == 4 ? '非合同制项目' : ''
            )
          }
        }, {
          "title": "新增时间",
          "align": "center",
          "key": "addDate",
          render: (h, params) => {
            return h(
              "span",
              formatDate(new Date(params.row.addDate), "yyyy-MM-dd")
            );
          }
        }, {
          "title": "需求提出人姓名",
          "align": "center",
          "key": "provider",
        }, {
          "title": "约定完成时间",
          "align": "center",
          "key": "agreeCompletionTime",
          render: (h, params) => {
            return h(
              "span",
              formatDate(new Date(params.row.agreeCompletionTime), "yyyy-MM-dd")
            );
          }
        }, {
          "title": "预计工时",
          "align": "center",
          "key": "estimateHours",
        }, {
          "title": "修改日期",
          "align": "center",
          "key": "modifyDate",
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
          'width': '140px',
          render: (h, params) => {
            return h('div', [
              h('span', {
                style: {
                  cursor: 'pointer',
                  color: '#2d8cf0'
                },
                on: {
                  click: () => {
                    // 点击操作事件
                    this.modifyModal(params.row.demandId)
                  }
                }
              }, '修改'),
              h('span', {
                style: {
                  color: '#2d8cf0',
                  cursor: 'pointer',
                  marginLeft: '4px',
                },
                on: {
                  click: () => {
                    this.deleteModal(params.row.demandId)
                  }
                }
              }, '删除'),
              h('span', {
                style: {
                  color: '#2d8cf0',
                  cursor: 'pointer',
                  marginLeft: '4px',
                },
                on: {
                  click: () => {
                    this.$router.push({ path: '/schedule', query: { projectId: params.row.projectId, name: this.$route.query.name, demandId: params.row.demandId, demandName: params.row.name } })
                  }
                }
              }, '技术排期'),
              h('span', {
                attrs: {
                  'title': params.row.fileName
                },
                style: {
                  color: '#2d8cf0',
                  cursor: 'pointer',
                  marginLeft: '4px',
                  display: params.row.file ? 'inline-block' : 'none'
                },
                on: {
                  click: () => {
                    this.downFile(params.row.file)
                  }
                }
              }, '查看文件')
            ])
          }
        }],
      pageInfo: {}
    }
  },
  created () {
    this.projectId = this.$route.query.projectId
    this.queryPageInfo()
  },
  computed: {
    roleId () {
      return this.$store.state.currentUserInfo.roleId
    }
  },
  methods: {
    //下载文件
    downFile (src) {
      window.open(src, 'download')
      // let url = this.$baseUrl + src
      // window.open(src, 'download')
    },
    // 获取分页信息
    queryPageInfo () {
      let params = {
        pagenum: this.pageNow,
        pagesize: this.pagesize,
        projectId: this.projectId,
        ...this.filterObj(this.search)
      }
      this.$Spin.show()
      this.$post('/projectDemand/queryPageList', params).then(data => {
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
    uploadFileAdd (file) {
      this.$set(this.addInfo, 'demandF', file)
      return false
    },
    uploadFileModify (file) {
      this.$set(this.modifyInfo, 'demandF', file)
      return false
    },
    filterObj (obj) {
      for(var key in obj) {
        obj[key] = obj[key] || ''
      }
      return obj
    },
    delFile (name) {
      this[name].demandF = null
      this.modifyInfo.file = ''
      this.modifyInfo.fileName = ''
    },
    // 删除弹框
    deleteModal (demandId) {
      this.demandId = demandId
      this.modal_delete = true
    },
    // 确认删除
    deleteMethod (demandId) {
      this.$post('/projectDemand/delete', {
        demandId: demandId
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
          var fd = new FormData()
          if(this.addInfo.demandF) {
            fd.append('f', this.addInfo.demandF)
          }

          for(var key in this.addInfo) {
            var val = this.addInfo[key]
            if(val && key != 'demandF') {
              fd.append(key, val)
            }
          }
          fd.append('projectId', this.projectId)

          this.$Spin.show()
          this.$post_('/projectDemand/add?_token_iben=' + this.$store.state.token, fd).then(data => {
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
        } else {
          this.$Message.error('新增失败!');
        }
      })
    },
    handleResDate (obj, keys) {
      keys.forEach(key => {
        if(obj[key]) {
          obj[key] = formatDate(new Date(obj[key]), 'yyyy-MM-dd')
        }
      })
    },
    // 编辑弹框
    modifyModal (demandId) {
      this.modifyInfo = {}
      this.modal_modify = true
      this.$post('/projectDemand/queryDetail', {
        demandId: demandId
      }).then(data => {
        if(data.rs === 1) {
          this.modifyInfo = data.data
          delete this.modifyInfo.modifyDate
          delete this.modifyInfo.createDate
          this.handleResDate(this.modifyInfo, ['addDate', 'agreeCompletionTime'])
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
          var fd = new FormData()

          if(this.modifyInfo.demandF) {
            fd.append('f', this.modifyInfo.demandF)
          }

          for(var key in this.modifyInfo) {
            var val = this.modifyInfo[key]
            if(val && key != 'demandF') {
              fd.append(key, val)
            }
          }

          this.$Spin.show()
          this.$post_('/projectDemand/update?_token_iben=' + this.$store.state.token, fd).then(data => {
            this.$Spin.hide()
            if(data.rs === 1) {
              this.modal_modify = false
              this.$Message.success('修改成功!');
              this.modifyInfo = {}
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
      this[name] = {}
    },
    // 重置
    clearSearch () {
      this.search = {}
    }
  }
}
</script>
<style lang="less" scoped>
</style>