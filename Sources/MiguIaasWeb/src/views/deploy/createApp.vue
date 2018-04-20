<template>
    <div class="app-container">

        <el-form ref="form" :model="form" :rules="rules" label-width="120px" label-suffix=":" :disabled="loading">
            <el-card style="width: 100%">
                <el-form-item label="应用名称" prop="name" style="width: 60%">
                    <el-input v-model="form.name"></el-input>
                </el-form-item>
                <el-form-item label="备注" prop="annotation" style="width: 60%">
                    <el-input type="textarea" v-model="form.annotation"></el-input>
                </el-form-item>
                <el-form-item label="命名空间" prop="namespace">
                    <el-select v-model="form.namespace">
                        <el-option
                                v-for="item in nss"
                                :key="item"
                                :label="item"
                                :value="item">
                        </el-option>
                    </el-select>
                </el-form-item>

                <el-form-item label="服务应用标识" prop="apptype">
                    <el-radio-group v-model="form.apptype">
                        <el-radio :label="0">独立应用</el-radio>
                        <el-radio :label="1">内部服务</el-radio>
                        <el-radio :label="2">外部服务</el-radio>
                    </el-radio-group>
                </el-form-item>

                <el-form-item label="外部端口" prop="nodePort" v-if="form.apptype == 2" style="width: 60%">
                    <el-input v-model="form.nodePort"></el-input>
                </el-form-item>
                <el-form-item label="内部端口" prop="podPort" v-if="form.apptype == 1 || form.apptype == 2" style="width: 60%">
                    <el-input v-model="form.podPort"></el-input>
                </el-form-item>

            </el-card>
            <el-form-item style="margin-top: 15px">
                <el-button type="primary" :loading="loading" icon="el-icon-plus" @click="onSubmit">Create</el-button>
                <el-button @click="onCancel">Cancel</el-button>
            </el-form-item>

        </el-form>


    </div>
</template>

<script>

export default {
  name: 'upload',
  computed: {
  },
  data() {
    const validateName = (rule, value, callback) => {
      if (!/[a-z0-9]([-a-z0-9]*[a-z0-9])?/.test(value)) {
        callback(new Error('请输入小写字母,数字,短横线'));
      } else {
        callback();
      }
    };
    const validatePort = (rule, value, callback) => {
      if (value < 30000 || value > 32767) {
        callback(new Error('端口范围30000-32767'));
      } else {
        callback();
      }
    };
    return {
      form: {
        name: '',
        apptype: null,
        namespace: '',
        nodePort: null,
        podPort: null,
        annotation: '',
        creator: '',
      },
      rules: {
        name: [{
          required: true, trigger: 'blur', message: '请输入应用名称', validator: validateName,
        }],
        apptype: [{ required: true, trigger: 'change', message: '请选择应用类型' }],
        namespace: [{ required: true, trigger: 'change', message: '请选择命名空间' }],
        nodePort: [{ required: true, trigger: 'blur', validator: validatePort }],
        podPort: [{ required: true, trigger: 'blur', validator: validatePort }],
      },
      nss: [],
      loading: false,
      showService: false,
    };
  },
  methods: {
    onSubmit() {
      this.$refs.form.validate((valid) => {
        if (valid) {
          this.loading = true;
          this.$store.dispatch('createApp', this.form).then(() => {
            this.loading = false;
            this.$refs.form.resetFields();
            this.$message.success('success');
          }).catch(() => {
            this.loading = false;
          });
        } else {
          console.log('error submit!!');
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
    },
    loadNss() {
      this.loading = true;
      this.$store.dispatch('getNamespaces').then((data) => {
        this.nss = data;
        this.loading = false;
      }).catch(() => {
        this.loading = false;
      });
    },
  },
  created() {
    this.loadNss();
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
