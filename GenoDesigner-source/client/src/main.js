import Vue from 'vue'
import { storage } from '@/utils/auth'

import Element from 'element-ui'
import './assets/styles/element-variables.scss'
import enLocale from 'element-ui/lib/locale/lang/en'
import zhLocale from 'element-ui/lib/locale/lang/zh-CN'
import locale from 'element-ui/lib/locale'
// locale.use(zhLocale)
// Vue.use(Element)
locale.use(enLocale)
Vue.use(Element)
import '@/assets/styles/index.scss' // global css
import '@/assets/styles/hmzhkj.scss' //
import '@/assets/styles/vxe-table.scss'
import 'intro.js/introjs.css'
import 'intro.js/themes/introjs-modern.css'
import App from './App'
import store from './store'
import router from './router'
import directive from './directive' // directive
import plugins from './plugins' // plugins
import { download } from '@/utils/request'
import uploader from 'vue-simple-uploader'
import './assets/icons' // icon
import './permission' // permission control
import { getDicts } from "@/api/system/dict/data";
import { getConfigKey } from "@/api/system/config";
import { parseTime, resetForm, addDateRange, selectDictLabel, selectDictLabels, handleTree } from "@/utils/hmzhkj";
Vue.use(uploader)
import VXETable from 'vxe-table'
import zhCN from 'vxe-table/lib/locale/lang/zh-CN'
import enUS from 'vxe-table/lib/locale/lang/en-US'
import XEUtils from 'xe-utils'
VXETable.setup({
  i18n: (key, args) => XEUtils.toFormatString(XEUtils.get(enUS, key), args)
})
Vue.use(VXETable)
import Pagination from "@/components/Pagination";


import Editor from "@/components/Editor"

import ImagePreview from "@/components/ImagePreview"

import DictTag from '@/components/DictTag'

import VueMeta from 'vue-meta'

import DictData from '@/components/DictData'

import intro from 'intro.js'


Vue.prototype.getDicts = getDicts
Vue.prototype.getConfigKey = getConfigKey
Vue.prototype.parseTime = parseTime
Vue.prototype.resetForm = resetForm
Vue.prototype.addDateRange = addDateRange
Vue.prototype.selectDictLabel = selectDictLabel
Vue.prototype.selectDictLabels = selectDictLabels
Vue.prototype.download = download
Vue.prototype.handleTree = handleTree
Vue.prototype.$intro = intro


Vue.component('DictTag', DictTag)
Vue.component('Pagination', Pagination)
Vue.component('Editor', Editor)
Vue.component('ImagePreview', ImagePreview)

Vue.use(directive)
Vue.use(plugins)
Vue.use(VueMeta)
DictData.install()

/**
 * If you don't want to use mock-server
 * you want to use MockJs for mock api
 * you can execute: mockXHR()
 *
 * Currently MockJs will be used in the production environment,
 * please remove it before going online! ! !
 */

Vue.use(Element, {
  size: storage('size') || 'medium' // set element-ui default size
})

Vue.config.productionTip = false

new Vue({
  el: '#app',
  router,
  store,
  render: h => h(App)
})
