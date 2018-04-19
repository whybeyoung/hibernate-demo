<template>
  <div class="content-container">
    <h3>主机管理</h3>
    <el-form :inline="true" :model="serverParams" ref="queryServersForm">
      <el-form-item label="服务器IP" prop="">
        <el-input v-model="serverParams.ipv4"></el-input>
      </el-form-item>
      <el-form-item label="主机名" prop="">
        <el-input v-model="serverParams.hostname"></el-input>
      </el-form-item>
      <el-form-item label="操作系统" prop="">
        <el-input v-model="serverParams.os"></el-input>
      </el-form-item>
      <!--<el-form-item label="集群名称" prop="">
        <el-input v-model="serverParams.clusterName"></el-input>
      </el-form-item>-->
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
        :data="servers"
        stripe
        border
        height="398"
        style="width: 100%">
      <el-table-column prop="ipv4" label="IP" fixed width="180"></el-table-column>
      <el-table-column prop="hostname" label="主机名" width="180"></el-table-column>
      <el-table-column prop="os" label="操作系统"></el-table-column>
      <el-table-column prop="kernel" label="kernel"></el-table-column>
      <el-table-column prop="dockerVersion" label="docker版本"></el-table-column>
      <el-table-column prop="dockerVersion" label="docker版本"></el-table-column>
      <el-table-column prop="memory" label="内存">
        <template slot-scope="scope">
          {{parseInt(scope.row.memory/1024/1024/1024)}}GB
        </template>
      </el-table-column>
      <el-table-column prop="disk" label="硬盘"></el-table-column>
      <el-table-column prop="status" label="状态">
        <template slot-scope="scope">
          {{scope.row.status === true ? '可用' : '不可用'}}
        </template>
      </el-table-column>
      <el-table-column fixed="right" align="center" label="操作">
        <template slot-scope="scope">
          <!--<el-button @click="forbidServer(scope.row)" type="text" size="small">禁用</el-button>-->
          <el-button @click="serverStatus(scope.row)" type="text" size="small">状态详情</el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
import ServerApi from '@/api/server';

export default {
  data() {
    return {
      serverParams: {
        ipv4: '',
        hostname: '',
        os: '',
        status: '',
        clusterName: '',
      },
      servers: [],
    };
  },
  methods: {
    queryServers() {
      ServerApi.index(Object.assign({}, { from: 'local' }, this.serverParams)).then((resp) => {
        this.servers = resp;
      });
    },
    serverStatus(server) {
      this.$router.push({ name: 'servers.detail', params: { id: server.id } });
    },
  },
  created() {
    ServerApi.index({ from: 'local' }).then((resp) => {
      this.servers = resp;
    });
  },
};
</script>

<style>
  .content-container {
    padding: 20px;
  }
</style>
