<script setup>
import MyForm from "../../../components/MyForm.vue";
import MyNav from "../../../components/MyNav.vue";
import {reactive, ref} from "vue";
import {updateApi} from "../../../api/axios.js";
import {RULE} from "../../../const";
import {ElMessage} from "element-plus";
import router from "../../../router";

// 方向记录
let direction = JSON.parse(sessionStorage.getItem('row'));
// 路径导航
const navItems = [
  {icon: 'Platform', label: '班级管理'},
  {icon: 'Rank', label: '方向列表', url: '/Direction'},
  {icon: 'Edit', label: '修改方向'},
];
// 表单项 + 表单值 + 表单规则
let items = ref([
  {label: '方向标题', prop: 'title', required: true, span: 12},
  {hidden: true, span: 12},
  {label: '方向描述', prop: 'info', type: 'textarea'},
]);
let params = reactive(direction);
let rules = {title: RULE.TITLE, info: RULE.INFO};

/* ==================== 修改成功后 ==================== */

function updateSuccess() {
  ElMessage.success('修改记录成功！');
  setTimeout(() => router.push('/Direction'), 1000);
}
</script>

<template>
  <my-nav :items="navItems"/>
  <el-card class="direction-update-card" header="修改方向信息">
    <my-form type="update"
             :items="items"
             :params="params"
             :rules="rules"
             :api="updateApi"
             :args="{module: 'direction'}"
             :callback="updateSuccess"/>
  </el-card>
</template>

<style scoped lang="scss">
.direction-update-card{
  width: 60%; // 宽度
  margin: 65px auto 0; // 外边距
}
</style>