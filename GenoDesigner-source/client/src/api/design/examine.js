import request from '@/utils/request'

const baseURL = '/design/examine/';
export function listExamine(query) {
  return request({
    url: baseURL+'list',
    method: 'get',
    params: query
  })
}

export function updateExamineState(params) {
  return request({
    url: baseURL,
    method: 'put',
    data: params
  })
}



