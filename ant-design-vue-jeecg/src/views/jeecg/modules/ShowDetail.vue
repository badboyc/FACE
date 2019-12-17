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
          :description="'发布人：'+ record.createBy + ' ' + ' 发布时间：' + record.createTime">
        </a-card-meta>
        <a-divider />
        <detail-list>
          <detail-list-item term="名称">
            <span v-html="record.docName" class="article-content"></span>
          </detail-list-item>
          <detail-list-item term="标准书号">
            <span v-html="record.isbn" class="article-content"></span>
          </detail-list-item>
          <detail-list-item term="类型">
            <span v-html="record.type" class="article-content"></span>
          </detail-list-item>
          <detail-list-item term="版本">
            <span v-html="record.version" class="article-content"></span>
          </detail-list-item>
          <detail-list-item term="引用">
            <span v-html="record.reference" class="article-content"></span>
          </detail-list-item>
        </detail-list>
        <detail-list :col="1">
          <detail-list-item term="描述">
            <span v-html="record.description" class="article-content"></span>
          </detail-list-item>
          <detail-list-item term="点击下载">
            <a href="javascript:;" @click="handleDownload(record)">{{getFilename(record)}}</a>
          </detail-list-item>
        </detail-list>
      </a-card>
    </a-modal>
</template>

<script>
    import DetailList from '@/components/tools/DetailList'
    import { ACCESS_TOKEN } from "@/store/mutation-types"
    import Vue from 'vue'
    import { axios } from '@/utils/request'
    const DetailListItem = DetailList.Item
    export default {
      name: "ShowDetail",
      components: {
        DetailList,
        DetailListItem
      },
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
                    height:(window.innerHeight*0.8)+"px",
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
                    this.modelStyle.style.top = '20px'
                } else {
                    this.modelStyle.width = '60%'
                    this.modelStyle.style.top = '50px'
                }
                this.modelStyle.fullScreen = mode
            },
            detail (record) {
                this.visible = true;
                this.record = record;
            },

            handleCancel(e) {
                this.visible = false
            },
          handleDownload: function (value) {
            var btn = document.createElement("a");
            // btn.setAttribute('download', filename);// download属性
            btn.setAttribute('href', value.downloadpath);// href链接
            btn.click();//自执行点击事件
          },
            getFilename(record){
                let url = record.downloadpath;
                let urlArr1 = url.split('?');
                let urlArr2 = urlArr1[0].split('/');
                let filename = urlArr2[urlArr2.length-1];
                return filename;
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