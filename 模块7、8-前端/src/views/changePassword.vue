<template>
    <div>
      <el-form :model="ruleForm" status-icon :rules="rules" ref="ruleForm" label-width="100px" style="width: 50%">
        <el-form-item label="原密码" prop="oldPass">
          <el-input type="password" v-model="ruleForm.oldPass" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input type="password" v-model="ruleForm.password" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="确认密码" prop="confirm">
          <el-input type="password" v-model="ruleForm.confirm" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="submitForm('ruleForm')">提交</el-button>
          <el-button @click="resetForm('ruleForm')">重置</el-button>
        </el-form-item>
      </el-form>
    </div>
</template>

<script>
  import qs from 'qs'
  import {Loading} from "element-ui";
    export default {
        name: "changePassword",
      created() {
        const _this = this
        let loadingInstance = Loading.service();

        axios.get('http://localhost:8080/login/load').then(function (res) {
          _this.ruleForm.account = res.data.account
        }).then(function (res) { //加载完毕后关闭loading
          console.log(_this.user)
          _this.$nextTick(() => { // 以服务的方式调用的 Loading 需要异步关闭
            loadingInstance.close();
          });
        })
      },
      data(){
        var validatePass2 = (rule, value, callback) => {
          if (value === '') {
            callback(new Error('请再次输入密码'));
          } else if (value !== this.ruleForm.password) {
            callback(new Error('两次输入密码不一致!'));
          } else {
            callback();
          }
        };
        return{
          ruleForm:{
            oldPass: '',
            password: '',
            confirm: '',
            account: ''
          },
          rules: {
            oldPass: [
              { required: true, message: '请输入密码', trigger: 'change' }
            ],
            password: [
              { required: true, message: '请输入密码', trigger: 'change' }
            ],
            confirm: [
              { validator: validatePass2, trigger: 'blur' },
              { required: true, message: '请输入密码', trigger: 'change' }
            ],
          }
        }
      },
      methods:{
        submitForm(formName) {
          const _this = this
          this.$refs[formName].validate((valid) => {
            console.log(_this.ruleForm)
            if (valid) {
              axios({
                url: 'http://localhost:8080/dealer/changePass/'+_this.ruleForm.oldPass,
                method: 'post',
                data: qs.stringify(_this.ruleForm)
              }).then(function (res) {
                if(res.data === 1){
                  _this.$message({
                    message: '修改密码成功！',
                    type: 'success'
                  });
                }else {
                  _this.$message.error('原密码错误！');
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
      }
    }
</script>

<style scoped>

</style>
