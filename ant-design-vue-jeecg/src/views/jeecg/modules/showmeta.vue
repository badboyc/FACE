<template>
    <a-modal
      class="announcementCustomModal"
      :width="modelStyle.width"
      :visible="visible"
      :bodyStyle ="bodyStyle"
      @cancel="handleCancel"
      destroyOnClose
      :footer="null"
    >
      <template slot="title">
        <a-button icon="fullscreen" class="custom-btn" @click="handleClickToggleFullScreen"/>
      </template>
      <a-card class="daily-article" :loading="loading">
        <a-card-meta
          :title="'详情'"
          :description="'创建人：'+record.createBy +' 创建时间： ' + record.createTime">
        </a-card-meta>
        <a-divider />
        产品名称：<span v-html="record.productName" class="article-content"></span><br>
        供应商名称：<span v-html="record.supplierName" class="article-content"></span><br>
        供应商地址：<span v-html="record.supplierAddress" class="article-content"></span><br>
        联系方式：<span v-html="record.contact" class="article-content"></span><br>
        产品版本号：<span v-html="record.version" class="article-content"></span><br>
        上一版本号：<span v-html="record.previousVersion" class="article-content"></span><br>
        关键词：<span v-html="record.keyWord" class="article-content"></span><br>
        产品研发及应用背景：<span v-html="record.background" class="article-content"></span><br>
        产品主要功能描述：<span v-html="record.functionDescription" class="article-content"></span><br>
        硬件环境基础要求：<span v-html="record.hardwareRequirements" class="article-content"></span><br>
        环境依赖包/工具：<span v-html="record.envDependency" class="article-content"></span><br>
        一致性认证结果：<span v-html="record.certificateResult" class="article-content"></span><br>
        相关库链接：<span v-html="record.libraryLink" class="article-content"></span><br>
        许可类别：<span v-html="record.licenseCategory" class="article-content"></span><br>
        版本状态：<span v-text="verstatus(record)" class="article-content"></span><br>
        审核状态：<span v-text="revstatus(record)" class="article-content"></span><br>
        审核结论：<span v-html="record.reviewResult" class="article-content"></span><br>
        审核人ID：<span v-html="record.updateBy" class="article-content"></span><br>
        审核时间：<span v-html="record.updateTime" class="article-content"></span><br>
        UoC包：<a href="javascript:;" @click="handleDownload(record)">{{getfilename(record)}}</a>
      </a-card>
    </a-modal>
</template>

<script>
  import Vue from 'vue'
  import { axios } from '@/utils/request'
    export default {
        name: "showmeta",
        data() {
            return {
                record: {},
                visible: false,
                loading: false,
                labelCol: {
                    xs: { span: 24 },
                    sm: { span: 5 },
                },
                wrapperCol: {
                    xs: { span: 24 },
                    sm: { span: 16 },
                },
                bodyStyle:{
                    padding: "0",
                    height:(window.innerHeight)+"px",
                    "overflow-y":"auto",

                },
                modelStyle:{
                    width: '60%',
                    style: { top: '20px' },
                    fullScreen: false
                }
            }
        },
        methods: {
            handleClickToggleFullScreen() {
                let mode = !this.modelStyle.fullScreen
                if (mode) {
                    this.modelStyle.width = '100%'
                    this.modelStyle.style.top = '80px'
                } else {
                    this.modelStyle.width = '60%'
                    this.modelStyle.style.top = '80px'
                }
                this.modelStyle.fullScreen = mode
            },
            getfilename(record){
              var url=record.uocPackageUrl;
              var urlArr = url.split('?');
              var k = urlArr[0], appU = k.split('/');
              var L = appU[appU.length - 1];
              return L;
            },
          handleDownload: function (value) {
            //window.open(value.downloadpath)
            axios.get(value.uocPackageUrl, {
              responseType: 'arraybuffer', // 或者responseType: 'blob'
              xsrfHeaderName: 'Authorization',
              headers: {
                'Content-Type': 'application/json',
                'Authorization': 'Bearer ' + Vue.ls.get(ACCESS_TOKEN),
              }
            }).then(res => {
              const blob = new Blob([res.data], {
                type: 'application/vnd.ms-excel'
              })
              const objectUrl = URL.createObjectURL(blob)
              window.location.href = objectUrl
            }).catch(err => {
              console.log(err)
            })
          },
          //handleDownload: function (value) {
            //window.open(value.uocPackageUrl)
          //},
            detail (record) {
                this.visible = true;
                this.record = record;
            },
          verstatus(record){
            if(record.versionStatus=='0'){
              return "最新版";
            }else if(record.versionStatus=='1'){
              return "旧版";
            }else if(record.versionStatus=='2'){
              return "申请移除";
            }else if(record.versionStatus=='3'){
              return "已移除";
            } else{
              return record.versionStatus;
            }
          },

          revstatus(record){
               if(record.reviewStatus=='0'){
                   return "待审核";
                }else if(record.reviewStatus=='1'){
                    return "审核中";
                }else if(record.reviewStatus=='2'){
                    return "审核已通过";
                }else if(record.reviewStatus=='3'){
                 return "审核未通过";
               } else{
                    return record.reviewStatus;
                }
            },

            handleCancel(e) {
                this.visible = false
            },
        }
    }
</script>

<style lang="less">
  .announcementCustomModal{
    .ant-modal-header {
      border: none;
      display: inline-block;
      position: absolute;
      z-index: 1;
      right: 56px;
      padding: 0;
      .ant-modal-title{
        .custom-btn{
          width: 56px;
          height: 56px;
          border: none;
          box-shadow: none;
        }
      }
    }
    .daily-article{
      border-bottom: 0;
    }
  }
</style>
<style scoped lang="less">
  .daily-article {
    .article-button {
      font-size: 1.2rem !important;
    }
    .ant-card-body {
      padding: 18px !important;
    }
    .ant-card-head {
      padding: 0 1rem;
    }
    .ant-card-meta {
      margin-bottom: 1rem;
    }
    .article-content {
      p {
        word-wrap: break-word;
        word-break: break-all;
        text-overflow: initial;
        white-space: normal;
        font-size: .9rem !important;
        margin-bottom: .8rem;
      }
    }
  }
</style>