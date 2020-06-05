package com.itdan.my_vhr.mapper;

import com.itdan.my_vhr.model.Department;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper
public interface DepartmentMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Department record);

    int insertSelective(Department record);

    Department selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Department record);

    int updateByPrimaryKey(Department record);

    /**
     * 获取所有部门（树形结构展示）
     * @param pid
     * @return
     */
    List<Department> getAllDepartmentsByPid(Integer pid);

    void addDep(Department dep);

    void deleteDepById(Department dep);

    /**
     * 获取所有部门信息
     * @return
     */
    List<Department> getAllDepartments();
}