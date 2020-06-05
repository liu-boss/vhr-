package com.itdan.my_vhr.utils;

import com.itdan.my_vhr.model.*;
import org.apache.poi.hpsf.DocumentSummaryInformation;
import org.apache.poi.hpsf.SummaryInformation;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 导出导入Excel操作处理
 */
public class POIUtil {


    /**
     * 将员工数据导出Excle表格
     *
     * @param employees 员工数据
     * @return
     */
    public static ResponseEntity<byte[]> exportEmp2Excle(List<Employee> employees) {
        //创建一个Excle文档
        HSSFWorkbook hssfWorkbook = new HSSFWorkbook();
        //创建文档摘要
        hssfWorkbook.createInformationProperties();
        //获取并配置摘要信息
        DocumentSummaryInformation information = hssfWorkbook.getDocumentSummaryInformation();
        //设置文档类别
        information.setCategory("员工信息");
        //设置文的管理员
        information.setManager("dan");
        //设置公司信息
        information.setCompany("xxxx公司");
        //获取文档摘要信息
        SummaryInformation summaryInformation = hssfWorkbook.getSummaryInformation();
        //设置文档标题和作者
        summaryInformation.setTitle("员工信息表");
        summaryInformation.setAuthor("dan");
        summaryInformation.setComments("备注信息");

        //设置Style
        HSSFCellStyle headerStyle = hssfWorkbook.createCellStyle();
        headerStyle.setFillBackgroundColor(IndexedColors.YELLOW.index);
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        //设置日期格式
        HSSFCellStyle dateCellStyle = hssfWorkbook.createCellStyle();
        dateCellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("m/d/yy"));

        //处理数据
        //创建表单
        HSSFSheet sheet = hssfWorkbook.createSheet("员工信息表");

        //设置列宽
        sheet.setColumnWidth(0, 5 * 256);
        sheet.setColumnWidth(1, 13 * 256);
        sheet.setColumnWidth(2, 13 * 256);
        sheet.setColumnWidth(3, 13 * 256);
        sheet.setColumnWidth(4, 13 * 256);
        sheet.setColumnWidth(5, 13 * 256);
        sheet.setColumnWidth(6, 13 * 256);
        sheet.setColumnWidth(7, 13 * 256);
        sheet.setColumnWidth(8, 13 * 256);
        sheet.setColumnWidth(9, 13 * 256);
        sheet.setColumnWidth(10, 13 * 256);
        sheet.setColumnWidth(11, 13 * 256);
        sheet.setColumnWidth(12, 13 * 256);
        sheet.setColumnWidth(13, 13 * 256);
        sheet.setColumnWidth(14, 13 * 256);
        sheet.setColumnWidth(15, 13 * 256);
        sheet.setColumnWidth(16, 13 * 256);
        sheet.setColumnWidth(17, 13 * 256);
        sheet.setColumnWidth(18, 13 * 256);
        sheet.setColumnWidth(19, 13 * 256);
        sheet.setColumnWidth(20, 13 * 256);
        sheet.setColumnWidth(21, 13 * 256);
        sheet.setColumnWidth(22, 13 * 256);
        sheet.setColumnWidth(23, 13 * 256);
        sheet.setColumnWidth(24, 13 * 256);
        sheet.setColumnWidth(25, 13 * 256);
        sheet.setColumnWidth(26, 13 * 256);

        //创建标题行和列
        HSSFRow r0 = sheet.createRow(0);
        HSSFCell c0 = r0.createCell(0);
        c0.setCellValue("员工编号");
        //设置背景颜色
        c0.setCellStyle(headerStyle);

        HSSFCell c1 = r0.createCell(1);
        c1.setCellValue("员工姓名");
        //设置背景颜色
        c1.setCellStyle(headerStyle);

        HSSFCell c2 = r0.createCell(2);
        c2.setCellValue("性别");
        //设置背景颜色
        c2.setCellStyle(headerStyle);

