<template>
  <div>
    <el-form :inline="true" :model="serverParams">
      <el-form-item label="服务器IP：">
        <el-input v-model="serverParams.ipv4" placeholder=""></el-input>
      </el-form-item>
      <el-form-item label="主机名：">
        <el-input v-model="serverParams.name" placeholder=""></el-input>
      </el-form-item>
      <el-form-item label="状态：">
        <el-input v-model="serverParams.status" placeholder=""></el-input>
      </el-form-item>
      <el-form-item>
        <el-button>查询</el-button>
      </el-form-item>
    </el-form>
    <el-table
        :data="servers"
        stripe
        border
        height="398"
        style="width: 100%">
      <el-table-column
          prop="ipv4"
          label="IP"
          fixed
          width="180">
      </el-table-column>
      <el-table-column
          prop="hostname"
          label="主机名"
          width="180">
      </el-table-column>
      <el-table-column prop="os" label="操作系统"></el-table-column>
      <el-table-column prop="kernel" label="kernel"></el-table-column>
      <el-table-column prop="cpu" label="cpu"></el-table-column>
      <el-table-column
          prop="sn"
          width="300"
          label="SN">
      </el-table-column>
      <el-table-column
          fixed="right"
          align="center"
          label="操作">
        <template slot-scope="scope">
          <div v-if="scope.row.id">已添加</div>
          <el-button v-else @click="addServer(scope.row)" type="text" size="small">添加</el-button>
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
        status: '',
      },
      servers: [],
    };
  },
  created() {
    ServerApi.index({ from: 'k8s-local' }).then((resp) => {
      this.servers = resp;
    });
  },
  methods: {
    addServer(server) {
      ServerApi.create(server).then((resp) => {
        this.servers.map((s) => {
          s.id = s.hostname === resp.hostname ? resp.id : s.id;
          return s;
        });
      });
    },
  },
};
</script>
