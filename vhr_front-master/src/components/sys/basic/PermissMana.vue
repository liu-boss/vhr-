<template>
    <div>
    <div class="perInput">
        <el-input placeholder="请输入角色英文名" v-model="role.name"  style="width:300px" >
            <template slot="prepend">ROLE_</template>
        </el-input>
        <el-input placeholder="请输入角色中文名" v-model="role.nameZh" style="width:300px;margin-left:10px"></el-input>
        <el-button size="small" type="primary" @click="addRole" style="margin-left:10px">添加角色</el-button>
    </div>
<!--  折叠面板-->
    <div>
        <el-collapse  v-model="activeNames" accordion style="width: 70%;margin-top:10px" @change="change">
            <el-collapse-item :title="r.nameZh" :name="r.id" v-for="(r,index) in roles" :key="index">
                <el-card class="box-card">
                    <div slot="header" class="clearfix">
                        <span>可访问资源</span>
                        <el-button style="float: right; padding: 3px 0"  icon="el-icon-delete" type="text" @click="deleteRole(r.id)"></el-button>
                    </div>
                    <div >
                        <el-tree show-checkbox
                                 ref="tree"
                                 :key="index"
                                 node-key="id"
                                 :default-checked-keys="selectMenus"
                                 :data="allmenus"
                                 :props="defaultProps" ></el-tree>
                          <div style="display:flex;justify-content: flex-end">
                              <el-button size="small" @click="closeUpdate" >取消修改</el-button>
                              <el-button size="small" type="primary" @click="doUpdateSelect(r.id,index)">确认修改</el-button>
                          </div>
                    </div>
                </el-card>
            </el-collapse-item>
        </el-collapse>
    </div>
    </div>
</template>

<script>
    export default {
        name: "PermissMana",
        data(){
            return{
                activeNames:-1,
                role:{
                    name:'',
                    nameZh:''
                },
                roles:[],
                allmenus:[],
                selectMenus:[],
                defaultProps: {
                    children: 'children',
                    label: 'name'
                }
            }
        },
        mounted() {
            this.initRoles()
        }
        ,
        methods:{
            addRole(){
                //检验数据不为空
                if(this.role.name && this.role.nameZh){
                    this.postRequest('/sys/basic/permiss/',this.role).then(resp=>{
                        if(resp){
                            //清空输入框中的数据
                            this.role.name='';
                            this.role.nameZh='';
                            //刷新列表
                            this.initRoles();
                        }
                    })
                }else {

                    this.$message({
                        type: 'error',
                        message: '输入参数不能为空'
                    });
                }
            },
            initRoles(){
                this.getRequest("/sys/basic/permiss/").then(resp=>{
                    if(resp){
                        this.roles=resp;
                    }
                })
            },
            //折叠面板的点击效果
            change(rid){
                //alert(name);
                //name有值，代表着折叠框被打开，此时去初始化，折叠框的值
                 if(rid){
                     //调用选中的选项
                     this.initSelectMenus(rid);
                     this.initAllMenus();
                 }
                },
              initAllMenus(){
                  this.getRequest("/sys/basic/permiss/menus").then(resp=>{
                      if(resp){
                          //将值赋予树形结构
                          this.allmenus=resp;
                      }
                  })
              },
            //初始化权限菜单栏选中选项
            initSelectMenus(rid){
                 this.getRequest("/sys/basic/permiss/mids/"+rid).then(resp=>{
                     this.selectMenus=resp;
                 })
            },
            closeUpdate(){
                this.activeNames=-1;
            },
            //更新权限操作
            doUpdateSelect(rid,index){
               let treeNode=this.$refs.tree[index];
               //获取被选中的节点
               let selectNode=treeNode.getCheckedKeys(true);
               //更新时，为了操作方便，我们想将原先的选中的权限数据清空，在将现选中的数据插入到数据中
               //拼接请求字符串
                let url="/sys/basic/permiss/?rid="+rid;
                //遍历mid
                selectNode.forEach(key=>{
                    url+='&mids='+key;
                })
                this.putRequest(url).then(resp=>{
                    this.initSelectMenus(rid);
                })
            },
            //删除角色
            deleteRole(rid){
                this.$confirm('此操作将永久删除该权限, 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    this.deleteRequest("/sys/basic/permiss/" + rid).then(resp => {
                        //刷新数据
                        if (resp) {
                            this.initRoles();
                        }
                    })
                }).catch(() => {
                    this.$message({
                        type: 'info',
                        message: '已取消删除'
                    });
                });
            }

        }
    }
</script>

<style >

</style>