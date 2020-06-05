package com.itdan.my_vhr.controller.emp;


import com.itdan.my_vhr.model.*;
import com.itdan.my_vhr.service.*;
import com.itdan.my_vhr.utils.POIUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@Api(value = "员工管理")
@RestController
@RequestMapping("/employee/basic")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private NationService nationService;

    @Autowired
    private PoliticsstatusService politicsstatusService;

    @Autowired
    private JobLevelService jobLevelService;

    @Autowired
    private PositionService positionService;

    @Autowired
    private DepartmentService departmentService;


    @ApiOperation(value = "获取所有员工(分页显示)",notes = "员工管理")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page",value = "页码"),
            @ApiImplicitParam(name = "size", value = "每页显示条数"),
            @ApiImplicitParam(name = "employee", value = "搜索封装对象"),
    })
    @GetMapping("/")
    public RespPageBean getEmployeeByPage(@RequestParam(defaultValue = "1") Integer page,
                                          @RequestParam(defaultValue = "10") Integer size,
                                          Employee employee,
                                          Date [] beginDateSize){
         return  employeeService.getEmployeeByPage(page,size,employee,beginDateSize);
    }

    @ApiOperation(value = "添加员工",notes = "员工管理")
    @ApiImplicitParam(name = "record",value = "员工实体类",required = true)
    @PostMapping("/")
    public RespBean addEmployee(@RequestBody Employee record){
         Integer row= employeeService.addEmployee(record);
        if(row>0){
             return RespBean.ok("添加成功");
         }
        return RespBean.error("添加失败");
    }

    @ApiOperation(value = "更新员工信息",notes = "员工管理")
    @ApiImplicitParam(name = "record",value = "员工实体类",required = true)
    @PutMapping("/")
    public RespBean updateEmployee(@RequestBody Employee record){
        Integer row= employeeService.updateEmployee(record);
        if(row==1){
            return RespBean.ok("更新成功");
        }
        return RespBean.error("更新失败");
    }

    @ApiOperation(value = "删除员工信息",notes = "员工管理")
    @ApiImplicitParam(name = "id",value = "员工ID",required = true)
    @DeleteMapping("/{id}")
    public RespBean deleteEmployee(@PathVariable Integer id){
        Integer row= employeeService.deleteEmployee(id);
        if(row==1){
            return RespBean.ok("删除成功");
        }
        return RespBean.error("删除失败");
    }


    @ApiOperation(value = "获取所有民族信息",notes = "员工管理")
    @GetMapping("/nation")
    public  List<Nation> getAllNations(){
        return nationService.getAllNations();
    }

    @ApiOperation(value = "获取所有政治面貌",notes = "员工管理")
    @GetMapping("/politics")
    public  List<Politicsstatus> getAllPoliticsstatus(){
        return politicsstatusService.getAllPoliticsstatus();
    }

    @ApiOperation(value = "获取所有员工级别",notes = "员工管理")
    @GetMapping("/jobLevel")
    public  List<JobLevel> getAllJobLevel(){
        return jobLevelService.getAllJobLevel();
    }

    @ApiOperation(value = "获取所有职位信息",notes = "员工管理")
    @GetMapping("/position")
    public  List<Position> getAllPositions(){
        return positionService.getAllPositions();
    }

    @ApiOperation(value = "获取员工最大工号",notes = "员工管理")
    @GetMapping("/maxWordId")
    public  RespBean getmaxWordId(){
       Integer wordId=employeeService.getmaxWordId();
       if(wordId!=null){
           return RespBean.build().setStatus(200)
                   .setObj(String.format("%08d",wordId));
       }
       return RespBean.error("查询失败");
    }

    @ApiOperation(value = "将员工数据导出Excle表格",notes = "员工管理")
    @GetMapping("/export")
    public ResponseEntity<byte[]>exportEmp(){
        //先获取员工数据
        List<Employee> employees=(List<Employee>) employeeService.getEmployeeByPage(null,null,null,null).getData();
        //将数据转换成Excle表格
        return POIUtil.exportEmp2Excle(employees);
    }

    @ApiOperation(value = "将Excle表格导入数据库",notes = "员工管理")
    @PostMapping("/import")
    public RespBean importEmp(MultipartFile file){
          List<Employee> employees= POIUtil.importExcle2Emp(file,
                   nationService.getAllNations(),
                   politicsstatusService.getAllPoliticsstatus(),
                   departmentService.getAllDepartments(),
                   positionService.getAllPositions(),
                   jobLevelService.getAllJobLevel());
          //将数据插入数据库中
        if(employeeService.addEmps(employees)==employees.size()){
            return RespBean.ok("导入成功");
        }
        return RespBean.error("导入失败");
    }


}
