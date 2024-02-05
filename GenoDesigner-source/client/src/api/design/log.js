import request from '@/utils/request'


export function listLogs(projectId) {
  return request({
    url: '/design/logs/'+projectId,
    method: 'get'
  })
}
