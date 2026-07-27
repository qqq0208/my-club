import {MC_MANAGE_AXIOS} from "./axios.js";

// PUT - 根据主键结班
export function endClubApi(id) {
    return MC_MANAGE_AXIOS.put(`club/endClub/${id}`);
}