<script setup>
import MyForm from "../../../components/MyForm.vue";
import MyNav from "../../../components/MyNav.vue";
import MyUpload from "../../../components/MyUpload.vue";
import {onMounted, reactive, ref} from "vue";
import {listApi, updateApi} from "../../../api/axios.js";
import {getResponseData} from "../../../request/index.js";
import {GENDER_OPTIONS, RULE} from "../../../const";
import {ElMessage} from "element-plus";
import {UPLOAD_AVATAR_URL} from "../../../api/emp.js";
import router from "../../../router";

// 员工记录
let emp = JSON.parse(sessionStorage.getItem('row'));
// 所在部门下拉菜单选项
let deptOptions = ref([]);
// 路径导航
const navItems = [
  {icon: 'Avatar', label: '用户管理'},
  {icon: 'User', label: '员工列表', url: '/Emp'},
  {icon: 'Edit', label: '修改员工'},
];
// 表单项 + 表单值 + 表单规则
let items = ref([
  {label: '登录账号', prop: 'username', disabled: true, span: 12},
  {label: '真实姓名', prop: 'realname', required: true, span: 12},
  {label: '手机号码', prop: 'phone', required: true, span: 12},
  {label: '身份证号', prop: 'idcard', required: true, span: 12},
  {label: '微信号码', prop: 'wechat', required: true, span: 12},
  {label: '电子邮箱', prop: 'email', required: true, span: 12},
  {label: '员工性别', prop: 'gender', required: true, type: 'select', options: GENDER_OPTIONS, span: 12},
  {label: '员工年龄', prop: 'age', required: true, type: 'number', span: 12},
  {label: '所在部门', prop: 'fkDeptId', required: true, type: 'select', options: deptOptions.value, span: 12},
  {label: '入职日期', prop: 'hiredate', required: true, type: 'datetime', span: 12},
  {label: '现居住地', prop: 'address', required: true, type: 'textarea', rows: 2},
  {label: '员工描述', prop: 'info', type: 'textarea', rows: 7},
]);
let params = reactive(emp);
let rules = {
  realname: RULE.REALNAME,
  phone: RULE.PHONE,
  email: RULE.EMAIL,
  address: RULE.ADDRESS,
  idcard: RULE.IDCARD,
  info: RULE.INFO,
};

/* ==================== 修改成功后 ==================== */

function updateSuccess() {
  ElMessage.success('修改记录成功！');
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
  <el-row :gutter="30" class="emp-update-row">
    <el-col :span="16">
      <el-card class="emp-update-card" header="修改基本信息">
        <my-form v-if="deptOptions.length > 0"
                 type="update"
                 :items="items"
                 :params="params"
                 :rules="rules"
                 :api="updateApi"
                 :args="{module: 'emp'}"
                 :callback="updateSuccess"/>
      </el-card>
    </el-col>
    <el-col :span="8">
      <el-card header="上传员工头像">
        <my-upload :url="UPLOAD_AVATAR_URL + '/' + emp['id']"
                   name="avatarFile"
                   :callback="updateSuccess"
                   :autoUpload="true"/>
      </el-card>
    </el-col>
  </el-row>
</template>

<style scoped lang="scss">
.emp-update-row {
  margin: 65px auto 0; // 外边距
}
</style>