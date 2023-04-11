<template>
  <el-container>
    <!-- 首部 -->
    <el-header class="header">
      <div class="header-left">
        <a href="/home" target="_blank"> <img src="/src/assets/countryLoge.png" class="logo" alt="答题国度logo"/>答题国度</a>
      </div>
      <div class="header-right">
        <el-input v-model="searchText" class="w-50 m-2" placeholder="搜索试卷"
                  :suffix-icon="Search" @keyup.enter="searchTest"/>
        <a id="head-user" href="#" @click="dialogFormVisible = true">登录/注册</a>
        <el-dialog class="dialog" v-model="dialogFormVisible" title="答题国度">
          <Login/>
        </el-dialog>
      </div>
    </el-header>
  </el-container>
</template>

<script setup>
import {Search} from '@element-plus/icons-vue'
import {ref} from "vue";
import Login from "@/components/Login.vue";

const searchText = ref('')
const dialogFormVisible = ref(false)

//搜索试卷
function searchTest() {
  alert('模拟搜索' + searchText.value);
}
</script>

<style scoped>
/*输入框*/
.el-input {
  font-size: 20px;
  width: 200px;
  height: 35px;
  margin-right: 30px;
}

.dialog {
  width: 200px;
}
</style>
