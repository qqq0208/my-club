import {MC_MANAGE_AXIOS} from "./axios.js";
import {PROJECT_INFO} from "../const";

// URL - 上传员工头像地址
export const UPLOAD_AVATAR_URL = PROJECT_INFO.manageHost + '/api/v1/emp/uploadAvatar';

// POST - 账号密码登录
export function loginByAccountApi(params) {
    return MC_MANAGE_AXIOS.post(`emp/loginByAccount`, params)
}

// PUT - 修改员工密码
export function updatePasswordApi(params) {
    return MC_MANAGE_AXIOS.put(`emp/updatePassword`, params)
}
