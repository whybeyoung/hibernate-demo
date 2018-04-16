import fetch from '@/utils/fetch';

const ClusterApi = {};

ClusterApi.create = cluster => fetch({
  url: '/clusters',
  method: 'post',
  data: cluster,
});

ClusterApi.update = cluster => fetch({
  url: '/clusters',
  method: 'patch',
  data: cluster,
});

ClusterApi.remove = clusterId => fetch({
  url: `clusters/${clusterId}`,
  method: 'delete',
});

ClusterApi.list = () => fetch({
  url: 'clusters',
  method: 'get',
});

ClusterApi.show = clusterId => fetch({
  url: `clusters/${clusterId}`,
  method: 'get',
});

export default ClusterApi;
