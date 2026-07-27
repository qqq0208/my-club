<script setup>
import MyNav from "../../../components/MyNav.vue";
import MyHead from "../../../components/MyHead.vue";
import MyTable from "../../../components/MyTable.vue";
import {onMounted, ref, reactive} from "vue";
import {getResponseData, myPage} from "../../../request";
import {deleteApi, deleteBatchApi, excelApi, listApi, pageApi} from "../../../api/axios";
import {dateFormat, genderFormat, studentStateFormat} from "../../../util/index.js";
import {ElMessage} from "element-plus";
import {PROJECT_INFO, STUDENT_STATUS_OPTIONS} from "../../../const/index.js";

// 学校下拉菜单选项
let schoolOptions = ref([]);
// 班级下拉菜单选项
let clubOptions = ref([]);
// 销售下拉菜单选项
let salesmanOptions = ref([]);
// 路径导航
const navItems = [
  {icon: 'Platform', label: '班级管理'},
  {icon: 'Avatar', label: '学生列表'}
];
// 数据头
const headItems = [
  {type: 'ipt', span: 4, placeholder: '搜索学生编号', callback: pageByNumber},
  {type: 'ipt', span: 4, placeholder: '搜索真实姓名', callback: pageByRealname},
  {type: 'opt', span: 4, placeholder: '搜索所在学校', callback: pageBySchoolId, options: schoolOptions.value},
  {type: 'opt', span: 4, placeholder: '搜索所在班级', callback: pageByClubId, options: clubOptions.value},
  {type: 'opt', span: 4, placeholder: '搜索负责销售', callback: pageBySalesmanId, options: salesmanOptions.value},
  {type: 'opt', span: 4, placeholder: '搜索学生状态', callback: pageByStatus, options: STUDENT_STATUS_OPTIONS},
];
// 表格列
const columns = [
  {label: '状态', prop: 'status', type: 'tag', tagTypeFn: studentStatusTagTypeFn, format: studentStateFormat},
  {label: '学生头像', prop: 'avatar', type: 'img', minio: minio},
  {label: '学生编号', prop: 'number', width: 90},
  {label: '学生姓名', prop: 'realname', width: 90},
  {label: '所在学校', prop: 'school', format: schoolFullTitleFormat, width: 300},
  {label: '所在班级', prop: 'club.title', width: 130},
  {label: '学生性别', prop: 'gender', type: 'tag', tagTypeFn: studentGenderTagTypeFn, format: genderFormat, width: 90},
  {label: '学生描述', prop: 'info', type: 'card'},
  {label: '手机号码', prop: 'phone', width: 90},
  {label: '身份证号', prop: 'idcard', width: 140},
  {label: '微信号码', prop: 'wechat'},
  {label: '学生年龄', prop: 'age', width: 90},
  {label: '所在省份', prop: 'province', width: 90},
  {label: '现居住地', prop: 'address', type: 'card'},
  {label: '入学日期', prop: 'studyDate', type: 'date', format: dateFormat, width: 110},
  {label: '急联姓名', prop: 'emergencyName'},
  {label: '急联电话', prop: 'emergencyPhone'},
  {label: '负责销售', prop: 'salesman.realname', width: 90},
];

/* ==================== 学生状态处理 ==================== */

// 根据学生状态返回标签颜色
function studentStatusTagTypeFn(statusCode) {
  if (statusCode === 0) return 'success';
  if (statusCode === 1) return 'primary';
  if (statusCode === 2) return 'warning';
  if (statusCode === 3) return 'danger';
  return 'default';
}

/* ==================== 学生头像 ==================== */

function minio(src) {
  return PROJECT_INFO.minioHost + '/avatar/' + src;
}

/* ==================== 学校处理 ==================== */

// 拼接完整的学校-学院-专业-班级名称
function schoolFullTitleFormat(school) {
  return school['title'] + school['college'] + school['major'] + school['clazz'];
}

/* ==================== 学生性别处理 ==================== */

