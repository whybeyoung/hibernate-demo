<template>
  <div>
    <h3 style="margin-left: 20px;">新建集群</h3>

    <el-form :inline="true" :model="cluster" ref="clusterForm" :rules="clusterRules" class="demo-form-inline">

      <el-row type="flex" class="row-bg" justify="center">
        <el-col :span="6" style="">
          <el-form-item label="集群名称:" prop="name">
            <el-input v-model="cluster.name" placeholder="请输入集群名称"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="6" style="">
          <el-form-item label="备注:" prop="annotation">
            <el-input v-model="cluster.annotation" placeholder="请输入备注"></el-input>
          </el-form-item>
        </el-col>
      </el-row>

      <el-row style="height: 500px;">
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
          <el-form-item>
            <el-button type="primary" @click="addHostDialogVisible = true">添加</el-button>
          </el-form-item>

          <el-table
              :data="userableHosts"
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

        </el-col>
        <el-col :span="12" style="height: inherit; padding: 20px;">

          <div style="height: 62px;">
            选中n台机器
          </div>

          <el-table
              :data="cluster.hosts"
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
                prop="sn"
                width="300"
                label="SN">
            </el-table-column>
            <el-table-column
                fixed="right"
                width="100"
                align="center"
                label="操作">
              <template slot-scope="scope">
                <el-button @click="unselect(scope.row)" type="text" size="small">取消选中</el-button>
              </template>
            </el-table-column>
          </el-table>

        </el-col>
      </el-row>

      <el-row justify="center">
        <el-col>
          <el-form-item>
            <el-button type="primary" @click="save('clusterForm')">提交</el-button>
          </el-form-item>
        </el-col>
      </el-row>
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

        <el-table
            :data="userableHosts"
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


      <span slot="footer" class="dialog-footer">
      <el-button @click="addHostDialogVisible = false">取 消</el-button>
      <el-button type="primary" @click="addHostDialogVisible = false">确 定</el-button>
    </span>
    </el-dialog>

  </div>
</template>

<script>
import ClusterApi from '@/api/cluster';

export default {
  data() {
    return {
      addHostDialogVisible: false,
      cluster: {
        name: '',
        annotation: '',
        hosts: [],
      },
      clusterRules: {
        name: [
          { required: true, message: '请输入集群名称', trigger: 'blur' },
          { max: 50, message: '长度不能超过50个字符', trigger: 'blur' },
        ],
        annotation: [
          { max: 50, message: '长度不能超过50个字符', trigger: 'blur' },
        ],
      },
      host: {
        name: '',
        ip: '',
      },
      userableHosts: [],
      hostParams: {},
    };
  },
  methods: {
    queryHost() {
      console.log('submit!');
    },
    addHostDialogClose() {

    },
    selectHost() {

    },
    unselect() {

    },
    save(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          console.log(this.cluster);
          ClusterApi.create(this.cluster).then((res) => {
            console.log(res);
          });
        }
      });
    },
  },
};
</script>
