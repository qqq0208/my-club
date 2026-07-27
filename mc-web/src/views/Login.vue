<script setup>
import {onMounted, shallowReactive, shallowRef} from "vue";
import {PROJECT_INFO, RULE} from "../const";
import {ElMessage, ElNotification} from "element-plus";
import router from "../router";
import vuex from "../vuex";
import {loginByAccountApi} from "../api/emp.js";
import {getResponseData} from "../request/index.js";

/* ==================== 员工登录 ==================== */
// 表单 + 表单数据 + 表单规则 todo: 上线后将默认值删除
let loginForm = shallowRef();
let loginFormData = shallowReactive({username: 'zhouhangyu', password: 'admin'});
let loginFormRules = {username: RULE.USERNAME, password: RULE.PASSWORD};

function login() {
  loginForm.value.validate(valid => {
    if (valid) {
      // 同步发送登录请求
      loginByAccountApi(loginFormData).then(res => {
        let data = getResponseData(res);
        if (data) {
          ElMessage.success('登录成功！');
          let token = data['token'];
          let loginEmp = JSON.stringify(data['emp']);
          vuex.dispatch('setLoginFlag', true);
          sessionStorage.setItem('token', token);
          sessionStorage.setItem('loginEmp', loginEmp);
          router.push('/Main');
        }
      });
    }
  });
}

/* ==================== 重置表单 ==================== */
function resetForm() {
  loginForm.value.resetFields();
}

/* ==================== 忘记密码 ==================== */
function forgetPassword() {
  ElNotification.info('员工测试账号: admin / admin');
}

/* ==================== 加载函数 ==================== */
onMounted(() => {
  sessionStorage.clear();
  vuex.dispatch('setLoginFlag', false);
});
</script>

<template>
  <section class="login-body">
    <el-card class="login-card" :header="PROJECT_INFO.title">
      <el-form class="login-form" ref="loginForm" status-icon :model="loginFormData" :rules="loginFormRules">
        <el-form-item prop="username" required>
          <el-input v-model="loginFormData['username']" suffix-icon="User" clearable placeholder="输入账号 .."/>
        </el-form-item>
        <el-form-item prop="password" required>
          <el-input v-model="loginFormData['password']" suffix-icon="Lock" clearable placeholder="输入密码 .."
                    show-password/>
        </el-form-item>
        <el-button class="login-btn" type="primary" @click="login">员工登录</el-button>
        <el-checkbox class="remember-cbx" label="记住账号" size="small"/>
        <el-button class="forget-btn" link size="small" @click="forgetPassword">忘记密码</el-button>
        <el-button class="reset-btn" link size="small" type="warning" @click="resetForm">重置内容</el-button>
      </el-form>
    </el-card>
  </section>
</template>

<style scoped lang="scss">
.login-body {
  height: 100vh; // 高度
  background: url("../assets/image/loginBackground.png") no-repeat; // 背景图片（不平铺）
  background-size: 100% 100%; // 上下 左右
  padding-top: 200px; // 上内边距
  box-sizing: border-box; // 忽略内边距影响

  .login-card {
    margin: auto; // 自居中
    width: 50vh; // 宽度
    opacity: 0.95; // 透明度
  }

  .login-btn {
    width: 100%; // 宽度
    margin: 0 auto 10px; // 外边距
    letter-spacing: 2px; // 字母间距
  }

  .forget-btn, .reset-btn {
    float: right; // 右浮动
    line-height: 18px; // 行高
  }
}
</style>