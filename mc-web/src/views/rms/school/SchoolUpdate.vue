<script setup>
import MyForm from "../../../components/MyForm.vue";
import MyNav from "../../../components/MyNav.vue";
import {reactive, ref} from "vue";
import {updateApi} from "../../../api/axios.js";
import {RULE} from "../../../const";
import {ElMessage} from "element-plus";
import router from "../../../router";

// 学校记录
let school = JSON.parse(sessionStorage.getItem('row'));
// 路径导航
const navItems = [
  {icon: 'List', label: '资源管理', url: '/School'},
  {icon: 'School', label: '学校列表', url: '/School'},
  {icon: 'Edit', label: '修改学校'},
];
// 表单项 + 表单值 + 表单规则
let items = ref([
  {label: '学校名称', prop: 'title', required: true, span: 12},
  {label: '学院名称', prop: 'college', required: true, span: 12},
  {label: '专业名称', prop: 'major', required: true, span: 12},
  {label: '班级名称', prop: 'clazz', required: true, span: 12},
  {label: '学校地址', prop: 'address', type: 'textarea'},
  {label: '学校描述', prop: 'info', type: 'textarea'},
]);
let params = reactive(school);
let rules = {title: RULE.TITLE, address: RULE.ADDRESS, info: RULE.INFO};

/* ==================== 修改成功后 ==================== */

function updateSuccess() {
  ElMessage.success('修改记录成功！');
  setTimeout(() => router.push('/School'), 1000);
}
</script>

<template>
  <my-nav :items="navItems"/>
  <el-card class="school-update-card" header="修改学校信息">
    <my-form type="update"
             :items="items"
             :params="params"
             :rules="rules"
             :api="updateApi"
             :args="{module: 'school'}"
             :callback="updateSuccess"/>
  </el-card>
</template>

<style scoped lang="scss">
.school-update-card {
  width: 60%; // 宽度
  margin: 65px auto 0; // 外边距
}
</style>