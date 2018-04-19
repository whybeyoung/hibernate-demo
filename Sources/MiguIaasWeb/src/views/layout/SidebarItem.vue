<template>
  <div>
    <template v-for="item in routes">
      <router-link v-if="!item.hidden&&item.noDropdown&&item.children.length>0" :to="item.path+'/'+item.children[0].path" :key="item.path">
        <el-menu-item :index="item.path+'/'+item.children[0].path">
          <icon-svg v-if='item.icon' :icon-class="item.icon" /> {{item.children[0].meta.displayName || item.name}}
        </el-menu-item>
      </router-link>
      <el-submenu :index="item.name" v-if="!item.noDropdown&&!item.hidden" :key="item.path">
        <template slot="title">
          <icon-svg v-if='item.icon' :icon-class="item.icon" /> {{item.meta.displayName || item.name}}
        </template>
        <template v-for="child in item.children" v-if='!child.hidden'>
          <sidebar-item class='menu-indent' v-if='child.children&&child.children.length>0' :routes='[child]' :key="child.path"> </sidebar-item>
          <router-link v-else class="menu-indent" :to="item.path+'/'+child.path" :key="child.path">
            <el-menu-item :index="item.path+'/'+child.path">
              <icon-svg v-if='child.icon' :icon-class="child.icon" />{{child.meta.displayName || item.name}}
            </el-menu-item>
          </router-link>
        </template>
      </el-submenu>
    </template>
  </div>
</template>

<script>
export default {
  name: 'SidebarItem',
  props: {
    routes: {
      type: Array,
    },
  },
};
</script>

<style rel="stylesheet/scss" lang="scss" >
.el-submenu__icon-arrow{
  color: #fff !important;
  font-size: 14px !important;
}
.svg-icon {
  margin-right: 10px;
  width: 1.2em;
  height: 1.2em;
  vertical-align: -0.2em;
  fill: currentColor;
  overflow: hidden;
}
.hideSidebar .menu-indent{
  display: block;
  text-indent: 10px;
}
.el-menu-item{
  min-width: 0px !important;
}
</style>

