<template>
  <div class="common-layout">
    <el-container>
      <!-- 首部 -->
      <el-header class="header">
        <div class="header-left">
          <a href="/home" target="_blank"> <img src="/src/assets/countryLoge.png" class="logo" alt="答题国度logo"/>答题国度</a>
        </div>
        <div class="header-right">
          <el-input v-model="searchText" class="w-50 m-2" placeholder="搜索试卷"
                    :suffix-icon="Search" @keyup.enter="testF"/>
          <a href="/home" target="_blank">登录/注册</a>
        </div>
      </el-header>
      <!--正文-->
      <el-main>
        <!--左-->
        <div id="left-part">
          <!--左-走马灯-->
          <el-carousel>
            <el-carousel-item v-for="item in 4" :key="item">
              <img src="../assets/carousel1.png" alt="走马灯展示图片"/>
            </el-carousel-item>
          </el-carousel>
          <!--左-热门试题-->
          <div class="content-box">
            <div class="box-title">热门试题</div>
            <div id="hot-test-box">
              <a href="#" class="test-div" v-for="test in hotTests" @click="goTest(test)">
                <div>
                  <img src="./src/assets/easy.png" v-if="test.difficulty === 'easy'" alt="简单"/>
                  <img src="../assets/middle.png" v-else-if="test.difficulty === 'middle'" alt="中等"/>
                  <img src="./src/assets/hard.png" v-else alt="困难"/>
                  {{ test.name }}
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
            <div id="text-list-box">
              <a href="#" v-for="test in recentTests">
                <div>{{ test.name }}</div>
              </a>
              <a href="#" v-if="recentTests.length >= 4">更多...</a>
            </div>
          </div>
          <!--右-学习历史-->
          <div class="content-box">
            <div class="box-title">学习历史</div>
            <el-calendar ref="calendar" v-model="value">
              <template #header="{ date }">
                <span>{{ date }}</span>
                <el-button type="danger" @click="sigh">签到</el-button>
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
    </el-container>
  </div>
</template>

<script setup>
import {reactive, ref} from "vue";
import {Search} from '@element-plus/icons-vue'
import {ElMessage} from "element-plus";

const searchText = ref('')
const value = ref(new Date())
let hotTests = reactive([
  {testId: '1523', name: '微积分练习题', difficulty: 'middle', publisher: '高老师', studyNum: '15234'},
  {testId: '1523', name: '微积分练习题', difficulty: 'middle', publisher: '高老师', studyNum: '15234'},
  {testId: '1523', name: '微积分练习题', difficulty: 'middle', publisher: '高老师', studyNum: '15234'},
  {testId: '1523', name: '微积分练习题', difficulty: 'middle', publisher: '高老师', studyNum: '15234'},
  {testId: '1523', name: '微积分练习题', difficulty: 'middle', publisher: '高老师', studyNum: '15234'},
  {testId: '1523', name: '微积分练习题', difficulty: 'middle', publisher: '高老师', studyNum: '15234'},
  {testId: '1523', name: '微积分练习题', difficulty: 'middle', publisher: '高老师', studyNum: '15234'}
])
let recentTests = reactive([
  {testId: '1523', name: '微积分练习题'},
  {testId: '1523', name: '微积分练习题'},
  {testId: '1523', name: '微积分练习题'},
  {testId: '1523', name: '微积分练习题'}
])
let discussions = reactive([
  {discussionId: '7536', title: '激发大学生创新创业意识'},
  {discussionId: '7536', title: '激发大学生创新创业意'},
  {discussionId: '7536', title: '激发大学生创新创业'},
  {discussionId: '7536', title: '激发大学生创新创'},
  {discussionId: '7536', title: '激发大学生创新创业意识'},
])

//进入试卷
function goTest(test) {
  location.href = '/test/' + test.testId;
}

// 是否签到
function isSigned(data) {
  let day = parseInt(data.day.split('-').slice(2).join('-'));
  return day > 10 && day < 16;
}

//搜索试卷
function testF() {
  alert('模拟搜索' + searchText.value);
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
/*输入框*/
.el-input {
  font-size: 20px;
  width: 200px;
  height: 35px;
  margin-right: 30px;
}

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
  text-decoration-line: none;
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