// 根据学生性别返回标签颜色
function studentGenderTagTypeFn(genderCode) {
  if (genderCode === 0) return 'danger';
  if (genderCode === 1) return 'primary';
  return 'default';
}

/* ==================== 分页查询 ==================== */

// 表格数据 + 分页数据 + 学生编号 + 真实姓名 + 手机号码 + 身份证号 + 所在学校ID + 所在班级ID
let records = ref();
let pageInfo = reactive({pageNum: 1, pageSize: 5, total: 0, callback: page});
let number = ref();
let realname = ref();
let fkSchoolId = ref();
let fkClubId = ref();
let fkSalesmanId = ref();
let status = ref();

async function page(pageNum = pageInfo['pageNum'], pageSize = pageInfo['pageSize']) {
  // 分页参数
  let config = {
    api: pageApi,
    args: {module: 'student'},
    params: {pageNum, pageSize},
    records, pageInfo
  };
  // 若学生编号不为空，则附加为分页条件
  if (number.value) config['params']['number'] = number.value;
  // 若真实姓名不为空，则附加为分页条件
  if (realname.value) config['params']['realname'] = realname.value;
  // 若所在学校ID不为空，则附加为分页条件
  if (fkSchoolId.value) config['params']['fkSchoolId'] = fkSchoolId.value;
  // 若所在班级ID不为空，则附加为分页条件
  if (fkClubId.value) config['params']['fkClubId'] = fkClubId.value;
  // 若负责销售ID不为空，则附加为分页条件
  if (fkSalesmanId.value) config['params']['fkSalesmanId'] = fkSalesmanId.value;
  // 若学生状态不为空，则附加为分页条件
  if (status.value) config['params']['status'] = status.value;
  // 发送分页请求
  await myPage(config);
}

/* ==================== 搜索学生编号 ==================== */

function pageByNumber(val) {
  // 仅当输入框有值，或者当前处于按条件分页状态时，发送分页请求
  if (val || number.value) {
    number.value = val;
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

/* ==================== 搜索所在学校 ==================== */

function pageBySchoolId(val) {
  // 仅当输入框有值，或者当前处于按条件分页状态时，发送分页请求
  if (val || fkSchoolId.value) {
    fkSchoolId.value = val;
    page();
  }
}

/* ==================== 搜索所在班级 ==================== */

function pageByClubId(val) {
  // 仅当输入框有值，或者当前处于按条件分页状态时，发送分页请求
  if (val || fkClubId.value) {
    fkClubId.value = val;
    page();
  }
}

/* ==================== 搜索负责销售 ==================== */

function pageBySalesmanId(val) {
  // 仅当输入框有值，或者当前处于按条件分页状态时，发送分页请求
  if (val || fkSalesmanId.value) {
    fkSalesmanId.value = val;
    page();
  }
}

/* ==================== 搜索学生状态 ==================== */

function pageByStatus(val) {
  // 若状态为0，则转换为字符串'0'，因为JS视0为false
  if (val === 0) val = '0';
  // 仅当输入框有值，或者当前处于按条件分页状态时，发送分页请求
  if (val || status.value) {
    status.value = val;
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
  excelApi('/student/excel', '学生报表');
}

/* ==================== 加载函数 ==================== */

onMounted(async () => {
  await page();
  // 查询全部学校并添加到下拉菜单选项中
  Object.values(getResponseData(await listApi({module: 'school'}))).forEach(school => {
    schoolOptions.value.push({label: school['title'], value: school['id']});
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
  <my-head :items="headItems" v-if="schoolOptions.length > 0 && clubOptions.length > 0 && salesmanOptions.length > 0"/>
  <my-table module="student"
            insert-page="/StudentInsert"
            update-page="/StudentUpdate"
            :records="records"
            :columns="columns"
            :delete-api="deleteApi"
            :delete-batch-api="deleteBatchApi"
            :delete-callback="deleteSuccess"
            :excel-api="downloadExcel"
            :pageInfo="pageInfo"/>
</template>

<style scoped lang="scss"></style>