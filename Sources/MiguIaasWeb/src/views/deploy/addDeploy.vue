<template>
    <div class="app-container">
        <el-form ref="form" :model="form" :rules="rules" label-width="120px" label-suffix=":" :disabled="loading">
            <el-tabs type="card">
                <el-tab-pane label="集群/镜像">

                    <el-card class="box-card">
                        <el-form-item label="集群" prop="clusterId">
                            <el-select name="clusterId" v-model="form.clusterId" @change="clusterChange">
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
                                <el-input-number :controls="false" v-model="form.containerPort"></el-input-number>
                            </el-form-item>
                            <el-form-item label="最小实例数" prop="minPods">
                                <el-input-number controls-position="right" v-model="form.minPods"></el-input-number>
                            </el-form-item>
                            <el-form-item label="内存限制(MB)" prop="memoryLimits">
                                <el-input-number :controls="false" v-model="form.memoryLimits"></el-input-number>
                            </el-form-item>
                            <el-form-item label="唯一性部署" prop="uniqueDeploy">
                                <el-switch v-model="form.uniqueDeploy" active-color="#13ce66"
                                           inactive-color="#ff4949" @change="uniqueCheck"></el-switch>
                            </el-form-item>
                        </el-col>
                        <el-col :span="10" :offset="2">
                            <el-form-item label="等待超时时间(S)" prop="timeOut">
                                <el-input-number :controls="false" v-model="form.timeOut"></el-input-number>
                            </el-form-item>
                            <el-form-item label="最大实例数" prop="maxPods">
                                <el-input-number controls-position="right" :min="1" v-model="form.maxPods"></el-input-number>
                            </el-form-item>
                            <el-form-item label="CPU限制" prop="cpuLimits">
                                <el-input-number :controls="false" v-model="form.cpuLimits"></el-input-number>
                            </el-form-item>
                            <el-form-item label="实例数" prop="pods">
                                <el-input-number controls-position="right" :min="1"
                                                 v-model="form.pods"></el-input-number>
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
                    <el-button type="primary" icon="el-icon-plus" @click="addMountDirs">新增目录</el-button>
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
                                    :prop="'mountDirs.' + index + '.serverDir'"
                                    :rules="{required: true, message: '主机目录'+(index+1)+'不能为空', trigger: 'blur'}">
                                <el-input v-model="dir.serverDir"></el-input>
                            </el-form-item>
                        </el-col>
                        <el-col :span="12">
                            <el-form-item
                                    v-for="(dir, index) in form.mountDirs"
                                    :label="'->'"
                                    :key="dir.key"
                                    :prop="'mountDirs.' + index + '.containerDir'"
                                    :rules="{required: true, message: '容器目录'+(index+1)+'不能为空', trigger: 'blur'}">
                                <el-input v-model="dir.containerDir">
                                    <el-button @click.prevent="removeMountDirs(dir)" icon="el-icon-delete"
                                               slot="append"></el-button>
                                </el-input>

                            </el-form-item>
                        </el-col>
                    </el-card>

                </el-tab-pane>
                <el-tab-pane label="环境变量">
                    <el-button type="primary" icon="el-icon-plus" @click="addEnvs">新增变量</el-button>
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
                                    :prop="'envs.' + index + '.key'"
                                    :rules="{required: true, message: '变量名不能为空', trigger: 'blur'}">
                                <el-input v-model="env.key"></el-input>
                            </el-form-item>
                        </el-col>
                        <el-col :span="12">
                            <el-form-item
                                    v-for="(env, index) in form.envs"
                                    :label="'->'"
                                    :key="env.key"
                                    :prop="'envs.' + index + '.value'"
                                    :rules="{required: true, message: '变量值不能为空', trigger: 'blur'}">
                                <el-input v-model="env.value">
                                    <el-button @click.prevent="removeEnvs(env)" icon="el-icon-delete"
                                               slot="append"></el-button>
                                </el-input>

                            </el-form-item>
                        </el-col>
                    </el-card>
                </el-tab-pane>
                <el-tab-pane label="健康检查">
                    <el-card class="box-card">
                        <el-form-item label="健康检查脚本" prop="healthCheck">
                            <el-input type="textarea" :autosize="{ minRows: 4, maxRows: 10 }" v-model="form.healthCheck"
                                      placeholder="脚本的返回码需要为0"></el-input>
                        </el-form-item>
                    </el-card>
                </el-tab-pane>

            </el-tabs>

            <el-form-item style="margin-top: 15px" align="right">
                <el-button type="primary" :loading="loading" size="medium" @click="onSubmit"><icon-svg icon-class="icon_deploy"/>Deploy</el-button>
                <el-button @click="onCancel">Cancel</el-button>
            </el-form-item>

        </el-form>

    </div>
