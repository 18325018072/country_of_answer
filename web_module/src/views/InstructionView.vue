<template>
  <div class="common-layout">
    <el-container>
      <el-header class="countryBlackHeader">
        <div id="header-left">
          <img src="/src/assets/countryLoge.png" class="logo" alt="答题国度logo"/>答题国度
        </div>
        <div class="header-right">
          <a href="/home" target="_blank">开始</a>
          <a href="/home" target="_blank">Github</a>
          <a href="/home" target="_blank">关于作者</a>
        </div>
      </el-header>

      <el-main>
        <div id="intro-div">
          <div class="intro-title">答题国度</div>
          <div class="intro-sub-title">高并发场景下的在线课程答题系统</div>
          <el-button type="danger">开始</el-button>
        </div>
      </el-main>
    </el-container>
  </div>
</template>

<script setup>

</script>

<style>
#header-left {
  display: flex;
  align-items: center;
}

#intro-div {
  margin: 50px;
}

.intro-title {
  font-weight: bold;
  font-size: 30px;
  margin-bottom: 10px;
}

.intro-sub-title {
  font-size: 20px;
  margin-bottom: 30px;
  color: #706b6b;
}
</style>