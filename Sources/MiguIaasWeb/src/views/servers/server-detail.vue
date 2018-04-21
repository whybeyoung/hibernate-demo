<template>
  <div class="content-container">
    <el-card class="server-info-card">
      <el-row class="">
        <el-col :xs="12" :sm="12" :md="6" :lg="3" class="cluster-title">主机IP：</el-col>
        <el-col :xs="12" :sm="12" :md="18" :lg="21">{{server.ipv4}}</el-col>
        <el-col :xs="12" :sm="12" :md="6" :lg="3" class="cluster-title">内存：</el-col>
        <el-col :xs="12" :sm="12" :md="18" :lg="21">{{parseInt(server.memory/1024/1024/1024)}}GB</el-col>
        <el-col :xs="12" :sm="12" :md="6" :lg="3" class="cluster-title">内核： </el-col>
        <el-col :xs="12" :sm="12" :md="18" :lg="21">{{server.kernel}}</el-col>
        <el-col :xs="12" :sm="12" :md="6" :lg="3" class="cluster-title">Docker版本： </el-col>
        <el-col :xs="12" :sm="12" :md="18" :lg="21">{{server.dockerVersion}}</el-col>
        <el-col :xs="12" :sm="12" :md="6" :lg="3" class="cluster-title">操作系统： </el-col>
        <el-col :xs="12" :sm="12" :md="18" :lg="21">{{server.os}}</el-col>
      </el-row>
    </el-card>

    <el-card class="grap-card">
      <div slot="header">cpu使用率：</div>
      <v-chart :forceFit="true" :height="height" :data="cpuChartData" :scale="scale">
        <v-tooltip />
        <v-axis/>
        <v-line position="time*value" />
        <v-point position="time*value" shape="circle" />
      </v-chart>
    </el-card>

    <el-card class="grap-card">
      <div slot="header">内存使用率：</div>
      <v-chart :forceFit="true" :height="height" :data="memoryChartData" :scale="scale">
        <v-tooltip />
        <v-axis/>
        <v-line position="time*value" />
        <v-point position="time*value" shape="circle" />
      </v-chart>
    </el-card>

    <network-chart :data="serverStatus"></network-chart>

  </div>

</template>

<script>
import ServerApi from '@/api/server';
import { formatUsage } from '@/utils';

export default {
  data() {
    return {
      server: {},
      cpuChartData: [],
      memoryChartData: [],
      serverStatus: [],
      scale: [{
        dataKey: 'value',
        formatter: '.0%',
        alias: '使用率',
        min: 0,
        max: 1,
      }, {
        dataKey: 'time',
      }],
      height: 400,
    };
  },
  created() {
    const serverId = this.$router.currentRoute.params.id;
    ServerApi.show(serverId).then((resp) => {
      this.server = resp;
    });
    ServerApi.serverStatus(serverId).then((resp) => {
      this.serverStatus = resp;
      this.cpuChartData = formatUsage(resp.cpu);
      this.memoryChartData = formatUsage(resp.memory);
    });
  },
};
</script>

<style>
.content-container{
  margin: 28px;
}

.content-container .el-card {
  margin-bottom: 20px;
}
  .server-info-card .el-col {
    border-bottom: lightgray 1px solid;
    margin-bottom: 15px;
    padding: 10px;
  }
</style>
