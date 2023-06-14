<template>
  <div class="detail">
    <div class="container_title">
      <!-- 面包屑 -->
      <Breadcrumb>
        <BreadcrumbItem>项目详情<span v-if="projectInfo.name" style="font-size:16px"> —— 项目名称：{{projectInfo.name}}</span></BreadcrumbItem>
      </Breadcrumb>
      <!-- 操作按钮 -->
      <div class="ops_btn">
        <Button type="primary" ghost size='small' @click="$router.go(-1)" style="margin-right:10px">返回</Button>
      </div>
    </div>
    <div class="container_box">
      <Card class="info_item">
        <p slot="title" class="title">
          销售提单信息
        </p>
        <ul>
          <li v-for="info in projectInfoList" :key="info.en">
            <span class="name">{{info.name}}：</span>
            <span class="info" v-if="!info.isDate">{{projectInfo[info.en]}}</span>
            <span class="info" v-if="info.isDate">{{formatDate(new Date(projectInfo[info.en]),'yyyy-MM-dd')}}</span>
          </li>
        </ul>
      </Card>
      <Card class="info_item">
        <p slot="title" class="title">
          售前立项信息
        </p>
        <ul>
          <li v-for="info in presaleInfoList" :key="info.en">
            <span class="name">{{info.name}}：</span>
            <span class="info" v-if="!info.isList && !info.isFile">{{presaleInfo[info.en]}}</span>
            <div class="info" v-if="info.isList">
              <div class="hardware" v-for="hardware in presaleInfo[info.en]" :key="hardware.id">
                <span style="width: 27%">● {{hardware.name}}：</span>
                <span style="width: 73%">{{hardware.demand || ''}}</span>
              </div>
            </div>
            <div class="info" v-if="info.isFile">
              <!-- <span>{{presaleInfo[info.en + 'Name']}}</span> -->
              <a v-if="presaleInfo[info.en]" href="javascript:void(0);" class="click_active" style="color:#2d8cf0" @click="downFile(presaleInfo[info.en])">{{presaleInfo[info.en + 'Name']}}</a>
            </div>
          </li>
        </ul>
      </Card>
      <Card class="info_item">
        <p slot="title" class="title">
          工厂发货信息
        </p>
        <ul>
          <li v-for="info in deliverGoodsInfoList" :key="info.en">
            <span class="name">{{info.name}}：</span>
            <span class="info" v-if="!info.isDate">{{deliverGoodsInfo[info.en]}}</span>
            <span class="info" v-if="info.isDate">{{formatDate(new Date(deliverGoodsInfo[info.en]),'yyyy-MM-dd')}}</span>
          </li>
        </ul>
      </Card>
      <Card class="info_item">
        <p slot="title" class="title">
          运营交付信息
        </p>
        <ul>
          <li v-for="info in deliverInfoList" :key="info.en">
            <span class="name">{{info.name}}：</span>
            <span class="info" v-if="!info.isDate && !info.isFile">{{deliverInfo[info.en]}}</span>
            <span class="info" v-if="info.isDate">{{formatDate(new Date(deliverInfo[info.en]),'yyyy-MM-dd')}}</span>
            <div class="info" v-if="info.isFile">
              <a v-if="deliverInfo[info.en]" href="javascript:void(0);" class="click_active" style="color:#2d8cf0" @click="downFile(deliverInfo[info.en])">{{deliverInfo[info.en + 'Name']}}</a>
            </div>
          </li>
        </ul>
      </Card>
    </div>
    <Card class="other_item">
      <p slot="title" class="title">新增需求信息</p>
      <!-- 查询条件 -->
      <div class="query_option">
        <Input style="width: 200px;margin-right:10px" v-model="search1.name" placeholder="请输入需求名称"></Input>
        <Input style="width: 200px;margin-right:10px" v-model="search1.provider" placeholder="请输入需求提出人姓名"></Input>
        <Select style="width: 200px;margin-right:10px;" v-model="search1.demandCategory" placeholder="请选择需求类别">
          <Option :value='1'>合同项目</Option>
          <Option :value='2'>标准产品</Option>
          <Option :value='3'>内部项目</Option>
          <Option :value='4'>非合同制项目</Option>
        </Select>
        <span class="query_btn">
          <Button type="info" @click="queryPageInfo1">查询</Button>
          <Button @click="clearSearch1">重置</Button>
        </span>
      </div>
      <!-- 表格列表 -->
      <div class="container_table">
        <Table :columns="columns1" :data="pageInfo1.data"></Table>
      </div>
      <div class="pageInfo">
        <Page size="small" :total="total1" :page-size='pagesize1' @on-change="handleChange1" show-total show-elevator></Page>
      </div>
    </Card>
    <Card class="other_item" style="margin-top:20px">
      <p slot="title" class="title">技术排期信息</p>
      <!-- 查询条件 -->
      <div class="query_option">
        <Select style="width: 200px;margin-right:10px;" v-model="search.teamName" placeholder="请选择团队">
          <Option :value='1'>Andriod</Option>
          <Option :value='2'>Java</Option>
          <Option :value='3'>Web</Option>
          <Option :value='4'>UI</Option>
          <Option :value='5'>IOS</Option>
          <Option :value='6'>硬件</Option>
          <Option :value='7'>测试</Option>
        </Select>
        <Input style="width: 200px;margin-right:10px" v-model="search.taskName" placeholder="请输入任务名称"></Input>
        <Input style="width: 200px;" v-model="search.developer" placeholder="请输入开发人员"></Input>
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
    </Card>
  </div>
