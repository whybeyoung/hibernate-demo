import Vue from 'vue';
import IconSvg from '@/components/Icon-svg';// svg组件
import V2test from '@/views/clusters/v2';

// register globally
Vue.component('icon-svg', IconSvg);
Vue.component('v2test', V2test);

const requireAll = requireContext => requireContext.keys().map(requireContext);
const req = require.context('./svg', false, /\.svg$/);
requireAll(req);
