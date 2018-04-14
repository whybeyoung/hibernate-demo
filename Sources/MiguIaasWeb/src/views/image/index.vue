<template>
    <div class="app-container" style="height: 100%;width: 100%;">
        <div class="app-header" style="height: 60px">
            <el-form ref="qf" :model="qf" label-width="70px" :inline="true" label-suffix=":">
                <el-form-item label="镜像名">
                    <el-input size="small" v-model="qf.name"></el-input>
                </el-form-item>
                <el-form-item label="版本">
                    <el-input size="small" v-model="qf.version"></el-input>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" size="small" icon="el-icon-search" @click="query(1)" style="">查询
                    </el-button>
                </el-form-item>
            </el-form>

        </div>
        <div class="app-main">
            <el-table :data="list" v-loading.body="listLoading"
                      element-loading-text="拼命加载中" border fit highlight-current-row>
                <el-table-column align="center" type="index" label='ID' min-width="45">
                </el-table-column>
                <el-table-column label="镜像名" prop="name">
                </el-table-column>
                <el-table-column label="版本号" min-width="40" prop="version" align="center">
                </el-table-column>
                <el-table-column label="备注" min-width="110" prop="annotation">
                </el-table-column>
                <el-table-column label="创建人" min-width="40" prop="creator">
                </el-table-column>
                <el-table-column align="center" prop="createtime" label="创建时间" width="200">
                    <template slot-scope="scope">
                        <i class="el-icon-time"></i>
                        <span>{{dateFormat(scope.row.createtime)}}</span>
                    </template>
                </el-table-column>
                <el-table-column label="操作" min-width="40" align="center">
                    <template slot-scope="scope">
                        <el-button size="mini" type="danger" @click.native="delImage(scope.row.id)">删除</el-button>
                    </template>
                </el-table-column>
            </el-table>
            <el-pagination
                    @size-change="sizeChange"
                    @current-change="pageChange"
                    align="right"
                    :current-page="qf.page"
                    :page-sizes="[10, 20, 50, 100]"
                    :page-size="qf.pagesize"
                    layout="total, sizes, prev, pager, next, jumper"
                    :total="total">
            </el-pagination>
        </div>
    </div>
</template>

<script>

export default {
  data() {
    return {
      qf: {
        name: null,
        version: null,
        page: 1,
        pagesize: 10,
      },
      total: 50,
      list: [],
      listLoading: false,
    };
  },
  created() {
    this.query();
  },
  methods: {
    sizeChange(size) {
      this.qf.pagesize = size;
      this.query();
    },
    pageChange(page) {
      this.qf.page = page;
      this.query();
    },
    query() {
      this.listLoading = true;
      this.$store.dispatch('getImages', this.qf).then((data) => {
        this.list = data.content;
        this.total = data.totalElements;
        this.listLoading = false;
      });
    },
    delImage(id) {
      this.$confirm('您确定删除该镜像吗?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'info',
      }).then(() => {
        this.$store.dispatch('deleteImage', id).then(() => {
          this.query();
        });
      }).catch(() => {
        // do nothing
      });
    },
    dateFormat(time) {
      if (time === undefined) {
        return '';
      }
      return this.$moment(time).format('YYYY-MM-DD HH:mm');
    },
  },
};
</script>