</template>

<script>
import { formatDate } from '@/util/date.js'
/* function getBlob (url, cb) {
  var xhr = new XMLHttpRequest();
  xhr.open("GET", url, true);
  xhr.responseType = "blob";
  xhr.onload = function () {
    if(xhr.status === 200) {
      cb(xhr.response);
    }
  };
  xhr.send();
}

function saveAs (blob, filename) {
  if(window.navigator.msSaveOrOpenBlob) {
    navigator.msSaveBlob(blob, filename);
  } else {
    var link = document.createElement("a");
    var body = document.querySelector("body");

    link.href = window.URL.createObjectURL(blob);
    link.download = filename;

    // fix Firefox
    link.style.display = "none";
    body.appendChild(link);

    link.click();
    body.removeChild(link);

    window.URL.revokeObjectURL(link.href);
  }
}
function download (url, filename) {
  getBlob(url, function (blob) {
    saveAs(blob, filename);
  });
} */

export default {
  created () {
    this.projectId = this.$route.query.projectId
    this.queryPageInfo()
    this.queryPageInfo1()
    this.queryDetail()
  },
  data () {
    return {
      projectInfo: {},
      presaleInfo: {},
      deliverGoodsInfo: {}, //工厂
      deliverInfo: {}, //运营交付
      formatDate,
      projectInfoList: [
        {
          name: '项目名称',
          en: 'name'
        },
        {
          name: '项目状态(当前)',
          en: 'projectState'
        },
        {
          name: '项目阶段(当前)',
          en: 'projectStage'
        },
        {
          name: '签订日期',
          en: 'signingDate',
          isDate: true
        },
        {
          name: '客户名称',
          en: 'customerName'
        },
        {
          name: '销售人员',
          en: 'sale'
        },
        {
          name: '机器人数量',
          en: 'robotNumber'
        },
        {
          name: '客户地区',
          en: 'customerArea'
        },
        {
          name: '客户地址',
          en: 'customerAddress'
        },
        {
          name: '客户联系人',
          en: 'customerContact'
        },
        {
          name: '客户联系人电话',
          en: 'customerPhone'
        },
        {
          name: '客户需求',
          en: 'customerDemand'
        },
        {
          name: '机器人使用地点',
          en: 'robotLocation'
        },
        {
          name: '合同交货时间',
          en: 'contractDeliverGoodsDate',
          isDate: true
        },
        {
          name: '合同交付时间',
          en: 'contractDeliverDate',
          isDate: true
        },
        {
          name: '项目类别',
          en: 'projectCategory',
        },
        {
          name: '机器人型号',
          en: 'robotModel',
        },
        {
          name: '合同工时',
          en: 'contractHours'
        },
        {
          name: '付款日期',
          en: 'payDate',
          isDate: true
        },
        {
          name: '备注',
          en: 'remark'
        }
      ],
      presaleInfoList: [{
        name: '定制类别',
        en: 'customizeCategory'
      },
      {
        name: '硬件定制信息',
        en: 'hardwares',
        isList: true
      },
      {
        name: '硬件定制文件',
        en: 'hardwareFile',
        isFile: true
      },
      {
        name: '硬件定制备注',
        en: 'hardwareRemark'
      },
      {
        name: '软件定制文件',
        en: 'softwareFile',
        isFile: true
      },
      {
        name: '软件定制备注',
        en: 'softwareRemark'
      }],
      deliverGoodsInfoList: [
        {
          name: '发货时间',
          en: 'deliverGoodsDate',
          isDate: true
        },
        {
          name: '快递单号',
          en: 'courierNumber'
        },
        {
          name: '物流品牌',
          en: 'logisticsBrand',
        },
        {
          name: '收货人姓名',
          en: 'receiverName'
        },
        {
          name: '收货人电话',
          en: 'receiverPhone',
        },
        {
          name: '预计到达时间',
          en: 'expectedArrivalDate',
          isDate: true
        },
        {
          name: '货件数量',
          en: 'goodsNumber',
        },
        {
          name: '备注',
          en: 'remark',
        }
      ],
      deliverInfoList: [
        {
          name: '交付人员名称',
          en: 'deliverPerson'
        },
        {
          name: '交付时间',
          en: 'deliverDate',
          isDate: true
        },
        {
          name: '交付结果',
          en: 'deliverResult',
        },
        {
          name: '现场反馈',
          en: 'siteFeedback'
        },
        {
          name: '问题记录文件',
          en: 'operateFile',
          isFile: true
        },
        {
          name: '交付地址',
          en: 'deliverAddress',
        },
        {
          name: '备注',
          en: 'remark',
        }
      ],
      pageInfo: {},
      pageInfo1: {},
      search: {},
      total: 0,// 初始化信息总条数
      pageNow: 1,
      pagesize: 20,// 每页显示多少条
      search1: {},
      total1: 0,// 初始化信息总条数
      pageNow1: 1,
      pagesize1: 20,// 每页显示多少条
      modal_delete: false,
      modal_add: false,
      modal_modify: false,
      addInfo: {},
      roleList: [],
      modifyInfo: {},
      columns: [
        {
          "title": "排期类型",
          "align": "center",
          "minWidth": "70px",
          render: (h, params) => {
            return h('span',
              params.row.demandId ? '新增排期' : '项目排期'
            )
          }
        }, {
          "title": "需求名称",
          "align": "center",
          "key": "demandName",
          "minWidth": "150px"
        }, {
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
        },
        {
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
        }
      ],
      columns1: [
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
    }
  },
  methods: {
    queryDetail () {
      this.$Spin.show()
      this.$post('/project/getAllInformation', { projectId: this.projectId }).then(data => {
        this.$Spin.hide()
        if(data.rs === 1) {
          this.projectInfo = data.project || {}
          this.presaleInfo = data.presale || { hardwares: [] }
          this.deliverGoodsInfo = data.deliverGoods || {}
          this.deliverInfo = data.deliver || {}
          var cate = this.projectInfo.projectCategory
          this.projectInfo.projectCategory = cate == 1 ? '合同项目' : cate == 2 ? '标准产品' : cate == 3 ? '内部项目' : cate == 4 ? '非合同制项目' : ''
          var state = this.projectInfo.projectState
          this.projectInfo.projectState = state == 1 ? '待启动' : state == 2 ? '进行中' : state == 3 ? '暂停中' : state == 4 ? '已结项' : ''
          var stage = this.projectInfo.projectStage
          this.projectInfo.projectStage = stage == 1 ? '前期支持' : stage == 2 ? '启动' : stage == 3 ? '设计' : stage == 4 ? '研发' : stage == 5 ? '测试' : stage == 6 ? '发布' : stage == 7 ? '交付' : stage == 8 ? '运营' : ''
          var robotModel = this.projectInfo.robotModel
          this.projectInfo.robotModel = robotModel == 1 ? '智大屏' : robotModel == 2 ? '智小鱼' : robotModel == 3 ? '紫外线消毒' : robotModel == 4 ? '脉冲消毒' : robotModel == 5 ? '喷雾消毒' : robotModel == 6 ? '智灵动' : robotModel == 7 ? '定制机器人' : ''
          this.presaleInfo.customizeCategory = this.presaleInfo.customizeCategory == 1 ? '一般定制' : this.presaleInfo.customizeCategory == 2 ? '外观定制' : ''
          this.presaleInfo.hardwares = this.presaleInfo.hardwares.filter(item => item.check == 1)
        } else {
          if(data.data && data.data.errorMsg) {
            this.$Message.error(data.data.errorMsg);
          } else {
            this.$Message.error(data.errorMsg);
          }
        }
      })
    },
    // 获取技术排期分页信息
    queryPageInfo () {
      let params = {
        pagenum: this.pageNow,
        pagesize: this.pagesize,
        // type: this.type,
        projectId: this.projectId,
        // demandId: this.type == 2 ? this.demandId : '',
        ...this.filterObj(this.search)
      }
      this.$Spin.show()
      this.$post('/projectSchedule/queryPageList', params).then(data => {
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
    // 获取新增需求分页信息
    queryPageInfo1 () {
      let params = {
        pagenum: this.pageNow1,
        pagesize: this.pagesize1,
        projectId: this.projectId,
        ...this.filterObj(this.search1)
      }
      this.$Spin.show()
      this.$post('/projectDemand/queryPageList2', params).then(data => {
        this.$Spin.hide()
        if(data.rs === 1) {
          this.pageInfo1 = data.pageInfo;
          this.total1 = data.pageInfo.totalRows;//总数
          this.chosePage1 = data.pageInfo.page;//选择页
          this.pageNow1 = data.pageInfo.page;//当前页
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
    // 重置
    clearSearch () {
      this.search = {}
    },
    //分页
    handleChange1 (page) {
      this.pageNow1 = page;//赋值当前页
      this.queryPageInfo1();
    },
    // 重置
    clearSearch1 () {
      this.search1 = {}
    },
    //下载文件
    downFile (src) {
      // download(src, name)
      window.open(src, 'download')
      /* let url = this.$baseUrl + src
      window.open(src, 'download') */
    },
  }
}
</script>

<style lang="less" scoped>
.container_box {
  display: flex;
  justify-content: space-between;
  flex-wrap: wrap;
}
.info_item {
  width: 49%;
  margin-bottom: 20px;
  .title {
    font-weight: bold;
    font-size: 16px;
    padding-left: 20px;
    position: relative;
  }
  .title::before {
    content: "";
    width: 3px;
    height: 16px;
    background: #2d8cf0;
    position: absolute;
    top: 2px;
    left: 0;
  }
  ul li {
    margin: 10px 0;
    .name {
      font-weight: bold;
      width: 21%;
      display: inline-block;
      // text-align: justify;
    }
    // .name::after {
    //   content: '';
    //   display:inline-block;
    //   padding-left: 100%
    // }
    .info {
      width: 79%;
      display: inline-block;
      vertical-align: top;
    }
    .hardware {
      margin-bottom: 4px;
      span {
        display: inline-block;
        vertical-align: top;
      }
    }
  }
}

.other_item {
  .title {
    font-weight: bold;
    font-size: 16px;
    padding-left: 20px;
    position: relative;
  }
  .title::before {
    content: "";
    width: 3px;
    height: 16px;
    background: #2d8cf0;
    position: absolute;
    top: 2px;
    left: 0;
  }
}

.detail /deep/ .ivu-table-cell {
  padding: 0 4px;
}
.detail /deep/ .container_table .ivu-table-header thead tr th,
.detail /deep/ .container_table .ivu-table-fixed-header thead tr th {
  font-size: 13px;
}
.detail /deep/ .ivu-table {
  overflow-x: auto;
}
.detail /deep/ .ivu-table-header,
.detail /deep/ .ivu-table-tip {
  overflow: initial;
}
.detail /deep/ .ivu-table table {
  width: 100% !important;
}
// .info_item:nth-child(2n) {
//   margin-left: 2%;
// }
</style>