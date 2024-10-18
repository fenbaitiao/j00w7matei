package com.fentb.ftbapisdk;

import com.fentb.ftbapisdk.client.FbtApiClient;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author 曹某
 * @version 1.0
 * description:
 */
// 通过 @Configuration 注解,将该类标记为一个配置类,告诉 Spring 这是一个用于配置的类
@Configuration
// 能够读取application.yml的配置,读取到配置之后,把这个读到的配置设置到我们这里的属性中,
@ConfigurationProperties("fbtapi.client")//yml文件中前缀
@Data
// @ComponentScan 注解用于自动扫描组件，使得 Spring 能够自动注册相应的 Bean
@ComponentScan
public class FbtapiClientConfig {
    private String accessKey;

    private String secretKey;
    @Bean
    public FbtApiClient fbtApiClient(){
        return new FbtApiClient(accessKey,secretKey);
    }
}
