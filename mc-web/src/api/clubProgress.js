import {MC_MANAGE_AXIOS} from "./axios.js";

// PUT - 根据主键结束班级进度
export function endClubProgressApi(id) {
    return MC_MANAGE_AXIOS.put(`clubProgress/endClubProgress/${id}`);
}