</template>

<script>
import ClusterApi from '@/api/cluster';

export default {
  name: 'addDeploy',
  props: {
    show: Boolean,
    appid: Number,
  },
  computed: {},
  data() {
    const validateUnique = (rule, value, callback) => {
      if (this.form.uniqueDeploy && this.form.maxPods > this.srvCount) {
        callback(new Error(`开启唯一性部署后,实例数和最大实例数不能超过集群机器总数: ${this.srvCount}`));
      } else {
        callback();
      }
    };
    const validateMinPods = (rule, value, callback) => {
      if (this.form.maxPods && this.form.minPods > this.form.maxPods) {
        callback(new Error('最小实例数不能大于最大实例数'));
      } else {
        callback();
      }
    };
    const numberValidator = (rule, value, callback) => {
      if (value && !/^\d+(\.\d+)?$/.test(value)) {
        callback(new Error('请输入大于0的整数'));
      } else {
        callback();
      }
    };
    return {
      form: {
        mountDirs: [{
          serverDir: '',
          containerDir: '',
        }],
        envs: [{
          key: '',
          value: '',
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

      },
      rules: {
        clusterId: [{ required: true, trigger: 'change', message: '请选择集群' }],
        imageId: [{ required: true, trigger: 'change', message: '请选择镜像' }],
        pods: [{
          required: true, trigger: 'blur', message: '请输入实例数', validator: validateUnique,
        },
        {
          trigger: 'blur', validator: numberValidator,
        }],
        minPods: [{ required: true, trigger: 'blur', validator: validateMinPods }],
        maxPods: [{ required: true, trigger: 'blur', validator: validateUnique }],
        containerPort: [{ trigger: 'blur', validator: numberValidator }],
        timeOut: [{ trigger: 'blur', validator: numberValidator }],
      },
      clusters: [],
      srvCount: null,
      images: [],
      loading: false,
      imageloading: false,
    };
  },
  methods: {
    addMountDirs() {
      this.form.mountDirs.push({
        serverDir: '',
        containerDir: '',
      });
    },
    removeMountDirs(item) {
      const index = this.form.mountDirs.indexOf(item);
      if (index !== -1) {
        this.form.mountDirs.splice(index, 1);
      }
    },
    addEnvs() {
      this.form.envs.push({
        key: '',
        value: '',
      });
    },
    removeEnvs(item) {
      const index = this.form.envs.indexOf(item);
      if (index !== -1) {
        this.form.envs.splice(index, 1);
      }
    },
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
    clusterChange(clusterid) {
      ClusterApi.srvcount(clusterid).then((count) => {
        this.srvCount = count;
      });
    },
    uniqueCheck() {
      this.$refs.form.validate();
    },
    onSubmit() {
      this.$refs.form.validate((valid) => {
        if (valid) {
          this.loading = true;
          this.$store.dispatch('deployImage', this.form).then((resp) => {
            this.loading = false;
            this.$refs.form.resetFields();
            this.$message.success(resp);
          }).catch(() => {
            this.loading = false;
          });
        } else {
          this.$message.error('请检查参数是否完全输入!');
          return false;
        }
        return true;
      });
    },
    onCancel() {
      this.$refs.form.resetFields();
      this.$message({
        message: 'canceled!',
        type: 'warning',
      });
      this.$emit('update:show', this.show = false);
    },
  },
  created() {
    this.searchImages();
    ClusterApi.list().then((data) => {
      this.clusters = data;
    });
  },
  watch: {
    appid(val) {
      this.form.appId = val;
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
</style>
