<template>
  <div id="newslist">
    <v-header></v-header>
    <v-consistency></v-consistency>
  </div>
</template>

<script>
  import header from '@/components/firstpage/header.vue'
  import consistency from '@/components/consistency/consistency.vue'
  export default {
    name: 'consistencywork',
    data(){
      return {
      }
    },
    methods: {
    },
    computed: {
    },
    components: {
      'v-header': header,
      'v-consistency': consistency,
    }

  }
</script>

<style lang="stylus" rel="stylesheet/stylus">
  .consult
    position fixed
    right 15px
    bottom 30px
    width 140px
    line-height 56px
    height 56px
    text-align center
    background #00aef3
    cursor pointer
    .text
      font-size 26px
      font-weight bolder
      color #ffffff

  .message
    position fixed
    bottom 50%
    left 50%
    margin-bottom -175px
    margin-left -120px
    width 240px

    background #cccccc
    z-index 100
    .title
      height 35px
      line-height 35px
      background #00aef3
      .text
        color #ffffff
        margin-left 10px
      .glyphicon
        color #ffffff
        float right
        line-height 35px
        margin-right 15px
    .message-content
      width 220px
      margin 10px 0 5px 10px
    .name, .telephone, .email, .adress
      margin 10px 10px
      width 220px
      background #ffffff
      font-size 14px
      height 22px
      line-height 22px
      .glyphicon
        margin 0 5px
      input
        width 150px
        height 20px
    .btn
      margin-left 10px
      margin-bottom 10px
</style>
