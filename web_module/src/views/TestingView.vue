<template>
  <div class="common-layout">
    <CountryHead @updateUserInfo="updateUser" :userName="userInfo.userName"/>
    <!--正文-->
    <el-main>
      <div>
        <h1>{{ testInfo.name }}</h1>
        <el-button type="success" @click="submitTest()">提交</el-button>
      </div>
      <div id="test-tabs-div">
        <el-tabs id="tab" :tab-position="'left'" class="left-tabs" v-model="curTab">
          <!--选择题-->
          <el-tab-pane name="select" label="选择题" v-if="selectProblem.data!==undefined">
            <div v-for="(question,index) in selectProblem.data">
              <div>{{ question.id }}、{{ question.title }}</div>
              <el-radio-group v-model="selectAnswer.answer[index]" class="ml-4">
                <el-radio size="large" v-for="(option,index) in question.options" :label="index">{{ option }}
                </el-radio>
              </el-radio-group>
            </div>
          </el-tab-pane>

          <!--填空题-->
          <el-tab-pane name="complete" label="填空题" v-if="completeProblem.data!==null">
            <div v-for="(question,index) in completeProblem.data">
              <div>{{ question.id }}、{{ question.title }}</div>
            </div>
          </el-tab-pane>

          <!--判断题-->
          <el-tab-pane name="judge" label="判断题" v-if="judgeProblem.data!==undefined">
            <div v-for="(question,index) in judgeProblem.data">
              <div>{{ question.id }}、{{ question.title }}</div>
              <el-radio-group v-model="judgeAnswer.answer[index]" class="ml-4">
                <el-radio size="large" label="true"><img src="@/assets/true.png" alt="true"></el-radio>
                <el-radio size="large" label="false"><img src="@/assets/false.png" alt="false"></el-radio>
              </el-radio-group>
            </div>
          </el-tab-pane>

          <!--综合题-->
          <el-tab-pane name="comprehension" label="综合题" v-if="comprehensionProblem.data!==undefined">
            <div v-for="(question,index) in comprehensionProblem.data">
              <div>{{ question.id }}、{{ question.title }}</div>
              <el-input class="multy-text" v-model="comprehensionAnswer.answer[index]"
                        :autosize="{ minRows: 3, maxRows: 6 }"
                        type="textarea" placeholder="在这里输入你的答案"/>
            </div>
          </el-tab-pane>
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

const curTab = ref('select');

const testInfo = reactive({
  testId: '5124963',
  name: 'MySQL 查询优化练习',
  existSelect: 'true',
  existComplete: 'false',
  existJudge: 'true',
  existComprehension: 'true',
  endDate: '2023/4/5',
  endTime: '22:45'
})
document.title = '考试中-' + testInfo.name;

const userInfo = reactive({
  userId: '801523',
  name: '小明'
})

// 题目
let selectProblem = reactive({
  data: [
    {id: '1', title: 'MySQL中，开启慢查询日志需要设置的配置项是？', options: ['slow_query_log', 'slow_qu']},
    {
      id: '2',
      title: 'MySQL中，开启慢查询日志需要设置的配置项是？',
      options: ['slow_query_log', 'slow_qu', 'sl']
    },
    {
      id: '3',
      title: 'MySQL中，开启慢查询日志需要设置的配置项是？',
      options: ['slow_query_log', 'slow_qu', 'sl', 'slow_query_lo']
    }
  ]
})
let completeProblem = reactive({
  data: [
    {id: '1', title: 'MySQL中，开启慢查询日志需要设置的配置项是{}。'},
    {id: '2', title: 'MySQL中，开启慢查询日志需要设置的配置项是{}。'},
    {id: '3', title: 'MySQL中，开启慢查询日志需要设置的配置项是{}。'},
    {id: '4', title: 'MySQL中，开启慢查询日志需要设置的配置项是{}。'},
    {id: '5', title: 'MySQL中，开启慢查询日志需要设置的配置项是{}。'}
  ]
})
let judgeProblem = reactive({
  data: [
    {id: '1', title: 'MySQL中，开启慢查询日志需要设置的配置项是slow_query_log。'},
    {id: '2', title: 'MySQL中，开启慢查询日志需要设置的配置项是slow_query_log。'},
    {id: '3', title: 'MySQL中，开启慢查询日志需要设置的配置项是slow_query_log。'},
    {id: '4', title: 'MySQL中，开启慢查询日志需要设置的配置项是slow_query_log。'}
  ]
})
let comprehensionProblem = reactive({
  data: [
    {id: '1', title: 'MySQL中，开启慢查询日志需要设置的配置项是什么？'},
    {id: '2', title: 'MySQL中，开启慢查询日志需要设置的配置项是什么？'}
  ]
})
//考生答案
let selectAnswer = reactive({answer: []});
let judgeAnswer = reactive({answer: []});
let completeAnswer = reactive({answer: []});
let comprehensionAnswer = reactive({answer: []});

//更新用户信息（登录后触发）
function updateUser(newUserInfo) {
  //更新 userInfo
  userInfo.userId = newUserInfo.userId;
  userInfo.userName = newUserInfo.userName;
}

//获取试题
function getTestQuestion() {
  testInfo.testId = location.pathname.split('/')[2];
  axios.get('getTestQuestion', {
    baseURL: TEST_BASE_URL,
    params: {testId: testInfo.testId}
  })
      .then(response => {
        if (response.data.status === 0) {
          let data = response.data.object;
          selectProblem.data = JSON.parse(data.selectQuestions);
          judgeProblem.data = JSON.parse(data.judgeQuestions);
          completeProblem.data = JSON.parse(data.completeQuestions);
          comprehensionProblem.data = JSON.parse(data.comprehensionQuestions);
        }
      })
      .catch(reason => ElMessage({message: '获取试题异常：' + reason, type: 'error'}))
}

//提交答案
function submitTest() {
  axios.post('submitAnswer', {
    testId: testInfo.testId,
    userId: userInfo.userId,
    userSelectAnswer: JSON.stringify(selectAnswer.answer),
    userJudgeAnswer: JSON.stringify(judgeAnswer.answer),
    userCompleteAnswer: JSON.stringify(completeAnswer.answer),
    userComprehensionAnswer: JSON.stringify(comprehensionAnswer.answer),
  }, {baseURL: TEST_BASE_URL})
      .then(response => {
        if (response.data.status === 0) {
          ElMessage({message: '提交成功', type: 'success'})
        } else {
          ElMessage({message: '提交失败', type: 'error'})
        }
      })
      .catch(reason => ElMessage({message: '提交异常' + reason, type: 'error'}))
}

getTestQuestion();
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


.el-tab-pane .el-button {
  font-size: 25px;
  justify-content: center;
  padding: 3px;
}


.multy-text {
  margin: 5px 0 15px 0;

}
</style>