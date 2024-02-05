import request from '@/utils/request'
import { parseStrEmpty } from "@/utils/hmzhkj";

export function queryPageList(query) {
  return request({
    url: '/system/programmestate/list/page',
    method: 'get',
    params: query
  })
}

export function removeProgramme(parameter) {
  return request({
    url: '/system/programmestate/delete/'+parameter,
    method: 'delete',
  })
}

export function removeBatch(parameter) {
  return request({
    url: '/system/programmestate/deleteBatch/'+parameter,
    method: 'delete',
  })
}

export function saveProgramme(data) {
  return request({
    url: '/system/programmestate/add',
    method: 'post',
    data:data
  })
}

export function updateProgramme(data) {
  return request({
    url: '/system/programmestate/edit',
    method: 'put',
    data:data
  })
}

