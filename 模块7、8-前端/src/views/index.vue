<template>
  <el-container style="height: 1000px; border: 3px solid #FFCC99">
    <el-aside width="200px" style="background-color: #99CCCC">
      <el-menu router :default-openeds="['0','1','2']" style="background-color: #99CCCC">
        <!--          遍历index.js中的路由-->
        <el-submenu v-for="(item,index) in $router.options.routes" :index="index+''" v-if="item.show === true && item.isDealer === user.isDealer" style="background-color: #FFCCCC">
          <!--          标题名字， 标签i是这个标题的图标，其中class指定图标类型-->
          <template slot="title"><i class="el-icon-s-data"></i>{{item.name}}</template>
          <!--          遍历子路由-->
<!--          v-if的逻辑是 不展示welcome页面的标题，如果是chara页面，必须admin的值为1，即这个用户是超级管理员-->
          <el-menu-item v-for="(item2,index2) in item.children" :index="item2.path"
                        v-if="item2.path!=='/welcome' && item2.path!=='/chara' || item2.path === '/chara' && user.admin === '1'">{{item2.name}}</el-menu-item>

        </el-submenu>
      </el-menu>
    </el-aside>

    <el-container>
      <el-header style="text-align: right; font-size: 12px; background-color: #FFCC99">
        <el-dropdown>
          <i class="el-icon-setting" style="margin-right: 15px"></i>
        </el-dropdown>
        <span>{{user.account}} 您好！</span>
      </el-header>

      <el-main>
        <router-view></router-view>
      </el-main>
    </el-container>
  </el-container>
</template>

<script>
  import { Loading } from 'element-ui';
  export default {
    data(){
      return{
        user: {
          isDealer: '',
          account:'',
          admin:'',
          identity:''
        },
      }
    },
    name: "index",
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
      }).then(function (res) { //加载完毕后关闭loading
        console.log(_this.user)
        _this.$nextTick(() => { // 以服务的方式调用的 Loading 需要异步关闭
          loadingInstance.close();
        });
      })
    }
  }

</script>

<style scoped>

</style>
