<template>
  <div>
    <el-row>
      <el-col :span="4">集群名称：</el-col>
      <el-col :span="4">{{cluster.name}}</el-col>
      <el-col :span="4">集群名称：</el-col>
      <el-col :span="4">{{cluster.name}}</el-col>
      <el-col :span="4">集群名称：</el-col>
      <el-col :span="4">{{cluster.name}}</el-col>
    </el-row>
    集群名称：{{cluster.name}}
    标签名：{{labelName}}
    备注： {{cluster.annotation}}
    创建人： {{cluster.creator}}
    创建时间：{{cluster.createtime}}
  </div>
</template>

<script>
import ClusterApi from '@/api/cluster';

export default {
  data() {
    return {
      cluster: {},
    };
  },
  created() {
    ClusterApi.show(this.$router.currentRoute.params.id).then((resp) => {
      this.cluster = resp;
    });
  },
};
</script>

<style>

</style>
