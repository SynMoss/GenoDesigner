import request from '@/utils/request'
import { parseStrEmpty } from "@/utils/hmzhkj";

const baseURL = '/system/programme/';
export function listProgramme(query) {
  return request({
    url: baseURL+'list',
    method: 'get',
    params: query
  })
}

export function deleteBatch(ids) {
  return request({
    url: baseURL+'deleteBatch',
    method: 'delete',
    params: ids
  })
}

export function createBlank(params) {
  return request({
    url: baseURL+'createBlank',
    method: 'post',
    data: params
  })
}
export function createByTemplate(params) {
  return request({
    url: baseURL+'createByTemplate',
    method: 'post',
    data: params
  })
}

export function editProgramme(params) {
  return request({
    url: baseURL+'edit',
    method: 'put',
    data: params
  })
}

export function removeProgrammeInProject(id) {
  return request({
    url: baseURL+'project/' + id,
    method: 'delete'
  })
}
export function programmePackage(params) {
  return request({
    url: baseURL+'programme-package',
    method: 'post',
    params: params
  })
}
export function getAllUser() {
  return request({
    url: baseURL+'user/list',
    method: 'get'
  })
}

export function shareProgramme(params) {
  return request({
    url: baseURL+'share%2Dprogramme',
    method: 'post',
    data:params
  })
}

export function disposeShareProgramme(data) {
  return request({
    url: baseURL+'dispose-share-programme',
    method: 'post',
    data: data
  })
}

export function listViewedProgrammes() {
  return request({
    url: baseURL+'listViewed',
    method: 'get'
  })
}

export function checkProjectName(params) {
  return request({
    url: baseURL +'checkName',
    method: 'get',
    params:params
  })
}

export function listNoProjectProgrammes(params) {
  return request({
    url: baseURL +'listNoProjectProgrammes',
    method: 'get',
    params: params
  })
}

export function updateProject(params) {
  return request({
    url: baseURL +'updateProject',
    method: 'put',
    data: params
  })
}

export function getDesignToken(params) {
  return request({
    url: baseURL +'getDesignToken',
    method: 'get',
    params:params
  })
}

export function sendtoLab(params) {
  return request({
    url: baseURL +'toLab',
    method: 'post',
    data:params
  })
}
export function cloneProgramme(params) {
  return request({
    url: baseURL+'clone',
    method: 'post',
    data: params
  })
}

export function share(params) {
  return request({
    url: baseURL+'share',
    method: 'post',
    data: params
  })
}

export function grna(params) {
  return request({
    url: baseURL +'grna',
    method: 'post',
    data:params,
    timeout: 900000,
    headers: {
      'Content-Type': 'multipart/form-data'
      }
  })
}

export function primerPrediction(params) {
  return request({
    url: baseURL +'primerPrediction',
    method: 'post',
    data:params,
    timeout: 900000,
    headers: {
      'Content-Type': 'multipart/form-data'
      }
  })
}

export function geneKnockout(params) {
  return request({
    url: baseURL +'geneKnockout',
    method: 'post',
    data:params,
    timeout: 900000,
    headers: {
      'Content-Type': 'multipart/form-data'
      }
  })
}

export function other(params) {
  return request({
    url: baseURL +'other',
    method: 'post',
    data:params,
    timeout: 900000,
    headers: {
      'Content-Type': 'multipart/form-data'
      }
  })
}

export function resultDownload(params) {
  return request({
    url: baseURL +'resultDownload',
    params: params,
    method:'get' ,
    responseType: 'blob'
  })
}

export function sampleFile(params) {
  return request({
    url: baseURL +'sampleFile',
    params: params,
    method:'get' ,
    responseType: 'blob'
  })
}

export function checkIsCompletes(params) {
  return request({
    url: baseURL +'checkIsCompletes',
    params: params,
    method:'get' ,
  })
}

export function delStepFiles(params) {
  return request({
    url: baseURL +'delStepFiles',
    data: params,
    method:'delete' ,
  })
}



export function blast2(params) {
  return request({
    url: baseURL +'blast2',
    data: params,
    method:'post' ,
    timeout: 15 * 60 * 1000,
  })
}

export function geneExpression(params) {
  return request({
    url: baseURL +'geneExpression',
    data: params,
    method:'post' ,
    timeout: 15 * 60 * 1000,
    responseType: 'blob'
  })
}


