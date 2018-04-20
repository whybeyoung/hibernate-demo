import fetch from '@/utils/fetch';

/**
 * 获取k8s命名空间
 */
export function getNamespaces() {
  return fetch({
    url: '/deploy/namespaces',
    method: 'get',
  });
}

/**
 * 获取应用列表
 */
export function getApps(name, creator, page, pagesize) {
  return fetch({
    url: '/deploy/apps',
    method: 'get',
    params: {
      name, creator, page, pagesize,
    },
  });
}

/**
 * 创建应用
 * @param name
 * @param namespace
 * @param annotation
 * @param service
 * @param creator
 */
export function createApp(name, namespace, annotation, apptype, nodePort, podPort) {
  return fetch({
    url: '/deploy/apps',
    method: 'post',
    data: {
      name, namespace, annotation, apptype, nodePort, podPort,
    },
  });
}

/**
 * 部署镜像
 */
export function deployImage(params) {
  const {
    appId, clusterId, imageId, containerPort, pods, minPods, maxPods, timeOut, cpuLimits, memoryLimits, uniqueDeploy, initCmd, mountDirs, envs, healthCheck,
  } = params;
  return fetch({
    url: '/deploy/image',
    method: 'post',
    data: {
      appId, clusterId, imageId, containerPort, pods, minPods, maxPods, timeOut, cpuLimits, memoryLimits, uniqueDeploy, initCmd, mountDirs, envs, healthCheck,
    },
  });
}

/**
 * 部署服务
 * @param appId 应用id
 * @param imageDids 镜像部署id集合
 */
export function deployService(appId, imageDids) {
  return fetch({
    url: '/deploy/service',
    method: 'post',
    data: {
      appId, imageDids,
    },
  });
}

/**
 * 删除镜像部署
 * @param deployId 镜像部署id
 */
export function deleteImageDeploy(deployId) {
  return fetch({
    url: `/deploy/image/${deployId}`,
    method: 'delete',
  });
}

/**
 * 删除服务部署
 * @param appId 服务应用id
 */
export function deleteService(appId) {
  return fetch({
    url: `/deploy/service/${appId}`,
    method: 'delete',
  });
}

/**
 * 获取已部署镜像的pods情况
 * @param ids 镜像部署id集合
 */
export function getDeployedImagePods(ids) {
  return fetch({
    url: '/deploy/image/pods',
    method: 'get',
    params: {
      ids: ids.join(','),
    },
  });
}

/**
 * pods伸缩
 * @param deployId
 * @param pods
 */
export function scalePods(deployId, pods) {
  return fetch({
    url: `/deploy/image/${deployId}/pods`,
    method: 'put',
    params: {
      pods,
    },
  });
}

/**
 *获取镜像部署的节点
 * @param deployId
 */
export function getImageServers(deployId) {
  return fetch({
    url: `/deploy/image/${deployId}/servers`,
    method: 'get',
  });
}
