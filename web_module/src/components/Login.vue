<template>
  <el-form :label-position="'left'" label-width="100px"
           :model="formLabelAlign" style="max-width: 460px">
    <el-form-item label="电话号码">
      <el-input v-model="loginData.tel"/>
    </el-form-item>
    <el-form-item label="验证码">
      <el-input v-model="loginData.verificationCode"/>
    </el-form-item>
    <el-form-item>
      <el-checkbox-group v-model="loginData.agree">
        <el-checkbox label="用户隐私协议" name="type"/>
      </el-checkbox-group>
    </el-form-item>
    <el-form-item>
      <el-button type="primary" @click="onSubmit">登录/注册</el-button>
    </el-form-item>
  </el-form>
</template>

<script setup>
import {reactive} from 'vue'
import axios from "axios";

axios.defaults.baseURL = "http://localhost:7000";

//登录表单数据
const loginData = reactive({
  tel: '',
  verificationCode: '',
  agree: []
})

//

//登录
const onSubmit = () => {
  axios.post('/login', {
    tel: loginData.tel,
    verificationCode: loginData.verificationCode
  })
      .then(function (response) {
        console.log(response);
      })
      .catch(function (error) {
        console.log(error);
      });
  alert('登陆成功');
}
</script>

<style scoped>
.el-form {
  width: 100%;
  height: 200px;
}
</style>