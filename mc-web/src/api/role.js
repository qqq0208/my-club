import {MC_MANAGE_AXIOS} from "./axios.js";

// GET - 查询员工角色
export function listByEmpIdApi(empId) {
    return MC_MANAGE_AXIOS.get(`role/listByEmpId/${empId}`)
}

// PUT - 修改员工角色
export function updateByEmpIdApi(empId, roleIds) {
    return MC_MANAGE_AXIOS.put(`role/updateByEmpId?empId=${empId}&roleIds=${roleIds}`)
}