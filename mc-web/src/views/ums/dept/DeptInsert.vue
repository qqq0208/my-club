<script setup>
import MyForm from "../../../components/MyForm.vue";
import MyNav from "../../../components/MyNav.vue";
import {onMounted, reactive, ref} from "vue";
import {insertApi, listApi} from "../../../api/axios.js";
import {getResponseData} from "../../../request/index.js";
import {RULE} from "../../../const";
import {ElMessage} from "element-plus";
import router from "../../../router";

// 所在房间下拉菜单选项
let roomOptions = ref([]);
// 路径导航
const navItems = [
  {icon: 'Avatar', label: '用户管理'},
  {icon: 'HomeFilled', label: '部门列表', url: '/Dept'},
  {icon: 'Plus', label: '添加新部门'},
]
// 表单项 + 表单值 + 表单规则
let items = ref([
  {label: '部门名称', prop: 'title', required: true, span: 12},
  {span: 12, hidden: true},
  {label: '所在房间', prop: 'fkRoomId', required: true, type: 'select', options: roomOptions.value, span: 12},
  {label: '部门描述', prop: 'info', type: 'textarea'},
]);
let params = reactive({});
let rules = {title: RULE.TITLE, info: RULE.INFO};

/* ==================== 添加成功后 ==================== */

function insertSuccess() {
  ElMessage.success('添加记录成功！');
  setTimeout(() => router.push('/Dept'), 1000);
}

/* ==================== 加载函数 ==================== */

onMounted(async () => {
  // 查询全部房间并添加到下拉菜单选项中
  Object.values(getResponseData(await listApi({module: 'room'}))).forEach(room => {
    roomOptions.value.push({label: room['title'], value: room['id']});
  });
});

</script>

<template>
  <my-nav :items="navItems"/>
  <el-card v-if="roomOptions.length > 0" class="dept-insert-card" header="添加新部门">
    <my-form type="insert"
             :items="items"
             :params="params"
             :rules="rules"
             :api="insertApi"
             :args="{module: 'dept'}"
             :callback="insertSuccess"/>
  </el-card>
</template>

<style scoped lang="scss">
.dept-insert-card {
  width: 60%; // 宽度
  margin: 65px auto 0; // 外边距
}
</style>