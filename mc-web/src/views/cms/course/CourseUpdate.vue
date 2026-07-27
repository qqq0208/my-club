<script setup>
import MyForm from "../../../components/MyForm.vue";
import MyNav from "../../../components/MyNav.vue";
import {reactive, ref} from "vue";
import {updateApi} from "../../../api/axios.js";
import {RULE} from "../../../const";
import {ElMessage} from "element-plus";
import router from "../../../router";

// 课程记录
let course = JSON.parse(sessionStorage.getItem('row'));
// 路径导航
const navItems = [
  {icon: 'Platform', label: '班级管理'},
  {icon: 'Reading', label: '课程列表', url: '/Course'},
  {icon: 'Edit', label: '修改课程'},
];
// 表单项 + 表单值 + 表单规则
let items = ref([
  {label: '课程标题', prop: 'title', required: true, span: 12},
  {label: '课程分类', prop: 'category', required: true, span: 12},
  {label: '分类排序', prop: 'idx', type: 'number', required: true, span: 12},
  {label: '总计课时', prop: 'hours', type: 'number', required: true, span: 12},
  {label: '课程描述', prop: 'info', type: 'textarea'},
]);
let params = reactive(course);
let rules = {title: RULE.TITLE, info: RULE.INFO};

/* ==================== 修改成功后 ==================== */

function updateSuccess() {
  ElMessage.success('修改记录成功！');
  setTimeout(() => router.push('/Course'), 1000);
}
</script>

<template>
  <my-nav :items="navItems"/>
  <el-card class="course-update-card" header="修改课程信息">
    <my-form type="update"
             :items="items"
             :params="params"
             :rules="rules"
             :api="updateApi"
             :args="{module: 'course'}"
             :callback="updateSuccess"/>
  </el-card>
</template>

<style scoped lang="scss">
.course-update-card {
  width: 60%; // 宽度
  margin: 65px auto 0; // 外边距
}
</style>