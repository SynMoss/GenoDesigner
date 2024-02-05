import request from '@/utils/request'
import { parseStrEmpty } from "@/utils/hmzhkj";

export function queryPageList(query) {
  return request({
    url: '/system/model/list/page',
    method: 'get',
    params: query
  })
}

export function queryList() {
  return request({
    url: '/system/model/list',
    method: 'get'
  })
}

export function deleteById(param) {
  return request({
    url: '/system/model/delete',
    method: 'delete',
    params: param
  })
}

export function deleteBatch(param) {
  return request({
    url: '/system/model/deleteBatch',
    method: 'delete',
    params: param
  })
}

export function editModel(data) {
  return request({
    url: '/system/model/edit',
    method: 'put',
    data:data
  })
}

export function addModel(data) {
  return request({
    url: '/system/model/add',
    method: 'post',
    data:data
  })
}

export function checkModelInfoNumber(params) {
  return request({
    url: '/system/model/checkNumber',
    method: 'get',
    params:params
  })
}
