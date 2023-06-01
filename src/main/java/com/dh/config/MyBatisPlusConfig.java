package com.dh.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/*
    @author dh
    @creat 2023-03-07 11:49
*/
@Configuration
@MapperScan("com.dh.Mapper")
public class MyBatisPlusConfig {
}
