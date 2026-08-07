<script setup lang="ts">
import router from "@/router/index"
</script>

<template>
  <div style="text-align: center">
    <img src="@/assets/404.png" alt="404图片">
    <div>
      <el-button type="primary" style="width: 150px; height: 60px; font-size: 20px" @click="router.push('/')">返回主页</el-button>
    </div>
  </div>
</template>

<style scoped>

</style>