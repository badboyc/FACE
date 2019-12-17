<template>
  <a-card :bordered="false">

    <!-- 操作按钮区域 -->
    <div class="table-operator">
      <a-button @click="handleAdd" type="primary" icon="plus" >发布新产品</a-button>
      <a-button @click="AllPublished" type="primary">已发布</a-button>
      <a-button @click="PendingReview" type="primary">待审核</a-button>
      <a-button @click="ReviewOk" type="primary">审核已通过</a-button>
      <a-button @click="ReviewNotOk" type="primary">审核未通过</a-button>
      <a-button @click="Removed" type="primary">已移除</a-button>
      <a-dropdown v-if="selectedRowKeys.length > 0">
        <a-menu slot="overlay">
          <a-menu-item key="1" @click="batchDel"><a-icon type="delete"/>删除</a-menu-item>
        </a-menu>
        <a-button style="margin-left: 8px"> 批量操作 <a-icon type="down" /></a-button>
      </a-dropdown>
    </div>

    <!-- table区域-begin -->
    <div>
      <div class="ant-alert ant-alert-info" style="margin-bottom: 16px;">
        <i class="anticon anticon-info-circle ant-alert-icon"></i> 已选择 <a style="font-weight: 600">{{ selectedRowKeys.length }}</a>项
        <a style="margin-left: 24px" @click="onClearSelected">清空</a>
      </div>

      <a-table
        ref="table"
        size="middle"
        bordered
        rowKey="id"
        :columns="columns"
        :dataSource="dataSource"
        :pagination="ipagination"
        :loading="loading"
        :rowSelection="{selectedRowKeys: selectedRowKeys, onChange: onSelectChange}"
        @change="handleTableChange">

        <span slot="action" slot-scope="text, record">
          <a href="javascript:;" @click="handleDetail(record)">详情</a>

          <a-divider type="vertical" />
          <a-dropdown>
            <a class="ant-dropdown-link">操作 <a-icon type="down" /></a>
            <a-menu slot="overlay">
              <a-menu-item>
                  <a href="javascript:;" @click="handleRemoveapply(record)" >申请移除</a>
              </a-menu-item>
              <a-menu-item>
                  <a href="javascript:;" @click="handleDownload(record)">下载</a>
              </a-menu-item>
            </a-menu>
          </a-dropdown>
        </span>

      </a-table>
    </div>
    <!-- table区域-end -->

    <!-- 表单区域 -->
    <metaData-modal ref="modalForm" @ok="modalFormOk"></metaData-modal>
    <showmeta ref="showmeta" @ok="modalFormOk2"></showmeta>
  </a-card>
</template>

