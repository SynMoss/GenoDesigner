<template>
  <el-drawer size="280px" :visible="visible" :with-header="false" :append-to-body="true" :show-close="false">
    <div class="drawer-container">
      <div>
        <div class="setting-drawer-content">
                      
                   </div>

        <el-divider/>

        <h3 class="drawer-title">Setting</h3>
      
        <div class="drawer-item">
          <span> TopNav</span>
          <el-switch v-model="topNav" class="drawer-switch" />
        </div>

        <div class="drawer-item">
          <span> Tags-Views</span>
          <el-switch v-model="tagsView" class="drawer-switch" />
        </div>

        <div class="drawer-item">
          <span> Header</span>
          <el-switch v-model="fixedHeader" class="drawer-switch" />
        </div>

        <div class="drawer-item">
          <span> Logo</span>
          <el-switch v-model="sidebarLogo" class="drawer-switch" />
        </div>

        <div class="drawer-item">
          <span>title</span>
          <el-switch v-model="dynamicTitle" class="drawer-switch" />
        </div>

        <el-divider/>

        <el-button size="small" type="primary" plain icon="el-icon-document-add" @click="saveSetting">save</el-button>
        <el-button size="small" plain icon="el-icon-refresh" @click="resetSetting">reset</el-button>
      </div>
    </div>
  </el-drawer>
</template>

<script>
import ThemePicker from '@/components/ThemePicker'

export default {
  components: { ThemePicker },
  data() {
    return {
      theme: this.$store.state.settings.theme,
      sideTheme: this.$store.state.settings.sideTheme
    };
  },
  computed: {
    visible: {
      get() {
        return this.$store.state.settings.showSettings
      }
    },
    fixedHeader: {
      get() {
        return this.$store.state.settings.fixedHeader
      },
      set(val) {
        this.$store.dispatch('settings/changeSetting', {
          key: 'fixedHeader',
          value: val
        })
      }
    },
    topNav: {
      get() {
        return this.$store.state.settings.topNav
      },
      set(val) {
        this.$store.dispatch('settings/changeSetting', {
          key: 'topNav',
          value: val
        })
        if (!val) {
          this.$store.dispatch('app/toggleSideBarHide', false);
          this.$store.commit("SET_SIDEBAR_ROUTERS", this.$store.state.permission.defaultRoutes);
        }
      }
    },
    tagsView: {
      get() {
        return this.$store.state.settings.tagsView
      },
      set(val) {
        this.$store.dispatch('settings/changeSetting', {
          key: 'tagsView',
          value: val
        })
      }
    },
    sidebarLogo: {
      get() {
        return this.$store.state.settings.sidebarLogo
      },
      set(val) {
        this.$store.dispatch('settings/changeSetting', {
          key: 'sidebarLogo',
          value: val
        })
      }
    },
    dynamicTitle: {
      get() {
        return this.$store.state.settings.dynamicTitle
      },
      set(val) {
        this.$store.dispatch('settings/changeSetting', {
          key: 'dynamicTitle',
          value: val
        })
      }
    },
  },
  methods: {
    themeChange(val) {
      this.$store.dispatch('settings/changeSetting', {
        key: 'theme',
        value: val
      })
      this.theme = val;
    },
    handleTheme(val) {
      this.$store.dispatch('settings/changeSetting', {
        key: 'sideTheme',
        value: val
      })
      this.sideTheme = val;
    },
    saveSetting() {
      this.$modal.loading("loading");
      this.$cache.local.set(
        "layout-setting",
        `{
            "topNav":${this.topNav},
            "tagsView":${this.tagsView},
            "fixedHeader":${this.fixedHeader},
            "sidebarLogo":${this.sidebarLogo},
            "dynamicTitle":${this.dynamicTitle},
            "sideTheme":"${this.sideTheme}",
            "theme":"${this.theme}"
          }`
      );
      setTimeout(this.$modal.closeLoading(), 1000)
    },
    resetSetting() {
      this.$modal.loading("loading");
      this.$cache.local.remove("layout-setting")
      setTimeout("window.location.reload()", 1000)
    }
  }
}
</script>

<style lang="scss" scoped>
  .setting-drawer-content {
    .setting-drawer-title {
      margin-bottom: 12px;
      color: rgba(0, 0, 0, .85);
      font-size: 14px;
      line-height: 22px;
      font-weight: bold;
    }

    .setting-drawer-block-checbox {
      display: flex;
      justify-content: flex-start;
      align-items: center;
      margin-top: 10px;
      margin-bottom: 20px;

      .setting-drawer-block-checbox-item {
        position: relative;
        margin-right: 16px;
        border-radius: 2px;
        cursor: pointer;

        img {
          width: 48px;
          height: 48px;
        }

        .setting-drawer-block-checbox-selectIcon {
          position: absolute;
          top: 0;
          right: 0;
          width: 100%;
          height: 100%;
          padding-top: 15px;
          padding-left: 24px;
          color: #1890ff;
          font-weight: 700;
          font-size: 14px;
        }
      }
    }
  }

  .drawer-container {
    padding: 20px;
    font-size: 14px;
    line-height: 1.5;
    word-wrap: break-word;

    .drawer-title {
      margin-bottom: 12px;
      color: rgba(0, 0, 0, .85);
      font-size: 14px;
      line-height: 22px;
    }

    .drawer-item {
      color: rgba(0, 0, 0, .65);
      font-size: 14px;
      padding: 12px 0;
    }

    .drawer-switch {
      float: right
    }
  }
</style>
