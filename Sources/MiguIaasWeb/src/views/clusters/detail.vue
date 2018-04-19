<template>
  <div class="content-container">
    <el-row>
      <el-col :span="4">集群名称：</el-col>
      <el-col :span="4">{{cluster.name}}</el-col>
      <el-col :span="4">集群名称：</el-col>
      <el-col :span="4">{{cluster.name}}</el-col>
      <el-col :span="4">集群名称：</el-col>
      <el-col :span="4">{{cluster.name}}</el-col>
    </el-row>
    集群名称：{{cluster.name}}
    标签名：{{cluster.labelName}}
    备注： {{cluster.annotation}}
    创建人： {{cluster.creator}}
    创建时间：{{cluster.createtime}}

    <el-card class="grap-card">
      <div slot="header">cpu平均使用率：</div>
      <v-chart :forceFit="true" :height="height" :data="cpuUsage" :scale="scale">
        <v-tooltip />
        <v-axis :label="percentageUsage"/>
        <v-line position="time*value" />
        <v-point position="time*value" shape="circle" />
      </v-chart>
    </el-card>

    <el-card class="grap-card">
      <div slot="header">内存平均使用率：</div>
      <v-chart :forceFit="true" :height="height" :data="memoryUsage" :scale="scale">
        <v-tooltip />
        <v-axis />
        <v-line position="time*value" />
        <v-point position="time*value" shape="circle" />
      </v-chart>
    </el-card>

    <el-card>
      <div slot="header">网络使用情况</div>
      <v-chart :force-fit="true" :height="height" :data="network">
        <v-tooltip />
        <v-axis />
        <v-legend />
        <v-line position="time*count" color="network" />
        <v-point position="time*count" color="network" :size="4" :scale="networkScale" :shape="'circle'" />
      </v-chart>
    </el-card>

  </div>
</template>

<script>
import ClusterApi from '@/api/cluster';
import DataSet from '@antv/data-set';

function formatTime(time) {
  const t = new Date(time * 1000);
  return t.toLocaleString();
}

function formatUsage(usage) {
  return usage[0].values.map(i => ({ time: formatTime(i[0]), value: parseFloat(i[1]) }));
}

export default {
  data() {
    return {
      cluster: {},
      cpuUsage: [],
      memoryUsage: [],
      network: [],
      scale: [{
        dataKey: 'value',
        formatter: '%',
      }, {
        dataKey: 'time',
      }],
      percentageUsage: [{
        value: {
          formatter: val => `${val}%`,
        },
      }],
      networkScale: [{
        dataKey: 'transmitValue',
        formatter: val => `${val}MB`,
      }],
      height: 400,
    };
  },
  created() {
    ClusterApi.show(this.$router.currentRoute.params.id).then((resp) => {
      this.cluster = resp;
      this.cpuUsage = formatUsage(this.cluster.cpuUsage);
      console.log('cpu:', this.cpuUsage);
      this.memoryUsage = formatUsage(this.cluster.memoryUsage);
      console.log('mem', this.memoryUsage);

      const transmits = this.cluster.networkUsage.transmitResult[0].values;
      const receives = this.cluster.networkUsage.receiveResult[0].values;
      this.network = transmits.map((i) => {
        const receive = receives.find(j => i[0] === j[0]);
        const transmitValue = parseFloat(i[1]);
        const receiveValue = parseFloat(receive[1]);
        const totalValue = transmitValue + receiveValue;
        return {
          time: formatTime(i[0]),
          transmitValue,
          receiveValue,
          totalValue,
        };
      });

      const dv = new DataSet.View().source(this.network);
      dv.transform({
        type: 'fold',
        fields: ['transmitValue', 'receiveValue', 'totalValue'],
        key: 'network',
        value: 'count',
      });
      this.network = dv.rows;
    });
  },
  method: {
  },
};
</script>

<style>
.content-container {
  margin: 20px;
}

  .grap-card {
    margin-bottom: 20px;
  }
</style>
