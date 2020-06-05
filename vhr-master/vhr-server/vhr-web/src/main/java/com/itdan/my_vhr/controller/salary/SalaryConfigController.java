package com.itdan.my_vhr.controller.salary;

import com.itdan.my_vhr.model.RespBean;
import com.itdan.my_vhr.model.RespPageBean;
import com.itdan.my_vhr.service.EmployeeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@Api(value = "员工账套管理")
@RestController
@RequestMapping("/salary/sobcfg")
public class SalaryConfigController {


    @Autowired
    private EmployeeService employeeService;

    @ApiOperation(value = "获取所有员工账套(分页显示)",notes = "员工账套管理")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page",value = "页码"),
            @ApiImplicitParam(name = "size", value = "每页显示条数")
    })
    @GetMapping("/")
    public RespPageBean getEmployeeWithSalary(@RequestParam(defaultValue = "1") Integer page,
                                          @RequestParam(defaultValue = "10") Integer size
                                        ){
        return  employeeService.getEmployeeWithSalary(page,size);
    }


    @ApiOperation(value = "更新员工账套",notes = "员工账套管理")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "eid",value = "员工实体类ID"),
            @ApiImplicitParam(name = "sid", value = "薪水套账实体类ID")
    })
    @PutMapping("/")
    public RespBean updateSalaryCfg(Integer eid,Integer sid){
       Integer row=  employeeService.updateSalaryCfg(eid,sid);
      if(row>0){
          return RespBean.ok("更新成功");
      }
      return RespBean.error("更新失败");
    }


}
