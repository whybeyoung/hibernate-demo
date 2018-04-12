import fetch from '@/utils/fetch';

export function login(username, password) {
  return fetch({
    url: 'login',
    method: 'post',
    data: {
      account: username,
      password,
    },
  });
}

export function getInfo() {
  return fetch({
    url: '/users/current',
    method: 'get',
  });
}

export function logout() {
  return fetch({
    url: 'logout',
    method: 'post',
  });
}
