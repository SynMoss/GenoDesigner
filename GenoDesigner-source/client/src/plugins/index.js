import tab from './tab'
import auth from './auth'
import cache from './cache'
import modal from './modal'
import download from './download'

export default {
  install(Vue) {
        Vue.prototype.$tab = tab
        Vue.prototype.$auth = auth
        Vue.prototype.$cache = cache
        Vue.prototype.$modal = modal
        Vue.prototype.$download = download
  }
}
