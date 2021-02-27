<template>
  <div>
    <div>
      <el-form :inline="true" class="demo-form-inline">
        <el-form-item label="账号">
          <el-input placeholder="请输入" v-model="condition.account"></el-input>
        </el-form-item>

        <el-form-item label="电话">
          <el-input placeholder="请输入" v-model="condition.phone"></el-input>
        </el-form-item>

        <el-form-item label="机构">
          <el-input placeholder="请输入" v-model="condition.mechanism"></el-input>
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
        label="账号"
        width="150">
      </el-table-column>
      <el-table-column
        prop="name"
        label="姓名"
        width="120">
      </el-table-column>
      <el-table-column
        prop="charaName"
        label="角色"
        width="120">
      </el-table-column>
      <el-table-column
        prop="mechanism"
        label="机构"
        width="120">
      </el-table-column>
      <el-table-column
        :formatter="statusFormat"
        prop="status"
        label="状态"
        width="120">
      </el-table-column>
      <el-table-column
        prop="sex"
        label="性别"
        width="120">
      </el-table-column>
      <el-table-column
        prop="phone"
        label="电话"
        width="300">
      </el-table-column>
      <el-table-column
        prop="mail"
        label="邮箱"
        width="120">
      </el-table-column>
      <el-table-column
        fixed="right"
        label="操作"
        width="350">
        <template slot-scope="scope">
          <el-button @click="handleClick(scope.row, '1')" type="button" size="small">启用</el-button>
          <el-button @click="handleClick(scope.row, '2')" type="button" size="small">禁用</el-button>
          <el-button @click="deleteUser(scope.row.account)" type="button" size="small">删除</el-button>
          <el-button @click="edit(scope.row.account)" type="button" size="small">编辑</el-button>
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
        <el-form-item label="密码" prop="password">
          <el-input v-model="tempUser.password" type="password"></el-input>
        </el-form-item>
        <el-form-item label="所属机构" prop="mechanism">
          <el-select v-model="tempUser.mechanism" placeholder="请选择">
            <el-option v-for="item in mechanisms" :label=item :value=item></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="角色" prop="charaName">
          <el-select v-model="tempUser.charaName" placeholder="请选择">
            <el-option v-for="item in CharaNames" :label=item :value=item></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="性别" prop="sex">
          <el-radio-group v-model="tempUser.sex">
            <el-radio label="男"></el-radio>
            <el-radio label="女"></el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="手机号码" prop="phone">
          <el-input v-model="tempUser.phone"></el-input>
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

    <el-dialog title="新增账户" :visible.sync="editFormVisible" style="width: 50%; margin-left: 500px">
      <el-form :model="addUser" :rules="rules" ref="tempUser" label-width="100px" class="demo-ruleForm">
        <el-form-item label="用户名" prop="account">
          <el-input v-model="addUser.account"></el-input>
        </el-form-item>
        <el-form-item label="真实姓名" prop="name">
          <el-input v-model="addUser.name"></el-input>
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input v-model="addUser.password" type="password"></el-input>
        </el-form-item>
        <el-form-item label="所属机构" prop="mechanism">
          <el-select v-model="addUser.mechanism" placeholder="请选择">
            <el-option v-for="item in mechanisms" :label=item :value=item></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="角色" prop="charaName">
          <el-select v-model="addUser.charaName" placeholder="请选择">
            <el-option v-for="item in CharaNames" :label=item :value=item></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="性别" prop="sex">
          <el-radio-group v-model="addUser.sex">
            <el-radio label="男"></el-radio>
            <el-radio label="女"></el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="手机号码" prop="phone">
          <el-input v-model="addUser.phone"></el-input>
        </el-form-item>
        <el-form-item label="电子邮件" prop="mail">
          <el-input v-model="addUser.mail"></el-input>
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-radio-group v-model="addUser.status">
            <el-radio label="1">启用</el-radio>
            <el-radio label="2">禁用</el-radio>
          </el-radio-group>
        </el-form-item>

        <el-form-item>
          <el-button type="primary" @click="editButton">修改</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>

  </div>
</template>

