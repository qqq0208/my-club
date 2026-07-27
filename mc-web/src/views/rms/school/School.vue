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
  {icon: 'List', label: '资源管理'},
  {icon: 'School', label: '学校列表'},
];
// 数据头
const headItems = [
  {type: 'ipt', span: 5, placeholder: '学校名称', callback: pageByTitle},
];
// 表格列
const columns = [
  {label: '学校名称', prop: 'title'},
  {label: '学院名称', prop: 'college'},
  {label: '专业名称', prop: 'major'},
  {label: '班级名称', prop: 'clazz'},
  {label: '学校地址', prop: 'address', type: 'card'},
  {label: '学校描述', prop: 'info', type: 'card'},
];

/* ==================== 分页查询 ==================== */

// 表格数据 + 分页数据 + 学校名称
let records = ref();
let pageInfo = reactive({pageNum: 1, pageSize: 5, total: 0, callback: page});
let schoolTitle = ref();

async function page(pageNum = pageInfo['pageNum'], pageSize = pageInfo['pageSize']) {
  // 分页参数
  let config = {
    api: pageApi,
    args: {module: 'school'},
    params: {pageNum, pageSize},
    records, pageInfo
  }
  // 若学校名不为空，则附加为分页条件
  if (schoolTitle.value) config['params']['title'] = schoolTitle.value;
  // 发送分页请求
  await myPage(config);
}

/* ==================== 搜索学校名 ==================== */

function pageByTitle(val) {
  // 仅当输入框有值，或者当前处于按条件分页状态时，发送分页请求
  if (val || schoolTitle.value) {
    schoolTitle.value = val;
    page();
  }
}

/* ==================== 删除成功回调 ==================== */

function deleteSuccess(){
  ElMessage.success('删除成功');
  page();
}

/* ==================== 报表打印 ==================== */

function downloadExcel(){
  excelApi('/school/excel', '学校报表');
}

/* ==================== 加载函数 ==================== */

onMounted(() => page());
</script>

<template>
  <my-nav :items="navItems"/>
  <my-head :items="headItems"/>
  <my-table module="school"
            insert-page="/SchoolInsert"
            update-page="/SchoolUpdate"
            :records="records"
            :columns="columns"
            :delete-api="deleteApi"
            :delete-batch-api="deleteBatchApi"
            :delete-callback="deleteSuccess"
            :excel-api="downloadExcel"
            :pageInfo="pageInfo"/>
</template>

<style scoped lang="scss"></style>