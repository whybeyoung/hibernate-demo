<template>
  <div class="clusters-list-container">
    <el-button @click="addCluster">新增集群</el-button>
    <el-row :gutter="30">
      <el-col :xs="24" :sm="12" :md="8" :lg="6" class="">
        <el-card shadow="always" class="cluster-card servers-overview">
          <el-card>服务器总数量</el-card>
          <el-card>已使用数量</el-card>
          <el-card>空闲总数量</el-card>
        </el-card>
      </el-col>

      <template v-for="cluster in clusters">
        <el-col :xs="24" :sm="12" :md="8" :lg="6" :key="cluster.id">
          <el-card shadow="hover" class="cluster-card">
            <div class="cluster-name" slot="header">{{cluster.name}}</div>
            <el-row>
              <el-col :span="8">机器数量: 88</el-col>
              <el-col :span="6">RC: 79</el-col>
              <el-col :span="10">POD: 83</el-col>
            </el-row>
            <el-row>
              <el-col :span="3">CPU</el-col>
              <el-col :span="20">
                <el-progress :text-inside="true" :stroke-width="18" :percentage="70"></el-progress>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="3">内存</el-col>
              <el-col :span="20">
                <el-progress :text-inside="true" :stroke-width="18" :percentage="70"></el-progress>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="3">网络</el-col>
              <el-col :span="20">
                <el-progress :text-inside="true" :stroke-width="18" :percentage="70"></el-progress>
              </el-col>
            </el-row>
            <div style="justify-content: center; display: flex;">
              <el-button @click="edit(cluster.id)">编辑</el-button>
              <el-button @click="detail(cluster.id)">详情</el-button>
            </div>
          </el-card>
        </el-col>
      </template>

    </el-row>
  </div>
</template>

<script>
import ClusterApi from '@/api/cluster';

export default {
  data() {
    return {
      clusters: [],
    };
  },
  methods: {
    addCluster() {
      this.$router.push({ name: 'clusters.create' });
    },
    getClusters() {
      ClusterApi.list().then((response) => {
        this.clusters = response;
      });
    },
    detail(clusterId) {
      this.$router.push({ name: 'clusters.detail', params: { id: clusterId } });
    },
    edit(clusterId) {
      this.$router.push({ name: 'clusters.edit', params: { id: clusterId } });
    },
  },
  mounted() {
    this.getClusters();
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
</style>
