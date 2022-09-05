package com.example.portfolio;

import com.example.portfolio.service.apitest.ApiTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class PortfolioApplicationTests {

    @Autowired
    ApiTest apiTest_1;

    @Test
    void apiTest_2(){
        apiTest_1.resultAPI();
    }

    @Test
    void contextLoads() {
    }

}
