import NProgress from 'nprogress'; // Progress 进度条
import 'nprogress/nprogress.css';// Progress 进度条样式
import { getToken } from '@/utils/auth'; // 验权
import router from './router';
import store from './store';

const whiteList = ['/login'];
router.beforeEach((to, from, next) => {
  NProgress.start();
  if (getToken()) {
    if (to.path === '/login') {
      next({ path: '/' });
    } else {
      store.dispatch('GenerateRoutes').then(() => {
        router.addRoutes(store.getters.addRouters);
        next();
      });
    }
  } else if (whiteList.indexOf(to.path) !== -1) {
    next();
  } else {
    next('/login');
    NProgress.done();
  }
});

router.afterEach(() => {
  NProgress.done(); // 结束Progress
});
