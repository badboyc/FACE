<template>
  <a-card :bordered="false">

    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline">
        <a-row :gutter="24">

          <a-col :md="6" :sm="8">
            <a-form-item label="产品名称">
              <a-input placeholder="请输入产品名称" v-model="queryParam.productName"></a-input>
            </a-form-item>
          </a-col>
          <a-col :md="6" :sm="8">
            <a-form-item label="供应商名称">
              <a-input placeholder="请输入供应商名称" v-model="queryParam.supplierName"></a-input>
            </a-form-item>
          </a-col>
          <template v-if="toggleSearchStatus">
            <a-col :md="6" :sm="8">
              <a-form-item label="创建人id">
                <a-input placeholder="请输入创建人id" v-model="queryParam.createBy"></a-input>
              </a-form-item>
            </a-col>
          </template>
          <a-col :md="6" :sm="8" >
            <span style="float: left;overflow: hidden;" class="table-page-search-submitButtons">
              <a-button type="primary" @click="searchQuery" icon="search">查询</a-button>
              <a-button type="primary" @click="searchReset" icon="reload" style="margin-left: 8px">重置</a-button>
              <a @click="handleToggleSearch" style="margin-left: 8px">
                {{ toggleSearchStatus ? '收起' : '展开' }}
                <a-icon :type="toggleSearchStatus ? 'up' : 'down'"/>
              </a>
            </span>
          </a-col>

        </a-row>
      </a-form>
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
          //{
            //   title: '版本状态',
              // align:"center",
               //dataIndex: 'versionStatus',
              //customRender:(text, record, index) => {
              //return filterDictText(this.versionstatus,text);
            //}
          //},
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
          list: "/meta/metaData/listclient",
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
     // handleDownload: function (value) {
       // window.open(value.uocPackageUrl)
      //},
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
        if(record.versionStatus=='2'){
          return this.$message.success('已申请移除成功，请勿重复申请！');
        }
        this.$refs.modalForm.editstatus (record);
        this.$refs.modalForm.disableSubmit = false;
        this.$refs.modalForm.handleOkstatus (2);
      },
      handleRemove:function (record){
        this.$refs.modalForm.editstatus (record);
        this.$refs.modalForm.disableSubmit = false;
        this.$refs.modalForm.handleOkstatus (3);
      }

    }
  }
</script>
<style scoped>
  @import '~@assets/less/common.less'
</style>