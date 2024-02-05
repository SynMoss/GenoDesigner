import axios from 'axios'
import { Notification, MessageBox, Message, Loading } from 'element-ui'
import store from '@/store'
import { getToken } from '@/utils/auth'
import errorCode from '@/utils/errorCode'
import { tansParams, blobValidate } from "@/utils/hmzhkj";
import cache from '@/plugins/cache'
import { saveAs } from 'file-saver'
import router from '@/router/'
import { ERR_EN_LIST } from "@/data/constant";
let language = process.env.VUE_APP_LANGUAGE;
let downloadLoadingInstance;
export let isRelogin = { show: false };

axios.defaults.headers['Content-Type'] = 'application/json;charset=utf-8'
const service = axios.create({
    baseURL: process.env.VUE_APP_BASE_API,
    timeout: 60 * 60 * 1000
})

service.interceptors.request.use(config => {
    const isToken = (config.headers || {}).isToken === false
    const isRepeatSubmit = (config.headers || {}).repeatSubmit === false
  if (getToken() && !isToken) {
    config.headers['Authorization'] = 'Bearer ' + getToken()   }
    if (config.method === 'get' && config.params) {
    let url = config.url + '?' + tansParams(config.params);
    url = url.slice(0, -1);
    config.params = {};
    config.url = url;
  }
  if (!isRepeatSubmit && (config.method === 'post' || config.method === 'put')) {
    const requestObj = {
      url: config.url,
      data: typeof config.data === 'object' ? JSON.stringify(config.data) : config.data,
      time: new Date().getTime()
    }
    const sessionObj = cache.session.getJSON('sessionObj')
    if (sessionObj === undefined || sessionObj === null || sessionObj === '') {
      cache.session.setJSON('sessionObj', requestObj)
    } else {
      const s_url = sessionObj.url;                        const s_data = sessionObj.data;                      const s_time = sessionObj.time;                      const interval = 1000;                               if (s_data === requestObj.data && requestObj.time - s_time < interval && s_url === requestObj.url) {
        const message = 'Data is being processed, please do not resubmit!';
        console.warn(`[${s_url}]: ` + message)
        return Promise.reject(new Error(message))
      } else {
        cache.session.setJSON('sessionObj', requestObj)
      }
    }
  }
  return config
}, error => {
    console.log(error)
    Promise.reject(error)
})

service.interceptors.response.use(res => {
        const code = res.data.code || 200;
        const msg = errorCode[code] || res.data.msg || errorCode['default']
        if(res.request.responseType ===  'blob' || res.request.responseType ===  'arraybuffer'){
      return res.data
    }
    if (code === 401) {
      if (!isRelogin.show) {
        isRelogin.show = true;
        MessageBox.confirm('The login status has expired. You can continue to stay on this page or log in again', 'Prompted', {
          confirmButtonText: 'Login again',
          cancelButtonText: 'Cancel',
          type: 'warning'
        }
      ).then(() => {
        isRelogin.show = false;
        store.dispatch('LogOut').then(() => {
          router.push("/login")
        })
      }).catch(() => {
        isRelogin.show = false;
      });
    }
      return Promise.reject('An invalid session, or the session has expired, log in again.。')
    } else if (code === 500) {
      let errmsg
            if(language === 'en'){
        for(const key in ERR_EN_LIST){
          if (msg.includes(key)) {
            errmsg = ERR_EN_LIST[key];
            break;
          }
        }
      }else{
        if(msg==='Not matched!'){
          errmsg = 'Not matched'
        }
      }
      Message({
        message: errmsg || msg,
        type: 'error'
      })
      return Promise.reject(new Error(msg))
    } else if (code !== 200) {
      Notification.error({
        title: msg
      })
      return Promise.reject('error')
    } else {
      return res.data
    }
  },
  error => {
    console.log('err' + error)
    let { message } = error;
    if (message == "Network Error") {
      message = "Network Error";
    }
    else if (message.includes("timeout")) {
      message = "Network Error";
    }
    else if (message.includes("Request failed with status code")) {
      message = "" + message.substr(message.length - 3) + "异常";
    }
    Message({
      message: message,
      type: 'error',
      duration: 5 * 1000
    })
    return Promise.reject(error)
  }
)

export function download(url, params, filename) {
  downloadLoadingInstance = Loading.service({ text: "Downloading data, please wait", spinner: "el-icon-loading", background: "rgba(0, 0, 0, 0.7)", })
  return service.post(url, params, {
    transformRequest: [(params) => { return tansParams(params) }],
    headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
    responseType: 'blob'
  }).then(async (data) => {
    const isLogin = await blobValidate(data);
    if (isLogin) {
      const blob = new Blob([data])
      saveAs(blob, filename)
    } else {
      const resText = await data.text();
      const rspObj = JSON.parse(resText);
      const errMsg = errorCode[rspObj.code] || rspObj.msg || errorCode['default']
      Message.error(errMsg);
    }
    downloadLoadingInstance.close();
  }).catch((r) => {
    console.error(r)
    Message.error('An error occurred while downloading the file. Please contact the administrator!')
    downloadLoadingInstance.close();
  })
}

export default service
