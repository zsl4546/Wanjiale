<template>
  <div>
    <div>
      <el-form :inline="true" class="demo-form-inline">
        <el-form-item label="用户名">
          <el-input placeholder="请输入" v-model="condition.account"></el-input>
        </el-form-item>

        <el-form-item label="真实姓名">
          <el-input placeholder="请输入" v-model="condition.name"></el-input>
        </el-form-item>

        <el-form-item label="手机号">
          <el-input placeholder="请输入" v-model="condition.phone"></el-input>
        </el-form-item>

        <el-form-item label="状态">
          <el-select  placeholder="请选择" v-model="condition.status">
            <el-option label="全部" value=""></el-option>
            <el-option label="启用" value="1"></el-option>
            <el-option label="禁用" value="2"></el-option>
          </el-select>
        </el-form-item>

        <el-form-item>
          <el-button type="primary" @click="findUser">查询</el-button>
        </el-form-item>
      </el-form>
    </div>
    <div>
      <el-button size="medium" @click="addFormVisible = true">新增</el-button>
      <el-button size="medium" @click="manyDelete">删除</el-button>
      <el-button size="medium" @click="changeStatus('1')">启用</el-button>
      <el-button size="medium" @click="changeStatus('2')">禁用</el-button>
    </div>
    <el-table
      ref="multipleTable"
      :data="tableData"
      tooltip-effect="dark"
      style="width: 100%"
      @selection-change="handleSelectionChange"
      v-if="1===1">
      <el-table-column
        type="selection"
        width="55">
      </el-table-column>
      <el-table-column
        fixed
        label="序号"
        type="index"
        width="50">
      </el-table-column>
      <el-table-column
        prop="account"
        label="用户名"
        width="150">
      </el-table-column>
      <el-table-column
        prop="name"
        label="真实姓名"
        width="120">
      </el-table-column>
      <el-table-column
        prop="mail"
        label="邮箱"
        width="120">
      </el-table-column>
      <el-table-column
        prop="phone"
        label="手机号"
        width="120">
      </el-table-column>
      <el-table-column
        :formatter="statusFormat"
        prop="status"
        label="状态"
        width="120">
      </el-table-column>
      <el-table-column
        fixed="right"
        label="操作"
        width="450">
        <template slot-scope="scope">
          <el-button @click="handleClick(scope.row, '1')" type="button" size="small">启用</el-button>
          <el-button @click="handleClick(scope.row, '2')" type="button" size="small">禁用</el-button>
          <el-button @click="deleteUser(scope.row.account)" type="button" size="small">删除</el-button>
          <el-button @click="edit(scope.row.account)" type="button" size="small">修改密码</el-button>
          <el-button @click="autho(scope.row.account)" type="button" size="small">分配权限</el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-dialog title="新增账户" :visible.sync="addFormVisible" style="width: 50%; margin-left: 500px">
      <el-form :model="tempUser" :rules="rules" ref="tempUser" label-width="100px" class="demo-ruleForm">
        <el-form-item label="用户名" prop="account">
          <el-input v-model="tempUser.account"></el-input>
        </el-form-item>
        <el-form-item label="真实姓名" prop="name">
          <el-input v-model="tempUser.name"></el-input>
        </el-form-item>
        <el-form-item label="手机号" prop="phone">
          <el-input v-model="tempUser.phone"></el-input>
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input v-model="tempUser.password"></el-input>
        </el-form-item>
        <el-form-item label="电子邮件" prop="mail">
          <el-input v-model="tempUser.mail"></el-input>
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-radio-group v-model="tempUser.status">
            <el-radio label="1">启用</el-radio>
            <el-radio label="2">禁用</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="submitForm('tempUser')">添加账号</el-button>
          <el-button @click="resetForm('tempUser')">重置</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>

    <el-dialog title="分配权限" :visible.sync="authoFormVisible" style="width: 50%; margin-left: 500px">
      <el-form ref="tempUser" label-width="100px" class="demo-ruleForm">
        <el-checkbox-group v-model="checkedClient" @change="handleCheckedCitiesChange">
          <el-checkbox v-for="item in ClientOptions" :label="item.code" :key="item.code" style="display: block; margin-bottom: 10px">{{item.name}}</el-checkbox>
        </el-checkbox-group>
        <br>
        <el-form-item>
          <el-button type="primary" @click="submitAuthoForm">保存</el-button>
          <el-button @click="resetAuthoForm">重置</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>

  </div>
