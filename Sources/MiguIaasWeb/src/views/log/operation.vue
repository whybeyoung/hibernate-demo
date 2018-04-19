<template>
  <div class="log">
      <el-row :gutter="20" style="margin-right:0px;">
        <el-col :span="6">
            <el-select v-model="logType" placeholder="请选择日志类型">
              <el-option
                v-for="item in options"
                :key="item.value"
                :label="item.label"
                :value="item.value">
              </el-option>
            </el-select>
        </el-col>
        <el-col :span="6">
          <div>
           <span> 创建用户：</span>
           <el-input class="username" v-model="creator" placeholder="请输入用户账号"></el-input>
          </div>
        </el-col>
        <el-col :span="6"><el-button type="primary" @click="getOperationLog(1)">查询</el-button></el-col>
      </el-row>

      <el-table class="list"
        :data="logList"
        border
        style="width: 100%">
        <el-table-column
          prop="createtime"
          label="创建日期"
          width="140">
        </el-table-column>
        <el-table-column
          prop="type"
          label="操作类型"
          width="100">
        </el-table-column>
        <el-table-column
          prop="obj"
          label="操作对象">
        </el-table-column>
        <el-table-column
          prop="detail"
          label="操作详情">
        </el-table-column>
        <el-table-column
          prop="param"
          label="操作参数">
        </el-table-column>
      </el-table>

      <el-pagination class="page" v-show="allEles>0"
        background
        layout="prev, pager, next"
        :current-page.sync=pageIndex
        :page-size=pageSize
        :total=allEles
        @current-change="getOperationLog">
      </el-pagination>

  </div>
</template>

<script>
import GetOperationLog from '@/api/log';

export default {
  data() {
    return {
      options: [
        {
          label: '新建部署',
          value: 0,
        },
        {
          label: '下线',
          value: 1,
        },
        {
          label: '扩容缩容',
          value: 2,
        },
        {
          label: '删除',
          value: 3,
        },
      ],
      logType: '',
      creator: '',
      pageIndex: 1,
      pageSize: 10,
      allEles: 0,
      logList: [],
    };
  },
  created() {
    this.getOperationLog(1);
  },
  methods: {
    getOperationLog(from) {
      if (from === 1) {
        this.pageIndex = 1;
      }
      GetOperationLog(this.logType, this.creator, this.pageIndex, this.pageSize).then((result) => {
        this.logList = result.content;
        this.pageSize = result.size;
        this.pageIndex = result.number + 1;
        this.allEles = result.totalElements;
        if (this.allEles === 0) {
          this.$message('暂无数据');
        } else {
          this.logList = this.logList.map((item) => {
            switch (item.type) {
              case 'NEW_DEPLOY':
                item.type = '新建部署';
                break;
              case 'OFFLINE':
                item.type = '下线';
                break;
              case 'SCALE':
                item.type = '扩容/缩容';
                break;
              case 'DELETE':
                item.type = '删除';
                break;
              default:
                item.type = '';
            }
            const date = new Date(item.createtime);
            item.createtime = `${date.getFullYear()}/${date.getMonth() + 1}/${date.getDate()} ${date.getHours()}:${date.getMinutes()}:${date.getSeconds()}`;

            return item;
          });
        }
      });
    },
  },


};
</script>

<style lang="scss" scoped>
.log{
  margin: 28px 0 0 28px;
  .username{
    display: inline-block;
    width: 60%;
  }
  .list{
    margin-top: 20px;
  }
  .page{
    float: right;
    margin: 28px 0;
  }
}

</style>

