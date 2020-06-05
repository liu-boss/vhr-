package com.itdan.my_vhr.controller.sys.basic;

import com.itdan.my_vhr.model.Menu;
import com.itdan.my_vhr.model.RespBean;
import com.itdan.my_vhr.model.Role;
import com.itdan.my_vhr.service.MenuService;
import com.itdan.my_vhr.service.RoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value="权限管理")
@RestController
@RequestMapping("/sys/basic/permiss")
public class PermissConroller {

    @Autowired
    private RoleService roleService;
    @Autowired
    private MenuService menuService;


    @ApiOperation(value = "获取所有角色权限",notes = "权限管理")
    @GetMapping("/")
    public List<Role> getAllRoles(){
            return roleService.getAllRoles();
    }

    @ApiOperation(value = "获取角色树形菜单栏",notes = "权限管理")
    @GetMapping("/menus")
    public List<Menu> getAllMenusByTree(){
        return menuService.getAllMenusByTree();
    }

    @ApiOperation(value="获取已选中的权限",notes = "权限管理")
    @ApiImplicitParam(name = "rid",value = "登入用户的id",required = true)
    @GetMapping("/mids/{rid}")
    public List<Integer> getMidsById(@PathVariable Integer rid){
        return menuService.getMidsByRid(rid);
    }


    @ApiOperation(value="更新用户角色的权限",notes = "权限管理")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "rid",value = "登入用户的id",required = true),
            @ApiImplicitParam(name = "mids", value = "被选中权限角色的ID数组", required = true)
    })
    @PutMapping("/")
    public RespBean updateMids(Integer rid, Integer[] mids){
        Integer row = menuService.updateMids(rid,mids);
           if(row>0){
               return RespBean.ok("更新成功");
           }
           return  RespBean.error("更新失败");
    }



    @ApiOperation(value = "添加权限角色操作",notes = "权限管理")
    @ApiImplicitParam(name = "record",value = "角色实体类",required = true)
    @PostMapping("/")
    public RespBean addPosition(@RequestBody Role record){
        Integer row= roleService.addRole(record);
        if(row>0){
            return RespBean.ok("添加成功");
        }
        return RespBean.error("添加失败");
    }

    @ApiOperation(value = "删除权限操作",notes = "权限管理")
    @ApiImplicitParam(name = "rid",value = "角色ID",required = true)
    @DeleteMapping("/{rid}")
    public RespBean deleteRole(@PathVariable Integer rid){
        Integer row= roleService.deleteRole(rid);
        if(row>0){
            return RespBean.ok("删除成功");
        }
        return RespBean.error("删除失败");
    }


}
