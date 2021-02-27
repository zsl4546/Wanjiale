<template>
  <div>
    <el-row :gutter="20">
      <el-col :span="5" class="content">
        销售订单号:
      </el-col>
      <el-col :span="5" style="padding: 0" class="content">订单日期:
      </el-col>
      <el-col :span="4">

      </el-col>
      <el-col :span="2" style="padding: 0" class="content">

      </el-col>
      <el-col :span="4">
      </el-col>
    </el-row>

    <el-row :gutter="20">
      <el-col :span="5"><div>
        <el-input v-model="select.orderid" placeholder="请输入内容" style="width: 80%"></el-input>
      </div></el-col>
      <el-col :span="1"></el-col>
      <el-col :span="4" style="padding: 0"><div>
        <el-date-picker
          v-model="select.timestart"
          type="date"
          placeholder="选择日期"
          value-format="yyyy-MM-dd HH:mm:ss"
          style="width: 95%;">
        </el-date-picker>-
      </div></el-col>
      <el-col :span="4" style="padding: 0"><div>
        <el-date-picker
          v-model="select.timeend"
          type="date"
          placeholder="选择日期"
          value-format="yyyy-MM-dd HH:mm:ss"
          style="width: 95%; padding: 0">
        </el-date-picker>
      </div></el-col>
      <el-col :span="4" style="padding-left: 50px"><div>
<!--        <el-select-->
<!--          v-model="select.status" placeholder="请选择" clearable><el-option-->
<!--          v-for="item in options"-->
<!--          :key="item.value"-->
<!--          :label="item.label"-->
<!--          :value="item.value">-->
<!--        </el-option></el-select>-->
      </div></el-col>
    </el-row>
    <el-row :gutter="20">
      <el-col :span="5" class="content" style="margin-top: 15px;">
        订单金额:
      </el-col>
    </el-row>
    <el-row :gutter="20" style="margin-bottom: 15px">
      <el-col :span="4" class="content" style="padding-right: 0">
        <div><el-input v-model="select.pricelow" placeholder="请输入最小金额" style="width: 95%" type="number"></el-input>-</div>
      </el-col>
      <el-col :span="4" style="padding: 0" class="content">
        <el-input v-model="select.pricehigh" placeholder="请输入最大金额" style="width: 95%" type="number"></el-input>
      </el-col>
      <el-col :span="4" style="padding: 0">
        <el-button type="primary" @click="sele">查 询</el-button>
      </el-col>
    </el-row>

    <el-table
      v-if="1===1"
      :data="orders"
      border
      style="width: 100%">
      <el-table-column
        fixed
        type="index"
        width="50">
      </el-table-column>
      <el-table-column
        prop="ordertime"
        label="订单日期"
        width="160">
        <template slot-scope="scope">{{getLocalTime(scope.row.ordertime)}}</template>
      </el-table-column>
      <el-table-column
        prop="orderid"
        label="订单编号"
        width="120">
      </el-table-column>
      <el-table-column
        label="配送方式"
        width="120">
        配送
      </el-table-column>
      <el-table-column label="状态" prop="status" :formatter="stateFormat" width="120"></el-table-column>
      <el-table-column
        prop="name"
        label="联系人"
        width="120">
      </el-table-column>
      <el-table-column
        prop="account"
        label="联系电话"
        width="120">
      </el-table-column>
      <el-table-column
        prop="totalNum"
        label="数量"
        width="80">
      </el-table-column>
      <el-table-column
        prop="totalPrice"
        label="订单金额"
        width="80">
      </el-table-column>
      <el-table-column
        prop="shopname"
        label="店铺"
        width="120">
      </el-table-column>
      <el-table-column
        prop="housename"
        label="仓库"
        width="120">
      </el-table-column>
      <el-table-column
        prop="start"
        label="时间段初"
        width="160">
        <template slot-scope="scope">{{getLocalTime(scope.row.start)}}</template>
      </el-table-column>
      <el-table-column
        prop="end"
        label="时间段末"
        width="160">
        <template slot-scope="scope">{{getLocalTime(scope.row.end)}}</template>
      </el-table-column>

      <el-table-column
        fixed="right"
        label="操作"
        width="130">
        <template slot-scope="scope">
          <el-button @click="handleClick(scope.row)" type="text" size="small"><i class="el-icon-s-order" style="font-size: 25px; color: red" @click="open(scope.row)"></i></el-button>
          <el-button type="text" size="small"><i class="el-icon-view" style="font-size: 25px;" @click="view1(scope.row.orderid)"></i></el-button>
          <el-button type="text" size="small"><i class="el-icon-download" style="font-size: 25px;" @click="viewFiles(scope.row)"></i></el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-dialog title="审核" :visible.sync="dialogFormVisible">
      <el-form>
        <el-form-item label="审核理由">
          <el-input v-model="content1"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="cancel1()">不 通 过</el-button>
        <el-button type="primary" @click="confirm1()">审 核 通 过</el-button>
      </div>
    </el-dialog>


    <el-dialog title="产品列表" :visible.sync="viewVisible">
      <!--      <div style="margin-left: 10px">订单编号：{{this.detail[0].orderid}}</div>-->
      <el-table :data="this.detail">
        <el-table-column property="code" label="产品编号" width="100"></el-table-column>
        <el-table-column property="model" label="型号" width="100"></el-table-column>
        <el-table-column property="num" label="数量"></el-table-column>
        <el-table-column property="category" label="品类"></el-table-column>
        <el-table-column property="invoicing" label="开票单价"></el-table-column>
        <el-table-column property="amount" label="金额单价"></el-table-column>
        <el-table-column property="volume" label="体积"></el-table-column>
      </el-table>
    </el-dialog>


    <el-dialog title="附件列表" :visible.sync="filesVisible">
      <div v-for="(item) in fileNames">
        <el-row>
          <el-col>
          <el-link type="primary" @click="tst(item)">{{item}}</el-link>
          </el-col>
        </el-row>
        <el-row>
