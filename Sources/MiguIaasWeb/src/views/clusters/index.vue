<template>
  <div>
    <el-button @click="addCluster">新增集群</el-button>
  </div>
</template>

<script>
export default {
  data() {
    return {
      addClusterDialogVisible: false,
    };
  },
  methods: {
    addCluster() {
      this.$router.push({ name: 'clusters.create' });
    },
  },
};
</script>
