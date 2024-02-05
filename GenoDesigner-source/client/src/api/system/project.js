import request from '@/utils/request'
import { parseStrEmpty } from "@/utils/hmzhkj";

const baseURL = '/system/project/main/';
export function listProject(query) {
  return request({
    url: baseURL+'list',
    method: 'get',
    params: query
  })
}

export function removeProject(parameter) {
  return request({
    url: baseURL+'remove',
    method: 'delete',
    data:parameter
  })
}

export function getProject(id) {
  return request({
    url: baseURL + id,
    method: 'get'
  })
}

export function saveProject(data) {
  return request({
    url: baseURL +'save',
    method: 'post',
    data:data
  })
}

export function updateProject(data) {
  return request({
    url: baseURL +'update',
    method: 'put',
    data:data
  })
}

export function listCreateStaff() {
  return request({
    url: baseURL +'listCreateStaff',
    method: 'get'
  })
}

export function listAllProjects() {
  return request({
    url: baseURL +'listAll',
    method: 'get'
  })
}
