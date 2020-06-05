package com.itdan.my_vhr.mapper;

import com.itdan.my_vhr.model.Employee;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;


@Mapper
@Component
public interface EmployeeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Employee record);

    int insertSelective(Employee record);

    Employee selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Employee record);

    int updateByPrimaryKey(Employee record);

    /**
     * 获取所有员工数据，并分页显示
     * @param page
     * @param size
     * @return
     */
    List<Employee> getEmployeeByPage(@Param("page") Integer page,
                                     @Param("size") Integer size,
                                     @Param("emp") Employee employee,
                                     @Param("beginDateSize") Date[] beginDateSize);

    /**
     * 获取员工总数
     * @return
     */
    Long getEmpToltal(@Param("employee") Employee employee);

    Long getTotal(@Param("emp") Employee employee, @Param("beginDateScope") Date[] beginDateScope);

    Integer maxWorkID();

    Integer addEmps(@Param("list") List<Employee> list);

    Employee getEmployeeById(Integer id);

    List<Employee> getEmployeeByPageWithSalary(@Param("page") Integer page, @Param("size") Integer size);

    Integer updateEmployeeSalaryById(@Param("eid") Integer eid, @Param("sid") Integer sid);
}