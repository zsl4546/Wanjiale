<template>
    <div>
      <div class="title">{{user.company}}采购申请</div>
      <el-form ref="form" label-width="80px" inline style="padding-bottom: 30px">
      <el-row :gutter="20">
        <el-col :span="8"><div class="grid-content bg-purple">客户名称:</div></el-col>
        <el-col :span="6"><div class="grid-content bg-purple">客户编码:</div></el-col>
        <el-col :span="6"><div class="grid-content bg-purple">区域:</div></el-col>
      </el-row>
      <el-row :gutter="20" style="padding-bottom: 25px">
        <el-col :span="8"><div class="grid-content1 bg-purple1">{{user.name}}</div></el-col>
        <el-col :span="6"><div class="grid-content1 bg-purple1">{{user.code}}</div></el-col>
        <el-col :span="6"><div class="grid-content1 bg-purple1">{{user.reg}}{{user.codename}}</div></el-col>
      </el-row>
        <el-row :gutter="20">
          <el-col :span="8"><div class="grid-content bg-purple">店铺名称:</div></el-col>
          <el-col :span="6"><div class="grid-content bg-purple">出货仓库:</div></el-col>
          <el-col :span="6"><div class="grid-content bg-purple">提货方式:</div></el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="8">
            <el-form-item>
            <el-select v-model=selectshop placeholder="请选择">
              <el-option v-for="item in user.shops" :key="item.shopid" :label="item.shopname" :value="item.shopid">{{item.shopname}}</el-option>
            </el-select>
          </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item>
            <el-select v-model=selecthouse placeholder="请选择">
              <el-option v-for="item in user.house" :key="item.id" :label="item.name" :value="item.id">{{item.name}}</el-option>
            </el-select>
          </el-form-item></el-col>
          <el-col :span="6"><div class="grid-content1 bg-purple1">配送</div></el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="8"><div class="grid-content bg-purple">备货需求时间段:</div></el-col>
          <el-col :span="6"><div class="grid-content bg-purple">备注:</div></el-col>
          <el-col :span="6"><div class="grid-content bg-purple"></div></el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="8">
            <el-date-picker
              v-model="user.date"
              type="daterange"
              range-separator="至"
              start-placeholder="开始日期"
              end-placeholder="结束日期">
            </el-date-picker>
          </el-col>
          <el-col :span="8">
              <el-input v-model="user.info"></el-input>
          </el-col>
        </el-row>


<!--        <el-button @click="printUser()">重置</el-button>-->
      </el-form>
      <el-button style="background-color: ButtonFace" @click="open()"><i class="el-icon-circle-plus-outline"></i> 新增</el-button>
      <el-table
        :data="order"
        border
        style="width: 69%">
        <el-table-column
          fixed
          prop="code"
          label="产品编码"
          width="150">
        </el-table-column>
        <el-table-column
          prop="model"
          label="产品型号"
          width="120">
        </el-table-column>
        <el-table-column
          prop="category"
          label="品质"
          width="120">
        </el-table-column>
        <el-table-column
          prop="num"
          label="数量"
          width="120">
        </el-table-column>
        <el-table-column
          prop="invoicing"
          label="开票价格"
          width="120">
        </el-table-column>
        <el-table-column
          prop="amount"
          label="金额"
          width="120">
        </el-table-column>
        <el-table-column
          prop="volume"
          label="体积"
          width="120">
        </el-table-column>
      </el-table>

      <el-dialog title="收货地址" :visible.sync="dialogFormVisible">
        <el-table
          ref="multipleTable"
          :data="product"
          tooltip-effect="dark"
          style="width: 100%"
          @selection-change="">
          <el-table-column
            type="selection"
            width="55">
          </el-table-column>
          <el-table-column
            label="日期"
            width="120">
          </el-table-column>
          <el-table-column
            prop="code"
            label="姓名"
            width="120">
          </el-table-column>
          <el-table-column
            prop="model"
            label="地址"
            show-overflow-tooltip>
          </el-table-column>
          <el-table-column
            label="数量"
          >
            <template slot-scope="scope"><el-input-number v-model="scope.row.num" :min="1" :max="100" @change="handleClick"></el-input-number></template>
          </el-table-column>
        </el-table>
        <div slot="footer" class="dialog-footer">
          <el-button @click="dialogFormVisible = false">取 消</el-button>
          <el-button type="primary" @click="dec()">增 加</el-button>
        </div>
      </el-dialog>
      <el-row :gutter="25" style="padding-top: 15px">
        <el-col :span="3"><div class="total">订单合计:</div></el-col>
        <el-col :span="3"><div class="total">| 总数量: {{totalNum}} 件</div></el-col>
        <el-col :span="3"><div class="total">总体积: {{totalVo}} m²</div></el-col>
        <el-col :span="3"><div class="total" style="color: red">总金额: {{totalPrice}} 元</div></el-col>
      </el-row>
      <el-row :gutter="10" >
        <el-col :span="2.5"><div class="total"><el-button type="primary" @click="submit(1)" style="text-align: center">提 交 订 单</el-button></div></el-col>
        <el-col :span="4"><el-button @click="submit(4)">保存至待处理列表</el-button></el-col>
      </el-row>
    </div>
