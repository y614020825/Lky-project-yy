<template>
  <!-- 项目管理 -->
  <div>
    <div class="container_title">
      <!-- 面包屑 -->
      <Breadcrumb>
        <BreadcrumbItem>项目管理</BreadcrumbItem>
      </Breadcrumb>
      <!-- 操作按钮 -->
      <div class="ops_btn">
        <Button type="success" ghost size='small' v-if="$_has('IBEN_PROJECT_ADD')" @click="addModal" style="margin-right:10px">新增</Button>
        <!-- <Button type="primary" ghost size='small' @click="downloadModal">下载文件</Button> -->
      </div>
    </div>
    <div class="container_info">
      <div class="container_box">
        <!-- 查询条件 -->
        <div class="query_option">
          <Input style="width: 150px;margin-right:10px;" v-model="search.name" placeholder="请输入项目名称"></Input>
          <Select style="width: 150px;margin-right:10px;" v-model="search.projectCategory" placeholder="请选择项目类别">
            <Option :value='1'>合同项目</Option>
            <Option :value='2'>标准产品</Option>
            <Option :value='3'>内部项目</Option>
            <Option :value='4'>非合同制项目</Option>
          </Select>
          <Select style="width: 150px;margin-right:10px;" v-model="search.projectState" placeholder="请选择项目状态">
            <Option :value='1'>待启动</Option>
            <Option :value='2'>进行中</Option>
            <Option :value='3'>暂停中</Option>
            <Option :value='4'>已结项</Option>
          </Select>
          <Select style="width: 150px;margin-right:10px;" v-model="search.robotModel" placeholder="请选择机器人型号">
            <Option :value='1'>智大屏</Option>
            <Option :value='2'>智小鱼</Option>
            <Option :value='3'>紫外线消毒</Option>
            <Option :value='4'>脉冲消毒</Option>
            <Option :value='5'>喷雾消毒</Option>
            <Option :value='6'>智灵动</Option>
            <Option :value='7'>定制机器人</Option>
          </Select>
          <Select style="width: 150px;margin-right:10px;" v-model="search.projectStage" placeholder="请选择项目阶段">
            <Option :value='1'>前期支持</Option>
            <Option :value='2'>启动</Option>
            <Option :value='3'>设计</Option>
            <Option :value='4'>研发</Option>
            <Option :value='5'>测试</Option>
            <Option :value='6'>发布</Option>
            <Option :value='7'>交付</Option>
            <Option :value='8'>运营</Option>
          </Select>
          <DatePicker format="yyyy-MM-dd" type="daterange" placeholder="请选择创建日期" :value="[search.startDate,search.endDate]" style="width: 210px" @on-change="changeSearchDate($event)" :options="options"></DatePicker>
          <span class="query_btn">
            <Button type="info" @click="queryPageInfo">查询</Button>
            <Button @click="clearSearch">重置</Button>
          </span>
        </div>
        <!-- 表格列表 -->
        <div class="container_table project">
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
        <p><img src="~/assets/images/warn_tip.png" alt="">您确定要删除该项目吗?</p>
      </div>
      <div slot="footer">
        <Button type="primary" class="confirm_btn" ghost @click="deleteMethod(projectId)">确定</Button>
        <Button type="default" class="clear_btn" @click="cancel()">取消</Button>
      </div>
    </Modal>
    <Modal class="sys_modal" v-model="modal_add" width="500" title="新增项目">
      <div class="modal_body">
        <Form ref="addInfo" label-position="left" :model="addInfo" :rules="ruleValidate" :label-width="120">
          <FormItem label="项目名称" prop="name">
            <Input v-model="addInfo.name"></Input>
          </FormItem>
          <FormItem label="签订日期">
            <DatePicker type="date" format="yyyy-MM-dd" v-model="addInfo.signingDate" @on-change="addInfo.signingDate=$event"></DatePicker>
          </FormItem>
          <FormItem label="客户名称">
            <Input v-model="addInfo.customerName"></Input>
          </FormItem>
          <FormItem label="销售人员">
            <Input v-model="addInfo.sale"></Input>
          </FormItem>
          <FormItem label="机器人数量">
            <Input type="number" v-model="addInfo.robotNumber"></Input>
          </FormItem>
          <FormItem label="客户地区">
            <Input v-model="addInfo.customerArea"></Input>
          </FormItem>
          <FormItem label="客户地址">
            <Input v-model="addInfo.customerAddress"></Input>
          </FormItem>
          <FormItem label="客户联系人">
            <Input v-model="addInfo.customerContact"></Input>
          </FormItem>
          <FormItem label="客户联系人电话">
            <Input v-model="addInfo.customerPhone"></Input>
          </FormItem>
          <FormItem label="客户需求">
            <Input v-model="addInfo.customerDemand" type="textarea" :rows="3" />
          </FormItem>
          <FormItem label="机器人使用地点">
            <Input v-model="addInfo.robotLocation"></Input>
          </FormItem>
          <FormItem label="合同交货时间">
            <DatePicker type="date" format="yyyy-MM-dd" v-model="addInfo.contractDeliverGoodsDate" @on-change="addInfo.contractDeliverGoodsDate=$event"></DatePicker>
          </FormItem>
          <FormItem label="合同交付时间">
            <DatePicker type="date" format="yyyy-MM-dd" v-model="addInfo.contractDeliverDate" @on-change="addInfo.contractDeliverDate=$event"></DatePicker>
          </FormItem>
          <FormItem label="项目类别">
            <Select style="margin-right:10px;" v-model="addInfo.projectCategory">
              <Option :value='1'>合同项目</Option>
              <Option :value='2'>标准产品</Option>
              <Option :value='3'>内部项目</Option>
              <Option :value='4'>非合同制项目</Option>
            </Select>
          </FormItem>
          <FormItem label="机器人型号">
            <Select style="margin-right:10px;" v-model="addInfo.robotModel">
              <Option :value='1'>智大屏</Option>
              <Option :value='2'>智小鱼</Option>
              <Option :value='3'>紫外线消毒</Option>
              <Option :value='4'>脉冲消毒</Option>
              <Option :value='5'>喷雾消毒</Option>
              <Option :value='6'>智灵动</Option>
              <Option :value='7'>定制机器人</Option>
            </Select>
          </FormItem>
          <FormItem label="合同工时">
            <Input v-model="addInfo.contractHours"></Input>
          </FormItem>
          <FormItem label="付款日期">
            <DatePicker type="date" format="yyyy-MM-dd" v-model="addInfo.payDate" @on-change="addInfo.payDate=$event"></DatePicker>
          </FormItem>
          <FormItem label="备注">
            <Input v-model="addInfo.remark" type="textarea" :rows="3"></Input>
          </FormItem>
        </Form>
      </div>
      <div slot="footer">
        <Button type="primary" class="confirm_btn" ghost @click="addInfoMethod('addInfo')">确定</Button>
        <Button type="default" class="clear_btn" @click="cancel('addInfo')">取消</Button>
      </div>
    </Modal>
    <Modal class="sys_modal" v-model="modal_modify" width="500" title="修改项目">
      <div class="modal_body">
        <Form ref="modifyInfo" label-position="left" :model="modifyInfo" :rules="ruleValidate" :label-width="120">
          <FormItem label="项目名称" prop="name">
            <Input v-model="modifyInfo.name"></Input>
          </FormItem>
          <FormItem label="签订日期">
            <DatePicker type="date" format="yyyy-MM-dd" @on-change="modifyInfo.signingDate=$event" :value="modifyInfo.signingDate"></DatePicker>
          </FormItem>
          <FormItem label="客户名称">
            <Input v-model="modifyInfo.customerName"></Input>
          </FormItem>
          <FormItem label="销售人员">
            <Input v-model="modifyInfo.sale"></Input>
          </FormItem>
          <FormItem label="机器人数量">
            <Input type="number" v-model="modifyInfo.robotNumber"></Input>
          </FormItem>
          <FormItem label="客户地区">
            <Input v-model="modifyInfo.customerArea"></Input>
          </FormItem>
          <FormItem label="客户地址">
            <Input v-model="modifyInfo.customerAddress"></Input>
          </FormItem>
          <FormItem label="客户联系人">
            <Input v-model="modifyInfo.customerContact"></Input>
          </FormItem>
          <FormItem label="客户联系人电话">
            <Input v-model="modifyInfo.customerPhone"></Input>
          </FormItem>
          <FormItem label="客户需求">
            <Input v-model="modifyInfo.customerDemand" type="textarea" :rows="3" />
          </FormItem>
          <FormItem label="机器人使用地点">
            <Input v-model="modifyInfo.robotLocation"></Input>
          </FormItem>
          <FormItem label="合同交货时间">
            <DatePicker type="date" format="yyyy-MM-dd" :value="modifyInfo.contractDeliverGoodsDate" @on-change="modifyInfo.contractDeliverGoodsDate=$event"></DatePicker>
          </FormItem>
          <FormItem label="合同交付时间">
            <DatePicker type="date" format="yyyy-MM-dd" :value="modifyInfo.contractDeliverDate" @on-change="modifyInfo.contractDeliverDate=$event"></DatePicker>
          </FormItem>
          <FormItem label="项目类别">
            <Select style="margin-right:10px;" v-model="modifyInfo.projectCategory">
              <Option :value='1'>合同项目</Option>
              <Option :value='2'>标准产品</Option>
              <Option :value='3'>内部项目</Option>
              <Option :value='4'>非合同制项目</Option>
            </Select>
          </FormItem>
          <FormItem label="机器人型号">
            <Select style="margin-right:10px;" v-model="modifyInfo.robotModel">
              <Option :value='1'>智大屏</Option>
              <Option :value='2'>智小鱼</Option>
              <Option :value='3'>紫外线消毒</Option>
              <Option :value='4'>脉冲消毒</Option>
              <Option :value='5'>喷雾消毒</Option>
              <Option :value='6'>智灵动</Option>
              <Option :value='7'>定制机器人</Option>
            </Select>
          </FormItem>
          <FormItem label="合同工时">
            <Input v-model="modifyInfo.contractHours"></Input>
          </FormItem>
          <FormItem label="付款日期">
            <DatePicker type="date" format="yyyy-MM-dd" :value="modifyInfo.payDate" @on-change="modifyInfo.payDate=$event"></DatePicker>
          </FormItem>
          <FormItem label="备注">
            <Input v-model="modifyInfo.remark" type="textarea" :rows="3"></Input>
          </FormItem>
        </Form>
      </div>
      <div slot="footer">
        <Button type="primary" class="confirm_btn" ghost @click="modifyInfoMethod('modifyInfo')">确定</Button>
        <Button type="default" class="clear_btn" @click="cancel('modifyInfo')">取消</Button>
      </div>
    </Modal>
    <!-- 售前 -->
    <Modal class="sys_modal" v-model="modal_input3" width="500" title="信息录入" v-if="roleId==3">
      <div class="modal_body">
        <Form ref="inputInfo" label-position="left" :model="inputInfo" :label-width="80">
          <FormItem label="定制类别">
            <RadioGroup v-model="inputInfo.customizeCategory" class="sys_radioGroup">
              <Radio :label="1">一般定制</Radio>
              <Radio :label="2">外观定制</Radio>
            </RadioGroup>
          </FormItem>
          <FormItem label="硬件定制">
            <CheckboxGroup @on-change="changeCheck" v-model="hardwareIds">
              <Checkbox :label="item.id" v-model="item.check" v-for="item in myHardwares" :key="item.id" style="display:block;margin-bottom:10px" class="clear-fix">
                <span>{{item.name}}</span>
                <Input v-model="item.demand" v-show="item.check" type="textarea" placeholder="请输入定制详情" style="width:69%;float:right"></Input>
              </Checkbox>
            </CheckboxGroup>
            <FormItem label="备注">
              <Input v-model="inputInfo.hardwareRemark" type="textarea" placeholder="硬件定制备注"></Input>
              <Upload :before-upload="uploadHardFile" action="" style="margin-top:20px">
                <Button icon="ios-cloud-upload-outline" type="primary" style="width:150px;font-size:12px;margin-right:10px">上传硬件定制文件</Button>
                <div v-if="hardwareF">{{hardwareF.name}}</div>
                <div v-if="inputInfo.hardwareFile && !hardwareF">{{inputInfo.hardwareFileName}}</div>
              </Upload>
              <Button v-if="hardwareF || inputInfo.hardwareFile" type="primary" size="small" shape="circle" ghost @click="delFile('hard')" style="width:90px;height:32px;">清空文件</Button>
            </FormItem>
          </FormItem>
          <FormItem label="软件定制">
            <Input v-model="inputInfo.softwareRemark" type="textarea" placeholder="软件定制备注"></Input>
            <Upload :before-upload="uploadSoftFile" action="" style="margin-top:20px">
              <Button icon="ios-cloud-upload-outline" type="primary" style="width:150px;font-size:12px;margin-right:10px">上传软件定制文件</Button>
              <div v-if="softwareF">{{softwareF.name}}</div>
              <div v-if="inputInfo.softwareFile && !softwareF">{{inputInfo.softwareFileName}}</div>
            </Upload>
            <Button v-if="softwareF || inputInfo.softwareFile" type="primary" ghost @click="delFile('soft')" style="width:90px;height:32px;">清空文件</Button>
          </FormItem>
        </Form>
      </div>
      <div slot="footer">
        <Button type="primary" class="confirm_btn" ghost @click="inputInfoMethod('inputInfo')">确定</Button>
        <Button type="default" class="clear_btn" @click="cancel('inputInfo')">取消</Button>
      </div>
    </Modal>
    <!-- 工厂生产 -->
    <Modal class="sys_modal" v-model="modal_input5" width="500" title="信息录入" v-if="roleId==5">
      <div class="modal_body">
        <Form ref="inputInfo" label-position="left" :model="inputInfo" :label-width="100">
          <FormItem label="发货时间">
            <DatePicker type="date" format="yyyy-MM-dd" :value="inputInfo.deliverGoodsDate" @on-change="inputInfo.deliverGoodsDate=$event"></DatePicker>
          </FormItem>
          <FormItem label="快递单号">
            <Input v-model="inputInfo.courierNumber"></Input>
          </FormItem>
          <FormItem label="物流品牌">
            <Input v-model="inputInfo.logisticsBrand"></Input>
          </FormItem>
          <FormItem label="收货人姓名">
            <Input v-model="inputInfo.receiverName"></Input>
          </FormItem>
          <FormItem label="收货人电话">
            <Input v-model="inputInfo.receiverPhone"></Input>
          </FormItem>
          <FormItem label="预计到达时间">
            <DatePicker type="date" format="yyyy-MM-dd" :value="inputInfo.expectedArrivalDate" @on-change="inputInfo.expectedArrivalDate=$event"></DatePicker>
          </FormItem>
          <FormItem label="货件数量">
            <Input v-model="inputInfo.goodsNumber" type="number"></Input>
          </FormItem>
          <FormItem label="备注">
            <Input v-model="inputInfo.remark" type="textarea"></Input>
          </FormItem>
        </Form>
      </div>
      <div slot="footer">
        <Button type="primary" class="confirm_btn" ghost @click="inputInfoMethod('inputInfo')">确定</Button>
        <Button type="default" class="clear_btn" @click="cancel('inputInfo')">取消</Button>
      </div>
    </Modal>
    <!-- 运营交付 -->
    <Modal class="sys_modal" v-model="modal_input6" width="500" title="信息录入" v-if="roleId==6">
      <div class="modal_body">
        <Form ref="inputInfo" label-position="left" :model="inputInfo" :label-width="100">
          <FormItem label="交付人员名称">
            <Input v-model="inputInfo.deliverPerson"></Input>
          </FormItem>
          <FormItem label="实际交付时间">
            <DatePicker type="date" format="yyyy-MM-dd" :value="inputInfo.deliverDate" @on-change="inputInfo.deliverDate=$event"></DatePicker>
          </FormItem>
          <FormItem label="交付结果">
            <Input v-model="inputInfo.deliverResult"></Input>
          </FormItem>
          <FormItem label="现场反馈">
            <Input v-model="inputInfo.siteFeedback" type="textarea"></Input>
          </FormItem>
          <FormItem label="问题记录文件">
            <Upload :before-upload="uploadOperateFile" action="">
              <Button icon="ios-cloud-upload-outline" type="primary" style="width:150px;font-size:12px;margin-right:10px">上传文件</Button>
              <div v-if="operateF">{{operateF.name}}</div>
              <div v-if="inputInfo.operateFile && !operateF">{{inputInfo.operateFileName}}</div>
            </Upload>
            <Button v-if="operateF || inputInfo.operateFile" type="primary" ghost @click="delFile('operate')" style="width:90px;height:32px;">清空文件</Button>
          </FormItem>
          <FormItem label="交付地址">
            <Input v-model="inputInfo.deliverAddress"></Input>
          </FormItem>
          <FormItem label="备注">
            <Input v-model="inputInfo.remark" type="textarea"></Input>
          </FormItem>
        </Form>
      </div>
      <div slot="footer">
        <Button type="primary" class="confirm_btn" ghost @click="inputInfoMethod('inputInfo')">确定</Button>
        <Button type="default" class="clear_btn" @click="cancel('inputInfo')">取消</Button>
      </div>
    </Modal>
    <!-- 修改进度 -->
    <Modal class="sys_modal" v-model="modal_process" width="400" title="修改进度">
      <div class="modal_body">
        <Form ref="processInfo" label-position="left" :model="processInfo" :label-width="100">
          <FormItem label="项目状态" prop="projectState">
            <Select style="margin-right:10px;" v-model="processInfo.projectState">
              <Option :value='1'>待启动</Option>
              <Option :value='2'>进行中</Option>
              <Option :value='3'>暂停中</Option>
              <Option :value='4'>已结项</Option>
            </Select>
          </FormItem>
          <FormItem label="项目阶段" prop="projectStage">
            <Select style="margin-right:10px;" v-model="processInfo.projectStage">
              <Option :value='1'>前期支持</Option>
              <Option :value='2'>启动</Option>
              <Option :value='3'>设计</Option>
              <Option :value='4'>研发</Option>
              <Option :value='5'>测试</Option>
              <Option :value='6'>发布</Option>
              <Option :value='7'>交付</Option>
              <Option :value='8'>运营</Option>
            </Select>
          </FormItem>
        </Form>
      </div>
      <div slot="footer">
        <Button type="primary" class="confirm_btn" ghost @click="processInfoMethod('processInfo')">确定</Button>
        <Button type="default" class="clear_btn" @click="cancel('processInfo')">取消</Button>
      </div>
    </Modal>
    <!-- 下载文件 -->
    <Modal class="sys_modal" v-model="modal_download" width="450" title="下载文件">
      <div class="modal_body">
        <div class="ivu-form-item">
          <p style="padding-left:42px;" v-for="(item,index) in fileList" :key="index">
            <span>{{item.fileName}}</span>
            <a href="javascript:void(0);" class="click_active" @click="downFile(item.url)" style="margin-left:20px">下载文件</a>
          </p>
        </div>
      </div>
      <div slot="footer">
        <!-- <Button type="primary" ghost class="confirm_btn" @click="importInfoMethod('importInfo')">确定</Button>
        <Button type="default" class="clear_btn" @click="cancel('importInfo')">取消</Button> -->
      </div>
    </Modal>
  </div>
