<script setup>
import MyForm from "../../../components/MyForm.vue";
import MyNav from "../../../components/MyNav.vue";
import MyUpload from "../../../components/MyUpload.vue";
import {reactive, ref} from "vue";
import {updateApi} from "../../../api/axios.js";
import {RULE} from "../../../const";
import router from "../../../router";
import {ElMessage} from "element-plus";
import {UPLOAD_ASSETS_URL} from "../../../api/assets.js";

// 资产记录
let assets = JSON.parse(sessionStorage.getItem('row'));
// 路径导航
const navItems = [
  {icon: 'List', label: '资源管理', url: '/Assets'},
  {icon: 'Coin', label: '资产列表', url: '/Assets'},
  {icon: 'Edit', label: '修改资产'},
];
// 表单项 + 表单值 + 表单规则
let items = ref([
  {label: '资产名称', prop: 'title', required: true},
  {label: '资产单价', prop: 'price', required: true, span: 12, type: 'number'},
  {label: '单价单位', prop: 'priceUnit', required: true, span: 12},
  {label: '总计库存', prop: 'total', required: true, span: 12, type: 'number'},
  {label: '库存单位', prop: 'stockUnit', required: true, span: 12},
  {label: '资产描述', prop: 'info', type: 'textarea'},
]);
let params = reactive(assets);
let rules = {title: RULE.TITLE, info: RULE.INFO};

/* ==================== 修改成功后 ==================== */

function updateSuccess() {
  ElMessage.success('修改记录成功！');
  setTimeout(() => router.push('/Assets'), 1000);
}
</script>

<template>
  <my-nav :items="navItems"/>
  <el-row :gutter="30" class="assets-update-row">
    <el-col :span="16">
      <el-card class="assets-update-card" header="修改基本信息">
        <my-form type="update"
                 :items="items"
                 :params="params"
                 :rules="rules"
                 :api="updateApi"
                 :args="{module: 'assets'}"
                 :callback="updateSuccess"/>
      </el-card>
    </el-col>
    <el-col :span="8">
      <el-card header="上传资产图片">
        <my-upload :url="UPLOAD_ASSETS_URL + '/' + assets['id']"
                   name="pictureFile"
                   :callback="updateSuccess"
                   :autoUpload="true"/>
      </el-card>
    </el-col>
  </el-row>
</template>

<style scoped lang="scss">
.assets-update-row {
  margin: 65px auto 0; // 外边距
}
</style>