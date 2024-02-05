import request from '@/utils/request'

export function list(query) {
  return request({
    url: '/system/logininfor/list',
    method: 'get',
    params: query
  })
}

export function delLogininfor(infoId) {
  return request({
    url: '/system/logininfor/' + infoId,
    method: 'delete'
  })
}

export function unlockLogininfor(userName) {
  return request({
    url: '/system/logininfor/unlock/' + userName,
    method: 'get'
  })
}
export function isLock(userName) {
  return request({
    url: '/system/logininfor/isLock/' + userName,
    method: 'get'
  })
}
export function cleanLogininfor() {
  return request({
    url: '/system/logininfor/clean',
    method: 'delete'
  })
}
