<template>
    <div>
        <el-tabs v-model="activeName" @tab-click="handleClick">
            <el-tab-pane label="部门管理" name="depMana"><DepMana></DepMana></el-tab-pane>
            <el-tab-pane label="职位管理" name="posMana"><PosMana></PosMana></el-tab-pane>
            <el-tab-pane label="职称管理" name="jobLevel"><JobLevel></JobLevel></el-tab-pane>
            <el-tab-pane label="奖惩规则" name="bcMana"><BcMana></BcMana></el-tab-pane>
            <el-tab-pane label="权限组" name="permissMana"><PermissMana></PermissMana></el-tab-pane>
        </el-tabs>
    </div>
</template>

<script>
    import DepMana from "../../components/sys/basic/DepMana";
    import BcMana from "../../components/sys/basic/BcMana";
    import JobLevel from "../../components/sys/basic/JobLevel";
    import PermissMana from "../../components/sys/basic/PermissMana";
    import PosMana from "../../components/sys/basic/PosMana";

    export default {
        name: "SysBasic",
        data(){
            return{

            }
        },
        components:{
            'DepMana':DepMana,
            'BcMana':BcMana,
            'JobLevel':JobLevel,
            'PermissMana':PermissMana,
            'PosMana':PosMana
        }
    }
</script>

<style scoped>

</style>