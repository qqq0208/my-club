<script setup>
import MyForm from "../../../components/MyForm.vue";
import MyNav from "../../../components/MyNav.vue";
import {onMounted, reactive, ref} from "vue";
import {insertApi, listApi} from "../../../api/axios.js";
import {getResponseData} from "../../../request/index.js";
import {RULE} from "../../../const";
import {ElMessage} from "element-plus";
import router from "../../../router";

// 员工下拉菜单选项
let empOptions = ref([]);
// 资产下拉菜单选项
let assetsOptions = ref([]);
// 路径导航
const navItems = [
  {icon: 'List', label: '资源管理', url: '/Assets'},
  {icon: 'Coin', label: '资产申请列表', url: '/AssetsBorrow'},
  {icon: 'Plus', label: '添加资产申请记录'},
];
// 表单项 + 表单值 + 表单规则
let items = ref([
  {label: '资产名称', prop: 'fkAssetsId', required: true, type: 'select', options: assetsOptions},
  {label: '员工名称', prop: 'fkEmpId', required: true, type: 'select', options: empOptions},
  {label: '申请数量', prop: 'count', required: true, type: 'number'},
  {label: '申请时间', prop: 'borrowTime', required: true, type: 'datetime', placeholder: '请选择申请时间'},
  {label: '预计归还时间', prop: 'expectedReturnTime', required: true, type: 'datetime', placeholder: '请选择预计归还时间'},
  {label: '资产描述', prop: 'info', type: 'textarea'},
]);
let params = reactive({});
let rules = {info: RULE.INFO};

/* ==================== 添加成功后 ==================== */

function insertSuccess() {
  ElMessage.success('添加记录成功！');
  setTimeout(() => router.push('/AssetsBorrow'), 1000);
}

/* ==================== 加载函数 ==================== */

onMounted(async () => {
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
  <el-card class="assets-borrow-insert-card" header="添加资产申请记录">
    <my-form type="insert"
             :items="items"
             :params="params"
             :rules="rules"
             :api="insertApi"
             :args="{module: 'assetsBorrow'}"
             :callback="insertSuccess"/>
  </el-card>
</template>

<style scoped lang="scss">
.assets-borrow-insert-card {
  width: 60%; // 宽度
  margin: 65px auto 0; // 外边距
}
</style>