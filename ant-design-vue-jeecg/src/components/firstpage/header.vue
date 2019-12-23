<template>
  <div class="header-wrapper">
    <div class="header">
      <div class="avatar">
        <img src="./bg-logo.png">
      </div>
      <nav class="topbar">
        <ul>
          <li class="nav-item" v-for="item in navs">
            <a class="nav-name" :href="item.sourceUrl">{{item.name}}</a>
          </li>
          <li class="nav-item">
            <router-link :to="{ path: '/user/login' }">登录</router-link>
          </li>
        </ul>
      </nav>

    </div>
  </div>
</template>

<script type="text/ecmascript-6">
  export default{
    data(){
      return {
        navs: [
          {name: '首页', sourceUrl: 'http://www.mchz.com.cn/meichuang/html/data_security_center/index.html'},
          {name: '行业动态', sourceUrl: 'http://www.mchz.com.cn/meichuang/html/backup_security_center/index.html'},
          {name: '一致性工作', sourceUrl: 'http://www.mchz.com.cn/meichuang/html/big_data_center/index.html'},
          {name: '文件和工具', sourceUrl: 'http://www.mchz.com.cn/meichuang/html/sre_center/index.html'},
          {name: '了解更多', sourceUrl: 'http://www.mchz.com.cn/meichuang/html/about_us/about.html'},
        ],
        moreInformation:true,
        nomoreInformation:false

      }
    },
    methods:{
      toggleIcon(){
        this.moreInformation=!this.moreInformation;
        this.nomoreInformation=!this.nomoreInformation
      }
    }
  }
</script>

<style lang="stylus" rel="stylesheet/stylus">
  .header-wrapper
    position fixed
    left 0
    top 0
    width 100%
    height 68px
    background #f8f8f8
    line-height 68px
    z-index 10
    .header
      position relative
      width 1200px
      margin 0 auto
      .avatar
        display inline-block
        margin-right 80px
        cursor pointer
      .topbar
        display inline-block
        font-size 0
        .nav-item
          display inline-block
          width 100px
          height 68px
          text-align center
          margin-right 50px
          font-size 20px
          cursor pointer
          &:hover
            color #0f0f0f
          .nav-name
            text-decoration none

      .more
        display inline-block
        float right
        font-size 20px
        cursor pointer
        .img
          vertical-align -2px
      .moreInformation
        width 240px
        height 468px
        position absolute
        top 68px
        right -80px
        background #f1f1f1
        overflow hidden
        &.fade-enter-active, &.fade-leave-active
          transition all 0.5s ease-in
        &.fade-enter, &.fade-leave-active
          height 0
        .list-item
          padding 10px 50px
          border-bottom solid black 1px
          &:last-child
            border-bottom none
          .title
            font-size 20px
            line-height 20px
            height 20px
            margin-top 10px
            cursor pointer
          dl
            padding-left 20px
            dd
              margin-bottom 15px
              cursor pointer

</style>
