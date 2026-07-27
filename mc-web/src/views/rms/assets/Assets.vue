<script setup>
import MyNav from "../../../components/MyNav.vue";
import MyHead from "../../../components/MyHead.vue";
import MyTable from "../../../components/MyTable.vue";
import {onMounted, ref, reactive} from "vue";
import {myPage} from "../../../request";
import {deleteApi, deleteBatchApi, excelApi, pageApi} from "../../../api/axios";
import {ElMessage} from "element-plus";
import {PROJECT_INFO} from "../../../const/index.js";

// 路径导航
const navItems = [
  {icon: 'List', label: '资源管理'},
  {icon: 'Coin', label: '资产列表'},
];
// 数据头
const headItems = [
  {type: 'ipt', span: 5, placeholder: '资产名称', callback: pageByTitle},
];
// 表格列
const columns = [
  {label: '资产图片', prop: 'picture', type: 'img', minio: minio},
  {label: '资产名称', prop: 'title'},
  {label: '资产单价', prop: 'price', width: 100},
  {label: '单价单位', prop: 'priceUnit', width: 100},
  {label: '剩余库存', prop: 'stock', width: 100},
  {label: '总计库存', prop: 'total', width: 100},
  {label: '库存单位', prop: 'stockUnit', width: 100},
  {label: '资产描述', prop: 'info', type: 'card'},
];

/* ==================== 资产图片 ==================== */

function minio(src) {
  return PROJECT_INFO.minioHost + '/assets/' + src;
}

/* ==================== 分页查询 ==================== */

// 表格数据 + 分页数据 + 资产名称
let records = ref();
let pageInfo = reactive({pageNum: 1, pageSize: 5, total: 0, callback: page});
let assetsTitle = ref();

async function page(pageNum = pageInfo['pageNum'], pageSize = pageInfo['pageSize']) {
  let config = {
    api: pageApi,
    args: {module: 'assets'},
    params: {pageNum, pageSize},
    records, pageInfo
  }
  // 若资产名不为空，则附加为分页条件
  if (assetsTitle.value) config['params']['title'] = assetsTitle.value;
  // 发送分页请求
  await myPage(config);
}

/* ==================== 搜索资产名 ==================== */

function pageByTitle(val) {
  // 仅当输入框有值，或者当前处于按条件分页状态时，发送分页请求
  if (val || assetsTitle.value) {
    assetsTitle.value = val;
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
  excelApi('/assets/excel', '资产报表');
}

/* ==================== 加载函数 ==================== */

onMounted(() => page());
</script>

<template>
  <my-nav :items="navItems"/>
  <my-head :items="headItems"/>
  <my-table module="assets"
            insert-page="/AssetsInsert"
            update-page="/AssetsUpdate"
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
