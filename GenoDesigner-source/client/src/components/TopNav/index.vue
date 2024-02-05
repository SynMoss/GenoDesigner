<template>
  <el-menu
    :default-active="activeMenu"
    mode="horizontal"
    @select="handleSelect"
  >
    <template v-for="(item, index) in topMenus">
      <el-menu-item :style="{'--theme': theme}" :index="item.path" :key="index" v-if="index < visibleNumber"
        ><svg-icon :icon-class="item.meta.icon" />
        {{ item.meta.title }}</el-menu-item
      >
    </template>

         <el-submenu :style="{'--theme': theme}" index="more" v-if="topMenus.length > visibleNumber">
      <template slot="title">More menus</template>
      <template v-for="(item, index) in topMenus">
        <el-menu-item
          :index="item.path"
          :key="index"
          v-if="index >= visibleNumber"
          ><svg-icon :icon-class="item.meta.icon" />
          {{ item.meta.title }}</el-menu-item
        >
      </template>
    </el-submenu>
  </el-menu>
</template>

<script>
import { constantRoutes } from "@/router";

const hideList = ['/index', '/user/profile'];

export default {
  data() {
    return {
      visibleNumber: 5,
      currentIndex: undefined
    };
  },
  computed: {
    theme() {
      return this.$store.state.settings.theme;
    },
    topMenus() {
      let topMenus = [];
      this.routers.map((menu) => {
        if (menu.hidden !== true) {
          if (menu.path === "/") {
              topMenus.push(menu.children[0]);
          } else {
              topMenus.push(menu);
          }
        }
      });
      return topMenus;
    },
    routers() {
      return this.$store.state.permission.topbarRouters;
    },
    childrenMenus() {
      var childrenMenus = [];
      this.routers.map((router) => {
        for (var item in router.children) {
          if (router.children[item].parentPath === undefined) {
            if(router.path === "/") {
              router.children[item].path = "/" + router.children[item].path;
            } else {
              if(!this.ishttp(router.children[item].path)) {
                router.children[item].path = router.path + "/" + router.children[item].path;
              }
            }
            router.children[item].parentPath = router.path;
          }
          childrenMenus.push(router.children[item]);
        }
      });
      return constantRoutes.concat(childrenMenus);
    },
    activeMenu() {
      const path = this.$route.path;
      let activePath = path;
      if (path !== undefined && path.lastIndexOf("/") > 0 && hideList.indexOf(path) === -1) {
        const tmpPath = path.substring(1, path.length);
        activePath = "/" + tmpPath.substring(0, tmpPath.indexOf("/"));
        this.$store.dispatch('app/toggleSideBarHide', false);
      } else if(!this.$route.children) {
        activePath = path;
        this.$store.dispatch('app/toggleSideBarHide', true);
      }
      this.activeRoutes(activePath);
      return activePath;
    },
  },
  beforeMount() {
    window.addEventListener('resize', this.setVisibleNumber)
  },
  beforeDestroy() {
    window.removeEventListener('resize', this.setVisibleNumber)
  },
  mounted() {
    this.setVisibleNumber();
  },
  methods: {
    setVisibleNumber() {
      const width = document.body.getBoundingClientRect().width / 3;
      this.visibleNumber = parseInt(width / 85);
    },
    handleSelect(key, keyPath) {
      this.currentIndex = key;
      const route = this.routers.find(item => item.path === key);
      if (this.ishttp(key)) {
        window.open(key, "_blank");
      } else if (!route || !route.children) {
        this.$router.push({ path: key });
        this.$store.dispatch('app/toggleSideBarHide', true);
      } else {
        this.activeRoutes(key);
        this.$store.dispatch('app/toggleSideBarHide', false);
      }
    },
    activeRoutes(key) {
      var routes = [];
      if (this.childrenMenus && this.childrenMenus.length > 0) {
        this.childrenMenus.map((item) => {
          if (key == item.parentPath || (key == "index" && "" == item.path)) {
            routes.push(item);
          }
        });
      }
      if(routes.length > 0) {
        this.$store.commit("SET_SIDEBAR_ROUTERS", routes);
      }
    },
    ishttp(url) {
      return url.indexOf('http://') !== -1 || url.indexOf('https://') !== -1
    }
  },
};
</script>

<style lang="scss">
.topmenu-container.el-menu--horizontal > .el-menu-item {
  float: left;
  height: 50px !important;
  line-height: 50px !important;
  color: #999093 !important;
  padding: 0 5px !important;
  margin: 0 10px !important;
}

.topmenu-container.el-menu--horizontal > .el-menu-item.is-active, .el-menu--horizontal > .el-submenu.is-active .el-submenu__title {
  border-bottom: 2px solid #{'var(--theme)'} !important;
  color: #303133;
}

/* submenu item */
.topmenu-container.el-menu--horizontal > .el-submenu .el-submenu__title {
  float: left;
  height: 50px !important;
  line-height: 50px !important;
  color: #999093 !important;
  padding: 0 5px !important;
  margin: 0 10px !important;
}
</style>
