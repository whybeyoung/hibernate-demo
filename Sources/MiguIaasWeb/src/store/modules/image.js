import { getImages, createImage, deleteImage } from '@/api/image_api';

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
    // 上传镜像
    createImage(...args) {
      const [, params] = args;
      const {
        name, version, ftpPath, annotation, creator,
      } = params.creator;
      return new Promise((resolve, reject) => {
        createImage(name, version, ftpPath, annotation, creator).then((response) => {
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
