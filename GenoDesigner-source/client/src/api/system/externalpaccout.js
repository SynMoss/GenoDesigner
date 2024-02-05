import request from '@/utils/request'

export function listExternalPaccout(query) {
  return request({
    url: '/system/external/paccout/list',
    method: 'get',
    params: query
  })
}
export function saveExternalPaccout(data) {
    return request({
      url: '/system/external/paccout/add',
      method: 'post',
      data:data
    })
}

export function verifyToken(query) {
  return request({
    url: '/system/external/paccout/verifyToken',
    method: 'post',
    data: query,
  })
}

export function updateExternalPaccout(data) {
    return request({
      url: '/system/external/paccout/edit',
      method: 'put',
      data:data
    })
}
export function unBindExternalPaccout(parameter) {
    return request({
        url: '/system/external/paccout/unbind/'+parameter,
        method: 'put',
    })
}
export function bindExternalPaccout(parameter) {
    return request({
        url: '/system/external/paccout/bind/'+parameter,
        method: 'put',
    })
}
export function removeExternalPaccout(parameter) {
    return request({
      url: '/system/external/paccout/delete/'+parameter,
      method: 'delete',
    })
  }
export function hasBindComponent() {
  return request({
    url: '/system/external/paccout/hasBindComponent',
    method: 'get',
  })
}
export function hasBindDesign() {
  return request({
    url: '/system/external/paccout/hasBindDesign',
    method: 'get',
  })
}
export function hasBindGenePool() {
  return request({
    url: '/system/external/paccout/hasBindGenePool',
    method: 'get',
  })
}

export function getDesignToken(params) {
  return request({
    url: '/system/external/paccout/getDesignToken',
    method: 'get',
    params:params
  })
}

export function insertProject(params) {
  return request({
    url: '/system/external/paccout/insertProject',
    method: 'post',
    data:params
  })
}

export function insertDesign(params) {
  return request({
    url: '/system/external/paccout/insertDesign',
    method: 'post',
    data:params
  })
}

