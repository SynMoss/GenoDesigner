import request from '@/utils/request'
import { parseStrEmpty } from "@/utils/hmzhkj";

export function listProject(query) {
  return request({
    url: '/design/project/main/list',
    method: 'get',
    params: query
  })
}

export function removeProject(parameter) {
  return request({
    url: '/design/project/main/remove',
    method: 'delete',
    data:parameter
  })
}

export function getProject(id) {
  return request({
    url: '/design/project/main/' + id,
    method: 'get'
  })
}

export function saveProject(data) {
  return request({
    url: '/design/project/main/save',
    method: 'post',
    data:data
  })
}

export function updateProject(data) {
  return request({
    url: '/design/project/main/update',
    method: 'put',
    data:data
  })
}

export function listCreateStaff() {
  return request({
    url: '/design/project/main/listCreateStaff',
    method: 'get'
  })
}

export function listAllProjects() {
  return request({
    url: '/design/project/main/listAll',
    method: 'get'
  })
}
