import {PROJECT_INFO} from "../const";
import {MC_MANAGE_AXIOS} from "./axios.js";

// URL - 上传资产图片地址
export const UPLOAD_ASSETS_URL = PROJECT_INFO.manageHost + '/api/v1/assets/uploadPicture';

// PUT - 根据主键归还资产
export function returnAssetsApi(id) {
    return MC_MANAGE_AXIOS.put(`assetsBorrow/returnAssets/${id}`);
}