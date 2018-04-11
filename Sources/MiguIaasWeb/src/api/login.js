import fetch from '@/utils/fetch';

export function login(username, password) {
  return fetch({
    url: 'login',
    method: 'post',
    data: {
      account: username,
      password,
    },
    headers:{'Content-Type':'text/plain;charset=UTF-8'}
  });
}

export function getInfo(token) {
  return fetch({
    url: '/user/info',
    method: 'get',
    params: { token },
  });
}

export function logout() {
  return fetch({
    url: 'logout',
    method: 'post',
  });
}
