import request from '@/utils/request'

export function listPost(query) {
  return request({
    url: '/system/post/list',
    method: 'get',
    params: query
  })
}

export function getPost(postId) {
  return request({
    url: '/system/post/' + postId,
    method: 'get'
  })
}

export function addPost(data) {
  return request({
    url: '/system/post',
    method: 'post',
    data: data
  })
}

export function updatePost(data) {
  return request({
    url: '/system/post',
    method: 'put',
    data: data
  })
}

export function delPost(postId) {
  return request({
    url: '/system/post/' + postId,
    method: 'delete'
  })
}
