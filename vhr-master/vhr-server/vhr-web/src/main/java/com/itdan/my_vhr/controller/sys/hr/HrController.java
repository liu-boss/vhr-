package com.itdan.my_vhr.controller.sys.hr;

import com.itdan.my_vhr.model.Hr;
import com.itdan.my_vhr.model.RespBean;
import com.itdan.my_vhr.model.Role;
import com.itdan.my_vhr.service.HrService;
import com.itdan.my_vhr.service.RoleService;
import com.itdan.my_vhr.utils.HrUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value = "Hr管理")
@RestController
@RequestMapping("/sys/hr")
public class HrController {

    @Autowired
    private HrService hrService;

    @Autowired
    private RoleService roleService;

    @ApiOperation(value ="查询所有Hr状态",notes = "Hr管理")
    @ApiImplicitParam(name = "keyword",value = "关键词")
    @GetMapping("/")
    public List<Hr> getAllHrs(String keyword){
            return   hrService.getAllHrs(keyword,HrUtil.getHr().getId());
    }

    @ApiOperation(value = "更新Hr启用信息",notes = "Hr管理")
    @ApiImplicitParam(name = "hr",value = "hr实体类",required = true)
    @PutMapping("/")
    public RespBean updateEnabled(@RequestBody Hr hr){
        Integer row =hrService.updateEnabled(hr);
        if(row>0){
            return RespBean.ok("更新成功");
        }
        return RespBean.error("更新失败");
    }

    @ApiOperation(value ="查询所有角色信息",notes = "Hr管理")
    @GetMapping("/roles")
    public List<Role> getAllRoles(){
        return  roleService.getAllRoles();
    }

    @ApiOperation(value = "更新Hr角色权限信息",notes = "Hr管理")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "hrid",value = "登入用户的id",required = true),
            @ApiImplicitParam(name = "rids", value = "被选中权限角色的ID数组", required = true)
    })
    @PutMapping("/roles")
    public RespBean updateRoles( Integer hrid,Integer[] rids){
        Integer row =hrService.updateRoles(hrid,rids);
        if(row>0){
            return RespBean.ok("更新成功");
        }
        return RespBean.error("更新失败");
    }

    @ApiOperation(value = "删除用户",notes = "Hr管理")
    @ApiImplicitParam(name = "rid",value = "用户ID",required = true)
    @DeleteMapping("/{rid}")
    public RespBean deleteById(@PathVariable Integer rid){
        Integer row =hrService.deleteByPrimaryKey(rid);
        if(row>0){
            return RespBean.ok("删除成功");
        }
        return RespBean.error("删除失败");
    }


}