</template>

<script>
  import qs from 'qs'
  import {Loading} from "element-ui";
  export default {
    name: "S_account",
    created() {
      const _this = this
      let loadingInstance = Loading.service();

      axios.get('http://localhost:8080/login/load').then(function (res) {
        _this.user.account = res.data.account
        console.log('load')
        console.log(res.data.account)
      }).then(function (res) { //加载完毕后关闭loading
        _this.$nextTick(() => { // 以服务的方式调用的 Loading 需要异步关闭
          loadingInstance.close();
        });
      }).then(function (response) {
        _this.findUser();
      })

    },
    data() {
      return {
        user:{account: ''},
        tableData: [],
        CharaNames: [],
        mechanisms: [],
        condition:{
          account:'',
          phone:'',
          name:'',
          status:'',
        },
        multipleSelection:[],
        addFormVisible: false,
        authoFormVisible: false,
        tempUser:{
          account:'',
          name:'',
          password:'',
          phone:'',
          mail:'',
          status:'1',
          type:'',
          fatherAccount:''
        },
        ClientOptions : [],
        checkedClient: [],
        tempAccount: '',
        rules: {
          account: [
            { required: true, message: '请输入用户名', trigger: 'blur' },
          ],
          name: [
            { required: true, message: '请输入真实姓名', trigger: 'blur' },
          ],
          password:[
            { required: true, message: '请输入密码', trigger: 'change' }
          ],
          phone: [
            { required: true, message: '请输入手机号', trigger: 'blur' }
          ],
        }
      }
    },
    methods: {
      handleClick(row, status) { //修改状态为启用或者禁用
        row.status = status
        axios({
          url: 'http://localhost:8080/dealer/changeStatus',
          method: 'get',
          params: row
        }).then(function (res) {
          console.log('修改status')
          console.log(row)
        })
      },

      deleteUser(account){ //单个删除角色
        const _this = this
        let data = {"account":account}
        this.$confirm('确定删除该账号?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          //如果点击确定就向后端发送请求
          axios({
            url: 'http://localhost:8080/dealer/deleteDealer',
            method: 'get',
            params: data
          }).then(function (res) {
            console.log('删除角色')
            _this.findUser() //重新加载角色数据
            _this.$message({
              type: 'success',
              message: '删除成功!'
            });
          })
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          });
        });
      },

      findUser(){ //根据condition向后台查询所有用户数据
        const _this = this
        console.log('condition')
        console.log(_this.condition)
        const loading = this.$loading({
          lock: true,
          text: 'Loading',
          spinner: 'el-icon-loading',
          background: 'rgba(0, 0, 0, 0.7)'
        });
        axios({
          url:'http://localhost:8080/dealer/findDealers',
          method: 'get',
          params: _this.condition
        }).then(function (res) {
          console.log()
          _this.tableData = res.data
          loading.close()
        })
      },
      handleSelectionChange(val) {
        this.multipleSelection = val;
      },

      changeStatus(status){
        const _this = this
        let model = status === '1'?'启用':'禁用' //根据status，model取不同的值用于提示用户该操作是启用还是禁用
        this.$confirm('此操作将'+model+ '选中的角色'+'是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          for (let i = 0; i < _this.multipleSelection.length; i++){
            _this.multipleSelection[i].status = status; //将所有选中的status的值赋值为status
          }
          axios({
            url: 'http://localhost:8080/login/manyStatus/'+status,
            method: 'post',
            dataType: 'json',
            data: JSON.stringify(_this.multipleSelection), //转换为json传递给后台
            headers: {
              'Content-Type': 'application/json', //content-type一定要为application/json
            },
          }).then(function (res) {
            _this.findCharas()
            _this.$message({
              type: 'success',
              message: model+'成功!'
            });
          })

        }).catch(() => {
          _this.$message({
            type: 'info',
            message: '已取消'
          });
        });
      },
      manyDelete(){
        const _this = this
        this.$confirm('此操作将删除选中的角色，是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          axios({
            url: 'http://localhost:8080/dealer/manyDelete',
            method: 'post',
            dataType: 'json',
            data: JSON.stringify(_this.multipleSelection), //转换为json传递给后台
            headers: {
              'Content-Type': 'application/json', //content-type一定要为application/json
            },
          }).then(function (res) {
            _this.findUser()
            _this.$message({
              type: 'success',
              message: '删除成功!'
            });
          })

        }).catch(() => {
          _this.$message({
            type: 'info',
            message: '已取消'
          });
        });
      },
      submitForm(tempUser){
        const _this = this
        const loading = this.$loading({
          lock: true,
          text: 'Loading',
          spinner: 'el-icon-loading',
          background: 'rgba(0, 0, 0, 0.7)'
        });
        _this.tempUser.type = '2'
        // _this.tempUser.fatherAccount = _this.user.account
        axios({
          url: 'http://localhost:8080/dealer/addDealer',
          method: 'post',
          data: JSON.stringify(_this.tempUser),
          headers:{
            'Content-Type': 'application/json'
          }
        }).then(function (res) {
          console.log(res.data)
          if (res.data === 1){
            _this.addFormVisible = false
            _this.$message({
              message: '添加成功！',
              type: 'success'
            });
            _this.findUser()
            loading.close()
          }else if(res.data === 0){
            _this.$message({
              message: '改账户已存在，请重新输入！',
              type: 'warning'
            });
          }
        })
      },
      resetForm(tempUser) {
        this.$refs[tempUser].resetFields();
      },
      edit(account){
        const _this = this
        this.$prompt('请输入要修改的密码', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
        }).then(({ value }) => {
          let data = {
            "account": account,
            "password": value
          }
          axios({
            url: 'http://localhost:8080/dealer/changePassChild',
            method: 'get',
            params: data
          }).then(function (res) {
            _this.$message({
              type: 'success',
              message: '修改成功！'
            });
          })
        }).catch(() => {
          _this.$message({
            type: 'info',
            message: '取消输入'
          });
        });
      },
      autho(account){
        const _this = this
        axios({
          url: 'http://localhost:8080/dealer/findAllClient',
          method: 'get',
        }).then(function (res) {
          _this.ClientOptions = res.data
          console.log(res.data)
          _this.tempAccount = account
        }).then(function (response) {
          axios({
            method:'get',
            url: 'http://localhost:8080/dealer/findAutho/'+account,
          }).then(function (re) {
            _this.checkedClient = re.data
            console.log(re)
            _this.authoFormVisible = true
          })
        })
      },
      submitAuthoForm(){
        const _this = this
        axios({
          method: 'post',
          url: 'http://localhost:8080/dealer/autho/'+_this.tempAccount,
          data: JSON.stringify(_this.checkedClient),
          headers:{
            'Content-Type':'application/json;charset=UTF-8',
          },
          dataType: "json",
        }).then(function (res) {

        })
      },
      resetAuthoForm(){
        this.checkedClient = []
      },
      handleCheckedCitiesChange(val){
        this.checkedClient = val;
        console.log(this.checkedClient)
      },
      UserChange(){
        const _this = this
      },
      mechanismChange(val){
        this.mechanismList = val
        console.log(val)
      },

      pageChange(val){
        this.pageList = val
        console.log(val)
      },

      statusFormat(row) { //在状态那一栏用了一个格式转换器
        if (row.status === '1' || row.status === 1) {
          return '启用'
        } else if (row.status === '2' || row.status === 2) {
          return '禁用'
        }
      },
    }

  }
</script>

<style scoped>

</style>

