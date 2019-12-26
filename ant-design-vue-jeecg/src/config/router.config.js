import { UserLayout, TabLayout, RouteView, BlankLayout, PageView } from '@/components/layouts'
import FirstPage from '@/FirstPage'
import newscontent from '@/views/jeecg/newscontent'
import  newslist from '@/views/jeecg/newslist'
import docandtoolslist from '@/views/jeecg/docandtoolslist'
import docandtoolscontent from '@/views/jeecg/docandtoolscontent'
import docList from '@/views/jeecg/docList'
import aboutus from '@/views/jeecg/aboutus'
import consistencycontent from '@/components/consistency/consistencycontent'
import consistencywork from '@/views/jeecg/consistencywork'
/**
 * 走菜单，走权限控制
 * @type {[null,null]}
 */
export const asyncRouterMap = [

  {
    path: '/dashboard',
    name: 'dashboard',
    component: TabLayout,
    meta: { title: '首页' },
    redirect: '/dashboard/workplace',
    children: [

    ]
  },
  {
    path: '*', redirect: '/404', hidden: true
  }
]

/**
 * 基础路由
 * @type { *[] }
 */
export const constantRouterMap = [
  {
    path: '/',
    name: 'FirstPage',
    component: FirstPage,
  },
  {
    path: '/NewsContent/:aid',
    name: 'newscontent',
    component: newscontent,
  },
  {
    path: '/Docandtoolscontent/:aid',
    name: 'docandtoolscontent',
    component: docandtoolscontent,
  },
  {
    path: '/Consistencycontent/:aid',
    name: 'consistencycontent',
    component: consistencycontent,
  },

  {
    path: '/Newslist/',
    name: 'newslist',
    component: newslist,
  },
  {
    path: '/DocList/',
    name: 'docList',
    component: docList,
  },
  {
    path: '/Consistencywork/',
    name: 'consistencywork',
    component: consistencywork,
  },
  {
    path: '/Aboutus/',
    name: 'aboutus',
    component: aboutus,
  },
  {
    path: '/Docandtoolslist/',
    name: 'docandtoolslist',
    component: docandtoolslist,
  },
  {
    path: '/user',
    component: UserLayout,
    redirect: '/user/login',
    hidden: true,
    children: [
      {
        path: 'login',
        name: 'login',
        component: () => import(/* webpackChunkName: "user" */ '@/views/user/Login')
      },
      {
        path: 'register',
        name: 'register',
        component: () => import(/* webpackChunkName: "user" */ '@/views/user/Register')
      },
      {
        path: 'register-result',
        name: 'registerResult',
        component: () => import(/* webpackChunkName: "user" */ '@/views/user/RegisterResult')
      },
      {
        path: 'alteration',
        name: 'alteration',
        component: () => import(/* webpackChunkName: "user" */ '@/views/user/Alteration')
      },
    ]
  },

  {
    path: '/test',
    component: BlankLayout,
    redirect: '/test/home',
    children: [
      {
        path: 'home',
        name: 'TestHome',
        component: () => import('@/views/Home')
      }
    ]
  },
  {
    path: '/404',
    component: () => import(/* webpackChunkName: "fail" */ '@/views/exception/404')
  },

]
