<template>
  <div class="sidebar-logo-container" :class="{'collapse':collapse}" :style="{ backgroundColor: sideTheme === 'theme-dark' ? variables.menuBackground : variables.menuLightBackground }">
    <transition name="sidebarLogoFade">
      <router-link v-if="collapse" key="collapse" class="sidebar-logo-link" to="/">
        <img v-if="logo" :src="logo" class="sidebar-logo" />
        <h1 v-else class="sidebar-title" :style="{ color: sideTheme === 'theme-dark' ? variables.logoTitleColor : variables.logoLightTitleColor }">{{ title }} </h1>

      </router-link>
      <router-link v-else key="expand" class="sidebar-logo-link" to="/">
        <!-- <img v-if="logo" :src="logo" class="sidebar-logo" /> -->
        <h1 class="sidebar-title" :style="{ color: sideTheme === 'theme-dark' ? variables.logoTitleColor : variables.logoLightTitleColor }">{{ title }} </h1>
      </router-link>
    </transition>
  </div>
   </template>

<script>
import logoImg from '@/assets/logo/logo.png'
import variables from '@/assets/styles/variables.scss'
import Hamburger from '@/components/Hamburger'
import { mapGetters } from 'vuex'
export default {
  name: 'SidebarLogo',
  components: {
    Hamburger
  },
  props: {
    collapse: {
      type: Boolean,
      required: true
    }
  },
  computed: {
    ...mapGetters(['sidebar']),
    isCollapse() {
            return !this.sidebar.opened;
        },
    variables() {
      return variables
    },
    sideTheme() {
      return this.$store.state.settings.sideTheme
    }
  },
  data() {
    return {
      title: 'GenoDesigner',
      logo: logoImg
    }
  },
  methods: {
    toggleSideBar() {
      this.$store.dispatch('app/toggleSideBar')
    }
  }
}
</script>

<style lang="scss" scoped>
.sidebarLogoFade-enter-active {
  transition: opacity 1.5s;
}

.sidebarLogoFade-enter,
.sidebarLogoFade-leave-to {
  opacity: 0;
}
.sidebar-logo-flex{
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 30px 18px;
  margin-bottom: 10px;
  .sidebar-logo-flex-left{
    width: 32px;
    height: 32px;

  }
  .sidebar-logo-flex-title{
    color: #fff;
    font-size: 24px;
  }
  border-bottom: 1px solid #fff;
}
.sidebar-logo-container {
  position: relative;
  width: 100%;
  height: 50px;
  line-height: 50px;
  background: #2b2f3a;
  text-align: center;
  overflow: hidden;

  & .sidebar-logo-link {
    height: 100%;
    width: 100%;

    & .sidebar-logo {
      width: 32px;
      height: 32px;
      vertical-align: middle;
      margin-right: 12px;
    }

    & .sidebar-title {
      display: inline-block;
      margin: 0;
      color: #fff;
      font-weight: 600;
      line-height: 50px;
      font-size: 24px;
      vertical-align: middle;
    }
    & .hamburger-container {
      line-height: 46px;
      height: 100%;
      float: left;
      cursor: pointer;
      transition: background 0.3s;
      -webkit-tap-highlight-color: transparent;
      vertical-align: middle;
      &:hover {
        background: rgba(0, 0, 0, 0.025);
      }
    }
  }

  &.collapse {
    .sidebar-logo {
      margin-right: 0px;
    }
  }
}
</style>
