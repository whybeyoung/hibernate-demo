import fetch from '@/utils/fetch';

export function getImages(name, version, page, pagesize) {
  return fetch({
    url: '/images',
    method: 'get',
    params: {
      name, version, page, pagesize,
    },
  });
}

export function createImage(name, version, ftpPath, annotation, creator) {
  return fetch({
    url: '/images',
    method: 'post',
    data: {
      name, version, ftpPath, annotation, creator,
    },
  });
}

export function deleteImage(imageId) {
  return fetch({
    url: `/images/${imageId}`,
    method: 'delete',
  });
}
