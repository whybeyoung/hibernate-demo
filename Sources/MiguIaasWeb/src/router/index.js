import Vue from 'vue';
import Router from 'vue-router';
/* layout */
import Layout from '../views/layout/Layout';

// eslint-disable-next-line import/no-dynamic-require
const dynamicImport = require(`./_import_${process.env.NODE_ENV}`);
// in development env not use Lazy Loading,because Lazy Loading too many pages will cause webpack hot update too slow.so only in production use Lazy Loading

Vue.use(Router);

/**
  * icon : the icon show in the sidebar
  * hidden : if `hidden:true` will not show in the sidebar
  * redirect : if `redirect:noredirect` will not redirct in the levelbar
  * noDropdown : if `noDropdown:true` will not has submenu in the sidebar
  * meta : `{ role: ['admin'] }`  will control the page role
  * */
export const constantRouterMap = [
  { path: '/login', component: dynamicImport('login/index'), hidden: true },
  { path: '/404', component: dynamicImport('404'), hidden: true },
  {
    path: '/',
    component: Layout,
    redirect: '/clusters',
    hidden: true,
  },
  {
    path: '',
    component: Layout,
    redirect: '/clusters',
    icon: 'tubiao',
    noDropdown: true,
    children: [{
      path: 'clusters',
      name: '集群管理',
      component: dynamicImport('clusters/index'),
    }],
  },

  {
    path: '/image',
    component: Layout,
    redirect: '/image/index',
    icon: 'zujian',
    name: '镜像管理',
    children: [
      {
        path: 'index', name: '镜像列表', icon: 'zonghe', component: dynamicImport('image/index'), meta: { role: ['admin'] },
      },
      {
        path: 'upload', name: '镜像上传', icon: 'zonghe', component: dynamicImport('image/upload'), meta: { role: ['admin'] },
      },
    ],
  },

  { path: '*', component: dynamicImport('404'), hidden: true },
];

export default new Router({
  // mode: 'history', //后端支持可开
  scrollBehavior: () => ({ y: 0 }),
  routes: constantRouterMap,
});

export const asyncRouterMap = [];
