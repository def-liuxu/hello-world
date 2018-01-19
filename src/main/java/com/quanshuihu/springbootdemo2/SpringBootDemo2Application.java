package com.quanshuihu.springbootdemo2;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.stereotype.Component;

import java.util.function.Supplier;

/**
 *  spring 5.x中代码方式注入bean
 */

//@Configuration
//@ComponentScan
@SpringBootApplication
public class SpringBootDemo2Application {

    //相当于<bean id="barService" class="...BarService"
//	@Bean
//	BarService barService(){
//		return  new BarService();
//	}

//	@Component
	public static class MyBDRPP implements BeanDefinitionRegistryPostProcessor{
        @Override
        public void postProcessBeanDefinitionRegistry(
                BeanDefinitionRegistry bdr) throws BeansException {

            System.out.println("Hello MyBdRPP");

            //重头戏
            //注入一个bean为barService到spring容器中
            bdr.registerBeanDefinition("barService",
                    BeanDefinitionBuilder.genericBeanDefinition(BarService.class).getBeanDefinition());

            //fooService依赖barService哦
            bdr.registerBeanDefinition("fooService",
                    BeanDefinitionBuilder.genericBeanDefinition(FooService.class, new Supplier<FooService>() {
                        @Override
                        public FooService get() {
                            return new FooService(BeanFactory.class.cast(bdr).getBean(BarService.class));
                        }
                    }).getBeanDefinition());

        }

        @Override
        public void postProcessBeanFactory(
                ConfigurableListableBeanFactory bf) throws BeansException {

        }
    }
//
//
//	@Bean
//	FooService fooService(BarService barService){
//		return  new FooService(barService);
//	}

	public static void main(String[] args) {
		SpringApplication.run(SpringBootDemo2Application.class, args);

//		ApplicationContext ac = new AnnotationConfigApplicationContext(SpringBootDemo2Application.class);


	}
}

class ProgrammaticBeanDefinitionInitializer implements
        ApplicationContextInitializer<GenericApplicationContext>{

    @Override
    public void initialize(GenericApplicationContext applicationContext) {
        System.out.println("spring 初始化程序！");
        applicationContext.registerBean(BarService.class);
        applicationContext.registerBean(FooService.class,
                ()->new FooService(applicationContext.getBean(BarService.class)));
    }
}

//@Component
class BarService{

}

//@Component
class FooService{

	private final BarService barService;

	FooService(BarService barService) {
		this.barService = barService;
	}
}

