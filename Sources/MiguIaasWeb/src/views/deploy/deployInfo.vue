<template>
    <div class="app-container">
        <el-form ref="form" :model="form" label-width="120px" label-suffix=":" :disabled="true">
            <el-tabs type="card">
                <el-tab-pane label="集群/镜像">
                    <el-card class="box-card">
                        <el-form-item label="集群" prop="clusterId">
                            <el-select name="clusterId" v-model="form.clusterId">
                                <el-option
                                        v-for="item in clusters"
                                        :key="item.id"
                                        :label="item.name"
                                        :value="item.id">
                                </el-option>
                            </el-select>
                        </el-form-item>

                        <el-form-item label="镜像" prop="imageId">
                            <el-select name="imageId" v-model="form.imageId" filterable remote
                                       :remote-method="searchImages"
                                       placeholder="Search and select" :loading="imageloading" icon="el-icon-search">
                                <el-option
                                        v-for="item in images"
                                        :key="item.id"
                                        :label="item.name+':'+item.version"
                                        :value="item.id">
                                </el-option>
                            </el-select>
                        </el-form-item>
                    </el-card>
                </el-tab-pane>
                <el-tab-pane label="安全/主机">

                    <el-card class="box-card">

                        <el-col :span="10">
                            <el-form-item label="实例端口" prop="containerPort">
                                <el-input v-model="form.containerPort"></el-input>
                            </el-form-item>
                            <el-form-item label="最小实例数" prop="minPods">
                                <el-input v-model="form.minPods"></el-input>
                            </el-form-item>
                            <el-form-item label="内存限制(MB)" prop="memoryLimits">
                                <el-input v-model="form.memoryLimits"></el-input>
                            </el-form-item>
                            <el-form-item label="唯一性部署" prop="uniqueDeploy">
                                <el-switch v-model="form.uniqueDeploy" active-color="#13ce66"
                                           inactive-color="#ff4949"></el-switch>
                            </el-form-item>
                        </el-col>
                        <el-col :span="10" :offset="2">
                            <el-form-item label="等待超时时间(S)" prop="timeOut">
                                <el-input v-model="form.timeOut"></el-input>
                            </el-form-item>
                            <el-form-item label="最大实例数" prop="maxPods">
                                <el-input v-model="form.maxPods"></el-input>
                            </el-form-item>
                            <el-form-item label="CPU限制" prop="cpuLimits">
                                <el-input v-model="form.cpuLimits"></el-input>
                            </el-form-item>
                            <el-form-item label="实例数" prop="pods">
                                <el-input v-model="form.pods"></el-input>
                            </el-form-item>
                        </el-col>

                    </el-card>

                </el-tab-pane>
                <el-tab-pane label="命令">
                    <el-card class="box-card">
                        <el-form-item label="命令" prop="initCmd">
                            <el-input v-model="form.initCmd" placeholder="例如: /usr/sbin/httpd -f httpd.conf"></el-input>
                        </el-form-item>
                    </el-card>
                </el-tab-pane>
                <el-tab-pane label="挂载目录">
                    <el-card class="box-card">
                        <div slot="header" class="clearfix">
                            <el-col :span="12">
                                <span>主机目录</span>
                            </el-col>
                            <el-col :span="10">
                                <span>容器目录</span>
                            </el-col>
                        </div>
                        <el-col :span="10">
                            <el-form-item
                                    v-for="(dir, index) in form.mountDirs"
                                    :key="dir.key"
                                    :prop="'mountDirs.' + index + '.serverDir'">
                                <el-input v-model="dir.serverDir"></el-input>
                            </el-form-item>
                        </el-col>
                        <el-col :span="12">
                            <el-form-item
                                    v-for="(dir, index) in form.mountDirs"
                                    :label="'->'"
                                    :key="dir.key"
                                    :prop="'mountDirs.' + index + '.containerDir'">
                                <el-input v-model="dir.containerDir"></el-input>

                            </el-form-item>
                        </el-col>
                    </el-card>

                </el-tab-pane>
                <el-tab-pane label="环境变量">
                    <el-card class="box-card">
                        <div slot="header" class="clearfix">
                            <el-col :span="12">
                                <span>环境变量名</span>
                            </el-col>
                            <el-col :span="10">
                                <span>环境变量值</span>
                            </el-col>
                        </div>
                        <el-col :span="10">
                            <el-form-item
                                    v-for="(env, index) in form.envs"
                                    :key="env.key"
                                    :prop="'envs.' + index + '.key'">
                                <el-input v-model="env.key"></el-input>
                            </el-form-item>
                        </el-col>
                        <el-col :span="12">
                            <el-form-item
                                    v-for="(env, index) in form.envs"
                                    :label="'->'"
                                    :key="env.key"
                                    :prop="'envs.' + index + '.value'">
                                <el-input v-model="env.value"></el-input>

                            </el-form-item>
                        </el-col>
                    </el-card>
                </el-tab-pane>
                <el-tab-pane label="健康检查">
                    <el-card class="box-card">
                        <el-form-item label="健康检查脚本" prop="healthCheck">
                            <el-input type="textarea" :autosize="{ minRows: 4, maxRows: 10 }"
                                      v-model="form.healthCheck"></el-input>
                        </el-form-item>
                    </el-card>
                </el-tab-pane>
                <el-tab-pane label="主机列表">
                    <div class="server-table">
                        <el-card class="server-card" shadow="hover" :body-style="{padding: '5px',marginLeft: '10px'}"
                                 v-for="srv in servers" :key="srv.hostname">
                            <div slot="header" class="clearfix">
                                <span>{{srv.hostname}}</span>
                            </div>
                            <div class="text">
                                <icon-svg icon-class="cluster"/>
                                {{srv.ipv4 }}
                            </div>
                        </el-card>
                    </div>
                </el-tab-pane>

            </el-tabs>

        </el-form>
    </div>
