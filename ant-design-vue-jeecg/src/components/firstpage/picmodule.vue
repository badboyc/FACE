<template>
  <div id="carousel-example-generic" class="carousel slide" data-ride="carousel">
    <!-- Indicators -->
    <ol class="carousel-indicators">
      <li data-target="#carousel-example-generic" data-slide-to="0" class="active"></li>
      <li data-target="#carousel-example-generic" data-slide-to="1"></li>
      <li data-target="#carousel-example-generic" data-slide-to="2"></li>
      <li data-target="#carousel-example-generic" data-slide-to="3"></li>
    </ol>

    <!-- Wrapper for slides -->
    <div class="carousel-inner" role="listbox">
      <div class="item active">
        <img :src=picture1 alt="First-side">
      </div>
      <div class="item">
        <img :src=picture2 alt="Second-side">
      </div>
      <div class="item">
        <img :src=picture3 alt="Three-side">
      </div>
      <div class="item">
        <img :src=picture4 alt="Four-side">
      </div>
    </div>

    <!-- Controls -->
    <a class="left carousel-control" href="#carousel-example-generic" role="button" data-slide="prev">
      <span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
      <span class="sr-only">Previous</span>
    </a>
    <a class="right carousel-control" href="#carousel-example-generic" role="button" data-slide="next">
      <span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
      <span class="sr-only">Next</span>
    </a>
  </div>

</template>

<script type="text/ecmascript-6">
  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import { filterObj } from '@/utils/util';
  import { deleteAction, getAction,downFile } from '@/api/manage'
  import { ACCESS_TOKEN } from "@/store/mutation-types"
  import Vue from 'vue'
  export default{
    name:"picturemodule",
    mixins:[JeecgListMixin],
    components: {
    },
    data () {
      return {
        queryParam: {},
        tokenHeader: {'X-Access-Token': Vue.ls.get(ACCESS_TOKEN)},
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
        picture1:'',
        picture2:'',
        picture3:'',
        picture4:'',
        columns: [
          {
            title: '图片编号',
            dataIndex: '',
            key:'rowIndex',
            width:60,
            align:"center",
            customRender:function (t,r,index) {
              return parseInt(index)+1;
            }
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
            title: '图片路径',
            align:"center",
            dataIndex: 'pictureurl'
          },

          {
            title: '操作',
            dataIndex: 'action',
            align:"center",
            scopedSlots: { customRender: 'action' },
          }
        ],
        url: {
          list: "/picture/picture/list",
        },
      }
    },
    created() {
      this.loadData();
      console.log("这是数据");
      console.log(this.dataSource)
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
            this.picture1=res.result.records[0].pictureurl;
            this.picture2=res.result.records[1].pictureurl;
            this.picture3=res.result.records[2].pictureurl;
            this.picture4=res.result.records[3].pictureurl;
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

<style lang="stylus" rel="stylesheet/stylus">
  #carousel-example-generic
    margin-top 68px
    z-index 1
</style>
