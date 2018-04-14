<template>
    <div class="app-container">
        <el-form ref="form" :model="form" :rules="rules" label-width="120px" label-suffix=":" :disabled="loading">
            <el-form-item label="镜像名" prop="name">
                <el-input v-model="form.name" style="width: 48%"></el-input>
            </el-form-item>
            <el-form-item label="版本" prop="version">
                <el-input v-model="form.version" style="width: 48%"></el-input>
            </el-form-item>
            <el-form-item label="FTP路径" prop="ftpPath">
                <el-input v-model="form.ftpPath" style="width: 48%"></el-input>
            </el-form-item>
            <el-form-item label="备注" prop="annotation">
                <el-input type="textarea" v-model="form.annotation" style="width: 48%"></el-input>
            </el-form-item>
            <el-form-item>
                <el-button type="primary" :loading="loading" @click="onSubmit">Upload</el-button>
                <el-button @click="onCancel">Cancel</el-button>
            </el-form-item>
        </el-form>
    </div>
</template>

<script>
import { mapGetters } from 'vuex';

export default {
  name: 'upload',
  computed: {
    ...mapGetters([
      'nickname',
    ]),
  },
  data() {
    return {
      form: {
        name: '',
        version: '',
        ftpPath: '',
        annotation: '',
        creator: '',
      },
      rules: {
        name: [{ required: true, trigger: 'blur' }],
        version: [{ required: true, trigger: 'blur' }],
        ftpPath: [{ required: true, trigger: 'blur' }],
      },
      loading: false,
    };
  },
  methods: {
    onSubmit() {
      this.$refs.form.validate((valid) => {
        if (valid) {
          this.loading = true;
          this.form.creator = this.nickname;
          this.$store.dispatch('createImage', this.form).then((resp) => {
            this.loading = false;
            this.$refs.form.resetFields();
            this.$message.success(resp);
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
  },
};
</script>

<style scoped>
    .line {
        text-align: center;
    }
</style>
