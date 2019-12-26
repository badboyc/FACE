<template>
  <div class="content">
    <div class="title">规范/工具</div>
    <div class="title">相关文档和工具</div>
    <a-card :bordered="false">
      <a-list itemLayout="horizontal" :dataSource="dataSource" :pagination="pagination">
        <a-list-item slot="renderItem" slot-scope="item, index">
          <a-list-item-meta
            :description=item.createTime
          >
            <router-link :to=" '/Docandtoolscontent/'+item.id " slot="title" >{{item.docName}}</router-link>
          </a-list-item-meta>
        </a-list-item>
      </a-list>
      <!-- table区域-end -->

      <!-- 表单区域 -->
    </a-card>
  </div>
</template>

<script>
  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import { filterObj } from '@/utils/util';
  import { deleteAction, getAction,downFile } from '@/api/manage'
  import { ACCESS_TOKEN } from "@/store/mutation-types"
  import Vue from 'vue'
  export default {
    name: "docandtools",
    mixins:[JeecgListMixin],
    components: {
    },
    data () {
      return {
        tokenHeader: {'X-Access-Token': Vue.ls.get(ACCESS_TOKEN)},
        /* 查询条件-请不要在queryParam中声明非字符串值的属性 */
        queryParam: {},
        /* 数据源 */
        dataSource: [],
        isorter:{
          column: 'createTime',
          order: 'desc',
        },
        superQueryFlag:false,
        /* 高级查询条件 */
        superQueryParams:"",
        filters: {},
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
        /* 分页参数 */
        url: {
          list: "/doc/doc/list",

        },
        pagination:{
          pageSize: 20,

        },
      }
    },
    created() {
      this.loadData();
    },
    methods:{
      loadData(arg) {
        if(!this.url.list){
          this.$message.error("请设置url.list属性!")
          return
        }
        //加载数据 若传入参数1则加载第一页的内容
        if (arg === 1) {
          this.ipagination.current = 1;
        }
        var params = this.getQueryParams();//查询条件
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
      getQueryParams() {
        //获取查询条件
        let sqp = {}
        if(this.superQueryParams){
          sqp['superQueryParams']=encodeURI(this.superQueryParams)
        }
        var param = Object.assign(sqp, this.queryParam, this.isorter ,this.filters);
        param.field = this.getQueryField();
        param.pageNo = this.ipagination.current;
        param.pageSize = this.ipagination.pageSize;
        return filterObj(param);
      },
      getQueryField() {
        //TODO 字段权限控制
        var str = "id,";
        this.columns.forEach(function (value) {
          str += "," + value.dataIndex;
        });
        return str;
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
    .more
      text-align right
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
