import request from '@/utils/request'
import { parseStrEmpty } from "@/utils/hmzhkj";

const baseURL = '/system/template/';
export function getTemplateList(params) {
  return request({
    url: baseURL+'getTemplateList',
    method: 'get',
    params: params
  })
}

export function saveTemplate(data) {
  return request({
    url: baseURL+'saveTemplate',
    method: 'post',
    data: data
  })
}

export function deleteTemplate(params) {
  return request({
    url: baseURL+'deleteTemplate',
    method: 'delete',
    params: params
  })
}
export function checkTemplateName(params) {
  return request({
    url: baseURL+'checkName',
    method: 'get',
    params:params
  })
}
