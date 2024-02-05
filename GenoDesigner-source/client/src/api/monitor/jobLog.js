import request from '@/utils/request'

export function listJobLog(query) {
  return request({
    url: '/schedule/job/log/list',
    method: 'get',
    params: query
  })
}

export function delJobLog(jobLogId) {
  return request({
    url: '/schedule/job/log/' + jobLogId,
    method: 'delete'
  })
}

export function cleanJobLog() {
  return request({
    url: '/schedule/job/log/clean',
    method: 'delete'
  })
}
