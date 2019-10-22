<template>
  <a-modal
    :title="title"
    :width="1200"
    :visible="visible"
    :confirmLoading="confirmLoading"
    @ok="handleOk"
    @cancel="handleCancel"
    cancelText="关闭">
    
    <a-spin :spinning="confirmLoading">
      <a-form :form="form">
      
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="产品名称">
          <a-input :disabled="isDisabledAuth('provider')" placeholder="请输入产品名称" v-decorator="['productName', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="供应商名称">
          <a-input :disabled="isDisabledAuth('provider')" placeholder="请输入供应商名称" v-decorator="['supplierName', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="供应商地址">
          <a-input :disabled="isDisabledAuth('provider')" placeholder="请输入供应商地址" v-decorator="['supplierAddress', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="联系方式">
          <a-input :disabled="isDisabledAuth('provider')" placeholder="请输入联系方式" v-decorator="['contact', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="产品版本号">
          <a-input :disabled="isDisabledAuth('provider')" placeholder="请输入产品版本号" v-decorator="['version', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="上一版本号">
          <a-input :disabled="isDisabledAuth('provider')" placeholder="请输入上一版本号" v-decorator="['previousVersion', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="关键词">
          <a-input placeholder="请输入关键词" v-decorator="['keyWord', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="产品研发及应用背景">
          <a-textarea placeholder="请输入产品研发及应用背景" rows="10" v-decorator="['background', {}]" />
          <!--<a-input placeholder="请输入产品研发及应用背景" v-decorator="['background', {}]" />-->
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="产品主要功能描述">
          <a-textarea placeholder="请输入产品主要功能描述" rows="10" v-decorator="['functionDescription', {}]" />
          <!--<a-input placeholder="请输入产品主要功能描述" v-decorator="['functionDescription', {}]" />-->
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="硬件环境基础要求">
          <a-textarea placeholder="请输入硬件环境基础要求" rows="5" v-decorator="['hardwareRequirements', {}]" />
          <!--<a-input placeholder="请输入硬件环境基础要求" v-decorator="['hardwareRequirements', {}]" />-->
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="环境依赖包/工具">
          <a-textarea placeholder="请输入环境依赖包/工具" rows="5" v-decorator="['envDependency', {}]" />
          <!--<a-input placeholder="请输入环境依赖包/工具" v-decorator="['envDependency', {}]" />-->
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="一致性认证结果">
          <a-textarea placeholder="请输入一致性认证结果" rows="2" v-decorator="['certificateResult', {}]" />
          <!--<a-input placeholder="请输入一致性认证结果" v-decorator="['certificateResult', {}]" />-->
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="相关库链接">
          <a-textarea placeholder="请输入相关库链接" rows="2" v-decorator="['libraryLink', {}]" />
        </a-form-item>
        <a-form-item
          v-if="false"
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="UoC包下载链接">
          <a-input placeholder="请输入UoC包下载链接" v-decorator="['uocPackageUrl', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="许可类别">
          <a-input placeholder="请输入许可类别" v-decorator="['licenseCategory', {}]" />
        </a-form-item>
        <a-form-item
          v-has="'has1:status'"
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="版本状态">
          <!--<a-input placeholder="请输入版本状态" v-decorator="['versionStatus', {}]" />-->
          <a-select  placeholder="请设置版本状态"   v-decorator="['versionStatus', {}]" default-value="0">
            <a-select-option v-for="(item, key) in versionstatus" :key="key" :value="item.value">
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
          v-has="'has1:status'"
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
          v-has="'has1:status'"
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="审核结论">
          <a-textarea placeholder="请输入审核结论" rows="2" v-decorator="['reviewResult', {}]" />
        </a-form-item>
        <a-form-item label="上传UoC包" :labelCol="labelCol" :wrapperCol="wrapperCol" >
          <a-upload
            name="file"
            :multiple="true"
            :action="uploadAction"
            :headers="headers"
            :data="{'isup':1,'bizPath':bizPath}"
            :fileList="fileList"
            :beforeUpload="beforeUpload"
            @change="handleChange">
            <a-button>
              <a-icon type="upload" />{{ text }}
            </a-button>
          </a-upload>
        </a-form-item>
		
      </a-form>
    </a-spin>
  </a-modal>
</template>