        HSSFCell c3 = r0.createCell(3);
        c3.setCellValue("出生日期");
        //设置背景颜色
        c3.setCellStyle(headerStyle);

        HSSFCell c4 = r0.createCell(4);
        c4.setCellValue("身份证号");
        //设置背景颜色
        c4.setCellStyle(headerStyle);

        HSSFCell c5 = r0.createCell(5);
        c5.setCellValue("婚姻状况");
        //设置背景颜色
        c5.setCellStyle(headerStyle);

        HSSFCell c6 = r0.createCell(6);
        c6.setCellValue("民族");
        //设置背景颜色
        c6.setCellStyle(headerStyle);

        HSSFCell c7 = r0.createCell(7);
        c7.setCellValue("籍贯");
        //设置背景颜色
        c7.setCellStyle(headerStyle);


        HSSFCell c8 = r0.createCell(8);
        c8.setCellValue("政治面貌");
        //设置背景颜色
        c8.setCellStyle(headerStyle);


        HSSFCell c9 = r0.createCell(9);
        c9.setCellValue("邮箱");
        //设置背景颜色
        c9.setCellStyle(headerStyle);


        HSSFCell c10 = r0.createCell(10);
        c10.setCellValue("手机号码");
        //设置背景颜色
        c10.setCellStyle(headerStyle);

        HSSFCell c11 = r0.createCell(11);
        c11.setCellValue("联系地址");
        //设置背景颜色
        c11.setCellStyle(headerStyle);


        HSSFCell c12 = r0.createCell(12);
        c12.setCellValue("所属部门");
        //设置背景颜色
        c12.setCellStyle(headerStyle);


        HSSFCell c13 = r0.createCell(13);
        c13.setCellValue("职称");
        //设置背景颜色
        c13.setCellStyle(headerStyle);

        HSSFCell c14 = r0.createCell(14);
        c14.setCellValue("职位");
        //设置背景颜色
        c14.setCellStyle(headerStyle);

        HSSFCell c15 = r0.createCell(15);
        c15.setCellValue("聘用形式");
        //设置背景颜色
        c15.setCellStyle(headerStyle);

        HSSFCell c16 = r0.createCell(16);
        c16.setCellValue("最高学历");
        //设置背景颜色
        c16.setCellStyle(headerStyle);

        HSSFCell c17 = r0.createCell(17);
        c17.setCellValue("所属专业");
        //设置背景颜色
        c17.setCellStyle(headerStyle);

        HSSFCell c18 = r0.createCell(18);
        c18.setCellValue("毕业院校");
        //设置背景颜色
        c18.setCellStyle(headerStyle);

        HSSFCell c19 = r0.createCell(19);
        c19.setCellValue("入职日期");
        //设置背景颜色
        c19.setCellStyle(headerStyle);

        HSSFCell c20 = r0.createCell(20);
        c20.setCellValue("在职状态");
        //设置背景颜色
        c20.setCellStyle(headerStyle);

        HSSFCell c21 = r0.createCell(21);
        c21.setCellValue("工号");
        //设置背景颜色
        c21.setCellStyle(headerStyle);

        HSSFCell c22 = r0.createCell(22);
        c22.setCellValue("合同期限(年)");
        //设置背景颜色
        c22.setCellStyle(headerStyle);

        HSSFCell c23 = r0.createCell(23);
        c23.setCellValue("转正日期");
        //设置背景颜色
        c23.setCellStyle(headerStyle);

        HSSFCell c24 = r0.createCell(24);
        c24.setCellValue("离职日期");
        //设置背景颜色
        c24.setCellStyle(headerStyle);

        HSSFCell c25 = r0.createCell(25);
        c25.setCellValue("合同起始日期");
        //设置背景颜色
        c25.setCellStyle(headerStyle);

        HSSFCell c26 = r0.createCell(26);
        c26.setCellValue("合同终止日期");
        //设置背景颜色
        c26.setCellStyle(headerStyle);


