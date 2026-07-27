<script setup>
import MyForm from "../../components/MyForm.vue";
import MyNav from "../../components/MyNav.vue";
import MyUpload from "../../components/MyUpload.vue";
import {GENDER_OPTIONS, PROVINCE_OPTIONS, RULE} from "../../const";
import {ElMessage} from "element-plus";
import {getResponseData} from "../../request";
import {onMounted, reactive, ref} from "vue";
import {listApi, updateApi} from "../../api/axios.js";
import {updatePasswordApi, UPLOAD_AVATAR_URL} from "../../api/emp.js";
import router from "../../router";

// 获取当前登录的员工记录
const loginEmp = JSON.parse(sessionStorage.getItem('loginEmp'));
// 所在部门下拉菜单选项
let deptOptions = ref([]);

// 路径导航
const navItems = [
  {icon: 'House', label: 'DashBoard', url: '/DashBoard'},
  {icon: 'Edit', label: '修改个人信息'},
];

/* ==================== 修改基本信息 ==================== */

// 表单项 + 表单值 + 表单规则
let items = ref([
  {label: '登录账号', prop: 'username', required: true, disabled: true, span: 12},
  {label: '登录密码', prop: 'password', required: true, disabled: true, hidden: true, span: 12},
  {label: '真实姓名', prop: 'realname', required: true, span: 12},
  {label: '员工年龄', prop: 'age', required: true, span: 12, type: 'number'},
  {label: '手机号码', prop: 'phone', required: true, span: 12},
  {label: '身份证号', prop: 'idcard', required: true, span: 12},
  {label: '微信号码', prop: 'wechat', required: true, span: 12},
  {label: '电子邮箱', prop: 'email', required: true, span: 12},
  {label: '入职日期', prop: 'hiredate', required: true, type: 'datetime', span: 12},
  {label: '员工性别', prop: 'gender', required: true, type: 'select', options: GENDER_OPTIONS, span: 12},
  {label: '所在省份', prop: 'province', required: true, type: 'select', options: PROVINCE_OPTIONS, span: 12},
  {label: '所在部门', prop: 'fkDeptId', required: true, type: 'select', options: deptOptions.value, span: 12},
  {label: '现居住地', prop: 'address', required: true, type: 'textarea', rows: 2},
  {label: '员工描述', prop: 'info', type: 'textarea', rows: 7},
]);
let params = reactive(loginEmp);
let rules = {
  realname: RULE.REALNAME,
  phone: RULE.PHONE,
  email: RULE.EMAIL,
  province: RULE.PROVINCE,
  address: RULE.ADDRESS,
  idcard: RULE.IDCARD,
  info: RULE.INFO,
};

/* ==================== 修改个人密码 ==================== */

// 表单项 + 表单值 + 表单规则
let updatePasswordItems = ref([
  {label: '原密码', prop: 'oldPassword', type: 'password', required: true, placeholder: '请输入原密码'},
  {label: '新密码', prop: 'newPassword', type: 'password', required: true, placeholder: '请输入新密码'},
  {label: '确认密码', prop: 'rePassword', type: 'password', required: true, placeholder: '请确认新密码'},
]);
let updatePasswordParams = reactive({id: loginEmp['id']});
let updatePasswordRules = {
  oldPassword: RULE.PASSWORD,
  newPassword: [RULE.PASSWORD[0], {
    validator: (rule, value, callback) => {
      if (value === updatePasswordParams['oldPassword']) callback('新旧密码不能相同');
      else callback();
    },
    trigger: ['blur', 'input']
  }],
  rePassword: [RULE.PASSWORD[0], {
    validator: (rule, value, callback) => {
      if (value !== updatePasswordParams['newPassword']) callback('两次密码不一致');
      else callback();
    },
    trigger: ['blur', 'input']
  }],
};

/* ==================== 修改成功后 ==================== */

function updateSuccess() {
  ElMessage('修改个人信息后需要重新登录！');
  setTimeout(() => router.push('/'), 1000);
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
  <el-row :gutter="20" class="personal-update-body">
    <el-col :span="16">
      <el-card class="update-card" header="修改基本信息">
        <my-form v-if="deptOptions.length > 0" type="update" :items="items" :params="params" :rules="rules" :api="updateApi" :args="{'module': 'emp'}" :callback="updateSuccess"/>
      </el-card>
    </el-col>
    <el-col :span="8">
      <el-card class="upload-avatar-card" header="上传个人头像">
        <my-upload :url="UPLOAD_AVATAR_URL + '/' + loginEmp['id']" name="avatarFile" :callback="updateSuccess" :autoUpload="true"/>
      </el-card>
      <el-card class="update-password-card" header="修改个人密码">
        <my-form type="update" :items="updatePasswordItems" :params="updatePasswordParams" :rules="updatePasswordRules" :api="updatePasswordApi" :callback="updateSuccess"/>
      </el-card>
    </el-col>
  </el-row>
</template>

<style scoped lang="scss">
.personal-update-body {
  margin-top: 22px; // 上外边距
  height: 620px; // 高度
  overflow-y: scroll; // Y轴溢出滚动

  .update-password-card {
    margin-top: 37px; // 上外边距
  }
}
</style>