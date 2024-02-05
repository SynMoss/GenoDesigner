<template>
  <el-breadcrumb class="app-breadcrumb" separator="/">
    <transition-group name="breadcrumb">
      <el-breadcrumb-item key="a">
        <span class="no-redirect" >{{language==='zh'?zh_list.system:'Genome Design'}}</span>
      </el-breadcrumb-item>
      <el-breadcrumb-item v-for="(item,index) in levelList" :key="item.path">
        <span v-if="item.redirect === 'noRedirect' || index == levelList.length - 1" class="no-redirect">{{ calTitle(item) }}</span>
        <a v-else @click.prevent="handleLink(item)">{{ item.meta.title }}</a>
      </el-breadcrumb-item>
    </transition-group>
  </el-breadcrumb>
</template>

<script>
import { zh_list } from "@/data/constant";
export default {
  data() {
    return {
      zh_list,
      levelList: null,
      language: process.env.VUE_APP_LANGUAGE
    }
  },
  watch: {
    $route(route) {
            if (route.path.startsWith('/redirect/')) {
        return
      }
      this.getBreadcrumb()
    }
  },
  created() {
    this.getBreadcrumb()
  },
  computed:{
  },
  methods: {
    calTitle(item){
      if(this.language==='en'){
        if(item.name==='ProgrammeList'){
          return "Design Tool"
        }
        if(item.name==='Part'){
          return "Biological Parts Library"
        }
        if(item.name==="Programmer"){
          return "Design Details"
        }
      }
      return item.meta.title
    },
    getBreadcrumb() {
            let matched = this.$route.matched.filter(item => item.meta && item.meta.title)
      const first = matched[0]


      this.levelList = matched.filter(item => item.meta && item.meta.title && item.meta.breadcrumb !== false)
    },
    isDashboard(route) {
      const name = route && route.name
      if (!name) {
        return false
      }
      return name.trim() === 'Index'
    },
    handleLink(item) {
      const { redirect, path } = item
      if (redirect) {
        this.$router.push(redirect)
        return
      }
      this.$router.push(path)
    }
  }
}
</script>

<style lang="scss" scoped>
.app-breadcrumb.el-breadcrumb {
  display: inline-block;
  font-size: 14px;
  line-height: 50px;
  margin-left: 8px;

  .no-redirect {
    color: #97a8be;
    cursor: text;
  }
}
</style>
