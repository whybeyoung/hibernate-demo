import fetch from '@/utils/fetch';

/**
 * 获取空间列表
 * @param ns
 * @param page
 * @param pagesize
 */
export function getNss(ns, page, pagesize) {
  return fetch({
    url: '/nss',
    method: 'get',
    params: {
      ns, page, pagesize,
    },
  });
}

/**
 * 命名空间search
 * @param name
 */
export function searchNss(ns) {
  return fetch({
    url: '/nss/search',
    method: 'get',
    params: {
      ns,
    },
  });
}

/**
 * 创建命名空间
 * @param name
 * @param annotation
 */
export function createNs(ns, annotation) {
  return fetch({
    url: '/nss',
    method: 'post',
    data: {
      ns, annotation,
    },
  });
}

/**
 * 更新命名空间
 * @param id
 * @param annotation
 */
export function updateNs(id, annotation) {
  return fetch({
    url: `/nss/${id}`,
    method: 'put',
    params: {
      annotation,
    },
  });
}

/**
 * 删除镜像
 * @param id
 */
export function deleteNs(id) {
  return fetch({
    url: `/nss/${id}`,
    method: 'delete',
  });
}
