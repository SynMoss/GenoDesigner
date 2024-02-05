import request from '@/utils/request'

export function list(query) {
  return request({
    url: '/system/operlog/list',
    method: 'get',
    params: query
  })
}

export function delOperlog(operId) {
  return request({
    url: '/system/operlog/' + operId,
    method: 'delete'
  })
}

export function cleanOperlog() {
  return request({
    url: '/system/operlog/clean',
    method: 'delete'
  })
}
