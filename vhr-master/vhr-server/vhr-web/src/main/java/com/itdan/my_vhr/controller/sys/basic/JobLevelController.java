package com.itdan.my_vhr.controller.sys.basic;

import com.itdan.my_vhr.model.JobLevel;
import com.itdan.my_vhr.model.RespBean;
import com.itdan.my_vhr.service.JobLevelService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value = "职称管理")
@RestController
@RequestMapping("/sys/basic/joblevel")
public class JobLevelController {

    @Autowired
    private JobLevelService jobLevelService;

    @ApiOperation(value = "获取所有职称操作",notes = "职称管理")
    @GetMapping("/")
    public  List<JobLevel>  getAllJobLevel(){
       return jobLevelService.getAllJobLevel();
    }

    @ApiOperation(value = "添加职称操作",notes = "职称管理")
    @ApiImplicitParam(name = "record",value = "职称实体类",required = true)
    @PostMapping("/")
    public RespBean addPosition(@RequestBody JobLevel record){
        Integer row= jobLevelService.addJobLevel(record);
        if(row>0){
            return RespBean.ok("添加成功");
        }
        return RespBean.error("添加失败");
    }


    @ApiOperation(value = "更新职称操作",notes = "职称管理")
    @ApiImplicitParam(name = "record",value = "职称实体类",required = true)
    @PutMapping ("/")
    public RespBean updatePosition(@RequestBody JobLevel record){
        Integer row= jobLevelService.updateJobLevel(record);
        if(row>0){
            return RespBean.ok("更新成功");
        }
        return RespBean.error("更新失败");
    }

    @ApiOperation(value = "根据ID删除职称操作",notes = "职称管理")
    @ApiImplicitParam(name = "id",value = "职称id",required = true)
    @DeleteMapping("/{id}")
    public RespBean deletePosition(@PathVariable Integer id){
        Integer row= jobLevelService.deleteJobLevelById(id);
        if(row>0){
            return RespBean.ok("删除成功");
        }
        return RespBean.error("删除失败");
    }

    @ApiOperation(value = "批量删除职称操作",notes = "职称管理")
    @ApiImplicitParam(name = "ids",value = "职称ID数组",required = true)
    @DeleteMapping("/")
    public RespBean deletePosition(Integer[]ids){
        Integer row= jobLevelService.deleteJobLevelByIds(ids);
        if(row>0 && row==ids.length){
            return RespBean.ok("删除成功");
        }
        return RespBean.error("删除失败");
    }


}
