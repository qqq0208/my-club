<script setup>
import MyForm from "../../../components/MyForm.vue";
import MyNav from "../../../components/MyNav.vue";
import {onMounted, reactive, ref} from "vue";
import {insertApi, listApi} from "../../../api/axios.js";
import {getResponseData} from "../../../request/index.js";
import {DEFAULT_PASSWORD, RULE} from "../../../const";
import {ElMessage} from "element-plus";
import router from "../../../router";

// 所在部门下拉菜单选项
let deptOptions = ref([]);
// 路径导航
const navItems = [
  {icon: 'Avatar', label: '用户管理'},
  {icon: 'User', label: '员工列表', url: '/Emp'},
  {icon: 'Plus', label: '添加新员工'},
]
// 表单项 + 表单值 + 表单规则
let items = ref([
  {label: '登录账号', prop: 'username', required: true, span: 12},
  {label: '真实姓名', prop: 'realname', required: true, span: 12},
  {label: '手机号码', prop: 'phone', required: true, span: 12},
  {label: '身份证号', prop: 'idcard', required: true, span: 12},
  {label: '微信号码', prop: 'wechat', required: true, span: 12},
  {label: '电子邮箱', prop: 'email', required: true, span: 12},
  {label: '所在部门', prop: 'fkDeptId', required: true, type: 'select', options: deptOptions.value, span: 12},
  {label: '入职日期', prop: 'hiredate', required: true, type: 'datetime', span: 12},
  {label: '现居住地', prop: 'address', required: true, type: 'textarea', rows: 2},
  {label: '员工描述', prop: 'info', type: 'textarea', rows: 7},
]);
let params = reactive({password: DEFAULT_PASSWORD});
let rules = {
  realname: RULE.REALNAME,
  phone: RULE.PHONE,
  email: RULE.EMAIL,
  address: RULE.ADDRESS,
  idcard: RULE.IDCARD,
  info: RULE.INFO,
};

/* ==================== 添加成功后 ==================== */

function insertSuccess() {
  ElMessage.success('添加成功！');
  setTimeout(() => router.push('/Emp'), 1000);
}

/* ==================== 加载函数 ==================== */

onMounted(async () => {
  // 查询全部部门并添加到下拉菜单选项中
  Object.values(getResponseData(await listApi({module: 'dept'}))).forEach(dept => {
    deptOptions.value.push({label: dept['title'], value: dept['id']});
  });
});
</script>

<template>
  <my-nav :items="navItems"/>
  <el-card class="emp-insert-card" header="添加新员工">
    <my-form v-if="deptOptions.length > 0"
             type="insert"
             :items="items"
             :params="params"
             :rules="rules"
             :api="insertApi"
             :args="{module: 'emp'}"
             :callback="insertSuccess"/>
  </el-card>
</template>

<style scoped lang="scss">
.emp-insert-card {
  width: 60%; // 宽度
  margin: 65px auto 0; // 外边距
}
</style>