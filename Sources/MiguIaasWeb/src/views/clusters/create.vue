<template>
  <div>
    <h3 style="margin-left: 20px;" v-if="mode === 'create'">新建集群</h3>
    <h3 style="margin-left: 20px;" v-else>修改集群</h3>


    <el-form :inline="true" :model="cluster" ref="clusterForm" :rules="clusterRules" class="demo-form-inline">

      <el-row type="flex" class="row-bg" justify="center">
        <el-col :span="5" style="">
          <el-form-item label="集群名称:" prop="name">
            <el-input v-model="cluster.name" placeholder="请输入集群名称"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="5" style="">
          <el-form-item label="标签名" prop="labelName">
            <el-input v-model="cluster.labelName" placeholder="请输入标签名"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="5">
          <el-form-item label="备注:" prop="annotation">
            <el-input v-model="cluster.annotation" placeholder="请输入备注"></el-input>
          </el-form-item>
        </el-col>
      </el-row>

      <el-row style="height: 500px;" type="flex">
        <el-col :span="12" style="height: inherit; padding: 20px;">
          <el-form-item label="IP:">
            <el-input v-model="host.ip" placeholder="请输入IP"></el-input>
          </el-form-item>
          <el-form-item label="主机名:">
            <el-input v-model="host.name" placeholder="请输入主机名"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="queryHost">查询</el-button>
          </el-form-item>
          <!--
          <el-form-item>-->
            <!--<el-button type="primary" @click="addHostDialogVisible = true">添加</el-button>-->
          <!--</el-form-item>
          -->

          <el-table
              :data="usableServers"
              stripe
              border
              height="398"
              style="width: 100%">
            <el-table-column prop="ipv4" label="IP" fixed width="180"></el-table-column>
            <el-table-column prop="hostname" label="主机名" width="180"></el-table-column>
            <el-table-column prop="os" label="操作系统"></el-table-column>
            <el-table-column prop="kernel" label="kernel"></el-table-column>
            <el-table-column prop="dockerVersion" label="docker版本"></el-table-column>
            <el-table-column
                fixed="right"
                width="80"
                align="center"
                label="操作">
              <template slot-scope="scope">
                <el-button @click="addServer(scope.row)" type="text" size="small">添加</el-button>
              </template>
            </el-table-column>
          </el-table>

        </el-col>
        <el-col :span="12" style="height: inherit; padding: 20px;">

          <div style="height: 62px;">
            选中n台机器
          </div>

          <el-table
              :data="cluster.servers"
              stripe
              border
              height="398"
              style="width: 100%">
            <el-table-column prop="ipv4" label="IP" fixed width="180"></el-table-column>
            <el-table-column prop="hostname" label="主机名" width="180"></el-table-column>
            <el-table-column prop="os" label="操作系统"></el-table-column>
            <el-table-column prop="kernel" label="kernel"></el-table-column>
            <el-table-column prop="dockerVersion" label="docker版本"></el-table-column>
            <el-table-column
                fixed="right"
                width="100"
                align="center"
                label="操作">
              <template slot-scope="scope">
                <el-button @click="unadd(scope.row)" type="text" size="small">移除</el-button>
              </template>
            </el-table-column>
          </el-table>

        </el-col>
      </el-row>
      <div class="justify-center">
        <el-form-item>
          <el-button @click="back">取消</el-button>
          <el-button type="primary" @click="save('clusterForm')">保存</el-button>
        </el-form-item>
      </div>
    </el-form>

    <el-dialog
        title="添加主机"
        :visible.sync="addHostDialogVisible"
        :before-close="addHostDialogClose">
      <el-form :inline="true" :model="hostParams">
        <el-form-item label="服务器IP：">
          <el-input v-model="hostParams.ip" placeholder=""></el-input>
        </el-form-item>
        <el-form-item label="主机名：">
          <el-input v-model="hostParams.name" placeholder=""></el-input>
        </el-form-item>
        <el-form-item label="状态：">
          <el-input v-model="hostParams.status" placeholder=""></el-input>
        </el-form-item>
        <el-form-item>
          <el-button>查询</el-button>
        </el-form-item>
      </el-form>
      <!--
      <el-form-item label="：">
        <el-input v-model="hostParams." placeholder=""></el-input>
      </el-form-item>
      -->


    <span slot="footer" class="dialog-footer">
      <el-button @click="addHostDialogVisible = false">取 消</el-button>
      <el-button type="primary" @click="addHostDialogVisible = false">确 定</el-button>
    </span>
    </el-dialog>

  </div>
</template>

<script>
import ClusterApi from '@/api/cluster';
import ServerApi from '@/api/server';

export default {
  data() {
    return {
      mode: 'create',
      addHostDialogVisible: false,
      cluster: {
        name: '',
        annotation: '',
        servers: [],
        labelName: '',
      },
      clusterRules: {
        name: [
          { required: true, message: '请输入集群名称', trigger: 'blur' },
          { max: 50, message: '长度不能超过50个字符', trigger: 'blur' },
        ],
        annotation: [
          { max: 50, message: '长度不能超过50个字符', trigger: 'blur' },
        ],
        labelName: [
          { required: true, message: '请输入标签名', trigger: 'blur' },
        ],
      },
      host: {
        name: '',
        ip: '',
      },
      usableServers: [],
      hostParams: {},
      addedServers: [],
    };
  },
  methods: {
    queryHost() {
      console.log('submit!');
    },
    addHostDialogClose() {

    },
    selectServer() {

    },
    unselect() {

    },
    save(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          if (this.$router.currentRoute.name === 'clusters.edit') {
            ClusterApi.update(this.cluster).then(() => {
              this.$router.push({ name: 'clusters.index' });
            });
          } else {
            ClusterApi.create(this.cluster).then(() => {
              this.$router.push({ name: 'clusters.index' });
            });
          }
        }
      });
    },
    back() {
      this.$router.go(-1);
    },
    addServer(server) {
      this.usableServers.splice(this.usableServers.indexOf(server), 1);
      this.cluster.servers.push(server);
    },
    unadd(server) {
      this.cluster.servers.splice((this.cluster.servers.indexOf(server)), 1);
      this.usableServers.push(server);
    },
  },
  created() {
    if (this.$router.currentRoute.name === 'clusters.edit') {
      this.mode = 'edit';
      ClusterApi.show(this.$router.currentRoute.params.id).then((response) => {
        this.cluster = response;
      });
    }
    ServerApi.index({ from: 'localunadded' }).then((resp) => {
      this.usableServers = resp;
    });
  },
};
</script>

<style>
.justify-center {
  display: flex;
  justify-content: center;
}
</style>
