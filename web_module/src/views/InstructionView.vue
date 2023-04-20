<template>
  <div class="common-layout">
    <el-container>
      <!-- 首部 -->
      <el-header class="header">
        <div class="header-left">
          <a href="/home" target="_blank"> <img src="@/assets/countryLoge.png" class="logo" alt="答题国度logo"/>答题国度</a>
        </div>
        <div class="header-right">
          <a href="/home" target="_blank">开始</a>
          <a href="https://github.com/18325018072?tab=repositories" target="_blank">Github</a>
          <!-- 关于作者按钮 -->
          <a href="#" @click="centerDialogVisible = true">关于作者</a>
          <el-dialog v-model="centerDialogVisible" title="关于作者" width="20%" align-center>
            <div>樱弥</div>
            <div>venus.zyk@qq.com</div>
            <template #footer>
              <span class="dialog-footer">
                <el-button type="info" @click="centerDialogVisible = false">知道了</el-button>
              </span>
            </template>
          </el-dialog>
        </div>
      </el-header>
      <!--正文-->
      <el-main>
        <div id="intro-div">
          <div class="intro-title">答题国度</div>
          <div class="intro-sub-title">高并发场景下的在线课程答题系统</div>
          <a href="/home">
            <el-button type="danger">开始</el-button>
          </a>
        </div>
        <img src="src/assets/carousel3.png" class="intro-pic" alt="项目封面展示图"/>
      </el-main>
    </el-container>
  </div>
</template>

<script setup>
import {ref} from 'vue'

let centerDialogVisible = ref(false)
</script>

<style scoped>
#intro-div {
  margin: 50px;
}

.intro-title {
  font-weight: bold;
  font-size: 50px;
  margin-bottom: 10px;
}

.intro-sub-title {
  font-size: 30px;
  margin-bottom: 160px;
  color: #706b6b;
}

#intro-div .el-button {
  height: 35px;
  font-size: 30px;
}

.intro-pic {
  width: 300px;
  height: 300px;
}
</style>