        //遍历数据
        for (int i = 0; i <employees.size() ; i++) {
          Employee emp=  employees.get(i);
          HSSFRow r=sheet.createRow(i+1);
            r.createCell(0).setCellValue(emp.getId());
            r.createCell(1).setCellValue(emp.getName());
            r.createCell(2).setCellValue(emp.getGender());
            HSSFCell cell3 = r.createCell(3);
            cell3.setCellValue(emp.getBirthday());
            cell3.setCellStyle(dateCellStyle);
            r.createCell(4).setCellValue(emp.getIdCard());
            r.createCell(5).setCellValue(emp.getWedlock());
            r.createCell(6).setCellValue(emp.getNation().getName());
            r.createCell(7).setCellValue(emp.getNativePlace());
            r.createCell(8).setCellValue(emp.getPoliticsstatus().getName());
            r.createCell(9).setCellValue(emp.getEmail());
            r.createCell(10).setCellValue(emp.getPhone());
            r.createCell(11).setCellValue(emp.getAddress());
            r.createCell(12).setCellValue(emp.getDepartment().getName());
            r.createCell(13).setCellValue(emp.getJobLevel().getName());
            r.createCell(14).setCellValue(emp.getPosition().getName());
            r.createCell(15).setCellValue(emp.getEngageForm());
            r.createCell(16).setCellValue(emp.getTiptopDegree());
            r.createCell(17).setCellValue(emp.getSpecialty());
            r.createCell(18).setCellValue(emp.getSchool());
            HSSFCell cell19 = r.createCell(19);
            cell19.setCellValue(emp.getBeginDate());
            cell19.setCellStyle(dateCellStyle);
            r.createCell(20).setCellValue(emp.getWorkState());
            r.createCell(21).setCellValue(emp.getWorkID());
            r.createCell(22).setCellValue(emp.getContractTerm());
            HSSFCell cell23 = r.createCell(23);
            cell23.setCellValue(emp.getConversionTime());
            cell23.setCellStyle(dateCellStyle);
            HSSFCell cell24= r.createCell(24);
            cell24.setCellValue(emp.getNotWorkDate());
            cell24.setCellStyle(dateCellStyle);
            HSSFCell cell25 = r.createCell(25);
            cell25.setCellValue(emp.getBeginContract());
            cell25.setCellStyle(dateCellStyle);
            HSSFCell cell26 = r.createCell(26);
            cell26.setCellValue(emp.getEndContract());
            cell26.setCellStyle(dateCellStyle);



        }
        ByteArrayOutputStream out=new ByteArrayOutputStream();
        HttpHeaders httpHeaders=new HttpHeaders();
        try {
            httpHeaders.setContentDispositionFormData("attachment",
                    new String("员工信息表.xls".getBytes("UTF-8"),"ISO-8859-1"));
           httpHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            hssfWorkbook.write(out);
        }catch (IOException e){
            e.printStackTrace();
        }
        return new ResponseEntity<byte[]>(out.toByteArray(),httpHeaders,HttpStatus.CREATED);
    }

    /**
     * 将Excle表格转换成数据库对象
     * @param multipartFile
     * @param nations
     * @param politicsstatus
     * @param departments
     * @param positions
     * @param jobLevels
     * @return
     */
    public static List<Employee> importExcle2Emp(MultipartFile multipartFile,
                    List<Nation> nations,
                    List<Politicsstatus> politicsstatus,
                    List<Department> departments,
                    List<Position> positions,
                    List<JobLevel> jobLevels){
            List<Employee> employees=new ArrayList<>();
            Employee employee=null;
        try {
            //创建一个workBook对象
            HSSFWorkbook workbook = new HSSFWorkbook(multipartFile.getInputStream());
            //获取workbook中的表单数量
            int numberOfSheets = workbook.getNumberOfSheets();
            for (int i = 0; i <numberOfSheets ; i++) {
                //获取表单
                HSSFSheet sheetAt = workbook.getSheetAt(i);
                //获取表单行数
                int rows = sheetAt.getPhysicalNumberOfRows();
                for (int j = 0; j <rows ; j++) {
                    //跳过标题行
                    if(j==0){
                        continue;
                    }
                    //获取行
                    HSSFRow row = sheetAt.getRow(j);
                    if(row==null){
                        continue;//防止出现空行
                    }
                    //获取列数
                    int physicalNumberOfCells = row.getPhysicalNumberOfCells();
                    employee=new Employee();
                    for (int k = 0; k <physicalNumberOfCells ; k++) {
                        HSSFCell cell = row.getCell(k);
                        switch (cell.getCellType()){
                            case STRING:
                                String stringCellValue = cell.getStringCellValue();

                                switch (k){
                                    case 1:
                                        employee.setName(stringCellValue);
                                        break;
                                    case 2:
                                        employee.setGender(stringCellValue);
                                        break;
                                    case 4:
                                        employee.setIdCard(stringCellValue);
                                        break;
                                    case 5:
                                        employee.setWedlock(stringCellValue);
                                        break;
                                    case 6:
                                         int nationIndex=nations.indexOf(new Nation(stringCellValue));
                                        employee.setNationId(nations.get(nationIndex).getId());
                                        break;
                                    case 7:
                                        employee.setNativePlace(stringCellValue);
                                        break;
                                    case 8:
                                        int politicIndex=politicsstatus.indexOf(new Politicsstatus(stringCellValue));
                                        employee.setPoliticId(politicsstatus.get(politicIndex).getId());
                                        break;
                                    case 9:
                                        employee.setEmail(stringCellValue);
                                        break;
                                    case 10:
                                        employee.setPhone(stringCellValue);
                                        break;
                                    case 11:
                                        employee.setAddress(stringCellValue);
                                        break;
                                    case 12:
                                         int depIndex= departments.indexOf(new Department(stringCellValue));
                                        employee.setDepartmentId(departments.get(depIndex).getId());
                                        break;
                                    case 13:
                                        int jobIndex= jobLevels.indexOf(new JobLevel(stringCellValue));
                                        employee.setJobLevelId(jobLevels.get(jobIndex).getId());
                                        break;
                                    case 14:
                                        int positionIndex= positions.indexOf(new Position(stringCellValue));
                                        employee.setPosId(positions.get(positionIndex).getId());
                                        break;
                                    case 15:
                                        employee.setEngageForm(stringCellValue);
                                        break;
                                    case 16:
                                        employee.setTiptopDegree(stringCellValue);
                                        break;
                                    case 17:
                                        employee.setSpecialty(stringCellValue);
                                        break;
                                    case 18:
                                        employee.setSchool(stringCellValue);
                                        break;
                                    case 20:
                                        employee.setWorkState(stringCellValue);
                                        break;
                                    case 21:
                                        employee.setWorkID(stringCellValue);
                                        break;

                                }
                                break;
                                //类型要不是string的话在下面处理
                                default:{
                                    switch (k) {
                                        case 3:
                                            employee.setBirthday(cell.getDateCellValue());
                                            break;
                                        case 19:
                                            employee.setBeginDate(cell.getDateCellValue());
                                            break;
                                        case 22:
                                            employee.setContractTerm(cell.getNumericCellValue());
                                            break;
                                        case 23:
                                            employee.setConversionTime(cell.getDateCellValue());
                                            break;
                                        case 24:
                                            employee.setNotWorkDate(cell.getDateCellValue());
                                            break;
                                        case 25:
                                            employee.setBeginContract(cell.getDateCellValue());
                                            break;
                                        case 26:
                                            employee.setEndContract(cell.getDateCellValue());
                                            break;

                                    }
                                }
                                break;
                        }
                    }
                    employees.add(employee);
                }
            }
            System.out.println(employees.toString());


        }catch (IOException e){
              e.printStackTrace();
        }

        return null;

    }
}
