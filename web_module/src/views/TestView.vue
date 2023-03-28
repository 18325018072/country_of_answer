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
    </el-container>
    <!--正文-->
    <el-main>
      <h1>{{ test.name }}</h1>
      <el-page-header :icon="null">
        <template #content>
          <div class="flex items-center">
            <span class="text-large font-600 mr-3"> {{ test.name }} </span>
          </div>
        </template>
        <template #extra>
          <div class="flex items-center">
            <el-button type="primary" class="ml-2">答题</el-button>
          </div>
        </template>
      </el-page-header>
    </el-main>
  </div>
</template>

<script setup>
import {reactive} from 'vue'

const test = reactive({
      testId: '5124963',
      name: 'MySQL 查询优化练习',
      difficulty: 'hard',
      publisher: '高老师',
      studyNum: '45234',
      detail: 'MySQL的性能优化非常广泛，包括索引优化，查询优化，查询缓存，服务器设置优化，操作系统和硬件优化，应用层面优化等等。MySQL的性能优化非常广泛，包括索引优化，查询优化，查询缓存，服务器设置优化，操作系统和硬件优化，应用层面优化等等',
      aveGrade: '74.66',
      openTime: '2023/4/26 - 2023/5/30',
      testTime: '4'
    }
)

const userTestInfo = reactive({
  userId: '801523',
  historyTop: '95',
  triedTime: '3'
})
</script>