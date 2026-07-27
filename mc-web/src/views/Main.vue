<script setup>
import router from "../router";
import MyIcon from "../components/MyIcon.vue";
import {ElNotification} from "element-plus";
import {PROJECT_INFO, PROJECT_SKILLS} from "../const";
import {onMounted, ref, shallowRef} from "vue";
import {listByEmpId} from "../api/menu.js";
import {getResponseData} from "../request";

// 当前登录的员工信息
const loginEmp = JSON.parse(sessionStorage.getItem('loginEmp'));
// 当前登录的员工头像
const avatar = PROJECT_INFO.minioHost + '/avatar/' + loginEmp['avatar'];
// 项目LOGO
const logo = PROJECT_INFO.minioHost + '/logo.jpg';

/* ==================== 功能菜单 ==================== */

// 当前选中菜单的index值：默认选中当前路由路径
let currentMenuIndex = shallowRef(router.currentRoute.value['path']);
// 左侧菜单列表是否折叠：向左收缩
const isCollapse = shallowRef(false);
// 当前登录的员工的菜单列表
const menus = ref();

/* ==================== 项目信息 ==================== */

const projectInfoDrawer = shallowRef();

function openProjectInfoDrawer() {
  projectInfoDrawer.value = true;
}

/* ==================== 项目技术栈 ==================== */

const projectSkillDrawer = shallowRef();

function openProjectSkillDrawer() {
  projectSkillDrawer.value = true;
}

/* ==================== 系统日历 ==================== */

const calendarDrawer = shallowRef();
let calendarData = shallowRef();

function openCalendarDrawer() {
  calendarData.value = new Date();
  calendarDrawer.value = true;
}

/* ==================== 系统通知 ==================== */

function notify() {
  ElNotification.info({title: '通知列表', message: '暂无通知消息', position: 'bottom-right'});
}

/* ==================== 路径跳转 ==================== */

function toPersonal() {
  router.push('/Personal');
}

function toPersonalUpdate() {
  router.push('/PersonalUpdate');
}

/* ==================== 退出登录 ==================== */

function logout() {
  router.push('/');
}

/* ==================== 加载函数 ==================== */

onMounted(() => {

  /**
   * 构建菜单树，每个父菜单都包含 subMenus 子菜单
   *
   * @param menus 菜单列表
   * @returns object[] 菜单树（父菜单数组）
   */
  function buildMenuTree(menus) {
    // 使用 JSON 缓存菜单，格式 {id : menu数据}
    const menuObj = {};
    // 父菜单数组（仅存放 pid 为 0 的菜单）
    const parentMenus = [];

    // 初始化
    menus.forEach(menu => {
      // 缓存全部菜单到 menuObj 中
      menuObj[menu['id']] = menu;
      // 将父菜单分离到父菜单数组 rootMenus 中
      if (menu['pid'] === 0) {
        menu['subMenus'] = [];
        parentMenus.push(menu);
      }
    });

    // 将子菜单关联到父菜单
    menus.forEach(menu => {
      // 仅子菜单需要找到并关联到自己的父菜单
      if (menu['pid'] !== 0) {
        // 从 JSON 中获取父菜单
        const parent = menuObj[menu['pid']];
        // 将自己关联到自己的父菜单
        if (parent) parent['subMenus'].push(menu);
      }
    });

    return parentMenus;
  }

  listByEmpId(loginEmp['id']).then(res => {
    let data = getResponseData(res);
    if (data) menus.value = buildMenuTree(data);
  });
});

</script>

