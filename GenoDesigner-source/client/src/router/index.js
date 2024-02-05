import Vue from 'vue'
import Router from 'vue-router'
import { zh_list } from "@/data/constant";
Vue.use(Router)


import Layout from '@/layout'



export const constantRoutes = [
  {
    path: '/redirect',
    component: Layout,
    hidden: true,
    children: [
      {
        path: '/redirect/:path(.*)',
        component: () => import('@/views/redirect')
      }
    ]
  },
  {
    path: '/login',
    name:'login',
    component: () => import('@/views/login2'),
    hidden: true
  },
  {
    path: '/login2',
    name:'login2',
    component: () => import('@/views/login'),
    hidden: true
  },
                      {
      path: '/register',
      component: () => import('@/views/registerforUsername'),
      hidden: true
    },
                  {
      path: '/forgetPassword',
      component: () => import('@/views/forgetPasswordforUsername'),
      hidden: true
    },
  {
    path: '/404',
    component: () => import('@/views/error/404'),
    hidden: true
  },
  {
    path: '/401',
    component: () => import('@/views/error/401'),
    hidden: true
  },
  {
    path: '',
    component: Layout,
    redirect: 'geneBlackList',
    children: [
      {
        path: '/geneBlackList',
        component: () => import('@/views/programme/ProgrammeList'),
        name: 'ProgrammeList',
        hidden: true,
        meta: { title: process.env.VUE_APP_LANGUAGE==='zh'?zh_list.aaaa:'Genome Design Tool',  affix: true }
      },
    ]
  },
  {
    path: '/user',
    component: Layout,
    hidden: true,
    redirect: 'noredirect',
    children: [
      // {
      //   path: 'profile',
      //   component: () => import('@/views/system/user/profile/index'),
      //   name: 'Profile',
      //   meta: { title: 'Personal center', icon: 'user' }
      // },
      {
        path: 'connected',
        component: () => import('@/views/system/user/connected/index'),
        name: 'connected',
        meta: { title: 'Related accounts', icon: 'user' }
      }
    ]
  }
]

export const dynamicRoutes = [
  {
    path: '/system/user-auth',
    component: Layout,
    hidden: true,
    permissions: ['system:user:edit'],
    children: [
      {
        path: 'role/:userId(\\d+)',
        component: () => import('@/views/system/user/authRole'),
        name: 'AuthRole',
        meta: { title: 'user-role', activeMenu: '/system/user' }
      }
    ]
  },
  {
    path: '/system/role-auth',
    component: Layout,
    hidden: true,
    permissions: ['system:role:edit'],
    children: [
      {
        path: 'user/:roleId(\\d+)',
        component: () => import('@/views/system/role/authUser'),
        name: 'AuthUser',
        meta: { title: 'role-user', activeMenu: '/system/role' }
      }
    ]
  },
  {
    path: '/system/dict-data',
    component: Layout,
    hidden: true,
    permissions: ['system:dict:list'],
    children: [
      {
        path: 'index/:dictId(\\d+)',
        component: () => import('@/views/system/dict/data'),
        name: 'Data',
        meta: { title: 'dict', activeMenu: '/system/dict' }
      }
    ]
  },
  // {
  //   path: '/monitor/job-log',
  //   component: Layout,
  //   hidden: true,
  //   permissions: ['monitor:job:list'],
  //   children: [
  //     {
  //       path: 'index',
  //       component: () => import('@/views/monitor/job/log'),
  //       name: 'JobLog',
  //       meta: { title: 'log', activeMenu: '/monitor/job' }
  //     }
  //   ]
  // },
  {
    path: '/tool/gen-edit',
    component: Layout,
    hidden: true,
    permissions: ['tool:gen:edit'],
    children: [
      {
        path: 'index/:tableId(\\d+)',
        component: () => import('@/views/tool/gen/editTable'),
        name: 'GenEdit',
        meta: { title: 'update-gen', activeMenu: '/tool/gen' }
      }
    ]
  }
]

let routerPush = Router.prototype.push;
Router.prototype.push = function push(location) {
  return routerPush.call(this, location).catch(err => err)
}

export default new Router({
  mode: 'hash',   scrollBehavior: () => ({ y: 0 }),
  routes: constantRoutes
})
