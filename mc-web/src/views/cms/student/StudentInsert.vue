<script setup>
import MyForm from "../../../components/MyForm.vue";
import MyNav from "../../../components/MyNav.vue";
import {onMounted, reactive, ref} from "vue";
import {insertApi, listApi} from "../../../api/axios.js";
import {getResponseData} from "../../../request/index.js";
import {RULE} from "../../../const";
import {ElMessage} from "element-plus";
import router from "../../../router";

// 学校下拉菜单选项
let schoolOptions = ref([]);
// 班级下拉菜单选项
let clubOptions = ref([]);
// 销售下拉菜单选项
let salesmanOptions = ref([]);
// 路径导航
const navItems = [
  {icon: 'Platform', label: '班级管理'},
  {icon: 'Avatar', label: '学生列表', url: '/Student'},
  {icon: 'Plus', label: '添加新学生'},
]
// 表单项 + 表单值 + 表单规则
let items = ref([
  {label: '学生姓名', prop: 'realname', required: true, span: 12},
  {label: '手机号码', prop: 'phone', required: true, span: 12},
  {label: '身份证号', prop: 'idcard', required: true, span: 12},
  {label: '微信号码', prop: 'wechat', required: true, span: 12},
  {label: '所在学校', prop: 'fkSchoolId', required: true, type: 'select', options: schoolOptions.value, span: 12},
  {label: '所在班级', prop: 'fkClubId', required: true, type: 'select', options: clubOptions.value, span: 12},
  {label: '负责销售', prop: 'fkSalesmanId', required: true, type: 'select', options: salesmanOptions.value, span: 12},
  {label: '入学日期', prop: 'studyDate', required: true, type: 'datetime', span: 12},
  {label: '急联姓名', prop: 'emergencyName', required: true, span: 12},
  {label: '急联电话', prop: 'emergencyPhone', required: true, span: 12},
  {label: '现居住地', prop: 'address', required: true, type: 'textarea', rows: 2},
  {label: '学生描述', prop: 'info', type: 'textarea', rows: 7},
]);
let params = reactive({});
let rules = {
  realname: RULE.REALNAME,
  phone: RULE.PHONE,
  address: RULE.ADDRESS,
  idcard: RULE.IDCARD,
  info: RULE.INFO,
  emergencyName: RULE.REALNAME,
  emergencyPhone: RULE.PHONE,
};

/* ==================== 添加成功后 ==================== */

function insertSuccess() {
  ElMessage.success('添加成功！');
  setTimeout(() => router.push('/Student'), 1000);
}

/* ==================== 加载函数 ==================== */

onMounted(async () => {
  // 查询全部学校并添加到下拉菜单选项中
  Object.values(getResponseData(await listApi({module: 'school'}))).forEach(school => {
    let label = school['title'] + school['college'] + school['major'] + school['clazz'];
    schoolOptions.value.push({label: label, value: school['id']});
  });
  // 查询全部班级并添加到下拉菜单选项中
  Object.values(getResponseData(await listApi({module: 'club'}))).forEach(club => {
    clubOptions.value.push({label: club['title'], value: club['id']});
  });
  // 查询全部销售并添加到下拉菜单选项中
  Object.values(getResponseData(await listApi({module: 'emp'}))).forEach(salesman => {
    salesmanOptions.value.push({label: salesman['realname'], value: salesman['id']});
  });
});
</script>

<template>
  <my-nav :items="navItems"/>
  <el-card class="student-insert-card" header="添加新学生">
    <my-form v-if="schoolOptions.length > 0 && clubOptions.length > 0 && salesmanOptions.length > 0"
             type="insert"
             :items="items"
             :params="params"
             :rules="rules"
             :api="insertApi"
             :args="{module: 'student'}"
             :callback="insertSuccess"/>
  </el-card>
</template>

<style scoped lang="scss">
.student-insert-card {
  width: 60%; // 宽度
  margin: 65px auto 0; // 外边距
}
</style>