<template>
    <a-modal
      class="announcementCustomModal"
      :width="modelStyle.width"
      :visible="visible"
      :bodyStyle ="bodyStyle"
      :confirmLoading="confirmLoading"
      @cancel="handleCancel"
      cancelText="关闭"
      destroyOnClose
      @ok="handleOk"
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
      <!--<a-spin :spinning="confirmLoading">-->
      <a-form :form="form" @submit="handleSubmit(record)">
        <a-form-item v-has="'admin:set'" label="请输入审核结论" :wrapper-col="{ span: 2, offset: 1 }"/>
        <a-form-item
          v-has="'admin:set'"
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="审核状态">
          <!--<a-input placeholder="请输入审核状态" v-decorator="['reviewStatus', {}]" />-->
          <a-select  placeholder="请设置审核状态"   v-decorator="['reviewStatus', {}]" default-value="0">
            <a-select-option v-for="(item, key) in metastatus" :key="key" :value="item.value">
              <span style="display: inline-block;width: 100%" :title=" item.text || item.label ">
                {{ item.text || item.label }}
              </span>
            </a-select-option>
            <!--            <a-select-option value="0">未处理</a-select-option>-->
            <!--            <a-select-option value="1">正在处理</a-select-option>-->
            <!--            <a-select-option value="2">处理完成</a-select-option>-->
          </a-select>
        </a-form-item>
        <a-form-item
          v-has="'admin:set'"
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="审核结论">
          <a-textarea  placeholder="请输入审核结论" rows="2" v-decorator="['reviewResult', {}]" />
        </a-form-item>
        <a-form-item v-has="'admin:set'" :wrapper-col="{ span: 16, offset: 10 }">
          <a-button type="primary" html-type="submit">
            提交
          </a-button>
        </a-form-item>
      </a-form>
     <!-- </a-spin>-->
    </a-modal>
</template>

<script>
  import Vue from 'vue'
  import { httpAction } from '@/api/manage'
  import pick from 'lodash.pick'
  import { axios } from '@/utils/request'
  import { ACCESS_TOKEN } from "@/store/mutation-types"
  import { disabledAuthFilter } from "@/utils/authFilter"
  import {initDictOptions, filterDictText} from '@/components/dict/JDictSelectUtil'
    export default {
        name: "showmeta",
        data() {
            return {
                record: {},
                visible: false,
                loading: false,
                model: {},
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
              confirmLoading: false,
              form: this.$form.createForm(this),
              url: {
                add: "/meta/metaData/add",
                edit: "/meta/metaData/edit",
              },
                modelStyle:{
                    width: '60%',
                    style: { top: '20px' },
                    fullScreen: false
                }
            }
        },
      created () {
        this.initDictConfig();
        const token = Vue.ls.get(ACCESS_TOKEN);
        this.headers = {"X-Access-Token":token};
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
          isDisabledAuth(code){
            return disabledAuthFilter(code);
          },
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
            var btn = document.createElement("a");
            // btn.setAttribute('download', filename);// download属性
            btn.setAttribute('href', value.uocPackageUrl);// href链接
            btn.click();//自执行点击事件
          },

            detail (record) {
                this.visible = true;
                this.record = record;
            },
          verstatus(record){
            if(record.versionStatus=='0'){
              return "正常";
            }else if(record.versionStatus=='1'){
              return "申请移除";
            }else if(record.versionStatus=='2'){
              return "已移除";
            } else{
              return record.versionStatus;
            }
          },

          revstatus(record){
               if(record.reviewStatus=='0'){
                   return "待审核";
                }else if(record.reviewStatus=='1'){
                    return "审核已通过";
                }else if(record.reviewStatus=='2'){
                    return "审核未通过";
                } else{
                    return record.reviewStatus;
                }
            },
          handleSubmit(record){
           // this.form.resetFields();
            this.model = Object.assign({}, record);
            this.$nextTick(() => {
              this.form.setFieldsValue(pick(this.model,'versionStatus','reviewStatus','reviewResult'))
              //时间格式化
            });
            const that = this;
            // 触发表单验证
            this.form.validateFields((err, values) => {
              if (!err) {
                that.confirmLoading = true;
                let httpurl = '';
                let method = '';
                if(!this.model.id){
                  httpurl+=this.url.add;
                  method = 'post';
                }else{
                  httpurl+=this.url.edit;
                  method = 'put';
                }
                let formData = Object.assign(this.model, values);
                formData.uocPackageUrl=this.uocPackageUrl;
                //时间格式化
                console.log(values);
                console.log(formData);
                httpAction(httpurl,formData,method).then((res)=>{
                  if(res.success){
                    that.$message.success(res.message);
                    that.$emit('ok');
                  }else{
                    that.$message.warning(res.message);
                  }
                }).finally(() => {
                  that.confirmLoading = false;
                  that.close();
                })



              }
            })
          },
          handleOk (e){
            this.visible = false
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