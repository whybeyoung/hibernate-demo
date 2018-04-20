import { getImages, searchImages, createImage, deleteImage } from '@/api/image_api';

const image = {
  state: {},

  mutations: {},

  actions: {
    // 获取镜像列表
    getImages(...args) {
      const [, params] = args;
      const {
        name, version, page, pagesize,
      } = params;
      return new Promise((resolve, reject) => {
        getImages(name, version, page, pagesize).then((response) => {
          resolve(response);
        }).catch((error) => {
          reject(error);
        });
      });
    },
    // 搜索镜像
    searchImages(...args) {
      const [, name] = args;
      return new Promise((resolve, reject) => {
        searchImages(name).then((response) => {
          resolve(response);
        }).catch((error) => {
          reject(error);
        });
      });
    },
    // 上传镜像
    createImage(...args) {
      const [, params] = args;
      const {
        name, version, ftpPath, annotation,
      } = params;
      return new Promise((resolve, reject) => {
        createImage(name, version, ftpPath, annotation).then((response) => {
          resolve(response);
        }).catch((error) => {
          reject(error);
        });
      });
    },
    // 删除镜像
    deleteImage(...args) {
      const [, imagId] = args;
      return new Promise((resolve, reject) => {
        deleteImage(imagId).then((response) => {
          resolve(response);
        }).catch((error) => {
          reject(error);
        });
      });
    },

  },
};

export default image;
