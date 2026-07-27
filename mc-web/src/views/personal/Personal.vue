<script setup>
import {PROJECT_INFO} from "../../const";
import {dateFormat, genderFormat} from "../../util";
import MyNav from "../../components/MyNav.vue";

/* ==================== 员工详情 ==================== */
// 当前登录的员工信息
const loginEmp = JSON.parse(sessionStorage.getItem('loginEmp'));
// 当前登录的员工头像
const avatar = PROJECT_INFO.minioHost + '/avatar/' + loginEmp['avatar'];

// 路径导航
const navItems = [
  {icon: 'House', label: 'DashBoard', url: '/DashBoard'},
  {icon: 'View', label: '个人信息'},
];

</script>

<template>
  <my-nav :items="navItems"/>
  <el-divider/>
  <div class="personal-body">
    <el-row :gutter="20">
      <el-col :span="6">
        <el-image class="avatar" :src="avatar"></el-image>
        <el-divider/>
        <el-descriptions column="1">
          <el-descriptions-item label="登录账号">{{ loginEmp['username'] }}</el-descriptions-item>
          <el-descriptions-item label="真实姓名">{{ loginEmp['realname'] }}</el-descriptions-item>
          <el-descriptions-item label="入职时间">{{ dateFormat(loginEmp['hiredate']) }}</el-descriptions-item>
          <el-descriptions-item label="创建时间">{{ dateFormat(loginEmp['created']) }}</el-descriptions-item>
          <el-descriptions-item label="修改时间">{{ dateFormat(loginEmp['updated']) }}</el-descriptions-item>
        </el-descriptions>
      </el-col>
      <el-col :span="18">
        <el-descriptions title="当前员工详细信息" border column="2">
          <el-descriptions-item label="所属部门" width="50">{{ loginEmp['dept'] ? loginEmp['dept']['title'] : '暂未分配部门' }}</el-descriptions-item>
          <el-descriptions-item label="员工年龄" width="50">{{ loginEmp['age'] }}</el-descriptions-item>
          <el-descriptions-item label="员工性别">{{ genderFormat(loginEmp['gender']) }}</el-descriptions-item>
          <el-descriptions-item label="所属省份">{{ loginEmp['province'] }}</el-descriptions-item>
          <el-descriptions-item label="手机号码">{{ loginEmp['phone'] }}</el-descriptions-item>
          <el-descriptions-item label="微信号码">{{ loginEmp['wechat'] }}</el-descriptions-item>
          <el-descriptions-item label="电子邮件">{{ loginEmp['email'] }}</el-descriptions-item>
          <el-descriptions-item label="身份证号">{{ loginEmp['idcard'] }}</el-descriptions-item>
          <el-descriptions-item label="现居住地" :span="2">
            <el-card>{{ loginEmp['address'] }}</el-card>
          </el-descriptions-item>
          <el-descriptions-item label="员工描述" :span="2">
            <el-card style="height: 200px">{{ loginEmp['info'] }}</el-card>
          </el-descriptions-item>
        </el-descriptions>
      </el-col>
    </el-row>
  </div>
</template>

<style scoped lang="scss">
.personal-body {
  margin: 20px auto 0; // 20px上外边距，自动水平居中

  .avatar {
    width: 278px; // 宽度
    height: 278px; // 高度
    border-radius: 10%; // 圆角
  }
}
</style>