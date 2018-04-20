<template>
    <div class="app-container" style="height: 100%;width: 100%;">
        <div class="app-header" style="height: 60px">
            <el-form ref="qf" :model="qf" label-width="80px" :inline="true" label-suffix=":">
                <el-form-item label="应用名称">
                    <el-input size="small" v-model="qf.name" clearable></el-input>
                </el-form-item>
                <el-form-item label="操作用户">
                    <el-input size="small" v-model="qf.creator" clearable></el-input>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" size="small" icon="el-icon-search" @click="query(1)" style="">查询
                    </el-button>
                </el-form-item>
            </el-form>

        </div>
        <div class="app-main">
            <el-table :data="list" v-loading.body="listLoading" :show-header="false"
                      @expand-change="rowExpandChange"
                      :row-key="getRowKeys"
                      :expand-row-keys="expands"
                      element-loading-text="拼命加载中" border fit highlight-current-row>
                <el-table-column type="expand" @expand-change="rowExpandChange">
                    <template slot-scope="props">
                        <el-form label-position="left" inline class="table-expand" label-suffix=":">
                            <el-form-item label="备注">
                                <el-tooltip :content="props.row.annotation" placement="top" effect="light">
                                <span>{{ props.row.annotation }}</span>
                                </el-tooltip>
                            </el-form-item>
                            <el-form-item label="IP">
                                <span>{{ props.row.ip }}</span>
                            </el-form-item>
                            <el-form-item label="外部端口">
                                <span>{{ props.row.nodePort }}</span>
                            </el-form-item>
                            <el-form-item label="内部端口">
                                <span>{{ props.row.podPort }}</span>
                            </el-form-item>
                        </el-form>
                        <hr v-if="props.row.deployImages" style="height:1px;border:none;border-top:1px dashed #99a9bf;"/>
                        <el-form label-position="left" inline class="table-expand" label-suffix=":">
                            <el-row v-for="image in props.row.deployImages" :key="image.id">
                                <el-form-item label="部署名">
                                    <span>{{ image.name }}</span>
                                </el-form-item>
                                <el-form-item label="版本">
                                    <span>{{ image.name? image.name.substr(image.name.lastIndexOf("-") + 1) : '' }}</span>
                                </el-form-item>
                                <el-form-item label="镜像">
                                    <span>{{ image.name? image.name.substr(0,image.name.lastIndexOf("-")):'' }}</span>
                                </el-form-item>
                                <el-form-item label="上线状态">
                                    <span>{{ podsMap[image.id]? podsMap[image.id].availablePods : 0}}/{{ image.pods }}</span>
                                    <span style="color: #409EFF; font-size: 12px;">&emsp;{{image.deployStatus|deployStatus}}</span>
                                </el-form-item>
                                <el-form-item label="">
                                    <el-button size="mini" v-if="props.row.apptype==0||props.row.apptype!=0&&!props.row.status"
                                               type="text" @click="delDeployedImage(image.id)">删除</el-button>
                                    <el-button size="mini" type="text" @click="openScale(image)">伸缩</el-button>
                                    <el-button size="mini" type="text" @click="openDepDetail(image)">详情</el-button>
                                </el-form-item>
                            </el-row>

                        </el-form>
                    </template>
                </el-table-column>
                <el-table-column min-width="200">
                    <template slot-scope="scope">
                        <el-row>
                            <el-col :span="8" :offset="1">
                                <strong style="color: #409EFF">{{scope.row.name}}</strong>
                            </el-col>
                            <el-col :span="3" :offset="1">
                                <el-tag :type="scope.row.apptype|apptypeFilter" size="mini"
                                        close-transition>{{scope.row.apptype|apptypeName}}</el-tag>
                            </el-col>
                            <el-col :span="5" :offset="1">
                                <i class="el-icon-time"></i>
                                <span>{{dateFormat(scope.row.createtime)}}</span>
                            </el-col>
                            <el-col :span="3" :offset="1">
                                <span>{{scope.row.creator}}</span>

                            </el-col>
                        </el-row>
                    </template>
                </el-table-column>
                <!--<el-table-column label="备注" min-width="110" prop="annotation">
                </el-table-column>-->
                <el-table-column label="操作" min-width="60">
                    <template slot-scope="scope">
                        <el-button size="mini" v-if="!scope.row.status&&scope.row.apptype!=0" type="primary"
                                   @click.native="openOnline(scope.row)" :disabled="!scope.row.deployImages">启动服务
                        </el-button>
                        <el-button size="mini" v-if="scope.row.status&&scope.row.apptype!=0" type="danger"
                                   @click.native="offline(scope.row.id)">服务下线
                        </el-button>
                        <el-button size="mini" @click.native="addCmpnt(scope.row.id)">添加镜像部署</el-button>
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
            <el-dialog width="1000px" title="镜像部署" :visible.sync="showAddDep" center >
                <addDeploy :show.sync="showAddDep" :appid="currentappid"></addDeploy>
            </el-dialog>
            <el-dialog width="1000px" title="部署详情" :visible.sync="showDetailDep" center >
                <deployInfo :show.sync="showDetailDep" :deployedImage="curDepImg"></deployInfo>
            </el-dialog>
            <el-dialog width="500px" title="启动服务" :visible.sync="showStartService" center >
                <el-form label-suffix=":" label-position="top">
                    <el-form-item label="请选择镜像">
                        <el-checkbox-group v-model="choosenImgs">
                            <el-checkbox v-for="image in currentApp.deployImages" :key="image.id" :label="image.id">
                                {{image.name}}
                            </el-checkbox>
                        </el-checkbox-group>
                    </el-form-item>
                    <el-form-item align="right">
                        <el-button size="small" type="primary" :disabled="!choosenImgs.length"
                                   @click.native="online"><icon-svg icon-class="qidong"/>启动
                        </el-button>
                    </el-form-item>
                </el-form>
            </el-dialog>
            <el-dialog width="400px" title="伸缩" :visible.sync="showScale" center >
                <span>当前Pods:&emsp;{{curDepImg.pods}}个, 最大{{curDepImg.maxPods}}个, 最小{{curDepImg.minPods}}个</span>
                <el-form label-suffix=":" label-position="left" style="margin-top: 10px">
                    <el-form-item label="所需Pods">
                        <el-input-number v-model="scalePods" size="small" controls-position="right" :min="1" :max="curDepImg.maxPods"></el-input-number>
                    </el-form-item>
                    <el-form-item align="right">
                        <el-button size="small" type="primary" :disabled="!scalePods"
                                   @click.native="scale"><icon-svg icon-class="yizhixing"/>执行
                        </el-button>
                    </el-form-item>
                </el-form>
            </el-dialog>
        </div>
    </div>
