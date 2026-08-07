<template>
  <div class="item">
    您好Vue！
  </div>

  <div style="margin-bottom: 30px">
    <RouterLink to="/manage/test">通过RouterLink跳转到Test</RouterLink><br>
    <a href="/manage/test">通过超链接a跳转到Test</a>
  </div>

  <div style="margin-bottom: 30px">
    <el-button type="success" @click="router.push('/manage/test')">push跳转</el-button>
    <el-button type="primary" @click="router.replace('/manage/test')">replace跳转</el-button>
  </div>
</template>
<script setup lang="ts">
  import router from '@/router/index.js'
  import request from '../utils/request';
</script>
