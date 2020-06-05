<template>
    <div>
        <div >
            <div style="display: flex; justify-content: space-between">
                <div>
                    <el-input placeholder="请输入员工姓名进行查询"
                              clearable
                              v-model="keyword"
                              class="inputSty"
                              @clear="initEmps"
                              :disabled="searchDisabled"
                              prefix-icon="el-icon-search"
                              @keydown.enter.native="searchEmp"></el-input>
                    <el-button type="primary" icon="el-icon-search"  :disabled="searchDisabled" @click="searchEmp">搜索</el-button>
                    <el-button type="primary" icon="el-icon-arrow-down" @click="searchDisabled=!searchDisabled">高级搜索</el-button>
                </div>
                <!-- 导入和导出数据-->
                <div>
                    <el-upload class="upload-demo"
                               action="/employee/basic/import"
                               :before-upload="beforeUpload"
                               :on-success="onSuccess"
                               :on-change="onError"
                               :show-file-list="false"
                               :disabled="importDisabled">
                        <el-button type="success"   :disabled="importDisabled" :icon="importIcon">{{importTitle}}</el-button>
                    </el-upload>
                    <el-button type="success" icon="el-icon-sort-up" @click="exportEmp">导出数据</el-button>
                    <el-button type="primary" icon="el-icon-s-custom" @click="showAddPos">添加用户</el-button>
                </div>
            </div>
         <!-- 高级搜索-->
            <transition name="slide-fade">
            <div class="search_style" v-show="searchDisabled">
                <el-row>
                    <el-col :span="5">
                            <el-tag>政治面貌:</el-tag>
                            <el-select v-model="searchValue.politicId" placeholder="政治面貌" size="small"
                                       style="width: 130px;">
                                <el-option
                                        v-for="item in politicsstatus"
                                        :key="item.id"
                                        :label="item.name"
                                        :value="item.id">
                                </el-option>
                            </el-select>
                    </el-col>

                    <el-col :span="4">
                        <el-tag>民族:</el-tag>
                        <el-select v-model="searchValue.nationId" placeholder="民族" size="small" style="width: 130px;">
                            <el-option
                                    v-for="item in nations"
                                    :key="item.id"
                                    :label="item.name"
                                    :value="item.id">
                            </el-option>
                        </el-select>
                    </el-col>

                    <el-col :span="4">
                        <el-tag>职位:</el-tag>
                        <el-select v-model="searchValue.posId" placeholder="职位" size="small" style="width: 130px;">
                            <el-option
                                    v-for="item in positions"
                                    :key="item.id"
                                    :label="item.name"
                                    :value="item.id">
                            </el-option>
                        </el-select>
                    </el-col>

                    <el-col :span="4">
                        <el-tag>职称:</el-tag>
                        <el-select v-model="searchValue.jobLevelId" placeholder="职称" size="small" style="width: 130px;">
                            <el-option
                                    v-for="item in jobLevels"
                                    :key="item.id"
                                    :label="item.name"
                                    :value="item.id">
                            </el-option>
                        </el-select>
                    </el-col>

                    <el-col :span="7">
                        <el-tag>合同形式:</el-tag>
                        <el-radio-group v-model="searchValue.engageForm">
                            <el-radio style="margin-left: 10px" label="劳动合同">劳动合同</el-radio>
                            <el-radio style="margin-left: 10px" label="劳务合同">劳务合同</el-radio>
                        </el-radio-group>
                    </el-col>

                </el-row>

                <el-row style="margin-top:5px">

                    <el-col :span="5">
                        <el-tag>所属部门:</el-tag>
                        <el-popover
                                placement="bottom"
                                title="请选择部门"
                                width="200"
                                trigger="manual"
                                v-model="depSearchVisible">
                            <el-tree default-expand-all :data="depData" :props="defaultProps"
                                     @node-click="searchHandleNodeClick"></el-tree>
                            <div slot="reference"
                                 style="width: 130px;display: inline-flex;font-size: 13px;border: 1px solid #dedede;height: 26px;border-radius: 5px;cursor: pointer;align-items: center;padding-left: 8px;box-sizing: border-box;margin-left: 3px"
                                 @click="showSearchDepView">{{depName}}
                            </div>
                        </el-popover>
                    </el-col>

                    <el-col :span="10">
                        <el-tag>入职日期:</el-tag>
                        <el-date-picker
                                v-model="searchValue.beginDateScope"
                                type="daterange"
                                value-format="yyyy-MM-dd"
                                size="small"
                                unlink-panels
                                range-separator="至"
                                start-placeholder="开始日期"
                                end-placeholder="结束日期">
                        </el-date-picker>
                    </el-col>

                    <el-col :span="5" :offset="4">
                         <el-button  type="primary" @click="searchDisabled=!searchDisabled" size="small">取消</el-button>
                         <el-button type="primary" icon="le-icon-search" size="small" @click="initEmps('advanced')">搜索</el-button>
                    </el-col>

                </el-row>

            </div>
            </transition>
        </div>
        <!--员工表格-->
        <div style="margin-top: 10px">
            <el-table
                    v-loading="loading"
                    element-loading-text="正在载入..."
                    element-loading-spinner="el-icon-loading"
                    element-loading-background="rgba(0, 0, 0, 0.8)"
                    :data="emps"
                    border
                    stripe
                    style="width: 100%">
                <el-table-column
                        type="selection"
                        width="40">
                </el-table-column>
                <el-table-column
                        prop="name"
                        label="姓名"
                        fixed
                        align="left"
                        width="100">
                </el-table-column>
                <el-table-column
                        prop="gender"
                        label="性别"
                        align="left"
                        width="100">
                </el-table-column>
                <el-table-column
                        prop="workID"
                        label="工号"
                        align="left"
                        width="100">
                </el-table-column>
                <el-table-column
                        prop="birthday"
                        width="100"
                        align="left"
                        label="出生日期">
                </el-table-column>
                <el-table-column
                        prop="idCard"
                        width="180"
                        align="left"
                        label="身份证号">
                </el-table-column>
                <el-table-column
                        prop="wedlock"
                        width="50"
                        align="left"
                        label="婚姻状况">
                </el-table-column>
                <el-table-column
                        prop="nation.name"
                        width="50"
                        align="left"
                        label="民族">
                </el-table-column>
                <el-table-column
                        prop="nativePlace"
                        width="50"
                        align="left"
                        label="籍贯">
                </el-table-column>
                <el-table-column
                        prop="politicsstatus.name"
                        width="80"
                        align="left"
                        label="政治面貌">
                </el-table-column>
                <el-table-column
                        prop="email"
                        width="180"
                        align="left"
                        label="邮箱">
                </el-table-column>
                <el-table-column
                        prop="phone"
                        width="150"
                        align="left"
                        label="电话号码">
                </el-table-column>
                <el-table-column
                        prop="address"
                        width="150"
                        align="left"
                        label="联系地址">
                </el-table-column>
                <el-table-column
                        prop="department.name"
                        width="120"
                        align="left"
                        label="所属部门">
                </el-table-column>
                <el-table-column
                        prop="position.name"
                        width="120"
                        align="left"
                        label="职位">
                </el-table-column>
                <el-table-column
                        prop="jobLevel.name"
                        width="120"
                        align="left"
                        label="职称">
                </el-table-column>
                <el-table-column
                        prop="engageForm"
                        width="80"
                        align="left"
                        label="聘用形式">
                </el-table-column>
                <el-table-column
                        prop="beginDate"
                        width="100"
                        align="left"
                        label="入职日期">
                </el-table-column>
                <el-table-column
                        prop="conversionTime"
                        width="100"
                        align="left"
                        label="转正日期">
                </el-table-column>
                <el-table-column
                        prop="beginContract"
                        width="110"
                        align="left"
                        label="合同起始日期">
                </el-table-column>
                <el-table-column
                        prop="endContract"
                        width="110"
                        align="left"
                        label="合同终止日期">
                </el-table-column>
                <el-table-column
                        width="100"
                        align="left"
                        label="合同期限">
                    <template slot-scope="scope">
                        <el-tag>{{scope.row.contractTerm}}</el-tag>
                        年
                    </template>
                </el-table-column>
                <el-table-column
                        prop="specialty"
                        width="120"
                        align="left"
                        label="专业">
                </el-table-column>
                <el-table-column
                        prop="school"
                        width="120"
                        align="left"
                        label="毕业院校">
                </el-table-column>
                <el-table-column
                        prop="tiptopDegree"
                        width="90"
                        align="left"
                        label="最高学历">
                </el-table-column>
                <el-table-column
                        fixed="right"
                        width="240"
                        label="操作">
                    <template slot-scope="scope">
                    <el-button class="buttonSize" type="info" size="small" @click="showEditEmp(scope.$index, scope.row)">编辑</el-button>
                    <el-button class="buttonSize" type="warning" size="small">查看高级资料</el-button>
                    <el-button class="buttonSize" type="danger" size="small" @click="removeEmp(scope.$index, scope.row)">删除</el-button>
                    </template>
                </el-table-column>
            </el-table>
            <div style="display: flex;justify-content: center">
                <!-- 分页 -->

                <el-pagination
                        background
                        @size-change="handleSizeChange"
                        @current-change="handleCurrentChange"
                        :current-page="page"
                        layout="total, sizes, prev, pager, next, jumper, ->,total  "
                        :total="total">
                </el-pagination>
            </div>
        </div>
        <!--  添加员工弹框 -->
        <el-dialog
                :title="diaTitle"
                :visible.sync="dialogVisible"
                width="80%">
            <!--     Layout 布局,通过基础的 24 分栏，迅速简便地创建布局。  -->
            <diV>
                添加员工
                <el-form
                        :rules="AddEmpRules"
                        :model="emp"
                        ref="empFrom"
                >
                    <el-row>
                        <el-col :span="6">
                            <el-form-item label="姓名" prop="name">
                                <el-input style="width: 150px;" prefix-icon="el-icon-edit" v-model="emp.name"
                                          placeholder="请输入员工姓名"></el-input>
                            </el-form-item>
                        </el-col>

                        <el-col :span="6">
                            <el-form-item label="性别" prop="gender">
                                <el-radio-group v-model="emp.gender">
                                    <el-radio label="男">男</el-radio>
                                    <el-radio style="margin-left: 10px" label="女">女</el-radio>
                                    <el-radio style="margin-left: 10px" label="双性">双性</el-radio>
                                </el-radio-group>
                            </el-form-item>
                        </el-col>

                        <el-col :span="6">
                            <el-form-item label="出生日期" prop="birthday">
                                <el-date-picker
                                        v-model="emp.birthday"
                                        size="small"
                                        type="date"
                                        placeholder="请选择出生日期"
                                        style="width: 150px;"
                                        value-format="yyyy-MM-dd">
                                </el-date-picker>
                            </el-form-item>
                        </el-col>

                        <el-col :span="6">
                            <el-form-item label="政治面貌" prop="politicId">
                                <el-select v-model="emp.politicId" placeholder="政治面貌" size="small"
                                           style="width: 180px;">
                                    <el-option
                                            v-for="item in politicsstatus"
                                            :key="item.id"
                                            :label="item.name"
                                            :value="item.id">
                                    </el-option>
                                </el-select>
                            </el-form-item>
                        </el-col>

                    </el-row>

                    <el-row>
                        <el-col :span="6">
                            <el-form-item label="民族" prop="nationId">
                                <el-select v-model="emp.nationId" placeholder="民族" size="small" style="width: 150px;">
                                    <el-option
                                            v-for="item in nations"
                                            :key="item.id"
                                            :label="item.name"
                                            :value="item.id">
                                    </el-option>
                                </el-select>
                            </el-form-item>
                        </el-col>

                        <el-col :span="6">
                            <el-form-item label="籍贯" prop="nativePlace">
                                <el-input style="width: 150px;" prefix-icon="el-icon-edit" v-model="emp.nativePlace"
                                          placeholder="请输入员工籍贯"></el-input>
                            </el-form-item>
                        </el-col>

                        <el-col :span="6">
                            <el-form-item label="电子邮箱" prop="email">
                                <el-input style="width: 180px;" prefix-icon="el-icon-message" v-model="emp.email"
                                          placeholder="请输入电子邮箱"></el-input>
                            </el-form-item>
                        </el-col>

                        <el-col :span="6">
                            <el-form-item label="联系地址" prop="address">
                                <el-input style="width: 180px;" prefix-icon="el-icon-house" v-model="emp.address"
                                          placeholder="请输入联系地址"></el-input>
                            </el-form-item>
                        </el-col>
                    </el-row>

                    <el-row>

                        <el-col :span="6">
                            <el-form-item label="职位" prop="posId">
                                <el-select v-model="emp.posId" placeholder="职位" size="small" style="width: 150px;">
                                    <el-option
                                            v-for="item in positions"
                                            :key="item.id"
                                            :label="item.name"
                                            :value="item.id">
                                    </el-option>
                                </el-select>
                            </el-form-item>
                        </el-col>

                        <el-col :span="6">
                            <el-form-item label="职称" prop="jobLevelId">
                                <el-select v-model="emp.jobLevelId" placeholder="职称" size="small" style="width: 150px;">
                                    <el-option
                                            v-for="item in jobLevels"
                                            :key="item.id"
                                            :label="item.name"
                                            :value="item.id">
                                    </el-option>
                                </el-select>
                            </el-form-item>
                        </el-col>

                        <el-col :span="6">
                            <el-form-item label="所属部门" prop="departmentId">
                                <el-popover
                                        placement="right"
                                        title="亲选择部门"
                                        width="200"
                                        trigger="manual"
                                        v-model="depVisible">
                                    <el-tree :data="depData"
                                             :props="defaultProps"
                                             default-expand-all
                                             @node-click="handleNodeClick"></el-tree>
                                    <div class="depBox" slot="reference" @click="showDepView">{{depName}}</div>
                                </el-popover>
                            </el-form-item>
                        </el-col>

                        <el-col :span="6">
                            <el-form-item label="手机号码" prop="phone">
                                <el-input style="width: 150px;" prefix-icon="el-icon-mobile-phone" v-model="emp.phone"
                                          placeholder="请输入手机号码"></el-input>
                            </el-form-item>
                        </el-col>
                    </el-row>

                    <el-row>
                        <el-col :span="6">
                            <el-form-item label="工号" prop="workID" >
                                <el-input style="width: 150px;" prefix-icon="el-icon-mobile-phone" v-model="emp.workID"
                                          disabled></el-input>
                            </el-form-item>
                        </el-col>

                        <el-col :span="6">
                            <el-form-item label="学历" prop="tiptopDegree">
                                <el-select v-model="emp.tiptopDegree" placeholder="请选择学历" size="small"
                                           style="width: 150px;">
                                    <el-option
                                            v-for="item in tiptopDegrees"
                                            :key="item"
                                            :label="item"
                                            :value="item">
                                    </el-option>
                                </el-select>
                            </el-form-item>
                        </el-col>

                        <el-col :span="6">
                            <el-form-item label="毕业院校" prop="school">
                                <el-input style="width: 150px;" prefix-icon="el-icon-school" v-model="emp.school"
                                          placeholder="请输入毕业院校"></el-input>
                            </el-form-item>
                        </el-col>

                        <el-col :span="6">
                            <el-form-item label="专业名称" prop="specialty">
                                <el-input style="width: 180px;" prefix-icon="el-icon-edit" v-model="emp.specialty"
                                          placeholder="请输入专业名称"></el-input>
                            </el-form-item>
                        </el-col>
                    </el-row>

                    <el-row>
                        <el-col :span="6">
                            <el-form-item label="入职日期" prop="beginDate">
                                <el-date-picker
                                        v-model="emp.beginDate"
                                        size="small"
                                        type="date"
                                        placeholder="入职日期"
                                        style="width: 150px;"
                                        value-format="yyyy-MM-dd">
                                </el-date-picker>
                            </el-form-item>
                        </el-col>

                        <el-col :span="6">
                            <el-form-item label="转正日期" prop="conversionTime">
                                <el-date-picker
                                        v-model="emp.conversionTime"
                                        size="small"
                                        type="date"
                                        placeholder="转正日期"
                                        style="width: 150px;"
                                        value-format="yyyy-MM-dd">
                                </el-date-picker>
                            </el-form-item>
                        </el-col>

                        <el-col :span="6">
                            <el-form-item label="合同起始日期" prop="beginContract">
                                <el-date-picker
                                        v-model="emp.beginContract"
                                        size="small"
                                        type="date"
                                        placeholder="合同起始日期"
                                        style="width: 150px;"
                                        value-format="yyyy-MM-dd">
                                </el-date-picker>
                            </el-form-item>
                        </el-col>

                        <el-col :span="6">
                            <el-form-item label="合同终至日期" prop="endContract">
                                <el-date-picker
                                        v-model="emp.endContract"
                                        size="small"
                                        type="date"
                                        placeholder="合同终至日期"
                                        style="width: 150px;"
                                        value-format="yyyy-MM-dd">
                                </el-date-picker>
                            </el-form-item>
                        </el-col>

                    </el-row>

                    <el-row>
                        <el-col :span="8">
                            <el-form-item label="身份证号码" prop="idCard">
                                <el-input style="width: 150px;" prefix-icon="el-icon-user" v-model="emp.idCard"
                                          placeholder="身份证号码"></el-input>
                            </el-form-item>
                        </el-col>

                        <el-col :span="8">
                            <el-form-item label="合同形式" prop="engageForm">
                                <el-radio-group v-model="emp.engageForm">
                                    <el-radio style="margin-left: 10px" label="劳动合同">劳动合同</el-radio>
                                    <el-radio style="margin-left: 10px" label="劳务合同">劳务合同</el-radio>
                                </el-radio-group>
                            </el-form-item>
                        </el-col>


                        <el-col :span="8">
                            <el-form-item label="婚姻状态" prop="wedlock">
                                <el-radio-group v-model="emp.wedlock">
                                    <el-radio style="margin-left: 10px" label="已婚">已婚</el-radio>
                                    <el-radio style="margin-left: 10px" label="未婚">未婚</el-radio>
                                    <el-radio style="margin-left: 10px" label="离异">离异</el-radio>
                                </el-radio-group>
                            </el-form-item>
                        </el-col>
                    </el-row>

                </el-form>
            </diV>
            <span slot="footer" class="dialog-footer">
    <el-button @click="dialogVisible = false">取 消</el-button>
    <el-button type="primary" @click="addEmp">确 定</el-button>
  </span>
        </el-dialog>

    </div>
