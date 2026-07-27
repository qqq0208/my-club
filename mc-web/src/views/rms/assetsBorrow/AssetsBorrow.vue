<script setup>
import MyNav from "../../../components/MyNav.vue";
import MyHead from "../../../components/MyHead.vue";
import MyTable from "../../../components/MyTable.vue";
import {onMounted, ref, reactive} from "vue";
import {getResponseData, myPage} from "../../../request";
import {deleteApi, deleteBatchApi, excelApi, listApi, pageApi} from "../../../api/axios";
import {datetimeFormat, isNotNull} from "../../../util/index.js";
import {returnAssetsApi} from "../../../api/assets.js";
import {ElMessage, ElMessageBox} from "element-plus";
import {PROJECT_INFO} from "../../../const/index.js";

// 员工下拉菜单选项
let empOptions = ref([]);
// 资产下拉菜单选项
let assetsOptions = ref([]);
// 路径导航
const navItems = [
  {icon: 'List', label: '资源管理'},
  {icon: 'Coin', label: '资产申请列表'},
];
// 数据头
const headItems = [
  {type: 'opt', span: 5, placeholder: '资产名称', options: assetsOptions.value, callback: pageByAssetsId},
  {type: 'opt', span: 5, placeholder: '员工名称', options: empOptions.value, callback: pageByEmpId},
];
// 表格列
const columns = [
  {label: '状态', prop: 'returnTime', format: e => isNotNull(e) ? '已归还' : '', width: 65},
  {label: '资产图片', prop: 'assets.picture', type: 'img', minio: minio},
  {label: '资产名称', prop: 'assets.title'},
  {label: '申请人', prop: 'emp.realname'},
  {label: '申请数量', prop: 'count', width: 100},
  {label: '资产单位', prop: 'assets.stockUnit', width: 100},
  {label: '申请时间', prop: 'created', format: datetimeFormat},
  {label: '预计归还时间', prop: 'expectedReturnTime', format: datetimeFormat},
  {label: '实际归还时间', prop: 'returnTime', format: datetimeFormat},
  {label: '资产申请描述', prop: 'info', type: 'card'},
];
// 按钮列
const buttons = [
  {label: '归还资产', callback: returnAssets},
]

/* ==================== 归还资产 ==================== */

function returnAssets(row) {
  ElMessageBox.confirm('确认归还资产吗？').then(() => {
    returnAssetsApi(row['id']).then(res => {
      let data = getResponseData(res);
      if (data) {
        ElMessage.success('归还成功');
        page();
      }
    })
  }).catch(() => {
    ElMessage.info('已取消');
  });
}

/* ==================== 资产图片 ==================== */

function minio(src) {
  return PROJECT_INFO.minioHost + '/assets/' + src;
}

/* ==================== 分页查询 ==================== */

// 表格数据 + 分页数据 + 资产ID + 员工ID
let records = ref();
let pageInfo = reactive({pageNum: 1, pageSize: 5, total: 0, callback: page});
let fkAssetsId = ref();
let fkEmpId = ref();

async function page(pageNum = pageInfo['pageNum'], pageSize = pageInfo['pageSize']) {
  let config = {
    api: pageApi,
    args: {module: 'assetsBorrow'},
    params: {pageNum, pageSize},
    records, pageInfo
  }

  // 若资产ID不为空，则附加为分页条件
  if (fkAssetsId.value) config['params']['fkAssetsId'] = fkAssetsId.value;
  // 若员工ID不为空，则附加为分页条件
  if (fkEmpId.value) config['params']['fkEmpId'] = fkEmpId.value;
  // 发送分页请求
  await myPage(config);
}

/* ==================== 搜索资产ID ==================== */

function pageByAssetsId(val) {
  // 仅当输入框有值，或者当前处于按条件分页状态时，发送分页请求
  if (val || fkAssetsId.value) {
    fkAssetsId.value = val;
    page();
  }
}

/* ==================== 搜索员工ID ==================== */

function pageByEmpId(val) {
  // 仅当输入框有值，或者当前处于按条件分页状态时，发送分页请求
  if (val || fkEmpId.value) {
    fkEmpId.value = val;
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
  excelApi('/assetsBorrow/excel', '资产申请报表');
}

/* ==================== 加载函数 ==================== */

onMounted(async () => {
  await page();
  // 查询全部员工并添加到下拉菜单选项中
  Object.values(getResponseData(await listApi({module: 'emp'}))).forEach(emp => {
    empOptions.value.push({label: emp['realname'], value: emp['id']});
  });
  // 查询全部资产并添加到下拉菜单选项中
  Object.values(getResponseData(await listApi({module: 'assets'}))).forEach(assets => {
    assetsOptions.value.push({label: assets['title'], value: assets['id']});
  });
});
</script>

<template>
  <my-nav :items="navItems"/>
  <my-head v-if="empOptions.length > 0 && assetsOptions.length > 0" :items="headItems"/>
  <my-table module="assetsBorrow"
            insert-page="/AssetsBorrowInsert"
            update-page="/AssetsBorrowUpdate"
            :records="records"
            :columns="columns"
            :buttons="buttons"
            :delete-api="deleteApi"
            :delete-batch-api="deleteBatchApi"
            :delete-callback="deleteSuccess"
            :excel-api="downloadExcel"
            :pageInfo="pageInfo"/>
</template>

<style scoped lang="scss"></style>
