<template>
  <div class="content">
    <div class="title">行业动态</div>
  <a-card :bordered="false">
      <a-list itemLayout="horizontal" :dataSource="dataSource" :pagination="ipagination">
        <a-list-item slot="renderItem" slot-scope="item, index">
          <a-list-item-meta
            :description=item.createTime
          >
            <a slot="title" @click="handleDetail(item)" >{{item.title}}</a>
          </a-list-item-meta>
        </a-list-item>
      </a-list>
    <!-- table区域-end -->

    <!-- 表单区域 -->
    <newsdetail ref="modalForm2" @ok="modalFormOk"></newsdetail>
  </a-card>
  </div>
</template>

<script>
  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import newsdetail from '@/components/firstpage/newsdetail.vue'
  export default {
    name: "TextList",
    mixins:[JeecgListMixin],
    components: {
      newsdetail
    },
    data () {
      return {
        description: '文本编辑管理页面',
        // 表头
        columns: [
          {
            title: '#',
            dataIndex: '',
            key:'rowIndex',
            width:60,
            align:"center",
            customRender:function (t,r,index) {
              return parseInt(index)+1;
            }
          },
          {
            title: '标题',
            align:"center",
            dataIndex: 'title'
          },
          {
            title: '发布人',
            align:"center",
            dataIndex: 'createBy'
          },
          {
            title: '发布时间',
            align:"center",
            dataIndex: 'createTime'
          },
          {
            title: '文本类型',
            align:"center",
            dataIndex: 'type'
          },
          {
            title: '操作',
            dataIndex: 'action',
            align:"center",
            scopedSlots: { customRender: 'action' },
          }
        ],
        url: {
          list: "/text/text/list",
          delete: "/text/text/delete",
          deleteBatch: "/text/text/deleteBatch",
          exportXlsUrl: "text/text/exportXls",
          importExcelUrl: "text/text/importExcel",
        },
      }
    },
    computed: {
    },
    methods: {
      handleDetail: function (item) {
        this.$refs.modalForm2.detail(item);
      },

    }
  }
</script>
<style scoped>
  @import '~@assets/less/common.less'
</style>
<style lang="stylus" rel="stylesheet/stylus">
  .content
    width 1200px
    margin 0 auto
    .title
      width 100%
      height 86px
      line-height 86px
      font-size 36px
      font-weight bold
      text-align center
    .list-content
      display flex
      .list-item
        width 289px
        flex 1
        .border
          padding 2px 1px
          width 290px
          height 420px
          border solid 1px #cccccc
          .img
            margin-bottom 20px
            cursor pointer
          .name
            padding-left 15px
            margin-bottom 15px
            font-size 16px
            color #3366cc
            cursor pointer
          .text
            padding 0 15px
            font-size 14px
            line-height 20px
            cursor pointer
</style>
