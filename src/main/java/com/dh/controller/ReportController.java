package com.dh.controller;

import com.dh.common.constant.MessageConstant;
import com.dh.common.entity.Result;
import com.dh.service.MemberService;
import com.dh.service.SetmealService;
import com.dh.service.reportService;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/*
    @author dh
    @creat 2023-05-06 15:19
    图形报表
*/
@RestController
@RequestMapping("/report/")
public class ReportController {


    @Autowired
    private MemberService memberService;

    @Autowired
    private SetmealService setmealService;

    @Autowired
    private reportService reportService;

    //会员数量统计
    @RequestMapping("getMemberReport")
    public Result getMemberReport() {

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -12);//获得当前日期之前12个月的日期
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            calendar.add(Calendar.MONTH, 1);
            list.add(new SimpleDateFormat("yyyy-MM").format(calendar.getTime()));
        }
        Map<String, Object> map = new HashMap<>();
        map.put("months", list);
        List<Integer> memberCount = memberService.findMemberCountByMonth(list);
        map.put("memberCount", memberCount);
        return new Result(true, MessageConstant.GET_MEMBER_NUMBER_REPORT_SUCCESS, map);
    }

    @RequestMapping("getSetmealReport")
    public Result getSetmealReport() {
        return new Result(true, MessageConstant.GET_SETMEAL_COUNT_REPORT_SUCCESS, setmealService.findSetmealCount());
    }

    @RequestMapping("getBusinessReportData")
    public Result getBusinessReportData() {
        try {
            Map<String, Object> result = reportService.getBusinessReport();
            return new Result(true, MessageConstant.GET_BUSINESS_REPORT_SUCCESS, result);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(true, MessageConstant.GET_BUSINESS_REPORT_FAIL);
        }
    }

    @RequestMapping("exportBusinessReport")
    public Result exportBusinessReport(HttpServletRequest request, HttpServletResponse response) throws Exception {
//        try {
            //远程调用报表服务获取报表数据
            Map<String, Object> result = reportService.getBusinessReport();

            //取出返回结果数据，准备将报表数据写入到Excel文件中
            String reportDate = (String) result.get("reportDate");
            Integer todayNewMember = (Integer) result.get("todayNewMember");
            Integer totalMember = (Integer) result.get("totalMember");
            Integer thisWeekNewMember = (Integer) result.get("thisWeekNewMember");
            Integer thisMonthNewMember = (Integer) result.get("thisMonthNewMember");
            Integer todayOrderNumber = (Integer) result.get("todayOrderNumber");
            Integer thisWeekOrderNumber = (Integer) result.get("thisWeekOrderNumber");
            Integer thisMonthOrderNumber = (Integer) result.get("thisMonthOrderNumber");
            Integer todayVisitsNumber = (Integer) result.get("todayVisitsNumber");
            Integer thisWeekVisitsNumber = (Integer) result.get("thisWeekVisitsNumber");
            Integer thisMonthVisitsNumber = (Integer) result.get("thisMonthVisitsNumber");
            List<Map> hotSetmeal = (List<Map>) result.get("hotSetmeal");
            //获得Excel模板文件绝对路径
                String temlateRealPath = "classpath:static/template/report_template.xlsx";
                File file = ResourceUtils.getFile(temlateRealPath);
                String absolutePath = file.getAbsolutePath();
        //读取模板文件创建Excel表格对象
            XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(new File(absolutePath)));
            XSSFSheet sheet = workbook.getSheetAt(0);
            XSSFRow row = sheet.getRow(2);
            row.getCell(5).setCellValue(reportDate);//日期
            row = sheet.getRow(4);
            row.getCell(5).setCellValue(todayNewMember);//新增会员数（本日）
            row.getCell(7).setCellValue(totalMember);//总会员数
            row = sheet.getRow(5);
            row.getCell(5).setCellValue(thisWeekNewMember);//本周新增会员数
            row.getCell(7).setCellValue(thisMonthNewMember);//本月新增会员数
            row = sheet.getRow(7);
            row.getCell(5).setCellValue(todayOrderNumber);//今日预约数
            row.getCell(7).setCellValue(todayVisitsNumber);//今日到诊数
            row = sheet.getRow(8);
            row.getCell(5).setCellValue(thisWeekOrderNumber);//本周预约数
            row.getCell(7).setCellValue(thisWeekVisitsNumber);//本周到诊数
            row = sheet.getRow(9);
            row.getCell(5).setCellValue(thisMonthOrderNumber);//本月预约数
            row.getCell(7).setCellValue(thisMonthVisitsNumber);//本月到诊数
            int rowNum = 12;
            for (Map map : hotSetmeal) {//热门套餐
                String name = (String) map.get("name");
                Long setmeal_count = (Long) map.get("setmeal_count");
                BigDecimal proportion = (BigDecimal) map.get("proportion");
                row = sheet.getRow(rowNum++);
                row.getCell(4).setCellValue(name);//套餐名称
                row.getCell(5).setCellValue(setmeal_count);//预约数量
                row.getCell(6).setCellValue(proportion.doubleValue());//占比
            }

            //通过输出流进行文件下载
            ServletOutputStream out = response.getOutputStream();
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("content-Disposition", "attachment;filename=report.xlsx");
            workbook.write(out);
            out.flush();
            out.close();
            workbook.close();

            return null;