<template>
  <el-container class="main-body" v-if="menus">
    <el-aside class="main-body-left" width="collapse" max-width="200px">
      <el-menu class="menus-menu el-menu-vertical-demo" unique-opened router :collapse="isCollapse" :default-active="currentMenuIndex">
        <el-image class="logo" :src="logo"/>
        <el-menu-item class="house-item" index="/DashBoard" title="回到后台项目首页">
          <my-icon icon="House" label="DashBoard"/>
        </el-menu-item>
        <el-sub-menu class="menus" v-for="(menu, i) in menus" :key="menu['id']" :index="i.toString()" :title="menu['info']">
          <template #title>
            <my-icon :icon="menu['icon']" :label="menu['title']"/>
          </template>
          <el-menu-item class="sub-menus" v-for="subMenu in menu['subMenus']" :key="subMenu['id']" :index="subMenu['url']" :title="subMenu['info']">
            <my-icon :icon="subMenu['icon']" :label="subMenu['title']"/>
          </el-menu-item>
        </el-sub-menu>
      </el-menu>
    </el-aside>
    <el-container class="main-body-right">
      <el-header class="main-body-right-head">
        <el-row class="is-align-middle">
          <el-col class="fold-expand" :span="2">
            <el-radio-group v-model="isCollapse">
              <el-radio-button :label="!isCollapse">
                <my-icon size="20" :icon="!isCollapse ? 'Fold' : 'Expand'"/>
              </el-radio-button>
            </el-radio-group>
          </el-col>
          <el-col class="project-title-col" :span="7">
            <el-popover width="500" :content="PROJECT_INFO.info" placement="bottom-start" trigger="click">
              <template #reference>
                {{ PROJECT_INFO.title }}
              </template>
            </el-popover>
          </el-col>
          <el-col class="operation-btn-col" :span="6" :offset="6">
            <el-divider direction="vertical"/>
            <el-tooltip content="全局搜索">
              <el-button icon="search" size="small" round @click=""/>
            </el-tooltip>
            <el-tooltip content="系统通知">
              <el-button icon="bell" size="small" round @click="notify"/>
            </el-tooltip>
            <el-tooltip content="项目基本信息">
              <el-button icon="list" size="small" round @click="openProjectInfoDrawer"/>
            </el-tooltip>
            <el-tooltip content="项目技术信息">
              <el-button icon="management" size="small" round @click="openProjectSkillDrawer"/>
            </el-tooltip>
            <el-tooltip content="系统日历">
              <el-button icon="calendar" size="small" round @click="openCalendarDrawer"/>
            </el-tooltip>
            <el-divider direction="vertical"/>
          </el-col>
          <el-col class="emp-realname-col" :span="2" v-if="loginEmp['realname']">
            {{ loginEmp['realname'] }}
          </el-col>
          <el-col class="emp-avatar-col" :span="1" v-if="loginEmp['avatar']">
            <el-dropdown trigger="click">
              <span class="el-dropdown-link">
                <el-avatar class="emp-avatar" :size="45" :src="avatar"/>
              </span>
              <template #dropdown>
                <el-dropdown-menu>
                  <el-dropdown-item icon="InfoFilled" @click="toPersonal">查看个人信息</el-dropdown-item>
                  <el-dropdown-item icon="Edit" @click="toPersonalUpdate">修改个人信息</el-dropdown-item>
                  <el-dropdown-item icon="WarnTriangleFilled" @click="logout">
                    <el-text type="danger">退出登录</el-text>
                  </el-dropdown-item>
                </el-dropdown-menu>
              </template>
            </el-dropdown>
          </el-col>
        </el-row>
      </el-header>
      <el-main class="main-body-right-main">
        <router-view/>
      </el-main>
    </el-container>
  </el-container>
  <el-drawer title="项目系统信息" v-model="projectInfoDrawer" size="50%">
    <el-descriptions border column="1">
      <el-descriptions-item v-for="(v, k) in PROJECT_INFO" :key="k" :label="k">
        {{ v }}
      </el-descriptions-item>
    </el-descriptions>
  </el-drawer>
  <el-drawer title="项目技术栈信息" v-model="projectSkillDrawer" size="50%">
    <el-descriptions border column="1">
      <el-descriptions-item v-for="item in PROJECT_SKILLS" :key="item['label']" :label="item['label']">
        {{ item['value'] }}({{item['version']}})
      </el-descriptions-item>
    </el-descriptions>
  </el-drawer>
  <el-drawer title="系统日历" v-model="calendarDrawer" size="50%">
    <el-calendar v-model="calendarData"/>
  </el-drawer>
</template>

<style scoped lang="scss">
.main-body-left {

  height: 100vh; // 高度
  border-right: 1px solid #cccccc; // 右边框

  .logo {
    padding: 10px; // 内边距
  }

  .el-menu-vertical-demo:not(.el-menu--collapse) {
    width: 200px; // 宽度
    height: 100vh; // 高度
    letter-spacing: 2px; // 字间距
  }

  .el-icon {
    margin: 0 10px; // 上下外边距 左右外边距
  }
}

.main-body-right-head {

  .project-title-col {
    font-weight: bolder; // 加粗
    font-size: 1.5rem; // 字号倍率
  }

  .emp-realname-col {
    text-align: right; // 右对齐
    height: 50px; // 高度
    display: inline-block; // 内联块
    text-shadow: 2px 2px 2px gray; // 文字阴影
    line-height: 50px; // 行高
  }

  .emp-avatar-col {
    text-align: right; // 右对齐
  }

  .emp-avatar {
    margin: 10px; // 外边距
    outline: 1px solid #854040; // 边框
    border: 1px solid #854040; // 边框
  }
}
</style>