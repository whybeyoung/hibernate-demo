<template>
  <div class="content-container">
    <div slot="header">主机管理</div>
    <el-table
        :data="addedServers"
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
  </div>
</template>

<style>
  .content-container {
    padding: 20px;
  }
</style>
