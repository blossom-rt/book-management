# 图书管理系统 (Book Management System)

前后端分离的图书管理 Web 应用，支持图书的增删改查、分页、按名称模糊搜索与批量删除。

## 技术栈

| 端 | 技术 |
|---|---|
| 后端 | Spring Boot 3.5.16 · MyBatis-Plus 3.5.16 · MySQL 8 |
| 前端 | Vue 3 · Vite 8 · Element Plus 2 · Vue Router 4 · Axios |

## 目录结构

```
├── book-management-system/   # 后端 Spring Boot 项目
│   └── src/main/resources/
│       ├── application.yml          # 本地配置（不入库，见下）
│       └── application.example.yml  # 配置文件模板（入库）
├── book-management-vue/      # 前端 Vue 项目
├── db/init/init.sql          # 数据库初始化脚本
└── .env.example              # 环境变量模板
```

## 快速开始

### 0. 环境要求

- JDK 21
- Maven（或使用项目自带的 `mvnw`）
- Node.js 18+
- MySQL 8（**端口 3307**，可通过配置修改）

### 1. 初始化数据库

```bash
mysql -uroot -p < db/init/init.sql
```

脚本会创建 `booksystem` 库和 `t_book` 表，并插入 3 条示例数据。

### 2. 配置后端

```bash
# 复制配置模板（首次 clone 后执行一次）
cp book-management-system/src/main/resources/application.example.yml \
   book-management-system/src/main/resources/application.yml
```

设置数据库密码环境变量：

```bash
# Windows (cmd)
set DB_PASSWORD=你的密码

# Windows (PowerShell)
$env:DB_PASSWORD="你的密码"

# Linux / macOS
export DB_PASSWORD=你的密码
```

> 也可以直接在 `application.yml` 中把 `${DB_PASSWORD}` 替换为真实密码。

### 3. 启动后端

```bash
cd book-management-system
./mvnw spring-boot:run        # Windows 用 mvnw.cmd
```

后端启动于 http://localhost:8080

### 4. 启动前端

```bash
cd book-management-vue
npm install
npm run dev
```

前端访问 http://localhost:5173

## 功能列表

- 图书列表：分页展示 + 按名称模糊查询
- 新增 / 编辑图书（弹窗表单）
- 单个删除 / 批量删除（带确认提示）
- 响应式管理布局（顶部栏 + 左侧导航）
- 404 兜底页

## 后端接口一览（前缀 `/book`）

| 方法 | 路径 | 说明 |
|---|---|---|
| GET | `/book/books` | 分页查询（参数：`name`、`pageNum`、`pageSize`） |
| GET | `/book/books/{id}` | 按 id 查询 |
| POST | `/book/books` | 新增 |
| PUT | `/book/books` | 修改 |
| DELETE | `/book/books/batch` | 批量删除（body 传 id 数组） |
| DELETE | `/book/books/{id}` | 按 id 删除 |

统一返回结构：`{ "code": "200" | "500", "msg": "...", "data": ... }`

## 接口测试（Apifox）

推荐使用 [Apifox](https://www.apifox.cn/) 进行接口调试。

### 1. 项目与环境准备

1. 新建 Apifox 项目，名称如 `book-management`；
2. 在「项目设置 → 环境管理」新建环境，添加环境变量：
   - `baseUrl = http://localhost:8080`
3. 新增 / 修改 / 批量删除接口需要统一请求头：`Content-Type: application/json`。

> 调试前请先启动后端，并确认数据库连接正常（见[快速开始](#快速开始)）。

### 2. 接口测试用例

**① 分页查询图书列表**

- 方法：`GET`　路径：`{{baseUrl}}/book/books`
- Query 参数（均可选）：

| 参数 | 类型 | 默认 | 说明 |
|---|---|---|---|
| name | string | 空 | 按书名模糊查询，留空查全部 |
| pageNum | int | 1 | 页码 |
| pageSize | int | 5 | 每页条数 |

- 预期返回：`code=200`，`data.records` 为图书数组，`data.total` 为总记录数。

**② 按 id 查询**

- 方法：`GET`　路径：`{{baseUrl}}/book/books/{id}`，如 `{{baseUrl}}/book/books/1`
- 预期返回：`code=200`，`data` 为单本图书对象；id 不存在时返回 `code=500`。

**③ 新增图书**

- 方法：`POST`　路径：`{{baseUrl}}/book/books`
- JSON 请求体示例：

```json
{
  "name": "数据结构",
  "price": 75.5,
  "category": "计算机类",
  "time": "2025-03-15",
  "isbn": "444-44-144",
  "author": "李娜",
  "press": "人民邮电出版社"
}
```

- 预期返回：`code=200`，数据库新增一条 id 自增的记录。

**④ 修改图书**

- 方法：`PUT`　路径：`{{baseUrl}}/book/books`
- JSON 请求体示例（`id` 必须存在，其余字段按需填写）：

```json
{
  "id": 1,
  "price": 98
}
```

- 预期返回：`code=200`；id 不存在时返回 `code=500`。

**⑤ 批量删除**

- 方法：`DELETE`　路径：`{{baseUrl}}/book/books/batch`
- JSON 请求体：待删除的 id 数组

```json
[1, 2, 3]
```

- 预期返回：`code=200`；空数组时返回 `code=400, msg=请选择要删除的数据`。

**⑥ 按 id 删除**

- 方法：`DELETE`　路径：`{{baseUrl}}/book/books/{id}`
- 预期返回：`code=200`；id 不存在时返回 `code=500`。

### 3. 返回结构说明

| 字段 | 说明 |
|---|---|
| code | 业务状态码：`200` 成功、`400` 参数错误、`500` 系统异常 |
| msg | 提示信息 |
| data | 数据（数组 / 对象 / 分页信息） |

## 常见问题

- **后端启动报数据库连接失败**：确认 MySQL 已启动、端口正确、`DB_PASSWORD` 与本地数据库一致，并已执行 `db/init/init.sql`。
- **前端接口不通 / 404**：确认后端运行在 8080 端口，且前端 `src/utils/request.js` 中 `baseURL` 为 `http://localhost:8080`。
- **前端跨域报错**：后端已配置全局 CORS，正常情况下不会出现；请通过 `npm run dev` 启动，不要直接双击打开页面文件。
- **端口占用**：8080（后端）/ 5173（前端）被占用时，分别修改 `application.yml` 和 `vite.config.js`。
