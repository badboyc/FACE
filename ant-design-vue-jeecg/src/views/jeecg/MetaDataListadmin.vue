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
              <a-form-item label="审核状态">
                <a-select v-model="queryParam.reviewStatus" placeholder="请设置审核状态"   v-decorator="['reviewStatus', {}]" default-value="0">
                  <a-select-option v-for="(item, key) in metastatus" :key="key" :value="item.value">
              <span style="display: inline-block;width: 100%" :title=" item.text || item.label ">
                {{ item.text || item.label }}
              </span>
                  </a-select-option>
                  <!--            <a-select-option value="0">未处理</a-select-option>-->
                  <!--            <a-select-option value="1">正在处理</a-select-option>-->
                  <!--            <a-select-option value="2">处理完成</a-select-option>-->
                </a-select>
                <!--<a-input placeholder="请选择审核状态" v-model="queryParam.reviewStatus"></a-input>-->
              </a-form-item>
            </a-col>
            <a-col :md="6" :sm="8">
              <a-form-item label="版本状态">
                <a-select  placeholder="请设置版本状态" @click="searchQuery"  v-model="queryParam.versionStatus" v-decorator="['versionStatus', {}]" default-value="0">
                  <a-select-option v-for="(item, key) in versionstatus" :key="key" :value="item.value">
              <span style="display: inline-block;width: 100%" :title=" item.text || item.label ">
                {{ item.text || item.label }}
              </span>
                  </a-select-option>
                  <!--            <a-select-option value="0">未处理</a-select-option>-->
                  <!--            <a-select-option value="1">正在处理</a-select-option>-->
                  <!--            <a-select-option value="2">处理完成</a-select-option>-->
                </a-select>
                <!--<a-input placeholder="请选择版本状态" v-model="queryParam.versionStatus"></a-input>-->
              </a-form-item>
            </a-col>
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

    <!-- 操作按钮区域 -->
    <div class="table-operator">
      <a-button @click="handleAdd" type="primary" icon="plus" v-has="'has2:provider'">发布新产品</a-button>
      <a-button type="primary" icon="download" @click="handleExportXls('元数据')">导出</a-button>
      <a-upload name="file" :showUploadList="false" :multiple="false" :headers="tokenHeader" :action="importExcelUrl" @change="handleImportExcel">
        <a-button type="primary" icon="import">导入</a-button>
      </a-upload>
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
              <a-menu-item v-has="'has1:status'">
                  <a @click="handleEdit(record)" >审核</a>
              </a-menu-item>
              <a-menu-item v-has="'has2:provider'">
                  <a href="javascript:;" @click="handleRemoveapply(record)" >申请移除</a>
              </a-menu-item>
              <a-menu-item v-has="'has1:status'">
                  <a href="javascript:;" @click="handleRemove(record)">移除</a>
              </a-menu-item>
              <a-menu-item>
                  <a href="javascript:;" @click="handleDownload(record)">下载</a>
              </a-menu-item>
              <a-menu-item v-has="'has1:status'">
                <a-popconfirm title="确定删除吗?" @confirm="() => handleDelete(record.id)">
                  <a>删除</a>
                </a-popconfirm>
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
      //handleDownload: function (value) {
        //window.open(value.uocPackageUrl)
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