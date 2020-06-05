package com.itdan.my_vhr.controller.sys.basic;


import com.itdan.my_vhr.model.Department;
import com.itdan.my_vhr.model.RespBean;
import com.itdan.my_vhr.service.DepartmentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Api(value = "部门管理")
@RestController
@RequestMapping("/sys/basic/department")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @ApiOperation(value = "获取所有部门信息", notes = "部门管理")
    @GetMapping("/")
    public List<Department> getAllDepartments() {
        return departmentService.getAllDepartmentsByPid();
    }


    @ApiOperation(value = "添加部门", notes = "部门管理")
    @ApiImplicitParam(name = "record", value = "部门实体类", required = true)
    @PostMapping("/")
    public RespBean addPosition(@RequestBody Department record) {
        departmentService.addDep(record);
        if (record.getResult() == 1) {
            return RespBean.ok("添加成功", record);
        }
        return RespBean.error("添加失败");
    }

    @ApiOperation(value = "删除部门", notes = "部门管理")
    @ApiImplicitParam(name = "id", value = "被删除部门id", required = true)
    @DeleteMapping("/{id}")
    public RespBean deletePosition(@PathVariable Integer id) {
        Department record = new Department();
        record.setId(id);
        departmentService.deleteDepById(record);
        System.out.println(record.getResult());
        if (record.getResult() == -2) {
            return RespBean.error("该部门存在子部门，删除失败");
        } else if (record.getResult() == -1) {
            return RespBean.error("该部门存在员工，删除失败");
        } else if (record.getResult() == 1) {
            return RespBean.ok("删除成功");
        }
        return RespBean.error("删除失败");
    }
}
