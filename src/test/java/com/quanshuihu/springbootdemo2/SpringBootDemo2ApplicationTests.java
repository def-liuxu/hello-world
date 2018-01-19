package com.quanshuihu.springbootdemo2;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
//@ContextConfiguration(classes = SpringBootDemo2Application.class)
@SpringBootTest
public class SpringBootDemo2ApplicationTests {

	@Autowired
	private ApplicationContext applicationContext;


    @Test
    public void contextLoads() {
        Assert.assertNotNull("BarService不为Null",this.applicationContext.getBean(BarService.class));
        Assert.assertNotNull("FooService不为Null",this.applicationContext.getBean(FooService.class));
    }

}
