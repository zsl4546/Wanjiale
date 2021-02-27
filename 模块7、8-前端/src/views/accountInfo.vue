<template>
  <div>
    <div v-for="item in formLabelAlign" class="show">
      <el-row :gutter="20" class="row">
        <el-col :span="3"><div class="grid-content">客户编码</div></el-col>
        <el-col :span="6"><div class="bg-purple">{{item.code}} </div></el-col>
      </el-row>
      <el-row :gutter="20">
        <el-col :span="3"><div class="grid-content">客户名称</div></el-col>
        <el-col :span="6"><div class="bg-purple">{{item.name}} </div></el-col>
      </el-row>
      <el-row :gutter="20">
        <el-col :span="3"><div class="grid-content">信用额度</div></el-col>
        <el-col :span="6"><div class="bg-purple">{{item.quota}} </div></el-col>
      </el-row>
      <el-row :gutter="20">
        <el-col :span="3"><div class="grid-content">实际发货余额</div></el-col>
        <el-col :span="6"><div class="bg-purple">{{item.shipAmount}} </div></el-col>
      </el-row>
      <el-row :gutter="20">
        <el-col :span="3"><div class="grid-content">实际商折余额</div></el-col>
        <el-col :span="6"><div class="bg-purple">{{item.discountAmount}} </div></el-col>
      </el-row>
      <el-row :gutter="20">
        <el-col :span="3"><div class="grid-content">已缴纳备货保金</div></el-col>
        <el-col :span="6"><div class="bg-purple">{{item.pay}} </div></el-col>
      </el-row>
      <el-row :gutter="20">
        <el-col :span="3"><div class="grid-content">备注</div></el-col>
        <el-col :span="6"><div class="bg-purple">{{item.info}}。</div></el-col>
      </el-row>
    </div>
  </div>
</template>

<script>
    import {Loading} from "element-ui";

    export default {
      name: "accountInfo",
      data(){
        return{
          user: {
            isDealer: '',
            account:'',
            admin:'',
            identity:''
          },
          formLabelAlign:[],
        }
      },
      created() {
        const _this = this
        let loadingInstance = Loading.service();

        axios.get('http://localhost:8080/login/load').then(function (res) {
          _this.user.account = res.data.account
          _this.user.admin = res.data.admin
          _this.user.identity = res.data.identity
          if (_this.user.identity === '1'){
            _this.user.isDealer = false
          }else {
            _this.user.isDealer = true
          }
        }).then(function (res1) { //加载完毕后关闭loading
          let url = ''
          if (_this.user.identity === '2'){
            url = 'http://localhost:8080/dealer/findClientChild?account='+_this.user.account
          }else {
            url = 'http://localhost:8080/dealer/findClient?account='+_this.user.account
          }
          axios({
            url: url,
            method: 'get',
          }).then(function (response) {
            console.log(response.data)
            _this.formLabelAlign = response.data
            console.log(_this.user)
            _this.$nextTick(() => { // 以服务的方式调用的 Loading 需要异步关闭
              loadingInstance.close();
            });
          })
        })
      },
    }//
</script>

<style scoped>
  .show{
    margin-left: 70px;
    /*border: 1px black solid;*/
    padding-bottom: 40px;
  }
  .grid-content{
    /*background: #d3dce6;*/
    /*border: 1px black solid;*/
    font-family: verdana,arial,sans-serif;
    font-size:15px;color:#333333;border-collapse: collapse;
    text-align: right;
    line-height: 30px;
  }
  .bg-purple {
    padding-left: 0 !important;
    border: 1px black solid;
    background: #d3dce6;
    padding: 0 !important;
    line-height: 30px;
  }
</style>