</template>

<script>
import deployInfo from './deployInfo';
import addDeploy from './addDeploy';

export default {
  components: { addDeploy, deployInfo },
  filters: {
    apptypeFilter(status) {
      const statusMap = {
        0: 'success',
        1: 'gray',
        2: 'danger',
      };
      return statusMap[status];
    },
    apptypeName(status) {
      const statusMap = {
        0: '独立应用',
        1: '内部服务',
        2: '外部服务',
      };
      return statusMap[status];
    },
    deployStatus(status) {
      const statusMap = {
        '-2': '停止部署',
        '-1': '未开始',
        0: '完成',
        1: '正在部署',
        2: '其他异常',
      };
      return statusMap[status];
    },
  },
  data() {
    return {
      choosenImgs: [],
      currentappid: null,
      currentApp: {},
      curDepImg: {},
      // 控制添加镜像部署弹窗
      showAddDep: false,
      // 控制启动服务弹窗
      showStartService: false,
      // 控制部署详情弹窗
      showDetailDep: false,
      // 控制扩容/缩容弹窗
      showScale: false,
      scalePods: null,
      qf: {
        name: null,
        creator: null,
        page: 1,
        pagesize: 10,
      },
      total: 50,
      list: [],
      listLoading: false,
      // 获取row的key值
      getRowKeys(row) {
        return row.id;
      },
      // 要展开的行，数值的元素是row的key值
      expands: [],
      podsMap: {},
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
    offline(appid) {
      this.$confirm('您确定删除该下线该服务吗?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'info',
      }).then(() => {
        this.$store.dispatch('deleteService', appid).then(() => {
          this.query();
        });
      }).catch(() => {});
    },
    openOnline(app) {
      this.showStartService = true;
      this.currentApp = app;
    },
    openScale(image) {
      this.showScale = true;
      this.curDepImg = image;
    },
    online() {
      this.$store.dispatch('deployService', { appId: this.currentApp.id, imageDids: this.choosenImgs }).then(() => {
        this.showStartService = false;
        this.choosenImgs = [];
        this.$message.success(`service ${this.currentApp.name}  success`);
        this.query();
      });
    },
    scale() {
      this.$store.dispatch('scalePods', { deployId: this.curDepImg.id, pods: this.scalePods }).then(() => {
        this.showScale = false;
        this.scalePods = [];
        this.$message.success(`scale ${this.curDepImg.name}  success`);
        this.query();
      });
    },
    addCmpnt(appid) {
      if (appid) {
        this.showAddDep = true;
        this.currentappid = appid;
      }
    },
    openDepDetail(deployedImage) {
      console.log(deployedImage);
      this.showDetailDep = true;
      this.curDepImg = deployedImage;
    },
    rowExpandChange(row, expandedRows) {
      if (!row || !expandedRows || !expandedRows.length) {
        return;
      }
      this.expands = expandedRows.map(row1 => row1.id);
      const dis = expandedRows.map(row2 => row2.deployImages.map(d => d.id)).reduce((coll, item) => coll.concat(item));
      this.listLoading = true;
      this.$store.dispatch('getDeployedImagePods', dis).then((data) => {
        this.podsMap = data;
        this.listLoading = false;
      }).catch(() => {
        this.listLoading = false;
      });
    },
    query() {
      this.listLoading = true;
      this.$store.dispatch('getApps', this.qf).then((data) => {
        this.list = data.content;
        this.total = data.totalElements;
        this.listLoading = false;
      });
    },
    delDeployedImage(id) {
      this.$confirm('您确定删除该镜像部署吗?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'info',
      }).then(() => {
        this.$store.dispatch('deleteImageDeploy', id).then(() => {
          this.query();
        });
      }).catch(() => {});
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
<style scoped>
    .table-expand {
        font-size: 0;
    }
    .table-expand label {
        width: 80px;
        color: #99a9bf;
    }
    .table-expand .el-form-item {
        margin-right: 0;
        margin-bottom: 0;
        width: 20%;
    }
</style>
