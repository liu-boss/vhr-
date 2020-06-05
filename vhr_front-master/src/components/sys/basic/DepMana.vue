<template>
    <div style="width: 65%">
        <el-input
                placeholder="请输入部门名称"
                prefix-icon="el-icon-search"
                v-model="filterText"
       >
        </el-input>

        <el-tree
                :data="depData"
                :props="defaultProps"
                :filter-node-method="filterNode"
                :expand-on-click-node=false
                ref="tree">
            <span class="custom-tree-node"  slot-scope="{ node, data }">
        <span>{{ node.label }}</span>
        <span>
          <el-button
                  type="primary"
                  class="depBtn"
                  size="mini"
                  @click="() => showAddNode(data)">
            添加部门
          </el-button>
          <el-button
                  type="danger"
                  class="depBtn"
                  size="mini"
                  @click="() => removeNode(data)">
            删除部门
          </el-button>
        </span>
      </span>
        </el-tree>
        <el-dialog
                title="添加部门"
                :visible.sync="dialogVisible"
                width="30%">
            <table>
                <div>
                    <el-tag>上级部门</el-tag>
                    <span>{{pname}}</span>
                </div>

                <div>
                 <el-tag>部门名称</el-tag>
                 <el-input v-model="dep.name" placeholder="请输入部门名称" style="width: 250px"></el-input>
                </div>

            </table>
            <span slot="footer" class="dialog-footer">
    <el-button @click="dialogVisible = false">取 消</el-button>
    <el-button type="primary" @click="addDep">确 定</el-button>
  </span>
        </el-dialog>

    </div>
</template>

<script>
    export default {
        name: "DepMana",
        watch: {
            filterText(val) {
                this.$refs.tree.filter(val);
            }
        },
        data(){
            return{
                dialogVisible:false,
                dep:{
                     name:'',
                    parentId:-1
                },
                pname:'',
                filterText:'',
                depData:[],
                defaultProps: {
                    children: 'children',
                    label: 'name'
                }
            }
        },
        mounted() {
            this.initDepData()
        }
        ,
        methods:{
            filterNode(value, data) {
                if (!value) return true;
                return data.name.indexOf(value) !== -1;
            },
            //初始化数据
            initDepData(){

                    this.getRequest("/sys/basic/department/").then(resp=>{
                        if(resp){
                            this.depData=resp;
                            window.sessionStorage.setItem("depData",JSON.stringify(resp));
                        }
                    })

            },
            showAddNode(data){
                this.pname=data.name;
                this.dep.parentId=data.id;
              //显示添加栏
                this.dialogVisible=true;
            },
            removeNode(data){
                 if(data.parent){
                     this.$message({
                         type: 'info',
                         message: '该节点为父类节点，删除失败'
                     });
                 }else {
                     this.$confirm('此操作将永久删除该部门, 是否继续?', '提示', {
                         confirmButtonText: '确定',
                         cancelButtonText: '取消',
                         type: 'warning'
                     }).then(() => {
                         this.deleteRequest("/sys/basic/department/" + data.id).then(resp => {
                             //刷新表格
                             if (resp) {
                                 this.removeDep(null,this.depData,data.id);
                             }
                         })
                     }).catch(() => {
                         this.$message({
                             type: 'info',
                             message: '已取消删除'
                         });
                     });
                 }
            },
            removeDep(parent,depData,id){
                for (let i=0;i<depData.length;i++){
                    let d=depData[i];
                    if(d.id==id){
                       depData.splice(i,1);
                       if(depData.length==0){
                           parent.parent=false;
                       }
                       return;
                    }else {
                        this.removeDep(d,d.children,id);
                    }
                }
            },
            addDep2Depdata(depData,dep){
                 for (let i=0;i<depData.length;i++){
                     let d=depData[i];
                     if(d.id==dep.parentId){
                          //说明新增的节点是d节点下的子节点，将其添加到children数组中
                         d.children=d.children.concat(dep);
                         d.parent=true;
                     }else {
                         //如果不是在遍历其子节点
                         this.addDep2Depdata(d.children,dep);
                     }
                 }
            },
            addDep(){
                 this.postRequest("/sys/basic/department/",this.dep).then(resp=>{
                     if(resp){
                          //刷新树形结构
                        //this.initDepData();
                         this.addDep2Depdata(this.depData,resp.obj);
                         //关闭添加对话框
                         this.dialogVisible=false;
                         //清理对话框数据
                         this.clearDep();
                     }
                 })
            },
            clearDep(){
                this.dep={
                    name:'',
                    parentId:-1
                };
                    this.pname=''
            }


           }
    }
</script>

<style >
    .custom-tree-node {
        font-size: 12px;
        width: 100%;
        display: flex;
        justify-content: space-between
    }
    .depBtn{
        padding: 2px;
    }

</style>