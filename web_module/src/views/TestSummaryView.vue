<!--suppress JSValidateTypes -->
<template>
  <div class="common-layout">
    <CountryHead @updateUserInfo="updateUser" :userName="userInfo.userName"/>
    <!--正文-->
    <el-main>
      <h1>{{ test.testName }}</h1>
      <div id="test-tabs-div">
        <el-tabs id="tab" :tab-position="'left'" class="left-tabs" v-model="curTab">
          <el-tab-pane name="summary" label="概述">
            <div class="space-between">
              <span id="test-info">
                <span class="gray-tip">{{ test.publisher }}</span>
                <span class="gray-tip">试卷ID：{{ test.testId }}</span>
              </span>
              <div id="but-div">
                <el-button v-if="isClosed()" type="danger" size="large" disabled>已关闭</el-button>
                <el-button v-else-if="isNotOpen()" type="danger" size="large" disabled>未到开放时间</el-button>
                <el-button v-else-if="testUserInfo.isScoring === 0" type="success" size="large" disabled>已提交
                </el-button>
                <el-button v-else-if="testUserInfo.tryTime === 0||test.tryLimit===-1"
                           class="countryRed" type="danger" size="large" @click="beginTest">答题
                </el-button>
                <el-button v-else-if="testUserInfo.tryTime >= test.tryLimit" type="success" size="large" disabled>已完成
                </el-button>
                <el-button v-else class="countryRed" type="danger" size="large" @click="beginTest">再次挑战</el-button>
                <div v-if="testUserInfo.bestGrade!==undefined">历史最高分：{{ testUserInfo.bestGrade }}</div>
              </div>
            </div>
            <div>
              <div class="sub-title">详情</div>
              <div id="test-detail">{{ test.description }}</div>
            </div>
            <div class="space-between">
              <span class="sub-title">难度：
                  <span class="prop-value" v-if="test.difficulty==='easy'">简单</span>
                  <span class="prop-value" v-if="test.difficulty==='middle'">中等</span>
                  <span class="prop-value" v-if="test.difficulty==='hard'">困难</span>
              </span>
              <span class="sub-title">
                参加人数：<span class="prop-value">{{ test.studyNum }}</span>
              </span>
            </div>
            <div class="space-between">
              <span class="sub-title">
                答题开放时间：<span class="prop-value">
                {{ test.openTime }} 至 {{ test.endTime }}
              </span>
              </span>
              <span class="sub-title">
                可答次数：
                <span v-if="test.tryLimit === -1" class="prop-value">无限制</span>
                <span v-else class="prop-value">{{ test.tryLimit }}</span>
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
import CountryHead from "@/components/CountryHead.vue";
import axios from "axios";
import {ElMessage} from "element-plus";

//当前所在标签
const curTab = ref('summary')
//试卷信息
const test = reactive({
  testId: undefined,
  testName: undefined,
  difficulty: undefined,
  publisher: undefined,
  studyNum: undefined,
  description: undefined,
  openTime: undefined,
  endTime: undefined,
  tryLimit: undefined,
  status: undefined
})

//用户试卷信息
const testUserInfo = reactive({
  userId: undefined,
  testId: undefined,
  bestGrade: undefined,
  tryTime: undefined,
  isScoring: undefined
})

//用户信息
const userInfo = reactive({
  userId: undefined,
  userName: undefined
});

//获取答题信息
function loadTestInfo() {
  test.testId = location.pathname.split('/')[2];
  testUserInfo.testId = test.testId;
  axios.get('getTestInfo?testId=' + test.testId, {baseURL: TEST_BASE_URL})
      .then(response => {
        if (response.data.status === 0) {
          test.testId = response.data.object.testId;
          test.testName = response.data.object.testName;
          //设置页面标签名
          document.title = test.testName;
          test.difficulty = response.data.object.difficulty;
          test.publisher = response.data.object.publisher;
          test.studyNum = response.data.object.studyNum;
          test.description = response.data.object.description;
          let timeStamp = new Date(response.data.object.openTimeStamp);
          test.openTime = timeStamp.getFullYear() + '/' + (timeStamp.getMonth() + 1) + '/' + timeStamp.getDate() + ' ' + formatDate(timeStamp);
          timeStamp = new Date(response.data.object.endTimeStamp);
          test.endTime = timeStamp.getFullYear() + '/' + (timeStamp.getMonth() + 1) + '/' + timeStamp.getDate() + ' ' + formatDate(timeStamp);
          test.tryLimit = response.data.object.tryLimit;
          test.status = response.data.status;
        } else {
          ElMessage({message: '获取试卷信息失败:' + response.data.info, type: 'error'})
        }
      })
      .catch(reason => {
        ElMessage({message: '获取试卷信息异常:' + reason, type: "error"})
      });
}

//格式化（答题开放）时间：将3:5格式化为03:05
function formatDate(timeStamp) {
  let timeString = timeStamp.getHours();
  //格式化小时
  if (timeString < 10) {
    timeString = '0' + timeStamp.getHours();
  }
  //格式化分钟
  if (timeStamp.getMinutes() < 10) {
    return timeString + ':0' + timeStamp.getMinutes();
  } else {
    return timeString + ':' + timeStamp.getMinutes();
  }
}

//获取用户答题信息
function loadTestUserResult() {
  axios.get('/getTestUserResult', {
    params: {userId: userInfo.userId, testId: test.testId}
    , baseURL: TEST_BASE_URL
  })
      .then(response => {
        if (response.data.status === 0) {
          testUserInfo.isScoring = response.data.object.isScoring;
          testUserInfo.tryTime = response.data.object.tryTime;
          testUserInfo.bestGrade = response.data.object.bestGrade;
        } else if (response.data.info === '无记录') {
          testUserInfo.tryTime = 0;
          testUserInfo.isScoring = 1;
        }
      })
      .catch(reason => ElMessage({message: '获取用户答题信息异常：' + reason, type: 'error'}));
}

//更新用户信息（登录后触发）
function updateUser(newUserInfo) {
  //更新 userInfo
  userInfo.userId = newUserInfo.userId;
  userInfo.userName = newUserInfo.userName;
  //更新 userTestInfo
  testUserInfo.userId = newUserInfo.userId;
  loadTestUserResult();
}

function isClosed() {
  return new Date().getTime() > new Date(test.endTime).getTime() || test.status === 'closed';
}

function isNotOpen() {
  return new Date().getTime() < new Date(test.openTime).getTime();
}

function beginTest() {
  location.href = '/test/' + test.testId + '/testing';
}

loadTestInfo();
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