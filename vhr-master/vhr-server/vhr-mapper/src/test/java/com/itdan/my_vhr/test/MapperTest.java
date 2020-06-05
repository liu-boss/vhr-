package com.itdan.my_vhr.test;

import com.itdan.my_vhr.mapper.EmployeeMapper;
import com.itdan.my_vhr.model.Employee;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = MapperTest.class)
public class MapperTest {

    @Autowired
    private EmployeeMapper employeeMapper;



    @Test
    public void test01(){

        Employee employee = employeeMapper.getEmployeeById(1941);
        System.out.println(employee.toString());
    }
}
