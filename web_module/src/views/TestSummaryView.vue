<template>
  <div class="common-layout">
    <Head/>
    <!--正文-->
    <el-main>
      <h1>{{ test.name }}</h1>
      <div id="test-tabs-div">
        <el-tabs id="tab" :tab-position="'left'" class="left-tabs" v-model="curTab">
          <el-tab-pane name="summary" label="概述">
            <div class="space-between">
              <span id="test-info">
                <span class="gray-tip">{{ test.publisher }}</span>
                <span class="gray-tip">试卷ID：{{ test.testId }}</span>
              </span>
              <el-button v-if="isPast()" type="danger" size="large">已关闭</el-button>
              <el-button v-else-if="isNotOpenTime()" type="danger" size="large">未到开放时间</el-button>
              <el-button v-else-if="userTestInfo.triedTime === '0'" class="countryRed" type="danger" size="large">答题
              </el-button>
              <el-button v-else-if="userTestInfo.isScoring === 'true'" type="success" size="large">已提交</el-button>
              <el-button v-else-if="userTestInfo.triedTime >= test.testTime" type="success" size="large">已完成
              </el-button>
              <el-button v-else class="countryRed" type="danger" size="large" @click="goTest">再次挑战</el-button>
            </div>
            <div>
              <div class="sub-title">详情</div>
              <div id="test-detail">{{ test.detail }}</div>
            </div>
            <div class="space-between">
              <span class="sub-title">
                难度：<span class="prop-value">{{ test.difficulty }}</span>
              </span>
              <span class="sub-title">
                参加人数：<span class="prop-value">{{ test.studyNum }}</span>
              </span>
              <span class="sub-title">
                平均成绩：<span class="prop-value">{{ test.aveGrade }}</span>
              </span>
            </div>
            <div class="space-between">
              <span class="sub-title">
                答题开放时间：<span class="prop-value">{{ test.beginTime }}-{{ test.endTime }}</span>
              </span>
              <span class="sub-title">
                可答次数：<span class="prop-value">{{ test.testTime }}</span>
              </span>
            </div>
          </el-tab-pane>
          <el-tab-pane name="discussion" label="讨论">讨论</el-tab-pane>
          <el-tab-pane name="rank" label="排行">排行</el-tab-pane>
        </el-tabs>
      </div>
    </el-main>
  </div>
</template>

<script setup>
import {reactive, ref} from 'vue'
import Head from "@/components/Head.vue";

const curTab = ref('summary')

const test = reactive({
  testId: '1523',
  name: 'MySQL 查询优化练习',
  difficulty: 'hard',
  publisher: '高老师',
  studyNum: '45234',
  detail: 'MySQL的性能优化非常广泛，包括索引优化，查询优化，查询缓存，服务器设置优化，操作系统和硬件优化，应用层面优化等等。MySQL的性能优化非常广泛，包括索引优化，查询优化，查询缓存，服务器设置优化，操作系统和硬件优化，应用层面优化等等',
  aveGrade: '74.66',
  beginTime: '2023/3/26',
  endTime: '2023/4/5',
  testTime: '4'
})

const userTestInfo = reactive({
  userId: '801523',
  historyTop: '95',
  triedTime: '3',
  isScoring: 'false'
})

const userInfo = reactive({
  userId: '801523',
  name: '小明'
});

document.title = test.name;

function isPast() {
  let end = new Date(test.endTime);
  end.setTime(end.getTime() + 86400000);
  return new Date().getTime() > end.getTime();
}

function isNotOpenTime() {
  return new Date().getTime() < new Date(test.beginTime).getTime();
}

function goTest() {
  location.href = '/test/' + test.testId + '/testing';
}
</script>

<style>
.el-main {
  display: flex;
  align-items: center;
  flex-direction: column;
}

#test-tabs-div {
  display: flex;
  flex-direction: row;
  align-items: stretch;
  margin-top: 20px;
}

/*tab项*/
.left-tabs .el-tabs__item {
  color: #464544;
  font-size: 20px;
}

/*tab选中*/
.left-tabs .is-active {
  background: linear-gradient(280deg, #ababab 25%, #ffffff);
}

/*tabs内容*/
.el-tabs__content {
  width: 700px;
  padding-left: 10px;
}

/*概述*/
.space-between {
  display: flex;
  justify-content: space-between;
}

#test-info {
  display: flex;
  justify-content: space-between;
  width: 200px;
}

.el-tab-pane .el-button {
  font-size: 25px;
  justify-content: center;
  padding: 3px;
}

#test-info {
  font-size: 15px;
  color: gray;
}

.sub-title {
  color: black;
  font-size: 22px;
  font-weight: bold;
  margin-bottom: 15px;
}

.prop-value {
  font-size: 20px;
  font-weight: normal;
}

#test-detail {
  color: black;
  font-size: 18px;
  margin-bottom: 15px;
}
</style>