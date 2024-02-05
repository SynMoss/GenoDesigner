
import request from '@/utils/request'
import { parseStrEmpty } from "@/utils/hmzhkj";

const baseURL = '/system/component/';

export function listComponent(query) {
  return request({
    url: baseURL+'list',
    method: 'get',
    params: query
  })
}

export function getComponentByCode(code) {
  return request({
    url: baseURL+'info/'+code,
    method: 'get',
    timeout: 180000
  })
}
  
export function addComponent(params) {
  return request({
    url: baseURL+'add/',
    method: 'post',
    data:params
  })
}

export function destroyUUID(params) {
  return request({
    url: baseURL+'uuid/',
    method: 'put',
    params:params,
    async:false,
  })
}