package com.sdsoon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class TestNettyTcpApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(TestNettyTcpApplication.class, args);
    }


    @Override//为了打包springboot项目
    protected SpringApplicationBuilder configure(
            SpringApplicationBuilder builder) {
        return builder.sources(this.getClass());
    }
}

//@Component
//@Order(value=1)
//public class ServerStarter implements CommandLineRunner{
//      @Override
//      public void run(String... strings) throws Exception {
//          System.out.println("1231231231231233333333333333333333");
//          start();
//      }
//}