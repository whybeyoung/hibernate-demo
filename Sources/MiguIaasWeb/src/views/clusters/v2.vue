<template>
  <div>
    <v-chart :forceFit="true" :height="height" :data="data" :scale="scale">
      <v-tooltip />
      <v-axis />
      <v-line position="time*value" />
      <v-point position="time*value" shape="circle" />
    </v-chart>
  </div>
</template>

<script>
const data = [{ time: 1524014499, value: 1.7453855640885387 }, { time: 1524014559, value: 1.4251120848697951 }, { time: 1524014619, value: 1.4330494833333298 }, { time: 1524014679, value: 1.4161919477604221 }, { time: 1524014739, value: 1.4902112574479176 }, { time: 1524014799, value: 1.4422287532812443 }, { time: 1524014859, value: 1.4873310465885463 }, { time: 1524014919, value: 1.5269895141666605 }, { time: 1524014979, value: 1.510179860624999 }, { time: 1524015039, value: 1.6453580881770808 }];

const scale = [{
  dataKey: 'value',
  min: 0,
}, {
  dataKey: 'time',
}];

export default {
  name: 'v2test',
  data() {
    return {
      data,
      scale,
      height: 400,
    };
  },
};
</script>
