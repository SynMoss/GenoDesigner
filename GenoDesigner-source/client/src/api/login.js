import request from '@/utils/request'
import { encrypt } from "@/utils/jsencrypt";
export function login(data) {
  let subData = Object.assign({},data)
  subData.password = encrypt(subData.password)
  return request({
    url: '/system/login',
    headers: {
      isToken: false
    },
    method: 'post',
    data: subData
  })
}

export function login2(data) {
  let subData = Object.assign({},data)
  subData.password = encrypt(subData.password)
  return request({
    url: '/system/login2',
    headers: {
      isToken: false
    },
    method: 'post',
    params: subData
  })
}

export function register(data) {
  let subData = Object.assign({},data)
  subData.password = encrypt(subData.password)
  return request({
    url: '/system/register',
    headers: {
      isToken: false
    },
    method: 'post',
    data: subData
  })
}
export function reset(data) {
  return request({
    url: '/system/reset',
    headers: {
      isToken: false
    },
    method: 'post',
    data: data
  })
}

export function refreshToken() {
  return request({
    url: '/system/refresh',
    method: 'post'
  })
}

export function getInfo() {
  return request({
    url: '/system/user/getInfo',
    method: 'get'
  })
}

export function logout() {
  return request({
    url: '/system/logout',
    method: 'delete'
  })
}

export function getCodeImg() {
  return request({
    url: '/code',
    headers: {
      isToken: false
    },
    method: 'get',
    timeout: 20000
  })
}

export function getEmailCode(data) {
  let subData = Object.assign({},data)
  return request({
    url: '/system/emailcode',
    method: 'post',
    data: subData,
    timeout: 20000
  })
}

export function checkEmailCaptcha(params) {
  return request({
    url: '/system/checkEmailCaptcha',
    method: 'post',
    data:params
  })
}

export function checkPhoneCaptcha(params) {
  return request({
    url: '/system/checkPhoneCaptcha',
    method: 'post',
    data:params
  })
}
