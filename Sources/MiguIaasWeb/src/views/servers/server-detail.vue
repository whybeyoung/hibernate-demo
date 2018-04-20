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

    <el-card class="grap-card">
      <div slot="header">网络使用情况：</div>

      <v-chart :force-fit="true" :data="networkChartData">
        <v-tooltip />
        <v-axis data-key="time" :tick-line="null" :label="null"/>
        <v-axis data-key="count" :label="countOpts.label"/>
        <v-legend />
        <v-line position="time*count" color="network" />
        <v-point position="time*count" color="network" :size="4"  :shape="'circle'" />
      </v-chart>
    </el-card>

    <!--<network-chart :data="serverStatus"></network-chart>-->


  </div>

</template>

<script>
import ServerApi from '@/api/server';
import { formatUsage } from '@/utils';
import DataSet from '@antv/data-set';

function byteToMb(b) {
  return parseFloat((b / 1024 / 1024).toFixed(2));
}

export default {
  data() {
    return {
      countOpts: {
        label: {
          formatter: (val) => {
            console.log('valu=', val);
            return `${(val)}MB/s`;
          },
        },
      },
      server: {},
      cpuChartData: [],
      memoryChartData: [],
      networkChartData: [],
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

      const receive = formatUsage(resp.network.receiveResult);
      const transmit = formatUsage(resp.network.transmitResult);
      // const total = formatUsage(newValue.network.totalResult);
      this.networkChartData = receive.map((i) => {
        i.transmitValue = transmit.find(j => j.time === i.time).value;
        i.totalValue = i.value + i.transmitValue;
        return {
          time: i.time,
          上行速率: byteToMb(i.transmitValue),
          下行速率: byteToMb(i.value),
          总速率: byteToMb(i.totalValue),
        };
      });

      const dv = new DataSet.View().source(this.networkChartData);
      dv.transform({
        type: 'fold',
        fields: ['上行速率', '下行速率', '总速率'],
        key: 'network',
        value: 'count',
      });
      this.networkChartData = dv.rows;
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
