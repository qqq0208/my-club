<script setup>
import MyForm from "../../../components/MyForm.vue";
import MyNav from "../../../components/MyNav.vue";
import {reactive, ref} from "vue";
import {updateApi} from "../../../api/axios.js";
import {RULE} from "../../../const";
import {ElMessage} from "element-plus";
import router from "../../../router";

// 房间记录
let room = JSON.parse(sessionStorage.getItem('row'));
// 路径导航
const navItems = [
  {icon: 'List', label: '资源管理', url: '/Room'},
  {icon: 'OfficeBuilding', label: '房间列表', url: '/Room'},
  {icon: 'Edit', label: '修改房间'},
];
// 表单项 + 表单值 + 表单规则
let items = ref([
  {label: '房间名称', prop: 'title', required: true, span: 12},
  {span: 12, hidden: true},
  {label: '房间容量', prop: 'capacity', type: 'number', required: true, span: 12},
  {label: '房间地址', prop: 'address', type: 'textarea', required: true, rows: 4},
  {label: '房间描述', prop: 'info', type: 'textarea'},
]);
let params = reactive(room);
let rules = {title: RULE.TITLE, address: RULE.ADDRESS, info: RULE.INFO};

/* ==================== 修改成功后 ==================== */

function updateSuccess() {
  ElMessage.success('修改记录成功！');
  setTimeout(() => router.push('/Room'), 1000);
}
</script>

<template>
  <my-nav :items="navItems"/>
  <el-card class="room-update-card" header="修改房间信息">
    <my-form type="update"
             :items="items"
             :params="params"
             :rules="rules"
             :api="updateApi"
             :args="{module: 'room'}"
             :callback="updateSuccess"/>
  </el-card>
</template>

<style scoped lang="scss">
.room-update-card {
  width: 60%; // 宽度
  margin: 65px auto 0; // 外边距
}
</style>