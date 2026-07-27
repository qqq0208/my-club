<script setup>
import MyForm from "../../../components/MyForm.vue";
import MyNav from "../../../components/MyNav.vue";
import MyUpload from "../../../components/MyUpload.vue";
import {onMounted, reactive, ref} from "vue";
import {listApi, updateApi} from "../../../api/axios.js";
import {getResponseData} from "../../../request/index.js";
import {GENDER_OPTIONS, PROVINCE_OPTIONS, RULE, STUDENT_STATUS_OPTIONS} from "../../../const";
import {ElMessage} from "element-plus";
import {UPLOAD_AVATAR_URL} from "../../../api/student.js";
import router from "../../../router";

// 学生记录
let student = JSON.parse(sessionStorage.getItem('row'));
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
  {icon: 'Edit', label: '修改学生'},
];
// 表单项 + 表单值 + 表单规则
let items = ref([
  {label: '学生编号', prop: 'number', disabled: true, span: 8},
  {label: '学生姓名', prop: 'realname', required: true, span: 8},
  {label: '所在班级', prop: 'fkClubId', required: true, type: 'select', options: clubOptions.value, span: 8},
  {label: '手机号码', prop: 'phone', required: true, span: 8},
  {label: '微信号码', prop: 'wechat', required: true, span: 8},
  {label: '身份证号', prop: 'idcard', required: true, span: 8},
  {label: '学生性别', prop: 'gender', required: true, type: 'select', options: GENDER_OPTIONS, span: 8},
  {label: '学生年龄', prop: 'age', required: true, type: 'number', span: 8},
  {label: '所在省份', prop: 'province', required: true, type: 'select', options: PROVINCE_OPTIONS, span: 8},
  {label: '负责销售', prop: 'fkSalesmanId', required: true, type: 'select', options: salesmanOptions.value, span: 8},
  {label: '急联姓名', prop: 'emergencyName', required: true, span: 8},
  {label: '急联电话', prop: 'emergencyPhone', required: true, span: 8},
  {label: '所在学校', prop: 'fkSchoolId', required: true, type: 'select', options: schoolOptions.value, span: 8},
  {label: '学生状态', prop: 'status', required: true, type: 'select', options: STUDENT_STATUS_OPTIONS, span: 8},
  {label: '入学日期', prop: 'studyDate', required: true, type: 'datetime', span: 8},
  {label: '现居住地', prop: 'address', required: true, type: 'textarea', rows: 2},
  {label: '学生描述', prop: 'info', type: 'textarea', rows: 7},
]);
let params = reactive(student);
let rules = {
  realname: RULE.REALNAME,
  phone: RULE.PHONE,
  address: RULE.ADDRESS,
  idcard: RULE.IDCARD,
  info: RULE.INFO,
  emergencyName: RULE.REALNAME,
  emergencyPhone: RULE.PHONE,
};

/* ==================== 修改成功后 ==================== */

function updateSuccess() {
  ElMessage.success('修改记录成功！');
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
  <el-row :gutter="30" class="student-update-row">
    <el-col :span="16">
      <el-card class="student-update-card" header="修改基本信息">
        <my-form v-if="schoolOptions.length > 0 && clubOptions.length > 0 && salesmanOptions.length > 0"
                 type="update"
                 :items="items"
                 :params="params"
                 :rules="rules"
                 :api="updateApi"
                 :args="{module: 'student'}"
                 :callback="updateSuccess"/>
      </el-card>
    </el-col>
    <el-col :span="8">
      <el-card header="上传学生头像">
        <my-upload :url="UPLOAD_AVATAR_URL + '/' + student['id']"
                   name="avatarFile"
                   :callback="updateSuccess"
                   :autoUpload="true"/>
      </el-card>
    </el-col>
  </el-row>
</template>

<style scoped lang="scss">
.student-update-row {
  margin: 65px auto 0; // 外边距
}
</style>