<!--          <hr style="filter: alpha(opacity=100, finishopacity=0, style=3)" width="80%" color=#987cb9 SIZE=3>-->
          <hr style="color: #909399; width: 50%; float: left">
        </el-row>
      </div>
    </el-dialog>


  </div>
</template>

<script>
  import axios from "axios";
  import qs from "qs"

  export default {
    computed:{

    },
    created() {
      const _this = this;
      console.log(this)
      this.sele()
    },
    methods: {
      Download(path, name){
        let link = document.createElement("a"); //建立a标签
        link.style.display = "none"; //使其隐藏
        link.href = path; //赋予文件下载地址
        link.setAttribute("download", name); //设置下载属性 以及文件名
        document.body.appendChild(link); //a标签插至页面中
        link.click(); //强制触发a标签事件
        link.remove()
      },

      tst(name){
        const _this = this
        console.log(name)
        let param = {};
        param.orderid = this.row.orderid;
        param.fileName = name;
        let url = 'http://localhost:8082/file/download?orderid='+_this.row.orderid+'&fileName='+name;
        console.log(url)
        this.Download(url, name)
      },
      viewFiles(row){
        this.row = row
        this.names(row.orderid);
        this.filesVisible = true;
      },
      names(orderid){
        const _this = this
        axios({
          type: 'get',
          url: 'http://localhost:8082/file/findFileNames/'+orderid,
        }).then(function (res) {
          _this.fileNames = res.data
          console.log(_this)
          console.log(_this.fileNames)
        })
      },
      stateFormat(row) {
        if (row.status === 1) {
          return '待审核'
        } else if (row.status === 2) {
          return '待复审'
        } else if (row.status === 3) {
          return '未通过'
        } else if (row.status === 4) {
          return '未提交'
        } else if (row.status === 5){
          return '待上传附件'
        }else {
          return '其他'
        }
      },
      view1(orderid){
        const _this = this
        axios({
          type: 'get',
          url: 'http://localhost:8082/review/detail/'+orderid
        }).then(function (res) {
          console.log(res)
          _this.detail = res.data
          _this.viewVisible = true
        })
      },
      open(row){
        this.row = row;
        this.dialogFormVisible = true
      },

      cancel1(){
        this.row.status = 3;
        if (this.content1 === ''){
          this.content1 = '审核不通过'
        }
        this.sta(this.row.orderid, 3, this.content1);
        this.dialogFormVisible = false
        this.sele()
      },

      confirm1(){
        this.row.status = 6;
        if (this.content1 === ''){
          this.content1 = '审核通过'
        }
        this.sta(this.row.orderid, 6, this.content1);
        this.dialogFormVisible = false
        this.sele()
      },

      sta(orderid, status, content){
        axios({
          url: 'http://localhost:8082/review/first/'+orderid +'/' + status +'/'+ content,
          method: 'get'
        }).then(function (res) {
          console.log("success-" + orderid + "-"+ status);
        })
      },
      // review1(row){
      //   this.$prompt('请审核理由', '提示', {
      //     cancelButtonClass: 'cancel',
      //     confirmButtonClass: 'confirm',
      //     confirmButtonText: '不通过',
      //     cancelButtonText: '审核通过',
      //   }).then(({ value }) => {
      //     row.status = 3;
      //     this.sta(row.orderid, 3, value);
      //     this.$message({
      //       type: 'success',
      //       message: '订单'+row.orderid+' 初审不通过'
      //     });
      //   }).catch(() => {
      //     row.status = 2;
      //     this.sta(row.orderid, 2, '初审通过');
      //     this.$message({
      //       type: 'success',
      //       message: '订单'+row.orderid+' 初审通过'
      //     });
      //   });
      // },

      sele() {
        const _this = this;
        this.temp = Object.assign({}, this.select)
        console.log("11111111")
        console.log(this.temp)
        delete this.temp.userid;
        this.temp.status = 2
        if (this.temp.orderid === null) {
          delete this.temp.orderid;
        }
        if (this.temp.timestart === null) {
          delete this.temp.timestart;
        }else {
          console.log('timestart')
          console.log(this.temp.timestart)
        }
        // else {
        //   this.temp.timestart = this.convertTime(this.temp.timestart)
        //   this.temp.timestart = this.temp.timestart.substr(0,10);
        // }
        if (this.temp.timeend === null) {
          delete this.temp.timeend;
        }
        // else {
        //   this.temp.timeend = this.convertTime(this.temp.timeend)
        //   this.temp.timeend = this.temp.timeend.substr(0,10);
        // }
        if (this.temp.status === null || this.temp.status === '') {
          delete this.temp.status;
        }
        if (this.temp.pricelow === null || this.temp.pricelow === '') {
          delete this.temp.pricelow;
        }
        if (this.temp.pricehigh === null || this.temp.pricehigh === '') {
          delete this.temp.pricehigh;
        }

        axios({
          url: 'http://localhost:8082/add/findtotal',
          type: 'get',
          params: this.temp
        }).then(function (response) {
          _this.orders = response.data;
          console.log(_this.orders);
        });

        // alert(key+':'+json[i][key]);
      }
      ,
      convertTime(time){
        let y = time.getFullYear();
        let m = time.getMonth() + 1;
        m = m < 10 ? ('0' + m) : m;
        let d = time.getDate();
        d = d < 10 ? ('0' + d) : d;
        return y + '-' + m + '-' + d;
      },
      getLocalTime(time) {
        var time = new Date(time);
        var y = time.getFullYear();
        var m = time.getMonth()+1;
        var d = time.getDate();
        var h = time.getHours();
        var mm = time.getMinutes();
        var s = time.getSeconds();
        return y+'-'+this.add0(m)+'-'+this.add0(d)+' '+this.add0(h)+':'+this.add0(mm)+':'+this.add0(s);
        // return this.orders.ordertime + '' + '###';
      },
      add0(m){return m<10?'0'+m:m },
      handleClick(row) {
        console.log(row);
      }
    },

    data() {
      return {
        orders:[],
        select:{
          userid:'1',
          orderid:'',
          timestart:'',
          timeend:'',
          status:'',
          pricelow: '',
          pricehigh: ''
        },
        content1:'',
        temp:{
        },
        dialogFormVisible: false,
        viewVisible: false,
        row:'',
        detail:'',
        options: [{
          value: '1',
          label: '待审核'
        }, {
          value: '2',
          label: '待复审'
        }, {
          value: '3',
          label: '未通过'
        }, {
          value: '5',
          label: '待上传附件'
        }],
        value: [],
        fileNames:'',
        filesVisible: false,
        myFile:{}
      }
    }
  }
</script>
<style>
  .content {
    color: darkgrey;
    font-family: "Adobe 仿宋 Std R";
    font-size: small;
  }
  .cancel{
    color: #FFF !important;
    background-color: #409EFF !important;
    border-color: #409EFF !important;
    width: 100px;
    height: 40px;
  }
  .confirm{
    background-color: #909399 !important;
    border-color: #909399 !important;
    width: 100px;
    height: 40px;
  }
  .el-message-box__btns {
    text-align: center !important;
  }
</style>
