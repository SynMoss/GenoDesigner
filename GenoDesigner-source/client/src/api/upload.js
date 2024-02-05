import request from '@/utils/request'

export function uploadFile(file){
    let formData = new FormData();
	formData.append('file', file); 
    return request({
      url: '/system/upload',
      data: formData,
      method:'post' ,
      headers: {
        'Content-Type': 'multipart/form-data',        },
    })
  }
  export function mergeBigFiles(data) {
    return request({
      url: '/system/mergeBigFiles',
      method: 'post',
      data: data
    })
  }