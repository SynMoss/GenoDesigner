
import request from '@/utils/request'
import { parseStrEmpty } from "@/utils/hmzhkj";

const baseURL = '/system/genePool/';

export function geneFiles(params) {
  return request({
    url: baseURL+'geneFiles',
    method: 'post',
    data: params,
    timeout: 15*60*1000   })
}

