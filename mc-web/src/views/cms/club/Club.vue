<script setup>
import MyNav from "../../../components/MyNav.vue";
import MyHead from "../../../components/MyHead.vue";
import MyTable from "../../../components/MyTable.vue";
import {ElMessage, ElMessageBox} from "element-plus";
import {onMounted, ref, reactive} from "vue";
import {getResponseData, myPage} from "../../../request";
import {deleteApi, deleteBatchApi, excelApi, listApi, pageApi} from "../../../api/axios";
import {datetimeFormat, isNotNull} from "../../../util/index.js";
import {endClubApi} from "../../../api/club.js";

// 所在方向下拉菜单选项
let directionOptions = ref([]);
// 路径导航
const navItems = [
  {icon: 'Platform', label: '班级管理'},
  {icon: 'Platform', label: '班级列表'}
];
// 数据头
const headItems = [
  {type: 'ipt', span: 5, placeholder: '搜索班级标题', callback: pageByTitle},
  {type: 'ipt', span: 5, placeholder: '搜索班级标签', callback: pageByTag},
  {type: 'opt', span: 5, placeholder: '搜索班级方向', callback: pageByDirectionId, options: directionOptions.value},
];
// 表格列
const columns = [
  {label: '状态', prop: 'endTime', format: e => isNotNull(e) ? '已结班' : '', width: 65},
  {label: '班级标题', prop: 'title', width: 120},
  {label: '班级标签', prop: 'tag', type: 'tag', width: 400},
  {label: '班级方向', prop: 'direction.title', width: 120},
  {label: '开班时间', prop: 'startTime', format: datetimeFormat},
  {label: '预计结班时间', prop: 'expectedEndTime', format: datetimeFormat},
  {label: '实际结班时间', prop: 'endTime', format: datetimeFormat},
  {label: '班级描述', prop: 'info', type: 'card', width: 520},
];
// 按钮列
const buttons = [
  {label: '立刻结班', callback: endClub},
]

/* ==================== 立刻结班 ==================== */

function endClub(row) {
  ElMessageBox.confirm('即将结班，确认吗？').then(() => {
    endClubApi(row['id']).then(res => {
      let data = getResponseData(res);
      if (data) {
        ElMessage.success('结班成功');
        page();
      }
    }).catch(() => {
      ElMessage.info('已取消');
    });
  });
}

/* ==================== 分页查询 ==================== */

// 表格数据 + 分页数据 + 班级标题 + 班级标签 + 班级方向
let records = ref();
let pageInfo = reactive({pageNum: 1, pageSize: 5, total: 0, callback: page});
let clubTitle = ref();
let clubTag = ref();
let directionId = ref();

async function page(pageNum = pageInfo['pageNum'], pageSize = pageInfo['pageSize']) {
  // 分页参数
  let config = {
    api: pageApi,
    args: {module: 'club'},
    params: {pageNum, pageSize},
    records, pageInfo
  };
  // 若班级标题不为空，则附加为分页条件
  if (clubTitle.value) config['params']['title'] = clubTitle.value;
  // 若班级标签不为空，则附加为分页条件
  if (clubTag.value) config['params']['tag'] = clubTag.value;
  // 若班级方向不为空，则附加为分页条件
  if (directionId.value) config['params']['fkDirectionId'] = directionId.value;
  // 发送分页请求
  await myPage(config);
}

/* ==================== 搜索班级标题 ==================== */

function pageByTitle(val) {
  // 仅当输入框有值，或者当前处于按条件分页状态时，发送分页请求
  if (val || clubTitle.value) {
    clubTitle.value = val;
    page();
  }
}

/* ==================== 搜索班级标签 ==================== */

function pageByTag(val) {
  // 仅当输入框有值，或者当前处于按条件分页状态时，发送分页请求
  if (val || clubTag.value) {
    clubTag.value = val;
    page();
  }
}

/* ==================== 搜索班级方向 ==================== */

function pageByDirectionId(val) {
  // 仅当输入框有值，或者当前处于按条件分页状态时，发送分页请求
  if (val || directionId.value) {
    directionId.value = val;
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
  excelApi('/club/excel', '班级报表');
}

/* ==================== 加载函数 ==================== */

onMounted(async () => {
  await page();
  // 查询全部方向并添加到下拉菜单选项中
  Object.values(getResponseData(await listApi({module: 'direction'}))).forEach(direction => {
    directionOptions.value.push({label: direction['title'], value: direction['id']});
  });
});
</script>

<template>
  <my-nav :items="navItems"/>
  <my-head :items="headItems" v-if="directionOptions.length > 0"/>
  <my-table module="club"
            insert-page="/ClubInsert"
            update-page="/ClubUpdate"
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