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
  {icon: 'Rank', label: '方向列表'}
];
// 数据头
const headItems = [
  {type: 'ipt', span: 5, placeholder: '搜索方向标题', callback: pageByTitle},
];
// 表格列
const columns = [
  {label: '方向标题', prop: 'title'},
  {label: '方向描述', prop: 'info', type: 'card', width: 520},
];

/* ==================== 分页查询 ==================== */

// 表格数据 + 分页数据 + 方向标题
let records = ref();
let pageInfo = reactive({pageNum: 1, pageSize: 5, total: 0, callback: page});
let directionTitle = ref();

async function page(pageNum = pageInfo['pageNum'], pageSize = pageInfo['pageSize']) {
  // 分页参数
  let config = {
    api: pageApi,
    args: {module: 'direction'},
    params: {pageNum, pageSize},
    records, pageInfo
  };
  // 若方向标题不为空，则附加为分页条件
  if (directionTitle.value) config['params']['title'] = directionTitle.value;
  // 发送分页请求
  await myPage(config);
}

/* ==================== 搜索方向标题 ==================== */

function pageByTitle(val) {
  // 仅当输入框有值，或者当前处于按条件分页状态时，发送分页请求
  if (val || directionTitle.value) {
    directionTitle.value = val;
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
  excelApi('/direction/excel', '方向报表');
}

/* ==================== 加载函数 ==================== */

onMounted(() => page());
</script>

<template>
  <my-nav :items="navItems"/>
  <my-head :items="headItems"/>
  <my-table module="direction"
            insert-page="/DirectionInsert"
            update-page="/DirectionUpdate"
            :records="records"
            :columns="columns"
            :delete-api="deleteApi"
            :delete-batch-api="deleteBatchApi"
            :delete-callback="deleteSuccess"
            :excel-api="downloadExcel"
            :pageInfo="pageInfo"/>
</template>

<style scoped lang="scss"></style>