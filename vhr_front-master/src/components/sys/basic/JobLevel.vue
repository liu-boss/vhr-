<template>
    <div>
        <div>
            <el-input style="width: 300px;margin-right: 10px"
                      size="small"
                      v-model="jodlevle.name"
                      placeholder="添加职称..."
                      prefix-icon="el-icon-plus"></el-input>
            <el-select v-model="jodlevle.titleLevel" placeholder="职称等级" size="small">
                <el-option
                        v-for="item in titleLevels"
                        :value="item">
                </el-option>
            </el-select>
            <el-button icon="el-icon-plus" type="primary" size="small" @click="addJobLevel">添加</el-button>
        </div>
        <!--表格-->
        <div>
            <el-table
                    :data="allJodlevle"
                    border
                    @selection-change="jobLevHandleSelectionChange"
                    style="width: 85%;margin-top: 10px">
                <el-table-column
                        type="selection"
                        width="55">
                </el-table-column>
                <el-table-column
                        prop="id"
                        label="编号"
                        width="150">
                </el-table-column>
                <el-table-column
                        prop="name"
                        label="职称名称"
                        width="150">
                </el-table-column>
                <el-table-column
                        prop="titleLevel"
                        label="职称级别"
                        width="150">
                </el-table-column>
                <el-table-column
                        prop="createDate"
                        label="创建时间"
                        width="150">
                </el-table-column>
                <el-table-column
                        prop="enabled"
                        label="是否启用"
                        width="150">
                    <template slot-scope="scope">
                      <el-tag type="success" v-if="scope.row.enabled" size="small">启用</el-tag>
                      <el-tag type="danger"  size="small" v-else>未启用</el-tag>
                    </template>
                </el-table-column>
                <el-table-column
                        prop="operator"
                        label="操作">
                    <template slot-scope="scope">
                        <el-button
                                size="mini"
                                @click="showJobLevelEdit(scope.$index, scope.row)">编辑
                        </el-button>
                        <el-button
                                size="mini"
                                type="danger"
                                @click="deleteJodLevel(scope.$index, scope.row)">删除
                        </el-button>
                    </template>
                </el-table-column>
            </el-table>
            <el-button type="danger" @click="deleteManyJobLevel" size="small" style="margin-top:10px" :disabled="jobmultipleSelection.length==0">批量删除</el-button>
        </div>
        <el-dialog
                title="职位级别修改"
                :visible.sync="dialogVisible"
                width="25%"
        >
            <div>
                <el-tag>职位名称</el-tag>
                <el-input size="small" style="width: 200px;margin-left: 10px" v-model="updateJoblevel.name"></el-input>
            </div>
            <div>
                <el-tag>职称等级</el-tag>
                <el-select v-model="updateJoblevel.titleLevel" style="width: 200px;margin-left: 10px" placeholder="职称等级" size="small">
                    <el-option
                            v-for="item in titleLevels"
                            :value="item">
                    </el-option>
                </el-select>
            </div>
            <div>
                <el-tag>是否启用</el-tag>
                <el-switch
                        v-model="updateJoblevel.enabled"
                        style="margin-left: 10px"
                        active-text="启用"
                        inactive-text="不启用">
                </el-switch>
            </div>
            <span slot="footer" class="dialog-footer">
    <el-button size="small" @click="dialogVisible=false">取 消</el-button>
    <el-button size="small" type="primary" @click="doUpdateLevel">确 定</el-button>
  </span>
        </el-dialog>

    </div>
</template>

<script>
    export default {
        name: "JobLevel",
        data() {
            return {
                jodlevle: {
                    name: '',
                    titleLevel: ''
                },
                titleLevels: ['正高级', '副正高级', '高级', '中级', '初级', '员级'],
                allJodlevle: [],
                jobmultipleSelection:[],
                dialogVisible:false,
                updateJoblevel:{
                    name: '',
                    titleLevel: '',
                    enabled:false
                }
            }
        },
        mounted() {
            //自动导入数据
            this.initAllJodlevle();
        }
        ,
        methods: {
            //初始化数据
            initAllJodlevle() {
                this.getRequest("/sys/basic/joblevel/").then(resp => {
                    if (resp) {
                        this.allJodlevle = resp;
                    }
                })
            },
            //添加
            addJobLevel() {
                //判断两输入框的值是否为空
                if (this.jodlevle.name && this.jodlevle.titleLevel) {
                    this.postRequest("/sys/basic/joblevel/", this.jodlevle).then(resp => {
                        if (resp) {
                            //刷新表格，清理数据
                            this.initAllJodlevle();
                            this.jodlevle.name = '';
                            this.jodlevle.titleLevel = '';
                        }
                    })

                } else {
                    this.$message({
                        type: "error",
                        message: '职位名称或职位级别不能为空'
                    })
                }
            },
            //展示修改编辑框
            showJobLevelEdit(index, row) {
                //将修改的对话框显示出来
                this.dialogVisible = true;
                //console.log(index, row);
                //this.updatePos.name = row;
                Object.assign(this.updateJoblevel,row);
            },
            //更新数据操作
            doUpdateLevel(){
                this.putRequest("/sys/basic/joblevel/", this.updateJoblevel).then(resp => {
                    if (resp) {
                        this.initAllJodlevle();
                        this.jodlevle.name = '';
                        this.jodlevle.titleLevel = '';
                        this.dialogVisible = false;
                    }
                })
            },
            //删除操作
            deleteJodLevel(index,row){
                this.$confirm('此操作将永久删除该职称级别信息, 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    this.deleteRequest("/sys/basic/joblevel/" + row.id).then(resp => {
                        //刷新表格
                        if (resp) {
                            this.initAllJodlevle();
                        }
                    })
                }).catch(() => {
                    this.$message({
                        type: 'info',
                        message: '已取消删除'
                    });
                });
            },
            jobLevHandleSelectionChange(val) {
                this.jobmultipleSelection = val;
            },
            deleteManyJobLevel() {
                //批量删除
                this.$confirm('此操作将永久删除选中的职称级别信息, 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    let ids = '?';
                    this.jobmultipleSelection.forEach(item => {
                        ids += 'ids=' + item.id + '&';
                    });
                    this.deleteRequest("/sys/basic/joblevel/" + ids).then(resp => {
                        //刷新表格
                        if (resp) {
                            this.initAllJodlevle();
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