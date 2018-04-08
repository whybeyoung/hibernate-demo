基础服务子平台API接口预定义，根据[restful api design guide](./api_predefinition.md)

[TOC]

# 集群管理模块
## 1. 集群管理首页显示  
### 1.1 服务器整体使用情况： 
request:  
GET `/servers/status`  
response: 
```
{
    总数量、已使用数量、空闲数量
}
```
### 1.2 每个集群的使用情况：  
request: GET `/clusters`  
response:
```
[{
主机数量、运行容器数量、集群当前平均CPU/内存使用情况和网络使用情况
},{
...
}]
```

## 2. 集群详细信息查看页面  
request: GET `/clusters/:clusterId`  
response: 
```
{
  名称
  创建人
  备注
  创建时间
  cpu:[历史cpu使用情况]
  memory:
  network:
}
```

## 3. 新增集群页面  
### 3.1 新增集群  
request: POST `/clusters`  
request payload:  
```
{
    名称（中文名、英文名？）
    主机列表
}
```
response:  
```
{
    新建成功的集群信息
}
```


~~### 3.2 查询主机  参见5.1~~
request: GET `/servers`  
request params(全部可选):  
```
ip:
hostname:
```


## 4. 修改集群页面  
### 4.1 修改集群  
request: PATCH `/clusters/:clusterId`  
request payload(参数可选):
``` 
{
    名称（中文名、英文名？）
    主机列表
}
```
response: 
```
{修改后的集群信息}
```

- 删除集群：  
request: DELETE `/clusters/:clusterId`  
response: 空  

## 5. 主机列表页面  
### 5.1 主机列表：  
request: GET `/servers?ip=&hostname=&os=&cluster=&status=&isIncludedByCluster=`  
response:  
```
[{
服务器ip：ip
SN号: sn
主机名: hostname
~~机房: hostroom~~
操作系统: os
内核: kernel
docker版本: dockerVersion
集群: cluster
内存: memory
硬盘: disk
准备状态: status
备注: annotation
}]
```

### 5.2 状态设置（主机信息修改）  
request: PATCH `/hosts/:hostId`  
request payload:   
``` 
{
    status: ready|disabled
}
response:   

```
```
{修改后的主机信息}
```

### 5.3 主机状态详情  
request: GET `/hosts/:hostId`  
response:   
``` 
[{
主机信息:
主机ip
序列号
内存
内核
docker版本
操作系统

硬件运行情况
容器运行情况
}]
```

# 镜像管理模块
## 6. 镜像列表页面  
### 6.1 镜像列表  
request: GET `/images?conponentName=&version=&imageName=`  
response: 
``` 
[{
组件名: componentName
版本号: version
镜像名: imageName
提交时间: commitDatetime
备注: comment
提交状态: commitStatus
}]
```

~~### 6.2 再提交？？~~

### 6.3 版本详情  
request: GET `/images/:imageId`  
response:  
``` 
{}
```

### 6.4 删除镜像
request: DELETE `/images/:imageId`
response: 空

## 7. 上传镜像页面
request: POST `/images`  
request payload: 
```
{
镜像名称
镜像路径
版本号
~~版本类型~~
镜像备注
}
```
response: 
``` 
{创建好的完整镜像信息}
```

# 部署管理模块
## 8. 应用列表页面
### 8.1 获取应用列表
request: GET `/apps?appName=&creator=?imageName=`
response:
```
[{
应用信息：
应用名称
创建日期
版本
镜像名称
状态
操作用户
}]
```

### 8.2 下线应用
request: PATCH `/apps/:appId`
request payload:
``` 
{
    status: 'down'
}
```
response:
`{下线的应用信息}`

### 8.3 新增应用
request: POST `/apps`
request payload: 
``` 
{
    应用名称：appName
    应用描述: appComment
    版本：ImageVersion
    标签： ClusterId
    同时更新数
    等待超时时间
    最小实例数
    最大实例数
    memory限制
    cpu限制
    唯一性部署
    停止容器的等待时间
    命令
    挂载目录
    环境变量：[]
    健康检查
    
}
```
response:
``` 
{新创建的应用信息}
```

### 8.4 添加镜像
request: POST `/images`

### 8.5 镜像配置信息、状态信息查看
request: GET `/images/:imageId`

### 8.6 删除镜像
request: DELETE `/images/:imageId`

### 8.7 ~~镜像升级~~、扩容缩容
request: PATCH `/images/:imageId`
测试升级和全网升级

### 8.8 应用运行详情
request: GET `/apps/:appId`

# 权限管理

# 日志管理
