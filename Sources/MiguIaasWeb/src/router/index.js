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
    name: 'clusters',
    noDropdown: false,
    meta: {
      displayName: '集群管理',
    },
    children: [{
      path: 'index',
      name: 'clusters.index',
      icon: 'summary',
      component: dynamicImport('clusters/index'),
      meta: {
        displayName: '集群总览',
      },
    }, {
      path: 'create',
      name: 'clusters.create',
      icon: 'new',
      component: dynamicImport('clusters/create'),
      meta: {
        displayName: '新增集群',
      },
    }, {
      path: ':id/detail',
      name: 'clusters.detail',
      hidden: true,
      component: dynamicImport('clusters/detail'),
    }, {
      path: ':id/edit',
      name: 'clusters.edit',
      hidden: true,
      component: dynamicImport('clusters/create'),
      meta: {
        displayName: '集群编辑',
      },
    }],
  },
  {
    path: '/servers',
    component: Layout,
    redirect: '/servers/index',
    icon: 'server',
    name: 'servers',
    meta: {
      displayName: '主机管理',
    },
    children: [{
      path: 'index',
      name: 'servers.index',
      icon: 'list',
      component: dynamicImport('servers/index'),
      meta: {
        displayName: '主机列表',
      },
    }, {
      path: 'create',
      name: 'servers.create',
      icon: 'new',
      component: dynamicImport('servers/create'),
      meta: {
        displayName: '添加主机',
      },
    }, {
      path: ':id/detail',
      name: 'servers.detail',
      component: dynamicImport('servers/detail'),
      meta: {
        displayName: '主机详情',
      },
    }],
  },

  {
    path: '/image',
    component: Layout,
    redirect: '/image/index',
    icon: 'docker',
    name: 'image',
    meta: {
      displayName: '镜像管理',
    },
    children: [
      {
        path: 'index',
        name: 'image.list',
        icon: 'list',
        component: dynamicImport('image/index'),
        meta: {
          displayName: '镜像列表',
        },
      },
      {
        path: 'upload',
        name: 'image.upload',
        icon: 'upload',
        component: dynamicImport('image/upload'),
        meta: {
          displayName: '镜像上传',
        },
      },
    ],
  },
  {
    path: '/nss',
    component: Layout,
    redirect: '/nss/index',
    icon: 'iconfontspace',
    name: 'namespace',
    meta: {
      displayName: '命名空间',
    },
    children: [
      {
        path: 'index',
        name: 'nss.list',
        icon: 'list',
        component: dynamicImport('namespace/index'),
        meta: {
          displayName: '命名空间',
        },
      },
    ],
  },
  {
    path: '/deploy',
    component: Layout,
    redirect: '/deploy/index',
    icon: 'xitongbushu',
    name: 'deploy',
    meta: {
      displayName: '部署管理',
    },
    children: [
      {
        path: 'index', name: 'deploy.apps', icon: 'list', component: dynamicImport('deploy/index'), meta: { role: ['admin'], displayName: '应用列表' },
      },
      {
        path: 'app', name: 'deploy.appnew', icon: 'app', component: dynamicImport('deploy/createApp'), meta: { role: ['admin'], displayName: '新建应用' },
      },
      {
        path: 'depAdd', name: 'deploy.depAdd', hidden: true, icon: 'zonghe', component: dynamicImport('deploy/addDeploy'), meta: { role: ['admin'], displayName: '添加镜像部署' },
      },
    ],
  },
  {
    path: '/log',
    component: Layout,
    redirect: '/log/operation',
    icon: 'log-manage',
    name: '日志管理',
    meta: {
      displayName: '日志管理',
    },
    children: [
      {
        path: 'operation',
        name: '操作日志',
        icon: 'operation-log',
        component: dynamicImport('log/operation'),
        meta: {
          displayName: '操作日志',
        },
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
