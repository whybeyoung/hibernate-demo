import fetch from '@/utils/fetch';

const ServerApi = {};
ServerApi.index = params => fetch({
  url: 'servers',
  method: 'get',
  params,
});

ServerApi.show = clusterId => fetch({
  url: `servers/${clusterId}`,
  method: 'get',
});

ServerApi.create = server => fetch({
  url: 'servers',
  method: 'post',
  data: server,
});

export default ServerApi;
