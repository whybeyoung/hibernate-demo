<template>
  <div>
  <el-card>
    <div slot="header">网络使用情况</div>
    <v-chart :force-fit="true" :height="height" :data="networkChartData">
      <v-tooltip />
      <v-axis data-key="count" :label="countOpts.label"/>
      <v-legend />
      <v-line position="time*count" color="network" />
      <v-point position="time*count" color="network" :size="4"  :shape="'circle'" />
    </v-chart>
  </el-card>
  </div>

</template>

<script>
import DataSet from '@antv/data-set';

function formatTime(time) {
  const t = new Date(time * 1000);
  return t.toLocaleString();
}

function formatUsage(usage) {
  return usage[0].values.map(i => ({ time: formatTime(i[0]), value: parseFloat(i[1]) }));
}

function byteToMb(b) {
  return parseFloat((b / 1024 / 1024).toFixed(2));
}

export default {
  name: 'network-chart',
  data() {
    return {
      countOpts: {
        label: {
          formatter: val => `${(val)}MB/s`,

        },
      },
      status: {
        network: {
          receive: {},
          transmit: {},
          total: {},
        },
      },
      networkChartData: [],
      height: 400,
    };
  },
  props: {
    data: {
      required: true,
    },
  },
  watch: {
    data(newValue) {
      this.status.network.receive = formatUsage(newValue.network.receiveResult);
      this.status.network.transmit = formatUsage(newValue.network.transmitResult);
      this.status.network.total = formatUsage(newValue.network.totalResult);
      this.networkChartData = this.status.network.receive.map((i) => {
        i.transmitValue = this.status.network.transmit.find(j => j.time === i.time).value;
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
    },
  },
};
</script>
