package com.nt.sorm;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SormApplicationTests {


    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    DataSource dataSource ;
    @Test
    public void contextLoads() {
//
//
//        DruidDataSource druidDataSource = (DruidDataSource)dataSource;
//        logger.info(druidDataSource.toString());
//        logger.info(String.valueOf(druidDataSource.getInitialSize()));

    }




}
