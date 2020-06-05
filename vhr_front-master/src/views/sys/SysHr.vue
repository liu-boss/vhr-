<template>
    <div>
        <div style="margin-top: 10px;display:flex;justify-content: center">
            <el-input
                    placeholder="请输入hr名称"
                    prefix-icon="el-icon-search"
                    v-model="keyword"
             style="width: 350px;margin-right: 10px"></el-input>
            <el-button size="small"  prefix-icon="el-icon-search" type="primary" @click="sreachHr">搜索</el-button>
        </div>
<!-- hr信息展示卡片列表-->
        <div class="hr_box_style">
            <el-card class="box-card" v-for="(hr,index) in hrs" :key="index">
                <div slot="header" class="clearfix">
                    <span>{{hr.name}}</span>
                    <el-button style="float: right; padding: 3px 0;color: #ff6928" type="text" icon="el-icon-delete" @click="removeHr(hr.id)"></el-button>
                </div>
                <div >
                    <div class="hr_img_container">
                       <img :src="hr.userface" :alt="hr.name" :title="hr.name" class="hr_img">
                    </div>
                    <div class="hr_text">
                        <div>用户名：{{hr.name}}</div>
                        <div>手机号码：{{hr.phone}}</div>
                        <div>电话号：{{hr.telephone}}</div>
                        <div>地址：{{hr.address}}</div>
                        <div>用户状态：<el-switch
                                v-model="hr.enabled"
                                active-color="#13ce66"
                                @change="enabledChange(hr)"
                                inactive-color="#ff4949"
                                active-text="启用"
                                inactive-text="未启用">
                        </el-switch></div>
                        <div>用户角色：<el-tag type="warning" style="margin-right: 2px" v-for="(r,i) in hr.roles" :key="i">{{r.nameZh}}</el-tag>
                            <el-popover
                                    placement="right"
                                    title="角色列表"
                                    width="200"
                                    @show="showRole(hr)"
                                    @hide="hideRole(hr)"
                                    trigger="click"
                                    >
                                <el-select
                                        v-model="selectRoles"
                                        multiple
                                        placeholder="请选择">
                                    <el-option
                                            v-for="(role,j) in rolesData "
                                            :key="j"
                                            :label="role.nameZh"
                                            :value="role.id"
                                    >
                                    </el-option>
                                 </el-select>
                                <el-button slot="reference" icon="el-icon-more"  type="text"></el-button>
                            </el-popover>

                        </div>
                        <div>备注：{{hr.remark}}</div>
                    </div>
                </div>
            </el-card>
        </div>

    </div>
</template>

<script>
    export default {
        name: "SysHr",
        data(){
            return{
                keyword:'',
                hrs:[],
                rolesData:[],
                selectRoles:[]
            }
        },
        mounted() {
           this.initHrs()
            },
        methods:{
            sreachHr(){
                  this.initHrs();
            },
            initHrs(){
                this.getRequest("/sys/hr/?keyword="+this.keyword).then(resp=>{
                    if(resp){
                        this.hrs=resp;
                    }
                })
            },
            removeHr(rid){
                this.$confirm('此操作将永久删除该用户, 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    this.deleteRequest("/sys/hr/" + rid).then(resp => {
                        //刷新数据
                        if (resp) {
                            this.initHrs();
                        }
                    })
                }).catch(() => {
                    this.$message({
                        type: 'info',
                        message: '已取消删除'
                    });
                });
        },
            enabledChange(hr){
                delete hr.roles;
                this.putRequest("/sys/hr/",hr).then(resp=>{
                    if(resp){
                        this.initHrs();
                    }
                })
            },
            initRoles(){
                this.getRequest("/sys/hr/roles").then(resp=>{
                     if(resp){
                         this.rolesData=resp;
                     }
                })
            }
            ,
            showRole(hr){
                this.initRoles();
                //获取已经选中的角色
                let roles=hr.roles;
                this.selectRoles=[];
                roles.forEach(r=>{
                    this.selectRoles.push(r.id);
                })
                //alert(this.selectRoles);
            },
            hideRole(hr){
                let roles = [];
                Object.assign(roles, hr.roles);
                let flag = false;
                if (roles.length != this.selectRoles.length) {
                    flag = true;
                } else {
                    for (let i = 0; i < roles.length; i++) {
                        let role = roles[i];
                        for (let j = 0; j < this.selectRoles.length; j++) {
                            let sr = this.selectRoles[j];
                            if (role.id == sr) {
                                roles.splice(i, 1);
                                i--;
                                break;
                            }
                        }
                    }
                    if (roles.length != 0) {
                        flag = true;
                    }
                }
                if (flag) {
                    let url = '/sys/hr/roles?hrid=' + hr.id;
                    this.selectRoles.forEach(sr => {
                        url += '&rids=' + sr;
                    });
                    this.putRequest(url).then(resp => {
                        if (resp) {
                            this.initHrs();
                        }
                    });
                }
            }
        }
    }
</script>

<style>
    .hr_box_style{
          margin-top: 10px;
          display: flex;
          /*自动排列和平均分配*/
          flex-wrap: wrap;
          justify-content: space-around;
    }
    .box-card{
          width: 350px;
          margin-top: 10px;
    }
    .hr_img_container{
        width: 100%;
        display: flex;
        justify-content: center;
    }
    .hr_img{
     width:70px ;
     height:70px;
 }
    .hr_text{
        margin-top: 10px;
    }
    .hr_text div{
        font-size: 12px;
        color: #8e52ff;
    }

</style>