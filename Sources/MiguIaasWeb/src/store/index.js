import Vue from 'vue';
import Vuex from 'vuex';
import app from './modules/app';
import user from './modules/user';
import image from './modules/image';
import deploy from './modules/deploy';
import permission from './modules/permission';
import getters from './getters';

Vue.use(Vuex);

const store = new Vuex.Store({
  modules: {
    app,
    user,
    image,
    deploy,
    permission,
  },
  getters,
});

export default store;
