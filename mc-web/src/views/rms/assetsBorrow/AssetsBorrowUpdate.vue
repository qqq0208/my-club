<script setup>
import MyForm from "../../../components/MyForm.vue";
import MyNav from "../../../components/MyNav.vue";
import {onBeforeMount, onMounted, ref, shallowReactive, shallowRef} from "vue";
import {listApi, updateApi} from "../../../api/axios.js";
import {getResponseData} from "../../../request/index.js";
import {RULE} from "../../../const";
import {ElMessage} from "element-plus";
import router from "../../../router";
import {isNotNull} from "../../../util/index.js";

// 员工下拉菜单选项
let empOptions = ref([]);
// 资产下拉菜单选项
let assetsOptions = ref([]);
// 资产申请记录
let assetsBorrow = JSON.parse(sessionStorage.getItem('row'));
// 路径导航
const navItems = [
  {icon: 'List', label: '资源管理', url: '/Assets'},
  {icon: 'Coin', label: '资产申请列表', url: '/AssetsBorrow'},
  {icon: 'Edit', label: '修改资产申请信息'},
];
// 表单项 + 表单值 + 表单规则
let items = shallowRef([
  {label: '资产名称', prop: 'fkAssetsId', type: 'select', options: assetsOptions.value, required: true},
  {label: '员工名称', prop: 'fkEmpId', type: 'select', options: empOptions.value, required: true},
  {label: '申请数量', prop: 'count', type: 'number', required: true},
  {label: '申请时间', prop: 'borrowTime', type: 'datetime', placeholder: '请选择申请时间', required: true},
  {
    label: '预计归还时间',
    prop: 'expectedReturnTime',
    type: 'datetime',
    placeholder: '请选择预计归还时间',
    required: true
  },
  {label: '资产描述', prop: 'info', type: 'textarea'},
]);
let params = shallowReactive(assetsBorrow);
let rules = {info: RULE.INFO};

/* ==================== 修改成功后 ==================== */

function updateSuccess() {
  ElMessage.success('修改记录成功！');
  setTimeout(() => router.push('/AssetsBorrow'), 1000);
}

/* ==================== 加载函数 ==================== */

onBeforeMount(() => {
  if (isNotNull(assetsBorrow['returnTime'])) {
    ElMessage.warning('已归还的记录不能修改！');
    router.push('/AssetsBorrow');
  }
})

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
  <my-nav :items="navItems" v-if="empOptions.length > 0 && assetsOptions.length > 0"/>
  <el-card class="assets-borrow-update-card" header="修改资产申请信息">
    <my-form type="update"
             :items="items"
             :params="params"
             :rules="rules"
             :api="updateApi"
             :args="{module: 'assetsBorrow'}"
             :callback="updateSuccess"/>
  </el-card>
</template>

<style scoped lang="scss">
.assets-borrow-update-card {
  width: 60%; // 宽度
  margin: 65px auto 0; // 外边距
}
</style>