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
    path: '/clusters',
    component: Layout,
    redirect: '/clusters/index',
    icon: 'cluster',
    name: '集群管理',
    displayName: '集群管理',
    noDropdown: false,
    children: [{
      path: 'index',
      name: '集群总览',
      icon: 'summary',
      displayName: '集群总览',
      component: dynamicImport('clusters/index'),
    }, {
      path: 'create',
      name: '新增集群',
      icon: 'new',
      displayName: '新增集群',
      component: dynamicImport('clusters/create'),
    }, {
      path: ':id/detail',
      name: '集群详情',
      hidden: true,
      component: dynamicImport('clusters/detail'),
    }, {
      path: ':id/edit',
      name: '集群编辑',
      hidden: true,
      component: dynamicImport('clusters/create'),
    }],
  },
  {
    path: '/servers',
    component: Layout,
    redirect: '/servers/index',
    icon: 'server',
    name: '主机管理',
    displayName: '主机管理',
    children: [{
      path: 'index',
      name: '主机列表',
      icon: 'list',
      displayName: '主机列表',
      component: dynamicImport('servers/index'),
    }, {
      path: 'create',
      name: '添加主机',
      icon: 'new',
      displayName: '添加主机',
      component: dynamicImport('servers/create'),
    }, {
      path: ':id/detail',
      name: 'servers.detail',
      displayName: '主机详情',
      component: dynamicImport('servers/detail'),
    }],
  },

  {
    path: '/image',
    component: Layout,
    redirect: '/image/index',
    icon: 'docker',
    name: '镜像管理',
    displayName: '镜像管理',
    children: [
      {
        path: 'index',
        name: '镜像列表',
        displayName: '镜像列表',
        icon: 'list',
        component: dynamicImport('image/index'),
      },
      {
        path: 'upload',
        name: '镜像上传',
        displayName: '镜像上传',
        icon: 'upload',
        component: dynamicImport('image/upload'),
      },
    ],
  },
  {
    path: '/log',
    component: Layout,
    redirect: '/log/operation',
    icon: 'log-manage',
    name: '日志管理',
    displayName: '日志管理',
    children: [
      {
        path: 'operation',
        name: '操作日志',
        displayName: '操作日志',
        icon: 'operation-log',
        component: dynamicImport('log/operation'),
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
