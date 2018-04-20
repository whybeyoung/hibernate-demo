import fetch from '@/utils/fetch';

const ServerApi = {};
ServerApi.index = params => fetch({
  url: 'servers',
  method: 'get',
  params,
});

ServerApi.show = serverId => fetch({
  url: `servers/${serverId}`,
  method: 'get',
});

ServerApi.create = server => fetch({
  url: 'servers',
  method: 'post',
  data: server,
});

ServerApi.count = () => fetch({
  url: 'servers/count',
});

ServerApi.serverStatus = serverId => fetch({
  url: `servers/${serverId}/status`,
  method: 'get',
});

export default ServerApi;