<script>
  import Vue from 'vue'
  import { httpAction } from '@/api/manage'
  import pick from 'lodash.pick'
  import moment from "moment"
  import { ACCESS_TOKEN } from "@/store/mutation-types"
  import { disabledAuthFilter } from "@/utils/authFilter"
  import {initDictOptions, filterDictText} from '@/components/dict/JDictSelectUtil'
  const FILE_TYPE_ALL = "all"
  const FILE_TYPE_IMG = "image"
  const FILE_TYPE_TXT = "file"
  const uidGenerator=()=>{
    return '-'+parseInt(Math.random()*10000+1,10);
  }
  const getFileName=(path)=>{
    if(path.lastIndexOf("\\")>=0){
      let reg=new RegExp("\\\\","g");
      path = path.replace(reg,"/");
    }
    return path.substring(path.lastIndexOf("/")+1);
  }
  export default {
    name: "MetaDataModal",
    data () {
      return {
        title:"发布新产品",
        visible: false,
        versionstatus:[],
        metastatus:[],
        model: {},
        labelCol: {
          xs: { span: 24 },
          sm: { span: 5 },
        },
        wrapperCol: {
          xs: { span: 24 },
          sm: { span: 16 },
        },

        confirmLoading: false,
        form: this.$form.createForm(this),
        validatorRules:{
        },
        url: {
          add: "/meta/metaData/add",
          edit: "/meta/metaData/edit",
        },
        uploadAction:window._CONFIG['domianURL']+"/sys/common/upload",
        urlDownload:window._CONFIG['domianURL'] + "/sys/common/download/",
        headers:{},
        fileList: [],
        uocPackageUrl:''
      }
    },
    props:{
      text:{
        type:String,
        required:false,
        default:"点击上传"
      },
      fileType:{
        type:String,
        required:false,
        default:FILE_TYPE_ALL
      },
      /*这个属性用于控制文件上传的业务路径*/
      bizPath:{
        type:String,
        required:false,
        default:"temp"
      },
      value:{
        type:String,
        required:false
      },
      //此属性被废弃了
      triggerChange:{
        type: Boolean,
        required: false,
        default: false
      },
    },
    watch:{
      value(val){
        this.initFileList(val)
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
      onChange(value, dateString) {
        console.log('Selected Time: ', value);
        console.log('Formatted Selected Time: ', dateString);
      },
      onOk(value) {
        console.log('onOk: ', value);
      },
      add () {
        this.edit({});
        this.uocPackageUrl=''
      },
      edit (record) {
        this.form.resetFields();
        this.model = Object.assign({}, record);
        this.visible = true;
        this.$nextTick(() => {
          this.form.setFieldsValue(pick(this.model,'productName','supplierName','supplierAddress','contact','version','previousVersion','keyWord','background','functionDescription','hardwareRequirements','envDependency','certificateResult','libraryLink','uocPackageUrl','licenseCategory','versionStatus','reviewStatus','reviewResult'))
		  //时间格式化
        });
      },
      editstatus (record) {
        this.form.resetFields();
        this.model = Object.assign({}, record);
        this.$nextTick(() => {
          this.form.setFieldsValue(pick(this.model,'versionStatus','reviewStatus'))
          //时间格式化
        });

      },
      handleOkstatus (code) {
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
            formData.versionStatus=code;
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
      initFileList(paths){
        if(!paths || paths.length==0){
          this.fileList = [];
          return;
        }
        let fileList = [];
        let arr = paths.split(",")
        for(var a=0;a<arr.length;a++){
          fileList.push({
            uid:uidGenerator(),
            name:getFileName(arr[a]),
            status: 'done',
            url: this.urlDownload+arr[a],
            response:{
              status:"history",
              message:arr[a]
            }
          })
        }
        this.fileList = fileList
      },

      handlePathChange(){
        let uploadFiles = this.fileList
        let path = ''
        if(!uploadFiles || uploadFiles.length==0){
          path = ''
        }
        let arr = [];

        for(var a=0;a<uploadFiles.length;a++){
          arr.push(uploadFiles[a].response.message)
        }
        if(arr.length>0){
          path = arr.join(",")
        }
        this.$emit('change', path);
      },

      beforeUpload(file){
        var fileType = file.type;
        if(fileType===FILE_TYPE_IMG){
          if(fileType.indexOf('image')<0){
            this.$message.warning('请上传图片');
            return false;
          }
        }else if(fileType===FILE_TYPE_TXT){
          if(fileType.indexOf('image')>=0){
            this.$message.warning('请上传文件');
            return false;
          }
        }
        //TODO 扩展功能验证文件大小
        return true
      },
      handleChange(info) {
        console.log("--文件列表改变--")
        let fileList = info.fileList
        fileList = fileList.slice(-3);
        if(info.file.status==='done'){
          if(info.file.response.success){
            fileList = fileList.map((file) => {
              if (file.response) {
                file.url = this.urlDownload + file.response.message;
                this.uocPackageUrl = file.url;
              }
              return file;
            });
            this.fileList = fileList;
          }
          this.$message.success(`${info.file.name} 上传成功!`);
        }else if (info.file.status === 'error') {
          this.$message.error(`${info.file.name} 上传失败.`);
        }else if(info.file.status === 'removed'){
          this.handleDelete(info.file)
        }
        this.fileList = fileList
        if(info.file.status==='done' || info.file.status === 'removed'){
          this.handlePathChange()
        }
      },

      handleDelete(file){
        //如有需要新增 删除逻辑
        console.log(file)
      },
      close () {
        this.$emit('close');
        this.visible = false;
        this.fileList = [];
      },
      handleOk () {
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
      handleCancel () {
        this.close()
      },


    }
  }
</script>

<style lang="less" scoped>

</style>