<script setup>
import MyNav from "../../../components/MyNav.vue";
import MyHead from "../../../components/MyHead.vue";
import MyTable from "../../../components/MyTable.vue";
import {onMounted, ref, reactive} from "vue";
import {myPage} from "../../../request";
import {deleteApi, deleteBatchApi, excelApi, pageApi} from "../../../api/axios";
import {ElMessage} from "element-plus";

// 路径导航
const navItems = [
  {icon: 'Platform', label: '班级管理'},
  {icon: 'Reading', label: '课程列表'}
];
// 数据头
const headItems = [
  {type: 'ipt', span: 5, placeholder: '搜索课程标题', callback: pageByTitle},
  {type: 'ipt', span: 5, placeholder: '搜索课程分类', callback: pageByCategory},
];
// 表格列
const columns = [
  {label: '课程标题', prop: 'title'},
  {label: '课程分类', prop: 'category'},
  {label: '分类排序', prop: 'idx', width: 90},
  {label: '总计课时', prop: 'hours', type: 'tag', suffix: ' 小时'},
  {label: '课程描述', prop: 'info', type: 'card', width: 520},
];

/* ==================== 分页查询 ==================== */

// 表格数据 + 分页数据 + 课程标题 + 课程分类
let records = ref();
let pageInfo = reactive({pageNum: 1, pageSize: 5, total: 0, callback: page});
let courseTitle = ref();
let courseCategory = ref();

async function page(pageNum = pageInfo['pageNum'], pageSize = pageInfo['pageSize']) {
  // 分页参数
  let config = {
    api: pageApi,
    args: {module: 'course'},
    params: {pageNum, pageSize},
    records, pageInfo
  };
  // 若课程标题不为空，则附加为分页条件
  if (courseTitle.value) config['params']['title'] = courseTitle.value;
  // 若课程分类不为空，则附加为分页条件
  if (courseCategory.value) config['params']['category'] = courseCategory.value;
  // 发送分页请求
  await myPage(config);
}

/* ==================== 搜索课程标题 ==================== */

function pageByTitle(val) {
  // 仅当输入框有值，或者当前处于按条件分页状态时，发送分页请求
  if (val || courseTitle.value) {
    courseTitle.value = val;
    page();
  }
}

/* ==================== 搜索课程分类 ==================== */

function pageByCategory(val) {
  // 仅当输入框有值，或者当前处于按条件分页状态时，发送分页请求
  if (val || courseCategory.value) {
    courseCategory.value = val;
    page();
  }
}

/* ==================== 删除成功回调 ==================== */

function deleteSuccess() {
  ElMessage.success('删除成功！');
  page();
}

/* ==================== 报表打印 ==================== */

function downloadExcel() {
  excelApi('/course/excel', '课程报表');
}

/* ==================== 加载函数 ==================== */

onMounted(() => page());
</script>

<template>
  <my-nav :items="navItems"/>
  <my-head :items="headItems"/>
  <my-table module="course"
            insert-page="/CourseInsert"
            update-page="/CourseUpdate"
            :records="records"
            :columns="columns"
            :delete-api="deleteApi"
            :delete-batch-api="deleteBatchApi"
            :delete-callback="deleteSuccess"
            :excel-api="downloadExcel"
            :pageInfo="pageInfo"/>
</template>

<style scoped lang="scss"></style>