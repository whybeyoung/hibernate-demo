import Vue from 'vue';
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import locale from 'element-ui/lib/locale/lang/en';
import App from '@/App';
import router from '@/router';
import store from '@/store';
import moment from 'moment';
import VueMomentJS from 'vue-momentjs';
import '@/icons'; // icon
import '@/permission'; // 权限
import ViserVue from 'viser-vue';
import networkChart from '@/components/network-chart';

Vue.use(ElementUI, { locale });
Vue.use(VueMomentJS, moment);
Vue.use(ViserVue);
Vue.component('network-chart', networkChart);

Vue.config.productionTip = false;
// eslint-disable-next-line no-new
new Vue({
  el: '#app',
  router,
  store,
  template: '<App/>',
  components: { App },
});
