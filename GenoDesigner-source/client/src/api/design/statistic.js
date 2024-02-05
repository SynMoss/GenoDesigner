import request from '@/utils/request'
const baseURL = '/design/statistic/';
export function getDesignCount() {
  return request({
    url: baseURL+'design/',
    method: 'get'
  })
}

export function getReportCount() {
  return request({
    url: baseURL+'report/',
    method: 'get'
  })
}