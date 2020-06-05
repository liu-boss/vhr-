# 项目介绍
该项目为企业的人事管理项目，本项目为本人前后端分离的练习项目，项目采用的SpringBoot+Vue进行开发。
* 项目浏览地址：http://123.57.128.124/index.html
* 项目后端代码地址：https://github.com/18376108492/vhr
* 项目前端代码地址：https://github.com/18376108492/vhr_front
# 项目技术栈
### 后端技术栈
* SpringBoot
* MyBatis
* MySQL
* Redis（项目中用于菜单栏的缓存，缓解数据库压力）
* RabbitMQ(项目中用于实现添加员工信息时，自动给添加的员工发送信息)
* SpringSecurity(Spring的安全框架，主要用于请求处理和权限拦截)
### 前端技术栈
* Vue
* ElementUI
* axios
* vue-router
* vue-cli3
# 功能介绍
### 1. 权限管理
* 权限数据库主要运通了五张表：用户表，角色表，用户角色表，访问资源表，资源角色表。五张表的关系模型图如下：
 ![](https://github.com/18376108492/vhr_front/blob/master/src/assets/Snipaste_2020-01-27_20-59-44.png)
* hr用户表主要用于存放登入用户的基本信息。
* role角色表主要用于存放角色权限，按照SpringSecurity的规则，name为角色的英文名，将以ROLE_开始。
* hr_role用户角色表，根据名字可以看出这是的用户与对应角色的中间表，用户可以拥有一个或者多个角色权限。
* 这意味着不同的用户登入后由于角色不同的缘故，左侧出线的动态界面不同的。
### 2. 员工信息的增删改查操作和员工的高级查询
### 3. 用户的权限的增删改操作
### 4. Excle导出导入员工信息
### 5. 自动发送邮件功能，等其他功能
# 项目部署
### 1. clone 项目到本地 https://github.com/18376108492/vhr
### 2.运行MySQL数据库脚本，将数据信息加载到本地数据库
### 3. 提前准备好redis,修改application.properties中的redis配置
### 4. 准备好RabbitMQ，修改application.properties中的RabbitMQ配置
### 5. 在idea中，将下载好的项目打开的，启动mail-server模块，再启动vhr-server中的vhr-web模块
### 6. 项目启动成功后，在浏览器中访问：https://localhost:9091/index.html
# 参考
* [vhr](https://github.com/lenve/vhr)
* [vue-chat](https://github.com/microzz/vue-chat)
