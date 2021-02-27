<template>
  <div>
  <div>
    <el-form :inline="true" class="demo-form-inline">
      <el-form-item label="角色名称">
        <el-input placeholder="请输入" v-model="condition.name"></el-input>
      </el-form-item>
      <el-form-item label="状态">
        <el-select  placeholder="请选择" v-model="condition.status">
          <el-option label="全部" value=""></el-option>
          <el-option label="启用" value="1"></el-option>
          <el-option label="禁用" value="2"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="findCharas">查询</el-button>
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
      @selection-change="handleSelectionChange">
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
        prop="name"
        label="角色名称"
        width="240">
      </el-table-column>
      <el-table-column
        prop="status"
        :formatter="statusFormat"
        label="状态"
        width="120">
      </el-table-column>
      <el-table-column
        prop="description"
        label="描述"
        width="340">
      </el-table-column>
      <el-table-column
        label="操作"
        width="350">
<!--        scope.row代表的是这一行的数据-->
        <template slot-scope="scope">
          <el-button @click="handleClick(scope.row, '1')" type="button" size="small">启用</el-button>
          <el-button @click="handleClick(scope.row, '2')" type="button" size="small">禁用</el-button>
          <el-button @click="deleteChara(scope.row)" type="button" size="small">删除</el-button>
          <el-button @click="authority(scope.row)" type="button" size="small">分配权限</el-button>
        </template>
      </el-table-column>
    </el-table>


    <el-dialog title="分配权限" :visible.sync="authorityFormVisible" style="width: 50%; margin-left: 500px">
<!--      一行两列的布局-->
      <el-form>
        <el-row :gutter="15">

          <el-col :span="11">
            <div>机构权限</div>
          </el-col>
          <el-col :span="4">
            <div>页面权限</div>
          </el-col>
        </el-row>
        <el-row :gutter="15">

          <el-col :span="11">
            <el-checkbox-group v-model="mechanismList" @change="mechanismChange">
              <el-checkbox v-for="item in allMechanism" :label="item" :key="item" style="display: block; font-size: 40px">{{item}}</el-checkbox>
            </el-checkbox-group>
          </el-col>
          <el-col :span="4">
            <el-checkbox-group v-model="pageList" @change="pageChange">
              <el-checkbox v-for="item in allPage" :label="item" :key="item" style="display: block; font-size: 40px">{{item}}</el-checkbox>
            </el-checkbox-group>
          </el-col>
        </el-row>

      </el-form>

      <div slot="footer" class="dialog-footer">
        <el-button @click="authorityFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="authorityChange">确 定</el-button>
      </div>
    </el-dialog>

    <el-dialog title="新增角色" :visible.sync="addFormVisible" style="width: 50%; margin-left: 500px">

      <el-form>
        <el-form label-width="80px" :model="addChara">
          <el-form-item label="角色名称">
            <el-input v-model="addChara.name"></el-input>
          </el-form-item>
          <el-form-item label="角色描述">
            <el-input v-model="addChara.description"></el-input>
          </el-form-item>
          <el-form-item label="状态">
            <el-radio v-model="addChara.status" label="1">启用</el-radio>
            <el-radio v-model="addChara.status" label="2">禁用</el-radio>
          </el-form-item>
        </el-form>
      </el-form>

      <div slot="footer" class="dialog-footer">
        <el-button @click="addFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="addChange">确 定</el-button>
      </div>
    </el-dialog>

</div>

</template>

<script>
  import qs from 'qs'
  export default {
    name: "chara",
    methods: {
      handleClick(row, status) { //修改状态为启用或者禁用
        row.status = status
        axios({
          url: 'http://localhost:8080/operation/changeStatus',
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
          url: 'http://localhost:8080/operation/add',
          method: 'post',
          data: qs.stringify(_this.addChara)
        }).then(function (res) {
          console.log('add')
          _this.findCharas()
          _this.$message({
            message: '添加成功！',
            type: 'success'
          });
        })
        _this.addChara = {}
        _this.addFormVisible = false
      },

      deleteChara(row){ //单个删除角色
        const _this = this
        this.$confirm('确定删除该角色?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          //如果点击确定就向后端发送请求
          axios({
            url: 'http://localhost:8080/operation/deleteChara',
            method: 'get',
            params: row
          }).then(function (res) {
            console.log('删除角色')
            _this.findCharas() //重新加载角色数据
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

      findCharas(){ //根据condition向后台查询所有角色数据
        const _this = this
        axios({
          url:'http://localhost:8080/operation/findCharas',
          method: 'get',
          params: _this.condition
        }).then(function (res) {
          _this.tableData = res.data
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
            url: 'http://localhost:8080/operation/manyStatus',
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
              url: 'http://localhost:8080/operation/manyDelete',
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
      authority(row){
        const _this = this
        axios({
          url: 'http://localhost:8080/operation/findMechanism',
          method: 'get',
          params: row
        }).then(function (res) {
          _this.mechanismList = res.data
          axios({
            url: 'http://localhost:8080/operation/findPage',
            method: 'get',
            params: row
          }).then(function (response) {
            _this.localRow = row       //localRow用于记录是哪一个角色的权限窗口被打开
            _this.pageList = response.data
            _this.authorityFormVisible = true
          })
        })
      },
      authorityChange(){
        const _this = this
        axios({
          url: 'http://localhost:8080/operation/insertMechanism/'+_this.localRow.name,
          method: 'post',
          data: _this.mechanismList
        }).then(function (res) {
          axios({
            url: 'http://localhost:8080/operation/insertPage/'+_this.localRow.name,
            method: 'post',
            data: _this.pageList
          }).then(function (res) {
            console.log('wanceng')
            _this.authorityFormVisible = false
          })

        })
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
    },
    created() {
      const _this = this
      _this.findCharas();
      axios({
        url: 'http://localhost:8080/operation/findAllMechanism',
        method: 'get'
      }).then(function (res) {

        _this.allMechanism = res.data
        console.log('all-mechanism')
        console.log(_this.allMechanism)
      })
      axios({
        url: 'http://localhost:8080/operation/findAllPage',
        method: 'get'
      }).then(function (res) {
        _this.allPage = res.data
        console.log('all-page')
        console.log(_this.allPage)
      })

    },

    data() {
      return {
        tableData: [],
        multipleSelection:[],
        condition:{ //绑定需要筛选的名字和状态
          name: '',
          status: ''
        },
        addChara:{
          name:'',
          status:'',
          description:''
        },
        authorityFormVisible:false,
        addFormVisible:false,
        localRow:'',
        mechanismList:[],
        pageList:[],
        allMechanism: [],
        allPage: []
      }
    }
  }
</script>

<style scoped>

</style>
