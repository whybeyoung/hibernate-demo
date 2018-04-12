import NProgress from 'nprogress'; // Progress 进度条
import 'nprogress/nprogress.css';// Progress 进度条样式
import router from './router';
import store from './store';

const whiteList = ['/login'];
router.beforeEach((to, from, next) => {
  NProgress.start();
  if (store.getters.user.id) {
    if (to.path === '/login') {
      next({ path: '/' });
    } else {
      next();
    }
  } else if (whiteList.indexOf(to.path) !== -1) {
    next();
  } else {
    store.dispatch('GetCurrentUserInfo').then(() => {
      store.dispatch('GenerateRoutes').then(() => {
        router.addRoutes(store.getters.addRouters);
        next({ ...to });
      }).catch(() => {
        next('/login');
      });
    });
  }
});

router.afterEach(() => {
  NProgress.done(); // 结束Progress
});
