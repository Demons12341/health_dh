package com.dh.health_dh.FreeMaker;

import freemarker.template.Configuration;
import freemarker.template.Template;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
    @author dh
    @creat 2023-04-30 17:54
*/
public class freemaker {

    public static void main(String[] args) throws Exception {
        //创建配置对象
        Configuration configuration = new Configuration(Configuration.getVersion());

        //设置模板目录
        configuration.setDirectoryForTemplateLoading(new File("D:\\Desktop\\code\\freemarker\\ftl"));

        //设置字符集
        configuration.setDefaultEncoding("utf-8");

        //加载模板文件
        Template template = configuration.getTemplate("test.ftl");

        //准备模板文件中所需要的数据，通常是通过map构造
        Map map = new HashMap();
        map.put("name","曰天");
        map.put("message","谦虚");

        List goodsList=new ArrayList();
        Map goods1=new HashMap();
        goods1.put("name", "苹果");
        goods1.put("price", 5.8);

        Map goods2=new HashMap();
        goods2.put("name", "香蕉");
        goods2.put("price", 2.5);

        Map goods3=new HashMap();
        goods3.put("name", "橘子");
        goods3.put("price", 3.2);

        goodsList.add(goods1);
        goodsList.add(goods2);
        goodsList.add(goods3);

        map.put("goodsList", goodsList);
//        map.put("success",true);

        //创建输出流文件，用于输出静态文件
        Writer writer = new FileWriter("D:\\Desktop\\code\\freemarker\\ftl\\test.html");

        //输出
        template.process(map,writer);
//        关闭流
        writer.close();

    }
}
