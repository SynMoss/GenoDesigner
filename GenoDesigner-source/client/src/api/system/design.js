import request from '@/utils/request'
import { parseStrEmpty } from "@/utils/hmzhkj";

const baseURL = '/system/template/';

export function getTemplateList() {
  return request({
    url: baseURL+'getTemplateList',
    method: 'get',
  })
}
