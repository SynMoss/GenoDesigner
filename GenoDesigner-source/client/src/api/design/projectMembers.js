import request from '@/utils/request'
import { parseStrEmpty } from "@/utils/hmzhkj";
export function listProjectMembers(projectId) {
  return request({
    url: '/design/project/members/list/' + projectId,
    method: 'get'
  })
}

export function removeProjectMember(data) {
  return request({
    url: '/design/project/members/remove',
    method: 'delete',
    data:data
  })
}

export function listNonProjectMembers(projectId) {
  return request({
    url: '/design/project/members/' + projectId,
    method: 'get'
  })
}

export function saveProjectMember(data) {
  return request({
    url: '/design/project/members/save',
    method: 'post',
    data:data
  })
}

