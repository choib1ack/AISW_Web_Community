package com.aisw.community.sql;


import com.aisw.community.component.annotation.LogExecutionTime;
import org.junit.jupiter.api.Test;

public class LoginTest{

    @Test
    @LogExecutionTime
    public void signupTest() {
        String username = "dltkddns13@naver.com";
        System.out.println(username);
    }


}
