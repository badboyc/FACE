<template>
  <a-modal
    :title="title"
    :width="800"
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
          label="标题">
          <a-input placeholder="请输入标题" v-decorator="['title', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="文本类型">
          <a-input-number v-decorator="[ 'type', {}]" />
        </a-form-item>
        <!--<a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="html页面内容">
          <a-input placeholder="请输入html页面内容" v-decorator="['content', {}]" />
        </a-form-item>-->
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="页面内容">
<!--
          <div class="tinymce-editor" >
-->
          <editor
            v-model="myValue"
            :init="init"
            :disabled="disabled"
            @onClick="onClick">
          </editor>
<!--          {{myValue}}-->
<!--          <p v-html='myValue'></p>-->
        </a-form-item>
      </a-form>
    </a-spin>
  </a-modal>
</template>

<script>
  import { httpAction } from '@/api/manage'
  import pick from 'lodash.pick'
  /********************************************************************************************/
  import tinymce from 'tinymce/tinymce'
  import Editor from '@tinymce/tinymce-vue'
  import 'tinymce/themes/silver/theme'
  import 'tinymce/plugins/image'
  import 'tinymce/plugins/link'
  import 'tinymce/plugins/media'
  import 'tinymce/plugins/table'
  import 'tinymce/plugins/lists'
  import 'tinymce/plugins/contextmenu'
  import 'tinymce/plugins/wordcount'
  import 'tinymce/plugins/colorpicker'
  import 'tinymce/plugins/textcolor'
  import 'tinymce/plugins/fullscreen'
  /****************************************************************************************/
  import moment from "moment"

  export default {
    name: "TextModal",
      /*******************************************************************************/
      components: {
          Editor
      },
      props: {
          value: {
              type: String,
              required:false
          },
          triggerChange:{
              type: Boolean,
              default: false,
              required:false
          },
          disabled: {
              type: Boolean,
              default: false
          },
          plugins: {
              type: [String, Array],
              default: 'lists image media table textcolor wordcount contextmenu fullscreen'
          },
          toolbar: {
              type: [String, Array],
              default: 'undo redo |  formatselect | bold italic | alignleft aligncenter alignright alignjustify | bullist numlist outdent indent | lists image media table | removeformat | fullscreen'
          }
      },
      /*********************************************************************************************************************/
    data () {
      return {
      /****************************************************************************************************/
          init: {
              language_url: '/tinymce/langs/zh_CN.js',
              language: 'zh_CN',
              skin_url: '/tinymce/skins/lightgray',
              height: 300,
              plugins: this.plugins,
              toolbar: this.toolbar,
              branding: false,
              menubar: false,
              images_upload_handler: (blobInfo, success) => {
                  const img = 'data:image/jpeg;base64,' + blobInfo.base64()
                  success(img)
              }
          },
          myValue:this.value,
      /****************************************************************************************************/
        title:"操作",
        visible: false,
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
          add: "/text/text/add",
          edit: "/text/text/edit",
        },
      }
    },
    /********************************************************************************/
      mounted() {
          tinymce.init({})
      },
    /********************************************************************************/

    created () {
    },
    methods: {
    /*******************************************************************************/
        onClick(e) {
            this.$emit('onClick', e, tinymce)
        },
        //可以添加一些自己的自定义事件，如清空内容
        clear() {
            this.myValue = ''
        },
    /*******************************************************************************/


      add () {
        this.edit({});
        this.clear();
      },
      edit (record) {
        // console.log('777'+this.value),//this.value为undefined
        // console.log('777'+record.content),
        this.form.resetFields();
        this.model = Object.assign({}, record);
        console.log(this.model);//this.model中有值
        this.myValue=this.model.content;
        this.visible = true;
        this.$nextTick(() => {
        this.form.setFieldsValue(pick(this.model,'title','content','type'))

		  //时间格式化
        });

      },
      close () {
        this.$emit('close');
        this.visible = false;
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
            //时间格式化
             formData.content=this.myValue;
            // console.log(formData.content);
            console.log(formData)
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


    },
    /*************************************************************************************************/
      watch: {
          value(newValue) {
              this.myValue = (newValue == null ? '' : newValue)
          },
          myValue(newValue) {
              console.log(newValue)
              if(this.triggerChange){
                  console.log(1)
                  this.$emit('change', newValue)
              }else{
                  console.log(2)
                  this.$emit('input', newValue)
              }
          }
      }
    /*************************************************************************************************/
  }
</script>

<style lang="less" scoped>

</style>