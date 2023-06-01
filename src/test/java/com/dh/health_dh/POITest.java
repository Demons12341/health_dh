package com.dh.health_dh;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/*
    @author dh
    @creat 2023-04-27 0:17
*/
public class POITest {
    //读取数据
    @Test
    public void test1() throws IOException {

        //加载指定文件创建一个Excel对象
        XSSFWorkbook excel = new XSSFWorkbook(new FileInputStream("D:\\Desktop\\test.xlsx"));

        //读取Excel文件中的第一个sheet标签页
        XSSFSheet sheet = excel.getSheetAt(0);

        //遍历sheet标签并获取每一行数据
        for(Row row : sheet){

            //遍历行，获得每个单元格对象
            for (Cell cell : row){
                System.out.println(cell.getStringCellValue());
            }
        }
        //关闭资源
        excel.close();

    }

    @Test
    public void test2() throws IOException {
        XSSFWorkbook excel = new XSSFWorkbook("D:\\Desktop\\test.xlsx");

        XSSFSheet sheet = excel.getSheetAt(0);

        //获得最后一行行号  行号从0 开始
        int lastRowNum = sheet.getLastRowNum();


        for(int i=0;i<=lastRowNum;i++){
            XSSFRow row = sheet.getRow(i);//根据行号获取每一行
            short lastCellNum = row.getLastCellNum();//获得当前行最后一个单元格索引
            for(int j=0;j<lastCellNum;j++){
                XSSFCell cell = row.getCell(j);//根据单元格索引获得对象
                System.out.println(cell.getStringCellValue());
            }
        }

        excel.close();


    }

    //写入数据
    @Test
    public void test3() throws IOException{
        //内存中创建一个excel
        XSSFWorkbook excel = new XSSFWorkbook();

        //创建一个工作表对象
        XSSFSheet sheet = excel.createSheet("曰天健康");

        //创建一个行对象
        XSSFRow title = sheet.createRow(0);
        //创建单元格对象
        title.createCell(0).setCellValue("姓名");
        title.createCell(1).setCellValue("地址");
        title.createCell(2).setCellValue("年龄");


        //创建一个行对象
        XSSFRow dataRow = sheet.createRow(1);
        //创建单元格对象
        dataRow.createCell(0).setCellValue("小明");
        dataRow.createCell(1).setCellValue("北京");
        dataRow.createCell(2).setCellValue("20");
        FileOutputStream out = new FileOutputStream(new File("D:\\Desktop\\曰天健康.xlsx"));

        excel.write(out);
        //刷新
        out.flush();
        excel.close();
    }
}