</template>

<script>
    export default {
        name: "EmpBasic",
        data() {
            return {
                emps: [],
                loading: false,
                total: 0,
                page: 1,
                size: 10,
                keyword: '',
                depName:'',
                diaTitle:'',
                dialogVisible: false,
                depVisible:false,
                depSearchVisible:false,
                searchDisabled:false,
                nations: [],
                jobLevels: [],
                politicsstatus: [],
                positions:[],
                depData:[],
                importTitle:"导入数据",
                importIcon:"el-icon-sort-down",
                importDisabled:false,
                searchValue: {
                    politicId: null,
                    nationId: null,
                    jobLevelId: null,
                    posId: null,
                    engageForm: null,
                    departmentId: null,
                    beginDateScope: null
                },
                tiptopDegrees: ['本科', '大专', '硕士', '博士', '高中', '初中', '小学', '其他'],
                defaultProps: {
                    children: 'children',
                    label: 'name'
                },
                emp: {
                    name: "测试员工 ",
                    gender: "男",
                    birthday: "1989-12-31",
                    idCard: "610122199001011256",
                    wedlock: "已婚",
                    nationId: 1,
                    nativePlace: "陕西",
                    politicId: 13,
                    email: "laowang@qq.com",
                    phone: "18565558897",
                    address: "深圳市南山区",
                    departmentId: null,
                    jobLevelId: 9,
                    posId: 29,
                    engageForm: "劳务合同",
                    tiptopDegree: "本科",
                    specialty: "信息管理与信息系统",
                    school: "深圳大学",
                    beginDate: "2017-12-31",
                    workState: "在职",
                    workID: "00000057",
                    contractTerm: 2,
                    conversionTime: "2018-03-31",
                    notworkDate: null,
                    beginContract: "2017-12-31",
                    endContract: "2019-12-31",
                    workAge: null
                },

                AddEmpRules:{
                    name: [{required: true, message: '请输入员工姓名', trigger: 'blur'}],
                    gender: [{required: true, message: '请选择性别', trigger: 'blur'}],
                    birthday: [{required: true, message: '请选择出生日期', trigger: 'blur'}],
                    idCard: [{required: true, message: '请输入身份证号码', trigger: 'blur'}, {
                        pattern: /(^[1-9]\d{5}(18|19|([23]\d))\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\d{3}[0-9Xx]$)|(^[1-9]\d{5}\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\d{2}$)/,
                        message: '身份证号码格式不正确',
                        trigger: 'blur'
                    }],
                    wedlock: [{required: true, message: '请选择婚姻状况', trigger: 'blur'}],
                    nationId: [{required: true, message: '请选择民族', trigger: 'blur'}],
                    nativePlace: [{required: true, message: '请输入籍贯', trigger: 'blur'}],
                    politicId: [{required: true, message: '请选择政治面貌', trigger: 'blur'}],
                    email: [{required: true, message: '请输入邮箱地址', trigger: 'blur'}, {
                        type: 'email',
                        message: '邮箱格式不正确',
                        trigger: 'blur'
                    }],
                    phone: [{required: true, message: '请输入手机号码', trigger: 'blur'},{
                        pattern:/^1[34578]\d{9}$/,
                        message:'手机号码格式不正确',
                        trigger: 'blur'
                    }],
                    address: [{required: true, message: '请输入员工地址', trigger: 'blur'}],
                    departmentId: [{required: true, message: '请选择部门名称', trigger: 'blur'}],
                    jobLevelId: [{required: true, message: '请选择职称', trigger: 'blur'}],
                    posId: [{required: true, message: '请选择职位', trigger: 'blur'}],
                    engageForm: [{required: true, message: '请输入合同形式', trigger: 'blur'}],
                    tiptopDegree: [{required: true, message: '请选择学历', trigger: 'blur'}],
                    specialty: [{required: true, message: '请输入专业', trigger: 'blur'}],
                    school: [{required: true, message: '请输入毕业院校', trigger: 'blur'}],
                    beginDate: [{required: true, message: '请选择入职日期', trigger: 'blur'}],
                    workState: [{required: true, message: '请输入工作状态', trigger: 'blur'}],
                    workID: [{required: true, message: '请输入工号', trigger: 'blur'}],
                    contractTerm: [{required: true, message: '请输入合同期限', trigger: 'blur'}],
                    conversionTime: [{required: true, message: '请选择转正日期', trigger: 'blur'}],
                    notworkDate: [{required: true, message: '请选择离职日期', trigger: 'blur'}],
                    beginContract: [{required: true, message: '请选择合同起始日期', trigger: 'blur'}],
                    endContract: [{required: true, message: '请选择合同结束日期', trigger: 'blur'}],
                    workAge: [{required: true, message: '请输入工龄', trigger: 'blur'}],
                }

            }
        },
        mounted() {
                this.initEmps();
                this.initDatas();
        }
        ,
        methods: {
            //滞空设置
            emptyEmp() {
                this.emp = {
                    name: "",
                    gender: "",
                    birthday: "",
                    idCard: "",
                    wedlock: "",
                    nationId: 1,
                    nativePlace: "",
                    politicId: 13,
                    email: "",
                    phone: "",
                    address: "",
                    departmentId: null,
                    jobLevelId: 9,
                    posId: 29,
                    engageForm: "",
                    tiptopDegree: "",
                    specialty: "",
                    school: "",
                    beginDate: "",
                    workID: "",
                    contractTerm: 2,
                    conversionTime: "",
                    notworkDate: null,
                    beginContract: "",
                    endContract: "",
                    workAge: null
                }
                this.depName = '';
            },
            //初始化数据
            initEmps(type) {
                this.loading = true;
                let url="/employee/basic/?page="+this.page + "&size=" + this.size ;
                if(type && type=="advanced"){
                    if (this.searchValue.politicId) {
                        url += '&politicId=' + this.searchValue.politicId;
                    }
                    if (this.searchValue.nationId) {
                        url += '&nationId=' + this.searchValue.nationId;
                    }
                    if (this.searchValue.jobLevelId) {
                        url += '&jobLevelId=' + this.searchValue.jobLevelId;
                    }
                    if (this.searchValue.posId) {
                        url += '&posId=' + this.searchValue.posId;
                    }
                    if (this.searchValue.engageForm) {
                        url += '&engageForm=' + this.searchValue.engageForm;
                    }
                    if (this.searchValue.departmentId) {
                        url += '&departmentId=' + this.searchValue.departmentId;
                    }
                    if (this.searchValue.beginDateScope) {
                        url += '&beginDateScope=' + this.searchValue.beginDateScope;
                    }
                }else {
                    url+="&name=" + this.keyword;
                }

                this.getRequest(url).then(resp => {
                    this.loading = false;
                    if (resp) {
                        this.emps = resp.data;
                        this.total = resp.total;
                    }
                })
            },
            searchHandleNodeClick(data){
                  // alert(data.name);
                   this.depName=data.name;
                   this.searchValue.departmentId=data.id;
                   this.depSearchVisible = !this.depSearchVisible;
            },
            handleCurrentChange(val) {
                // console.log(`当前页: ${val}`);
                this.page = val;
                this.initEmps();
            },
            handleSizeChange(val) {
                //  console.log(`每页 ${val} 条`);
                this.size = val;
                this.initEmps();
            },
            searchEmp() {
                if (this.keyword) {
                    this.initEmps();
                } else {
                    this.$message({
                        type: 'info',
                        message: '搜索信息不能为空'
                    });
                }
            },
            showAddPos() {
                this.emptyEmp();
                this.diaTitle="添加员工资料";
                this.dialogVisible = true;
                this.getMaxWordId();

            },
            initDatas(){
                //先从session中获取，如果不存在再去数据库中获取
                //
                // if(!window.sessionStorage.getItem("nations")){
                //     this.initNations();
                // }else {
                //     this.nations=JSON.parse(window.sessionStorage.getItem("nations"));
                // }
                //
                // if(!window.sessionStorage.getItem("jobLevels")){
                //     this.initJobLevels();
                // }else {
                //     this.nations=JSON.parse(window.sessionStorage.getItem("jobLevels"));
                // }
                //
                //
                // if(!window.sessionStorage.getItem("politicsstatus")){
                //     this.initPoliticsstatus();
                // }else {
                //  this.politicsstatus= JSON.parse(window.sessionStorage.getItem("politicsstatus"));
                // }
                //
                //
                //     if(!window.sessionStorage.getItem("depData")){
                //         this.initDeps();
                // }else{
                //         this.depData=JSON.parse(window.sessionStorage.getItem("depData"));
                // }
                //初始化选择框参数
                this.initDeps();
                this.initPoliticsstatus();
                this.initJobLevels();
                this.initNations();
                this.initPositions();

            }
            ,
            //获取所有民族列表
            initNations() {
                this.getRequest("/employee/basic/nation").then(resp => {
                    if (resp) {
                        this.nations = resp;
                        window.sessionStorage.setItem("nations",JSON.stringify(resp));
                    }
                })
            },
            //获取所有职位等级列表
            initJobLevels() {
                this.getRequest("/employee/basic/jobLevel").then(resp => {
                    if (resp) {
                        this.jobLevels = resp;
                        window.sessionStorage.setItem("jobLevels",JSON.stringify());
                    }
                })
            },
            //获取所有政治面貌列表
            initPoliticsstatus() {
                this.getRequest("/employee/basic/politics").then(resp => {
                    if (resp) {
                        this.politicsstatus = resp;
                        window.sessionStorage.setItem("politicsstatus",JSON.stringify());
                    }
                })


            },
            //获取所有部门操作
            initDeps(){
                this.getRequest("/sys/basic/department/").then(resp=>{
                    if(resp){
                        this.depData=resp;
                        window.sessionStorage.setItem("depData",JSON.stringify(resp));
                    }
                })
            }
            ,
            //初始化学历参数
            //初始化职位（职位发生变化比其他选项频繁，所以不设置在session中）
            initPositions(){
                    this.getRequest("/employee/basic/position").then(resp=>{
                        if(resp){
                            this.positions=resp;
                        }
                    })
            },

            //获取最大工号值
            getMaxWordId(){
                this.getRequest("/employee/basic/maxWordId").then(resp=>{
                    if(resp){
                        this.emp.workID=resp.obj;
                    }
                })
            },
            //点击树形节点事件
            handleNodeClick(data){
                this.emp.departmentId=data.id;
                this.depName=data.name;
                this.depVisible = !this.depVisible;
            },
            showDepView(){
                this.depVisible = true;
            },
            showSearchDepView(){
                //alert("search");
                this.depSearchVisible = true;
            },
            //添加员工
            addEmp(){
                if(this.emp.id){
                      this.editEmp();
                 }else {
                    //先检验表单
                    this.$refs.empFrom.validate(vali => {
                        if (vali) {
                            this.postRequest("/employee/basic/", this.emp).then(resp => {
                                if (resp) {
                                    this.initEmps();
                                    this.dialogVisible = false;
                                }
                            })
                        }
                    })
                }
            },
            //删除员工
            removeEmp(index,row){
                this.$confirm('此操作将永久删除该员工信息, 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    this.deleteRequest("/employee/basic/" + row.id).then(resp => {
                        //刷新表格
                        if (resp) {
                            this.initEmps();
                        }
                    })
                }).catch(() => {
                    this.$message({
                        type: 'info',
                        message: '已取消删除'
                    });
                });
            },
              showEditEmp(index,row){
                  //先获取该行员工数据
                  //打开添加员工编辑器
                  //保存员工信息
                  this.diaTitle="修改员工资料";
                  this.depName = row.department.name;
                  this.dialogVisible = true;
                  //初始化选择框参数
                  this.emp=row;
                  this.initPositions();
              },
            //编辑员工资料
            editEmp(index,row){
                this.putRequest("/employee/basic/",this.emp).then(resp=>{
                    if(resp){
                        this.dialogVisible = false;
                        this.initEmps();
                    }
                })
            },
            //导出数据
            exportEmp(){
                   window.open("/employee/basic/export","_parent") ;
            },
            //导入数据
            beforeUpload(){
                this.importTitle="正在导入数据";
                this.importIcon="el-icon-loading";
                this.importDisabled=true;
            },
            //导入成功
            onSuccess(response, file, fileList){
                this.importTitle="导入数据";
                this.importIcon="el-icon-sort-down";
                this.importDisabled=false;
                this.initEmps();
            },
            //导入失败
            onError(err, file, fileList){
                this.importTitle="导入数据";
                this.importIcon="el-icon-sort-down";
                this.importDisabled=false;
            },

        }
    }
</script>

<style>
    .inputSty {
        width: 300px;
        margin-right: 10px;
    }

    .buttonSize {
        padding: 3px;
    }
    .depBox{
        width: 150px;
        height: 28px;
        display: inline-flex;
        font-size: 13px;
        border: 1px solid #dedede;
        border-radius: 5px;
        cursor: pointer;
        align-items: center;
        padding:0px 8px ;
        box-sizing: border-box;
    }
    .upload-demo{
        display: inline-flex;
        margin-right:10px;
    }
    .search_style{
        border: 1px solid #8e52ff;
        border-radius: 5px;
        box-sizing: border-box;
        padding: 5px ;
        margin: 10px;
    }
    /* 可以设置不同的进入和离开动画 */
    /* 设置持续时间和动画函数 */
    .slide-fade-enter-active {
        transition: all .3s ease;
    }
    .slide-fade-leave-active {
        transition: all .8s cubic-bezier(1.0, 0.5, 0.8, 1.0);
    }
    .slide-fade-enter, .slide-fade-leave-to
        /* .slide-fade-leave-active for below version 2.1.8 */ {
        transform: translateX(10px);
        opacity: 0;
    }


</style>