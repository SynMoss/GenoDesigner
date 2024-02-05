import request from '@/utils/request'

const baseURL = '/design/report/';
export function listReport(query) {
  return request({
    url: baseURL+'list',
    method: 'get',
    params: query
  })
}

export function getReportFile(id) {
  return request({
    url: baseURL+'file/'+id,
    method: 'get'
  })
}

export function saveReportFile(params) {
  return request({
    url: baseURL+'file/analysis',
    method: 'post',
    data:params
  })
}

export function saveReport(designId) {
  return request({
    url: baseURL+designId,
    method: 'post'
  })
}

export function deleteReport(id) {
  return request({
    url: baseURL+id,
    method: 'delete'
  })
}

export function listExaminer() {
  return request({
    url: baseURL+'examiner',
    method: 'get'
  })
}

export function savaExamine(params) {
  return request({
    url: baseURL+'submit',
    method: 'post',
    data: params
  })
}

export function getOperationRecord(params) {
  return request({
    url: baseURL+'record/'+params,
    method: 'get'
  })
}

