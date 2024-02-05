import request from '@/utils/request'
import { parseStrEmpty } from "@/utils/hmzhkj";

export function listDesign(query) {
  return request({
    url: '/design/design/main/list',
    method: 'get',
    params: query
  })
}

export function saveDesign(data) {
  return request({
    url: '/design/design/main/save',
    method: 'post',
    data: data
  })
}

export function removeDesignInProject(id) {
  return request({
    url: '/design/design/main/project/' + id,
    method: 'delete'
  })
}

export function removeDesign(id) {
  return request({
    url: '/design/design/main/' + id,
    method: 'delete'
  })
}

export function listViewedDesigns() {
  return request({
    url: '/design/design/main/listViewed',
    method: 'get'
  })
}
