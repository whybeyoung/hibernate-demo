import { getNss, searchNss, createNs, updateNs, deleteNs } from '@/api/nss_api';

const nss = {
  state: {
  },
  mutations: {
  },
  actions: {
    getNss(...args) {
      const [, params] = args;
      const {
        ns, page, pagesize,
      } = params;
      return new Promise((resolve, reject) => {
        getNss(ns, page, pagesize).then((response) => {
          resolve(response);
        }).catch((error) => {
          reject(error);
        });
      });
    },
    searchNss(...args) {
      const [, ns] = args;
      return new Promise((resolve, reject) => {
        searchNss(ns).then((response) => {
          resolve(response);
        }).catch((error) => {
          reject(error);
        });
      });
    },
    updateNs(...args) {
      const [, params] = args;
      const {
        id, annotation,
      } = params;
      return new Promise((resolve, reject) => {
        updateNs(id, annotation).then((response) => {
          resolve(response);
        }).catch((error) => {
          reject(error);
        });
      });
    },
    createNs(...args) {
      const [, params] = args;
      const {
        ns, annotation,
      } = params;
      return new Promise((resolve, reject) => {
        createNs(ns, annotation).then((response) => {
          resolve(response);
        }).catch((error) => {
          reject(error);
        });
      });
    },
    deleteNs(...args) {
      const [, id] = args;
      return new Promise((resolve, reject) => {
        deleteNs(id).then((response) => {
          resolve(response);
        }).catch((error) => {
          reject(error);
        });
      });
    },
  },
};

export default nss;
