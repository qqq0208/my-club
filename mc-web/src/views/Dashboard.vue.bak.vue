<!--/views/echarts/MyChartsTest.vue-->

<script setup>
import {onMounted, ref} from "vue";
import {bar, line, pie, point} from "../echarts";
import MyIcon from "../components/MyIcon.vue";
import MyNav from "../components/MyNav.vue";

// 路径导航
const navItems = [
  {icon: 'House', label: 'DashBoard'},
];


onMounted(() => {

  pie({
    dom: document.getElementById('mainA'),
    data: [
      {name: '男', value: 50},
      {name: '女', value: 20},
      {name: '保密', value: 20},
    ],
    name: '学员性别分布',
    unit: '人'
  });

  bar({
    dom: document.getElementById('mainB'),
    xData: ['01班', '02班', '03班', '04班', '05班', '06班', '07班', '08班','01班', '02班', '03班', '04班', '05班', '06班', '07班', '08班'],
    yData: [5, 20, 36, 10, 10, 20, 11, 12, 5, 20, 36, 10, 10, 20, 11, 12],
    name: '学员人数',
    xName: '班级名称',
    yName: '学员人数'
  });

  line({
    dom: document.getElementById('mainC'),
    xData: ['衬衫', '羊毛衫', '雪纺衫', '裤子', '高跟鞋', '袜子'],
    yData: [5, 20, 36, 10, 10, 20],
    name: '销量',
    xName: '商品名称',
    yName: '销量'
  });

  point({
    dom: document.getElementById('mainD'),
    xData: ['衬衫', '羊毛衫', '雪纺衫', '裤子', '高跟鞋', '袜子'],
    yData: [5, 20, 36, 10, 10, 20],
    name: '销量',
    xName: '商品名称',
    yName: '销量'
  });
});

let activeTab = ref('first');

</script>

<template>
  <my-nav :items="navItems"/>
  <el-row :gutter="20" style="margin-top: 20px">
    <el-col :span="6" v-for="item in 2" :key="item">
      <div class="statistic-card">
        <el-statistic :value="98500" title="今日新增用户数"/>
        <div class="statistic-footer">
          <div class="footer-item">
            <span>than yesterday</span>
            <span class="green">
              <my-icon label="24%" icon="CaretTop" size="12"/>
            </span>
          </div>
        </div>
      </div>
    </el-col>
    <el-col :span="6" v-for="item in 2" :key="item">
      <div class="statistic-card">
        <el-statistic :value="98500" title="今日新增用户数"/>
        <div class="statistic-footer">
          <div class="footer-item">
            <span>than yesterday</span>
            <span class="red">
              <my-icon label="24%" icon="CaretBottom" size="12"/>
            </span>
          </div>
        </div>
      </div>
    </el-col>
  </el-row>
  <el-tabs v-model="activeTab" type="border-card" style="margin-top:20px;" >
    <el-tab-pane label="班级人数分布" name="first" style="margin-left: 0">
      <div id="mainB" class="main"/>
    </el-tab-pane>
    <el-tab-pane label="学员性别分布" name="second">
      <div id="mainA" class="main"/>
    </el-tab-pane>
    <el-tab-pane label="折线图" name="third">
      <div id="mainC" class="main"/>
    </el-tab-pane>
    <el-tab-pane label="散点图" name="fourth">
      <div id="mainD" class="main"/>
    </el-tab-pane>
  </el-tabs>
</template>

<style scoped lang="scss">
.main {
  width: 80vw; // 宽度
  height: 400px; // 高度
  margin: auto; // 居中
}


.el-statistic {
  --el-statistic-content-font-size: 28px;
}

.statistic-card {
  padding: 20px;
  border-radius: 4px;
  background-color: var(--el-bg-color-overlay);
}

.statistic-footer {
  display: flex;
  justify-content: space-between;
  align-items: center;
  flex-wrap: wrap;
  font-size: 12px;
  color: var(--el-text-color-regular);
  margin-top: 16px;
}

.statistic-footer .footer-item {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.statistic-footer .footer-item span:last-child {
  display: inline-flex;
  align-items: center;
  margin-left: 4px;
}

.green {
  color: var(--el-color-success);
}

.red {
  color: var(--el-color-error);
}
</style>