import {
  getNamespaces, createApp, getApps, deployImage, deployService, deleteImageDeploy,
  deleteService, getDeployedImagePods, getImageServers, scalePods,
} from '@/api/deploy_api';

const deploy = {
  state: {},

  mutations: {},

  actions: {
    // 获取命名空间
    getNamespaces() {
      return new Promise((resolve, reject) => {
        getNamespaces().then((response) => {
          resolve(response);
        }).catch((error) => {
          reject(error);
        });
      });
    },
    // 获取应用列表
    getApps(...args) {
      const [, params] = args;
      const {
        name, creator, page, pagesize,
      } = params;
      return new Promise((resolve, reject) => {
        getApps(name, creator, page, pagesize).then((response) => {
          resolve(response);
        }).catch((error) => {
          reject(error);
        });
      });
    },
    // 创建应用
    createApp(...args) {
      const [, params] = args;
      const {
        name, namespace, annotation, apptype, nodePort, podPort,
      } = params;
      return new Promise((resolve, reject) => {
        createApp(name, namespace, annotation, apptype, nodePort, podPort).then((response) => {
          resolve(response);
        }).catch((error) => {
          reject(error);
        });
      });
    },
    // 部署镜像
    deployImage(...args) {
      const [, params] = args;
      return new Promise((resolve, reject) => {
        deployImage(params).then((response) => {
          resolve(response);
        }).catch((error) => {
          reject(error);
        });
      });
    },
    // 部署服务
    deployService(...args) {
      const [, params] = args;
      const { appId, imageDids } = params;
      return new Promise((resolve, reject) => {
        deployService(appId, imageDids).then((response) => {
          resolve(response);
        }).catch((error) => {
          reject(error);
        });
      });
    },
    // 删除镜像部署
    deleteImageDeploy(...args) {
      const [, deployId] = args;
      return new Promise((resolve, reject) => {
        deleteImageDeploy(deployId).then((response) => {
          resolve(response);
        }).catch((error) => {
          reject(error);
        });
      });
    },
    // 删除服务部署
    deleteService(...args) {
      const [, appId] = args;
      return new Promise((resolve, reject) => {
        deleteService(appId).then((response) => {
          resolve(response);
        }).catch((error) => {
          reject(error);
        });
      });
    },
    // 获取镜像部署的pods
    getDeployedImagePods(...args) {
      const [, ids] = args;
      return new Promise((resolve, reject) => {
        getDeployedImagePods(ids).then((response) => {
          resolve(response);
        }).catch((error) => {
          reject(error);
        });
      });
    },
    // 获取镜像部署的servers
    getImageServers(...args) {
      const [, deployId] = args;
      return new Promise((resolve, reject) => {
        getImageServers(deployId).then((response) => {
          resolve(response);
        }).catch((error) => {
          reject(error);
        });
      });
    },
    // 伸缩镜像部署的pods
    scalePods(...args) {
      const [, params] = args;
      const { deployId, pods } = params;
      return new Promise((resolve, reject) => {
        scalePods(deployId, pods).then((response) => {
          resolve(response);
        }).catch((error) => {
          reject(error);
        });
      });
    },
  },
};

export default deploy;
