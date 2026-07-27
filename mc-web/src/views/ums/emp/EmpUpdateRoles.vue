<script setup>
import MyNav from "../../../components/MyNav.vue";
import {getResponseData} from "../../../request";
import {onMounted, ref} from "vue";
import {listApi} from "../../../api/axios.js";
import {listByEmpIdApi, updateByEmpIdApi} from "../../../api/role.js";
import {ElMessage} from "element-plus";
import router from "../../../router";

// 获取路由中的员工主键和真实姓名
let empId = router.currentRoute.value.query['empId'];
let realname = router.currentRoute.value.query['realname'];
// 路径导航
const navItems = [
  {icon: 'Avatar', label: '用户管理'},
  {icon: 'User', label: '员工列表', url: '/Emp'},
  {icon: 'Edit', label: '为员工重设角色'},
];
// 全部角色 + 我的角色主键数组
let allRoles = ref([]);
let roleIds = ref([]);

/* ==================== 确认修改角色 ==================== */

async function updateRoles() {
  let data = getResponseData(await updateByEmpIdApi(empId, roleIds.value));
  if (data) {
    ElMessage.success('角色重设成功，下次登录生效！');
  }
}

/* ==================== 加载函数 ==================== */


onMounted(async () => {
  // 查询全部角色
  Object.values(getResponseData(await listApi({module: 'role'}))).forEach(role => {
    allRoles.value.push({label: role['title'], key: role['id']});
  });

  // 查询该员工的角色
  Object.values(getResponseData(await listByEmpIdApi(empId))).forEach(role => {
    roleIds.value.push(role['id']);
  });
});

</script>

<template>
  <div class="emp-roles-body">
    <my-nav :items="navItems"/>
    <el-transfer class="emp-roles-transfer"
                 filterable filter-placeholder="输入关键字"
                 v-if="allRoles.length > 0"
                 v-model="roleIds"
                 :data="allRoles"
                 :titles="['全部可选角色', '【' + realname+ '】已选角色']"
                 :props="{key: 'key', label: 'label'}"
                 :button-texts="['移除', '添加']">
      <template #left-footer>
        <el-text class="mx-1" type="info">tips: 请重新选择该员工的角色！</el-text>
      </template>
      <template #right-footer>
        <el-button type="primary" @click="updateRoles" size="small">确认修改</el-button>
      </template>
    </el-transfer>
  </div>
</template>

<style scoped lang="scss">
.emp-roles-body {
  text-align: center; // 内容居中

  .emp-roles-transfer {
    margin-top: 65px; // 上外边距
  }
}

:deep(.el-transfer-panel) {
  width: 300px; // 宽度
}

:deep(.el-transfer-panel__body) {
  height: 400px; // 高度
}

:deep(.el-transfer-panel__footer) {
  text-align: center; // 内容居中
}
</style>