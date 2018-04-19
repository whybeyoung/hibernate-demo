<template>
  <div class="clusters-list-container">
    <el-button @click="addCluster">新增集群</el-button>
    <el-row :gutter="30">
      <el-col :xs="24" :sm="12" :md="8" :lg="6" class="">
        <el-card shadow="always" class="cluster-card servers-overview">
          <el-card>服务器总数量：{{serversCount.total}}</el-card>
          <el-card>已使用数量：{{serversCount.used}}</el-card>
          <el-card>空闲总数量：{{serversCount.total - serversCount.used}}</el-card>
        </el-card>
      </el-col>

      <template v-for="cluster in clusters">
        <el-col :xs="24" :sm="12" :md="8" :lg="6" :key="cluster.id">
          <el-card shadow="hover" class="cluster-card">
            <div class="cluster-name" slot="header">{{cluster.name}}</div>
            <el-row>
              <el-col :span="8">机器数量: {{cluster.servers.length}}</el-col>
              <el-col :span="10">POD: {{cluster.podsNum}}</el-col>
            </el-row>
            <el-row>
              <el-col :span="3">CPU</el-col>
              <el-col :span="20">
                <el-progress :text-inside="true" :stroke-width="18" :percentage="cluster.cpuPercentage"></el-progress>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="3">内存</el-col>
              <el-col :span="20">
                <el-progress :text-inside="true" :stroke-width="18" :percentage="cluster.memoryPercentage"></el-progress>
              </el-col>
            </el-row>
            <el-row>
              <el-col>
                上行速率：{{cluster.transmit}}kb/s
                下行速率：{{cluster.receive}}kb/s
              </el-col>
            </el-row>
            <div style="justify-content: center; display: flex;">
              <el-button @click="edit(cluster.id)">编辑</el-button>
              <el-button @click="detail(cluster.id)">详情</el-button>
              <el-button @click="remove(cluster)">删除</el-button>
            </div>
          </el-card>
        </el-col>
      </template>

    </el-row>
  </div>
</template>

<script>
import ClusterApi from '@/api/cluster';
import ServerApi from '@/api/server';

function currentPercentage(usage) {
  try {
    (parseFloat(usage[0].values[0][1]) * 100).toFixed(2);
    return (parseFloat(usage[0].values[0][1]) * 100).toFixed(2);
  } catch (error) {
    return 0;
  }
}

function currentNetwork(result) {
  return (parseFloat(result[0].values[0][1]) / 1000).toFixed(2);
}

export default {
  data() {
    return {
      clusters: [],
      serversCount: {
        total: 0,
        used: 0,
      },
    };
  },
  methods: {
    addCluster() {
      this.$router.push({ name: 'clusters.create' });
    },
    getClusters() {
      ClusterApi.list().then((response) => {
        this.clusters = response;
        this.serversCount.used = this.clusters.reduce((sum, i) => i.servers.length + sum, 0);
        this.clusters.map((cluster) => {
          cluster.cpuPercentage = currentPercentage(cluster.cpuUsage);
          cluster.memoryPercentage = currentPercentage(cluster.memoryUsage);
          // disable-eslint-next-line
          console.log('===', cluster.networkUsage.transmitResult[0].values[0][1] / 1000);
          cluster.transmit = currentNetwork(cluster.networkUsage.transmitResult);
          console.log('transmit=', cluster.transmit);
          cluster.receive = currentNetwork(cluster.networkUsage.receiveResult);
          return cluster;
        });
      });
    },
    detail(clusterId) {
      this.$router.push({ name: 'clusters.detail', params: { id: clusterId } });
    },
    edit(clusterId) {
      this.$router.push({ name: 'clusters.edit', params: { id: clusterId } });
    },
    remove(cluster) {
      ClusterApi.remove(cluster.id).then(() => {
        this.clusters.splice(this.clusters.indexOf(cluster));
      });
    },
  },
  created() {
    this.getClusters();
    ServerApi.count().then((count) => {
      this.serversCount.total = count;
    });
  },
};
</script>

<style>
  .clusters-list-container {
    margin: 20px;
  }

  .cluster-name {
    font-size: 18px;
  }
  .cluster-card {

  }

  .el-col {
    margin-bottom: 20px;
  }

  .servers-overview {
    height: 291px;
  }

  .el-progress-bar__innerText {
    color: #381717;
  }
</style>
