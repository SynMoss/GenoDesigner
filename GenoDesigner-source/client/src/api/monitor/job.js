import request from '@/utils/request'

export function listJob(query) {
  return request({
    url: '/schedule/job/list',
    method: 'get',
    params: query
  })
}

export function getJob(jobId) {
  return request({
    url: '/schedule/job/' + jobId,
    method: 'get'
  })
}

export function addJob(data) {
  return request({
    url: '/schedule/job',
    method: 'post',
    data: data
  })
}

export function updateJob(data) {
  return request({
    url: '/schedule/job',
    method: 'put',
    data: data
  })
}

export function delJob(jobId) {
  return request({
    url: '/schedule/job/' + jobId,
    method: 'delete'
  })
}

export function changeJobStatus(jobId, status) {
  const data = {
    jobId,
    status
  }
  return request({
    url: '/schedule/job/changeStatus',
    method: 'put',
    data: data
  })
}


export function runJob(jobId, jobGroup) {
  const data = {
    jobId,
    jobGroup
  }
  return request({
    url: '/schedule/job/run',
    method: 'put',
    data: data
  })
}