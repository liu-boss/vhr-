package com.itdan.my_vhr.service;


import com.itdan.my_vhr.mapper.DepartmentMapper;
import com.itdan.my_vhr.model.Department;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentService {

    private Logger logger=LoggerFactory.getLogger(DepartmentService.class);

    @Autowired
    private DepartmentMapper departmentMapper;

    /**
     * 根据当父节点获取所有部门信息
     * @return
     */
    public List<Department> getAllDepartmentsByPid(){
        //树形节点的根节点ID
        Integer parendId=-1;

        logger.info("根据父节点获取所有部门信息操作");
        List<Department> departments= departmentMapper.getAllDepartmentsByPid(parendId);
        logger.info("根据父节点获取所有部门信息操作成功");
        return departments;
    }

    /**
     * 获取所有部门信息
     * @return
     */
    public List<Department> getAllDepartments(){
        logger.info("获取所有部门信息操作");
        List<Department> departments= departmentMapper.getAllDepartments();
        logger.info("获取所有部门信息操作成功");
        return departments;
    }

    /**
     * 添加部门信息
     * @return
     */
    public void addDep(Department record){
        logger.info("添加部门信息操作");

        if(record==null){
            logger.error("添加部门信息操作，传入的参数为空");
            throw new  NullPointerException("添加部门信息操作，传入的参数为空");
        }
        departmentMapper.addDep(record);
    }

    /**
     * 删除部门操作
     * @param record
     */
    public void deleteDepById(Department record){
        logger.info("删除部门信息操作");

        if(record==null){
            logger.error("删除部门信息操作，传入的参数为空");
            throw new  NullPointerException("删除部门信息操作，传入的参数为空");
        }

        record.setEnabled(true);
        departmentMapper.deleteDepById(record);
    }


}
