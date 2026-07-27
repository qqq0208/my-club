<script setup>
import MyForm from "../../../components/MyForm.vue";
import MyNav from "../../../components/MyNav.vue";
import {onMounted, reactive, ref} from "vue";
import {listApi, updateApi} from "../../../api/axios.js";
import {getResponseData} from "../../../request/index.js";
import {RULE} from "../../../const";
import {ElMessage} from "element-plus";
import router from "../../../router";

// 班级进度记录
let clubProgress = JSON.parse(sessionStorage.getItem('row'));
// 班级下拉菜单选项
let clubOptions = ref([]);
// 课程下拉菜单选项
let courseOptions = ref([]);
// 房间下拉菜单选项
let roomOptions = ref([]);
// 员工下拉菜单选项
let empOptions = ref([]);
// 路径导航
const navItems = [
  {icon: 'Platform', label: '班级管理'},
  {icon: 'More', label: '班级进度列表', url: '/ClubProgress'},
  {icon: 'Edit', label: '修改班级进度'},
];
// 表单项 + 表单值 + 表单规则
let items = ref([
  {label: '班级排序', prop: 'idx', type: 'number', required: true, span: 12},
  {hidden: true, span: 12},
  {label: '班级', prop: 'fkClubId', type: 'select', options: clubOptions.value, required: true, span: 12},
  {label: '课程', prop: 'fkCourseId', type: 'select', options: courseOptions.value, required: true, span: 12},
  {label: '房间', prop: 'fkRoomId', type: 'select', options: roomOptions.value, required: true, span: 12},
  {label: '主讲老师', prop: 'fkTeacherId', type: 'select', options: empOptions.value, required: true, span: 12},
  {label: '班主任老师', prop: 'fkManagerId', type: 'select', options: empOptions.value, required: true, span: 12},
  {label: '助教老师', prop: 'fkAssistantId', type: 'select', options: empOptions.value, required: true, span: 12},
  {label: '进度开始时间', prop: 'startTime', type: 'datetime', required: true, span: 12},
  {label: '预计结束时间', prop: 'expectedEndTime', type: 'datetime', required: true, span: 12},
  {label: '进度描述', prop: 'info', type: 'textarea'},
]);
let params = reactive(clubProgress);
let rules = {info: RULE.INFO};

/* ==================== 修改成功后 ==================== */

function updateSuccess() {
  ElMessage.success('修改记录成功！');
  setTimeout(() => router.push('/ClubProgress'), 1000);
}

/* ==================== 加载函数 ==================== */

onMounted(async () => {
  // 查询全部班级并添加到下拉菜单选项中
  Object.values(getResponseData(await listApi({module: 'club'}))).forEach(direction => {
    clubOptions.value.push({label: direction['title'], value: direction['id']});
  });
  // 查询全部课程并添加到下拉菜单选项中
  Object.values(getResponseData(await listApi({module: 'course'}))).forEach(direction => {
    courseOptions.value.push({label: direction['title'], value: direction['id']});
  });
  // 查询全部房间并添加到下拉菜单选项中
  Object.values(getResponseData(await listApi({module: 'room'}))).forEach(direction => {
    roomOptions.value.push({label: direction['title'], value: direction['id']});
  });
  // 查询全部员工并添加到下拉菜单选项中
  Object.values(getResponseData(await listApi({module: 'emp'}))).forEach(direction => {
    empOptions.value.push({label: direction['realname'], value: direction['id']});
  });
});
</script>

<template>
  <my-nav :items="navItems"/>
  <el-card class="club-progress-update-card" header="修改班级进度信息">
    <my-form v-if="clubOptions.length > 0 && courseOptions.length > 0 && roomOptions.length > 0 && empOptions.length > 0"
             type="update"
             :items="items"
             :params="params"
             :rules="rules"
             :api="updateApi"
             :args="{module: 'clubProgress'}"
             :callback="updateSuccess"/>
  </el-card>
</template>

<style scoped lang="scss">
.club-progress-update-card {
  width: 60%; // 宽度
  margin: 65px auto 0; // 外边距
}
</style>