</template>

<script>
  export default {
    data(){
      return{
        user: {
          id:'',
          name:'',
          code:'',
          reg:'',
          div:'',
          shops:[],
          house:[],
          date:'',
          info:'',
          codename: '',
          company: ''
        },
        num:'1',
        selectshop:'',
        selecthouse:'',
        product:[],
        order:[],
        temp:[],
        flag:false,
        dialogFormVisible: false,
        form: {
          name: '',
          region: '',
        },
        formLabelWidth: '120px',
        totalorder:{}
      }
    },
    watch:{
      selecthouse:function (val) {
        const _this = this;
        axios({
          type:'get',
          url:'http://localhost:8082/add/products?houseId='+val
        }).then(function (res) {
          _this.order = [];
          _this.temp = [];
          _this.product = res.data;
          _this.flag = false;
          console.log(res.data);
          console.log(_this.product)
        })
      }
    },
    computed:{
      totalPrice(){
        let sum = 0;
        for (let i = 0; i < this.order.length; ++i){
          sum += this.order[i].amount * this.order[i].num;
        }
        return sum;
      },
      totalNum(){
        let sum = 0;
        for (let i = 0; i < this.order.length; ++i){
          sum += this.order[i].num;
        }
        return sum;
      },
      totalVo(){
        let sum = 0;
        for (let i = 0;i < this.order.length; ++i){
          sum += this.order[i].volume * this.order[i].num;
        }
        return sum;
      }
    },
    methods:{
      totalPrice1(){
        let sum = 0;
        for (let i = 0; i < this.order.length; ++i){
          sum += this.order[i].amount * this.order[i].num;
        }
        return sum;
      },
      totalNum1(){
        let sum = 0;
        for (let i = 0; i < this.order.length; ++i){
          sum += this.order[i].num;
        }
        return sum;
      },
      totalVo1(){
        let sum = 0;
        for (let i = 0;i < this.order.length; ++i){
          sum += this.order[i].volume * this.order[i].num;
        }
        return sum;
      },
    getCurrentTime() {
      const time = Date.now();
      const today = new Date(time);
      return today.toISOString();
    },
      open(){
        this.dialogFormVisible = true;
        if(this.flag){
          this.temp = this.order;
        }else {
          this.temp = this.product;
          this.flag = true;
        }
      },
      submit(status){ //1为待审核， 2位待复审， 3为未通过， 4为未提交， 5为待上传附件
        let mess = '';

        if (this.selectshop === ''){
          mess = '请选择店铺'
        }else if (this.selecthouse === ''){
          mess = '请选择仓库'
        }else if (this.user.date === ''){
          mess = '请选择备货需求时间段'
        }
        if (mess !== ''){
          this.$notify.error({
            title: '错误',
            message: mess
          });
          return;
        }
        let message;
        if (status === 1){
          message = '确认提交订单？'
        }else if (status === 4){
          message = '确认保存至待处理列表？'
        }
          this.$confirm(message, '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning',
            center: true
          }).then(() => {
            console.log('***************');
            const { nanoid } = require("nanoid");
            let orderid = nanoid(8);
            for (let i = 0; i < this.order.length; ++i){
              this.order[i].userid = this.user.id;
              this.order[i].orderid = orderid;
              console.log(this.totalorder);
            }
            this.totalorder.start = this.user.date[0];
            this.totalorder.end = this.user.date[1];
            this.totalorder.shopid = this.selectshop;
            this.totalorder.house = this.selecthouse;
            this.totalorder.info = this.user.info;
            this.totalorder.orderid = orderid;
            this.totalorder.userid = this.user.id;
            this.totalorder.totalPrice = this.totalPrice1();
            this.totalorder.totalNum = this.totalNum1();
            this.totalorder.totalVolume = this.totalVo1();
            this.totalorder.status = status;
            console.log('******************')
            console.log(this.totalorder)
            const _this = this;
            axios({
              url: 'http://localhost:8082/add/submit',
              method: 'post',
              data: this.order,
              headers: {
                'Content-Type': 'application/json'
              }
            }).then(function (res) {
              axios({
                url:'http://localhost:8082/add/totalorder',
                method: 'post',
                data: _this.totalorder,
                headers :{
                  'Content-Type': 'application/json'
                }
              }).then(function (res) {
                _this.$router.push('/pending');
              })
            });
            this.$message({
              type: 'success',
              message: status === 1?'提交成功!':'保存成功!'
            });
          }).catch(() => {
            this.$message({
              type: 'info',
              message: status === 1?'已取消提交!':'已取消保存!'
            });
          });

    } ,
      dec(){
        this.dialogFormVisible = false;
        this.order=this.temp;
        console.log('order')
        console.log(this.order);
      },
      handleClick(val){
        console.log(this.product);
      },
    },
    name: "Add",
    created() {
      const _this = this
      axios({
        type:'get',
        url:'http://localhost:8082/z/load',
        xhrFields:{withCredentials: true}
      }).then(function (resp) {
        console.log(123)
        console.log(resp)
        _this.user.id = resp.data.id;
        _this.user.cc = resp.data.code;
        axios({
          type:'get',
          url:'http://localhost:8082/add/find/'+resp.data.id
        }).then(function (res) {
          _this.user.name = res.data.name;
          _this.user.code = res.data.code;
          _this.user.reg = res.data.reg;
          _this.user.shops = res.data.shops;
          axios({
            type:'get',
            url:'http://localhost:8082/add/houses'
          }).then(function (res) {
            console.log(res.data),
            _this.user.house = res.data;
            axios({
              type: 'get',
              url: 'http://localhost:8082/z/code/'+_this.user.cc
            }).then(function (res) {
              _this.user.codename = res.data.codename;
              _this.user.company = res.data.company;
              console.log(_this.user.company)
            })
          })
        })

        console.log(_this.user)
      })
    }
  }
</script>

<style scoped>
  .grid-content {
    color: darkgrey;
    font-family: "Adobe 仿宋 Std R";
    border-radius: 4px;
    min-height: 36px;
    line-height: 36px;
    font-size: small;
  }
  .row-bg {
    padding: 10px 0;
    background-color: #f9fafc;
  }
  .total {
    color: dimgray;
    font-family: 微軟正黑體;
    font-size: 13px;
    line-height: 39.6px;
  }
  .title {
    text-align: center;
    font-size: 23px;
    font-family: "Adobe 黑体 Std R";
    font-weight: bolder;
    padding-bottom: 20px;
    width: 900px;
  }
</style>
