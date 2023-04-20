<template>
  <el-container>
    <!-- 首部 -->
    <el-header class="header">
      <div class="header-left">
        <a href="/home" target="_blank"> <img src="@/assets/countryLoge.png" class="logo"
                                              alt="答题国度logo"/>答题国度</a>
      </div>
      <div class="header-right">
        <el-input v-model="searchText" class="w-50 m-2" placeholder="搜索试卷"
                  :suffix-icon="Search" @keyup.enter="searchTest"/>
        <a id="head-user" v-if="userName===undefined" href="#" @click="dialogFormVisible = true">登录/注册</a>
        <div v-else>{{ userName }}</div>
        <el-dialog v-model="dialogFormVisible" title="登录" width="500px">
          <Login @loginSuccess="checkToken"/>
        </el-dialog>
      </div>
    </el-header>
  </el-container>
</template>

<script setup>
import {Search} from '@element-plus/icons-vue'
import {ref} from "vue";
import Login from "@/components/Login.vue";
import axios from "axios";
import {ElMessage} from "element-plus";

defineProps({
  userName: {type: String}
})

//搜索框
let searchText = ref('')
let dialogFormVisible = ref(false)

const emit = defineEmits(['updateUserInfo']);

//检测是否已登录
function checkToken() {
  let token = localStorage.getItem('country_token');
  if (token !== null) {
    //token 存在：设置每次请求放入请求行
    axios.defaults.headers.common['Authorization'] = token;
    updateUserInfo();
  }
}

//从服务器请求登录信息
function updateUserInfo() {
  axios.get('/userInfo', {
    baseURL: USER_BASE_URL
  }).then(response => {
    if (response.data.status === 0) {
      //获取用户信息成功
      let loginUserInfo = {
        userId: response.data.object.userId,
        userName: response.data.object.userName,
        signHistory: response.data.object.signHistory,
        recentTest: response.data.object.recentTest
      }
      emit('updateUserInfo', loginUserInfo);
    } else if (response.data === 'invalid token') {
      //token无用，则清除无效 token
      localStorage.removeItem('country_token');
      axios.defaults.headers.common = {};
    } else {
      ElMessage({
        message: 'checkLoginStatus失败',
        type: 'success',
      });
    }
  })
}

//搜索试卷
function searchTest() {
  alert('模拟搜索' + searchText.value);
}

checkToken();
</script>

<style scoped>
/*输入框*/
.el-input {
  font-size: 20px;
  width: 200px;
  height: 35px;
  margin-right: 30px;
}

</style>
