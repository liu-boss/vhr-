package com.itdan.my_vhr.test.controller.salary;

import com.itdan.my_vhr.model.RespBean;
import com.itdan.my_vhr.model.Salary;
import com.itdan.my_vhr.service.SalaryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value = "薪水账套管理")
@CrossOrigin
@RestController
@RequestMapping("/salary/sal")
public class SalaryController  {

    @Autowired
    private SalaryService salaryService;


    @ApiOperation(value = "添加薪水账套",notes = "薪水账套管理")
    @ApiImplicitParam(name = "record",value = "薪水实体类",required = true)
    @PostMapping("/")
    public RespBean addSalary(@RequestBody Salary record){
         Integer row=salaryService.addSalary(record);
         if(row==1){
             return RespBean.ok("添加成功");
         }
         return RespBean.error("添加失败");
    }

    @ApiOperation(value = "获取所有薪水账套",notes = "薪水账套管理")
    @GetMapping ("/")
    public List<Salary> getAllSalary(){
         return  salaryService.getAllSalaries();
    }

    @ApiOperation(value = "更新薪水账套",notes = "薪水账套管理")
    @ApiImplicitParam(name = "record",value = "薪水实体类",required = true)
    @PutMapping ("/")
    public RespBean updateSalary(@RequestBody Salary record){
        Integer row=salaryService.updateSalary(record);
        if(row==1){
            return RespBean.ok("更新成功");
        }
        return RespBean.error("更新失败");
    }

    @ApiOperation(value = "删除薪水账套",notes = "薪水账套管理")
    @ApiImplicitParam(name = "id",value = "被薪水实体类的ID",required = true)
    @DeleteMapping("/")
    public RespBean deleteSalary(@PathVariable Integer id){
        Integer row=salaryService.deleteSalary(id);
        if(row==1){
            return RespBean.ok("添加成功");
        }
        return RespBean.error("添加失败");
    }

}
