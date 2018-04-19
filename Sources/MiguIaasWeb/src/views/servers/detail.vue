<template>
  <el-row>
    <el-col>IP</el-col>
    <el-col></el-col>
    <el-col>内存</el-col>
    <el-col></el-col>
    <el-col>内核</el-col>
    <el-col></el-col>
  </el-row>
</template>

<script>
import ServerApi from '@/api/server';

export default {
  data() {
    return {
      server: {},
    };
  },
  created() {
    ServerApi.show(this.$router.currentRoute.params.id).then((resp) => {
      this.server = resp;
    });
  },
};
</script>
