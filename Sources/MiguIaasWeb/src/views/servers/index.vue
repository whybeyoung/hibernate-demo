<template>
  <div class="content-container">
    <h3>主机管理</h3>
    <el-form :inline="true" :model="serverParams" ref="queryServersForm">
      <el-form-item label="服务器IP" prop="">
        <el-input v-model="serverParams.ip"></el-input>
      </el-form-item>
      <el-form-item label="主机名" prop="">
        <el-input v-model="serverParams.hostname"></el-input>
      </el-form-item>
      <el-form-item label="操作系统" prop="">
        <el-input v-model="serverParams.os"></el-input>
      </el-form-item>
      <el-form-item label="集群名称" prop="">
        <el-input v-model="serverParams.clusterName"></el-input>
      </el-form-item>
      <el-form-item label="状态" prop="">
        <el-input v-model="serverParams.status"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="queryServers">
          查询
        </el-button>
      </el-form-item>
    </el-form>
    <el-row>
      <el-col></el-col>
    </el-row>
    <el-table
        :data="addedServers"
        stripe
        border
        height="398"
        style="width: 100%">
      <el-table-column
          prop="ip"
          label="IP"
          fixed
          width="180">
      </el-table-column>
      <el-table-column
          prop="name"
          label="主机名"
          width="180">
      </el-table-column>
      <el-table-column
          prop="SN"
          width="300"
          label="SN">
      </el-table-column>
      <el-table-column
          fixed="right"
          width="80"
          align="center"
          label="操作">
        <template slot-scope="scope">
          <el-button @click="selectHost(scope.row)" type="text" size="small">选中</el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
export default {
  data() {
    return {
      serverParams: {
        ip: '',
        hostname: '',
        os: '',
        status: '',
        clusterName: '',
      },
    };
  },
};
</script>

<style>
  .content-container {
    padding: 20px;
  }
</style>
