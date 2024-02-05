import request from '@/utils/request'
export function listTable(query) {
  return request({
    url: '/code/gen/list',
    method: 'get',
    params: query
  })
}
export function listDbTable(query) {
  return request({
    url: '/code/gen/db/list',
    method: 'get',
    params: query
  })
}

export function getGenTable(tableId) {
  return request({
    url: '/code/gen/' + tableId,
    method: 'get'
  })
}

export function updateGenTable(data) {
  return request({
    url: '/code/gen',
    method: 'put',
    data: data
  })
}

export function importTable(data) {
  return request({
    url: '/code/gen/importTable',
    method: 'post',
    params: data
  })
}

export function previewTable(tableId) {
  return request({
    url: '/code/gen/preview/' + tableId,
    method: 'get'
  })
}

export function delTable(tableId) {
  return request({
    url: '/code/gen/' + tableId,
    method: 'delete'
  })
}

export function genCode(tableName) {
  return request({
    url: '/code/gen/genCode/' + tableName,
    method: 'get'
  })
}

export function synchDb(tableName) {
  return request({
    url: '/code/gen/synchDb/' + tableName,
    method: 'get'
  })
}
