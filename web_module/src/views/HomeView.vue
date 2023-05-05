<template>
  <div class="common-layout">
    <CountryHead @updateUserInfo="updateUser" :userName="userInfo.userName"/>
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
            <a href="#" class="test-div" v-for="test in hotTests.data" @click="goTest(test.testId)">
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
          <div v-if="userInfo.userId===undefined" style="color: gray">请先登录</div>
          <div id="text-list-box" v-else>
            <a href="#" v-for="test in userInfo.recentTest" @click="goTest(test.testId)">
              <div>{{ test.testName }}</div>
            </a>
            <a href="#" v-if="userInfo.recentTest.length >= 4">更多...</a>
          </div>
        </div>

        <!--右-签到历史-->
        <div class="content-box">
          <div class="box-title">学习历史</div>
          <el-calendar ref="calendar" v-model="todayDate">
            <template #header="{ date }">
              <span>{{ date }}</span>
              <el-button v-if="userInfo.userId===undefined" type="info" disabled>未登录</el-button>
              <el-button v-else-if="isTodaySigned" type="success" disabled>已签到</el-button>
              <el-button v-else class="countryRed" type="danger" @click="sigh">签到</el-button>
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
            <a href="#" v-for="dis in discussions.data">
              <div>{{ dis.title }}</div>
            </a>
            <a href="#" v-if="discussions.length >= 4">更多...</a>
          </div>
        </div>
      </div>
    </el-main>
  </div>
</template>

<script setup>
import {computed, reactive, ref} from "vue";
import {ElMessage} from "element-plus";
import CountryHead from "@/components/CountryHead.vue";
import axios from "axios";

document.title = '答题国度';
const todayDate = ref(new Date());
const thisMonth = new Date().getMonth() + 1;

let hotTests = reactive({
  data: [
    {testId: '1523', testName: '微积分练习题', difficulty: 'middle', publisher: '高老师', studyNum: '15234'}
  ]
})

let discussions = reactive({
  data: [
    {discussionId: '7536', title: '激发大学生创新创业意识'},
    {discussionId: '5113', title: '网络安全常用术语'}
  ]
})

//用户信息
const userInfo = reactive({
  userId: undefined,
  userName: undefined,
  signHistory: [],
  recentTest: []
});

//更新用户信息（登录后触发）
function updateUser(newUserInfo) {
  userInfo.userId = newUserInfo.userId;
  userInfo.userName = newUserInfo.userName;
  userInfo.signHistory = JSON.parse(newUserInfo.signHistory);
  //获取每个 用户最近访问试卷的信息
  let recentTestId = JSON.parse(newUserInfo.recentTest);
  for (const testId of recentTestId) {
    axios.get('/test/getTestInfo?testId=' + testId, {baseURL: TEST_BASE_URL})
        .then(response => {
          if (response.data.status === 0) {
            userInfo.recentTest.push({testId: testId, testName: response.data.object.testName});
          } else {
            ElMessage({
              message: '获取试卷信息失败' + response.data.info,
              type: "error"
            });
          }
        }).catch(err => {
      ElMessage({
        message: '获取试卷信息失败:' + err,
        type: "error"
      });
    })
  }
}

//请求热门试卷
axios.request({
  method: 'get',
  url: '/test/hotTest',
  baseURL: TEST_BASE_URL
})
    .then(response => {
      hotTests.data = response.data.object;
    })
    .catch(error => {
      console.log(error);
    });

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

//进入试卷详情页面
function goTest(testId) {
  // location.href = '/test/' + testId + '/summary';
  window.open('/test/' + testId + '/summary', '_blank');
}

// 判断每个日期是否签到
function isSigned(data) {
  let day = parseInt(data.day.split('-')[2]);
  let month = parseInt(data.day.split('-')[1]);
  for (const value of userInfo.signHistory) {
    if (day === value && month === thisMonth) {
      return true;
    }
  }
  return false;
}

//今日是否签到
const isTodaySigned = computed(() => {
  let thisDay = new Date().getDate();
  for (const signDay of userInfo.signHistory) {
    if (signDay === thisDay) {
      return true;
    }
  }
  return false;
});

// 签到
function sigh() {
  axios.post('/user/sign', null, {baseURL: USER_BASE_URL})
      .then(response => {
        if (response.data.status === 0) {
          ElMessage({
            message: '签到成功',
            type: 'success',
          });
          location.reload();
        } else {
          ElMessage({
            message: '签到异常',
            type: 'warning',
          });
        }
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

/*签到历史-日历*/
.el-calendar .el-button {
  height: 25px;
  width: 55px;
  font-size: 15px;
  margin-left: 30px;
}

.el-calendar__header {
  justify-content: end;
  align-items: center;
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