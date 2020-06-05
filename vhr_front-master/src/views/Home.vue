<template>
    <div>
        <el-container>
            <el-header class="homeHeader">
                <div class="homeHeaderTitle">人事管理系统</div>

                <div>
                    <el-button icon="el-icon-chat-dot-round" type="text" class="el-button"  @click="goChat" title="聊天室"></el-button>
                    <el-dropdown class="userInfo" @command="commandHandler">
  <span class="el-dropdown-link">
    {{user.username}}<i><img src="https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png" alt=""></i>
  </span>
                       <el-dropdown-menu slot="dropdown">
                           <el-dropdown-item command="userinfo">个人中心</el-dropdown-item>
                           <el-dropdown-item command="usersetting">设置</el-dropdown-item>
                           <el-dropdown-item command="logout" divided>注销</el-dropdown-item>
                       </el-dropdown-menu>
                   </el-dropdown>
               </div>
            </el-header>
            <el-container>
                <el-aside width="200px">
                    <el-menu router unique-opened>
                        <!-- this.$router.options.routes 获取到index.js中的routes数组-->
                        <el-submenu :index="index+''" v-for="(item,index) in routes" v-if="!item.hidden" :key="index">
                            <template slot="title">
                                <i style="color: #7893ff; margin-right: 5px " :class="item.iconCls"></i>
                                <span>{{item.name}}</span>
                            </template>
                            <el-menu-item :index="child.path" v-for="(child,indexj) in item.children" :key="indexj">
                                {{child.name}}
                            </el-menu-item>
                        </el-submenu>
                    </el-menu>

                </el-aside>
                <el-main>
                    <el-breadcrumb separator-class="el-icon-arrow-right" v-if="this.$route.path!='/home'">
                        <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
                        <el-breadcrumb-item>{{this.$router.currentRoute.name}}</el-breadcrumb-item>
                    </el-breadcrumb>
                    <div class="homeFont" v-else>
                        欢迎来到人事管理系统！
                    </div>
                    <router-view class="homeRouterView"/>
                </el-main>
            </el-container>
        </el-container>
    </div>

</template>

<script>
    import router from "../router";

    export default {
        name: "Home",
        data() {
            return {
                user: JSON.parse(window.sessionStorage.getItem('user'))
            }
        },
        computed: {
            routes() {
                return this.$store.state.routes;
            }
        }
        ,
        methods: {
            // menuClick:function(index){
            //    //点击标题时，跳转至相应的界面
            //     this.$router.push(index);
            //     //console.log(index);
            //   //console.log(indexPath);
            //               },

            commandHandler: function (mod) {
                if (mod == 'logout') {
                    this.$confirm('此操作将退出系统, 是否继续?', '提示', {
                        confirmButtonText: '确定',
                        cancelButtonText: '取消',
                        type: 'warning'
                    }).then(() => {
                        this.getRequest('/logout');
                        //清空缓存数据
                        window.sessionStorage.removeItem('user');
                        //清空vuex里面的数据
                        this.$store.commit('initRoutes', []);
                        //跳转至登入界面
                        this.$router.replace('/');
                    }).catch(() => {
                        this.$message({
                            type: 'info',
                            message: '已取消删除'
                        });
                    });
                } else if (mod == 'usersetting') {

                } else if (mod == 'userinfo') {

                }
            },
            goChat(){
                this.$router.push("/chat");
            }
        }
    }
</script>

<style>
    .homeRouterView {
        margin-top: 10px
    }

    .homeFont {
        text-align: center;
        font-size: 50px;
        font-family: 方正粗黑宋简体;
        color: #7893ff;
        margin-top: 100px;
    }

    .homeHeader {
        background-color: cornflowerblue;
        display: flex;
        /*剧中*/
        align-items: center;
        justify-content: space-between;
        padding: 0px 15px;
        box-sizing: border-box;
    }

    .homeHeaderTitle {
        font-size: 25px;
        font-family: 楷体;
        color: whitesmoke;

    }

   userInfo {
        cursor: pointer;
    }

    .el-dropdown-link {
        display: flex;
        align-items: center;
    }

    .el-dropdown-link img {
        height: 48px;
        width: 48px;
        border-radius: 20px;
        margin-left: 10px;
    }
    .el-button{
        margin-right: 8px;

    }

    .el-button .el-icon-chat-dot-round{
        font-size: 30px;
        color: #000000;
    }

</style>