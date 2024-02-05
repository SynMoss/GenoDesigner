import request from '@/utils/request'

export function list(query) {
  return request({
    url: '/system/online/list',
    method: 'get',
    params: query
  })
}

export function forceLogout(tokenId) {
  return request({
    url: '/system/online/' + tokenId,
    method: 'delete'
  })
}
