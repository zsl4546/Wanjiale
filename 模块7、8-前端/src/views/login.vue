<template>
  <div class="center1">
    <el-form :model="user" status-icon :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
      <el-form-item label="账号" prop="account">
        <el-input v-model.number="user.account"></el-input>
      </el-form-item>
      <el-form-item label="密码" prop="pass">
        <el-input type="password" v-model="user.pass"></el-input>
      </el-form-item>
      <el-form-item>
        <el-radio v-model="user.identity" label="3">我是子经销商</el-radio>
        <el-radio v-model="user.identity" label="2">我是总经销商</el-radio>
        <el-radio v-model="user.identity" label="1">我是运营人员</el-radio>
      </el-form-item>

      <el-form-item>
        <el-button type="primary" @click="submitForm('ruleForm')">登录</el-button>
        <el-button @click="resetForm('ruleForm')">重置</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>
<script>
  import qs from 'qs'
  export default {
    data() {
      var validatePass = (rule, value, callback) => {
        if (value === '') {
          callback(new Error('请输入密码'));
        } else {
          callback();
        }
      };
      var validateAccount = (rule, value, callback) => {
        if (value === '') {
          callback(new Error('请输入账号'));
        } else {
          callback();
        }
      };

      return {
        user: { //表单绑定的数据
          account: '',
          pass: '',
          admin: '',
          identity: '3',
        },
        rules: {
          account: [
            {
              validator: validateAccount, trigger: 'blur' //指定验证器和触发事件
            }
          ],
          pass: [
            { validator: validatePass, trigger: 'blur' }   //指定验证器和触发事件
          ],
        }
      };
    },
    methods: {
      submitForm(formName) {
        const _this = this //解决在这个ajax里面写的this和这个地方的this代指不一致的问题，这里的this代指整个全局，如果在下面这个语句块里面写this代指的是这个方法
        this.$refs[formName].validate((valid) => {   //验证账号和密码，前端先初步验证填写是否为空，再提交后端验证账号密码
          if (valid) {
            let url = ''
            let data1 = {"account":_this.user.account,"password":_this.user.pass};
            if (_this.user.identity === '1'){
              url = 'http://localhost:8080/login/user'
            }else {
              url = 'http://localhost:8080/login/dealer'
            }
            console.log(url)
              axios({
                url: url,
                method: 'post',
                data: qs.stringify(data1)   //这里要用qs的stringfy方法将data1进行处理才能正确传参到后台
              }).then(function (res) {
                console.log(res)
                if (res.data !== '' && (_this.user.identity !== '1' && res.data.type === _this.user.identity || _this.user.identity === '1'))  //判断是不是同一个身份的账号
                {
                  _this.user.admin = res.data.admin
                  let url2 = ''
                  if (_this.user.identity === '1'){
                    url2 = 'http://localhost:8080/login/session1'
                  }else if (_this.user.identity === '2'){
                    url2 = 'http://localhost:8080/login/session2/2'
                  }else if (_this.user.identity === '3'){
                    url2 = 'http://localhost:8080/login/session2/3'
                  }
                  console.log(url2)
                  axios({
                    url: url2,
                    method: 'get',
                    params: res.data
                  }).then(function (res) {
                    console.log('session')
                    if (_this.user.identity === '1')
                    {
                      console.log('跳转到1')
                      _this.$router.push('/1') //一定要上面的session加载完才跳转，不然可能跳转后找不到session
                    }
                    else {
                      console.log('跳转到2')
                      _this.$router.push('/2')
                    }
                  })
                }else {
                  console.log()
                  alert('账号或密码有误！')
                }
              })
          } else {
            alert('请输入正确格式的账号！');
            return false;
          }
        });
      },
      resetForm(formName) {
        this.$refs[formName].resetFields(); //将forName内容清空
      }
    }
  }
</script>
<style>
  .center1{
    margin-top: 200px;
    width: 50%;
    height: 600px;
    margin-left: 500px;
  }
</style>
