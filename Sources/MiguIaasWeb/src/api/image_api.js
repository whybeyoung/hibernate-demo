import fetch from '@/utils/fetch';

/**
 * 获取镜像列表
 * @param name
 * @param version
 * @param page
 * @param pagesize
 */
export function getImages(name, version, page, pagesize) {
  return fetch({
    url: '/images',
    method: 'get',
    params: {
      name, version, page, pagesize,
    },
  });
}

/**
 * 镜像search
 * @param name
 */
export function searchImages(name) {
  return fetch({
    url: '/images/search',
    method: 'get',
    params: {
      name,
    },
  });
}

/**
 * 创建镜像
 * @param name
 * @param version
 * @param ftpPath
 * @param annotation
 * @param creator
 */
export function createImage(name, version, ftpPath, annotation) {
  return fetch({
    url: '/images',
    method: 'post',
    data: {
      name, version, ftpPath, annotation,
    },
  });
}

/**
 * 删除镜像
 * @param imageId
 */
export function deleteImage(imageId) {
  return fetch({
    url: `/images/${imageId}`,
    method: 'delete',
  });
}
