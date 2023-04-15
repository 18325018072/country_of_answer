<template>
  <div class="common-layout">
    <Head/>
    <!--正文-->
    <el-main>
      <!--左-->
      <div id="left-part">
        <!--左-走马灯-->
        <el-carousel>
          <el-carousel-item>
            <img src="../assets/ad1.png" alt="走马灯展示图片"/>
          </el-carousel-item>
          <el-carousel-item>
            <img src="../assets/ad2.png" alt="走马灯展示图片"/>
          </el-carousel-item>
          <el-carousel-item>
            <img src="../assets/ad3.png" alt="走马灯展示图片"/>
          </el-carousel-item>
        </el-carousel>
        <!--左-热门试题-->
        <div class="content-box">
          <div class="box-title">热门试卷</div>
          <div id="hot-test-box">
            <a href="#" class="test-div" v-for="test in hotTests.data" @click="goTest(test)">
              <div>
                <img src="@/assets/easy.png" v-if="test.difficulty === 'easy'" alt="简单"/>
                <img src="@/assets/middle.png" v-else-if="test.difficulty === 'middle'" alt="中等"/>
                <img src="@/assets/hard.png" v-else alt="困难"/>
                {{ test.testName }}
              </div>
              <div class="test-down">
                <span>{{ test.publisher }}</span>
                <span>{{ test.studyNum }}人参加</span>
              </div>
            </a>
          </div>
        </div>
      </div>
      <!--右-->
      <div id="right-part">
        <!--右-最近答题-->
        <div class="content-box">
          <div class="box-title">最近答题</div>
          <div v-if="userInfo.userId==='none'" style="color: gray">请先登录</div>
          <div id="text-list-box" v-else>
            <a href="#" v-for="test in recentTests.data">
              <div>{{ test.testName }}</div>
            </a>
            <a href="#" v-if="recentTests.data.length >= 4">更多...</a>
          </div>
        </div>
        <!--右-学习历史-->
        <div class="content-box">
          <div class="box-title">学习历史</div>
          <el-calendar ref="calendar" v-model="today">
            <template #header="{ date }">
              <span>{{ date }}</span>
              <el-button class="countryRed" type="danger" @click="sigh">签到</el-button>
            </template>
            <template #date-cell="{ data }">
              <p :class="isSigned(data)?'sighed':''">
                {{ data.day.split('-').slice(2).join('-') }}
              </p>
            </template>
          </el-calendar>
        </div>
        <!--右-热门讨论-->
        <div class="content-box">
          <div class="box-title">热门讨论</div>
          <div id="text-list-box">
            <a href="#" v-for="dis in discussions">
              <div>{{ dis.title }}</div>
            </a>
            <a href="#" v-if="recentTests.length >= 4">更多...</a>
          </div>
        </div>
      </div>
    </el-main>
  </div>
</template>

<script setup>
import {reactive, ref} from "vue";
import {ElMessage} from "element-plus";
import Head from "@/components/Head.vue";
import axios from "axios";

document.title = '答题国度';
const today = ref(new Date())
let hotTests = reactive({
  data: [
    {testId: '1523', testName: '微积分练习题', difficulty: 'middle', publisher: '高老师', studyNum: '15234'}
  ]
})
let recentTests = reactive({
  data: [
    {testId: '1523', testName: '微积分练习题'}
  ]
})
let discussions = reactive({
  data: [
    {discussionId: '7536', title: '激发大学生创新创业意识'}
  ]
})

const userInfo = reactive({
  userId: 'none',
  name: 'none',
  tel: 'none'
});

const USER_BASE_URL = 'http://localhost:7000';
const TEST_BASE_URL = 'http://localhost:7001';

//请求热门试卷
axios.request({
  method: 'get',
  url: '/hotTest',
  baseURL: TEST_BASE_URL
}).then(response => {
  let a = response.data.object;
  console.log(a);
  hotTests.data = a;
}).catch(error => {
  console.log(error);
});

if (userInfo.userId !== "none") {
  //请求最近试卷
  axios.request({
    method: 'get',
    url: '/recentTest',
    params: {tel: userInfo.tel},
    baseURL: USER_BASE_URL
  }).then(response => {
    recentTests = JSON.stringify(response.object);
  }).catch(error => {
    console.log(error);
  });
}


//请求热门讨论
// axios.request({
//   method: 'get',
//   url: '/discussion',
//   baseURL: 'http://localhost:7000'
// }).then(response => {
//   discussions = JSON.stringify(response.object);
// }).catch(error => {
//   console.log(error);
// });

//进入试卷
function goTest(test) {
  location.href = '/test/' + test.testId + '/summary';
}

// 是否签到
function isSigned(data) {
  let day = parseInt(data.day.split('-').slice(2).join('-'));
  return day > 10 && day < 16;
}

// 签到
function sigh() {
  ElMessage({
    message: '签到成功',
    type: 'success',
  })
}
</script>

<style>
/*走马灯*/
img {
  width: 100%;
}

.content-box {
  min-height: 250px;
  border: 2px solid darkgray;
  border-radius: 5px;
  padding: 8px;
  margin: 15px;
}

.box-title {
  font-size: 18px;
  font-weight: bold;
  margin-bottom: 10px;
}

#left-part {
  width: 700px;
  margin-right: 10px;
}

#right-part {
  width: 300px;
}

/*热门试题*/
#hot-test-box {
  display: flex;
  padding-right: 10px;
  justify-content: space-between;
  flex-wrap: wrap;
}

.test-div {
  color: black;
  text-decoration-line: none;
  padding: 10px;
  margin: 0 5px 10px 0;
  border: 2px solid #555353;
  border-radius: 3px;
  width: 45%;
  height: 60px;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
}

.test-div img {
  width: 20px;
}

.test-down {
  font-size: 10px;
  color: gray;
  display: flex;
  justify-content: space-between;
}

/*最近答题*/
#text-list-box {
  display: flex;
  flex-direction: column;
}

#text-list-box a {
  font-size: 17px;
  color: gray;
  margin-bottom: 5px;
}

/*日历*/
.el-calendar .el-button {
  height: 22px;
  width: 40px;
  font-size: 15px;
  margin-left: 30px;
}

.el-calendar__header {
  justify-content: end;
  padding: 2px;
}

.el-calendar__body {
  padding: 0;
}

.el-calendar-table__row td {
  border: 0;
}

.el-calendar-table .el-calendar-day {
  font-size: 2px;
  width: 100%;
  height: 25px;
  display: flex;
  justify-content: center;
  padding: 2px 0 4px 0;
}

.el-calendar-table .el-calendar-day p {
  width: 70%;
}

.sighed {
  background-color: rgba(255, 215, 0, 0.48);
}
</style>