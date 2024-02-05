const TokenKey = 'Admin-Token'
const ExpiresInKey = 'Admin-Expires-In'
import store from "store2";
export const storage = store.namespace("gene_black")

export function getToken() {
  if(storage('rememberMe')){
    return storage(TokenKey)
  }else{
    return storage.session(TokenKey)
  }
}

export function setToken(token) {
  if(storage('rememberMe')){
    return storage(TokenKey,token)
  }else{
    return storage.session(TokenKey,token)
  }
}
export function removeToken() {
  if(storage('rememberMe')){
    return storage.remove(TokenKey)
  }else{
    return storage.session.remove(TokenKey)
  }
}
export function getExpiresIn() {
  let time
  if(storage('rememberMe')){
     time = storage(ExpiresInKey)
  }else{
    time = storage.session(ExpiresInKey)
  }
  if(time){
    return +time
  }else{
    return -1
  }
}

export function setExpiresIn(time) {
  if(storage('rememberMe')){
    return storage(ExpiresInKey,time)
  }else{
    return storage.session(ExpiresInKey,time)
  }
}

export function removeExpiresIn() {
  if(storage('rememberMe')){
    return storage.remove(ExpiresInKey)
  }else{
    return storage.session.remove(ExpiresInKey)
  }
}
