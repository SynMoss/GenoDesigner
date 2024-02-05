import request from '@/utils/request'
import axios from 'axios'

const baseURL = '/system'
const { CancelToken, isCancel } = axios
let cancel

export function importFeature(data,query) {
  return request({
    url: baseURL+'/sequence/importFeature',
    method: 'post',
    params: query,
    data:data,
    headers:{repeatSubmit: false}
  })
}
export function insertSequenceByFeatureTypeEnd3(data,query) {
  return request({
    url: baseURL+'/sequence/insertSequenceByFeatureTypeEnd3',
    method: 'post',
    params: query,
    data:data,
  })
}
export function codonReplace(data,query) {
  return request({
    url: baseURL+'/sequence/codonReplace',
    method: 'post',
    params: query,
    data:data,
  })
}
export function removeRepeatFeature(data,query) {
  return request({
    url: baseURL+'/sequence/removeRepeatFeature',
    method: 'post',
    data: data,
    params: query,
  })
}

export function cutSequence(data,query) {
  return request({
    url: baseURL+'/sequence/save',
    method: 'post',
    params: query,
    data:data,
  })
}
export function importConfig(data,query) {
  return request({
    url: baseURL+'/sequence/importConfig',
    method: 'post',
    params: query,
    data:data,
  })
}
export function chooseFile(data,query) {
  return request({
    url: baseURL+'/sequence/chooseFile',
    method: 'post',
    params: query,
    data:data,
  })
}
export function listSequence(query) {
  return request({
    url: baseURL+'/sequence/list',
    method: 'get',
    params: query
  })
}
export function listBySequenceId(query){
  return request({
    url: baseURL+'/sequence/listBySequenceId',
    method: 'get',
    params: query
  })
}
export function listSequenceFile(query) {
  return request({
    url: baseURL+'/sequence/list/file',
    method: 'get',
    params: query
  })
}
export function listSequenceOperate(query) {
  return request({
    url: baseURL+'/sequence/operate/list',
    method: 'get',
    params: query
  })
}
export function saveOperateAll(data) {
  return request({
    url: baseURL+'/sequence/operate/saveAll',
    method: 'post',
    data: data
  })
}
export function saveFeatureList(data) {
  return request({
    url: baseURL+'/sequence/saveFeatureList',
    method: 'post',
    data: data
  })
}
export function saveOperate(data) {
  return request({
    url: baseURL+'/sequence/operate',
    method: 'post',
    data: data
  })
}
export function operateRollback(query) {
  return request({
    url: baseURL+'/sequence/operate/rollback',
    method: 'delete',
    params: query
  })
}
export function cancelCutSequence(query) {
  return request({
    url: baseURL+'/sequence/cancel',
    method: 'delete',
    params: query
  })
}
export function getSequence(query) {
  return request({
    url: baseURL+'/sequence/get',
    method: 'get',
    params: query
  })
}
export function search(data){
  return request({
    url: baseURL+ '/sequence/search',
    data: data,
    method:'post' ,
    headers: {
      'Content-Type': 'multipart/form-data',      },
  })
}
export function pack(data) {
  return request({
    url: baseURL+'/sequence/pack',
    cancelToken: new CancelToken((c) => {       cancel = c;
    }),
    method: 'post',
    data: data
  })
}

export function cancelPack(){
    cancel("取消打包")
}
export function listType(query) {
  return request({
    url: baseURL+'/sequence/listType',
    method: 'get',
    params: query
  })
}
export function removeByType(data,query) {
  return request({
    url: baseURL+'/sequence/removeByType',
    method: 'post',
    data: data,
    params: query
  })
}
export function mergeFeature(data,query) {
  return request({
    url: baseURL+'/sequence/mergeFeature',
    method: 'post',
    data: data,
    params: query
  })
}
export function remainByType(data,query) {
  return request({
    url: baseURL+'/sequence/remainByType',
    method: 'post',
    data: data,
    params: query
  })
}
export function importFeatureFromGb(data,query) {
  return request({
    url: baseURL+'/sequence/importFeatureFromGb',
    method: 'post',
    params: query,
    data:data,
    headers:{repeatSubmit: false}
  })
}