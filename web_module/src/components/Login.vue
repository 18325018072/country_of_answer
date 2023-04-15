<template>
  <el-form :label-position="'left'" label-width="100px"
           :rules="loginRules" ref="ruleFormRef"
           :model="loginData">
    <el-form-item label="电话号码">
      <el-input v-model="loginData.tel" @change="isTel"/>
    </el-form-item>
    <el-form-item label="验证码" prop="verificationCode">
      <el-input v-model="loginData.verificationCode">
        <template #append>
          <el-button type="primary" @click="getVerificationCode" :disabled="buttonDisable">发送验证码</el-button>
        </template>
      </el-input>
    </el-form-item>
    <el-form-item prop="agree">
      <el-checkbox-group v-model="loginData.agree">
        <el-checkbox label="用户隐私协议" name="agree">同意<a href="https://baidu.com">用户隐私协议</a></el-checkbox>
      </el-checkbox-group>
    </el-form-item>
    <el-form-item>
      <el-button type="primary" @click="onSubmit(ruleFormRef)">登录/注册</el-button>
    </el-form-item>
  </el-form>
</template>

<script lang="ts" setup>
import {reactive, ref} from 'vue'
import axios from "axios";
import {ElMessage} from 'element-plus'
import type {FormInstance, FormRules} from "element-plus";

const USER_BASE_URL = 'http://localhost:7000';

let buttonDisable = ref(true);

function isTel() {
  buttonDisable.value = isNaN(parseInt(loginData.tel));
}

const ruleFormRef = ref<FormInstance>();
//登录表单数据
const loginData = reactive({
  tel: '',
  verificationCode: '',
  agree: []
})

//获取验证码
function getVerificationCode() {
  //校验手机号码
  const reg = /^1[3456789]\d{9}$/;
  if (!reg.test(loginData.tel)) {
    ElMessage({
      message: '手机号码格式错误',
      type: 'warning',
    })
    return;
  }
  //发送验证码
  axios.request({
    method: 'get',
    url: '/verify',
    params: {
      tel: loginData.tel
    },
    baseURL: USER_BASE_URL
  }).then(response => {
    if (response.data.status === 0) {
      ElMessage({
        message: '验证码已发送',
        type: 'success',
      })
    } else {
      ElMessage({
        message: '验证码发送失败：' + response.data.info,
        type: 'warning',
      })
    }
  }).catch(error => {
    ElMessage({
      message: '发送失败：' + error,
      type: 'warning',
    })
  });
}

//登录
const onSubmit = async (formEl: FormInstance | undefined) => {
  if (!formEl) return
  await formEl.validate((valid, fields) => {
    if (valid) {
      //输入合法
      const json = JSON.stringify({
        tel: loginData.tel,
        verificationCode: loginData.verificationCode
      });
      axios.post(USER_BASE_URL + '/login/tel', json)
          .then(response => {
            console.log(response);
            ElMessage({
              message: '欢迎您',
              type: 'success',
            })
          }).catch(error => {
        ElMessage({
          message: '登录失败',
          type: 'warning',
        })
        console.log(error);
      });
    } else {
      //不符合登录输入规则
      ElMessage({
        message: '登录失败，请检查输入信息',
        type: 'warning',
      })
      formEl.resetFields('verificationCode');
    }
  })
}

const loginRules = reactive<FormRules>({
  agree: [
    {
      type: 'array',
      required: true,
      message: '必须选择',
      trigger: 'change',
    },
  ]
})
</script>

<style scoped>

.el-checkbox a {
  color: dodgerblue;
}
</style>