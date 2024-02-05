import request from '@/utils/request'

export const getRouters = () => {
  return request({
    url: '/system/menu/getRouters',
    method: 'get'
  })
}