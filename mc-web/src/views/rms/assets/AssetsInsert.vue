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
  {icon: 'List', label: '资源管理', url: '/Assets'},
  {icon: 'Coin', label: '资产列表', url: '/Assets'},
  {icon: 'Plus', label: '添加新资产'},
];
// 表单项 + 表单值 + 表单规则
let items = ref([
  {label: '资产名称', prop: 'title', required: true},
  {label: '资产单价', prop: 'price', required: true, span: 12, type: 'number'},
  {label: '单价单位', prop: 'priceUnit', required: true, span: 12 },
  {label: '总计库存', prop: 'total', required: true, span: 12, type: 'number'},
  {label: '库存单位', prop: 'stockUnit', required: true, span: 12},
  {label: '资产描述', prop: 'info', type: 'textarea'},
]);
let params = reactive({});
let rules = {title: RULE.TITLE, info: RULE.INFO};

/* ==================== 添加成功后 ==================== */

function insertSuccess() {
  ElMessage.success('添加记录成功！');
  setTimeout(() => router.push('/Assets'), 1000);
}
</script>

<template>
  <my-nav :items="navItems"/>
  <el-card class="assets-insert-card" header="添加新资产">
    <my-form type="insert"
             :items="items"
             :params="params"
             :rules="rules"
             :api="insertApi"
             :args="{module: 'assets'}"
             :callback="insertSuccess"/>
  </el-card>
</template>

<style scoped lang="scss">
.assets-insert-card {
  width: 60%; // 宽度
  margin: 65px auto 0; // 外边距
}
</style>