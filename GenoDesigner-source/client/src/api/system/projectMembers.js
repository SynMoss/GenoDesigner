import request from '@/utils/request'
import { parseStrEmpty } from "@/utils/hmzhkj";

const baseURL = '/system/project/members/';

export function listProjectMembers(projectId) {
  return request({
    url: baseURL+'list/' + projectId,
    method: 'get'
  })
}

export function removeProjectMember(data) {
  return request({
    url: baseURL+'remove',
    method: 'delete',
    data:data
  })
}

export function listNonProjectMembers(projectId) {
  return request({
    url: baseURL+ projectId,
    method: 'get'
  })
}

export function saveProjectMember(data) {
  return request({
    url: baseURL+'save',
    method: 'post',
    data:data
  })
}

