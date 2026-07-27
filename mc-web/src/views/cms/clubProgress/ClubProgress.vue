<script setup>
import MyNav from "../../../components/MyNav.vue";
import MyHead from "../../../components/MyHead.vue";
import MyTable from "../../../components/MyTable.vue";
import {ElMessage, ElMessageBox} from "element-plus";
import {onMounted, ref, reactive} from "vue";
import {getResponseData, myPage} from "../../../request";
import {deleteApi, deleteBatchApi, excelApi, listApi, pageApi} from "../../../api/axios";
import {datetimeFormat, isNotNull} from "../../../util/index.js";
import {endClubProgressApi} from "../../../api/clubProgress.js";

// 班级下拉菜单选项
let clubOptions = ref([]);
// 课程下拉菜单选项
let courseOptions = ref([]);
// 路径导航
const navItems = [
  {icon: 'Platform', label: '班级管理'},
  {icon: 'More', label: '班级进度列表'}
];
// 数据头
const headItems = [
  {type: 'opt', span: 5, placeholder: '搜索班级', options: clubOptions.value, callback: pageByClubId},
  {type: 'opt', span: 5, placeholder: '搜索课程', options: courseOptions.value, callback: pageByCourseId},
];
// 表格列
const columns = [
  {label: '状态', prop: 'endTime', format: e => isNotNull(e) ? '已结束' : '', width: 65},
  {label: '班级', prop: 'club.title', width: 100},
  {label: '课程', prop: 'course.title', width: 150},
  {label: '房间', prop: 'room.title'},
  {label: '老师', prop: 'teacher.realname', width: 100},
  {label: '班主任', prop: 'manager.realname', width: 100},
  {label: '助教', prop: 'assistant.realname', width: 100},
  {label: '进度开始时间', prop: 'startTime', format: datetimeFormat},
  {label: '预计结束时间', prop: 'expectedEndTime', format: datetimeFormat},
  {label: '实际结束时间', prop: 'endTime', format: datetimeFormat},
  {label: '班级排序', prop: 'idx', width: 90},
  {label: '班级进度描述', prop: 'info', type: 'card', width: 520},
];
// 按钮列
const buttons = [
  {label: '立刻结束', callback: endClubProgress},
]

/* ==================== 立刻结束 ==================== */

function endClubProgress(row) {
  ElMessageBox.confirm('确认立刻结束该进度吗？').then(() => {
    endClubProgressApi(row['id']).then(res => {
      let data = getResponseData(res);
      if (data) {
        ElMessage.success('班级进度已结束');
        page();
      }
    }).catch(() => {
      ElMessage.info('已取消');
    });
  });
}

/* ==================== 分页查询 ==================== */

// 表格数据 + 分页数据 + 班级Id + 课程Id
let records = ref();
let pageInfo = reactive({pageNum: 1, pageSize: 5, total: 0, callback: page});
let clubId = ref();
let courseId = ref();

async function page(pageNum = pageInfo['pageNum'], pageSize = pageInfo['pageSize']) {
  // 分页参数
  let config = {
    api: pageApi,
    args: {module: 'clubProgress'},
    params: {pageNum, pageSize},
    records, pageInfo
  };
  // 若班级Id不为空，则附加为分页条件
  if (clubId.value) config['params']['fkClubId'] = clubId.value;
  // 若课程Id不为空，则附加为分页条件
  if (courseId.value) config['params']['fkCourseId'] = courseId.value;
  // 发送分页请求
  await myPage(config);
}

/* ==================== 搜索班级 ==================== */

function pageByClubId(val) {
  // 仅当输入框有值，或者当前处于按条件分页状态时，发送分页请求
  if (val || clubId.value) {
    clubId.value = val;
    page();
  }
}

/* ==================== 搜索课程 ==================== */

function pageByCourseId(val) {
  // 仅当输入框有值，或者当前处于按条件分页状态时，发送分页请求
  if (val || courseId.value) {
    courseId.value = val;
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
  excelApi('/clubProgress/excel', '课程进度报表');
}

/* ==================== 加载函数 ==================== */

onMounted(async () => {
  await page();
  // 查询全部课程并添加到下拉菜单选项中
  Object.values(getResponseData(await listApi({module: 'club'}))).forEach(direction => {
    clubOptions.value.push({label: direction['title'], value: direction['id']});
  });
  // 查询全部课程并添加到下拉菜单选项中
  Object.values(getResponseData(await listApi({module: 'course'}))).forEach(direction => {
    courseOptions.value.push({label: direction['title'], value: direction['id']});
  });
});
</script>

<template>
  <my-nav :items="navItems"/>
  <my-head :items="headItems" v-if="clubOptions.length > 0 && courseOptions.length > 0"/>
  <my-table module="clubProgress"
            insert-page="/ClubProgressInsert"
            update-page="/ClubProgressUpdate"
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