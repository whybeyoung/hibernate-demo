import fetch from '@/utils/fetch';

export default function getOperationLog(type, creator, index, size) {
  return fetch({
    url: '/operationlogs',
    method: 'get',
    params: {
      type, creator, index, size,
    },
  });
}
