<template>
  <div style="background-image: url('https://cdn.pixabay.com/photo/2016/10/17/14/31/background-1747777_960_720.jpg'); height: 1000px">
    <div style="height: 300px"></div>
    <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="40%" class="demo-ruleForm" style="width: 75%">
      <el-form-item label="编号" prop="">
        <el-input v-model="ruleForm.user_id"></el-input>
      </el-form-item>
      <el-form-item label="用户名" prop="account">
        <el-input v-model="ruleForm.account"></el-input>
      </el-form-item>
      <el-form-item label="密码" prop="password">
        <el-input v-model="ruleForm.password" type="password"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="submit" @click="submitForm('ruleForm')">登录</el-button>
        <el-button @click="resetForm('ruleForm')">重置</el-button>
        <a href="http://beian.miit.gov.cn/" style="text-decoration: none; display: block">蜀ICP备2021000191号</a>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
  import qs from 'qs'

  export default {
    edit(row){
      console.log(row)
    },
    data() {
      return {
        ruleForm: {
          user_id:'',
          account: '',
          password: '',
          tag:''
        },
        rules: {
          account: [
            { required: true, message: '请输入用户名', trigger: 'blur' }
          ],
          password: [
            { required: true, message: '请输入密码', trigger: 'blur' }
          ],
        }
      };
    },
    methods: {
      submitForm(formName) {
        const _this = this
        this.$refs[formName].validate((valid) => {
          console.log(valid)
          if (valid) {
            let data = {"account":_this.ruleForm.account,"password":_this.ruleForm.password};
            axios.post('http://localhost:8082/z/login/', qs.stringify(data),
              ).then(function (response) {
              _this.ruleForm.user_id = response.data.id;
              _this.ruleForm.tag = response.data.tag;
              console.log(response)
              if(response.data !== ''){
                let url = 'http://localhost:8082/z/save/'+response.data.id+'/'+response.data.account+'/'+response.data.tag+'/'+response.data.code;
                console.log(url)
                axios({
                  type:'get',
                  url:url,
                  crossDomain: true
                }).then(function (res) {
                    console.log(res)
                }).then(function (res) {
                  if (_this.ruleForm.tag === 1){
                    _this.$router.push('/1');
                  }
                  else if (_this.ruleForm.tag === 2){
                    _this.$router.push('/2')
                  }
                  else if (_this.ruleForm.tag === 3){
                    _this.$router.push('/3')
                  }
                  console.log(_this.ruleForm + '*')

                })
              }
          })

          } else {
            console.log('error submit!!');
            return false;
          }
        });
      },
      resetForm(formName) {
        this.$refs[formName].resetFields();
      }
    },
  }
</script>
<style>

</style>
