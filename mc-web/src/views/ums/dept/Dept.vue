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
  {icon: 'Avatar', label: '用户管理'},
  {icon: 'HomeFilled', label: '部门列表'},
];
// 数据头
const headItems = [
  {type: 'ipt', span: 5, placeholder: '搜索部门名', callback: pageByTitle},
];
// 表格列
const columns = [
  {label: '部门名称', prop: 'title'},
  {label: '所在房间', prop: 'room.title'},
  {label: '部门描述', prop: 'info', type: 'card', width: 520},
];

/* ==================== 分页查询 ==================== */

// 表格数据 + 分页数据 + 部门名称
let records = ref();
let pageInfo = reactive({pageNum: 1, pageSize: 5, total: 0, callback: page});
let deptTitle = ref();

async function page(pageNum = pageInfo['pageNum'], pageSize = pageInfo['pageSize']) {
  // 分页参数
  let config = {
    api: pageApi,
    args: {module: 'dept'},
    params: {pageNum, pageSize},
    records, pageInfo
  };
  // 若房间名不为空，则附加为分页条件
  if (deptTitle.value) config['params']['title'] = deptTitle.value;
  // 发送分页请求
  await myPage(config);
}

/* ==================== 搜索部门名 ==================== */

function pageByTitle(val) {
  // 仅当输入框有值，或者当前处于按条件分页状态时，发送分页请求
  if (val || deptTitle.value) {
    deptTitle.value = val;
    page();
  }
}

/* ==================== 删除成功回调 ==================== */

function deleteSuccess() {
  ElMessage.success('删除成功');
  page();
}

/* ==================== 报表打印 ==================== */

function downloadExcel() {
  excelApi('/dept/excel', '部门报表');
}

/* ==================== 加载函数 ==================== */

onMounted(() => page());

</script>

<template>
  <my-nav :items="navItems"/>
  <my-head :items="headItems"/>
  <my-table module="dept"
            insert-page="/DeptInsert"
            update-page="/DeptUpdate"
            :records="records"
            :columns="columns"
            :delete-api="deleteApi"
            :delete-batch-api="deleteBatchApi"
            :delete-callback="deleteSuccess"
            :excel-api="downloadExcel"
            :pageInfo="pageInfo"/>
</template>

<style scoped lang="scss">

</style>