<script>
  import qs from 'qs'
    export default {
      name: "manaNum",
      created() {
        const _this = this
        this.findUser();
        axios({
          url: 'http://localhost:8080/operation/findAllCharaName',
          method: 'get',
        }).then(function (res) {
          _this.CharaNames = res.data
          console.log(_this.CharaNames)
        })
        axios({
          url: 'http://localhost:8080/operation/findAllMechanism',
          method: 'get',
        }).then(function (res) {
          _this.mechanisms = res.data
          console.log(_this.mechanisms)
        })
      },
      data() {
        return {
          tableData: [],
          CharaNames: [],
          mechanisms: [],
          condition:{
            account:'',
            phone:'',
            mechanism:'',
            status:''
          },
          multipleSelection:[],
          addFormVisible: false,
          tempUser:{
            account:'',
            name:'',
            password:'',
            mechanism:'',
            charaName: '',
            sex: '',
            phone:'',
            mail:'',
            status:''
          },
          editFormVisible: false,
          editAccount:'',
          addUser:{
            account:'',
            name:'',
            password:'',
            mechanism:'',
            charaName: '',
            sex: '',
            phone:'',
            mail:'',
            status:''
          },
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
            mechanism: [
              { required: true, message: '请选择所属机构', trigger: 'change' }
            ],
            charaName: [
              { required: true, message: '请选择角色', trigger: 'change' }
            ],
            phone: [
              { required: true, message: '请输入用户名', trigger: 'blur' }
            ],
          }
        }
      },
      methods: {
        handleClick(row, status) { //修改状态为启用或者禁用
          row.status = status
          axios({
            url: 'http://localhost:8080/login/changeStatus',
            method: 'get',
            params: row
          }).then(function (res) {
            console.log('修改status')
            console.log(row)
          })
        },

        addChange(){
          const _this = this
          axios({
            url: 'http://localhost:8080/login/add',
            method: 'post',
            data: qs.stringify(_this.addUser)
          }).then(function (res) {
            console.log('add')
            _this.findUser()
            _this.$message({
              message: '添加成功！',
              type: 'success'
            });
          })
          _this.addChara = {}
          _this.addFormVisible = false
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
              url: 'http://localhost:8080/login/deleteUser',
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
          const loading = this.$loading({
            lock: true,
            text: 'Loading',
            spinner: 'el-icon-loading',
            background: 'rgba(0, 0, 0, 0.7)'
          });
          axios({
            url:'http://localhost:8080/login/findAllUser',
            method: 'get',
            params: _this.condition
          }).then(function (res) {
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
              url: 'http://localhost:8080/login/manyDelete',
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
          axios({
            url: 'http://localhost:8080/login/addUser',
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
          const loading = this.$loading({
            lock: true,
            text: 'Loading',
            spinner: 'el-icon-loading',
            background: 'rgba(0, 0, 0, 0.7)'
          });
          axios({
            url: 'http://localhost:8080/login/account/'+account,
            method: 'get',
          }).then(function (res) {
            _this.addUser = res.data
            _this.editAccount = account
            _this.editFormVisible = true
            loading.close()
            console.log(0)
            console.log(_this.editUser)
            console.log(_this.editAccount)
          })
        },
        editButton(){
          const _this = this
          const loading = this.$loading({
            lock: true,
            text: 'Loading',
            spinner: 'el-icon-loading',
            background: 'rgba(0, 0, 0, 0.7)'
          });
          let data = {"account":_this.editAccount}
          axios({
            url: 'http://localhost:8080/login/deleteUser',
            method: 'get',
            params: data
          }).then(function (res) {
            axios({
              url: 'http://localhost:8080/login/addUser',
              method: 'post',
              data: JSON.stringify(_this.addUser),
              headers:{
                'Content-Type': 'application/json'
              }
            }).then(function (res) {
              console.log(res.data)
              if (res.data === 1){
                _this.editFormVisible = false
                _this.$message({
                  message: '修改成功！',
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
          })
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
          if (row.status === '1') {
            return '启用'
          } else if (row.status === '2') {
            return '禁用'
          }
        },
      }

    }
</script>

<style scoped>

</style>