</template>

<script>
import ClusterApi from '@/api/cluster';

export default {
  name: 'deployInfo',
  props: {
    show: Boolean,
    deployedImage: Object,
  },
  computed: {},
  data() {
    return {
      form: {
        mountDirs: [{
          serverDir: 'mysql-pv-claim',
          containerDir: '/var/lib/mysql',
        }],
        envs: [{
          key: 'MYSQL_ROOT_PASSWORD',
          value: 'iflytek_migu',
        }],
        appId: null,
        clusterId: null,
        imageId: null,
        pods: null,
        minPods: 1,
        maxPods: null,
        timeOut: 2000,
        cpuLimits: null,
        memoryLimits: null,
        initCmd: null,
        containerPort: null,
        uniqueDeploy: false,
        healthCheck: null,
        id: null,

      },
      clusters: [],
      images: [],
      servers: [],
      loading: false,
      imageloading: false,
    };
  },
  methods: {
    searchImages(name) {
      this.imageloading = true;
      setTimeout(() => {
        this.imageloading = false;
        this.$store.dispatch('searchImages', name).then((resp) => {
          this.imageloading = false;
          this.images = resp;
        }).catch(() => {
          this.imageloading = false;
        });
      }, 200);
    },
    fillForm(val) {
      Object.assign(this.form, val);
      if (val.mountDirs) {
        this.form.mountDirs = JSON.parse(val.mountDirs);
      }
      if (val.envs) {
        this.form.envs = JSON.parse(val.envs);
      }
      if (val.healthCheck) {
        this.form.healthCheck = JSON.parse(val.healthCheck).config;
      }
      this.$store.dispatch('getImageServers', val.id).then((data) => {
        this.servers = data;
      });
    },
  },
  created() {
    this.searchImages();
    ClusterApi.list().then((data) => {
      this.clusters = data;
    });
    this.fillForm(this.deployedImage);
  },
  watch: {
    deployedImage(val) {
      this.fillForm(val);
    },
  },
};
</script>

<style scoped>
    .text {
        font-size: 14px;
    }

    .item {
        margin-bottom: 18px;
    }

    .clearfix:before,
    .clearfix:after {
        display: table;
        content: "";
    }

    .clearfix:after {
        clear: both
    }

    .box-card {
        width: 100%;
        margin-top: 5px;
    }

    .server-table {
        display: flex;
        flex-flow: row;
    }

    .server-card {
        width: 22%;
        margin-right: 5px;
    }
</style>
