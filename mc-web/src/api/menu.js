import {MC_MANAGE_AXIOS} from "./axios.js";

// GET - 查询员工菜单
export function listByEmpId(empId) {
    return MC_MANAGE_AXIOS.get(`menu/listByEmpId/${empId}`)
}

// GET - 查询角色角色
export function listByRoleIdApi(roleId) {
    return MC_MANAGE_AXIOS.get(`menu/listByRoleId/${roleId}`)
}

// PUT - 修改角色菜单
export function updateByRoleIdApi(roleId, menuIds) {
    return MC_MANAGE_AXIOS.put(`menu/updateByRoleId?roleId=${roleId}&menuIds=${menuIds}`)
}