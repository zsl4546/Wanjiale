import Vue from 'vue'
import VueRouter from 'vue-router'
import login from '../views/login.vue'
import index from '../views/index'
import chara from "../views/chara";
import welcome from "../views/welcome";
import manaNum from "../views/manaNum";
import accountInfo from "../views/accountInfo";
import changePassword from "../views/changePassword";
import S_account from "../views/S_account";
import member from "../views/member";
import dealerMana from "../views/dealerMana";


Vue.use(VueRouter)

  const routes = [
    {
      show:false,
      path:'/login',
      component: login
    },
    {
      identity: "1",
      show: true,
      isDealer: false,
      path: '/1',
      name: "系统配置",
      component: index,
      redirect: "/welcome",
      children:[
        {
          path: '/welcome',
          component: welcome,
          name: "欢迎"
        },
        {
          path: '/chara',
          component: chara,
          name: "运营角色管理"
        },
        {
          path: '/mana',
          component: manaNum,
          name: "运营账号管理"
        },
        {
          path: "/dealer",
          component: dealerMana,
          name: "经销商账户管理"
        }
      ]
    },
    {
      identity: "2",
      show: true,
      isDealer: true,
      path: '/2',
      name: "会员管理",
      component: index,
      redirect: "/welcome",
      children:[
        {
          path: '/welcome',
          component: welcome,
          name: "欢迎"
        },
        {
          path: '/accountInfo',
          component: accountInfo,
          name: "账户信息"
        },
        {
          path: '/changePassword',
          component: changePassword,
          name: "修改密码"
        },
        {
          path: '/s_account',
          component: S_account,
          name: "子账号管理"
        },
        {
          path: '/member',
          component: member,
          name: "会员信息"
        }
      ]
    }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
