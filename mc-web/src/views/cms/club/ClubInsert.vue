<script setup>
import MyForm from "../../../components/MyForm.vue";
import MyNav from "../../../components/MyNav.vue";
import {onMounted, reactive, ref} from "vue";
import {insertApi, listApi} from "../../../api/axios.js";
import {getResponseData} from "../../../request/index.js";
import {CLUB_TAG_OPTIONS, RULE} from "../../../const";
import {ElMessage} from "element-plus";
import router from "../../../router";

// 所在方向下拉菜单选项
let directionOptions = ref([]);
// 路径导航
const navItems = [
  {icon: 'Platform', label: '班级管理'},
  {icon: 'Platform', label: '班级列表', url: '/Club'},
  {icon: 'Plus', label: '添加新班级'},
]
// 表单项 + 表单值 + 表单规则
let items = ref([
  {label: '班级标题', prop: 'title', required: true, span: 12},
  {label: '班级方向', prop: 'fkDirectionId', type: 'select', options: directionOptions.value, required: true, span: 12},
  {label: '开班时间', prop: 'startTime', type: 'datetime', required: true, span: 12},
  {label: '预计结班时间', prop: 'expectedEndTime', type: 'datetime', required: true, span: 12},
  {label: '班级标签', prop: 'tag', type: 'select', options: CLUB_TAG_OPTIONS, multiple: true, required: true},
  {label: '班级描述', prop: 'info', type: 'textarea'},
]);
let params = reactive({});
let rules = {title: RULE.TITLE, info: RULE.INFO};

/* ==================== 添加成功后 ==================== */

function insertSuccess() {
  ElMessage.success('添加成功！');
  setTimeout(() => router.push('/Club'), 1000);
}

/* ==================== 加载函数 ==================== */

onMounted(async () => {
  // 查询全部方向并添加到下拉菜单选项中
  Object.values(getResponseData(await listApi({module: 'direction'}))).forEach(direction => {
    directionOptions.value.push({label: direction['title'], value: direction['id']});
  });
});
</script>

<template>
  <my-nav :items="navItems"/>
  <el-card class="club-insert-card" header="添加新班级">
    <my-form v-if="directionOptions.length > 0"
             type="insert"
             :items="items"
             :params="params"
             :rules="rules"
             :api="insertApi"
             :args="{module: 'club'}"
             :callback="insertSuccess"/>
  </el-card>
</template>

<style scoped lang="scss">
.club-insert-card {
  width: 60%; // 宽度
  margin: 65px auto 0; // 外边距
}
</style>