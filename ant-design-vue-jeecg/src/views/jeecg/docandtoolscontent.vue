<template>
  <div class="content">
    <div v-for="(item,index) in dataSource" :key="index">
      <div class="title">{{item.docName}}</div>
      <div class="time">
        发布人：{{item.createBy}}     发布时间：{{item.createTime}}
      </div>
      标准书号:<div v-html="item.isbn" class="article-content"></div><br>
      类型:<div v-html="item.type" class="article-content"></div><br>
      版本:<div v-html="item.version" class="article-content"></div><br>
      引用:<div v-html="item.reference" class="article-content"></div><br>
      简介：<div v-html="item.description" class="article-content"></div><br>
      <router-link to="/DocList/" >点击进入下载页面</router-link>
    </div>
  </div>
</template>

<script>
  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import { filterObj } from '@/utils/util';
  import { deleteAction, getAction,downFile } from '@/api/manage'
  import Vue from 'vue'
  import { ACCESS_TOKEN } from "@/store/mutation-types"
  import DetailList from '@/components/tools/DetailList'
  const DetailListItem = DetailList.Item
  export default {
    name: "docandtoolscontent",
    mixins:[JeecgListMixin],
    components: {
      DetailList,
      DetailListItem
    },
    data () {
      return {
        description: '文本编辑管理页面',
        tokenHeader: {'X-Access-Token': Vue.ls.get(ACCESS_TOKEN)},
        /* 查询条件-请不要在queryParam中声明非字符串值的属性 */
        queryParam: {},
        /* 数据源 */
        dataSource: [],
        /* 分页参数 */
        ipagination: {
          current: 1,
          pageSize: 10,
          pageSizeOptions: ['10', '20', '30'],
          showTotal: (total, range) => {
            return range[0] + "-" + range[1] + " 共" + total + "条"
          },
          // 表头

        } ,
        url: {
          list: `/doc/doc/list?id=${this.$route.params.aid}`,
        },
      }
    },
    created() {
      this.loadData();
      console.log("这是接收到的数据");
      console.log(this.dataSource);
      //初始化字典配置 在自己页面定义
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
        var params = {};//查询条件
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
    .time
      text-align center
      font-size 16px
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
