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

ClusterApi.delete = clusterId => fetch({
  url: 'clusters',
  method: 'delete',
  params: {
    clusterId,
  },
});

ClusterApi.list = () => fetch({
  url: 'clusters',
  method: 'get',
});

export default ClusterApi;
