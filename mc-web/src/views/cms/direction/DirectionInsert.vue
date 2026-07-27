<script setup>
import MyForm from "../../../components/MyForm.vue";
import MyNav from "../../../components/MyNav.vue";
import {reactive, ref} from "vue";
import {insertApi} from "../../../api/axios.js";
import {RULE} from "../../../const";
import {ElMessage} from "element-plus";
import router from "../../../router";

// 路径导航
const navItems = [
  {icon: 'Platform', label: '班级管理'},
  {icon: 'Rank', label: '方向列表', url: '/Direction'},
  {icon: 'Plus', label: '添加新方向'},
]
// 表单项 + 表单值 + 表单规则
let items = ref([
  {label: '方向标题', prop: 'title', required: true, span: 12},
  {hidden: true, span: 12},
  {label: '方向描述', prop: 'info', type: 'textarea'},
]);
let params = reactive({});
let rules = {title: RULE.TITLE, info: RULE.INFO};

/* ==================== 添加成功后 ==================== */

function insertSuccess() {
  ElMessage.success('添加成功！');
  setTimeout(() => router.push('/Direction'), 1000);
}
</script>

<template>
  <my-nav :items="navItems"/>
  <el-card class="direction-insert-card" header="添加新方向">
    <my-form type="insert"
             :items="items"
             :params="params"
             :rules="rules"
             :api="insertApi"
             :args="{module: 'direction'}"
             :callback="insertSuccess"/>
  </el-card>
</template>

<style scoped lang="scss">
.direction-insert-card {
  width: 60%; // 宽度
  margin: 65px auto 0; // 外边距
}
</style>