</template>
<script>
import { formatDate } from '@/util/date.js'
export default {
  name: "ProjectManage",
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
      roleList: [],// 角色列表
      projectId: '',// 删除的时候传值
      modal_delete: false,
      modal_add: false,
      modal_modify: false,
      modal_input1: false,
      modal_input3: false,
      modal_input4: false,
      modal_input5: false,
      modal_input6: false,
      modal_process: false,
      modal_download: false,
      fileList: [{ fileName: '1234545.pdf', url: 'www.baidu.com' }],
      addInfo: {
        projectState: 1,
        projectStage: 1
      },
      ruleValidate: {
        name: [
          { required: true, message: '请输入项目名称', trigger: 'blur' },
        ]
      },
      modifyInfo: {},
      inputInfo: {},
      processInfo: {},
      myHardwares: [],
      hardwareIds: [],
      hardwareF: null,
      softwareF: null,
      operateF: null,
      options: {
        disabledDate (date) {
          return date && date.valueOf() > Date.now()
        }
      },
      columns: [
        {
          "title": "项目名称",
          "align": "center",
          "key": "name",
          // 'width': '248px'
          "minWidth": 168
        }, {
          "title": "销售",
          "align": "center",
          "key": "sale"
        }, {
          "title": "项目状态",
          "align": "center",
          "key": "projectState",
          render: (h, params) => {
            var state = params.row.projectState
            return h('span', {
              style: {
                color: state == 1 ? '#ff9900' : state == 2 ? '#19be6b' : state == 3 ? '#ed4014' : state == 4 ? '#c5c8ce' : ''
              }
            },
              state == 1 ? '待启动' : state == 2 ? '进行中' : state == 3 ? '暂停中' : state == 4 ? '已结项' : ''
            )
          }
        }, {
          "title": "项目阶段",
          "align": "center",
          "key": "projectStage",
          render: (h, params) => {
            var stage = params.row.projectStage
            return h('span',
              stage == 1 ? '前期支持' : stage == 2 ? '启动' : stage == 3 ? '设计' : stage == 4 ? '研发' : stage == 5 ? '测试' : stage == 6 ? '发布' : stage == 7 ? '交付' : stage == 8 ? '运营' : ''
            )
          }
        }, {
          "title": "项目类别",
          "align": "center",
          "key": "projectCategory",
          'width': '110px',
          render: (h, params) => {
            var type = params.row.projectCategory
            return h('span',
              type == 1 ? '合同项目' : type == 2 ? '标准产品' : type == 3 ? '内部项目' : type == 4 ? '非合同制项目' : ''
            )
          }
        }, {
          "title": "合同交付时间",
          "align": "center",
          "key": "contractDeliverDate",
          'width': '120px',
          render: (h, params) => {
            return h(
              "span",
              // formatDate(new Date(params.row.modifyDate), "yyyy-MM-dd hh:mm:ss")
              formatDate(new Date(params.row.contractDeliverDate), "yyyy-MM-dd")
            );
          }
        }, {
          "title": "创建日期",
          "align": "center",
          "key": "createDate",
          'width': '105px',
          render: (h, params) => {
            return h(
              "span",
              // formatDate(new Date(params.row.createDate), "yyyy-MM-dd hh:mm:ss")
              formatDate(new Date(params.row.createDate), "yyyy-MM-dd")
            );
          }
        }, {
          'title': '操作',
          'align': 'center',
          'key': 'action',
          'width': '151px',
          'className': 'opt',
          render: (h, params) => {
            return h('div', [
              h('span', {
                // class: 'iconfont icon-bianji',
                style: {
                  // fontSize: '18px', // 改变icon的样式
                  color: '#2d8cf0',
                  cursor: 'pointer',
                  display: this.$_has('IBEN_PROJECT_UPDATE_OTHER_INFO') ? 'inline-block' : 'none'
                },
                on: {
                  click: () => {
                    // 点击操作事件
                    this.inputInfoModal(params.row.projectId, params.row.name)
                  }
                }
              }, '信息录入'),

              h('span', {
                // class: 'iconfont icon-bianji',
                style: {
                  // fontSize: '18px', // 改变icon的样式
                  color: '#2d8cf0',
                  marginLeft: '4px',
                  cursor: 'pointer',
                  display: this.$_has('IBEN_PROJECT_UPDATE_STATE') ? 'inline-block' : 'none'
                },
                on: {
                  click: () => {
                    // 点击操作事件
                    this.processModal(params.row.projectId, params.row.projectState, params.row.projectStage)
                  }
                }
              }, '修改进度'),
              h('span', {
                // class: 'iconfont icon-bianji',
                style: {
                  // fontSize: '18px', // 改变icon的样式
                  color: '#2d8cf0',
                  marginLeft: '4px',
                  cursor: 'pointer',
                  display: this.$_has('IBEN_PROJECT_ADD_DEMAND') ? 'inline-block' : 'none'
                },
                on: {
                  click: () => {
                    // 点击操作事件
                    this.$router.push({ path: '/increaseDemand', query: { 'projectId': params.row.projectId, 'name': params.row.name } })
                  }
                }
              }, '新增需求'),
              h('span', {
                // class: 'iconfont icon-bianji',
                style: {
                  // fontSize: '18px', // 改变icon的样式
                  color: '#2d8cf0',
                  marginLeft: '4px',
                  cursor: 'pointer',
                  // display: this.$_has('IBEN_PROJECT_UPDATE') ? 'inline-block' : 'none'
                  display: params.row.createUserId == this.userId || this.roleId == 1 ? 'inline-block' : 'none'
                },
                on: {
                  click: () => {
                    // 点击操作事件
                    this.modifyModal(params.row.projectId)
                  }
                }
              }, '修改'),
              h('span', {
                // props: {
                //   type: 'md-trash' // iview自带的删除icon
                // },
                style: {
                  // fontSize: '20px', // 改变icon的样式
                  color: '#2d8cf0',
                  cursor: 'pointer',
                  marginLeft: '4px',
                  display: params.row.createUserId == this.userId || this.roleId == 1 ? 'inline-block' : 'none'
                },
                on: {
                  click: () => {
                    this.deleteModal(params.row.projectId)
                  }
                }
              }, '删除'),
              h('span', {
                // props: {
                //   type: 'md-trash' // iview自带的删除icon
                // },
                style: {
                  // fontSize: '20px', // 改变icon的样式
                  color: '#2d8cf0',
                  cursor: 'pointer',
                  marginLeft: '4px',
                },
                on: {
                  click: () => {
                    this.$router.push({ path: '/viewDetail', query: { projectId: params.row.projectId } })
                  }
                }
              }, '查看详情')
            ])
          }
        }],
      pageInfo: {}

    }
  },
  created () {
    this.queryPageInfo();
  },
  computed: {
    roleId () {
      return this.$store.state.currentUserInfo.roleId
    },
    userId () {
      return this.$store.state.currentUserInfo.userId
    }
  },
  methods: {
    changeCheck (id) {
      // console.log(this.hardwareIds)
      // console.log(this.myHardwares)
    },
    changeSearchDate (e) {
      this.search.startDate = e[0]
      this.search.endDate = e[1]
    },
    uploadHardFile (file) {
      this.hardwareF = file
      return false
    },
    uploadSoftFile (file) {
      this.softwareF = file
      return false
    },
    uploadOperateFile (file) {
      this.operateF = file
      return false
    },
    // 获取角色列表
    queryRoleListInfo () {
      let params = {}
      this.$post('/pm/getEnterpriseRoleList', params).then(data => {
        if(data.rs === 1) {
          // 过滤一下除了id != 1的角色
          this.roleList = data.roleList.filter(item => {
            return item.roleId !== 1
          })
          // 将默认的角色 = 第一个角色赋值
          if(this.roleList && this.roleList.length > 0) {
            this.roleList.some((item, i) => {
              if(i === 0) {
                this.defaultRoleId = item.roleId
                return true
              }
            })
          }
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
        obj[key] = obj[key] || ''
      }
      return obj
    },
    // 获取分页信息
    queryPageInfo () {
      let params = {
        pagenum: this.pageNow,
        pagesize: this.pagesize,
        ...this.filterObj(this.search)
      }
      this.$Spin.show()
      this.$post('/project/queryPageList', params).then(data => {
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
    deleteModal (projectId) {
      this.projectId = projectId
      this.modal_delete = true
    },
    // 确认删除
    deleteMethod (projectId) {
      this.$post('/project/delete', {
        projectId: projectId
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
    downloadModal () {
      this.modal_download = true
    },
    //下载文件
    downFile (src) {
      let url = this.$baseUrl + src
      window.open(url, 'download');
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
          this.$post('/project/add', this.filterObj(this.addInfo)).then(data => {
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
    modifyModal (projectId) {
      this.modifyInfo = {}
      this.modal_modify = true
      this.$post('/project/queryDetail', {
        projectId: projectId
      }).then(data => {
        if(data.rs === 1) {
          this.modifyInfo = data.data
          delete this.modifyInfo.modifyDate
          delete this.modifyInfo.createDate
          this.handleResDate(this.modifyInfo, ['signingDate', 'contractDeliverGoodsDate', 'contractDeliverDate', 'payDate'])
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
          this.$post('/project/update', this.filterObj(this.modifyInfo)).then(data => {
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
    // 信息录入弹窗
    inputInfoModal (projectId, name) {
      this.inputInfo = {}
      this.hardwareF = null
      this.softwareF = null
      this.operateF = null
      this.projectId = projectId

      if(this.roleId == 4) { // 技术
        this.$router.push({ path: '/schedule', query: { projectId, name } })
        return false
      }
      this['modal_input' + this.roleId] = true
      this.$Spin.show()
      this.$post('/project/getInformation', {
        projectId: projectId
      }).then(data => {
        this.$Spin.hide()
        if(data.rs === 1) {
          this.inputInfo = data.data || {}
          this.handleResDate(this.inputInfo, ['deliverGoodsDate', 'expectedArrivalDate', 'deliverDate'])

          delete this.inputInfo.createDate
          delete this.inputInfo.modifyDate

          switch(this.roleId) {
            case 3: // 售前
              this.myHardwares = [...this.inputInfo.hardwares] || []
              delete this.inputInfo.hardwares
              var ids = []
              this.myHardwares.forEach(item => {
                if(item.check) {
                  item.check = true
                  ids.push(item.id)
                } else {
                  item.check = false
                }
              })
              this.hardwareIds = ids
              break;

            default:
              break;
          }
        } else {
          if(data.data && data.data.errorMsg) {
            this.$Message.error(data.data.errorMsg);
          } else {
            this.$Message.error(data.errorMsg);
          }
        }
      })
    },
    delFile (name) {
      if(name == 'hard') {
        this.hardwareF = null
        this.inputInfo.hardwareFile = ''
        this.inputInfo.hardwareFileName = ''
      } else if(name == 'soft') {
        this.softwareF = null
        this.inputInfo.softwareFile = ''
        this.inputInfo.softwareFileName = ''
      } else {
        this.operateF = null
        this.inputInfo.operateFile = ''
        this.inputInfo.operateFileName = ''
      }
    },
    // 确认信息录入
    inputInfoMethod () {
      var fd = new FormData()

      for(var key in this.inputInfo) {
        var val = this.inputInfo[key]
        if(val) {
          fd.append(key, val)
        }
      }

      fd.set('projectId', this.projectId)

      switch(this.roleId) {
        case 3: // 售前
          var hardwares = []
          for(var i = 0; i < this.myHardwares.length; i++) {
            var item = this.myHardwares[i]
            if(item.check) {
              // if(!item.demand) {
              //   this.$Message.error('请输入' + item.name + '的定制详情')
              //   return false
              // }
              hardwares.push({
                id: item.id,
                demand: item.demand,
                check: item.check ? 1 : 0
              })
            }
          }
          fd.append('hardwares', JSON.stringify(hardwares))

          if(this.hardwareF) {
            fd.append('hardwareF', this.hardwareF)
          }
          if(this.softwareF) {
            fd.append('softwareF', this.softwareF)
          }
          break;
        case 6: // 运营
          if(this.operateF) {
            fd.append('operateF', this.operateF)
          }
          break;
        default:
          break;
      }
      this.$post_('/project/enterInformation?_token_iben=' + this.$store.state.token, fd).then(data => {
        this.$Spin.hide()
        if(data.rs === 1) {
          this.$Message.success('保存成功!');
          this['modal_input' + this.roleId] = false
        } else {
          if(data.data && data.data.errorMsg) {
            this.$Message.error(data.data.errorMsg);
          } else {
            this.$Message.error(data.errorMsg);
          }
        }
      })

    },
    // 打开修改弹窗
    processModal (projectId, projectState, projectStage) {
      this.modal_process = true
      this.processInfo = { projectId, projectState, projectStage }
    },
    // 确认修改进度
    processInfoMethod (name) {
      this.$Spin.show()
      this.$post('/project/updateState', {
        ...this.filterObj(this.processInfo)
      }).then(data => {
        this.$Spin.hide()
        if(data.rs === 1) {
          this.modal_process = false
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
    },
    cancel (name) {
      this.modal_add = false
      this.modal_modify = false
      this.modal_delete = false
      this.modal_input2 = false
      this.modal_input3 = false
      this.modal_input4 = false
      this.modal_input5 = false
      this.modal_input6 = false
      this.modal_process = false
      this.hardwareF = null
      this.softwareF = null
      this.operateF = null
      if(name) {
        this.$refs[name].resetFields();
      }
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
.query_btn {
  margin-left: 0;
}
.query_btn button {
  margin-right: 0;
  margin-left: 10px;
}
/* .project /deep/ .opt .ivu-table-cell span:hover {
  color: #3a5da9 !important;
} */
</style>