<script setup lang="ts">
import router from "@/router/index.js";
</script>

<template>
  <!-- 头部开始-->
  <div style="height: 60px; background-color: cornflowerblue; display: flex;">
    <div style="width: 200px; display: flex; align-items: center; font-size: 20px;">
        图书后台管理系统
    </div>
    <div style="flex: 1"></div>
    <div style="width: 150px; display: flex; align-items: center; padding-right: 10px">
      <img src="@/assets/touxiang.jpg" alt="头像" style="width: 40px; height: 40px;">
      <span style="color: white; margin-left: 5px;">1 || 0</span>
    </div>
  </div>
  <!-- 头部结束-->

  <!-- 主体开始-->
  <div style="display: flex">
    <!-- 主体左侧导航区域开始-->
    <div style="width: 200px; border-right: 1px solid #dddddd; min-height: calc(100vh - 60px)">
      <el-menu
          :default-active="router.currentRoute.value.path"
          style="border: 0;"
          class="el-menu-vertical-demo"
          router
          

      >
        <el-menu-item index="/manage/home">
            <el-icon><House /></el-icon>
            系统首页
        </el-menu-item>
        <el-sub-menu index="1">
          <template #title>
            <el-icon><UserFilled /></el-icon>
            <span>用户管理</span>
          </template>
            <el-menu-item index="1-1">管理员信息</el-menu-item>
            <el-menu-item index="1-2">普通用户信息</el-menu-item>
        </el-sub-menu>

        <el-menu-item index="/manage/book">
            <el-icon><Memo /></el-icon>
            图书管理
        </el-menu-item>

      </el-menu>
    </div>
    <!-- 主体左侧导航区域结束-->

    <!-- 主体右侧数据区域开始-->
    <div style="flex: 1; width: 0; background-color: aliceblue; padding: 10px;">
      <RouterView></RouterView>
    </div>
    <!-- 主体右侧数据区域结束-->
  </div>
  <!-- 主体结束 -->

  <!-- <div>
    父级页面，可以嵌套其他的子路由页面
  </div>
  <div style="width: 50%; background-color: azure; padding: 30px; border: 2px solid #ccc; margin: 30px auto">
    <RouterView></RouterView>
  </div> -->
</template>

<style scoped>

</style>