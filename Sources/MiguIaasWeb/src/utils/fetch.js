import axios from 'axios';
import { Message } from 'element-ui';

// 创建axios实例
const service = axios.create({
  baseURL: process.env.VUE_APP_BASE_API, // api的base_url
  // timeout: 15000, // 请求超时时间
  withCredentials: true,
});

// request拦截器
service.interceptors.request.use(config => config, (error) => {
  // Do something with request error
  console.log(error); // for debug
  Promise.reject(error);
});

// respone拦截器
service.interceptors.response.use(
  response => response.data,
  (error) => {
    console.error(`error: ${error}`);// for debug
    Message({
      message: error.message,
      type: 'error',
      duration: 5 * 1000,
    });
    return Promise.reject(error);
  },
);

export default service;
