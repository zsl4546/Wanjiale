<template>
  <el-container style="height: 1000px; border: 1px solid #eee">
    <el-aside width="200px" style="background-color: rgb(238, 241, 246)">
      <el-menu router :default-openeds="['0','1','2']">
        <el-submenu v-for="(item,index) in $router.options.routes" :index="index+''" v-if="item.tag===user.tag">
          <template slot="title"><i class="el-icon-message"></i>{{item.name}}</template>
          <el-menu-item v-for="(item2,index2) in item.children" :index="item2.path" v-if="item2.path!=='/welcome'" >{{item2.name}}</el-menu-item>
        </el-submenu>
      </el-menu>
    </el-aside>

<!--    :class="$route.path==item2.path?'is-active':''"-->
    <el-container>
      <el-header style="text-align: right; font-size: 12px">
        <el-dropdown>
          <i class="el-icon-setting" style="margin-right: 15px"></i>
          <el-dropdown-menu slot="dropdown">
            <el-dropdown-item>查看</el-dropdown-item>
            <el-dropdown-item>新增</el-dropdown-item>
            <el-dropdown-item>删除</el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>
        <span>{{user.account}} 您好！</span>
      </el-header>
      <el-image :src="require('@/assets/background-1747777__340.webp')" style="height: 50px" lazy></el-image>

      <el-main>
        <router-view></router-view>
      </el-main>
    </el-container>
  </el-container>
</template>

<script>
  export default {
    data(){
      return{
        user: {
          account:'',
          tag:'',
          id:''
        },
      }
    },
    name: "index",
    created() {
      const _this = this
      axios({
        type:'get',
        url:'http://localhost:8082/z/load',
        xhrFields:{withCredentials: true}
      }).then(function (resp) {
        console.log(123)
        console.log(resp)
        console.log(resp.data.account)
        _this.user.account = resp.data.account;
        _this.user.id = resp.data.id;
        _this.user.tag = resp.data.tag;
      })
    }
  }

</script>

<style scoped>

</style>
