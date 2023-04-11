<template>
  <div class="common-layout">
    <Head/>
    <!--正文-->
    <el-main>
      <div>
        <h1>{{ testInfo.name }}</h1>
        <el-button type="success" @click="submitTest()">提交</el-button>
      </div>
      <div id="test-tabs-div">
        <el-tabs id="tab" :tab-position="'left'" class="left-tabs" v-model="curTab">
          <!--选择题-->
          <el-tab-pane name="select" label="选择题" v-if="testInfo.existSelect==='true'">
            <div v-for="question in selectProblem">
              <div>{{ question.id }}、{{ question.title }}</div>
              <el-radio-group v-model="question.answer" class="ml-4">
                <el-radio size="large" v-for="(option,index) in question.options" :label="index">{{ option }}
                </el-radio>
              </el-radio-group>
            </div>
          </el-tab-pane>

          <!--填空题-->
          <el-tab-pane name="complete" label="填空题" v-if="testInfo.existComplete==='true'">
            填空题
          </el-tab-pane>

          <!--判断题-->
          <el-tab-pane name="judge" label="判断题" v-if="testInfo.existJudge==='true'">
            <div v-for="question in judgeProblem">
              <div>{{ question.id }}、{{ question.title }}</div>
              <el-radio-group v-model="question.answer" class="ml-4">
                <el-radio size="large" label="true"><img src="/src/assets/true.png" alt="true"></el-radio>
                <el-radio size="large" label="false"><img src="/src/assets/false.png" alt="false"></el-radio>
              </el-radio-group>
            </div>
          </el-tab-pane>

          <!--综合题-->
          <el-tab-pane name="comprehension" label="综合题" v-if="testInfo.existComprehension==='true'">
            <div v-for="question in comprehensionProblem">
              <div>{{ question.id }}、{{ question.title }}</div>
              <el-input class="multy-text" v-model="question.answer" :autosize="{ minRows: 3, maxRows: 6 }"
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
import Head from "@/components/Head.vue";

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

const userTestInfo = reactive({
  userId: '801523',
  historyTop: '95',
  triedTime: '3',
  isScoring: 'false'
})

const userInfo = reactive({
  userId: '801523',
  name: '小明'
})

let selectProblem = reactive([
  {id: '1', answer: null, title: 'MySQL中，开启慢查询日志需要设置的配置项是？', options: ['slow_query_log', 'slow_qu']},
  {
    id: '2',
    answer: null,
    title: 'MySQL中，开启慢查询日志需要设置的配置项是？',
    options: ['slow_query_log', 'slow_qu', 'sl']
  },
  {
    id: '3',
    answer: null,
    title: 'MySQL中，开启慢查询日志需要设置的配置项是？',
    options: ['slow_query_log', 'slow_qu', 'sl', 'slow_query_lo']
  }
])

// 题目
let completeProblem = reactive([
  {id: '1', answer: null, title: 'MySQL中，开启慢查询日志需要设置的配置项是{}。'},
  {id: '2', answer: null, title: 'MySQL中，开启慢查询日志需要设置的配置项是{}。'},
  {id: '3', answer: null, title: 'MySQL中，开启慢查询日志需要设置的配置项是{}。'},
  {id: '4', answer: null, title: 'MySQL中，开启慢查询日志需要设置的配置项是{}。'},
  {id: '5', answer: null, title: 'MySQL中，开启慢查询日志需要设置的配置项是{}。'}
])
let judgeProblem = reactive([
  {id: '1', answer: null, title: 'MySQL中，开启慢查询日志需要设置的配置项是slow_query_log。'},
  {id: '2', answer: null, title: 'MySQL中，开启慢查询日志需要设置的配置项是slow_query_log。'},
  {id: '3', answer: null, title: 'MySQL中，开启慢查询日志需要设置的配置项是slow_query_log。'},
  {id: '4', answer: null, title: 'MySQL中，开启慢查询日志需要设置的配置项是slow_query_log。'}
])
let comprehensionProblem = reactive([
  {id: '1', answer: null, title: 'MySQL中，开启慢查询日志需要设置的配置项是什么？'},
  {id: '2', answer: null, title: 'MySQL中，开启慢查询日志需要设置的配置项是什么？'}
])
document.title = '考试中-' + testInfo.name;

function submitTest() {
  alert('提交成功');
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


.el-tab-pane .el-button {
  font-size: 25px;
  justify-content: center;
  padding: 3px;
}


.multy-text {
  margin: 5px 0 15px 0;

}
</style>