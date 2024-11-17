<h1 align="center">JinYu后台管理系统（后端）</h1>
<p align="center">
  <a target="_blank" href="https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html">
    <img src="https://img.shields.io/badge/JDK-17-green.svg" alt="jdk-17" />
  </a>
  <img src="https://img.shields.io/badge/SpringBoot-v3.2-blue" alt="springboot">
  <img src="https://img.shields.io/github/stars/Xiao-Tree/JinYu-server?style=flat-square" alt="stars" />
  <img src="https://img.shields.io/github/license/Xiao-Tree/JinYu-server?style=flat" alt="license" />
  <img src="https://img.shields.io/github/issues/Xiao-Tree/JinYu-server" alt="issues" />
  <img src="https://img.shields.io/github/forks/Xiao-Tree/JinYu-server?style=flat" alt="forks" />
</p>

## 介绍
一个通用的后台管理系统，基于`SpringBoot`、`SpringSecurity`、`MybatisFlex`、`Redis`等主流技术开发。

## 主要功能
* 基于`SpringSecurity`实现`session`+`JWT`的有状态的认证和授权。
* 用户管理：用户是系统操作者，该功能主要完成系统用户配置。
* 角色管理：角色菜单权限分配、设置角色按机构进行数据范围权限划分。
* 字典管理：对系统中经常使用的一些较为固定的数据进行维护。
* 在线用户：当前系统中活跃用户状态监控。
* 连接池监视：监视当期系统数据库连接池状态，可进行分析SQL找出系统性能瓶颈。
* 基于`WebSocket`进行实时通信

## 目录说明

```
├─ src
│  ├─ main
│  │  ├─ java
│  │  │  └─ com
│  │  │     └─ xiaotree
│  │  │        └─ jinyuserver
│  │  │           ├─ config                       // 全局配置
│  │  │           ├─ controller
│  │  │           │  ├─ CommonController.java     // 公共接口
│  │  │           │  ├─ SystemController.java     // 系统接口
│  │  │           │  └─ UserController.java       // 用户接口
│  │  │           ├─ domain
│  │  │           │  ├─ dto                       // 传输层实体
│  │  │           │  ├─ entity                    // 实体类 与数据库的表保持一致
│  │  │           │  ├─ Message.java              // 统一消息封装
│  │  │           │  ├─ Result.java               // 统一响应体封装
│  │  │           │  └─ vo                        // 视图相关实体
│  │  │           ├─ handler                      // 认证鉴权相关处理类
│  │  │           ├─ mapper                       // 数据操作
│  │  │           ├─ service                      // 业务服务
│  │  │           ├─ util                         // 工具函数
│  │  │           └─ JinYuServerApplication.java
│  │  └─ resources
│  │     ├─ application-dev.yaml                  // 开发配置
│  │     ├─ application-pro.yaml                  // 生产配置
│  │     ├─ application.yaml
│  │     ├─ mapper                                // mapper对应的xml
│  │     └─ mybatis-flex.config
│  └─ test
├─ .gitignore
├─ pom.xml
└─ README.md
```