<script>
  import Vue from 'vue'
  import { USER_INFO} from "@/store/mutation-types"
  import MetaDataModal from './modules/MetaDataModal'
  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import {initDictOptions, filterDictText} from '@/components/dict/JDictSelectUtil'
  import showmeta from "./modules/showmeta"
  import { getAction,putAction } from '@/api/manage'
  import { ACCESS_TOKEN } from "@/store/mutation-types"
  import { axios } from '@/utils/request'
  export default {
    name: "MetaDataList",
    mixins:[JeecgListMixin],
    components: {
      MetaDataModal,
      showmeta
    },
    data () {
      return {
        description: '元数据管理页面',
        // 表头
        columns: [
          {
            title: '产品编号',
            dataIndex: '',
            key:'rowIndex',
            width:60,
            align:"center",
            customRender:function (t,r,index) {
              return parseInt(index)+1;
            }
          },
          {
            title: '产品名称',
            align:"center",
            dataIndex: 'productName'
          },
          {
            title: '创建人ID',
            align:"center",
            dataIndex: 'createBy'
          },
            {
                title: '下载量',
                align:"center",
                dataIndex: 'downloadCount',
                sorter: true
            },
          {
            title: '创建时间',
            align:"center",
            dataIndex: 'createTime'
          },
          {
            title: '供应商名称',
            align:"center",
            dataIndex: 'supplierName'
          },
          //{
          //     title: '供应商地址',
          //     align:"center",
          //     dataIndex: 'supplierAddress'
          //    },
          //{
          //     title: '联系方式',
          //     align:"center",
          //     dataIndex: 'contact'
          //    },
          //{
          //     title: '产品版本号',
          //     align:"center",
          //     dataIndex: 'version'
          //    },
          {
            title: '关键词',
            align:"center",
            dataIndex: 'keyWord'
          },
          //{
          //     title: '产品研发及应用背景',
          //     align:"center",
          //     dataIndex: 'background'
          //    },
          //{
          //     title: '产品主要功能描述',
          //     align:"center",
          //     dataIndex: 'functionDescription'
          //    },
          //{
          //     title: '硬件环境基础要求',
          //     align:"center",
          //     dataIndex: 'hardwareRequirements'
          //    },
          //{
          //     title: '环境依赖包/工具',
          //     align:"center",
          //     dataIndex: 'envDependency'
          //    },
          //{
          //     title: '一致性认证结果',
          //     align:"center",
          //     dataIndex: 'certificateResult'
          //    },
          //{
          //     title: '相关库链接',
          //     align:"center",
          //     dataIndex: 'libraryLink'
          //    },
          //{
          //     title: '许可类别',
          //     align:"center",
          //     dataIndex: 'licenseCategory'
          //    },
          {
               title: '版本状态',
               align:"center",
               dataIndex: 'versionStatus',
              customRender:(text, record, index) => {
              return filterDictText(this.versionstatus,text);
            }
          },
          {
            title: '审核状态',
            align:"center",
            dataIndex: 'reviewStatus',
            customRender:(text, record, index) => {
              return filterDictText(this.metastatus,text);
            }
          },
          //{
          //     title: '审核结论',
          //     align:"center",
          //    dataIndex: 'reviewResult'
          //    },
          {
            title: '操作',
            dataIndex: 'action',
            align:"center",
            scopedSlots: { customRender: 'action' },
          }
        ],
        url: {
          list: "/meta/metaData/list",
          delete: "/meta/metaData/delete",
          deleteBatch: "/meta/metaData/deleteBatch",
          exportXlsUrl: "meta/metaData/exportXls",
          importExcelUrl: "meta/metaData/importExcel",
          detail:"meta/metaData/queryById",
        },
      }
    },
    created(){
      this.initDictConfig();
    },
    computed: {
      importExcelUrl: function(){
        return `${window._CONFIG['domianURL']}/${this.url.importExcelUrl}`;
      }
    },
    methods: {
      initDictConfig() {
        //初始化字典 - 处理状态
        initDictOptions('version_status').then((res) => {
          if (res.success) {
            this.versionstatus = res.result;
          }
        });
        initDictOptions('meta_status').then((res) => {
          if (res.success) {
            this.metastatus = res.result;
          }
        });
      },
      handleDetail(record){
        getAction(this.url.detail,{id:record.id}).then((res)=>{
          if(res.success){
            this.loadData();
          }
        });
        this.$refs.showmeta.detail(record);
      },
      handleDownload: function (value) {
        var btn = document.createElement("a");
        // btn.setAttribute('download', filename);// download属性
        btn.setAttribute('href', value.uocPackageUrl);// href链接
        btn.click();//自执行点击事件
      },
      AllPublished: function() {
        var params ={createBy:Vue.ls.get(USER_INFO).username,versionStatus:'0,1'};//查询条件
        this.loading = true;
        getAction(this.url.list, params).then((res) => {
          if (res.success) {
            this.dataSource = res.result.records;
            this.ipagination.total = res.result.total;
          }
          if(res.code===510){
            this.$message.warning(res.message)
          }
          this.loading = false;
        })
      },
      PendingReview: function() {
        var params ={createBy:Vue.ls.get(USER_INFO).username,reviewStatus:'0',versionStatus:'0,1'};//查询条件
        this.loading = true;
        getAction(this.url.list, params).then((res) => {
          if (res.success) {
            this.dataSource = res.result.records;
            this.ipagination.total = res.result.total;
          }
          if(res.code===510){
            this.$message.warning(res.message)
          }
          this.loading = false;
        })
      },
      ReviewOk: function() {
        var params ={createBy:Vue.ls.get(USER_INFO).username,reviewStatus:'1',versionStatus:'0,1'};//查询条件
        this.loading = true;
        getAction(this.url.list, params).then((res) => {
          if (res.success) {
            this.dataSource = res.result.records;
            this.ipagination.total = res.result.total;
          }
          if(res.code===510){
            this.$message.warning(res.message)
          }
          this.loading = false;
        })
      },
      ReviewNotOk: function() {
        var params ={createBy:Vue.ls.get(USER_INFO).username,reviewStatus:'2',versionStatus:'0,1'};//查询条件
        this.loading = true;
        getAction(this.url.list, params).then((res) => {
          if (res.success) {
            this.dataSource = res.result.records;
            this.ipagination.total = res.result.total;
          }
          if(res.code===510){
            this.$message.warning(res.message)
          }
          this.loading = false;
        })
      },
      Removed: function() {
        var params ={createBy:Vue.ls.get(USER_INFO).username,versionStatus:'2'};//查询条件
        this.loading = true;
        getAction(this.url.list, params).then((res) => {
          if (res.success) {
            this.dataSource = res.result.records;
            this.ipagination.total = res.result.total;
          }
          if(res.code===510){
            this.$message.warning(res.message)
          }
          this.loading = false;
        })
      },
      handleAdd: function () {
        this.$refs.modalForm.add();
        this.$refs.modalForm.title = "发布";
        this.$refs.modalForm.disableSubmit = false;
      },
      handleEdit: function (record){
        this.$refs.modalForm.edit(record);
        this.$refs.modalForm.title = "更新";
        this.$refs.modalForm.disableSubmit = false;
      },
      handleRemoveapply:function (record){
        if(record.versionStatus=='1'){
          return this.$message.success('已申请移除成功，请勿重复申请！');
        }
        this.$refs.modalForm.editstatus (record);
        this.$refs.modalForm.disableSubmit = false;
        this.$refs.modalForm.handleOkstatus (1);
      },
      handleRemove:function (record){
        this.$refs.modalForm.editstatus (record);
        this.$refs.modalForm.disableSubmit = false;
        this.$refs.modalForm.handleOkstatus (2);
      }

    }
  }
</script>
<style scoped>
  @import '~@assets/less/common.less'
</style>