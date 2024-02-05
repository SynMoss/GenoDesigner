
import request from '@/utils/request'

const baseURL = '/system/part/';

export function listPart(query) {
  return request({
    url: baseURL+'list',
    method: 'get',
    params: query
  })
}
export function addPart(params) {
  return request({
    url: baseURL+'add',
    method: 'post',
    data: params
  })
}

export function editPart(params) {
    return request({
      url: baseURL+'edit',
      method: 'put',
      data: params
    })
  }

  export function deletePart(params) {
    return request({
      url: baseURL+'delete',
      method: 'delete',
      params: params
    })
  }
  export function checkPartName(params) {
    return request({
      url: baseURL +'checkName',
      method: 'get',
      params:params
    })
  }

