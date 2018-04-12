<template>
  <div>
    <h3 style="margin-left: 20px;">新建集群</h3>

    <el-form :inline="true" :model="cluster" class="demo-form-inline">

      <el-row type="flex" class="row-bg" justify="center">
        <el-col :span="6" style="">
          <el-form-item label="集群名称:">
            <el-input v-model="cluster.name" placeholder="请输入集群名称"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="6" style="">
          <el-form-item label="备注:">
            <el-input v-model="cluster.comment" placeholder="请输入备注"></el-input>
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
              :data="tableData"
              stripe
              border
              height="398"
              style="width: 100%">
            <el-table-column
                prop="date"
                label="日期"
                fixed
                width="180">
            </el-table-column>
            <el-table-column
                prop="name"
                label="姓名"
                width="180">
            </el-table-column>
            <el-table-column
                prop="address"
                width="300"
                label="地址">
            </el-table-column>
            <el-table-column
                prop="address"
                width="300"
                label="地址">
            </el-table-column>
            <el-table-column
                prop="address"
                width="300"
                label="地址">
            </el-table-column>
            <el-table-column
                prop="address"
                width="300"
                label="地址">
            </el-table-column>
            <el-table-column
                prop="address"
                width="300"
                label="地址">
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
              :data="tableData"
              stripe
              border
              height="398"
              style="width: 100%">
            <el-table-column
                prop="date"
                label="日期"
                fixed
                width="180">
            </el-table-column>
            <el-table-column
                prop="name"
                label="姓名"
                width="180">
            </el-table-column>
            <el-table-column
                prop="address"
                width="300"
                label="地址">
            </el-table-column>
            <el-table-column
                prop="address"
                width="300"
                label="地址">
            </el-table-column>
            <el-table-column
                prop="address"
                width="300"
                label="地址">
            </el-table-column>
            <el-table-column
                prop="address"
                width="300"
                label="地址">
            </el-table-column>
            <el-table-column
                prop="address"
                width="300"
                label="地址">
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
            <el-button type="primary" @click="save">提交</el-button>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>

    <el-dialog
        title="提示"
        :visible.sync="addHostDialogVisible"
        :fullscreen=true
        :before-close="addHostDialogClose">
      <span>这是一段信息</span>
      <span slot="footer" class="dialog-footer">
      <el-button @click="dialogVisible = false">取 消</el-button>
      <el-button type="primary" @click="dialogVisible = false">确 定</el-button>
    </span>
    </el-dialog>

  </div>
</template>

<script>
export default {
  data() {
    return {
      addHostDialogVisible: false,
      cluster: {
        name: '',
        comment: '',
      },
      host: {
        name: '',
        ip: '',
      },
      tableData: [{
        date: '2016-05-02',
        name: '王小虎',
        address: '上海市普陀区金沙江路 1518 弄',
      }, {
        date: '2016-05-04',
        name: '王小虎',
        address: '上海市普陀区金沙江路 1517 弄',
      }, {
        date: '2016-05-01',
        name: '王小虎',
        address: '上海市普陀区金沙江路 1519 弄',
      }, {
        date: '2016-05-03',
        name: '王小虎',
        address: '上海市普陀区金沙江路 1516 弄',
      }],
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
    save() {

    },
  },
};
</script>