//        } catch (Exception e) {
//            return new Result(false, MessageConstant.GET_BUSINESS_REPORT_FAIL, null);
//        }
    }
}
//        try {
//            Map<String, Object> result = reportService.getBusinessReport();
//            // 省略中间代码
//            writeExcel(workbook, sheet, result, hotSetmeal);
//            response.setContentType("application/vnd.ms-excel");
//            response.setHeader("content-Disposition", "attachment;filename=report.xlsx");
//            workbook.write(out);
//            out.flush();
//        } catch (FileNotFoundException e) {
//            logger.error("找不到报表模板文件", e);
//            return new Result(false, MessageConstant.GET_BUSINESS_REPORT_FAIL, null);
//        } catch (IOException e) {
//            logger.error("读写报表文件失败", e);
//            return new Result(false, MessageConstant.GET_BUSINESS_REPORT_FAIL, null);
//        } catch (Exception e) {
//            logger.error("生成报表失败", e);
//            return new Result(false, MessageConstant.GET_BUSINESS_REPORT_FAIL, null);
//        } finally {
//            try {
//                if (out != null) {
//                    out.close();
//                }
//                if (workbook != null) {
//                    workbook.close();
//                }
//            } catch (IOException e) {
//                logger.error("关闭报表文件流失败", e);
//            }
//        }
//
//    }
//    private void writeExcel(XSSFWorkbook workbook, XSSFSheet sheet, Map<String, Object> data, List<Map> hotSetmeal) {
//        XSSFRow row = sheet.getRow(2);
//        row.getCell(5).setCellValue((String) data.get("reportDate"));//日期
//
//        row = sheet.getRow(4);
//        row.getCell(5).setCellValue((Integer) data.get("todayNewMember"));//新增会员数（本日）
//        row.getCell(7).setCellValue((Integer) data.get("totalMember"));//总会员数
//
//        row = sheet.getRow(5);
//        row.getCell(5).setCellValue((Integer) data.get("thisWeekNewMember"));//本周新增会员数
//        row.getCell(7).setCellValue((Integer) data.get("thisMonthNewMember"));//本月新增会员数
//
//        row = sheet.getRow(7);
//        row.getCell(5).setCellValue((Integer) data.get("todayOrderNumber"));//今日预约数
//        row.getCell(7).setCellValue((Integer) data.get("todayVisitsNumber"));//今日到诊数
//
//        row = sheet.getRow(8);
//        row.getCell(5).setCellValue((Integer) data.get("thisWeekOrderNumber"));//本周预约数
//        row.getCell(7).setCellValue((Integer) data.get("thisWeekVisitsNumber"));//本周到诊数
//
//        row = sheet.getRow(9);
//        row.getCell(5).setCellValue((Integer) data.get("thisMonthOrderNumber"));//本月预约数
//        row.getCell(7).setCellValue
//
//    }
