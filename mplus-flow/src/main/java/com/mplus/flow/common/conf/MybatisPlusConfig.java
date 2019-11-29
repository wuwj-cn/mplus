package com.mplus.flow.common.conf;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.mplus.flow.common.interceptor.ExecutorInterceptor;
import org.apache.ibatis.plugin.Interceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.Properties;

@EnableTransactionManagement
@Configuration
@MapperScan("com.mplus.*.mapper*")
public class MybatisPlusConfig {
    /**
     *   mybatis-plus分页插件
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        PaginationInterceptor page = new PaginationInterceptor();
        page.setDialectType("mysql");
        // 设置请求的页面大于最大页后操作， true调回到首页，false 继续请求  默认false
        // paginationInterceptor.setOverflow(false);
        // 设置最大单页限制数量，默认 500 条，-1 不受限制
        // paginationInterceptor.setLimit(500);
        return page;
    }

    @Bean
    public Interceptor executorInterceptor() {
        ExecutorInterceptor executorInterceptor = new ExecutorInterceptor();
        Properties properties = new Properties();
        properties.setProperty("prop1","value1");
        executorInterceptor.setProperties(properties);
        return executorInterceptor;
    }
}
