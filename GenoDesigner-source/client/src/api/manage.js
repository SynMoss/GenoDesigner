import request from '@/utils/request'


export function postAction(url,data,parameter) {

  return request({
    url: url,
    method:'post' ,
    data: data,
    params: parameter,
  })
}


export function httpAction(url,parameter,method) {

  return request({
    url: url,
    method:method ,
    data: parameter,
  })
}

//put
export function putAction(url,parameter) {
  return request({
    url: url,
    method:'put',
    data: parameter
  })
}


export function getAction(url,parameter) {

  return request({
    url: url,
    method: 'get',
    params: parameter,
  })
}


export function deleteAction(url,parameter) {
  return request({
    url: url,
    method: 'delete',
    params: parameter
  })
}





export function downFile(url,parameter){
  return request({
    url: url,
    params: parameter,
    method:'get' ,
    responseType: 'blob'
  })
}

 export function downFilePost(url,data){
  return request({
    url: url,
    data:data,
    method:'post' ,
    responseType: 'blob'
  })
}

export function downloadFile(url, fileName, parameter) {
  return downFile(url, parameter).then((data) => {
    if (!data || data.size === 0) {
      return
    }
    if (typeof window.navigator.msSaveBlob !== 'undefined') {
      window.navigator.msSaveBlob(new Blob([data]), fileName)
    } else {
      let url = window.URL.createObjectURL(new Blob([data]))
      let link = document.createElement('a')
      link.style.display = 'none'
      link.href = url
      link.setAttribute('download', fileName)
      document.body.appendChild(link)
      link.click()
      document.body.removeChild(link) 
      window.URL.revokeObjectURL(url) 
    }
  })
}
export function uploadFile(url,formData){
  return request({
    url: url,
    data: formData,
    method:'post' ,
    headers: {
      'Content-Type': 'multipart/form-data',
    },
  })
}

export function uploadAction(url,parameter){
  return request({
    url: url,
    data: parameter,
    method:'post' ,
    headers: {
      'Content-Type': 'multipart/form-data',  
    },
  })
}


export function getFileAccessHttpUrl(avatar,subStr) {
  if(!subStr) subStr = 'http'
  try {
    if(avatar && avatar.startsWith(subStr)){
      return avatar;
    }else{
      if(avatar &&　avatar.length>0 && avatar.indexOf('[')==-1){
        return window._CONFIG['staticDomainURL'] + "/" + avatar;
      }
    }
  }catch(err){
   return;
  }
}
