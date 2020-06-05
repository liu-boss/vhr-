package com.itdan.my_vhr.controller.sys.basic;

import com.itdan.my_vhr.model.Position;
import com.itdan.my_vhr.model.RespBean;
import com.itdan.my_vhr.service.PositionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Api(value = "职位管理")
@RestController
@RequestMapping("/sys/basic/pos")
public class PositionController {

    @Autowired
    private PositionService positionService;

    @ApiOperation(value = "获取所有职位操作",notes = "职位管理")
    @GetMapping("/")
    public List<Position> getAllPositions(){
        List<Position> positions= positionService.getAllPositions();
        return  positions;
    }

    @ApiOperation(value = "添加职位操作",notes = "职位管理")
    @ApiImplicitParam(name = "record",value = "职位实体类",required = true)
    @PostMapping("/")
   public RespBean addPosition(@RequestBody Position record){
       Integer row= positionService.addPosition(record);
       if(row>0){
           return RespBean.ok("添加成功");
       }
       return RespBean.error("添加失败");
   }

    @ApiOperation(value = "更新职位操作",notes = "职位管理")
    @ApiImplicitParam(name = "record",value = "职位实体类",required = true)
    @PutMapping ("/")
    public RespBean updatePosition(@RequestBody Position record){
        Integer row= positionService.updatePosition(record);
        if(row>0){
            return RespBean.ok("更新成功");
        }
        return RespBean.error("更新失败");
    }

    @ApiOperation(value = "根据ID删除职位操作",notes = "职位管理")
    @ApiImplicitParam(name = "id",value = "职位id",required = true)
    @DeleteMapping("/{id}")
    public RespBean deletePosition(@PathVariable Integer id){
        Integer row= positionService.deletePosition(id);
        if(row>0){
            return RespBean.ok("删除成功");
        }
        return RespBean.error("删除失败");
    }

    @ApiOperation(value = "批量删除职位操作",notes = "职位管理")
    @ApiImplicitParam(name = "ids",value = "职位ID数组",required = true)
    @DeleteMapping("/")
    public RespBean deletePosition(Integer[]ids){
        Integer row= positionService.deletePositionByIds(ids);
        if(row>0 && row==ids.length){
            return RespBean.ok("删除成功");
        }
        return RespBean.error("删除失败");
    }


}
