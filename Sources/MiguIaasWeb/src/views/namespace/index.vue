<template>
    <div class="app-container" style="height: 100%;width: 100%;">
        <div class="app-header" style="height: 60px">
            <el-form ref="qf" :model="qf" label-width="70px" inline label-suffix=":">
                <el-form-item label="关键字">
                    <el-input size="small" v-model="qf.ns" clearable></el-input>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" size="small" icon="el-icon-search" @click="query(1)" style="">查询
                    </el-button>
                </el-form-item>
                <el-form-item>
                    <el-button size="small" icon="el-icon-plus" @click.native="openNewNs" style="">新增
                    </el-button>
                </el-form-item>
            </el-form>

        </div>
        <div class="app-main">
            <el-table :data="list" v-loading.body="listLoading"
                      element-loading-text="拼命加载中" border fit highlight-current-row>
                <el-table-column align="center" type="index" label='#' min-width="45">
                </el-table-column>
                <el-table-column label="命名空间" prop="ns">
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
                        <el-button size="mini" type="danger" icon="el-icon-delete" title="删除" @click.native="delNs(scope.row.id)"></el-button>
                        <el-button size="mini" icon="el-icon-edit" title="修改" @click.native="openMdfNs(scope.row)"></el-button>
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
        <el-dialog width="500px" title="命名空间" :visible.sync="showAdd"  center>
            <el-form label-suffix=":" :model="form" ref="form" label-position="left">
                <el-row>
                <el-form-item label="名称" prop="ns" >
                    <el-input v-model="form.ns" maxlength="50" :disabled="!isNew" size="medium" controls-position="right" ></el-input>
                </el-form-item>
                </el-row>
                <el-row>
                <el-form-item label="备注" prop="annotation">
                    <el-input type="textarea" maxlength="255" v-model="form.annotation" ></el-input>
                </el-form-item>
                </el-row>
                <el-row>
                <el-form-item align="right">
                    <el-button size="small"
                               @click.native="onCancel"><icon-svg icon-class="cancel"/>取消
                    </el-button>
                    <el-button size="small" v-if="isNew" type="primary" :disabled="!form.ns" :loading="commitable"
                               @click.native="onCommit"><icon-svg icon-class="yizhixing"/>提交
                    </el-button>
                    <el-button size="small" v-if="!isNew" type="primary" :disabled="!form.ns" :loading="commitable"
                               @click.native="onUpdate"><icon-svg icon-class="yizhixing"/>更新
                    </el-button>
                </el-form-item>
                </el-row>
            </el-form>
        </el-dialog>
    </div>
</template>

<script>
export default {
  data() {
    return {
      commitable: false,
      showAdd: false,
      isNew: true,
      qf: {
        ns: null,
        page: 1,
        pagesize: 10,
      },
      form: {
        id: null,
        ns: null,
        annotation: null,
      },
      formable: true,
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
      this.$store.dispatch('getNss', this.qf).then((data) => {
        this.list = data.content;
        this.total = data.totalElements;
        this.listLoading = false;
      });
    },
    delNs(id) {
      this.$confirm('您确定删除该命名空间吗?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'info',
      }).then(() => {
        this.$store.dispatch('deleteNs', id).then(() => {
          this.query();
        });
      }).catch(() => {
        // do nothing
      });
    },
    openNewNs() {
      this.form = {};
      this.showAdd = true;
      this.isNew = true;
    },
    openMdfNs(ns) {
      Object.assign(this.form, ns);
      this.showAdd = true;
      this.isNew = false;
    },
    onUpdate() {
      this.commitable = true;
      this.$store.dispatch('updateNs', this.form).then(() => {
        this.$message.success(`${this.form.ns} updated success`);
        this.$refs.form.resetFields();
        this.query();
        this.commitable = false;
        this.showAdd = false;
      }).catch(() => { this.commitable = false; });
    },
    onCommit() {
      this.commitable = true;
      this.$store.dispatch('createNs', this.form).then(() => {
        this.$message.success(`${this.form.ns} create success`);
        this.$refs.form.resetFields();
        this.query();
        this.commitable = false;
        this.showAdd = false;
      }).catch(() => { this.commitable = false; });
    },
    onCancel() {
      this.$refs.form.resetFields();
      this.showAdd = false;
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
