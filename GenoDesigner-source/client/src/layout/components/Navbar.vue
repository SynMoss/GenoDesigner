<template>
  <div class="navbar">
    <div>

    <breadcrumb id="breadcrumb-container" class="breadcrumb-container" v-if="!topNav"/>
    <top-nav id="topmenu-container" class="topmenu-container" v-if="topNav"/>
    </div>
    <div class="right-menu">
                    <div class="navbar-svg">
        <el-badge :hidden="!unReadCount" :value="unReadCount" :max="99" >
        <svg-icon icon-class="messageA" @click="showNotice"/>
        </el-badge>
      </div>
      <el-dropdown @command="handleCommand" v-if="needLogin" class="avatar-container right-menu-item hover-effect" trigger="click">
        <div class="avatar-wrapper">
          <!-- <img :src="avatar" class="user-avatar "> -->
          <i class="el-icon-arrow-down"></i>
        </div>
        <el-dropdown-menu slot="dropdown">
                                           <el-dropdown-item divided @click.native="logout">
            <span>{{language==='zh'?zh_list.logout:'Login out'}}</span>
          </el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>
    </div>
    <notice-list-dialog @cancel="loadMsg" ref="noticeListDialog"></notice-list-dialog>
  </div>
</template>

<script>
import { zh_list } from "@/data/constant";
import { mapGetters } from 'vuex'
import Breadcrumb from '@/components/Breadcrumb'
import TopNav from '@/components/TopNav'
import Hamburger from '@/components/Hamburger'
import Screenfull from '@/components/Screenfull'
import SizeSelect from '@/components/SizeSelect'
import Search from '@/components/HeaderSearch'
import { getAction } from "@/api/manage";
import { STATIC_MANUAL } from "@/data/constant";
import NoticeListDialog from "../../views/system/notice/modules/NoticeListDialog";
import {NO_LOGIN_ROLE} from "@/data/constant"
export default {
  components: {
    Breadcrumb,
    TopNav,
    Hamburger,
    Screenfull,
    SizeSelect,
    Search,
    NoticeListDialog
  },
  computed: {
    ...mapGetters([
      'sidebar',
      'avatar',
      'device',
      'nickName',
    ]),
    setting: {
      get() {
        return this.$store.state.settings.showSettings
      },
      set(val) {
        this.$store.dispatch('settings/changeSetting', {
          key: 'showSettings',
          value: val
        })
      }
    },
    topNav: {
      get() {
        return this.$store.state.settings.topNav
      }
    }
  },
  data(){
    return{
      zh_list,
      needLogin: process.env.VUE_APP_LOGIN==='t',
      unReadCount: 0,
      language: process.env.VUE_APP_LANGUAGE
    }
  },
  created(){
    this.loadMsg()
  },
  methods: {
    handleCommand(command){
      if (command === "introStart") {
        this.introStart()
      }else if(command === "toManual"){
        this.toManual()
      }
    },
    toManual() {
      let url = STATIC_MANUAL
      window.open(url, "_blank")
    },
    //新手引导
    introStart(){
      this.$intro()
      .setOption("nextLabel", this.language==='zh'?this.zh_list.nextstep:'next step')
      .setOption("prevLabel", this.language==='zh'?this.zh_list.nextstep:'last step')
      .setOption("doneLabel", this.language==='zh'?this.zh_list.accomplish:'OK')
      .start();
    },
    loadMsg(){
      getAction('/system/notice/list/self',{isRead: 0}).then(res=>{
        this.unReadCount = res.total
      })
    },
    showNotice(){
      this.$refs.noticeListDialog.show()
    },
    toNewTab(){
      window.open('https://wenjuan.feishu.cn/m?t=sxIBleBlK0Gi-loyj')
    },
    toggleSideBar() {
      this.$store.dispatch('app/toggleSideBar')
    },
    async logout() {

      let role=this.$store.state.user.roles.includes(NO_LOGIN_ROLE)

      this.$store.dispatch('LogOut').then(() => {
        this.$router.push({path:'/login'})
      })


      // if(role){
      //   this.$store.dispatch('LogOut').then(() => {
      //   this.$router.push({path:'/login'})
      //   })
      // }else{
      //   this.$store.dispatch('LogOut').then(() => {
      //   this.$router.push({path:'/login2'})
      //   })
      // }

    }
  }
}
</script>
<style lang="scss">
.navbar-svg{
      font-size: 20px;
      color:#A3A4B2;
      padding-right: 18px;
      cursor: pointer;
      .el-badge__content.is-fixed{
        top:10px;
      }
    }
</style>
<style lang="scss" scoped>
.navbar {
  height: 50px;
  overflow: hidden;
  position: relative;
  background: #fff;
  box-shadow: 0 1px 4px rgba(0,21,41,.08);
  display: flex;
  justify-content: space-between;
  .hamburger-container {
    line-height: 46px;
    height: 100%;
    float: left;
    cursor: pointer;
    transition: background .3s;
    -webkit-tap-highlight-color:transparent;

    &:hover {
      background: rgba(0, 0, 0, .025)
    }
  }

  .breadcrumb-container {
    float: left;
  }

  .topmenu-container {
    position: absolute;
    left: 50px;
  }

  .errLog-container {
    display: inline-block;
    vertical-align: top;
  }

  .right-menu {
    height: 100%;
    line-height: 50px;
    display: flex;
    align-items: center;
    &:focus {
      outline: none;
    }

    .right-menu-item {
      display: inline-block;
      padding: 0 8px;
      height: 100%;
      font-size: 18px;
      color: #5a5e66;

      &.hover-effect {
        cursor: pointer;
        transition: background .3s;

        &:hover {
          background: rgba(0, 0, 0, .025)
        }
      }
    }
    .right-menu-avatar{
      display: flex;
      align-items: center;
      height: 100%;
      margin-right: 20px;
      img{
        width: 24px;
        height: 24px;
        border-radius: 50%;
        margin-right:10px;
      }
      span{
        font-size: 14px;
        font-weight: 500;
        color: #666666;
        line-height: 16px;
        margin-right:10px;
      }
      i{
        cursor: pointer;
      }
    }
    .avatar-container {
      margin-right: 30px;

      .avatar-wrapper {
        margin-top: 5px;
        position: relative;

        .user-avatar {
          cursor: pointer;
          width: 30px;
          height: 30px;
          border-radius: 10px;
          margin-top:5px;
        }

        .el-icon-caret-bottom {
          cursor: pointer;
          position: absolute;
          right: -20px;
          top: 25px;
          font-size: 12px;
        }
      }
    }
  }
}
</style>
