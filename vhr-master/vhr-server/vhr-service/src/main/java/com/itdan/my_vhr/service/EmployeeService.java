package com.itdan.my_vhr.service;

import com.itdan.my_vhr.mapper.EmployeeMapper;
import com.itdan.my_vhr.mapper.MailSendLogMapper;
import com.itdan.my_vhr.model.Employee;
import com.itdan.my_vhr.model.MailConstants;
import com.itdan.my_vhr.model.MailSendLog;
import com.itdan.my_vhr.model.RespPageBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class EmployeeService {

    private Logger logger = LoggerFactory.getLogger(EmployeeService.class);

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private EmployeeMapper employeeMapper;

    @Autowired
    private SimpleDateFormat yearSimple;

    @Autowired
    private SimpleDateFormat mouthSimple;

    @Autowired
    private DecimalFormat decimalFormat;

    @Autowired
    private MailSendLogMapper mailSendLogMapper;

    /**
     * 获取所有员工信息(分页显示)
     *
     * @param page
     * @param size
     * @param employee
     * @param beginDateSize
     * @return
     */
    public RespPageBean getEmployeeByPage(Integer page,
                                          Integer size,
                                          Employee employee,
                                          Date[] beginDateSize) {

        logger.info("获取所有员工信息操作(分页显示)");
        if (page != null && size != null) {
            page = ((page - 1) * size);
        }
        List<Employee> employees = employeeMapper.getEmployeeByPage(page, size, employee, beginDateSize);
        Long total = employeeMapper.getTotal(employee, beginDateSize);
        RespPageBean respPageBean = new RespPageBean(total, employees);

        logger.info("获取所有员工信息操作成功");
        return respPageBean;
    }


    /**
     * 添加员工
     *
     * @param record
     * @return
     */
    public Integer addEmployee(Employee record) {
        logger.info("添加员工操作");
        if (record == null) {
            logger.error("添加员工操作失败参数为空");
            throw new NullPointerException("添加员工操作失败参数为空");
        }
        //计算合同期限
        Date beginContract = record.getBeginContract();
        Date endContract = record.getEndContract();
        double mouth = (Double.parseDouble(yearSimple.format(endContract)) - Double.parseDouble(yearSimple.format(beginContract))) * 12 +
                (Double.parseDouble(mouthSimple.format(endContract)) - Double.parseDouble(mouthSimple.format(beginContract)));
        record.setContractTerm(Double.parseDouble(decimalFormat.format(mouth / 12)));
        Integer row = employeeMapper.insertSelective(record);


        //添加员工成功后，将添加的员工信息传到消息中间件
        if (row == 1) {
            //获取刚插入的员工信息
            Employee employee = employeeMapper.getEmployeeById(record.getId());

            logger.info("发送员工入职消息");
            //添加消息记录
            String msgId = UUID.randomUUID().toString();
            MailSendLog mailSendLog = new MailSendLog();
            mailSendLog.setMsgId(msgId);
            mailSendLog.setEmpId(employee.getId());
            mailSendLog.setExchange(MailConstants.MAIL_EXCHANGE_NAME);
            mailSendLog.setRouteKey(MailConstants.MAIL_ROUTING_KEY_NAME);
            mailSendLog.setTryTime(new Date(System.currentTimeMillis() + 1000 * 60 * MailConstants.MSG_TIMEOUT));
            mailSendLog.setCreateTime(new Date());
            mailSendLogMapper.insert(mailSendLog);

            //发送消息
            rabbitTemplate.convertAndSend(MailConstants.MAIL_EXCHANGE_NAME,
                                          MailConstants.MAIL_ROUTING_KEY_NAME,
                                          employee, new CorrelationData(msgId));

        }
        logger.info("添加员工操作成功");
        return row;
    }

    /**
     * 获取员工最大工号
     *
     * @return
     */
    public Integer getmaxWordId() {
        logger.info("获取员工最大工号");
        Integer wordId = employeeMapper.maxWorkID();
        logger.info("获取员工最大工号成功");
        return wordId;
    }

    /**
     * 更新员工信息
     *
     * @return
     */
    public Integer updateEmployee(Employee record) {
        logger.info("更新员工信息");
        if (record == null) {
            logger.error("更新员工操作失败参数为空");
            throw new NullPointerException("更新员工操作失败参数为空");
        }
        Integer row = employeeMapper.updateByPrimaryKeySelective(record);
        logger.info("更新员工信息成功");
        return row;
    }

    /**
     * 删除员工
     *
     * @param id
     * @return
     */
    public Integer deleteEmployee(Integer id) {
        logger.info("删除员工信息");
        if (id == null) {
            logger.error("删除员工操作失败参数为空");
            throw new NullPointerException("删除员工操作失败参数为空");
        }
        Integer row = employeeMapper.deleteByPrimaryKey(id);
        logger.info("删除员工信息成功");
        return row;
    }

    /**
     * 将员工集合插入数据库中
     *
     * @param employees
     * @return
     */
    public Integer addEmps(List<Employee> employees) {
        logger.info("将员工集合插入数据库中");
        if (employees == null) {
            logger.error("将员工集合插入数据库中参数为空");
            throw new NullPointerException("将员工集合插入数据库中参数为空");
        }
        Integer row = employeeMapper.addEmps(employees);
        logger.info("将员工集合插入数据库中成功");
        return row;
    }

    /**
     * 获取所有员工账套(分页显示)
     *
     * @param page
     * @param size
     * @return
     */
    public RespPageBean getEmployeeWithSalary(Integer page, Integer size) {
        logger.info("获取所有员工账套(分页显示)操作");
        if (page != null && size != null) {
            page = ((page - 1) * size);
        }
        List<Employee> employees = employeeMapper.getEmployeeByPageWithSalary(page, size);
        Long total = employeeMapper.getTotal(null, null);
        logger.info("获取所有员工账套(分页显示)操作成功");
        return new RespPageBean(total, employees);
    }

    /**
     * 更新员工账套
     *
     * @param eid
     * @param sid
     * @return
     */
    public Integer updateSalaryCfg(Integer eid, Integer sid) {
        logger.info("获取所有员工账套操作");
        if (eid == null || sid == null) {
            logger.error("更新员工账套失败，参数为空");
            throw new NullPointerException("更新员工账套失败，参数为空");
        }
        Integer row = employeeMapper.updateEmployeeSalaryById(eid, sid);
        logger.info("获取所有员工账套操作成功");
        return row;
    }

    public Employee getEmpById(Integer empId) {
          return  employeeMapper.getEmployeeById(empId);
    }
}
