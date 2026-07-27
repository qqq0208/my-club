<script setup>
import MyNav from "../../../components/MyNav.vue";
import MyHead from "../../../components/MyHead.vue";
import MyTable from "../../../components/MyTable.vue";
import {onMounted, ref, reactive} from "vue";
import {getResponseData, myPage} from "../../../request";
import {deleteApi, deleteBatchApi, excelApi, listApi, pageApi} from "../../../api/axios";
import {ElMessage} from "element-plus";
import {dateFormat, genderFormat} from "../../../util/index.js";
import {PROJECT_INFO} from "../../../const/index.js";
import router from "../../../router/index.js";

// 所在部门下拉菜单选项
let deptOptions = ref([]);
// 路径导航
const navItems = [
  {icon: 'Avatar', label: '用户管理'},
  {icon: 'User', label: '员工列表'}
];
// 数据头
const headItems = [
  {type: 'ipt', span: 5, placeholder: '搜索登录账号', callback: pageByUsername},
  {type: 'ipt', span: 5, placeholder: '搜索手机号码', callback: pageByPhone},
  {type: 'ipt', span: 5, placeholder: '搜索身份证号', callback: pageByIdcard},
  {type: 'ipt', span: 5, placeholder: '搜索真实姓名', callback: pageByRealname},
  {type: 'opt', span: 4, placeholder: '搜索部门', callback: pageByDeptId, options: deptOptions.value},
];
// 表格列
const columns = [
  {label: '员工头像', prop: 'avatar', type: 'img', minio: minio},
  {label: '真实姓名', prop: 'realname', width: 90},
  {label: '所在部门', prop: 'dept.title', width: 130},
  {
    label: '员工性别',
    prop: 'gender',
    type: 'tag',
    tagTypeFn: e => e === 0 ? 'danger' : e === 1 ? 'primary' : 'default',
    format: genderFormat,
    width: 90
  },
  {label: '员工描述', prop: 'info', type: 'card'},
  {label: '登录账号', prop: 'username', width: 130},
  {label: '手机号码', prop: 'phone', width: 90},
  {label: '身份证号', prop: 'idcard', width: 140},
  {label: '微信号码', prop: 'wechat'},
  {label: '电子邮箱', prop: 'email'},
  {label: '员工年龄', prop: 'age', width: 90},
  {label: '所在省份', prop: 'province', width: 90},
  {label: '现居住地', prop: 'address', type: 'card'},
  {label: '入职日期', prop: 'hiredate', type: 'date', format: dateFormat, width: 110},
];
// 按钮列
const buttons = [
  {label: '重设角色', icon: 'Edit', callback: toEmpUpdateRoles},
];

/* ==================== 重设角色 ==================== */

function toEmpUpdateRoles(row) {
  router.push({
    path: '/EmpUpdateRoles', query: {
      empId: row['id'],
      realname: row['realname']
    }
  });
}

/* ==================== 员工头像 ==================== */

function minio(src) {
  return PROJECT_INFO.minioHost + '/avatar/' + src;
}

/* ==================== 分页查询 ==================== */

// 表格数据 + 分页数据 + 登录账号 + 手机号码 + 身份证号 + 真实姓名 + 所在部门ID
let records = ref();
let pageInfo = reactive({pageNum: 1, pageSize: 5, total: 0, callback: page});
let username = ref();
let phone = ref();
let idcard = ref();
let realname = ref();
let fkDeptId = ref();

async function page(pageNum = pageInfo['pageNum'], pageSize = pageInfo['pageSize']) {
  // 分页参数
  let config = {
    api: pageApi,
    args: {module: 'emp'},
    params: {pageNum, pageSize},
    records, pageInfo
  };
  // 若登录账号不为空，则附加为分页条件
  if (username.value) config['params']['username'] = username.value;
  // 若手机号码不为空，则附加为分页条件
  if (phone.value) config['params']['phone'] = phone.value;
  // 若身份证号不为空，则附加为分页条件
  if (idcard.value) config['params']['idcard'] = idcard.value;
  // 若真实姓名不为空，则附加为分页条件
  if (realname.value) config['params']['realname'] = realname.value;
  // 若所在部门ID不为空，则附加为分页条件
  if (fkDeptId.value) config['params']['fkDeptId'] = fkDeptId.value;
  // 发送分页请求
  await myPage(config);
}

/* ==================== 搜索登录账号 ==================== */

function pageByUsername(val) {
  // 仅当输入框有值，或者当前处于按条件分页状态时，发送分页请求
  if (val || username.value) {
    username.value = val;
    page();
  }
}

/* ==================== 搜索手机号码 ==================== */

function pageByPhone(val) {
  // 仅当输入框有值，或者当前处于按条件分页状态时，发送分页请求
  if (val || phone.value) {
    phone.value = val;
    page();
  }
}

/* ==================== 搜索身份证号 ==================== */

function pageByIdcard(val) {
  // 仅当输入框有值，或者当前处于按条件分页状态时，发送分页请求
  if (val || idcard.value) {
    idcard.value = val;
    page();
  }
}

/* ==================== 搜索真实姓名 ==================== */

function pageByRealname(val) {
  // 仅当输入框有值，或者当前处于按条件分页状态时，发送分页请求
  if (val || realname.value) {
    realname.value = val;
    page();
  }
}

/* ==================== 搜索所在部门 ==================== */

function pageByDeptId(val) {
  // 仅当输入框有值，或者当前处于按条件分页状态时，发送分页请求
  if (val || fkDeptId.value) {
    fkDeptId.value = val;
    page();
  }
}

/* ==================== 删除成功回调 ==================== */

function deleteSuccess() {
  ElMessage.success('删除成功！');
  page();
}

/* ==================== 报表打印 ==================== */

function downloadExcel() {
  excelApi('/emp/excel', '员工报表');
}

/* ==================== 加载函数 ==================== */

onMounted(async () => {
  await page();
  // 查询全部部门并添加到下拉菜单选项中
  Object.values(getResponseData(await listApi({module: 'dept'}))).forEach(dept => {
    deptOptions.value.push({label: dept['title'], value: dept['id']});
  });
});
</script>

<template>
  <my-nav :items="navItems"/>
  <my-head :items="headItems" v-if="deptOptions.length > 0"/>
  <my-table module="emp"
            insert-page="/EmpInsert"
            update-page="/EmpUpdate"
            :records="records"
            :columns="columns"
            :buttons="buttons"
            :delete-api="deleteApi"
            :delete-batch-api="deleteBatchApi"
            :delete-callback="deleteSuccess"
            :excel-api="downloadExcel"
            :pageInfo="pageInfo"/>
</template>

<style scoped lang="scss"></style>