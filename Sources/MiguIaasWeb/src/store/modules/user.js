import { login, logout, getInfo } from '@/api/login';

const user = {
  state: {
    avatar: '',
    id: '',
    account: '',
    nickname: '',
    permissions: [],
  },

  mutations: {
    SET_USER: (state, userInfo) => {
      state.id = userInfo.id;
      state.account = userInfo.account;
      state.nickname = userInfo.nickname;
      state.permissions = userInfo.permissions;
    },
    RESET_USER: (state) => {
      state.id = '';
      state.account = '';
      state.nickname = '';
      state.permissions = '';
    },
  },

  actions: {
    // 登录
    Login({ commit }, userInfo) {
      const username = userInfo.username.trim();
      return new Promise((resolve, reject) => {
        login(username, userInfo.password).then((response) => {
          commit('SET_USER', response);
          resolve();
        }).catch((error) => {
          reject(error);
        });
      });
    },

    // 获取用户信息
    GetCurrentUserInfo({ commit }) {
      return new Promise((resolve, reject) => {
        getInfo().then((response) => {
          commit('SET_USER', response);
          resolve(response);
        }).catch((error) => {
          reject(error);
        });
      });
    },

    // 登出
    LogOut({ commit, state }) {
      return new Promise((resolve, reject) => {
        logout(state.token).then(() => {
          commit('RESET_USER');
          resolve();
        }).catch((error) => {
          reject(error);
        });
      });
    },

    // 前端 登出
    // FedLogOut({ commit }) {
    //   return new Promise((resolve) => {
    //     commit('SET_TOKEN', '');
    //     removeToken();
    //     resolve();
    //   });
    // },
  },
};

export default user;
