<template>
  <div class="clusters-list-container">
    <el-button @click="addCluster">新增集群</el-button>
    <el-row :gutter="12">
      <el-col :xs="4" :sm="6" :md="6" :lg="8">
        <el-card shadow="always">
          <el-card>服务器总数量</el-card>
          <el-card>已使用数量</el-card>
          <el-card>空闲总数量</el-card>
        </el-card>
      </el-col>

      <template v-for="cluster in clusters">
        <el-col :xs="4" :sm="6" :md="6" :lg="8" :key="cluster.id">
          <el-card shadow="hover">
            {{cluster.name}}
            <el-row>
              <el-col :span="8">机器数量</el-col>
              <el-col :span="8">RC</el-col>
              <el-col :span="8">POD</el-col>
            </el-row>
            CPU<el-progress :text-inside="true" :stroke-width="18" :percentage="70"></el-progress>
            内存<el-progress :text-inside="true" :stroke-width="18" :percentage="70"></el-progress>
            网络<el-progress :text-inside="true" :stroke-width="18" :percentage="70"></el-progress>
            <el-button @click="edit(cluster.id)">编辑</el-button>
            <el-button @click="detail(cluster.id)">详情</el-button>
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
</style>
