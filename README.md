# 家庭物品管理系统

基于 SpringBoot + MyBatis + MySQL 开发的家庭物品台账管理系统，用于管理家庭日用品、药品、零食等物品，实现物品登记、分类维护、过期预警、库存统计等功能。前端采用单文件 HTML + 原生 JS，整体粉色主题，开箱即用。

## 技术栈

| 类别 | 技术 |
|------|------|
| 后端框架 | Spring Boot 3.2.5 |
| 持久层 | MyBatis |
| 数据库 | MySQL 8.0 |
| 构建工具 | Maven |
| JDK | Java 17 |
| 前端 | HTML + CSS + JavaScript（单文件，零依赖） |

## 功能模块

### 核心功能

- **物品分类管理**：分类的新增、修改、删除，支持分类名称唯一性校验
- **物品台账 CRUD**：物品的新增、修改、删除、批量删除，字段包含名称、分类、存放位置、库存数量、保质期、低值阈值
- **过期时间预警**：自动识别物品状态 —— 正常 / 临期（7 天内）/ 已过期
- **分页多条件查询**：支持按物品名称、存放位置、分类进行模糊筛选，分页展示
- **库存分类统计**：SQL 聚合统计各分类物品数量，首页柱状图展示
- **参数校验 & 全局异常**：`@Valid` 参数校验 + 统一异常处理，统一接口响应格式

### 增强功能

- **低值库存预警**：库存数量 ≤ 阈值时自动标记，统计卡片实时展示
- **采购清单**：自动汇总已过期 + 库存不足的物品，标注原因
- **操作日志**：记录物品的新增 / 修改 / 删除操作，可追溯
- **导出 Excel**：一键导出物品台账为 CSV（Excel 可直接打开，中文不乱码）
- **热门物品 TOP5**：基于使用频率统计，首页展示排行
- **数据看板**：首页统计卡片 + 分类分布柱状图 + 热门物品排行

## 项目结构

```
family-goods/
├── pom.xml                              # Maven 依赖配置
├── sql/
│   ├── init.sql                         # 数据库初始化脚本（建库建表+初始数据）
│   └── upgrade_v2.sql                   # v2 升级脚本（低值库存、操作日志等）
├── src/main/java/com/example/familygoods/
│   ├── FamilyGoodsApplication.java      # 启动类
│   ├── config/
│   │   └── CorsConfig.java              # 跨域配置
│   ├── controller/                      # 控制层
│   │   ├── CategoryController.java      # 分类接口
│   │   ├── ItemController.java          # 物品接口
│   │   └── OperationLogController.java  # 操作日志接口
│   ├── dto/                             # 数据传输对象（含参数校验）
│   ├── entity/                          # 实体类
│   ├── exception/                       # 全局异常处理 + 自定义业务异常
│   ├── mapper/                          # MyBatis Mapper 接口
│   ├── service/                         # 业务接口
│   │   └── impl/                        # 业务实现
│   └── util/                            # 工具类（统一返回体、分页对象）
└── src/main/resources/
    ├── application.yml                  # 应用配置（端口、数据源）
    ├── mapper/                          # MyBatis XML 映射文件
    └── static/
        └── index.html                   # 前端页面（粉色主题，单文件）
```

## 环境要求

- JDK 17+
- Maven 3.6+
- MySQL 8.0+

## 快速开始

### 1. 数据库初始化

```sql
-- 执行 sql/init.sql 创建数据库、表结构及初始数据
-- 再执行 sql/upgrade_v2.sql 升级（低值库存、操作日志等字段）
```

### 2. 修改配置

编辑 `src/main/resources/application.yml`，修改数据库连接信息：

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/family_goods?useUnicode=true&characterEncoding=utf8&serverTimezone=Asia/Shanghai
    username: root
    password: 你的密码
```

### 3. 启动项目

```bash
mvn spring-boot:run
```

或打包后运行：

```bash
mvn clean package
java -jar target/family-goods-1.0.0.jar
```

### 4. 访问

- 前端页面：http://localhost:8080/
- 接口文档见下方

## 接口说明

### 分类管理

| 接口 | 方法 | 说明 |
|------|------|------|
| `/category/list` | GET | 查询全部分类 |
| `/category/add` | POST | 新增分类 |
| `/category/update` | POST | 修改分类 |
| `/category/delete` | POST | 删除分类 |

### 物品管理

| 接口 | 方法 | 说明 |
|------|------|------|
| `/item/add` | POST | 新增物品 |
| `/item/update` | POST | 修改物品 |
| `/item/delete` | POST | 删除物品 |
| `/item/batchDelete` | POST | 批量删除 |
| `/item/pageList` | GET | 分页查询（支持名称/位置/分类筛选） |
| `/item/warnList` | GET | 过期预警列表（已过期 + 7 天内临期） |
| `/item/lowStockList` | GET | 低值库存列表 |
| `/item/purchaseList` | GET | 采购清单（已过期 + 库存不足） |
| `/item/useFrequencyTop` | GET | 热门物品 TOP5 |
| `/item/getStatistics` | GET | 首页统计（总数/过期/分类分组） |
| `/item/export` | GET | 导出 CSV |

### 操作日志

| 接口 | 方法 | 说明 |
|------|------|------|
| `/log/pageList` | GET | 操作日志分页查询 |

### 统一响应格式

```json
{
  "code": 200,
  "message": "操作成功",
  "data": {}
}
```

- `code`: 状态码（200 成功，400 参数错误，500 业务异常）
- `message`: 提示信息
- `data`: 返回数据

## 数据库表

| 表名 | 说明 |
|------|------|
| `goods_category` | 物品分类表 |
| `goods_item` | 物品台账表 |
| `operation_log` | 操作日志表 |

## 项目亮点

- 采用标准 MVC 三层架构，职责清晰
- 统一接口响应格式，全局异常集中捕获
- MyBatis 动态 SQL 实现多条件查询
- SQL 聚合统计实现分类分组与数据看板
- 前端单文件部署，零构建依赖，粉色主题美观
