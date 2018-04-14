const getters = {
  sidebar: state => state.app.sidebar,
  token: state => state.user.token,
  avatar: state => state.user.avatar,
  nickname: state => state.user.nickname,
  user: state => state.user,
  permissionRouters: state => state.permission.routers,
  addRouters: state => state.permission.addRouters,
};
export default getters;
