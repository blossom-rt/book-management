<template>
  <div>
    <el-card>
      <el-input style="width: 240px; margin-right: 10px;" v-model="data.name" placeholder="请输入名称"></el-input>
      <el-button type="primary" @click="load">查询</el-button>
      <el-button type="warning" @click="reset">重置</el-button>
    </el-card>
    <el-card>
      <el-button type="primary" @click="handleAdd">新增</el-button>
      <el-button type="warning" @click="handleBatchDelete">批量删除</el-button>
      <el-button type="info">导入</el-button>
      <el-button type="success">导出</el-button>
    </el-card>
    <el-card>
      <el-table :data="data.books" stripe @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="55"></el-table-column>
        <el-table-column label="序号" width="80">
          <template #default="scope">
            {{ (data.pageNum - 1) * data.pageSize + scope.$index + 1 }}
          </template>
        </el-table-column>
        <el-table-column label="名称" prop="name"></el-table-column>
        <el-table-column label="价格" prop="price"></el-table-column>
        <el-table-column label="类别" prop="category"></el-table-column>
        <el-table-column label="ISBN" prop="isbn"></el-table-column>
        <el-table-column label="出版时间" prop="time"></el-table-column>
        <el-table-column label="出版社" prop="press"></el-table-column>
        <el-table-column label="操作">
          <template #default="scope" style="width: 120px">
            <el-button 
              type="primary" 
              :icon="Edit" 
              circle
              @click="handleUpdate(scope.row)"
            ></el-button>
            <el-button 
              type="danger" 
              :icon="Delete" 
              circle
              @click="handleDelete(scope.row)"
            ></el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <div>
        <el-pagination 
            @size-change="load"
            @current-change="load"
            v-model:current-page="data.pageNum"
            v-model:page-size="data.pageSize"
            :page-sizes="[5, 10, 15, 20]"
            background 
            layout="total, sizes, prev, pager, next, jumper"
            :total="data.total" />
    </div>
    <el-dialog v-model="data.dialogFormVisible" title="图书信息" width="500">
    <el-form :model="data.form">
      <el-form-item label="图书名称" label-width="80px">
        <el-input v-model="data.form.name" autocomplete="off" />
      </el-form-item>
      <el-form-item label="图书价格" label-width="80px">
        <el-input v-model="data.form.price" autocomplete="off" />
      </el-form-item>
      <el-form-item label="图书类别" label-width="80px">
        <el-select v-model="data.form.category" placeholder="请选择图书类别">
          <el-option label="计算机类" value="计算机类" />
          <el-option label="文学类" value="文学类" />
          <el-option label="科技类" value="科技类" />
        </el-select>
      </el-form-item>
      <el-form-item label="出版时间" label-width="80px">
        <el-date-picker 
            v-model="data.form.time"
            type="date"
            placeholder="请选择一个日期"
            style="width: 100%"
          >
        </el-date-picker>
      </el-form-item>
      <el-form-item label="ISBN" label-width="80px">
        <el-input v-model="data.form.isbn" autocomplete="off" />
      </el-form-item>
      <el-form-item label="图书作者" label-width="80px">
        <el-input v-model="data.form.author" autocomplete="off" />
      </el-form-item>
      <el-form-item label="出版社" label-width="80px">
        <el-input v-model="data.form.press" autocomplete="off" />
      </el-form-item>
    </el-form>
    <template #footer>
      <div class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取消</el-button>
        <el-button type="primary" @click="save()">
          保存
        </el-button>
      </div>
    </template>
  </el-dialog>  
  </div>
</template>

<style scoped>

</style>

<script setup lang="ts">
import { reactive } from "vue";
import request from '../utils/request';
import { ElMessage, ElMessageBox } from 'element-plus';
import { Edit, Delete } from '@element-plus/icons-vue'


const data = reactive({
  name: null,
  books: [],
  pageNum: 1,
  pageSize: 5,
  total: 0,
  dialogFormVisible: false,
  form: {},
  multipleSelection: []
})

const load = () => {
  request.get('/book/books', {
    params: {
      name: data.name,
      pageNum: data.pageNum,
      pageSize: data.pageSize
    }
  }).then(res => {
    console.log(res)
    data.books = res.data.records
    data.total = res.data.total
  })
}

load()

const reset = () => {
  data.name = null
  load()
}

const handleAdd = () => {
  data.dialogFormVisible = true
  data.form = {} //清空数据
}

const save = () => {
  data.form.id ? update() : add()
}

const add = () => {
  request.post('/book/books', data.form).then(res => {
    if (res.code === '200') {
      data.dialogFormVisible = false;
      ElMessage.success('操作成功！');
      load();
    } else {
      ElMessage.error(res.msg);
    }
  });
};

const update = () => {
  request.put('/book/books', data.form).then(res => {
    if (res.code === '200') {
      data.dialogFormVisible = false;
      ElMessage.success('操作成功！');
      load();
    } else {
      ElMessage.error(res.msg);
    }
  });
};

const handleUpdate = (row) => {
  data.form = JSON.parse(JSON.stringify(row)) // 深拷贝一个新的对象用于编辑，不影响行对象
  data.dialogFormVisible = true;
}

const handleDelete = (row) => {
  ElMessageBox.confirm('确认删除该图书？', '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning',
  }).then(() => {
    request.delete('/book/books/' + row.id).then(res => {
      if (res.code === '200') {
        ElMessage.success('删除成功！');
        load();
      } else {
        ElMessage.error(res.msg);
      }
    });
  }).catch(() => {});
};

const handleBatchDelete = () => {
  if (data.multipleSelection.length === 0) {
    ElMessage.warning('请先选择要删除的数据');
    return;
  }
  ElMessageBox.confirm('确认删除选中的 ' + data.multipleSelection.length + ' 条记录？', '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning',
  }).then(() => {
    const ids = data.multipleSelection.map(row => row.id);
    request.delete('/book/books/batch', { data: ids }).then(res => {
      if (res.code === '200') {
        ElMessage.success('批量删除成功！');
        load();
      } else {
        ElMessage.error(res.msg);
      }
    });
  }).catch(() => {});
};

const handleSelectionChange = (val) => {
  data.multipleSelection = val;
  console.log(data.multipleSelection)
};

</script>