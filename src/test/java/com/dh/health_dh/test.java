package com.dh.health_dh;

import com.aliyuncs.exceptions.ClientException;
import com.dh.common.untils.SMSUtils;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/*
    @author dh
    @creat 2023-04-30 16:11
*/
public class test {

    @Test
    public void test22() throws ClientException {
        SMSUtils.sendShortMessage("SMS_460670291","13668601061","1234");
    }

    @Test
        public  void f() {
            String password = "123456";
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            String encodedPassword = encoder.encode(password);
            System.out.println("Encoded password: " + encodedPassword);
        }
    }

