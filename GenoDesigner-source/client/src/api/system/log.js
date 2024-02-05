import request from '@/utils/request'


export function listLogs(projectId) {
  return request({
    url: '/system/logs/'+projectId,
    method: 'get'
  })
}
