package com.dupenghao.knife4jautoconfig.autoconfigure;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.logging.Logger;


@Configuration
@ConditionalOnProperty(prefix = "knife4j",name="autoEnable",havingValue = "true")
@EnableConfigurationProperties({MyKnife4jAutoConfiguration.Knife4jProperties.class})
public class MyKnife4jAutoConfiguration {

    @Bean
    public Docket apiDocket(Knife4jProperties knife4jProperties) {
        Docket docket = new Docket(springfox.documentation.spi.DocumentationType.SWAGGER_2);
        docket.apiInfo(apiInfo(knife4jProperties))
                .groupName("webApi")
                .select()
                .apis(RequestHandlerSelectors.basePackage(knife4jProperties.getBasePackage()))
                .build();
        return docket;
    }

    public ApiInfo apiInfo(Knife4jProperties knife4jProperties) {
        ApiInfo apiInfo = new ApiInfoBuilder()
                .title(knife4jProperties.getTitle())
                .description(knife4jProperties.getDescription())
                .version(knife4jProperties.getVersion())
                .contact(new Contact(knife4jProperties.name, knife4jProperties.url, knife4jProperties.email))
                .build();
        return apiInfo;
    }

    @ConfigurationProperties(prefix = "knife4j")
    static class Knife4jProperties implements InitializingBean {

        private Logger logger=Logger.getLogger("Knife4jProperties");

        private String basePackage;

        private String title="";
        private String description="";
        private String version="1.0";
        private String name="dph";
        private String url="www.dupenghao.com";
        private String email="xb8023xx@gmail.com";

        public String getBasePackage() {
            return basePackage;
        }

        public void setBasePackage(String basePackage) {
            this.basePackage = basePackage;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        @Override
        public void afterPropertiesSet() throws Exception {
            logger.info("Knife4jProperties init success!");
        }
    }

}
