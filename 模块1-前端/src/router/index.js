import Vue from 'vue'
import VueRouter from 'vue-router'
import Review from "../views/Review.vue";
import Pending from "../views/Pending.vue";
import PageThree from "../views/login";
import Add from "../views/Add";
import Rehear from "../views/Rehear";
import App from "../App";
import index from "../views/index.vue"
import About from "../views/About"
import File from "../views/File"
import Home from "../views/Home";
import Total from "../views/Total";
import Complete from "../views/Complete";

Vue.use(VueRouter)

  const routes = [

    {
      tag:'1',
      path: "/1",
      name:"订单管理",
      component:index,
      show:true,
      redirect:"/welcome",
      children:[
        {
          path: "/welcome",
          name: "欢迎",
          component: About
        },
        {
          path: "/add",
          name: "提交备货订单",
          component: Add
        },
        {
          path: "/pending",
          name: "待处理订单",
          component: Pending
        },
        {
          path: '/file',
          name: '采购订单',
          component: File
        },
        {
          path: '/complete',
          name: "已通过订单",
          component: Complete
        }
      ]
    },
    {
      tag:'2',
      path: "/2",
      name:"订单管理",
      component:index,
      show:true,
      redirect:"/welcome",
      children:[
        {
          path: "/welcome",
          name: "欢迎",
          component: About
        },
        {
          path: "/pageOne",
          name: "查询图书",
          component: PageThree
        },
        {
          path: "/pageTwo",
          name: "添加图书",
          component: About
        },
      ]
    },
    {
      tag:'3',
      path: "/3",
      name:"订单审核",
      component:index,
      show:true,
      redirect:"/welcome",
      children:[
        {
          path: "/welcome",
          name: "欢迎",
          component: About
        },
        {
          path: "/Review",
          name: "待审核备货计划列表",
          component: Review
        },
        {
          path: "/Rehear",
          name: "待复审备货计划列表",
          component: Rehear
        },
        {
          path: "/plan",
          name: "总订单计划表",
          component: Total
        }
      ]
    },
    {
        path: '/',
        component: PageThree,
        // show: true
    },
    {
      path: '/file1',
      component: Home
    }

]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
