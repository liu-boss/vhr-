<template>
    <div>
        <div>
            <el-input
                    size="small"
                    placeholder="添加职位..."
                    prefix-icon="el-icon-plus"
                    @keydown.enter.native="addPos"
                    v-model="pos.name"
                    style="width: 300px;margin-right: 10px;margin-top:10px;margin-bottom: 10px"
            >
            </el-input>
            <el-button type="primary" size="small" icon="el-icon-plus" @click="addPos">添加</el-button>
        </div>
        <!-- 表格-->
        <div>
            <el-table
                    :data="positions"
                    stripe
                    border
                    style="width: 80%"
                    @selection-change="handleSelectionChange"
                    :row-class-name="tableRowClassName">
                <el-table-column
                    type="selection"
                    width="55">
            </el-table-column>
                <el-table-column
                        prop="id"
                        label="编号"
                        width="100">
                </el-table-column>
                <el-table-column
                        prop="name"
                        label="职位名称"
                        width="150">
                </el-table-column>
                <el-table-column
                        prop="createDate"
                        label="入职时间"
                        width="150">
                </el-table-column>
                <el-table-column
                        prop="enabled"
                        label="是否启用"
                        width="200">
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
                                @click="showEdit(scope.$index, scope.row)">编辑
                        </el-button>
                        <el-button
                                size="mini"
                                type="danger"
                                @click="handleDelete(scope.$index, scope.row)">删除
                        </el-button>
                    </template>
                </el-table-column>
            </el-table>
            <el-button type="danger" @click="deleteMany" size="small" style="margin-top:10px" :disabled="multipleSelection.length==0">批量删除</el-button>
        </div>
        <el-dialog
                title="职位修改"
                :visible.sync="dialogVisible"
                width="30%"
        >
            <div>
                <el-tag>职位名称</el-tag>
                <el-input size="small" style="width: 200px;margin-left: 10px" v-model="updatePos.name"></el-input>
            </div>
            <div>
                <el-tag>是否启用</el-tag>
                <el-switch
                        v-model="updatePos.enabled"
                        style="margin-left: 10px"
                        active-text="启用"
                        inactive-text="不启用">
                </el-switch>
            </div>
            <span slot="footer" class="dialog-footer">
    <el-button size="small" @click="dialogVisible=false">取 消</el-button>
    <el-button size="small" type="primary" @click="doUpdate">确 定</el-button>
  </span>
        </el-dialog>
    </div>
</template>

<script>
    export default {
        name: "PosMana",
        mounted() {
            //由于调用初始化职位参数
            this.initPositions();
        },
        methods: {
            tableRowClassName({row, rowIndex}) {
                if ((rowIndex%2) === 0) {
                    return 'warning-row';
                } else if ((rowIndex%2) === 1) {
                    return 'success-row';
                }
                return '';
            },
            initPositions() {
                this.getRequest("/sys/basic/pos/").then(resp => {
                    //将请求到的参数赋予positions
                    this.positions = resp;
                })
            },
            showEdit(index, row) {
                //将修改职位信息的对话框显示出来
               this.dialogVisible = true;
                //console.log(index, row);
                //this.updatePos.name = row;
                Object.assign(this.updatePos,row);
            },
            doUpdate() {
                this.putRequest("/sys/basic/pos/", this.updatePos).then(resp => {
                    if (resp) {
                        this.initPositions();
                        this.updatePos.name = '';
                        this.dialogVisible = false;
                    }
                })
            },
            handleDelete(index, row) {
                //console.log(index, row);
                this.$confirm('此操作将永久删除该职位, 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    this.deleteRequest("/sys/basic/pos/" + row.id).then(resp => {
                        //刷新表格
                        if (resp) {
                            this.initPositions();
                        }
                    })
                }).catch(() => {
                    this.$message({
                        type: 'info',
                        message: '已取消删除'
                    });
                });
            },
            addPos() {
                //判断pos中shifou
                if (this.pos.name) {
                    //保存数据到数据库中
                    this.postRequest("/sys/basic/pos/", this.pos).then(resp => {
                        if (resp) {
                            //刷新表格
                            this.initPositions();
                            //清空添加栏
                            this.pos.name = '';
                        }

                    })
                } else {

                    this.$message({
                        type: 'error',
                        message: '职位名称不能为空'
                    });
                }
            },
            handleSelectionChange(val) {
                this.multipleSelection = val;
            },
            deleteMany(){
                //批量删除
                this.$confirm('此操作将永久删除选中的职位, 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    let ids='?';
                    this.multipleSelection.forEach(item=>{
                        ids+='ids='+item.id+'&';
                    });
                    this.deleteRequest("/sys/basic/pos/"+ids).then(resp => {
                        //刷新表格
                        if (resp) {
                            this.initPositions();
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
        data() {
            return {
                multipleSelection: [],
                pos: {
                    name: ''
                },
                updatePos: {
                    name: '',
                    enabled:false
                },
                positions: [],
                dialogVisible: false//默认对话框是关闭的
            }
        }
    }
</script>

<style>
    .el-table .warning-row {
        background: oldlace;
    }

    .el-table .success-row {
        background: #f0f9eb;
    }
</style>
